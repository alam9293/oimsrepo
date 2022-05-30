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
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewPendingSubscriptionRequestWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ViewPendingSubscriptionRequestWindow.class);
	private static final long serialVersionUID = 1L;
	public ViewPendingSubscriptionRequestWindow(){
		logger.info("ViewPendingSubscriptionRequestWindow()");
	}
	public void init(){
		logger.info("init()");
		Listbox subscriptionRequests = (Listbox)this.getFellow("subscriptionRequests");
		subscriptionRequests.getItems().clear();
		((Textbox)this.getFellow("remarks")).setText(null);
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getPendingSubscriptionRequests();
		if(requests.isEmpty()){
			Listfoot footer = (Listfoot)this.getFellow("footer");
			footer.setVisible(true);
		}else{
			for(Integer requestId : requests.keySet()){
				Map<String, String> request = requests.get(requestId);
				Listitem requestItem = new Listitem();
				requestItem.appendChild(newListcell(new Integer(request.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				requestItem.appendChild(new Listcell(request.get("acctName")));
				requestItem.appendChild(new Listcell(request.get("divCodeName")));
				requestItem.appendChild(new Listcell(request.get("deptCodeName")));
				requestItem.appendChild(new Listcell(request.get("prdType")));
				requestItem.appendChild(new Listcell(request.get("prdDisc")));
				requestItem.appendChild(new Listcell(request.get("loyaltyPlan")));
				requestItem.appendChild(new Listcell(request.get("subscPlan")));
				requestItem.appendChild(new Listcell(request.get("issuancePlan")));
				requestItem.appendChild(new Listcell(request.get("action")));
				requestItem.appendChild(new Listcell(request.get("reqBy")));
				requestItem.appendChild(new Listcell(request.get("reqDate")));
				Listcell checkboxCell = new Listcell();
				checkboxCell.appendChild(new Checkbox());
				requestItem.appendChild(checkboxCell);
				requestItem.setValue(requestId);
				subscriptionRequests.appendChild(requestItem);
			}
		}
		
		if(!this.checkUriAccess(Uri.SEARCH_SUBSCRIPTION))
			((Button)this.getFellow("viewHistoryBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.APPROVE_SUBSCRIPTION)){
			((Button)this.getFellow("approveBtn")).setDisabled(true);
			((Button)this.getFellow("rejectBtn")).setDisabled(true);
		}
	}
	public void selectAll(){
		logger.info("selectAll()");
		Listbox subscriptionRequests = (Listbox)this.getFellow("subscriptionRequests");
		for(Object subscriptionRequest : subscriptionRequests.getItems()){
			Listitem item = (Listitem)subscriptionRequest;
			Listcell checkboxCell = (Listcell)item.getLastChild();
			((Checkbox)checkboxCell.getFirstChild()).setChecked(true);
		}
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Integer requestId = (Integer)selectedItem.getValue();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestId", requestId);
		this.forward(Uri.APPROVE_SUBSCRIPTION, params);
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void approveSelected() throws InterruptedException{
		List<Integer> requestIds = new ArrayList<Integer>();
		Listbox subscriptionRequests = (Listbox)this.getFellow("subscriptionRequests");
		for(Object subscriptionRequest : subscriptionRequests.getItems()){
			Checkbox checkbox = (Checkbox)((Listitem)subscriptionRequest).getLastChild().getFirstChild();
			if(checkbox.isChecked()){
				requestIds.add((Integer)((Listitem)subscriptionRequest).getValue());
			}
		}
		if(requestIds.isEmpty()){
			Messagebox.show("No selected request!", "Approve Subscription Request", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(Messagebox.show("Confirm Approve?", "Approve Subscription Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			try {
				String remarks = ((Textbox)this.getFellow("remarks")).getValue();
				this.businessHelper.getAccountBusiness().approveSubscriptionRequest(requestIds, remarks);
				Messagebox.show("Subscriptions Approved", "Approve Subscription Request", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			} catch (CniiInterfaceException e) {
				logger.error("Error", e);
				Messagebox.show("Unable to approve request due to CNII! Please try again later", "Approve Subscription Request", Messagebox.OK, Messagebox.ERROR);
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to approve request! Please try again later", "Approve Subscription Request", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void rejectSelected() throws InterruptedException{
		List<Integer> requestIds = new ArrayList<Integer>();
		Listbox subscriptionRequests = (Listbox)this.getFellow("subscriptionRequests");
		for(Object subscriptionRequest : subscriptionRequests.getItems()){
			Checkbox checkbox = (Checkbox)((Listitem)subscriptionRequest).getLastChild().getFirstChild();
			if(checkbox.isChecked()){
				requestIds.add((Integer)((Listitem)subscriptionRequest).getValue());
			}
		}
		if(requestIds.isEmpty()){
			Messagebox.show("No selected request!", "Approve Subscription Request", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(Messagebox.show("Confirm Reject?", "Approve Subscription Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			if(remarks!=null && remarks.length()!=0){
				this.businessHelper.getAccountBusiness().rejectSubscriptionRequest(requestIds, remarks);
				Messagebox.show("Subscriptions Rejected", "Approve Subscription Request", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}else{
				Messagebox.show("Please key in rejection remarks!", "Approve Subscription Request", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		}
	}
	public void viewHistory() throws InterruptedException {
		logger.info("viewHistory()");
		this.forward(Uri.SEARCH_SUBSCRIPTION, null);
	}
}