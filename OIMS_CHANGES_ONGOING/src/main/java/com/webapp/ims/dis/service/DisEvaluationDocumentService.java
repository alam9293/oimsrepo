package com.webapp.ims.dis.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webapp.ims.dis.model.DisEvaluationDocument;
import com.webapp.ims.dis.model.DisViewEvaluate;
import com.webapp.ims.dis.repository.DisEvaluationDocumentRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on CISDocumentService documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class DisEvaluationDocumentService {
	private final Logger logger = LoggerFactory.getLogger(DisEvaluationDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	DisEvaluationDocumentRepository disEvalRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateEvalDocList(List<DisEvaluationDocument> files, String disEvalId) {
		try {
			List<DisEvaluationDocument> evalDocList = disEvalRepos.getListBydisEvalId(disEvalId);
			if (!evalDocList.isEmpty()) {
				for (DisEvaluationDocument objEvalDocument : files) {
					for (int k = 0; k < evalDocList.size(); k++) {
						DisEvaluationDocument evalDocfromDb = evalDocList.get(k);
						if (evalDocfromDb.getFieldName().equals(objEvalDocument.getFieldName())) {
							evalDocfromDb.setData(objEvalDocument.getData());
							evalDocfromDb.setFileName(objEvalDocument.getFileName());
							evalDocfromDb.setFileType(objEvalDocument.getFileType());
							evalDocfromDb.setDocUpdateDate(new Date());
							evalDocfromDb.setDocId(evalDocfromDb.getDocId());
							disEvalRepos.save(evalDocfromDb);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ DIS Evaluation Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertEvalDocList(List<DisEvaluationDocument> files, HttpSession session) {
		String disEvalId = null;
		String docId = null;
		String appId = (String) session.getAttribute("appId");
		disEvalId = appId + "DISEVL";
		for (DisEvaluationDocument objDisEvaluationDocument : files) {
			docId = appId + "DC" + atomicInteger.getAndIncrement();
			objDisEvaluationDocument.setDisEvalId(disEvalId);
			objDisEvaluationDocument.setDocId(docId);
			disEvalRepos.insert(objDisEvaluationDocument);

		}

	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<DisEvaluationDocument> multipartFileList, HttpSession session) {
		String disEvalId = null;
		Object niveshResponse = null;

		Map<String, String> response = null;
		try {
			String appId = (String) session.getAttribute("appId");
			disEvalId = appId + "DISEVL";

			// to update files by id
			List<DisEvaluationDocument> cisDocList = disEvalRepos.getListBydisEvalId(disEvalId);

			if (!cisDocList.isEmpty()) {
				updateEvalDocList(multipartFileList, disEvalId);

			} else { // to insert new files
				insertEvalDocList(multipartFileList, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ DisEvaluation Document ^^^ %s", e.getMessage()));
		}
	}

	// Pankaj Mongo File Get and Download
	public void evalDocFromMongoDB(DisViewEvaluate disEvaluationDocument, String disEvalId, Model model) {
		try {
			List<DisEvaluationDocument> evalDocList = disEvalRepos.getListBydisEvalId(disEvalId);
			if (evalDocList != null) {
				for (int i = 0; i < evalDocList.size(); i++) {
					DisEvaluationDocument evalDoc = evalDocList.get(i);
					if (evalDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(evalDoc.getData());
							String confProvbyCTDDocBase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disEvaluationDocument.setConfProvbyCTDDocBase64(confProvbyCTDDocBase64Encoded);
							disEvaluationDocument.setConfProvbyCTDDoc(evalDoc.getFileName());
							model.addAttribute("confProvbyCTDDoc", evalDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(evalDoc.getData());
							String confProvbyBankDocBase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disEvaluationDocument.setConfProvbyBankDocBase64(confProvbyBankDocBase64Encoded);
							disEvaluationDocument.setConfProvbyBankDoc(evalDoc.getFileName());
							model.addAttribute("confProvbyBankDoc", evalDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(evalDoc.getData());
							String externalERDDocBase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
							disEvaluationDocument.setExternalERDDocBase64(externalERDDocBase64Encoded);
							disEvaluationDocument.setExternalERDDoc(evalDoc.getFileName());
							model.addAttribute("externalERDDoc", evalDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ DisEvaluation Document  $$$$ %s", e.getMessage()));
		}
	}

}
