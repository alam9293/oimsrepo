package com.cdgtaxi.ibs.prepaid.ui;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.util.ComponentUtil;
import com.google.common.base.Strings;


@SuppressWarnings("serial")
public class ApproveAdjustmentReqWindow extends DetailsAdjustmentReqWindow {

	private Textbox approvalRemarksField;
	
	public void approve() throws InterruptedException{
		
		if (!ComponentUtil.confirmBox("Do you confirm to approved the request?", "Approve Adjustment Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(approvalRemarksField.getValue());
		this.businessHelper.getPrepaidBusiness().approveAdjustmentRequest(req, getUserLoginIdAndDomain());
		
		Messagebox.show("Request was approved and adjustment has been done successfully.", "Approve Adjustment Request", Messagebox.OK, Messagebox.INFORMATION);

		
		this.back();
		
	}
	
	public void reject() throws InterruptedException{
		
		String remarks = approvalRemarksField.getValue();
		//Approval remarks is mandatory if reject.
		if(Strings.isNullOrEmpty(remarks)){
			throw new WrongValueException("Approval remarks is mandatory if reject.");
		}
		
		if (!ComponentUtil.confirmBox("Do you confirm to reject the request?", "Approve Adjustent Request")) {
			return;
		}
		
		displayProcessing();
		
		req.setApprovalRemarks(remarks);
		this.businessHelper.getPrepaidBusiness().commonReject(req, getUserLoginIdAndDomain());
		this.back();
	}
	
	
	
	
	
	
}
