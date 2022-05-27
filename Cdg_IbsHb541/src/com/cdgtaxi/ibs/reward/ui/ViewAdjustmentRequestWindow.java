package com.cdgtaxi.ibs.reward.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InsufficientRewardPointsException;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReqFlow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("unchecked")
public class ViewAdjustmentRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewAdjustmentRequestWindow.class);
	
	private Label acctNoLbl, acctNameLbl, ptsLbl, requestTypeLbl, adjPtsFromLbl, reasonLbl, remarksLbl, approvalRemarksLbl, approvalStatusLbl;
	private LrtbRewardAdjReqFlow requestFlow;
	private Row approvalRemarksRow;
	
	public ViewAdjustmentRequestWindow(){
		 Map params = Executions.getCurrent().getArg();
		 requestFlow = (LrtbRewardAdjReqFlow)params.get("requestFlow");
		if(requestFlow == null)
			throw new NullPointerException("Adjustment Request Flow Object not found!"); //This should not happen at all
	}
	
	public void afterCompose(){
		Components.wireVariables(this, this);
		
		acctNoLbl.setValue(requestFlow.getLrtbRewardAdjReq().getAmtbAccount().getCustNo());
		acctNameLbl.setValue(requestFlow.getLrtbRewardAdjReq().getAmtbAccount().getAccountName());
		ptsLbl.setValue(requestFlow.getLrtbRewardAdjReq().getPoints().toString());
		requestTypeLbl.setValue(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPES.get(requestFlow.getLrtbRewardAdjReq().getRequestType()));
		adjPtsFromLbl.setValue(NonConfigurableConstants.REWARDS_REDEEM_FROM.get(requestFlow.getLrtbRewardAdjReq().getAdjustPointsFrom()));
		reasonLbl.setValue(requestFlow.getLrtbRewardAdjReq().getMstbMasterTable().getMasterValue());
		remarksLbl.setValue(requestFlow.getLrtbRewardAdjReq().getRemarks());

		approvalStatusLbl.setValue(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS.get(requestFlow.getToStatus()));
		
		if(!requestFlow.getToStatus().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING)){
			approvalRemarksRow.setVisible(true);
			approvalRemarksLbl.setValue(requestFlow.getRemarks());
		}
	}
	
	@Override
	public void refresh() {

	}
}
