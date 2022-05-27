package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class NewAccountsRevenue extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(NewAccountsRevenue.class);
	public NewAccountsRevenue() throws IOException {
		super("New Accounts Revenue", "Revenue");
	}
	// to create the year list box
	public void init(){
		Listbox usageYear = (Listbox)this.getFellow("4. Usage Year");
		usageYear.getChildren().clear();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=2000;i<currentYear+10;i++){
			usageYear.appendChild(new Listitem(String.valueOf(i), String.valueOf(i)));
			if(i==currentYear){
				((Listitem)usageYear.getLastChild()).setSelected(true);
			}
		}
	}
	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}
		//retrieve format
		String report = super.getReport();
		ERSClient client = super.getClient();
		String repository = super.getRepository();
		String reportCategory = super.getReportCategory();
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();
		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
			if(format.equals(Constants.FORMAT_EXCEL)){
				Filedownload.save(media);
			}else if(format.equals(Constants.FORMAT_PDF)){
				HashMap params = new HashMap();
				params.put("report", media);
				this.forward(Uri.REPORT_RESULT, params);
			}
			os.close();
			client.close();
		}
	}

	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException{
		String acctTypeNo = (String)reportParamsProperties.get("1. Account Type");
		String joinedStart = (String)reportParamsProperties.get("2. Joined Start Date");
		String joinedEnd = (String)reportParamsProperties.get("3. Joined End Date");
		String usageYear = (String)reportParamsProperties.get("4. Usage Year");
		String entityNo = (String)reportParamsProperties.get("5. Entity");
		String acctStatus = (String)reportParamsProperties.get("6. Account Status");
		String industryCode = (String)reportParamsProperties.get("7. Business Nature");
		String salespersonNo = (String)reportParamsProperties.get("8. Sales Person");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"New Accounts Revenue"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		// criteria
		if(acctTypeNo!=null && acctTypeNo.length()>0){
			String acctTypeName = ((Listbox)this.getFellow("1. Account Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((joinedStart!=null && joinedStart.length()>0) || (joinedEnd!=null && joinedEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Joined Date: ");
			if(joinedStart!=null && joinedStart.length()>0){
				buffer.append(joinedStart);
			}else{
				buffer.append(joinedEnd);
			}
			buffer.append(" to ");
			if(joinedEnd!=null && joinedEnd.length()!=0){
				buffer.append(joinedEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(joinedStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(usageYear!=null && usageYear.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Usage Year: "+usageYear+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("5. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctStatus!=null && acctStatus.length()>0){
			String acctStatusName = ((Listbox)this.getFellow("6. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+acctStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(industryCode!=null && industryCode.length()>0){
			String industryName = ((Listbox)this.getFellow("7. Business Nature")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Business Nature: "+industryName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salespersonName = ((Listbox)this.getFellow("8. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salespersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		// header
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Date Joined"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"January Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"February Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"March Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"April Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"May Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"June Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"July Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"August Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"September Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"October Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"November Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"December Usage Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getNewAccountsRevenue(
				acctTypeNo,
				joinedStart!=null && joinedStart.length()!=0 ? joinedStart + " 00:00:00" : joinedEnd!=null && joinedEnd.length()!=0 ? joinedEnd + " 00:00:00" : joinedStart,
				joinedEnd!=null && joinedEnd.length()!=0 ? joinedEnd + " 23:59:59" : joinedStart!=null && joinedStart.length()!=0 ? joinedStart + " 23:59:59" : joinedEnd,
				usageYear, entityNo, acctStatus, industryCode, salespersonNo
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			Map<String, Double> salespersonTotal = new HashMap<String, Double>();
			String prevSalespersonNo = null;
			String prevSalespersonName = null;
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			int sn = 1;
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				if(prevSalespersonNo!=null && !prevSalespersonNo.equals(result[3])){// checking change of sales person
					buffer.append("\n");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+"TOTAL FOR "+prevSalespersonName+"=====>>"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jan"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("feb"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("mar"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("apr"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("may"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jun"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jul"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("aug"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("sep"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("oct"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("nov"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("dec"))+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("total"))+Constants.TEXT_QUALIFIER);
					buffer.append("\n");
					buffer.append("\n");
					salespersonTotal.clear();
					sn = 1;
				}
				salespersonTotal.put("jan", salespersonTotal.get("jan")!=null ? salespersonTotal.get("jan") + Double.parseDouble(String.valueOf(result[6]).replace(",", "")) : Double.parseDouble(String.valueOf(result[6]).replace(",", "")));
				salespersonTotal.put("feb", salespersonTotal.get("feb")!=null ? salespersonTotal.get("feb") + Double.parseDouble(String.valueOf(result[7]).replace(",", "")) : Double.parseDouble(String.valueOf(result[7]).replace(",", "")));
				salespersonTotal.put("mar", salespersonTotal.get("mar")!=null ? salespersonTotal.get("mar") + Double.parseDouble(String.valueOf(result[8]).replace(",", "")) : Double.parseDouble(String.valueOf(result[8]).replace(",", "")));
				salespersonTotal.put("apr", salespersonTotal.get("apr")!=null ? salespersonTotal.get("apr") + Double.parseDouble(String.valueOf(result[9]).replace(",", "")) : Double.parseDouble(String.valueOf(result[9]).replace(",", "")));
				salespersonTotal.put("may", salespersonTotal.get("may")!=null ? salespersonTotal.get("may") + Double.parseDouble(String.valueOf(result[10]).replace(",", "")) : Double.parseDouble(String.valueOf(result[10]).replace(",", "")));
				salespersonTotal.put("jun", salespersonTotal.get("jun")!=null ? salespersonTotal.get("jun") + Double.parseDouble(String.valueOf(result[11]).replace(",", "")) : Double.parseDouble(String.valueOf(result[11]).replace(",", "")));
				salespersonTotal.put("jul", salespersonTotal.get("jul")!=null ? salespersonTotal.get("jul") + Double.parseDouble(String.valueOf(result[12]).replace(",", "")) : Double.parseDouble(String.valueOf(result[12]).replace(",", "")));
				salespersonTotal.put("aug", salespersonTotal.get("aug")!=null ? salespersonTotal.get("aug") + Double.parseDouble(String.valueOf(result[13]).replace(",", "")) : Double.parseDouble(String.valueOf(result[13]).replace(",", "")));
				salespersonTotal.put("sep", salespersonTotal.get("sep")!=null ? salespersonTotal.get("sep") + Double.parseDouble(String.valueOf(result[14]).replace(",", "")) : Double.parseDouble(String.valueOf(result[14]).replace(",", "")));
				salespersonTotal.put("oct", salespersonTotal.get("oct")!=null ? salespersonTotal.get("oct") + Double.parseDouble(String.valueOf(result[15]).replace(",", "")) : Double.parseDouble(String.valueOf(result[15]).replace(",", "")));
				salespersonTotal.put("nov", salespersonTotal.get("nov")!=null ? salespersonTotal.get("nov") + Double.parseDouble(String.valueOf(result[16]).replace(",", "")) : Double.parseDouble(String.valueOf(result[16]).replace(",", "")));
				salespersonTotal.put("dec", salespersonTotal.get("dec")!=null ? salespersonTotal.get("dec") + Double.parseDouble(String.valueOf(result[17]).replace(",", "")) : Double.parseDouble(String.valueOf(result[17]).replace(",", "")));
				salespersonTotal.put("total", salespersonTotal.get("total")!=null ? salespersonTotal.get("total") + Double.parseDouble(String.valueOf(result[18]).replace(",", "")) : Double.parseDouble(String.valueOf(result[18]).replace(",", "")));
				prevSalespersonNo = String.valueOf(result[3]);
				prevSalespersonName = String.valueOf(result[4]);
				buffer.append(""+Constants.TEXT_QUALIFIER+(sn++)+Constants.TEXT_QUALIFIER+",");
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j!=3){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
			buffer.append("\n");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+"TOTAL FOR "+prevSalespersonName+"=====>>"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jan"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("feb"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("mar"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("apr"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("may"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jun"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("jul"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("aug"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("sep"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("oct"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("nov"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("dec"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(salespersonTotal.get("total"))+Constants.TEXT_QUALIFIER);
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}