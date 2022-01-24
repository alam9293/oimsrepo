package com.webapp.ims.dis.service.impl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.service.NewProjDisburseService;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.repository.InvestmentDetailsRepository;

@Service
@Transactional
public class NewProjDisburseServiceImpl implements NewProjDisburseService {
	@Autowired
	public NewProjDisburseRepository newProjDisbRepository;
	@Autowired
	private InvestmentDetailsRepository invRepository;
	@Autowired
	private NewProjDisburseService newProjDisburseService;

	@Override
	public void saveNewProjDisburse(NewProjDisbursement newProjDisbursement) {
		newProjDisbRepository.save(newProjDisbursement);
	}

	/**
	 * The responsibility of this method is to save Capital Investment New Project
	 * records in the table.
	 */

	public void saveNewProjIncDisburse(ExistProjDisbursement existprojDisbursement, BindingResult result, Model model,
			HttpSession session) {

		NewProjDisbursement newProjDisburse = new NewProjDisbursement();

		if (session.getAttribute("appId") != null || !session.getAttribute("appId").toString().isEmpty()) {
			String applId = (String) session.getAttribute("appId");
			InvestmentDetails investDetails = invRepository.findByApplId(applId);

			String projDisburseId = applId.substring(0, applId.length() - 2) + "NIB";

			newProjDisburse.setNewprojApcId(applId);
			newProjDisburse.setNewprojId(projDisburseId);
			newProjDisburse.setNewprojInfra(existprojDisbursement.getNewprojInfra());
			newProjDisburse.setNewprojBldgCost(investDetails.getInvBuildingCost());
			newProjDisburse.setNewprojLandCost(investDetails.getInvLandCost());
			newProjDisburse.setNewprojPlantMachCost(investDetails.getInvPlantAndMachCost());
			newProjDisburse.setNewprojMiscFixedAsset(investDetails.getInvOtherCost());
			newProjDisburse.setTotal(existprojDisbursement.getTotal());
			
			newProjDisburse.setNewprojCreatedBy("Disbursement User");
			newProjDisburse.setNewprojModifiyDate(new Date());
			newProjDisburseService.saveNewProjDisburse(newProjDisburse);
		}
	}

	@Override
	public ExistProjDisbursement getDetailsByprojDisId(String id) {
		
		return null;
	}

}
