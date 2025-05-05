package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ApproveCreditReviewWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApproveCreditReviewWindow.class);
	private Integer requestId;
	@SuppressWarnings("unchecked")
	public ApproveCreditReviewWindow() throws InterruptedException{
		logger.info("ApproveCreditReviewWindow()");
		//request number
		Map<String, Integer> map = Executions.getCurrent().getArg();
		requestId = map.get("requestId");
		if(requestId==null){
			Messagebox.show("Request ID not found!", "Approve Credit Review Request", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(requestId==null){
			this.back();
			return;
		}
		Map<String, String> creditReviewDetails = this.businessHelper.getAccountBusiness().getPendingCreditReviewRequest(requestId);
		((Label)this.getFellow("acctName")).setValue(creditReviewDetails.get("acctName"));
		((Label)this.getFellow("reviewType")).setValue(creditReviewDetails.get("reviewType"));
		((Label)this.getFellow("creditLimit")).setValue(creditReviewDetails.get("creditLimit"));
		((Label)this.getFellow("creditBalance")).setValue(creditReviewDetails.get("creditBalance"));
		((Label)this.getFellow("newCreditLimit")).setValue(creditReviewDetails.get("newCreditLimit"));
		((Label)this.getFellow("fromDate")).setValue(creditReviewDetails.get("effectiveDateFrom"));
		((Label)this.getFellow("toDate")).setValue(creditReviewDetails.get("effectiveDateTo"));
		((Label)this.getFellow("requester")).setValue(creditReviewDetails.get("requester"));
		((Label)this.getFellow("reqDate")).setValue(creditReviewDetails.get("reqDate"));
		((Label)this.getFellow("reqRemarks")).setValue(creditReviewDetails.get("remarks"));
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public void approve() throws WrongValueException, InterruptedException{
		if(Messagebox.show("Confirm Approve?", "Approve Credit Review Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			List<Integer> requestIds = new ArrayList<Integer>();
			requestIds.add(requestId);
			try {
				//Check #39 Temp Increase Credit Limit Check				
				if(checkTempSpecial(requestIds))
				{
					this.businessHelper.getAccountBusiness().approveCreditReviewRequest(requestIds, remarks, this.getUserLoginIdAndDomain());
					Messagebox.show("Approved", "Approve Credit Review Request", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to approve credit review request! Please try again later", "Approve Credit Review Request", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void reject() throws InterruptedException{
		if(Messagebox.show("Confirm Reject?", "Approve Credit Review Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			String remarks = ((Textbox)this.getFellow("remarks")).getValue();
			if(remarks!=null && remarks.length()!=0){
				List<Integer> requestIds = new ArrayList<Integer>();
				requestIds.add(requestId);
				this.businessHelper.getAccountBusiness().rejectCreditReviewRequest(requestIds, remarks, this.getUserLoginIdAndDomain());
				Messagebox.show("Rejected", "Approve Credit Review Request", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Please key in rejection remarks!", "Approve Credit Review Request", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		}
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