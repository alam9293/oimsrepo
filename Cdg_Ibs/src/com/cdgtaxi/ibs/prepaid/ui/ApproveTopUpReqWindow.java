package com.cdgtaxi.ibs.prepaid.ui;
import java.io.IOException;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;


@SuppressWarnings("serial")
public class ApproveTopUpReqWindow extends DetailsTopUpReqWindow {

	private Textbox approvalRemarksField;
	
	public void approve() throws NetException, IOException, InterruptedException, ExpectedException{
		
		if (!ComponentUtil.confirmBox("Do you confirm to approved the request?", "Approve Top Up Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(approvalRemarksField.getValue());
		this.businessHelper.getPrepaidBusiness().approveTopUpRequest(req, getUserLoginIdAndDomain());
		
		try {
			this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(req);
		} catch (Exception e){
			LoggerUtil.printStackTrace(logger, e);
			logger.debug("Failed to generate report for request " + req.getReqNo());
		}

		
		String msg = "Top Up request approved.";
		if(req.getMstbCreditTermMaster() != null){
			int createdProductSize = req.getPmtbTopUpReqCards().size();
			msg += " As credit term is not COD, total " + createdProductSize +" card(s) were top up before payment received.";
		} else {
			msg += " As the credit term is COD, product(s) will only top up upon full payment.";
		}
		Messagebox.show(msg, "Approve Top Up Request", Messagebox.OK, Messagebox.INFORMATION);
		
	
		this.back();
	}
	
	public void reject() throws InterruptedException{
		
		String remarks = approvalRemarksField.getValue();
		//Approval remarks is mandatory if reject.
		if(Strings.isNullOrEmpty(remarks)){
			throw new WrongValueException("Approval remarks is mandatory if reject.");
		}
		
		if (!ComponentUtil.confirmBox("Do you confirm to rejected the request?", "Approve Top Up Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(remarks);
		this.businessHelper.getPrepaidBusiness().commonReject(req, getUserLoginIdAndDomain());
		
		this.back();
	}

}
