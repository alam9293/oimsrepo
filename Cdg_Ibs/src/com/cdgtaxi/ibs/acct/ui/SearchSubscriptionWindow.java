package com.cdgtaxi.ibs.acct.ui;

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
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchSubscriptionWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(SearchSubscriptionWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> requestStatuses = ComponentUtil.convertToListitems(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS, false);
	private List<Listitem> actionStatuses = ComponentUtil.convertToListitems(NonConfigurableConstants.SUBSCRIPTION_ACTION_STATUS, false);
	public SearchSubscriptionWindow(){
		logger.info("SearchSubscriptionWindow()");
	}
	public void search(){
		logger.info("search()");
		String custNo = ((Textbox)this.getFellow("custNo")).getValue();
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		Date requestDateFrom = ((Datebox)this.getFellow("requestDateFrom")).getValue();
		Date requestDateTo = ((Datebox)this.getFellow("requestDateTo")).getValue();
		Listitem requestStatusItem = ((Listbox)this.getFellow("requestStatusList")).getSelectedItem();
		String requestStatus = requestStatusItem!=null ? (String)requestStatusItem.getValue() : null;
		Listitem requestActionItem = ((Listbox)this.getFellow("requestActionList")).getSelectedItem();
		String requestActionStatus = requestActionItem!=null ? (String)requestActionItem.getValue() : null;
		String requester = ((Textbox)this.getFellow("requester")).getValue();
		Listbox creditReviewRequests = (Listbox)this.getFellow("subscriptionRequests");
		creditReviewRequests.getItems().clear();
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getSubscriptionRequests(custNo, acctName, requestDateFrom, requestDateTo, requestStatus, requester, requestActionStatus);
		for(Integer requestId : requests.keySet()){
			Map<String, String> request = requests.get(requestId);
			Listitem requestItem = new Listitem();
			requestItem.appendChild(newListcell(new Integer(request.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
			requestItem.appendChild(newListcell(request.get("acctName")));
			requestItem.appendChild(newListcell(request.get("divCodeName")));
			requestItem.appendChild(newListcell(request.get("deptCodeName")));
			requestItem.appendChild(newListcell(request.get("action")));
			requestItem.appendChild(newListcell(request.get("reqBy")));
			requestItem.appendChild(new Listcell(request.get("reqDate")));
			requestItem.appendChild(newListcell(request.get("appBy")));
			requestItem.appendChild(new Listcell(request.get("appDt")));
			requestItem.appendChild(newListcell(request.get("reqStatus")));
			requestItem.setValue(requestId);
			creditReviewRequests.appendChild(requestItem);
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
		this.forward(Uri.VIEW_SUBSCRIPTION_REQ, params);
	}
	public List<Listitem> getRequestStatuses(){
		return this.requestStatuses;
	}
	public List<Listitem> getActionStatuses(){
		return this.actionStatuses;
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}