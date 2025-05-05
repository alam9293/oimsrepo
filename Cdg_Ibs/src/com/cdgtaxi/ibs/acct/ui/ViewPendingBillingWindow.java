package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewPendingBillingWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ViewPendingBillingWindow.class);
	private static final long serialVersionUID = 1L;
	public ViewPendingBillingWindow(){
		logger.info("ViewPendingBillingWindow()");
	}
	public void init(){
		logger.info("init()");
		Listbox billingRequests = (Listbox)this.getFellow("billingRequests");
		billingRequests.getItems().clear();
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getPendingBillingChangeRequests();
		if(requests.isEmpty()){
			Listfoot footer = (Listfoot)this.getFellow("footer");
			footer.setVisible(true);
		}else{
			for(Integer requestId : requests.keySet()){
				Map<String, String> request = requests.get(requestId);
				Listitem requestItem = new Listitem();
				requestItem.appendChild(newListcell(new Integer(request.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				requestItem.appendChild(newListcell(request.get("acctName")));
				requestItem.appendChild(newListcell(request.get("requester")));
				requestItem.appendChild(newListcell(DateUtil.convertStrToTimestamp(request.get("reqDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
				//requestItem.appendChild(newListcell(request.get("remarks")));
				requestItem.setValue(requestId);
				billingRequests.appendChild(requestItem);
			}
		}
		
		if(!this.checkUriAccess(Uri.SEARCH_BILLING))
			((Button)this.getFellow("viewHistoryBtn")).setDisabled(true);
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Integer requestId = (Integer)selectedItem.getValue();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestId", requestId);
		this.forward(Uri.APPROVE_BILLING, params);
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void viewHistory() throws InterruptedException {
		logger.info("viewHistory()");
		this.forward(Uri.SEARCH_BILLING, null);
	}
}