package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewPendingCreditReviewWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ViewPendingCreditReviewWindow.class);
	private static final long serialVersionUID = 1L;
	public ViewPendingCreditReviewWindow(){
		logger.info("ViewPendingCreditReviewWindow()");
	}
	public void init(){
		logger.info("init()");
		Listbox creditReviewRequests = (Listbox)this.getFellow("creditReviewRequests");
		creditReviewRequests.getItems().clear();
		((Textbox)this.getFellow("remarks")).setText(null);
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getPendingCreditReviewRequests();
		if(requests.isEmpty()){
			Listfoot footer = (Listfoot)this.getFellow("footer");
			footer.setVisible(true);
		}else{
			for(Integer requestId : requests.keySet()){
				Map<String, String> request = requests.get(requestId);
				Listitem requestItem = new Listitem();
				requestItem.appendChild(newListcell(new Integer(request.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				requestItem.appendChild(new Listcell(request.get("acctName")));
				requestItem.appendChild(new Listcell(request.get("creditLimit")));
				requestItem.appendChild(new Listcell(request.get("newCreditLimit")));
				requestItem.appendChild(new Listcell(request.get("requester")));
				requestItem.appendChild(new Listcell(request.get("reqDate")));
				requestItem.appendChild(new Listcell(request.get("remarks")));
				Listcell checkboxCell = new Listcell();
				checkboxCell.appendChild(new Checkbox());
				requestItem.appendChild(checkboxCell);
				requestItem.setValue(requestId);
				creditReviewRequests.appendChild(requestItem);
			}
		}
		
		if(!this.checkUriAccess(Uri.SEARCH_CREDIT_REVIEW))
			((Button)this.getFellow("viewHistoryBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.APPROVE_CREDIT_REVIEW)){
			((Button)this.getFellow("approveBtn")).setDisabled(true);
			((Button)this.getFellow("rejectBtn")).setDisabled(true);
		}
	}
	public void selectAll(){
		logger.info("selectAll()");
		Listbox creditReviewRequests = (Listbox)this.getFellow("creditReviewRequests");
		for(Object creditReviewRequest : creditReviewRequests.getItems()){
			Listitem item = (Listitem)creditReviewRequest;
			Listcell checkboxCell = (Listcell)item.getLastChild();
			((Checkbox)checkboxCell.getFirstChild()).setChecked(true);
		}
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Integer requestId = (Integer)selectedItem.getValue();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestId", requestId);
		this.forward(Uri.APPROVE_CREDIT_REVIEW, params);
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void approveSelected() throws InterruptedException{
		List<Integer> requestIds = new ArrayList<Integer>();
		Listbox creditReviewRequests = (Listbox)this.getFellow("creditReviewRequests");
		for(Object creditReviewRequest : creditReviewRequests.getItems()){
			Checkbox checkbox = (Checkbox)((Listitem)creditReviewRequest).getLastChild().getFirstChild();
			if(checkbox.isChecked()){
				requestIds.add((Integer)((Listitem)creditReviewRequest).getValue());
			}
		}
		if(requestIds.isEmpty()){
			Messagebox.show("No selected request!", "Approve Credit Review Request", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(Messagebox.show("Confirm Approve?", "Approve Credit Review Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			try {
				String remarks = ((Textbox)this.getFellow("remarks")).getValue();
				
				//Check #39 Temp Increase Credit Limit Check				
				if(checkTempSpecial(requestIds))
				{
					this.businessHelper.getAccountBusiness().approveCreditReviewRequest(requestIds, remarks, getUserLoginIdAndDomain());
					Messagebox.show("Approved", "Approve Credit Review Request", Messagebox.OK, Messagebox.INFORMATION);
					this.refresh();
				}
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to approve request! Please try again later", "Approve Credit Review Request", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void rejectSelected() throws InterruptedException{
		List<Integer> requestIds = new ArrayList<Integer>();
		Listbox creditReviewRequests = (Listbox)this.getFellow("creditReviewRequests");
		for(Object creditReviewRequest : creditReviewRequests.getItems()){
			Checkbox checkbox = (Checkbox)((Listitem)creditReviewRequest).getLastChild().getFirstChild();
			if(checkbox.isChecked()){
				requestIds.add((Integer)((Listitem)creditReviewRequest).getValue());
			}
		}
		if(requestIds.isEmpty()){
			Messagebox.show("No selected request!", "Approve Credit Review Request", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(Messagebox.show("Confirm Reject?", "Approve Credit Review Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			if(remarks!=null && remarks.length()!=0){
				this.businessHelper.getAccountBusiness().rejectCreditReviewRequest(requestIds, remarks, getUserLoginIdAndDomain());
				Messagebox.show("Rejected", "Approve Credit Review Request", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}else{
				Messagebox.show("Please key in rejection remarks!", "Approve Credit Review Request", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		}
	}
	public void viewHistory() throws InterruptedException {
		logger.info("viewHistory()");
		this.forward(Uri.SEARCH_CREDIT_REVIEW, null);
	}
	
	public Boolean checkTempSpecial(List<Integer> requestIds) throws InterruptedException{

		String listAcctsAffect = "";
		
		for(Integer requestId : requestIds) 
		{
			AmtbCredRevReq request = this.businessHelper.getAccountBusiness().getPendingCreditReviewRequestsCheck(requestId);
			
			if(request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)) 
			{
				if(this.businessHelper.getAccountBusiness().hasTempChildFutureCreditLimit(request.getAmtbAccount().getCustNo(), null, null))
				{
					if(listAcctsAffect.trim().equals(""))
						listAcctsAffect = request.getAmtbAccount().getCustNo();
					else
						listAcctsAffect = listAcctsAffect + ", "+request.getAmtbAccount().getCustNo();
				}
			}
		}
		
		if(!listAcctsAffect.trim().equals(""))
		{
			String promptMsg2 = "Warning: There are future Temporary Credit limits in child for (Acct: "+listAcctsAffect+"). Do you want to clear (Acct: "+listAcctsAffect+")'s future Temporary Limits?";
			if(Messagebox.show(promptMsg2, "Credit Review Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
				return false;
			}
		}
		
		return true;
	}
}