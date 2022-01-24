package com.webapp.ims.dis.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;

public interface NewProjDisburseService {
	
	public void saveNewProjDisburse(NewProjDisbursement newProjDisbursement);
	public void saveNewProjIncDisburse(ExistProjDisbursement existprojDisbursement, BindingResult result, Model model,
			HttpSession session);
	
	public ExistProjDisbursement getDetailsByprojDisId(String id);
	

}
