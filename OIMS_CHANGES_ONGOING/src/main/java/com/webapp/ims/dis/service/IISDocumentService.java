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

import com.webapp.ims.dis.model.IISDocument;
import com.webapp.ims.dis.repository.IISDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on IISDocumentService documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class IISDocumentService {
	private final Logger logger = LoggerFactory.getLogger(IISDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	IISDocumentRepository iisRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateIISDocList(List<IISDocument> files, String iisId) {
		try {
			List<IISDocument> iisDocList = iisRepos.getListBydisIISId(iisId);
			if (!iisDocList.isEmpty()) {
				for (IISDocument objIISDocument : files) {
					for (int k = 0; k < iisDocList.size(); k++) {
						IISDocument iisDocfromDb = iisDocList.get(k);
						if (iisDocfromDb.getFieldName().equals(objIISDocument.getFieldName())) {
							iisDocfromDb.setData(objIISDocument.getData());
							iisDocfromDb.setFileName(objIISDocument.getFileName());
							iisDocfromDb.setFileType(objIISDocument.getFileType());
							iisDocfromDb.setDocUpdateDate(new Date());
							iisDocfromDb.setDocId(iisDocfromDb.getDocId());
							iisRepos.save(iisDocfromDb);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ IIS Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertIISDocList(List<IISDocument> files, HttpSession session) {
		String docIISId = null;
		String docId = null;

		for (IISDocument ObjIISDocument : files) {
			String appId = (String) session.getAttribute("appId");
			docIISId = appId + "IIS";

			docId = appId + "DC" + atomicInteger.getAndIncrement();
			ObjIISDocument.setDisIISId(docIISId);
			ObjIISDocument.setDocId(docId);
			iisRepos.insert(ObjIISDocument);

		}

	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<IISDocument> files, HttpSession session) {
		String docIISId = null;
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
						docIISId = entry.getValue() + "IIS";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			docIISId = appId + "IIS";

			// to update files by id
			List<IISDocument> iisDocList = iisRepos.getListBydisIISId(docIISId);

			if (!iisDocList.isEmpty()) {
				updateIISDocList(files, docIISId);

			} else { // to insert new files
				insertIISDocList(files, session);
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
				IISDocument dbFile = new IISDocument(fileName, multipartFile.getContentType(),
						multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String docIISId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docIISId = entry.getValue() + "A1IIS";
					}
				}

				dbFile.setDisIISId(docIISId);
				dbFile.setDocId(docId);
				iisRepos.save(dbFile);

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
			String docIISId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					docIISId = entry.getValue() + "A1IIS";
				}
			}

			if (iisRepos.findBydisIISId(docIISId) != null) {
				IISDocument iisDocfromDb = iisRepos.findBydisIISId(docIISId);
				Optional<IISDocument> iisDoc = iisRepos.findById(iisDocfromDb.get_id());
				if (iisDoc.isPresent()) {// to update existing documents
					iisDocfromDb.setData(multipartFile.getBytes());
					iisDocfromDb.setFileName(multipartFile.getOriginalFilename());
					iisDocfromDb.setFileType(multipartFile.getContentType());
					iisDocfromDb.setDocUpdateDate(new Date());
					iisDocfromDb.setDisIISId(docIISId);
					iisDocfromDb.setDocId(docId);
					iisRepos.save(iisDocfromDb);
				}

			} else {// to insert new documents
				IISDocument dbFile = new IISDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setDisIISId(docIISId);
				dbFile.setDocId(docId);
				iisRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(IISDocument iisDocument, List<MultipartFile> files, HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String docIISId = null;

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
						docIISId = entry.getValue() + "A1IIS";
					}
				}

				if (iisRepos.findBydisIISId(docIISId) != null) {
					IISDocument iisDocfromDb = iisRepos.findBydisIISId(docIISId);
					Optional<IISDocument> iisDoc = iisRepos.findById(iisDocfromDb.get_id());
					if (iisDoc.isPresent()) {// to update existing documents
						iisDocfromDb.setData(multipartFile.getBytes());
						iisDocfromDb.setFileName(fileName);
						iisDocfromDb.setFileType(multipartFile.getContentType());
						iisDocfromDb.setDocUpdateDate(new Date());
						iisDocfromDb.setDisIISId(docIISId);
						iisDocfromDb.setDocId(docId);
						iisRepos.save(iisDocfromDb);
					}

				} else {// to insert new documents
					IISDocument dbFile = new IISDocument(fileName, multipartFile.getContentType(),
							multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setDisIISId(docIISId);
					dbFile.setDocId(docId);
					iisRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	// Pankaj Mongo File Get and Download
	public void iisDocFromMongoDB(String iisId, Model model) {
		try {
			List<IISDocument> iisDocList = iisRepos.getListBydisIISId(iisId);
			if (iisDocList != null) {

				for (int i = 0; i < iisDocList.size(); i++) {
					IISDocument iisDoc = iisDocList.get(i);
					if (iisDoc != null) {
						if (i == 0) {
							model.addAttribute("sectionletteriis", iisDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("certifyingLoaniis", iisDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("auditedAccountsiis", iisDoc.getFileName());
						}

						if (i == 3) {
							model.addAttribute("fiBankCertificateiis", iisDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ CIS Document $$$$ %s", e.getMessage()));
		}
	}

}
