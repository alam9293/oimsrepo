package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchBillingRequestWindow extends CommonInnerAcctWindow {
	private static Logger logger = Logger.getLogger(SearchBillingRequestWindow.class);
	private static final long serialVersionUID = 1L;
	private String custNo;
	@SuppressWarnings("unchecked")
	public SearchBillingRequestWindow(){
		logger.info("SearchBillingRequestWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Search Billing Requests", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Listbox billingRequests = (Listbox)this.getFellow("billingRequests");
		billingRequests.getItems().clear();
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getAllBillingChangeRequest(custNo);
		if(requests.isEmpty()){
			Listfoot footer = (Listfoot)this.getFellow("footer");
			footer.setVisible(true);
		}else{
			for(Integer requestId : requests.keySet()){
				Map<String, String> request = requests.get(requestId);
				Listitem requestItem = new Listitem();
				requestItem.appendChild(newListcell(new Integer(custNo), StringUtil.GLOBAL_STRING_FORMAT));
				requestItem.appendChild(newListcell(request.get("acctName")));
				requestItem.appendChild(newListcell(request.get("requester")));
				requestItem.appendChild(newListcell(DateUtil.convertStrToTimestamp(request.get("requestDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
				requestItem.appendChild(newListcell(request.get("approver")));
				if(request.get("approveDate").equals("-")){
					requestItem.appendChild(newListcell("-"));
				}else{
					requestItem.appendChild(newListcell(DateUtil.convertStrToTimestamp(request.get("approveDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
				}
				requestItem.appendChild(newListcell(request.get("status")));
				requestItem.setValue(requestId);
				billingRequests.appendChild(requestItem);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Integer requestId = (Integer)selectedItem.getValue();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestId", requestId);
		this.forward(Uri.VIEW_BILLING_REQ, params, this.getParent());
	}
}