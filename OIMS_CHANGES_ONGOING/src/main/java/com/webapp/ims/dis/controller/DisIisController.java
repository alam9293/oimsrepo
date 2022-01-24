/**
 * Author:: Sachin
* Created on:: 
 */
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.IISDocument;
import com.webapp.ims.dis.repository.IISDocumentRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.DisbursmentIisService;
import com.webapp.ims.dis.service.IISDocumentService;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class DisIisController {

	@Autowired
	DisbursmentIisService disbursmentIisService;

	@Autowired
	IISDocumentService iisService;

	@Autowired
	ProjDisburseRepository projDisburseRepository;
	
	@Autowired
	IISDocumentRepository iisRepository;
	
	@Autowired AdditionalInterest  additionalInterest;

	private final Logger logger = LoggerFactory.getLogger(DisIisController.class);

	@GetMapping("/disiis")
	public ModelAndView showIIS(@ModelAttribute("iisincentiveDeatilsForm") @Validated Disiis disiis, Model model,
			HttpSession session) {
		logger.info("method ShowIIS Start");
		String iisid = null;
		// Disiis disiis2 = disbursmentIisService.getDiscisById(iisid);
		try {

			String appId = (String) session.getAttribute("appId");

			iisid = appId + "IIS";
			Disiis disiis2 = disbursmentIisService.getDisiisBydisiisId(iisid);
			if (disiis2 != null) {
				iisDocFromMongoDB(disiis2, iisid);
				model.addAttribute("iisincentiveDeatilsForm", disiis2);
			} else {
				model.addAttribute("iisincentiveDeatilsForm", disiis);
			}
			ExistProjDisbursement existproject = projDisburseRepository.getDetailsByprojApcId(appId);
			model.addAttribute("totalLoan", existproject.getTotalLoan());
			model.addAttribute("totalInt", existproject.getTotalInterest());
			long eligibleFixedCapitalInvestment = additionalInterest.getDISEligibleFixedCapitalInvestment(appId);
			 long infra = additionalInterest.getDISInfra(appId);
			double propnateValue = Math.abs(infra / eligibleFixedCapitalInvestment);
			
			model.addAttribute("propnateValue", propnateValue);
			model.addAttribute("eligibleFixedCapitalInvestment", eligibleFixedCapitalInvestment);
			model.addAttribute("infra", infra);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("method ShowIIS ended");
		return new ModelAndView("/Disbursement/disiis");

	}

	@PostMapping("/disIisSave")
	public ModelAndView iisSave(@ModelAttribute("iisincentiveDeatilsForm") Disiis disiis,
			@RequestParam("sectionletter") MultipartFile sectionletterDocFile,
			@RequestParam("certifyingLoan") MultipartFile certifyingLoanDocFile,
			@RequestParam("auditedAccounts") MultipartFile auditedAccountsDocFile,
			@RequestParam("fiBankCertificate") MultipartFile fiBankCertificateDocFile, Model model, HttpSession session,
			String appId) {

		logger.info(" Start IIS apply Save Method iisSave ");

		try {
			appId = (String) session.getAttribute("appId");

			String iisId = appId + "IIS";

			try {
				List<IISDocument> multipartFileList = new LinkedList<>();

				if ((!sectionletterDocFile.getOriginalFilename().isEmpty()
						&& sectionletterDocFile.getOriginalFilename() != null)
						&& sectionletterDocFile.getBytes() != null) {

					String fileName = sectionletterDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					IISDocument dbFile = new IISDocument(sectionletterDocFile.getOriginalFilename(),
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

					IISDocument dbFile = new IISDocument(certifyingLoanDocFile.getOriginalFilename(),
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

					IISDocument dbFile = new IISDocument(auditedAccountsDocFile.getOriginalFilename(),
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

					IISDocument dbFile = new IISDocument(fiBankCertificateDocFile.getOriginalFilename(),
							fiBankCertificateDocFile.getContentType(), fiBankCertificateDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("fiBankCertificate");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);
				}

				// For the saving of docs in MONgo DB iisService
				iisService.saveAndUpdateMultipleFiles(multipartFileList, session);

			} catch (Exception e) {
				logger.error(String.format("***** Reimbursment Document exception  **** %s", e.getMessage()));
			}

			disiis.setDisiisId(iisId);
			disiis.setDisAppId(appId);
			disiis.setStatus("active");
			disiis.setCreateBy("Admin");
			disiis.setCreateDate(new Date());
			disiis.setModifiyDate(new Date());
			disiis.setModifiedBy(appId);

			disbursmentIisService.saveDisbursmentCis(disiis);

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("iisincentiveDeatilsForm", disiis);

		logger.info(" End IIS Save Method");
		return new ModelAndView("redirect:/disincentivetype");

	}

	private void iisDocFromMongoDB(Disiis disiis, String iisId) {

		try {
			List<IISDocument> iisDocList = iisRepository.getListBydisIISId(iisId);
			if (iisDocList != null) {

				for (int i = 0; i < iisDocList.size(); i++) {
					IISDocument iisDoc = iisDocList.get(i);
					if (iisDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(iisDoc.getData());
							String sectionletterDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disiis.setSectionletterDocbase64File(sectionletterDocbase64Encoded);
							disiis.setSectionletter(iisDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(iisDoc.getData());
							String certifyingLoanDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disiis.setCertifyingLoanDocbase64File(certifyingLoanDocbase64Encoded);
							disiis.setCertifyingLoan(iisDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(iisDoc.getData());
							String auditedAccountsDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disiis.setAuditedAccountsDocbase64File(auditedAccountsDocbase64Encoded);
							disiis.setAuditedAccounts(iisDoc.getFileName());
						}

						if (i == 3) {
							byte[] encodeBase64 = Base64.encodeBase64(iisDoc.getData());
							String fiBankCertificateDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disiis.setFiBankCertificateDocbase64File(fiBankCertificateDocbase64Encoded);
							disiis.setFiBankCertificate(iisDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Reimbursment Document $$$$ %s", e.getMessage()));
		}

	}

}
