package com.cdgtaxi.ibs.prepaid.ui;

import java.io.IOException;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;


@SuppressWarnings("serial")
public class ApproveIssuanceReqWindow extends DetailsIssuanceReqWindow {


	private Textbox approvalRemarksField;
	public void approve() throws NetException, IOException, InterruptedException{

		if (!ComponentUtil.confirmBox("Do you confirm to approved the request?", "Approve Issuance Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(approvalRemarksField.getValue());
		this.businessHelper.getPrepaidBusiness().approveIssuanceRequest(req, getUserLoginIdAndDomain());
		
		try {
			this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(req);
		} catch (Exception e){
			LoggerUtil.printStackTrace(logger, e);
			logger.debug("Failed to generate report for request " + req.getReqNo());
		}
		
		String msg = "Issuance request approved.";
		if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT.equals(req.getStatus())){
			int createdProductSize = req.getPmtbIssuanceReqCards().size();
			msg += " As credit term is not COD, total " + createdProductSize +" product(s) were created before payment received.";
		} else {
			msg += " As the credit term is COD, product(s) will only created upon full payment.";
		}
		
		Messagebox.show(msg, "Approve Issuance Request", Messagebox.OK, Messagebox.INFORMATION);
		
		this.back();
		
	}
	
	public void reject() throws InterruptedException{
		
		String remarks = approvalRemarksField.getValue();
		//Approval remarks is mandatory if reject.
		if(Strings.isNullOrEmpty(remarks)){
			throw new WrongValueException("Approval remarks is mandatory if reject.");
		}
		
		if (!ComponentUtil.confirmBox("Do you confirm to reject the request?", "Approve Issuance Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(remarks);

		this.businessHelper.getPrepaidBusiness().commonReject(req, getUserLoginIdAndDomain());
		this.back();
	}

}
