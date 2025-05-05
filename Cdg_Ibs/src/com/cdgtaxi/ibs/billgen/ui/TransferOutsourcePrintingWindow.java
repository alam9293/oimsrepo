package com.cdgtaxi.ibs.billgen.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
import com.google.common.base.Splitter;

public class TransferOutsourcePrintingWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TransferOutsourcePrintingWindow.class);
	
	public void afterCompose() {
			
		//populate entity list
		Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
		Listitem listItem2 =  new Listitem("-", null);
		listItem2.setSelected(true);
		entityListBox.appendChild(listItem2);
		Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
		for(Integer em : ems.keySet()){
			entityListBox.appendChild(new Listitem(ems.get(em), em));
		}
		if(entityListBox.getChildren().size()>0) entityListBox.setSelectedIndex(0);
	} 
	
	public void submitByReqNo() throws InterruptedException{
		logger.info("submitByReqNo");
		try {
			String reqNoStrTrim = "";	
			Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
			Map printingProperties = (Map)SpringUtil.getBean("printingProperties");

			List<String> requestIds = new ArrayList<String>();
			Listbox reqNos = (Listbox)this.getFellow("resultList");
			for(Object reqNo : reqNos.getItems()){
				Checkbox checkbox = (Checkbox)((Listitem)reqNo).getLastChild().getFirstChild();
				if(checkbox.isChecked()){
					requestIds.add(((BmtbBillGenReq)((Listitem)reqNo).getValue()).getReqNo().toString());
				}
			}
			if(requestIds.isEmpty()){
				Messagebox.show("No selected request!", "Reject Request", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			Iterator<String> iter = requestIds.iterator();
			int i = 0;
			while (iter.hasNext()) {
				if(i==0)
					reqNoStrTrim = iter.next();
				else
					reqNoStrTrim = reqNoStrTrim + "."+iter.next();
				i++;
			}

			if(!reqNoStrTrim.trim().equals("")) {
				displayProcessing();
				
				String csvZipEncryptFtpBatFilePath = (String)pdfGenProperties.get("pdfgen.scripts.folder") + "CsvZipEncryptFtpGen.bat";
				String csvzipcommand = "cmd /c START " + checkFilePathForSpace(csvZipEncryptFtpBatFilePath) + " \'" + reqNoStrTrim +"+Y\'";
//				for testing
//				String csvzipcommand = "cmd /c START " + csvZipEncryptFtpBatFilePath + " \'3900+Y\'";
//				System.out.println("test > '"+reqNoStrTrim+"'");
				System.out.println("csvzipcommand > "+csvzipcommand);
				Process pe = Runtime.getRuntime().exec(csvzipcommand);
				pe.waitFor();

				Messagebox.show("File is processing and will transfer to print vendor!", "Transfer Outsource Printing", Messagebox.OK, Messagebox.INFORMATION);
				Executions.getCurrent().sendRedirect("");
			}
		}
		catch (WrongValueException wve)
		{
			Messagebox.show("Please enter the mandatory fields", "Transfer Outsource Printing", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
						"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
	}
	public void searchRequest() throws InterruptedException{
		logger.info("");
		
		try{
			Intbox requestNoIntBox = (Intbox)this.getFellow("requestNoIntBox");
			Datebox requestDateFromBox = (Datebox)this.getFellow("requestDateFromBox");
			Datebox requestDateToBox = (Datebox)this.getFellow("requestDateToBox");
			Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
			
			Integer requestNo = requestNoIntBox.getValue();
			String status = "RC";
			Date requestDateFrom = requestDateFromBox.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(requestDateFromBox.getValue());
			Date requestDateTo = requestDateToBox.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(requestDateToBox.getValue());
			Integer entityNo = (Integer)entityListBox.getSelectedItem().getValue();
			
			if(requestDateFrom!=null && requestDateTo==null)
				requestDateTo = requestDateFrom;
			
			if(requestDateFrom!=null && requestDateTo!=null){
				if(requestDateTo.compareTo(requestDateFrom)<0){
					Messagebox.show("Invoice Date To cannot be earlier than Invoice Date From", 
						"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
				
			this.displayProcessing();
			
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
			
			List<BmtbBillGenReq> requests = this.businessHelper.getBillGenBusiness().searchRequest(requestNo, status, null, requestDateFrom, requestDateTo, entityNo,"backward");
			if(requests.size()>0){
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(BmtbBillGenReq request : requests){
					Listitem item = new Listitem();
					item.setValue(request);
					item.appendChild(newListcell(request.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.get(request.getStatus())));
					
					String billGenSetup = NonConfigurableConstants.BILL_GEN_SETUP.get(request.getBmtbBillGenSetupBySetupNo().getSetupNo());
					if(request.getBmtbBillGenSetupBySetupNo().getSetupNo().equals(NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC)) {
						if(request.getAmtbAccount() != null)
							billGenSetup += " ("+request.getAmtbAccount().getCustNo() +")";
					}
					item.appendChild(newListcell(billGenSetup));
					Listcell regenRequestNoListCell = new Listcell();
					regenRequestNoListCell.setLabel("-");
					regenRequestNoListCell.setValue(new Integer(0));
					if(request.getBmtbBillGenReq()!=null){
						regenRequestNoListCell.setLabel(request.getBmtbBillGenReq().getReqNo().toString());
						regenRequestNoListCell.setValue(request.getBmtbBillGenReq().getReqNo());
					}
					item.appendChild(regenRequestNoListCell);
					item.appendChild(newListcell(request.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(request.getBillGenTime()==null?"-":request.getBillGenTime()));
					
					
					item.appendChild(newListcell(checkEntity(request)));
						
					//CheckBox
					Listcell lastCell=new Listcell();
					lastCell.appendChild(new Checkbox());
					item.appendChild(lastCell);
					
					resultListBox.appendChild(item);
				}
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(7));
				}
			}
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void clear(){
		((Intbox)this.getFellow("requestNoIntBox")).setText("");
		((Datebox)this.getFellow("requestDateFromBox")).setText("");
		((Datebox)this.getFellow("requestDateToBox")).setText("");
	}
	@Override
	public void refresh() {
		
	}
	public String checkFilePathForSpace(String path) {
		
		String trimSpace = "";
		Iterable<String> result0 = Splitter.on("/").split(path);
		
		int i = 0;
		for(String s0: result0) {
			
			if(i != 0)
				trimSpace = trimSpace + "/";
			if(s0.contains(" "))
				trimSpace = trimSpace+"\""+s0+"\"";
			else
				trimSpace = trimSpace+s0;
			
			i++;
		}
		
		return trimSpace;
	}
	
	public String checkEntity(BmtbBillGenReq request) {
		String entity = "-";
		
		if(request.getFmtbEntityMaster() != null) {
			entity = request.getFmtbEntityMaster().getEntityName();
		}
		else if(request.getAmtbAccount() != null) {
			if(request.getAmtbAccount().getFmtbArContCodeMaster() != null)
				entity = request.getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
		}
		else if(request.getBmtbBillGenReq() != null) {
			
			if(request.getBmtbBillGenReq().getFmtbEntityMaster() != null) {
				entity = request.getBmtbBillGenReq().getFmtbEntityMaster().getEntityName();
			}
			else if(request.getBmtbBillGenReq().getAmtbAccount() != null) {
				if(request.getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster() != null)
					entity = request.getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
			}
		} 
		else if(request.getBmtbBillGenReq().getBmtbBillGenReq() != null) {
			if(request.getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster() != null) {
				entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster().getEntityName();
			}
			else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount() != null) {
				if(request.getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster() != null)
					entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
			}
		}
		else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq() != null) {
			if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster() != null) {
				entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster().getEntityName();
			}
			else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount() != null) {
				if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster() != null)
					entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
			}
		}
		else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq() != null) {
			if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster() != null) {
				entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster().getEntityName();
			}
			else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount() != null) {
				if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster() != null)
					entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
			}
		}
		else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq() != null) {
			if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster() != null) {
				entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getFmtbEntityMaster().getEntityName();
			}
			else if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount() != null) {
				if(request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster() != null)
						entity = request.getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getBmtbBillGenReq().getAmtbAccount().getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName();
			}
		}
		
		
		return entity;
	}
}
