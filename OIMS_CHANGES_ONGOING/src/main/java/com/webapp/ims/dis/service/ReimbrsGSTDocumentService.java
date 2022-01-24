package com.webapp.ims.dis.service;

import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;
import static com.webapp.ims.exception.GlobalConstants.Unit_Id;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.dis.model.ReimbrsmentGSTDocument;
import com.webapp.ims.dis.repository.ReimbrsDepositGSTDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on InvestmentDetails documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class ReimbrsGSTDocumentService {
	private final Logger logger = LoggerFactory.getLogger(ReimbrsGSTDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	private BusinessEntityDocumentRepository beDocRepository;

	@Autowired
	ReimbrsDepositGSTDocumentRepository disbursmentRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateReimbrsGSTDocList(List<ReimbrsmentGSTDocument> files, String reimbrsId) {
		try {
			List<ReimbrsmentGSTDocument> reimbrsGSTDocList = disbursmentRepos.getListByreimbrsGSTId(reimbrsId);
			if (!reimbrsGSTDocList.isEmpty()) {
				for (ReimbrsmentGSTDocument objReimbrsmentGSTDocument : files) {
					for (int k = 0; k < reimbrsGSTDocList.size(); k++) {
						ReimbrsmentGSTDocument reimbrsDocfromDb = reimbrsGSTDocList.get(k);
						if (reimbrsDocfromDb.getFieldName().equals(objReimbrsmentGSTDocument.getFieldName())) {
							reimbrsDocfromDb.setData(objReimbrsmentGSTDocument.getData());
							reimbrsDocfromDb.setFileName(objReimbrsmentGSTDocument.getFileName());
							reimbrsDocfromDb.setFileType(objReimbrsmentGSTDocument.getFileType());
							reimbrsDocfromDb.setDocUpdateDate(new Date());
							reimbrsDocfromDb.setDocId(reimbrsDocfromDb.getDocId());
							disbursmentRepos.save(reimbrsDocfromDb);

						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ Reimbursment GST Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertReimbrsGSTDocList(List<ReimbrsmentGSTDocument> files, HttpSession session) {
		String docReimbrsGSTId = null;
		String docId = null;

		for (ReimbrsmentGSTDocument ObjReimbrGSTDoc : files) {
			String appId = (String) session.getAttribute("appId");
			docReimbrsGSTId = appId + "RGST";

			docId = appId + "DC" + atomicInteger.getAndIncrement();
			ObjReimbrGSTDoc.setReimbrsGSTId(docReimbrsGSTId);
			ObjReimbrGSTDoc.setDocId(docId);
			disbursmentRepos.insert(ObjReimbrGSTDoc);

		}

	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<ReimbrsmentGSTDocument> files, HttpSession session) {
		String docReimbrsGSTId = null;
		Object niveshResponse = null;

		Map<String, String> response = null;
		try {
			if (session != null) {
				niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			}
			if (niveshResponse != null) {
				response = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			}
			if (response != null) {
				for (Map.Entry<String, String> entry : response.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
						docReimbrsGSTId = entry.getValue() + "A1RGST1";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			docReimbrsGSTId = appId + "RGST";
			// to update files by id
			List<ReimbrsmentGSTDocument> reimbrsDocList = disbursmentRepos.getListByreimbrsGSTId(docReimbrsGSTId);

			if (!reimbrsDocList.isEmpty()) {
				updateReimbrsGSTDocList(files, docReimbrsGSTId);

			} else { // to insert new files
				insertReimbrsGSTDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ Reimbrs Document ^^^ %s", e.getMessage()));
		}
	}

	public void storeMultipleFiles(List<MultipartFile> files, HttpSession session) {
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			try {
				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
				}
				ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(fileName, multipartFile.getContentType(),
						multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String docReimbrsId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docReimbrsId = entry.getValue() + "A1RGST1";
					}
				}

				dbFile.setReimbrsGSTId(docReimbrsId);
				dbFile.setDocId(docId);
				disbursmentRepos.save(dbFile);

			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	public void saveAndUpdateFile(MultipartFile multipartFile, HttpSession session) {

		String docId = "";
		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			String docReimbrsId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					docReimbrsId = entry.getValue() + "A1RGST1";
				}
			}

			if (disbursmentRepos.findByreimbrsGSTId(docReimbrsId) != null) {
				ReimbrsmentGSTDocument reimbrsDocfromDb = disbursmentRepos.findByreimbrsGSTId(docReimbrsId);
				Optional<ReimbrsmentGSTDocument> reimbrsdoc = disbursmentRepos.findById(reimbrsDocfromDb.get_id());
				if (reimbrsdoc.isPresent()) {// to update existing documents
					reimbrsDocfromDb.setData(multipartFile.getBytes());
					reimbrsDocfromDb.setFileName(multipartFile.getOriginalFilename());
					reimbrsDocfromDb.setFileType(multipartFile.getContentType());
					reimbrsDocfromDb.setDocUpdateDate(new Date());
					reimbrsDocfromDb.setReimbrsGSTId(docReimbrsId);
					reimbrsDocfromDb.setDocId(docId);
					disbursmentRepos.save(reimbrsDocfromDb);
				}

			} else {// to insert new documents
				ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setReimbrsGSTId(docReimbrsId);
				dbFile.setDocId(docId);
				disbursmentRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(ReimbrsmentGSTDocument reimbrsDocument, List<MultipartFile> files,
			HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String docReimbrsId = null;

		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			try {
				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
				}

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docReimbrsId = entry.getValue() + "A1RGST1";
					}
				}

				if (disbursmentRepos.findByreimbrsGSTId(docReimbrsId) != null) {
					ReimbrsmentGSTDocument reimbrsDocfromDb = disbursmentRepos.findByreimbrsGSTId(docReimbrsId);
					Optional<ReimbrsmentGSTDocument> reimbrsdoc = disbursmentRepos.findById(reimbrsDocfromDb.get_id());
					if (reimbrsdoc.isPresent()) {// to update existing documents
						reimbrsDocfromDb.setData(multipartFile.getBytes());
						reimbrsDocfromDb.setFileName(fileName);
						reimbrsDocfromDb.setFileType(multipartFile.getContentType());
						reimbrsDocfromDb.setDocUpdateDate(new Date());
						reimbrsDocfromDb.setReimbrsGSTId(docReimbrsId);
						reimbrsDocfromDb.setDocId(docId);
						disbursmentRepos.save(reimbrsDocfromDb);
					}

				} else {// to insert new documents
					ReimbrsmentGSTDocument dbFile = new ReimbrsmentGSTDocument(fileName, multipartFile.getContentType(),
							multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setReimbrsGSTId(docReimbrsId);
					dbFile.setDocId(docId);
					disbursmentRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	// Pankaj Mongo File Get and Download
	public void reimbrsDocFromMongoDB(String docReimbrsId, Model model) {
		try {
			List<ReimbrsmentGSTDocument> reimbrsDocList = disbursmentRepos.getListByreimbrsGSTId(docReimbrsId);
			if (reimbrsDocList != null) {

				for (int i = 0; i < reimbrsDocList.size(); i++) {
					ReimbrsmentGSTDocument reimbrsDoc = reimbrsDocList.get(i);
					if (reimbrsDoc != null) {
						if (i == 0) {
							model.addAttribute("relevantDocRGST", reimbrsDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("auditedAccountsRGST", reimbrsDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("GSTAuditRGST", reimbrsDoc.getFileName());
						}

						if (i == 3) {
							model.addAttribute("CACertificateRGST", reimbrsDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ ReimbursGST Document $$$$ %s", e.getMessage()));
		}
	}

}
