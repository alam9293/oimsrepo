/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */

package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;
import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;
import com.webapp.ims.dis.model.ReimbrsmentGSTDocument;
import com.webapp.ims.dis.repository.ReimbrsDepositGSTDocumentRepository;
import com.webapp.ims.dis.service.DisReimbrsGStTableService;
import com.webapp.ims.dis.service.DisbursmentRiembrsDepositGSTService;
import com.webapp.ims.dis.service.ReimbrsGSTDocumentService;
import com.webapp.ims.exception.FileStorageException;

/**
 * @author nic
 *
 */

@Controller
public class DisReimOfDepositeGSTController {

	private final Logger logger = LoggerFactory.getLogger(DisReimOfDepositeGSTController.class);

	@Autowired
	DisbursmentRiembrsDepositGSTService disbrsReimGSTService;

	@Autowired
	ReimbrsDepositGSTDocumentRepository disbursmentRepos;

	@Autowired
	ReimbrsGSTDocumentService disRiembrsService;

	@Autowired
	DisReimbrsGStTableService disReimbrsGStTableService;

	private AtomicInteger atomicInteger = new AtomicInteger();
	private int rGSTIndex = 0;
	private boolean isRowRecord = false;

	private List<DisReimbrsDepositeGstTable> disReimbrsGSTTableList = new LinkedList<>();

	@GetMapping(value = "/disReimbrsDepositeGST")
	public ModelAndView disReimbrsDepositeGST(
			@ModelAttribute("disReimbrsDepositeGST") DissbursmentReimbrsDepositeGST disReimbrsDetails, Model model,
			HttpSession session) {

		logger.info(" Start REIMBURSEMENT OF DEPOSIT GST Get Method ");

		try {
			String appId = (String) session.getAttribute("appId");
			String appStr = appId.substring(0, appId.length() - 2);
			String reimbrsGSTId = appId + "RGST";
			DissbursmentReimbrsDepositeGST disReimbrsDetails1 = disbrsReimGSTService.getDetailsBydisAppId(appId);
			if (disReimbrsDetails1 != null) {
				model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails1);
				model.addAttribute("FinancialYear", disReimbrsDetails1.getFinancialYear());

				disReimbrsGSTTableList = disReimbrsGStTableService.getListBydisAppId(appId);
				model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);

				reimbursmentDocFromMongoDB(disReimbrsDetails1, reimbrsGSTId);
			} else {
				model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" End REIMBURSEMENT OF DEPOSIT GST Get Method ");
		return new ModelAndView("/Disbursement/disReimrsDepositeGST");
	}

	@PostMapping(value = "/disReimbrsDepositeGSTSave")
	public ModelAndView disReimbrsDepositeGSTSave(
			@ModelAttribute("disReimbrsDepositeGST") DissbursmentReimbrsDepositeGST disReimbrsDetails,
			@RequestParam("relevantDoc") MultipartFile relevantDocFile,
			@RequestParam("auditedAccounts") MultipartFile auditedAccountsFile,
			@RequestParam("GSTAudit") MultipartFile GSTAuditFile,
			@RequestParam("CACertificate") MultipartFile CACertificateFile, Model model, HttpSession session) {

		logger.info(" Start REIMBURSEMENT OF DEPOSIT GST POST Method ");

		try {
			String appId = (String) session.getAttribute("appId");
			String appStr = appId.substring(0, appId.length() - 2);
			String reimbrsGSTId = appId + "RGST";

			try {
				List<ReimbrsmentGSTDocument> multipartFileList = new LinkedList<>();

				if ((!relevantDocFile.getOriginalFilename().isEmpty() && relevantDocFile.getOriginalFilename() != null)
						&& relevantDocFile.getBytes() != null) {

					String fileName = relevantDocFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(relevantDocFile.getOriginalFilename(),
							relevantDocFile.getContentType(), relevantDocFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("relevantDoc");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);
				}
				if ((!auditedAccountsFile.getOriginalFilename().isEmpty()
						&& auditedAccountsFile.getOriginalFilename() != null)
						&& auditedAccountsFile.getBytes() != null) {

					String fileName = auditedAccountsFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(
							auditedAccountsFile.getOriginalFilename(), auditedAccountsFile.getContentType(),
							auditedAccountsFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("auditedAccounts");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);

				}

				if ((!GSTAuditFile.getOriginalFilename().isEmpty() && GSTAuditFile.getOriginalFilename() != null)
						&& GSTAuditFile.getBytes() != null) {

					String fileName = GSTAuditFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(GSTAuditFile.getOriginalFilename(),
							GSTAuditFile.getContentType(), GSTAuditFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("GSTAudit");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);

				}

				if ((!CACertificateFile.getOriginalFilename().isEmpty()
						&& CACertificateFile.getOriginalFilename() != null) && CACertificateFile.getBytes() != null) {

					String fileName = CACertificateFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}

					ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(CACertificateFile.getOriginalFilename(),
							CACertificateFile.getContentType(), CACertificateFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("CACertificate");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);

				}

				// For the saving of docs in MONgo DB
				disRiembrsService.saveAndUpdateMultipleFiles(multipartFileList, session);

			} catch (Exception e) {
				logger.error(String.format("***** Reimbursment Document exception  **** %s", e.getMessage()));
			}

			disReimbrsDetails.setDisId("DIS" + appId);
			disReimbrsDetails.setDisAppId(appId);
			disReimbrsDetails.setCreateBy("User");
			disReimbrsDetails.setCreateDate(new Date());
			disReimbrsDetails.setModifiedBy("User");
			disReimbrsDetails.setCreateDate(new Date());

			DissbursmentReimbrsDepositeGST disReimbrsGStDetails = disbrsReimGSTService.getDetailsBydisAppId(appId);
			if (disReimbrsGStDetails != null) {
				disbrsReimGSTService.updateDisbursmentReimbrsDetails(disReimbrsDetails);
			} else {
				disbrsReimGSTService.saveDisbursmentReimbrsDetails(disReimbrsDetails);
			}

			try {
				if (!disReimbrsGStTableService.getListBydisAppId(appId).isEmpty()) {
					List<DisReimbrsDepositeGstTable> rGstTableList = disReimbrsGStTableService.getListBydisAppId(appId);
					for (DisReimbrsDepositeGstTable rGstTable : rGstTableList) {
						disReimbrsGStTableService.deletedisId(rGstTable.getDisId());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// save DIS Reimbrs Table List in table
			if (!disReimbrsGSTTableList.isEmpty()) {
				disReimbrsGStTableService.saveReimbrsGSTTableList(disReimbrsGSTTableList);
			}

			reimbursmentDocFromMongoDB(disReimbrsDetails, reimbrsGSTId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);

		logger.info(" End REIMBURSEMENT OF DEPOSIT GST POST Method ");
		return new ModelAndView("redirect:/disincentivetype");
	}

	/**
	 * This method is responsible to add REIMBURSEMENT OF DEPOSIT GST in the list.
	 * Create by Pankaj
	 */
	@PostMapping("/addReimbrsGSTTable")
	public ModelAndView addReimbrsGSTTable(
			@ModelAttribute("disReimbrsDepositeGST") DissbursmentReimbrsDepositeGST disReimbrsDetails,
			BindingResult result, Model model, HttpSession session, HttpServletRequest request) {

		DisReimbrsDepositeGstTable reimbrsGSTTable = new DisReimbrsDepositeGstTable();
		try {
			String appId = (String) session.getAttribute("appId");
			String reimbrsGSTId = appId.substring(0, appId.length() - 2) + "RGST" + atomicInteger.getAndIncrement();
			String rembrsGSTId = appId + "RGST";

			reimbrsGSTTable.setFinancialYear(disReimbrsDetails.getFinancialYear());
			reimbrsGSTTable.setAmtNetSgstQYr(disReimbrsDetails.getAmtNetSgstQYr());
			reimbrsGSTTable.setAmtNetSgst(disReimbrsDetails.getAmtNetSgst());
			reimbrsGSTTable.setTtlSgstReim(disReimbrsDetails.getTtlSgstReim());
			reimbrsGSTTable.setAmtAdmtTaxDeptGst(disReimbrsDetails.getAmtAdmtTaxDeptGst());
			reimbrsGSTTable.setEligbAmtDepo(disReimbrsDetails.getEligbAmtDepo());
			reimbrsGSTTable.setDurationPeriodDtFr(disReimbrsDetails.getDurationPeriodDtFr());
			reimbrsGSTTable.setDurationPeriodDtTo(disReimbrsDetails.getDurationPeriodDtTo());
			
			reimbrsGSTTable.setCreateBy("User");
			reimbrsGSTTable.setCreateDate(new Date());
			reimbrsGSTTable.setModifiedBy("User");
			reimbrsGSTTable.setModifiyDate(new Date());
			reimbrsGSTTable.setDisAppId(appId);
			reimbrsGSTTable.setDisId(reimbrsGSTId);

			disReimbrsDetails.setFinancialYear(null);
			disReimbrsDetails.setAmtNetSgstQYr(null);
			disReimbrsDetails.setAmtNetSgst(null);
			disReimbrsDetails.setTtlSgstReim(null);
			disReimbrsDetails.setAmtAdmtTaxDeptGst(null);
			disReimbrsDetails.setEligbAmtDepo(null);
			disReimbrsDetails.setDurationPeriodDtFr(null);
			disReimbrsDetails.setDurationPeriodDtTo(null);
			
			if (isRowRecord) {
				if (!disReimbrsGSTTableList.isEmpty() && disReimbrsGSTTableList.get(rGSTIndex) != null) {
					disReimbrsGSTTableList.remove(rGSTIndex);
				}
				updateRGSTTableRow(rGSTIndex, reimbrsGSTTable);
				isRowRecord = false;
				model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);
				model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);
			} else {
				if (reimbrsGSTTable != null)
					// disReimbrsGSTTableList.clear();
					disReimbrsGSTTableList.add(reimbrsGSTTable);
				model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);
				model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);
			}
			reimbursmentDocFromMongoDB(disReimbrsDetails, rembrsGSTId);
		} catch (

		Exception e) {
			logger.error(String.format("#### add RGST Table exception $$$ %s", e.getMessage()));
		}
		model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);
		model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);

		return new ModelAndView("/Disbursement/disReimrsDepositeGST");
	}

	/**
	 * This method is responsible to delete row from the RGST Table list as well as
	 * table.
	 */
	@PostMapping("/deleteRgstTable")
	public ModelAndView deleteRgstTable(
			@ModelAttribute("disReimbrsDepositeGST") DissbursmentReimbrsDepositeGST disReimbrsDetails, Model model,
			@RequestParam(value = "selectedRow", required = false) int index, HttpSession session,
			HttpServletRequest request) {
		String appId = (String) session.getAttribute("appId");
		String rembrsGSTId = appId + "RGST";
		try {
			if (!disReimbrsGSTTableList.isEmpty() && disReimbrsGSTTableList.get(index) != null) {
				DisReimbrsDepositeGstTable rgstTable = disReimbrsGSTTableList.get(index);
				try {
					disReimbrsGStTableService.deletedisId(rgstTable.getDisId());
				} catch (Exception e) {

				}
				disReimbrsGSTTableList.remove(index);
			}
		} catch (Exception e) {
			logger.error(String.format("#### delete RGST Table exception $$$ %s", e.getMessage()));
		}
		reimbursmentDocFromMongoDB(disReimbrsDetails, rembrsGSTId);
		model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);
		model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);

		return new ModelAndView("/Disbursement/disReimrsDepositeGST");

	}

	/**
	 * This method is responsible to Edit row from the RGST Table list as well as
	 * table.
	 */
	@PostMapping(value = "/editRGSTTable")
	public ModelAndView editRGSTTable(
			@ModelAttribute("disReimbrsDepositeGST") DissbursmentReimbrsDepositeGST disReimbrsDetails,
			@RequestParam(value = "editRecord", required = false) int index, Model model, HttpSession session,
			HttpServletRequest request) {
		String appId = (String) session.getAttribute("appId");
		String rembrsGSTId = appId + "RGST";

		DisReimbrsDepositeGstTable reimbrsGSTTable = null;
		try {
			if (!disReimbrsGSTTableList.isEmpty() && disReimbrsGSTTableList.get(index) != null) {
				reimbrsGSTTable = disReimbrsGSTTableList.get(index);

				disReimbrsDetails.setFinancialYear(reimbrsGSTTable.getFinancialYear());
				disReimbrsDetails.setAmtNetSgstQYr(reimbrsGSTTable.getAmtNetSgstQYr());
				disReimbrsDetails.setAmtNetSgst(reimbrsGSTTable.getAmtNetSgst());
				disReimbrsDetails.setTtlSgstReim(reimbrsGSTTable.getTtlSgstReim());
				disReimbrsDetails.setAmtAdmtTaxDeptGst(reimbrsGSTTable.getAmtAdmtTaxDeptGst());
				disReimbrsDetails.setEligbAmtDepo(reimbrsGSTTable.getEligbAmtDepo());
				disReimbrsDetails.setDurationPeriodDtFr(reimbrsGSTTable.getDurationPeriodDtFr());
				disReimbrsDetails.setDurationPeriodDtTo(reimbrsGSTTable.getDurationPeriodDtTo());
				rGSTIndex = index;
				isRowRecord = true;
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ edit Exist RGST TaBLE Row Exception ^^^ %s", e.getMessage()));
		}

		reimbursmentDocFromMongoDB(disReimbrsDetails, rembrsGSTId);
		model.addAttribute("edit", "edit");
		model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);
		model.addAttribute("disReimbrsDepositeGST", disReimbrsDetails);

		return new ModelAndView("/Disbursement/disReimrsDepositeGST");

	}

	public void updateRGSTTableRow(int index, DisReimbrsDepositeGstTable rGstTable) {
		disReimbrsGSTTableList.add(index, rGstTable);
	}

	private void reimbursmentDocFromMongoDB(DissbursmentReimbrsDepositeGST reimbrsDepositeGST, String reimbrsGSTId) {

		try {
			List<ReimbrsmentGSTDocument> reimbrsDocList = disbursmentRepos.getListByreimbrsGSTId(reimbrsGSTId);
			if (reimbrsDocList != null) {

				for (int i = 0; i < reimbrsDocList.size(); i++) {
					ReimbrsmentGSTDocument reimbrsDoc = reimbrsDocList.get(i);
					if (reimbrsDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(reimbrsDoc.getData());
							String relevantDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							reimbrsDepositeGST.setRelevantDocbase64File(relevantDocbase64Encoded);
							reimbrsDepositeGST.setRelevantDoc(reimbrsDoc.getFileName());
							reimbrsDepositeGST.setRelevantDocID(reimbrsDoc.getDocId());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(reimbrsDoc.getData());
							String auditedAccountsbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							reimbrsDepositeGST.setAuditedAccountsDocbase64File(auditedAccountsbase64Encoded);
							reimbrsDepositeGST.setAuditedAccounts(reimbrsDoc.getFileName());
							reimbrsDepositeGST.setAuditedAccountsId(reimbrsDoc.getDocId());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(reimbrsDoc.getData());
							String GSTAuditDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							reimbrsDepositeGST.setGSTAuditDocbase64File(GSTAuditDocbase64Encoded);
							reimbrsDepositeGST.setGSTAudit(reimbrsDoc.getFileName());
							reimbrsDepositeGST.setGSTAuditId(reimbrsDoc.getDocId());
						}

						if (i == 3) {
							byte[] encodeBase64 = Base64.encodeBase64(reimbrsDoc.getData());
							String CACertificateDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							reimbrsDepositeGST.setCACertificateDocbase64File(CACertificateDocbase64Encoded);
							reimbrsDepositeGST.setCACertificate(reimbrsDoc.getFileName());
							reimbrsDepositeGST.setCACertificateId(reimbrsDoc.getDocId());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Reimbursment Document $$$$ %s", e.getMessage()));
		}

	}

}
