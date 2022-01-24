package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.CapitalInvestmentDocument;
import com.webapp.ims.dis.model.DIS_PhaseWiseInvestmentDetails;
import com.webapp.ims.dis.model.DisEmploymentDetails;
import com.webapp.ims.dis.model.DissbursmentApplicantDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.repository.CapitalInvestDocumentRepository;
import com.webapp.ims.dis.repository.CapitalInvestRepository;
import com.webapp.ims.dis.repository.DISPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.CapitalInvestService;
import com.webapp.ims.dis.service.CapitalInvestmentDocumentService;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;
import com.webapp.ims.dis.service.DisbursmentEmploymentDetailsService;
import com.webapp.ims.dis.service.NewProjDisburseService;
import com.webapp.ims.dis.service.ProjDisburseService;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;

@Controller
public class DisIncentiveController {

	private final Logger logger = LoggerFactory.getLogger(DisIncentiveController.class);

	@Autowired
	private ProjDisburseService projDisburseService;
	@Autowired
	private NewProjDisburseService newProjDisburseService;
	@Autowired
	private CapitalInvestService capInvService;

	@Autowired
	CapitalInvestRepository capitalInvestRepository;

	@Autowired
	private ProjDisburseRepository projDisburseRepository;

	@Autowired
	CapitalInvestDocumentRepository capInvRepoitory;

	@Autowired
	CapitalInvestmentDocumentService capInvDocService;

	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;

	@Autowired
	private InvestmentDetailsRepository invRepository;

	@Autowired
	PhaseWiseInvestmentDetailsService pwInvestDs;

	@Autowired
	PhaseWiseInvestmentDetailsRepository pwRepository;

	@Autowired
	DISPhaseWiseInvestmentDetailsRepository dISPhaseWiseInvestmentDetailsRepository;

	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository dptPWResos;
	
	@Autowired
	DisbursmentApplicantDetailsService disApplicantService;
	
	@Autowired
	CapitalInvestService capitalInvestService;
	
	@Autowired
	DisbursmentEmploymentDetailsService disbursmentEmploymentDetailsService;

	@GetMapping(value = "/disincentive")
	public ModelAndView disincentive() {
		return new ModelAndView("/Disbursement/disincentive");
	}

	@GetMapping(value = "/disbIncentiveCapInv")
	public ModelAndView disbIncentiveCapInv(
			@ModelAttribute("existProjDisbursement") ExistProjDisbursement existprojDisbursement2, String appId,
			Model model, HttpSession session) {

		String applId = (String) session.getAttribute("appId");
		InvestmentDetails investDetails = invRepository.findByApplId(applId);
		String applyStatus = investDetails.getPwApply();

		System.out.println(applyStatus);
		model.addAttribute("applyStatus", applyStatus);
		

		try {
			appId = (String) session.getAttribute("appId");

			String appStr = appId.substring(0, appId.length() - 2);
			String cisInvId = appStr + "CI";
			String projInvId = appStr + "DSB";
			String capInvId = appStr + "A1CAPINV";

			CapitalInvestmentDetails a = capitalInvestRepository.getDetailsBycapInvId(cisInvId);

			NewProjDisbursement newProjDisbursement = newProjDisburseRepository.getDetailsBynewprojApcId(appId);
			if (a != null) {

				

				existprojDisbursement2 = projDisburseRepository.getDetailsByprojDisId(projInvId);

				if (existprojDisbursement2 != null) {
				
					existprojDisbursement2.setAnnualTurnOver(a.getAnnualTurnOver());
					existprojDisbursement2.setTtlCostDpr(a.getTtlCostDpr());
					existprojDisbursement2.setTtlcostProAppFrBank(a.getTtlcostProAppFrBank());
					existprojDisbursement2.setTtlBeforeCutOff(a.getTtlBeforeCutOff());
					existprojDisbursement2.setTtlCutOffCommProduction(a.getTtlCutOffCommProduction());
					existprojDisbursement2.setTtlActualInvestment(a.getTtlActualInvestment());
					existprojDisbursement2.setTtlAddiActualInvestment(a.getTtlAddiActualInvestment());
					existprojDisbursement2.setDisValueTotalOfTotal(a.getDisValueTotalOfTotal());
					
					
					existprojDisbursement2.setCapInvActualInvLC(a.getCapInvActualInvLC());
					existprojDisbursement2.setCapInvAppraisalLC(a.getCapInvAppraisalLC());
					existprojDisbursement2.setCapInvDPRLC(a.getCapInvDPRLC());
					existprojDisbursement2.setCapInvCommProdLC(a.getCapInvCommProdLC());
					existprojDisbursement2.setCapInvCutoffDateLC(a.getCapInvCutoffDateLC());
					existprojDisbursement2.setCapInvTotalLC(a.getCapInvTotalLC());
					existprojDisbursement2.setCapInvAddlInvLC(a.getCapInvAddlInvLC());

					existprojDisbursement2.setCapInvActualInvBC(a.getCapInvActualInvBC());
					existprojDisbursement2.setCapInvAppraisalBC(a.getCapInvAppraisalBC());
					existprojDisbursement2.setCapInvDPRBC(a.getCapInvDPRBC());
					existprojDisbursement2.setCapInvCommProdBC(a.getCapInvCommProdBC());
					existprojDisbursement2.setCapInvCutoffDateBC(a.getCapInvCutoffDateBC());
					existprojDisbursement2.setCapInvTotalBC(a.getCapInvTotalBC());
					existprojDisbursement2.setCapInvAddlInvBC(a.getCapInvAddlInvBC());

					existprojDisbursement2.setCapInvActualInvPMC(a.getCapInvActualInvPMC());
					existprojDisbursement2.setCapInvAppraisalPMC(a.getCapInvAppraisalPMC());
					existprojDisbursement2.setCapInvDPRPMC(a.getCapInvDPRPMC());
					existprojDisbursement2.setCapInvCommProdPMC(a.getCapInvCommProdPMC());
					existprojDisbursement2.setCapInvCutoffDatePMC(a.getCapInvCutoffDatePMC());
					existprojDisbursement2.setCapInvTotalPMC(a.getCapInvTotalPMC());
					existprojDisbursement2.setCapInvAddlInvPMC(a.getCapInvAddlInvPMC());

					existprojDisbursement2.setCapInvActualInvMFA(a.getCapInvActualInvMFA());
					existprojDisbursement2.setCapInvAppraisalMFA(a.getCapInvAppraisalMFA());
					existprojDisbursement2.setCapInvDPRMFA(a.getCapInvDPRMFA());
					existprojDisbursement2.setCapInvCommProdMFA(a.getCapInvCommProdMFA());
					existprojDisbursement2.setCapInvCutoffDateMFA(a.getCapInvCutoffDateMFA());
					existprojDisbursement2.setCapInvTotalMFA(a.getCapInvTotalMFA());
					existprojDisbursement2.setCapInvAddlInvMFA(a.getCapInvAddlInvMFA());

					existprojDisbursement2.setCapInvActualInvTKF(a.getCapInvActualInvTKF());
					existprojDisbursement2.setCapInvAppraisalTKF(a.getCapInvAppraisalTKF());
					existprojDisbursement2.setCapInvDPRTKF(a.getCapInvDPRTKF());
					existprojDisbursement2.setCapInvCommProdTKF(a.getCapInvCommProdTKF());
					existprojDisbursement2.setCapInvCutoffDateTKF(a.getCapInvCutoffDateTKF());
					existprojDisbursement2.setCapInvTotalTKF(a.getCapInvTotalTKF());
					existprojDisbursement2.setCapInvAddlInvTKF(a.getCapInvAddlInvTKF());

					existprojDisbursement2.setCapInvActualInvICP(a.getCapInvActualInvICP());
					existprojDisbursement2.setCapInvAppraisalICP(a.getCapInvAppraisalICP());
					existprojDisbursement2.setCapInvDPRICP(a.getCapInvDPRICP());
					existprojDisbursement2.setCapInvCommProdICP(a.getCapInvCommProdICP());
					existprojDisbursement2.setCapInvCutoffDateICP(a.getCapInvCutoffDateICP());
					existprojDisbursement2.setCapInvTotalICP(a.getCapInvTotalICP());
					existprojDisbursement2.setCapInvAddlInvICP(a.getCapInvAddlInvICP());

					existprojDisbursement2.setCapInvActualInvPPE(a.getCapInvActualInvPPE());
					existprojDisbursement2.setCapInvAppraisalPPE(a.getCapInvAppraisalPPE());
					existprojDisbursement2.setCapInvDPRPPE(a.getCapInvDPRPPE());
					existprojDisbursement2.setCapInvCommProdPPE(a.getCapInvCommProdPPE());
					existprojDisbursement2.setCapInvCutoffDatePPE(a.getCapInvCutoffDatePPE());
					existprojDisbursement2.setCapInvTotalPPE(a.getCapInvTotalPPE());
					existprojDisbursement2.setCapInvAddlInvPPE(a.getCapInvAddlInvPPE());

					existprojDisbursement2.setCapInvActualInvMMC(a.getCapInvActualInvMMC());
					existprojDisbursement2.setCapInvAppraisalMMC(a.getCapInvAppraisalMMC());
					existprojDisbursement2.setCapInvDPRMMC(a.getCapInvDPRMMC());
					existprojDisbursement2.setCapInvCommProdMMC(a.getCapInvCommProdMMC());
					existprojDisbursement2.setCapInvCutoffDateMMC(a.getCapInvCutoffDateMMC());
					existprojDisbursement2.setCapInvTotalMMC(a.getCapInvTotalMMC());
					existprojDisbursement2.setCapInvAddlInvMMC(a.getCapInvAddlInvMMC());
					existprojDisbursement2.setLandPurchaseFrUPSIDC(a.getLandPurchaseFrUPSIDC());

					
					projDisburseService.disbIncentiveCapInv(existprojDisbursement2, model, session);

					existprojDisbursement2.setNewprojInfra(newProjDisbursement.getNewprojInfra());

					CapInvDocFromMongoDB(existprojDisbursement2, capInvId);

					List<PhaseWiseInvestmentDetails> pwInvDetailsList = pwRepository.findByPwApcId(appId);
					model.addAttribute("pwInvDetailsList", pwInvDetailsList);
					model.addAttribute("existProjDisbursement", existprojDisbursement2);
					model.addAttribute("radioBtnStatus",a.getLandPurchaseFrUPSIDC());
				
				}
			} else {
				projDisburseService.disbIncentiveCapInv(existprojDisbursement2, model, session);
				model.addAttribute("existProjDisbursement", existprojDisbursement2);

			}

		} catch (Exception e) {
			logger.error(String.format("#### disbIncentiveCapInv exception $$$ %s", e.getMessage()));
		}
		
		DissbursmentApplicantDetails applicant = disApplicantService.getDetailsBydisAppId(appId);
		CapitalInvestmentDetails capi = capitalInvestService.getDetailsBycapInvApcId(appId);
		DisEmploymentDetails empappdis = disbursmentEmploymentDetailsService.getDetailsBydisAppId(appId);
		try {
			String applicantID = applicant.getDisAppId();
			if(applicantID != null) {
				model.addAttribute("applicantID", applicantID);
			}
		} catch (Exception e) {}
		
		try {
			String capiapp = capi.getCapInvApcId();
			if(capiapp != null) {
				model.addAttribute("capiapp", capiapp);
			}
			
		} catch (Exception e) {
			
		}
		try {
			 String empapp = empappdis.getDisAppId();
			if(empapp != null) {
				model.addAttribute("empapp", empapp);
			}
			
		} catch (Exception e) {
			
		}


		return new ModelAndView("/Disbursement/discapitalinvestment");
	}

	@PostMapping(value = "/saveIncentiveDisburse")
	public ModelAndView saveIncentiveDisburse(
			@Validated @ModelAttribute("existProjDisbursement") ExistProjDisbursement existprojDisbursement,
			@RequestParam("statutoryAuditorDoc") MultipartFile statutoryAuditorDocFile,
			@RequestParam("purchasePriceDoc") MultipartFile purchasePriceDocFile,
			@RequestParam("stampDutyDoc") MultipartFile stampDutyDocFile,
			@RequestParam("registrationFeeDoc") MultipartFile registrationFeeDocFile,
			@RequestParam("banksAuctionDoc") MultipartFile banksAuctionDocFile,
			@RequestParam("civilWorksDoc") MultipartFile civilWorksDocFile,
			@RequestParam("machineryMiscDoc") MultipartFile machineryMiscDocFile,
			@RequestParam("encCertificateFName") MultipartFile encCertificateFNamefiles,

			BindingResult result, Model model, HttpSession session) {

		try {

			String appId = (String) session.getAttribute("appId");

			String appStr = appId.substring(0, appId.length() - 2);
			String investId = appStr + "I1";
			String capInvId = appId + "CAPINV";

			List<CapitalInvestmentDocument> multipartFileList = new LinkedList<>();

			if ((!statutoryAuditorDocFile.getOriginalFilename().isEmpty()
					&& statutoryAuditorDocFile.getOriginalFilename() != null)
					&& statutoryAuditorDocFile.getBytes() != null) {

				String fileName = statutoryAuditorDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						statutoryAuditorDocFile.getOriginalFilename(), statutoryAuditorDocFile.getContentType(),
						statutoryAuditorDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("statutoryAuditorDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}
			if ((!purchasePriceDocFile.getOriginalFilename().isEmpty()
					&& purchasePriceDocFile.getOriginalFilename() != null) && purchasePriceDocFile.getBytes() != null) {
				String fileName = purchasePriceDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						purchasePriceDocFile.getOriginalFilename(), purchasePriceDocFile.getContentType(),
						purchasePriceDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("purchasePriceDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!stampDutyDocFile.getOriginalFilename().isEmpty() && stampDutyDocFile.getOriginalFilename() != null)
					&& stampDutyDocFile.getBytes() != null) {
				String fileName = stampDutyDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(stampDutyDocFile.getOriginalFilename(),
						stampDutyDocFile.getContentType(), stampDutyDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("stampDutyDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!registrationFeeDocFile.getOriginalFilename().isEmpty()
					&& registrationFeeDocFile.getOriginalFilename() != null)
					&& registrationFeeDocFile.getBytes() != null) {
				String fileName = registrationFeeDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						registrationFeeDocFile.getOriginalFilename(), registrationFeeDocFile.getContentType(),
						registrationFeeDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("registrationFeeDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!banksAuctionDocFile.getOriginalFilename().isEmpty()
					&& banksAuctionDocFile.getOriginalFilename() != null) && banksAuctionDocFile.getBytes() != null) {
				String fileName = banksAuctionDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						banksAuctionDocFile.getOriginalFilename(), banksAuctionDocFile.getContentType(),
						banksAuctionDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("banksAuctionDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!civilWorksDocFile.getOriginalFilename().isEmpty() && civilWorksDocFile.getOriginalFilename() != null)
					&& civilWorksDocFile.getBytes() != null) {
				String fileName = civilWorksDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						civilWorksDocFile.getOriginalFilename(), civilWorksDocFile.getContentType(),
						civilWorksDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("civilWorksDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!machineryMiscDocFile.getOriginalFilename().isEmpty()
					&& machineryMiscDocFile.getOriginalFilename() != null) && machineryMiscDocFile.getBytes() != null) {
				String fileName = machineryMiscDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						machineryMiscDocFile.getOriginalFilename(), machineryMiscDocFile.getContentType(),
						machineryMiscDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("machineryMiscDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!encCertificateFNamefiles.getOriginalFilename().isEmpty()
					&& encCertificateFNamefiles.getOriginalFilename() != null)
					&& encCertificateFNamefiles.getBytes() != null) {
				String fileName = encCertificateFNamefiles.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(
						encCertificateFNamefiles.getOriginalFilename(), encCertificateFNamefiles.getContentType(),
						encCertificateFNamefiles.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("encCertificateFName");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			// For the saving of docs in MONgo DB
			capInvDocService.saveAndUpdateMultipleFiles(multipartFileList, session);

			projDisburseService.saveIncentiveDisburse(existprojDisbursement, result, model, session);
			newProjDisburseService.saveNewProjIncDisburse(existprojDisbursement, result, model, session);
			capInvService.saveCapialInvestDetails(existprojDisbursement, result, model, session);

			
		} catch (Exception e) {
			logger.error(String.format("#### saveIncentiveDisburse exception $$$ %s", e.getMessage()));
		}
		
		return new ModelAndView("redirect:/disEmploymentDetails");
	}

	private void CapInvDocFromMongoDB(ExistProjDisbursement projDisbursement, String capInvId) {

		try {
			List<CapitalInvestmentDocument> capInvDocList = capInvRepoitory.getListBycapInvId(capInvId);
			if (capInvDocList != null) {

				for (int i = 0; i < capInvDocList.size(); i++) {
					CapitalInvestmentDocument capInvDoc = capInvDocList.get(i);
					if (capInvDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String statutoryAuditorDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setStatutoryAuditorDocbase64File(statutoryAuditorDocbase64Encoded);
							projDisbursement.setStatutoryAuditorDoc(capInvDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String purchasePriceDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setPurchasePriceDocbase64File(purchasePriceDocbase64Encoded);
							projDisbursement.setPurchasePriceDoc(capInvDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String stampDutyDocbase64FileEncoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setStampDutyDocbase64File(stampDutyDocbase64FileEncoded);
							projDisbursement.setStampDutyDoc(capInvDoc.getFileName());
						}

						if (i == 3) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String registrationFeeDocbase64FileEncoded = new String(encodeBase64,
									StandardCharsets.UTF_8);
							projDisbursement.setRegistrationFeeDocbase64File(registrationFeeDocbase64FileEncoded);
							projDisbursement.setRegistrationFeeDoc(capInvDoc.getFileName());
						}

						if (i == 4) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String banksAuctionDocbase64FileEncoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setBanksAuctionDocbase64File(banksAuctionDocbase64FileEncoded);
							projDisbursement.setBanksAuctionDoc(capInvDoc.getFileName());
						}

						if (i == 5) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String civilWorksDocbase64FileEncoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setCivilWorksDocbase64File(civilWorksDocbase64FileEncoded);
							projDisbursement.setCivilWorksDoc(capInvDoc.getFileName());
						}

						if (i == 6) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String machineryMiscDocbase64FileEncoded = new String(encodeBase64, StandardCharsets.UTF_8);
							projDisbursement.setMachineryMiscDocbase64File(machineryMiscDocbase64FileEncoded);
							projDisbursement.setMachineryMiscDoc(capInvDoc.getFileName());
						}

						if (i == 7) {
							byte[] encodeBase64 = Base64.encodeBase64(capInvDoc.getData());
							String encCertificateFNamebase64FileEncoded = new String(encodeBase64,
									StandardCharsets.UTF_8);
							projDisbursement.setEncCertificateFNamebase64File(encCertificateFNamebase64FileEncoded);
							projDisbursement.setEncCertificateFName(capInvDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Reimbursment Document $$$$ %s", e.getMessage()));
		}

	}

}
