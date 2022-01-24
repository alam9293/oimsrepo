package com.webapp.ims.dis.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.ProjDisburseService;
import com.webapp.ims.model.DeptProjectDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.repository.DeptProjectRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;

@Service
@Transactional
public class ProjDisburseServiceImpl implements ProjDisburseService {
	private final Logger logger = LoggerFactory.getLogger(ProjDisburseServiceImpl.class);
	@Autowired
	private ProjDisburseRepository disburseRepository;
	@Autowired
	private DeptProjectRepository deptProjRepository;
	@Autowired
	private InvestmentDetailsRepository invRepository;
	@Autowired
	private PhaseWiseInvestmentDetailsRepository pwRepository;
	@Autowired
	private ProjDisburseService projDisburseService;
	
	@Autowired
	private ProjDisburseRepository projDisburseRepository;

	@Override
	public ExistProjDisbursement saveProjDisbursement(ExistProjDisbursement ProjDisburse) {
		return disburseRepository.save(ProjDisburse);
	}

	/**
	 * The purpose of this method is to fetch records from table and set into model.
	 * Author: Hemant kumar
	 */
	public void disbIncentiveCapInv(ExistProjDisbursement projDisbursement,  Model model,
			HttpSession session) {
		logger.info("## disbIncentiveCapInv method start ##");

		String applId = (String) session.getAttribute("appId");

		DeptProjectDetails deptProjDetails = deptProjRepository.findByApplicantDetailId(applId);
		InvestmentDetails investDetails = invRepository.findByApplId(applId);
		List<PhaseWiseInvestmentDetails> pwInvDetailsList = pwRepository.findByPwApcId(applId);

		model.addAttribute("natureOfProject", deptProjDetails.getNatureOfProject());
		model.addAttribute("expansion", deptProjDetails.getExpansion());
		model.addAttribute("diversification", deptProjDetails.getDiversification());
		model.addAttribute("buildingcost", investDetails.getInvBuildingCost());
		model.addAttribute("landcost", investDetails.getInvLandCost());
		model.addAttribute("plantmachcost", investDetails.getInvPlantAndMachCost());
		model.addAttribute("InvFci", investDetails.getInvFci());
		model.addAttribute("mfaCost", investDetails.getInvOtherCost());
		model.addAttribute("pwInvDetailsList", pwInvDetailsList);

		logger.info("## disbIncentiveCapInv method end ##");

	}

	/**
	 * The responsibility of this method is to save Capital Investment Project
	 * records in the table.
	 */
	@Override
	public void saveIncentiveDisburse(ExistProjDisbursement projDisbursement, BindingResult result, Model model,
			HttpSession session) {

		ExistProjDisbursement newProjDisburse = new ExistProjDisbursement();

		if (session.getAttribute("appId") != null || !session.getAttribute("appId").toString().isEmpty()) {
			String applId = (String) session.getAttribute("appId");
			InvestmentDetails investDetails = invRepository.findByApplId(applId);

			String projDisburseId = applId.substring(0, applId.length() - 2) + "DSB";

			newProjDisburse.setProjApcId(applId);
			newProjDisburse.setProjDisId(projDisburseId);
			newProjDisburse.setProjDisBldgCost(investDetails.getInvBuildingCost());
			newProjDisburse.setProjDisLandCost(investDetails.getInvLandCost());
			newProjDisburse.setProjDisPlantMachCost(investDetails.getInvPlantAndMachCost());
			newProjDisburse.setProjDisConstruct(projDisbursement.getProjDisConstruct());
			newProjDisburse.setProjDisInfra(projDisbursement.getProjDisInfra());
			newProjDisburse.setProjDisExpandOrDiverse(projDisbursement.getProjDisExpandOrDiverse());
			newProjDisburse.setExProjMiscFixedAsset(projDisbursement.getExProjMiscFixedAsset());
			newProjDisburse.setExMiscExpDiv(projDisbursement.getExMiscExpDiv());
			newProjDisburse.setExMiscIncrement(projDisbursement.getExMiscIncrement());

			newProjDisburse.setProjLandIncrement(projDisbursement.getProjLandIncrement());
			newProjDisburse.setProjBuildIncrement(projDisbursement.getProjBuildIncrement());
			newProjDisburse.setProjConstructIncrement(projDisbursement.getProjConstructIncrement());
			newProjDisburse.setProjInfraIncrement(projDisbursement.getProjInfraIncrement());
			newProjDisburse.setProjPlantIncrement(projDisbursement.getProjPlantIncrement());
			newProjDisburse.setProjDisCreatedBy("Disbursement User");
			newProjDisburse.setTotalAmountOfInvest(projDisbursement.getTotalAmountOfInvest());
			newProjDisburse.setTotalIncrement(projDisbursement.getTotalIncrement());
			newProjDisburse.setTtlAmtExitProj(projDisbursement.getTtlAmtExitProj());
			
			newProjDisburse.setProjDisModifiyDate(new Date());

			newProjDisburse.setTotalInterest(projDisbursement.getTotalInterest());
			newProjDisburse.setTotalLoan(projDisbursement.getTotalLoan());
			projDisburseService.saveProjDisbursement(newProjDisburse);
		}
	}

	@Override
	public ExistProjDisbursement getDetailsBycapInvId(String id) {
		
		return projDisburseRepository.getDetailsByprojDisId(id);
	}

	@Override
	public ExistProjDisbursement getDetailsByprojApcId(String appId) {
		
		return projDisburseRepository.getDetailsByprojApcId(appId);
	}

	

}
