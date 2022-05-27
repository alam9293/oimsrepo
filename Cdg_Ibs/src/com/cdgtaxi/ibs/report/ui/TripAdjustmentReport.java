package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class TripAdjustmentReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TripAdjustmentReport.class);
	public TripAdjustmentReport() throws IOException {
		super("Trip Adjustment Report", "Trips");
	}
	public void init(){
		// removing all
		Listbox paymentTypes = (Listbox)this.getFellow("02. Payment Type");
		for(Object paymentType : paymentTypes.getChildren()){
			if(((Listitem)paymentType).getValue().equals("0")){
				paymentTypes.removeChild((Listitem)paymentType);
				break;
			}
		}
		paymentTypes.setSelectedIndex(0);
		// removing new status
		Listbox approvalStatuses = (Listbox)this.getFellow("09. Approval Status");
		for(Object approvalStatus : approvalStatuses.getChildren()){
			if(((Listitem)approvalStatus).getValue().equals("N")){
				approvalStatuses.removeChild((Listitem)approvalStatus);
				break;
			}
		}
		for(Object approvalStatus : approvalStatuses.getChildren()){
			if(((Listitem)approvalStatus).getValue().equals("V")){
				((Listitem)approvalStatus).setLabel("PENDING APPR (ACTIVE)");
			}
			if(((Listitem)approvalStatus).getValue().equals("F")){
				((Listitem)approvalStatus).setLabel("PENDING APPR (BILLED)");
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
			throw new FormatNotSupportedException();
		}
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {
		String acctNo = (String)reportParamsProperties.get("01a. Account No");
		String acctName = (String)reportParamsProperties.get("01b. Account Name");
		String paymentType = (String)reportParamsProperties.get("02. Payment Type");
		String createStart = (String)reportParamsProperties.get("03. Creation Start Date");
		String createEnd = (String)reportParamsProperties.get("04. Creation End Date");
		String approveStart = (String)reportParamsProperties.get("05. Approval Start Date");
		String approveEnd = (String)reportParamsProperties.get("06. Approval End Date");
		String entityNo = (String)reportParamsProperties.get("07. Entity");
		String providerNo = (String)reportParamsProperties.get("08. Company Code");
		String approvalStatus = (String)reportParamsProperties.get("09. Approval Status");
		String txnStatus = (String)reportParamsProperties.get("10. Transaction Status");
		String action = (String)reportParamsProperties.get("11. Action to Transaction");
		String fmsUpdate = (String)reportParamsProperties.get("12. FMS Update");
		String sort = (String)reportParamsProperties.get("13. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Adjustment Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(acctNo !=null && acctNo.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+acctNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctName !=null && acctName.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+acctName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(paymentType!=null && paymentType.length()>0){
			String paymentTypeName = ((Listbox)this.getFellow("02. Payment Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Type: "+paymentTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((createStart!=null && createStart.length()>0) || (createEnd!=null && createEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Creation Date: ");
			if(createStart!=null && createStart.length()>0){
				buffer.append(createStart);
			}else{
				buffer.append(createEnd);
			}
			buffer.append(" to ");
			if(createEnd!=null && createEnd.length()!=0){
				buffer.append(createEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(createStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((approveStart!=null && approveStart.length()>0) || (approveEnd!=null && approveEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Approval Date: ");
			if(approveStart!=null && approveStart.length()>0){
				buffer.append(approveStart);
			}else{
				buffer.append(approveEnd);
			}
			buffer.append(" to ");
			if(approveEnd!=null && approveEnd.length()!=0){
				buffer.append(approveEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(approveStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("07. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity"+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("07. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity"+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(providerNo!=null && providerNo.length()>0){
			String providerName = ((Listbox)this.getFellow("08. Company Code")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Company Code: "+providerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(approvalStatus!=null && approvalStatus.length()>0){
			String approvalStatusName = ((Listbox)this.getFellow("09. Approval Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Approval Status: "+approvalStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(txnStatus!=null && txnStatus.length()>0){
			String txnStatusName = ((Listbox)this.getFellow("10. Transaction Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Transaction Status: "+txnStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(action!=null && action.length()>0){
			String actionName = ((Listbox)this.getFellow("11. Action to Transaction")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Action to Transaction: "+actionName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(fmsUpdate!=null && fmsUpdate.length()>0){
			String fmsUpdateName = ((Listbox)this.getFellow("12. FMS Update")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"FMS Update: "+fmsUpdateName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sortLabel = ((Listbox)this.getFellow("13. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortLabel+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Entity"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Creation Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Job No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Driver NRIC"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi No."+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Company Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Date / Time"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Product Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi Fare"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin Fee"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Levy"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Incentive Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Promo Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CabRewards Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Transaction Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Action to Transaction"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjustment on Driver Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjustment on Levy"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjustment on Incentive Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjustment on Promo Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjustment on CabRewards Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Approval Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Approve Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Remarks"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FMS Update"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FMS Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FMS Error Message"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getTripAdjustmentReport(
				acctNo!=null&&acctNo.length()!=0 ? acctNo : "%",
				acctName!=null&&acctName.length()!=0 ? acctName : "%",
				paymentType,
				createStart,
				createEnd,
				approveStart,
				approveEnd,
				entityNo,
				providerNo,
				approvalStatus,
				txnStatus,
				action,
				fmsUpdate,
				sort);
//		List<Object[]> results = this.businessHelper.getReportBusiness().getBankChargebackReport(
//				chargebackStart!=null && chargebackStart.length()!=0 ? chargebackStart + " 00:00:00" : chargebackEnd!=null && chargebackEnd.length()!=0 ? chargebackEnd + " 00:00:00" : chargebackStart,
//				chargebackEnd!=null && chargebackEnd.length()!=0 ? chargebackEnd + " 23:59:59" : chargebackStart!=null && chargebackStart.length()!=0 ? chargebackStart + " 23:59:59" : chargebackEnd,
//				batchStart!=null && batchStart.length()!=0 ? batchStart + " 00:00:00" : batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 00:00:00" : batchStart,
//				batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 23:59:59" : batchStart!=null && batchStart.length()!=0 ? batchStart + " 23:59:59" : batchEnd,
//				entityNo, providerNo, acquirerNo, sort
//		);
		logger.info("size = " + results.size());
		action = null;
		double[] totals = {0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0};
		double[] grands = {0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0};
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				if(action!=null && !action.equals(result[19]!=null ? result[19].toString() : "")){
					buffer.append("\n");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"TOTAL"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[0]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[1]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[2]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[3]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[4]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[5]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[6]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[7]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[8]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[9]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[10]+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+totals[11]+Constants.TEXT_QUALIFIER+",");
					buffer.append("\n");
					buffer.append("\n");
					totals[0] = 0;totals[1] = 0;totals[2] = 0;totals[3] = 0;totals[4] = 0;totals[5] = 0; totals[6] = 0; totals[7] = 0; totals[8] = 0; totals[9] = 0; totals[10] = 0; totals[11] = 0;
				}
				if(result[11]!=null){
					totals[0] += Double.parseDouble(result[11].toString().replaceAll(",", ""));
					grands[0] += Double.parseDouble(result[11].toString().replaceAll(",", ""));
				}
				if(result[12]!=null){
					totals[1] += Double.parseDouble(result[12].toString().replaceAll(",", ""));
					grands[1] += Double.parseDouble(result[12].toString().replaceAll(",", ""));
				}
				if(result[13]!=null){
					totals[2] += Double.parseDouble(result[13].toString().replaceAll(",", ""));
					grands[2] += Double.parseDouble(result[13].toString().replaceAll(",", ""));
				}
				if(result[14]!=null){
					totals[3] += Double.parseDouble(result[14].toString().replaceAll(",", ""));
					grands[3] += Double.parseDouble(result[14].toString().replaceAll(",", ""));
				}
				if(result[15]!=null){
					totals[4] += Double.parseDouble(result[15].toString().replaceAll(",", ""));
					grands[4] += Double.parseDouble(result[15].toString().replaceAll(",", ""));
				}
				if(result[16]!=null){
					totals[5] += Double.parseDouble(result[16].toString().replaceAll(",", ""));
					grands[5] += Double.parseDouble(result[16].toString().replaceAll(",", ""));
				}
				if(result[17]!=null){
					totals[6] += Double.parseDouble(result[17].toString().replaceAll(",", ""));
					grands[6] += Double.parseDouble(result[17].toString().replaceAll(",", ""));
				}
				if(result[20]!=null){
					totals[7] += Double.parseDouble(result[20].toString().replaceAll(",", ""));
					grands[7] += Double.parseDouble(result[20].toString().replaceAll(",", ""));
				}
				if(result[21]!=null){
					totals[8] += Double.parseDouble(result[21].toString().replaceAll(",", ""));
					grands[8] += Double.parseDouble(result[21].toString().replaceAll(",", ""));
				}
				if(result[22]!=null){
					totals[9] += Double.parseDouble(result[22].toString().replaceAll(",", ""));
					grands[9] += Double.parseDouble(result[22].toString().replaceAll(",", ""));
				}
				if(result[23]!=null){
					totals[10] += Double.parseDouble(result[23].toString().replaceAll(",", ""));
					grands[10] += Double.parseDouble(result[23].toString().replaceAll(",", ""));
				}
				if(result[24]!=null){
					totals[11] += Double.parseDouble(result[24].toString().replaceAll(",", ""));
					grands[11] += Double.parseDouble(result[24].toString().replaceAll(",", ""));
				}
				action = result[19]!=null ? result[19].toString() : "";
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null && data.toString().length()!=0){
						if(j==4 || j==5){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==6) {
							buffer.append(""+Constants.TEXT_QUALIFIER+StringUtil.maskNric(data.toString())+Constants.TEXT_QUALIFIER+",");
						}else if(j==18){
							if(paymentType.equals("2") || paymentType.equals("3")){
								buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							}else{
								buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.TXN_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
							}
							
						}else if(j==19){
							buffer.append(""+Constants.TEXT_QUALIFIER+(NonConfigurableConstants.FMS_COLLECT.equals(data) ? "COLLECT" : "REFUND")+Constants.TEXT_QUALIFIER+",");
						}else if(j==25){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.TRANSACTION_REQUEST_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==28){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.BOOLEAN.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==29){
							buffer.append(""+Constants.TEXT_QUALIFIER+("P".equals(data) ? "PENDING" : "E".equals(data) ? "ERROR" : "SUCCESSFUL")+Constants.TEXT_QUALIFIER+",");
						}else{
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
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"TOTAL"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[0]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[1]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[2]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[3]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[4]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[5]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[6]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[7]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[8]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[9]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[10]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[11]+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			buffer.append("\n");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"GRAND TOTAL"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[0]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[1]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[2]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[3]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[4]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[5]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[6]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[7]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[8]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[9]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[10]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+grands[11]+Constants.TEXT_QUALIFIER+",");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}