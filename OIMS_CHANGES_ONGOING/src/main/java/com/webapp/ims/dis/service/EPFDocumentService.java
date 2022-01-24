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

import com.webapp.ims.dis.model.EPFDocument;
import com.webapp.ims.dis.repository.EPFDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on EPFDocumentService documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class EPFDocumentService {
	private final Logger logger = LoggerFactory.getLogger(EPFDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	EPFDocumentRepository epfRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateEPFDocList(List<EPFDocument> files, String epfId) {
		try {
			List<EPFDocument> epfDocList = epfRepos.getListBydisEpfId(epfId);
			if (!epfDocList.isEmpty()) {
				for (EPFDocument objEPFDocument : files) {
					for (int k = 0; k < epfDocList.size(); k++) {
						EPFDocument epfDocfromDb = epfDocList.get(k);

						if (epfDocfromDb.getFieldName().equals(objEPFDocument.getFieldName())) {

							epfDocfromDb.setData(objEPFDocument.getData());
							epfDocfromDb.setFileName(objEPFDocument.getFileName());
							epfDocfromDb.setFileType(objEPFDocument.getFileType());
							epfDocfromDb.setDocUpdateDate(new Date());
							epfDocfromDb.setDocId(epfDocfromDb.getDocId());
							epfRepos.save(epfDocfromDb);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ EPF Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertEPFDocList(List<EPFDocument> files, HttpSession session) {
		String epfId = null;
		String docId = null;
		String appId = (String) session.getAttribute("appId");
		epfId = appId + "EPF";

		for (EPFDocument objEPFDocument : files) {
			docId = appId + "DC" + atomicInteger.getAndIncrement();
			objEPFDocument.setDisEpfId(epfId);
			objEPFDocument.setDocId(docId);
			epfRepos.insert(objEPFDocument);

		}
	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<EPFDocument> files, HttpSession session) {
		String docEPFId = null;
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
						docEPFId = entry.getValue() + "A1EPF";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			docEPFId = appId + "EPF";

			// to update files by id
			List<EPFDocument> epfDocList = epfRepos.getListBydisEpfId(docEPFId);

			if (!epfDocList.isEmpty()) {
				updateEPFDocList(files, docEPFId);

			} else { // to insert new files
				insertEPFDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ EPF Document ^^^ %s", e.getMessage()));
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
				EPFDocument dbFile = new EPFDocument(fileName, multipartFile.getContentType(),
						multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String docEPFId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docEPFId = entry.getValue() + "A1EPF";
					}
				}

				dbFile.setDisEpfId(docEPFId);
				dbFile.setDocId(docId);
				epfRepos.save(dbFile);

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
			String docEPFId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					docEPFId = entry.getValue() + "A1EPF";
				}
			}

			if (epfRepos.findBydisEpfId(docEPFId) != null) {
				EPFDocument epfDocfromDb = epfRepos.findBydisEpfId(docEPFId);
				Optional<EPFDocument> epfDoc = epfRepos.findById(epfDocfromDb.get_id());
				if (epfDoc.isPresent()) {// to update existing documents
					epfDocfromDb.setData(multipartFile.getBytes());
					epfDocfromDb.setFileName(multipartFile.getOriginalFilename());
					epfDocfromDb.setFileType(multipartFile.getContentType());
					epfDocfromDb.setDocUpdateDate(new Date());
					epfDocfromDb.setDisEpfId(docEPFId);
					epfDocfromDb.setDocId(docId);
					epfRepos.save(epfDocfromDb);
				}

			} else {// to insert new documents
				EPFDocument dbFile = new EPFDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setDisEpfId(docEPFId);
				dbFile.setDocId(docId);
				epfRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(EPFDocument epfDocument, List<MultipartFile> files, HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String docEPFId = null;

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
						docEPFId = entry.getValue() + "A1EPF";
					}
				}

				if (epfRepos.findBydisEpfId(docEPFId) != null) {
					EPFDocument epfDocfromDb = epfRepos.findBydisEpfId(docEPFId);
					Optional<EPFDocument> epfDoc = epfRepos.findById(epfDocfromDb.get_id());
					if (epfDoc.isPresent()) {// to update existing documents
						epfDocfromDb.setData(multipartFile.getBytes());
						epfDocfromDb.setFileName(fileName);
						epfDocfromDb.setFileType(multipartFile.getContentType());
						epfDocfromDb.setDocUpdateDate(new Date());
						epfDocfromDb.setDisEpfId(docEPFId);
						epfDocfromDb.setDocId(docId);
						epfRepos.save(epfDocfromDb);
					}

				} else {// to insert new documents
					EPFDocument dbFile = new EPFDocument(fileName, multipartFile.getContentType(),
							multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setDisEpfId(docEPFId);
					dbFile.setDocId(docId);
					epfRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	// Pankaj Mongo File Get and Download
	public void epfDocFromMongoDB(String epfDocId, Model model) {
		try {
			List<EPFDocument> epfDocList = epfRepos.getListBydisEpfId(epfDocId);
			if (epfDocList != null) {

				for (int i = 0; i < epfDocList.size(); i++) {
					EPFDocument epfDoc = epfDocList.get(i);
					if (epfDoc != null) {
						if (i == 0) {
							model.addAttribute("affidavitDoc", epfDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("copyFormDoc", epfDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("monthwiseDoc", epfDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ EPF Document $$$$ %s", e.getMessage()));
		}
	}

}
