package com.webapp.ims.dis.service.impl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.repository.CapitalInvestRepository;
import com.webapp.ims.dis.service.CapitalInvestService;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.repository.InvestmentDetailsRepository;

@Service
@Transactional
public class CapitalInvestServiceImpl implements CapitalInvestService {
	private final Logger logger = LoggerFactory.getLogger(CapitalInvestServiceImpl.class);
	@Autowired
	private CapitalInvestRepository capInvRepository;
	@Autowired
	private InvestmentDetailsRepository invRepository;
	@Autowired
	private CapitalInvestService capInvService;

	@Override
	public void saveCapialInvestDetails(ExistProjDisbursement existproj, BindingResult result, Model model,
			HttpSession session) {

		logger.info("saveCapialInvestDetails method start");
		CapitalInvestmentDetails newCapitalInv = new CapitalInvestmentDetails();

		if (session.getAttribute("appId") != null || !session.getAttribute("appId").toString().isEmpty()) {
			String applId = (String) session.getAttribute("appId");
			InvestmentDetails investDetails = invRepository.findByApplId(applId);

			String capInvId = applId.substring(0, applId.length() - 2) + "CI";

			newCapitalInv.setCapInvApcId(applId);
			newCapitalInv.setCapInvId(capInvId);

			newCapitalInv.setAnnualTurnOver(existproj.getAnnualTurnOver());
			newCapitalInv.setTtlCostDpr(existproj.getTtlCostDpr());
			newCapitalInv.setTtlcostProAppFrBank(existproj.getTtlcostProAppFrBank());
			newCapitalInv.setTtlBeforeCutOff(existproj.getTtlBeforeCutOff());
			newCapitalInv.setTtlCutOffCommProduction(existproj.getTtlCutOffCommProduction());
			newCapitalInv.setTtlActualInvestment(existproj.getTtlActualInvestment());
			newCapitalInv.setTtlAddiActualInvestment(existproj.getTtlAddiActualInvestment());
			newCapitalInv.setDisValueTotalOfTotal(existproj.getDisValueTotalOfTotal());
			
			
			// Item land cost
			newCapitalInv.setCapInvDPRLC(investDetails.getInvLandCost());
			newCapitalInv.setCapInvAppraisalLC(existproj.getCapInvAppraisalLC());
			newCapitalInv.setCapInvCutoffDateLC(existproj.getCapInvCutoffDateLC());
			newCapitalInv.setCapInvCommProdLC(existproj.getCapInvCommProdLC());
			newCapitalInv.setCapInvAddlInvLC(existproj.getCapInvAddlInvLC());
			newCapitalInv.setCapInvActualInvLC(investDetails.getInvFci());
			newCapitalInv.setCapInvTotalLC(existproj.getCapInvTotalLC());

			// Item Building cost
			newCapitalInv.setCapInvDPRBC(investDetails.getInvBuildingCost());
			newCapitalInv.setCapInvAppraisalBC(existproj.getCapInvAppraisalBC());
			newCapitalInv.setCapInvCutoffDateBC(existproj.getCapInvCutoffDateBC());
			newCapitalInv.setCapInvCommProdBC(existproj.getCapInvCommProdBC());
			newCapitalInv.setCapInvAddlInvBC(existproj.getCapInvAddlInvBC());
			newCapitalInv.setCapInvActualInvBC(existproj.getCapInvActualInvBC());
			newCapitalInv.setCapInvTotalBC(existproj.getCapInvTotalBC());

			// Item Plant&Machinery cost
			newCapitalInv.setCapInvDPRPMC(investDetails.getInvPlantAndMachCost());
			newCapitalInv.setCapInvAppraisalPMC(existproj.getCapInvAppraisalPMC());
			newCapitalInv.setCapInvCutoffDatePMC(existproj.getCapInvCutoffDatePMC());
			newCapitalInv.setCapInvCommProdPMC(existproj.getCapInvCommProdPMC());
			newCapitalInv.setCapInvAddlInvPMC(existproj.getCapInvAddlInvPMC());
			newCapitalInv.setCapInvActualInvPMC(existproj.getCapInvActualInvPMC());
			newCapitalInv.setCapInvTotalPMC(existproj.getCapInvTotalPMC());

			newCapitalInv.setCapInvDPRMFA(existproj.getCapInvDPRMFA());
			newCapitalInv.setCapInvAppraisalMFA(existproj.getCapInvAppraisalMFA());
			newCapitalInv.setCapInvCutoffDateMFA(existproj.getCapInvCutoffDateMFA());
			newCapitalInv.setCapInvCommProdMFA(existproj.getCapInvCommProdMFA());
			newCapitalInv.setCapInvAddlInvMFA(existproj.getCapInvAddlInvMFA());
			newCapitalInv.setCapInvActualInvMFA(existproj.getCapInvActualInvMFA());
			newCapitalInv.setCapInvTotalMFA(existproj.getCapInvTotalPMC());

			newCapitalInv.setCapInvDPRMMC(existproj.getCapInvDPRMMC());
			newCapitalInv.setCapInvAppraisalMMC(existproj.getCapInvAppraisalMMC());
			newCapitalInv.setCapInvCutoffDateMMC(existproj.getCapInvCutoffDateMMC());
			newCapitalInv.setCapInvCommProdMMC(existproj.getCapInvCommProdMMC());
			newCapitalInv.setCapInvAddlInvMMC(existproj.getCapInvAddlInvMMC());
			newCapitalInv.setCapInvActualInvMMC(existproj.getCapInvActualInvMMC());
			newCapitalInv.setCapInvTotalMMC(existproj.getCapInvTotalMMC());

			newCapitalInv.setCapInvDPRTKF(existproj.getCapInvDPRTKF());
			newCapitalInv.setCapInvAppraisalTKF(existproj.getCapInvAppraisalTKF());
			newCapitalInv.setCapInvCutoffDateTKF(existproj.getCapInvCutoffDateTKF());
			newCapitalInv.setCapInvCommProdTKF(existproj.getCapInvCommProdTKF());
			newCapitalInv.setCapInvAddlInvTKF(existproj.getCapInvAddlInvTKF());
			newCapitalInv.setCapInvActualInvTKF(existproj.getCapInvActualInvTKF());
			newCapitalInv.setCapInvTotalTKF(existproj.getCapInvTotalTKF());

			newCapitalInv.setCapInvDPRICP(existproj.getCapInvDPRICP());
			newCapitalInv.setCapInvAppraisalICP(existproj.getCapInvAppraisalICP());
			newCapitalInv.setCapInvCutoffDateICP(existproj.getCapInvCutoffDateICP());
			newCapitalInv.setCapInvCommProdICP(existproj.getCapInvCommProdICP());
			newCapitalInv.setCapInvAddlInvICP(existproj.getCapInvAddlInvICP());
			newCapitalInv.setCapInvActualInvICP(existproj.getCapInvActualInvICP());
			newCapitalInv.setCapInvTotalICP(existproj.getCapInvTotalICP());

			newCapitalInv.setCapInvDPRPPE(existproj.getCapInvDPRPPE());
			newCapitalInv.setCapInvAppraisalPPE(existproj.getCapInvAppraisalPPE());
			newCapitalInv.setCapInvCutoffDatePPE(existproj.getCapInvCutoffDatePPE());
			newCapitalInv.setCapInvCommProdPPE(existproj.getCapInvCommProdPPE());
			newCapitalInv.setCapInvAddlInvPPE(existproj.getCapInvAddlInvPPE());
			newCapitalInv.setCapInvActualInvPPE(existproj.getCapInvActualInvPPE());
			newCapitalInv.setCapInvTotalPPE(existproj.getCapInvTotalPPE());
			newCapitalInv.setLandPurchaseFrUPSIDC(existproj.getLandPurchaseFrUPSIDC());

			newCapitalInv.setCapInvCreatedBy("Disbursement User");
			// newCapitalInv.setCapInvCreatedDate(new Date());
			newCapitalInv.setCapInvModifiyDate(new Date());
			capInvService.saveCapialInvest(newCapitalInv);

			logger.info("saveCapialInvestDetails method end");
		}
	}

	@Override
	public void saveCapialInvest(CapitalInvestmentDetails capitalInvestment) {
		capInvRepository.save(capitalInvestment);
	}

	@Override
	public CapitalInvestmentDetails getDetailsByprojDisId(String id) {

		return capInvRepository.getDetailsBycapInvId(id);
	}

	@Override
	public CapitalInvestmentDetails getDetailsBycapInvApcId(String appid) {
		
		return capInvRepository.getDetailsBycapInvApcId(appid);
	}

}
