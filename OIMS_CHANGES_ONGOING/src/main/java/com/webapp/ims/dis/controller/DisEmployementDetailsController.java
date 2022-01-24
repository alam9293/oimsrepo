/**
 * Author:: Pankaj
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
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

import com.webapp.ims.dis.model.DisEmploymentDetails;
import com.webapp.ims.dis.model.EmploymentdetailsDocument;
import com.webapp.ims.dis.repository.EmploymentDetailsDocumentRepository;
import com.webapp.ims.dis.service.DisbursmentEmploymentDetailsService;
import com.webapp.ims.dis.service.EmployementDetailsDocumentService;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.service.BusinessEntityDetailsService;

@Controller
public class DisEmployementDetailsController {

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	DisbursmentEmploymentDetailsService disEmpService;

	@Autowired
	EmployementDetailsDocumentService empDetlDocService;

	@Autowired
	EmploymentDetailsDocumentRepository emplDocRepos;

	private final Logger logger = LoggerFactory.getLogger(DisLoginController.class);

	@GetMapping("/disEmploymentDetails")
	public ModelAndView disEmploymentDetails(
			@ModelAttribute("employmentDetailsForm") DisEmploymentDetails employmentDetails, Model model,
			HttpSession session) {
		logger.info("Start disEmploymentDetails  Get Method");
		try {
			String appId = (String) session.getAttribute("appId");
			String empId = appId + "EMP";
			businessService.commonDetails(appId, session, model);
			DocFromMongoDB(employmentDetails, empId);
			model.addAttribute("employmentDetailsForm", employmentDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("End disEmploymentDetails  Get Method");
		return new ModelAndView("/Disbursement/disEmplymentDetails");

	}

	@PostMapping("/disEmploymentDetailsSave")
	public ModelAndView disEmploymentDetailsSave(
			@ModelAttribute("employmentDetailsForm") DisEmploymentDetails employmentDetails,
			@RequestParam("noBPLEmplDoc") MultipartFile noBPLEmplDocFile,
			@RequestParam("noSCEmplDoc") MultipartFile noSCEmplDocFile,
			@RequestParam("noSTEmplDoc") MultipartFile noSTEmplDocFile,
			@RequestParam("noFemaleEmplDoc") MultipartFile noFemaleEmplDocFile, Model model, HttpSession session,
			String appId) {

		logger.info("Start disEmploymentDetails  Post Method");

		try {
			appId = (String) session.getAttribute("appId");
			String empId = appId + "EMP";

			List<EmploymentdetailsDocument> multipartFileList = new ArrayList<EmploymentdetailsDocument>();

			if ((!noBPLEmplDocFile.getOriginalFilename().isEmpty() && noBPLEmplDocFile.getOriginalFilename() != null)
					&& noBPLEmplDocFile.getBytes() != null) {
				String fileName = noBPLEmplDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(noBPLEmplDocFile.getOriginalFilename(),
						noBPLEmplDocFile.getContentType(), noBPLEmplDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("noBPLEmplDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());

				multipartFileList.add(dbFile);
			}
			if ((!noSCEmplDocFile.getOriginalFilename().isEmpty() && noSCEmplDocFile.getOriginalFilename() != null)
					&& noSCEmplDocFile.getBytes() != null) {

				String fileName = noSCEmplDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(noSCEmplDocFile.getOriginalFilename(),
						noSCEmplDocFile.getContentType(), noSCEmplDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("noSCEmplDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());

				multipartFileList.add(dbFile);
			}

			if ((!noSTEmplDocFile.getOriginalFilename().isEmpty() && noSTEmplDocFile.getOriginalFilename() != null)
					&& noSTEmplDocFile.getBytes() != null) {

				String fileName = noSTEmplDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(noSTEmplDocFile.getOriginalFilename(),
						noSTEmplDocFile.getContentType(), noSTEmplDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("noSTEmplDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());

				multipartFileList.add(dbFile);
			}

			if ((!noFemaleEmplDocFile.getOriginalFilename().isEmpty()
					&& noFemaleEmplDocFile.getOriginalFilename() != null) && noFemaleEmplDocFile.getBytes() != null) {

				String fileName = noFemaleEmplDocFile.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(
						noFemaleEmplDocFile.getOriginalFilename(), noFemaleEmplDocFile.getContentType(),
						noFemaleEmplDocFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("noFemaleEmplDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());

				multipartFileList.add(dbFile);
			}

			// For the saving of docs in MONgo DB
			empDetlDocService.saveAndUpdateMultipleFiles(multipartFileList, session);

			employmentDetails.setDisId(empId);
			employmentDetails.setDisAppId(appId);
			employmentDetails.setCreateBy("Admin");
			employmentDetails.setCreateDate(new Date());
			employmentDetails.setModifiyDate(new Date());
			employmentDetails.setModifiedBy(appId);

			disEmpService.saveDisEmploymentDetails(employmentDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("employmentDetailsForm", employmentDetails);

		logger.info("End DisEmploymentDetails  Post Method");
		return new ModelAndView("redirect:/disincentivetype");

	}

	private void DocFromMongoDB(DisEmploymentDetails employmentDetails, String empId) {

		try {
			List<EmploymentdetailsDocument> empDetlDocList = emplDocRepos.getListByemplDtlId(empId);
			if (empDetlDocList != null) {

				for (int i = 0; i < empDetlDocList.size(); i++) {
					EmploymentdetailsDocument empDetlDoc = empDetlDocList.get(i);
					if (empDetlDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(empDetlDoc.getData());
							String noBPLEmplDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							employmentDetails.setNoBPLEmplDocbase64File(noBPLEmplDocbase64Encoded);
							employmentDetails.setNoBPLEmplDoc(empDetlDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(empDetlDoc.getData());
							String noSCEmplDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							employmentDetails.setNoSCEmplDocbase64File(noSCEmplDocbase64Encoded);
							employmentDetails.setNoSCEmplDoc(empDetlDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(empDetlDoc.getData());
							String noSTEmplDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							employmentDetails.setNoSTEmplDocbase64File(noSTEmplDocbase64Encoded);
							employmentDetails.setNoSTEmplDoc(empDetlDoc.getFileName());
						}

						if (i == 3) {
							byte[] encodeBase64 = Base64.encodeBase64(empDetlDoc.getData());
							String noFemaleEmplDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							employmentDetails.setNoFemaleEmplDocbase64File(noFemaleEmplDocbase64Encoded);
							employmentDetails.setNoFemaleEmplDoc(empDetlDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Employment details Document $$$$ %s", e.getMessage()));
		}

	}

}
