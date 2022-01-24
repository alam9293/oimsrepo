/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.EXISTING_PROJECT;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.webapp.ims.dis.model.CISDocument;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.repository.CISDocumentRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.CISDocumentService;
import com.webapp.ims.dis.service.DisbursmentCisService;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class DisCisController {

	@Autowired
	DisbursmentCisService disbursmentCisService;

	@Autowired
	CISDocumentService cisService;

	@Autowired
	CISDocumentRepository cisRepository;
	
	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;

	@Autowired
	ProjDisburseRepository projDisburseRepository;
	
	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;
	@Autowired
	AdditionalInterest additionalInterest;
	
	private final Logger logger = LoggerFactory.getLogger(DisLoginController.class);

	@GetMapping("/disCis")
	public ModelAndView showCIS(@ModelAttribute("cisincentiveDeatilsForm") Discis discis, Model model,
			HttpSession session) {

		try {
			String appId = (String) session.getAttribute("appId");
			String cisid = appId + "CIS";

			Discis discis1 = disbursmentCisService.getDiscisBydiscisId(cisid);
			
			if (discis1 != null) {

				cisDocFromMongoDB(discis1, cisid);
				model.addAttribute("cisincentiveDeatilsForm", discis1);
			}

			else {
				model.addAttribute("cisincentiveDeatilsForm", discis);
				long noPnm = additionalInterest.getDISPlantnMachineAmount(appId);
				 model.addAttribute("noPnM", noPnm);
			}
			
			
			EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
					.getEvalProjDetByapplicantDetailId(appId);
			NewProjDisbursement newproject = newProjDisburseRepository.getDetailsBynewprojApcId(appId);
			ExistProjDisbursement existproject = projDisburseRepository.getDetailsByprojApcId(appId);
			
			 
			if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
				model.addAttribute("newtotal", newproject.getTotal());
				model.addAttribute("newpnm", existproject.getProjDisPlantMachCost());
				model.addAttribute("newinfra", existproject.getProjDisInfra());
				/* if (!expension.isEmpty() && !diversification.isEmpty()) { */

			} else {
				model.addAttribute("newtotal", newproject.getTotal());
				model.addAttribute("newpnm", newproject.getNewprojPlantMachCost());
				model.addAttribute("newinfra", newproject.getNewprojInfra());
			}
			 long eligibleFixedCapitalInvestment = additionalInterest.getDISEligibleFixedCapitalInvestment(appId);
			 model.addAttribute("eligibleFixedCapitalInvestment", eligibleFixedCapitalInvestment);
			 model.addAttribute("totalLoan", existproject.getTotalLoan());
			model.addAttribute("totalInt", existproject.getTotalInterest());
			long noPnm = additionalInterest.getDISPlantnMachineAmount(appId);
			
			 if(discis1.getBankcert().equals("No") || discis1.getBankcert().isEmpty() ) {
				 model.addAttribute("noPnM", noPnm);
				
			} else {
				noPnm = discis1.getPnmloan();
				 model.addAttribute("noPnM", noPnm);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("method ShowCIS ended");
		return new ModelAndView("/Disbursement/discis");

	}

	@PostMapping("/disCisSave")
	public ModelAndView cisSave(@ModelAttribute("cisincentiveDeatilsForm") Discis discis,
			@RequestParam("sectionletter") MultipartFile sectionletterDocFile,
			@RequestParam("certifyingLoan") MultipartFile certifyingLoanDocFile,
			@RequestParam("auditedAccounts") MultipartFile auditedAccountsDocFile,
			@RequestParam("fiBankCertificate") MultipartFile fiBankCertificateDocFile, Model model, HttpSession session,
			String appId) {

		logger.info(" Start CIS apply Save Method cisSave ");

		try {
			appId = (String) session.getAttribute("appId");
			String cisId = appId + "CIS";

			try {
				List<CISDocument> multipartFileList = new LinkedList<CISDocument>();

				if ((!sectionletterDocFile.getOriginalFilename().isEmpty()
						&& sectionletterDocFile.getOriginalFilename() != null)
						&& sectionletterDocFile.getBytes() != null) {

					String fileName = sectionletterDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					CISDocument dbFile = new CISDocument(sectionletterDocFile.getOriginalFilename(),
							sectionletterDocFile.getContentType(), sectionletterDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("sectionletter");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);
				}
				if ((!certifyingLoanDocFile.getOriginalFilename().isEmpty()
						&& certifyingLoanDocFile.getOriginalFilename() != null)
						&& certifyingLoanDocFile.getBytes() != null) {

					String fileName = certifyingLoanDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					CISDocument dbFile = new CISDocument(certifyingLoanDocFile.getOriginalFilename(),
							certifyingLoanDocFile.getContentType(), certifyingLoanDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("certifyingLoan");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);
				}

				if ((!auditedAccountsDocFile.getOriginalFilename().isEmpty()
						&& auditedAccountsDocFile.getOriginalFilename() != null)
						&& auditedAccountsDocFile.getBytes() != null) {

					String fileName = auditedAccountsDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					CISDocument dbFile = new CISDocument(auditedAccountsDocFile.getOriginalFilename(),
							auditedAccountsDocFile.getContentType(), auditedAccountsDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("auditedAccounts");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);

				}

				if ((!fiBankCertificateDocFile.getOriginalFilename().isEmpty()
						&& fiBankCertificateDocFile.getOriginalFilename() != null)
						&& fiBankCertificateDocFile.getBytes() != null) {

					String fileName = fiBankCertificateDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					CISDocument dbFile = new CISDocument(fiBankCertificateDocFile.getOriginalFilename(),
							fiBankCertificateDocFile.getContentType(), fiBankCertificateDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("fiBankCertificate");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);

				}

				// For the saving of docs in MONgo DB // cisService
				cisService.saveAndUpdateMultipleFiles(multipartFileList, session);

			} catch (Exception e) {
				logger.error(String.format("***** Reimbursment Document exception  **** %s", e.getMessage()));
			}

			discis.setDiscisId(cisId);
			discis.setDisAppId(appId);
			discis.setStatus("active");
			discis.setCreateBy("Admin");
			discis.setCreateDate(new Date());
			discis.setModifiyDate(new Date());
			discis.setModifiedBy(appId);

			disbursmentCisService.saveDisbursmentCis(discis);

			cisDocFromMongoDB(discis, cisId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("cisincentiveDeatilsForm", discis);

		logger.info(" End CIS Save Method");
		return new ModelAndView("redirect:/disincentivetype");

	}

	private void cisDocFromMongoDB(Discis discis, String cisId) {

		try {
			List<CISDocument> cisDocList = cisRepository.getListBydisCisId(cisId);
			if (cisDocList != null) {

				for (int i = 0; i < cisDocList.size(); i++) {
					CISDocument cisDoc = cisDocList.get(i);
					if (cisDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(cisDoc.getData());
							String sectionletterDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							discis.setSectionletterDocbase64File(sectionletterDocbase64Encoded);
							discis.setSectionletter(cisDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(cisDoc.getData());
							String certifyingLoanDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							discis.setCertifyingLoanDocbase64File(certifyingLoanDocbase64Encoded);
							discis.setCertifyingLoan(cisDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(cisDoc.getData());
							String auditedAccountsDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							discis.setAuditedAccountsDocbase64File(auditedAccountsDocbase64Encoded);
							discis.setAuditedAccounts(cisDoc.getFileName());
						}

						if (i == 3) {
							byte[] encodeBase64 = Base64.encodeBase64(cisDoc.getData());
							String fiBankCertificateDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							discis.setFiBankCertificateDocbase64File(fiBankCertificateDocbase64Encoded);
							discis.setFiBankCertificate(cisDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Reimbursment Document $$$$ %s", e.getMessage()));
		}

	}

}
