package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchBillingWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(SearchBillingWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> requestStatuses = ComponentUtil.convertToListitems(NonConfigurableConstants.BILLING_REQUEST_STATUS, false);
	public SearchBillingWindow(){
		logger.info("SearchBillingWindow()");
		// removing request reject and request approve from request status
		List<Listitem> removeItems = new ArrayList<Listitem>();
		for(Listitem requestStatus : requestStatuses){
			if(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED.equals(requestStatus.getValue())
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED.equals(requestStatus.getValue())
					|| NonConfigurableConstants.BILLING_REQUEST_STATUS_NEW.equals(requestStatus.getValue())){
				removeItems.add(requestStatus);
			}
		}
		requestStatuses.removeAll(removeItems);
	}
	public void search(){
		logger.info("search()");
		String custNo = ((Textbox)this.getFellow("custNo")).getValue();
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		Date requestDateFrom = ((Datebox)this.getFellow("requestDateFrom")).getValue();
		Date requestDateTo = ((Datebox)this.getFellow("requestDateTo")).getValue();
		Listitem requestStatusItem = ((Listbox)this.getFellow("requestStatusList")).getSelectedItem();
		String requestStatus = requestStatusItem!=null ? (String)requestStatusItem.getValue() : null;
		String requester = ((Textbox)this.getFellow("requester")).getValue();
		Listbox billingRequests = (Listbox)this.getFellow("billingRequests");
		billingRequests.getItems().clear();
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getBillingChangeRequests(custNo, acctName, requestDateFrom, requestDateTo, requestStatus, requester);
		for(Integer requestId : requests.keySet()){
			Map<String, String> request = requests.get(requestId);
			Listitem requestItem = new Listitem();
			requestItem.appendChild(newListcell(new Integer(request.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
			requestItem.appendChild(newListcell(request.get("acctName")));
			requestItem.appendChild(newListcell(request.get("requester")));
			requestItem.appendChild(new Listcell(request.get("reqDate")));
			requestItem.appendChild(newListcell(request.get("approver")));
			requestItem.appendChild(new Listcell(request.get("approveDate")));
			requestItem.appendChild(newListcell(request.get("status")));
			requestItem.setValue(requestId);
			billingRequests.appendChild(requestItem);
		}
		if(requests.isEmpty()){
			((Listfoot)this.getFellow("footer")).setVisible(true);
		}else{
			((Listfoot)this.getFellow("footer")).setVisible(false);
		}
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Integer requestId = (Integer)selectedItem.getValue();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestId", requestId);
		this.forward(Uri.VIEW_BILLING_REQ, params);
	}
	public List<Listitem> getRequestStatuses(){
		return this.requestStatuses;
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}