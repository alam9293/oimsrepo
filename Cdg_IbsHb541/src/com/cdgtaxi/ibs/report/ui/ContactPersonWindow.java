package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ContactPersonWindow extends ReportWindow implements CSVFormat{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditBalanceWindow.class);
	public ContactPersonWindow() throws IOException {
		super("Contact Person", "Account");
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
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {		
		String accountNo = (String)reportParamsProperties.get("01a. Account No");
		String accountName = (String)reportParamsProperties.get("01b. Account Name");
		String division = (String)reportParamsProperties.get("01c. Division");
		String department = (String)reportParamsProperties.get("01d. Department");
		String accountStatus = (String)reportParamsProperties.get("02. Account Status");
		String contactPersonName = (String)reportParamsProperties.get("03. Contact Person Name");
		String typeOfContact = (String)reportParamsProperties.get("04. Type of Contact");
		String businessNature = (String)reportParamsProperties.get("05. Business Nature");
		String productType = (String)reportParamsProperties.get("06. Product Type");
		String salesPerson = (String)reportParamsProperties.get("07. Sales Person");
		String reportPurpose = (String)reportParamsProperties.get("08. Report Purpose");
		String sortBy = (String)reportParamsProperties.get("09. Sort By");
		String entityNo = (String)reportParamsProperties.get("10. Entity");
		
		StringBuffer buffer = new StringBuffer();
		
		Textbox contactPersonNameA = (Textbox)this.getFellow("03. Contact Person Name");
		if(contactPersonName.length()>0){
			if(contactPersonName.length()<=2)
			throw new WrongValueException(contactPersonNameA, "3 characters minimum required");
		}

		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Contact Person Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(accountNo!=null && accountNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+accountNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(accountName!=null && accountName.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(division!=null && division.length()>0){
			String divisionName = ((Listbox)this.getFellow("01c. Division")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Division: "+divisionName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(department!=null && department.length()>0){
			String departmentName = ((Listbox)this.getFellow("01d. Department")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Department: "+departmentName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(accountStatus!=null && accountStatus.length()>0){
			String accountStatusName = ((Listbox)this.getFellow("02. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+accountStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(contactPersonName!=null && contactPersonName.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Contact Person Name: "+contactPersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(typeOfContact!=null && typeOfContact.length()>0){
			String typeOfContactName = ((Listbox)this.getFellow("04. Type of Contact")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Type of Contact: "+typeOfContactName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(businessNature!=null && businessNature.length()>0){
			String businessNatureName = ((Listbox)this.getFellow("05. Business Nature")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Business Nature: "+businessNatureName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productType!=null && productType.length()>0){
			String productTypeName = ((Listbox)this.getFellow("06. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salesPerson!=null && salesPerson.length()>0){
			String salesPersonName = ((Listbox)this.getFellow("07. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesPersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(reportPurpose!=null && reportPurpose.length()>0){
			String reportPurposeName = ((Listbox)this.getFellow("08. Report Purpose")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Report Purpose: "+reportPurposeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}

		String sort = ((Listbox)this.getFellow("09. Sort By")).getSelectedItem().getLabel();
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Format: "+Constants.EXTENSION_CSV+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sort+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		if(entityNo!=null && entityNo.length()>0) {
					String entityName = ((Listbox)this.getFellow("10. Entity")).getSelectedItem().getLabel();
					buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
					buffer.append("\n");
				}
		
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Business Nature"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Product Type"+Constants.TEXT_QUALIFIER+",");
		if(!reportPurpose.equalsIgnoreCase(NonConfigurableConstants.REPORT_PURPOSE_MAIL_MERGE)){
			buffer.append(Constants.TEXT_QUALIFIER+"Div Code"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Div Name"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Div Status"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Dept Code"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Dept Name"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Dept Status"+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append(Constants.TEXT_QUALIFIER+"Contact Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Type Of Contact"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Tel(O)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Tel(M)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Fax"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Email"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Area"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Block No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Street Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Unit No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Building Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Country"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"State"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"City"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Postal"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getContactPerson(
				accountNo,accountName,division,department,accountStatus,
				contactPersonName!=null? "%"+contactPersonName+"%" : contactPersonName,typeOfContact,businessNature,
						productType!=null? "%"+productType+"%" : productType,
				salesPerson,sortBy, entityNo
		);
		logger.info("size = " + results.size());
		
		if(results.size()!=0){
			List<Integer> enableMailMerge = new ArrayList<Integer>();
			if(reportPurpose.equalsIgnoreCase(NonConfigurableConstants.REPORT_PURPOSE_MAIL_MERGE)){
				enableMailMerge.add(5);
				enableMailMerge.add(6);
				enableMailMerge.add(7);
				enableMailMerge.add(8);
				enableMailMerge.add(9);
				enableMailMerge.add(10);
			}
			List<String> duplicateContactPersonMailMerge = new ArrayList<String>();
			int counter=1;
			for(int i=0;i<results.size(); i++){
				
				Object[] result = results.get(i);
				if(reportPurpose.equalsIgnoreCase(NonConfigurableConstants.REPORT_PURPOSE_MAIL_MERGE)){
					String contactPerson = (String)result[11];
					if(duplicateContactPersonMailMerge.contains(contactPerson)){
						continue;
					}else{
						duplicateContactPersonMailMerge.add(String.valueOf(contactPerson));
					}
				}
				buffer.append(""+Constants.TEXT_QUALIFIER+counter+Constants.TEXT_QUALIFIER+",");
				counter++;
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					
					if(!enableMailMerge.contains(j)){
						if(data!=null){
							if(j==2 || j==7 || j==10)data = NonConfigurableConstants.ACCOUNT_STATUS.get(data);
							if(j==12)data = NonConfigurableConstants.MAIN_CONTACT.get(data);
							if(j==5 || j==8 || j==25){
								buffer.append("'"+data+",");
							}else{
								buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							}
						}else{
							buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
						}
					}
				}
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}