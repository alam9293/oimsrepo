package com.webapp.ims.dis.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.ExistProjDisbursement;

public interface ProjDisburseService {

	public ExistProjDisbursement saveProjDisbursement(ExistProjDisbursement ProjDisburse);

	public void disbIncentiveCapInv(ExistProjDisbursement projDisbursement,  Model model,
			HttpSession session);

	public void saveIncentiveDisburse(ExistProjDisbursement projDisbursement, BindingResult result, Model model,
			 HttpSession session);
	
	public ExistProjDisbursement getDetailsBycapInvId(String id);
	
	public ExistProjDisbursement getDetailsByprojApcId(String appId);
}
