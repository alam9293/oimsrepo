package com.cdgtaxi.ibs.prepaid.ui;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonSearchByAccountWindow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
import com.cdgtaxi.ibs.web.component.Datebox;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class PrepaidRequestApprovalSearchWindow extends CommonSearchByAccountWindow implements AfterCompose {

	private Intbox reqNoField;
	private Listbox reqTypeField;
	private Datebox requestDateFromField, requestDateToField;
	private Textbox requestorField;
	private Listbox resultList;
	
	private Checkbox checkAllBox;
	private Textbox approvalRemarksField;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		ComponentUtil.buildListbox(reqTypeField, NonConfigurableConstants.PREPAID_REQUEST_TYPE, true);
		
	}
	
	
	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		super.onCreate(ce);
		
		//by default list all pending approve request
		searchRequest();
	}
	
	public void searchRequest() throws InterruptedException{
		
	
		SearchPrepaidRequestForm form = new SearchPrepaidRequestForm();
		form.setReqNo(reqNoField.getValue());
		form.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
		if(selectedAccount!=null){
			form.setAccountNo(selectedAccount.getAccountNo());
		}
		form.setRequestDateFrom(requestDateFromField.getValue());
		form.setRequestDateTo(requestDateToField.getValue());
		form.setRequestor(requestorField.getValue());
		form.setRequestType((String) reqTypeField.getSelectedItem().getValue());
		
		this.displayProcessing();
		List<PmtbPrepaidReq> requestList = businessHelper.getPrepaidBusiness().searchPrepaidRequest(form);
		
		resultList.getItems().clear();
		
		if(requestList.size()>0){
			
			if(requestList.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for(final PmtbPrepaidReq req : requestList){
				Listitem item = new Listitem();
				item.setValue(req);
				
				Listcell checkboxListcell = new Listcell();
				Checkbox checkbox = new Checkbox();
				checkbox.setChecked(req.isSelected());
				checkbox.addEventListener(Events.ON_CHECK, new EventListener() {
					public void onEvent(Event e) throws Exception {
						Checkbox self = (Checkbox) e.getTarget();
						req.setSelected(self.isChecked());
					}
				});
				
				AmtbAccount acct = req.getAmtbAccount();
				AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
				
				checkboxListcell.appendChild(checkbox);
				item.appendChild(checkboxListcell);
				item.appendChild(newListcell(req.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(topAcct.getCustNo()));
				item.appendChild(newListcell(topAcct.getAccountName()));
				item.appendChild(newListcell(NonConfigurableConstants.PREPAID_REQUEST_TYPE.get(req.getRequestType())));
				item.appendChild(newListcell(NonConfigurableConstants.PREPAID_REQUEST_STATUS.get(req.getStatus())));
				item.appendChild(newListcell(req.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(req.getRequestBy().getName()));
				
				resultList.appendChild(item);
			}
			
			if(requestList.size()>ConfigurableConstants.getMaxQueryResult())
				resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultList.getListfoot()!=null)
				resultList.removeChild(resultList.getListfoot());
		}
		else{
			if(resultList.getListfoot()==null){
				resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(7));
			}
		}
		
		resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultList.setPageSize(10);
		
		
		

	}
	

	public void reset() throws InterruptedException{
		
		ComponentUtil.reset(reqNoField, reqTypeField, requestDateFromField, requestDateToField, requestorField, resultList);
		super.reset();
	}

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String name) {
		return this.businessHelper.getAccountBusiness().getTopLevelAccountsWithEntity(custNo, name);
	}
	
	
	public void selectRequest() throws InterruptedException{
		
		PmtbPrepaidReq request = ComponentUtil.getSelectedItem(resultList);
		String url = null;
		if (request instanceof PmtbIssuanceReq) {
			url = Uri.APPROVE_ISSUANCE_REQ;
		} else if (request instanceof PmtbTopUpReq) {
			url = Uri.APPROVE_TOP_UP_REQ;
		} else if (request instanceof PmtbTransferReq) {
			url = Uri.APPROVE_TRANSFER_REQ;
		} else if (request instanceof PmtbExtBalExpDateReq) {
			url = Uri.APPROVE_EXTEND_BALANCE_EXPIRY_DATE_REQ;
		} else if (request instanceof PmtbAdjustmentReq) {
			url = Uri.APPROVE_ADJUSTMENT_REQ;
		}

		if (url == null) {
			throw new WrongValueException("Not supported request. Please contact system administrator!");
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("requestNo", request.getReqNo());
		this.forward(url, map);
		
	}
	
	@Override
	public void refresh() throws InterruptedException {
		super.refresh();
		resultList.clearSelection();
		searchRequest();
		
		ComponentUtil.reset(approvalRemarksField);
		
	}

	
	@Override
	public CommonWindow back() throws InterruptedException {
		refresh();
		return super.back();
	}
	
	
	public void checkAll() throws InterruptedException {

		// this will only display the tick in the check box, it will not set waive issuance fee of card to yes or not
		ComponentUtil.toggelCheckAllBox(checkAllBox, resultList);

	}
	
	
	
	@SuppressWarnings("unchecked")
	private List<PmtbPrepaidReq> getSelectedRequests(){
		
		List<PmtbPrepaidReq> selectedList =  Lists.newArrayList();
		List<Listitem> itemList = resultList.getItems();
		for(Listitem item: itemList){
			
			PmtbPrepaidReq req= (PmtbPrepaidReq) item.getValue();
			if(req.isSelected()){
				selectedList.add(req);
			}
		}

		
		if(selectedList.isEmpty()){
			throw new WrongValueException("No any request selected.");
		}
		
		return selectedList;
		
		
	}
	
	public void massApprove() throws InterruptedException{
		
		logger.debug("massApprove");
		
		displayProcessing();
		
		String remarks = approvalRemarksField.getValue();

		List<String> successList = Lists.newArrayList();
		Map<String, String> errorMap = Maps.newLinkedHashMap();
		String user = CommonWindow.getUserLoginIdAndDomain();
		
		List<PmtbPrepaidReq> selectedList = getSelectedRequests();
		
		for(PmtbPrepaidReq req: selectedList){
			
			logger.debug("Processing req: " + req.getReqNo());
			
			req.setApprovalRemarks(remarks);

			String reqNo = String.valueOf(req.getReqNo());
			try{
				
				this.businessHelper.getPrepaidBusiness().commonApprove(req, user);
				
				try {
					this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(req);
				} catch (Exception e){
					LoggerUtil.printStackTrace(logger, e);
					logger.debug("Failed to generate report for request " + req.getReqNo());
				}
				
				successList.add(reqNo);
				
			}catch(Exception e){
				if(! (e instanceof WrongValueException)){
					LoggerUtil.printStackTrace(logger, e);
				}
				
				errorMap.put(reqNo, e.getMessage());
				
			}
		}
		
		if(errorMap.isEmpty()){
			ComponentUtil.showBox("All selected request successfuly approved.", "Approve Selected");
		} else {
			
			
			List<String> erorrList = Lists.newArrayList();
			for(Entry<String, String> entry: errorMap.entrySet()){
				erorrList.add(entry.getKey() + " - " + entry.getValue());
			}
			
			String msg = "";
			if(!successList.isEmpty()){
				msg += "Not all request successfully approved.\n [Successfully approved: " + Joiner.on("; ").join(successList) + "] \n";
			} else {
				msg += "All requests failed to approve. \n";
			}
			
			msg += "[Failed approve: " + Joiner.on("; ").join(erorrList) + "]\n";
			
			ComponentUtil.showBox(msg, "Approve Selected");
		}
		
		searchRequest();
		
	}
	
	
	public void massReject() throws InterruptedException{
		
		logger.debug("massReject");
		
		displayProcessing();
		
		List<String> successList = Lists.newArrayList();
		Map<String, String> errorMap = Maps.newLinkedHashMap();
		String user = CommonWindow.getUserLoginIdAndDomain();
		
		List<PmtbPrepaidReq> selectedList = getSelectedRequests();
		
		String remarks = approvalRemarksField.getValue();
	
		//Approval remarks is mandatory if reject.
		if(Strings.isNullOrEmpty(remarks)){
			throw new WrongValueException("Approval remarks is mandatory if reject.");
		}
		
		for(PmtbPrepaidReq req: selectedList){
			
			logger.debug("Processing req: " + req.getReqNo());
			
			req.setApprovalRemarks(remarks);

			String reqNo = String.valueOf(req.getReqNo());
			try{
				
				this.businessHelper.getPrepaidBusiness().commonReject(req, user);

				successList.add(reqNo);
				
			}catch(Exception e){
				LoggerUtil.printStackTrace(logger, e);
				errorMap.put(reqNo, e.getMessage());
				
			}
		}
		
		if(errorMap.isEmpty()){
			ComponentUtil.showBox("All selected request successfuly rejected.", "Reject Selected");
		} else {
			
			List<String> erorrList = Lists.newArrayList();
			for(Entry<String, String> entry: errorMap.entrySet()){
				erorrList.add(entry.getKey() + " - " + entry.getValue());
			}
			
			String msg = "";
			if(!successList.isEmpty()){
				msg += "Not all request successfully rejected.\n [Successfully rejected: " + Joiner.on("; ").join(successList) + "] \n";
			} else {
				msg += "All requests failed to reject. \n";
			}
			
			msg += "[Failed reject: " + Joiner.on("; ").join(erorrList) + "]\n";
			
			
			ComponentUtil.showBox( msg, "Reject Selected");
		}
		
		searchRequest();
		
	}

	
}
