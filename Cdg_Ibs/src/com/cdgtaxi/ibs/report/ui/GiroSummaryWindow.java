package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class GiroSummaryWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GiroSummaryWindow.class);
	private String report = "GIRO Summary";
	private Listbox entityLB, sortByLB, reportFormat;
	private Datebox valueDateFromDB, valueDateToDB, 
		generationDateFromDB, generationDateToDB, 
		uploadDateFromDB, uploadDateToDB;
	private Long reportRsrcId;
	
	public GiroSummaryWindow(){
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
		String generationDateFrom, generationDateTo;
		String uploadDateFrom, uploadDateTo;
		String entityNo;
		String sortBy;
		
		if(valueDateFromDB.getValue() != null && valueDateToDB.getValue() == null)
			valueDateToDB.setValue(valueDateFromDB.getValue());
		else if(valueDateToDB.getValue() != null && valueDateFromDB.getValue() == null)
			valueDateFromDB.setValue(valueDateToDB.getValue());
		if(generationDateFromDB.getValue() != null && generationDateToDB.getValue() == null)
			generationDateToDB.setValue(generationDateFromDB.getValue());
		else if(generationDateToDB.getValue() != null && generationDateFromDB.getValue() == null)
			generationDateFromDB.setValue(generationDateToDB.getValue());
		if(uploadDateFromDB.getValue() != null && uploadDateToDB.getValue() == null)
			uploadDateToDB.setValue(uploadDateFromDB.getValue());
		else if(uploadDateToDB.getValue() != null && uploadDateFromDB.getValue() == null)
			uploadDateFromDB.setValue(uploadDateToDB.getValue());
		
		if(valueDateFromDB.getValue() != null && valueDateToDB.getValue() != null)
			if(valueDateFromDB.getValue().compareTo(valueDateToDB.getValue()) > 0){
				Messagebox.show("Value Date From must be later than Value Date To", "Report", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		if(generationDateFromDB.getValue() != null && generationDateToDB.getValue() != null)
			if(generationDateFromDB.getValue().compareTo(generationDateToDB.getValue()) > 0){
				Messagebox.show("Generation Date From must be later than Generation Date To", "Report", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		if(uploadDateFromDB.getValue() != null && uploadDateToDB.getValue() != null)
			if(uploadDateFromDB.getValue().compareTo(uploadDateToDB.getValue()) > 0){
				Messagebox.show("Upload Date From must be later than Upload Date To", "Report", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		if(valueDateFromDB.getValue() == null && valueDateToDB.getValue() == null &&
				generationDateFromDB.getValue() == null && generationDateToDB.getValue() == null &&
				uploadDateFromDB.getValue() == null && uploadDateToDB.getValue() == null){
			Messagebox.show("Please enter one of the selection criteria", 
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		valueDateFrom = DateUtil.convertDateToStr(valueDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		valueDateTo = DateUtil.convertDateToStr(valueDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		generationDateFrom = DateUtil.convertDateToStr(generationDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		generationDateTo = DateUtil.convertDateToStr(generationDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		uploadDateFrom = DateUtil.convertDateToStr(uploadDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		uploadDateTo = DateUtil.convertDateToStr(uploadDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		entityNo = entityLB.getSelectedItem().getValue() == null ? "" : (String) entityLB.getSelectedItem().getValue();
		sortBy = sortByLB.getSelectedItem().getValue().toString();
		
		//retrieve format
		if(reportFormat.getSelectedItem()==null) throw new WrongValueException(reportFormat, "* Mandatory field");
		String extension = (String)reportFormat.getSelectedItem().getLabel();
		String format = (String)reportFormat.getSelectedItem().getValue();

		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(valueDateFrom, valueDateTo, 
					generationDateFrom, generationDateTo, uploadDateFrom, uploadDateTo, 
					entityNo, sortBy, getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String valueDateFrom, String valueDateTo, String generationDateFrom,
			String generationDateTo, String uploadDateFrom, String uploadDateTo, String entityNo,
			String sortBy, String printedBy){
		
		StringBuffer sb = new StringBuffer();
		
		//Report Header
		sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"GIRO Summary Report"+Constants.TEXT_QUALIFIER+",");
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
		if(generationDateFrom!=null && generationDateFrom.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Generation Date From: "+generationDateFrom+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(generationDateTo!=null && generationDateTo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Generation Date To: "+generationDateTo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(uploadDateFrom!=null && uploadDateFrom.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Upload Date From: "+uploadDateFrom+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(uploadDateTo!=null && uploadDateTo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Upload Date To: "+uploadDateTo+Constants.TEXT_QUALIFIER+",");
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
		sb.append(Constants.TEXT_QUALIFIER+"VALUE DATE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"GENERATION DATE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"UPLOAD DATE"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"ENTITY"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"GIRO FILENAME"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"RETURN FILENAME"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"OUTGOING COUNT"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"OUTGOING AMOUNT"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"INCOMING COUNT (A) = (B) + (C)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"INCOMING AMOUNT (a) = (b) + (c)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"SUCCESS COUNT (B)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"SUCCESS AMOUNT (b)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTED COUNT (C)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"REJECTED AMOUNT (c)"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getGiroSummary(valueDateFrom, valueDateTo, generationDateFrom, generationDateTo, uploadDateFrom, uploadDateTo, entityNo);
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
		generationDateFromDB.setText("");
		generationDateToDB.setText("");
		uploadDateFromDB.setText("");
		uploadDateToDB.setText("");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}