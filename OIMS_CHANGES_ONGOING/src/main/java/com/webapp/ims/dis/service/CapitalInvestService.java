package com.webapp.ims.dis.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;

public interface CapitalInvestService {
	public void saveCapialInvest(CapitalInvestmentDetails capitalInvestment);

	public void saveCapialInvestDetails(ExistProjDisbursement existprojDisbursement, BindingResult result, Model model,
			HttpSession session);
	
	public CapitalInvestmentDetails getDetailsByprojDisId(String id);
	
	public CapitalInvestmentDetails getDetailsBycapInvApcId(String appid);
}
