package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class GiroRejectWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GiroRejectWindow.class);
	private String report = "GIRO Reject";
	private Listbox entityLB, sortByLB, reportFormat,
		accountTypeLB, salesPersonLB, rejectedReasonLB, rejectedByLB;
	private Datebox valueDateFromDB, valueDateToDB;
	private Long reportRsrcId;
	
	private Map<Integer, String> accountTypes;
	private Map<Integer, String> salesPersons;
	private Map<String, String> rejectionCodeMasters;
	
	public GiroRejectWindow(){
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		//populate entity list
		entityLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		for(Integer entityNo : entities.keySet()){
			entityLB.appendChild(new Listitem(entities.get(entityNo),entityNo.toString()));
		}
		entityLB.setSelectedIndex(0);
		
		//populate sortByLB
		sortByLB.appendChild(new Listitem("Value Date, Entity", "1"));
		sortByLB.setSelectedIndex(0);
		
		//account types
		accountTypeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		accountTypes = this.businessHelper.getReportBusiness().getAllAccountTypes();
		for(Integer acctTypeNo : accountTypes.keySet()){
			accountTypeLB.appendChild(new Listitem(accountTypes.get(acctTypeNo), String.valueOf(acctTypeNo)));
		}
		accountTypeLB.setSelectedIndex(0);
		
		//Sales Person
		salesPersonLB.appendChild(ComponentUtil.createNotRequiredListItem());
		salesPersons = MasterSetup.getSalespersonManager().getAllMasters();
		for(Integer salesPersonId : salesPersons.keySet()){
			salesPersonLB.appendChild(new Listitem(salesPersons.get(salesPersonId), salesPersonId.toString()));
		}
		salesPersonLB.setSelectedIndex(0);
		
		//Rejected Reason
		rejectedReasonLB.appendChild(ComponentUtil.createNotRequiredListItem());
		rejectionCodeMasters = ConfigurableConstants.getAllMasters(ConfigurableConstants.GIRO_UOB_REJECTION_CODE);
		for(Entry<String, String> entry : rejectionCodeMasters.entrySet()){
			rejectedReasonLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		rejectedReasonLB.setSelectedIndex(0);
		
		//Rejected By
		rejectedByLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Map<String, String> rejectionBys = NonConfigurableConstants.GIRO_REJECTED_BY;
		for(Entry<String, String> entry : rejectionBys.entrySet()){
			rejectedByLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		rejectedByLB.setSelectedIndex(0);
	}
	
	public void populateReportFormatList(Listbox listbox) throws NetException{
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness().getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for(MstbReportFormatMap formatMap : reportFormatMapList){
			Listitem listItem = new Listitem(formatMap.getReportFormat(), Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if(firstItem){
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}
	
	public void generate() throws HttpException, IOException, InterruptedException, NetException, WrongValueException {
		logger.info("");
		this.displayProcessing();
		
		String valueDateFrom, valueDateTo;
		String accountTypeNo, salesPersonNo;
		String rejectedReasonCode, rejectedBy;
		String entityNo;
		String sortBy;
		
		if(valueDateFromDB.getValue() != null && valueDateToDB.getValue() == null)
			valueDateToDB.setValue(valueDateFromDB.getValue());
		else if(valueDateToDB.getValue() != null && valueDateFromDB.getValue() == null)
			valueDateFromDB.setValue(valueDateToDB.getValue());
		
		if(valueDateFromDB.getValue() != null && valueDateToDB.getValue() != null)
			if(valueDateFromDB.getValue().compareTo(valueDateToDB.getValue()) > 0){
				Messagebox.show("Value Date From must be later than Value Date To", "Report", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		if(valueDateFromDB.getValue() == null || valueDateToDB.getValue() == null){
			Messagebox.show("Please enter the value dates", 
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		valueDateFrom = DateUtil.convertDateToStr(valueDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		valueDateTo = DateUtil.convertDateToStr(valueDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		entityNo = entityLB.getSelectedItem().getValue() == null ? "" : (String) entityLB.getSelectedItem().getValue();
		accountTypeNo = accountTypeLB.getSelectedItem().getValue() == null ? "" : (String) accountTypeLB.getSelectedItem().getValue();
		salesPersonNo = salesPersonLB.getSelectedItem().getValue() == null ? "" : (String) salesPersonLB.getSelectedItem().getValue();
		rejectedReasonCode = rejectedReasonLB.getSelectedItem().getValue() == null ? "" : (String) rejectedReasonLB.getSelectedItem().getValue();
		rejectedBy = rejectedByLB.getSelectedItem().getValue() == null ? "" : (String) rejectedByLB.getSelectedItem().getValue();
		sortBy = sortByLB.getSelectedItem().getValue().toString();
		
		//retrieve format
		if(reportFormat.getSelectedItem()==null) throw new WrongValueException(reportFormat, "* Mandatory field");
		String extension = (String)reportFormat.getSelectedItem().getLabel();
		String format = (String)reportFormat.getSelectedItem().getValue();

		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(valueDateFrom, valueDateTo, 
					accountTypeNo, salesPersonNo, rejectedReasonCode, rejectedBy, 
					entityNo, sortBy, getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String valueDateFrom, String valueDateTo, String accountTypeNo,
			String salesPersonNo, String rejectedReasonCode, String rejectedBy, String entityNo,
			String sortBy, String printedBy){
		
		StringBuffer sb = new StringBuffer();
		
		//Report Header
		sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"GIRO Reject Report"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		if(valueDateFrom!=null && valueDateFrom.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Value Date From: "+valueDateFrom+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(valueDateTo!=null && valueDateTo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Value Date To: "+valueDateTo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(accountTypeNo!=null && accountTypeNo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account Type: "+accountTypes.get(new Integer(accountTypeNo))+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(salesPersonNo!=null && salesPersonNo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesPersons.get(new Integer(salesPersonNo))+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(rejectedReasonCode!=null && rejectedReasonCode.length()>0){
			rejectedReasonCode = new Integer(rejectedReasonCode).toString(); //This will remove the zero in front for single digit
			String reason = rejectionCodeMasters.get(rejectedReasonCode);
			reason = reason == null ? "" : reason;
			sb.append(Constants.TEXT_QUALIFIER+"Rejected Reason: "+reason+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(rejectedBy!=null && rejectedBy.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Rejected By: "+rejectedBy+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			FmtbEntityMaster entityMaster = (FmtbEntityMaster)MasterSetup.getEntityManager().getMaster(new Integer(entityNo));
			sb.append(Constants.TEXT_QUALIFIER+"Entity: "+entityMaster.getEntityName()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		//Line Break
		sb.append("\n");
		
		sb.append(Constants.TEXT_QUALIFIER+"Printed By: "+printedBy+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		//Column Title
		sb.append(Constants.TEXT_QUALIFIER+"ACCOUNT NO"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"ACCOUNT NAME"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"ACCOUNT TYPE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"ENTITY"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"RETURN FILE NAME"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"BANK CODE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"BRANCH CODE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"BANK ACCOUNT NO"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"TRANSACTION CODE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"AMOUNT"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"PARTICULARS"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REFERENCE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"CLEAR FATE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTION CODE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"INVOICE NO."+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"INVOICE DATE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTED AMOUNT"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"OUTSTANDING AMOUNT"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"SALES PERSON"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTED REASON"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTED BY"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getGiroReject(valueDateFrom, valueDateTo, accountTypeNo, salesPersonNo, rejectedReasonCode, rejectedBy, entityNo);
		for(Object[] columnDataObject : rowsOfData){
			for(Object data : columnDataObject){
				if(data!=null){
					sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
				}
				else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			sb.append("\n");
		}
		
		if(rowsOfData.size()==0){
			sb.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		return sb;
	}
	
	public void reset(){
		entityLB.setSelectedIndex(0);
		valueDateFromDB.setText("");
		valueDateToDB.setText("");
		accountTypeLB.setSelectedIndex(0);
		salesPersonLB.setSelectedIndex(0);
		rejectedReasonLB.setSelectedIndex(0);
		rejectedByLB.setSelectedIndex(0);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}