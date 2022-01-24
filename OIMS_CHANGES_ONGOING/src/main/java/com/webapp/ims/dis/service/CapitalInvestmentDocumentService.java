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

import com.webapp.ims.dis.model.CapitalInvestmentDocument;
import com.webapp.ims.dis.repository.CapitalInvestDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on CapitalInvestmentDocumentService
 * documents in MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class CapitalInvestmentDocumentService {
	private final Logger logger = LoggerFactory.getLogger(CapitalInvestmentDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	CapitalInvestDocumentRepository capInvRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateCapitalInvestmentDocList(List<CapitalInvestmentDocument> files, String capInvId) {
		try {
			List<CapitalInvestmentDocument> capInvDocList = capInvRepos.getListBycapInvId(capInvId);
			if (!capInvDocList.isEmpty()) {
				for (CapitalInvestmentDocument objCapInvDocument : files) {
					for (int k = 0; k < capInvDocList.size(); k++) {
						CapitalInvestmentDocument capInvDocfromDb = capInvDocList.get(k);
						if (capInvDocfromDb.getFieldName().equals(objCapInvDocument.getFieldName())) {
							capInvDocfromDb.setData(objCapInvDocument.getData());
							capInvDocfromDb.setFileName(objCapInvDocument.getFileName());
							capInvDocfromDb.setFileType(objCapInvDocument.getFileType());
							capInvDocfromDb.setDocUpdateDate(new Date());
							capInvDocfromDb.setDocId(capInvDocfromDb.getDocId());
							capInvRepos.save(capInvDocfromDb);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ Capital Investment GST Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertCapitalInvestDocList(List<CapitalInvestmentDocument> files, HttpSession session) {
		String capInvId = null;
		String docId = null;
		String appId = (String) session.getAttribute("appId");
		capInvId = appId + "CAPINV";
		
		/*
		 * Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		 * Map<String, String> responce = ((SoapDataModel)
		 * niveshResponse).getNiveshSoapResponse();
		 */
			for (CapitalInvestmentDocument ObjCapitalInvestmentDocument : files) {
					docId = appId + "DC" + atomicInteger.getAndIncrement();
					//	capInvId = entry.getValue() + "A1CAPINV";
					
			
				
				ObjCapitalInvestmentDocument.setCapInvId(capInvId);
				ObjCapitalInvestmentDocument.setDocId(docId);
				capInvRepos.insert(ObjCapitalInvestmentDocument);
			}
			
		
	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<CapitalInvestmentDocument> files, HttpSession session) {
		String capInvId = null;
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
						capInvId = entry.getValue() + "A1CAPINV";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			capInvId = appId + "CAPINV";
			
			// to update files by id
			List<CapitalInvestmentDocument> capInvDocList = capInvRepos.getListBycapInvId(capInvId);

			if (!capInvDocList.isEmpty()) {
				updateCapitalInvestmentDocList(files, capInvId);

			} else { // to insert new files
				insertCapitalInvestDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ CAp Inv Document ^^^ %s", e.getMessage()));
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
				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(fileName,
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String capInvId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						capInvId = entry.getValue() + "A1CAPINV";
					}
				}

				dbFile.setCapInvId(capInvId);
				dbFile.setDocId(docId);
				capInvRepos.save(dbFile);

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
			String capInvId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					capInvId = entry.getValue() + "A1CAPINV";
				}
			}

			if (capInvRepos.getListBycapInvId(capInvId) != null) {
				CapitalInvestmentDocument capInvDocfromDb = capInvRepos.getDocBycapInvId(capInvId);
				Optional<CapitalInvestmentDocument> capInvDoc = capInvRepos.findById(capInvDocfromDb.get_id());
				if (capInvDoc.isPresent()) {// to update existing documents
					capInvDocfromDb.setData(multipartFile.getBytes());
					capInvDocfromDb.setFileName(multipartFile.getOriginalFilename());
					capInvDocfromDb.setFileType(multipartFile.getContentType());
					capInvDocfromDb.setDocUpdateDate(new Date());
					capInvDocfromDb.setCapInvId(capInvId);
					capInvDocfromDb.setDocId(docId);
					capInvRepos.save(capInvDocfromDb);
				}

			} else {// to insert new documents
				CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setCapInvId(capInvId);
				dbFile.setDocId(docId);
				capInvRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(CapitalInvestmentDocument capInvDocument, List<MultipartFile> files,
			HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String capInvId = null;

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
						capInvId = entry.getValue() + "A1CAPINV";
					}
				}

				if (capInvRepos.findBycapInvId(capInvId) != null) {
					CapitalInvestmentDocument capInvDocfromDb = capInvRepos.findBycapInvId(capInvId);
					Optional<CapitalInvestmentDocument> reimbrsdoc = capInvRepos.findById(capInvDocfromDb.get_id());
					if (reimbrsdoc.isPresent()) {// to update existing documents
						capInvDocfromDb.setData(multipartFile.getBytes());
						capInvDocfromDb.setFileName(fileName);
						capInvDocfromDb.setFileType(multipartFile.getContentType());
						capInvDocfromDb.setDocUpdateDate(new Date());
						capInvDocfromDb.setCapInvId(capInvId);
						capInvDocfromDb.setDocId(docId);
						capInvRepos.save(capInvDocfromDb);
					}

				} else {// to insert new documents
					CapitalInvestmentDocument dbFile = new CapitalInvestmentDocument(fileName,
							multipartFile.getContentType(), multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setCapInvId(capInvId);
					dbFile.setDocId(docId);
					capInvRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	// Pankaj Mongo File Get and Download
	public void capInvDocFromMongoDB(String capINVId, Model model) {
		try {
			List<CapitalInvestmentDocument> capInvDocList = capInvRepos.getListBycapInvId(capINVId);
			if (capInvDocList != null) {

				for (int i = 0; i < capInvDocList.size(); i++) {
					CapitalInvestmentDocument capInvDoc = capInvDocList.get(i);
					if (capInvDoc != null) {
						if (i == 0) {
							model.addAttribute("statutoryAuditorDoc", capInvDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("purchasePriceDoc", capInvDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("stampDutyDoc", capInvDoc.getFileName());
						}

						if (i == 3) {
							model.addAttribute("registrationFeeDoc", capInvDoc.getFileName());
						}

						if (i == 4) {
							model.addAttribute("banksAuctionDoc", capInvDoc.getFileName());
						}

						if (i == 5) {
							model.addAttribute("civilWorksDoc", capInvDoc.getFileName());
						}

						if (i == 6) {
							model.addAttribute("machineryMiscDoc", capInvDoc.getFileName());
						}

						if (i == 7) {
							model.addAttribute("encCertificateFName", capInvDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Capital Investment Document $$$$ %s", e.getMessage()));
		}
	}

}
