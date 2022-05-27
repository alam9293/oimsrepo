package com.cdgtaxi.ibs.reward.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InsufficientRewardPointsException;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;

@SuppressWarnings("unchecked")
public class ApproveAdjustmentRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApproveAdjustmentRequestWindow.class);
	
	private Label acctNoLbl, acctNameLbl, ptsLbl, requestTypeLbl, adjPtsFromLbl, reasonLbl, remarksLbl;
	private CapsTextbox remarksTB;
	private LrtbRewardAdjReq request;
	
	public ApproveAdjustmentRequestWindow(){
		 Map params = Executions.getCurrent().getArg();
		 request = (LrtbRewardAdjReq)params.get("request");
		if(request == null)
			throw new NullPointerException("Adjustment Request Object not found!"); //This should not happen at all
	}
	
	public void afterCompose(){
		Components.wireVariables(this, this);
		
		acctNoLbl.setValue(request.getAmtbAccount().getCustNo());
		acctNameLbl.setValue(request.getAmtbAccount().getAccountName());
		ptsLbl.setValue(request.getPoints().toString());
		requestTypeLbl.setValue(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPES.get(request.getRequestType()));
		adjPtsFromLbl.setValue(NonConfigurableConstants.REWARDS_REDEEM_FROM.get(request.getAdjustPointsFrom()));
		reasonLbl.setValue(request.getMstbMasterTable().getMasterValue());
		remarksLbl.setValue(request.getRemarks());
	}
	
	public void approve() throws InterruptedException{
		try{
			this.displayProcessing();
			this.businessHelper.getRewardBusiness().approveAdjustmentRequest(request, remarksTB.getValue(), getUserLoginIdAndDomain());
			Messagebox.show("Rewards adjustment request approved", "Rewards Adjustment Request Approval", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(InsufficientRewardPointsException irpe){
			Messagebox.show("Points is not sufficient for adjustment",
					"Rewards Adjustment Request", Messagebox.OK, Messagebox.ERROR);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void reject() throws InterruptedException{
		try{
			RequiredConstraint constraint = new RequiredConstraint();
			constraint.validate(remarksTB, remarksTB.getValue());
			this.displayProcessing();
			this.businessHelper.getRewardBusiness().rejectAdjustmentRequest(request, remarksTB.getValue(), getUserLoginIdAndDomain());
			Messagebox.show("Rewards adjustment request rejected", "Rewards Adjustment Request Approval", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() {

	}
}
