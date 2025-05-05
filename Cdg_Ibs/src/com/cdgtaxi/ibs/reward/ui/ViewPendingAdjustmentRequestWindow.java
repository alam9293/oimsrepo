package com.cdgtaxi.ibs.reward.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewPendingAdjustmentRequestWindow extends CommonWindow implements AfterCompose{
	private static final long serialVersionUID = 1L;
	
	private Listbox requestLB;
	private Button viewHistoryBtn;
	private Listfoot footer;
	
	public void afterCompose(){
		Components.wireVariables(this, this);
		
		this.listRequests();
		
		if(!this.checkUriAccess(Uri.VIEW_REWARDS_ADJUSTMENT_REQUEST_HISTORY))
			viewHistoryBtn.setDisabled(true);
	}
	
	private void listRequests(){
		requestLB.getItems().clear();
		
		List<LrtbRewardAdjReq> requests = this.businessHelper.getRewardBusiness().getPendingAdjustmentRequests();
		if(requests.isEmpty()){
			footer.setVisible(true);
		}else{
			footer.setVisible(false);
			
			for(LrtbRewardAdjReq request : requests){
				Listitem requestItem = new Listitem();
				requestItem.appendChild(newListcell(request.getAdjReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
				requestItem.appendChild(newListcell(request.getAmtbAccount().getCustNo()));
				requestItem.appendChild(newListcell(request.getAmtbAccount().getAccountName()));
				requestItem.appendChild(newListcell(request.getCreatedBy()));
				requestItem.appendChild(newListcell(request.getCreatedDt()));
				requestItem.setValue(request);
				requestLB.appendChild(requestItem);
			}
		}
	}
	
	public void proceedToApproval() throws InterruptedException{
		Map<String, LrtbRewardAdjReq> params = new HashMap<String, LrtbRewardAdjReq>();
		params.put("request", (LrtbRewardAdjReq)requestLB.getSelectedItem().getValue());
		this.forward(Uri.APPROVE_REWARDS_ADJUSTMENT_REQUEST, params);
	}
	
	@Override
	public void refresh() {
		listRequests();
	}
	
	public void viewHistory() throws InterruptedException {
		this.forward(Uri.VIEW_REWARDS_ADJUSTMENT_REQUEST_HISTORY, null);
	}
}