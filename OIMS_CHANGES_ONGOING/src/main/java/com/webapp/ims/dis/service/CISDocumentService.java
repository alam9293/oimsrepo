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

import com.webapp.ims.dis.model.CISDocument;
import com.webapp.ims.dis.repository.CISDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on CISDocumentService documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class CISDocumentService {
	private final Logger logger = LoggerFactory.getLogger(CISDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	CISDocumentRepository cisRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateCISDocList(List<CISDocument> files, String cisId) {
		try {
			List<CISDocument> cisDocList = cisRepos.getListBydisCisId(cisId);
			if (!cisDocList.isEmpty()) {
				for (CISDocument objCISDocument : files) {
					for (int k = 0; k < cisDocList.size(); k++) {
						CISDocument cisDocfromDb = cisDocList.get(k);
						if (cisDocfromDb.getFieldName().equals(objCISDocument.getFieldName())) {
							cisDocfromDb.setData(objCISDocument.getData());
							cisDocfromDb.setFileName(objCISDocument.getFileName());
							cisDocfromDb.setFileType(objCISDocument.getFileType());
							cisDocfromDb.setDocUpdateDate(new Date());
							cisDocfromDb.setDocId(cisDocfromDb.getDocId());
							cisRepos.save(cisDocfromDb);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ CIS Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertCISDocList(List<CISDocument> files, HttpSession session) {
		String docCISId = null;
		String docId = null;
		String appId = (String) session.getAttribute("appId");
		docCISId = appId + "CIS";
			for (CISDocument objCISDocument : files) {
				docId = appId + "DC" + atomicInteger.getAndIncrement();
				objCISDocument.setDisCisId(docCISId);
				objCISDocument.setDocId(docId);
				cisRepos.insert(objCISDocument);

			}

	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<CISDocument> multipartFileList, HttpSession session) {
		String docCISId = null;
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
						docCISId = entry.getValue() + "A1CIS";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			docCISId = appId + "CIS";

			// to update files by id
			List<CISDocument> cisDocList = cisRepos.getListBydisCisId(docCISId);

			if (!cisDocList.isEmpty()) {
				updateCISDocList(multipartFileList, docCISId);

			} else { // to insert new files
				insertCISDocList(multipartFileList, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ CIS Document ^^^ %s", e.getMessage()));
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
				CISDocument dbFile = new CISDocument(fileName, multipartFile.getContentType(),
						multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String docCISId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docCISId = entry.getValue() + "A1CIS";
					}
				}

				dbFile.setDisCisId(docCISId);
				dbFile.setDocId(docId);
				cisRepos.save(dbFile);

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
			String docCISId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					docCISId = entry.getValue() + "A1CIS";
				}
			}

			if (cisRepos.findBydisCisId(docCISId) != null) {
				CISDocument cisDocfromDb = cisRepos.findBydisCisId(docCISId);
				Optional<CISDocument> cisDoc = cisRepos.findById(cisDocfromDb.get_id());
				if (cisDoc.isPresent()) {// to update existing documents
					cisDocfromDb.setData(multipartFile.getBytes());
					cisDocfromDb.setFileName(multipartFile.getOriginalFilename());
					cisDocfromDb.setFileType(multipartFile.getContentType());
					cisDocfromDb.setDocUpdateDate(new Date());
					cisDocfromDb.setDisCisId(docCISId);
					cisDocfromDb.setDocId(docId);
					cisRepos.save(cisDocfromDb);
				}

			} else {// to insert new documents
				CISDocument dbFile = new CISDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setDisCisId(docCISId);
				dbFile.setDocId(docId);
				cisRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(CISDocument cisDocument, List<MultipartFile> files, HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String docCISId = null;

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
						docCISId = entry.getValue() + "A1CIS";
					}
				}

				if (cisRepos.findBydisCisId(docCISId) != null) {
					CISDocument cisDocfromDb = cisRepos.findBydisCisId(docCISId);
					Optional<CISDocument> cisDoc = cisRepos.findById(cisDocfromDb.get_id());
					if (cisDoc.isPresent()) {// to update existing documents
						cisDocfromDb.setData(multipartFile.getBytes());
						cisDocfromDb.setFileName(fileName);
						cisDocfromDb.setFileType(multipartFile.getContentType());
						cisDocfromDb.setDocUpdateDate(new Date());
						cisDocfromDb.setDisCisId(docCISId);
						cisDocfromDb.setDocId(docId);
						cisRepos.save(cisDocfromDb);
					}

				} else {// to insert new documents
					CISDocument dbFile = new CISDocument(fileName, multipartFile.getContentType(),
							multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setDisCisId(docCISId);
					dbFile.setDocId(docId);
					cisRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	// Pankaj Mongo File Get and Download
	public void cisDocFromMongoDB(String cisId, Model model) {
		try {
			List<CISDocument> cisDocList = cisRepos.getListBydisCisId(cisId);
			if (cisDocList != null) {

				for (int i = 0; i < cisDocList.size(); i++) {
					CISDocument cisDoc = cisDocList.get(i);
					if (cisDoc != null) {
						if (i == 0) {
							model.addAttribute("sectionletter", cisDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("certifyingLoan", cisDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("auditedAccounts", cisDoc.getFileName());
						}

						if (i == 3) {
							model.addAttribute("fiBankCertificate", cisDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ CIS Document $$$$ %s", e.getMessage()));
		}
	}

}
