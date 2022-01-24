package com.webapp.ims.service;

import static com.webapp.ims.exception.GlobalConstants.Unit_Id;
import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;
import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;

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
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.BusinessEntityDocument;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on InvestmentDetails documents in
 * MongoDB.
 * 
 * @author Hemant kumar
 */

@Service
@Transactional
public class BusinessEntityDocumentService {
	private final Logger logger = LoggerFactory.getLogger(BusinessEntityDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();
	@Autowired
	private BusinessEntityDocumentRepository beDocRepository;
	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateBusinessDocList(List<MultipartFile> files, String bentityId) {
		try {
			List<BusinessEntityDocument> businessDocList = beDocRepository
					.getBusinessDocListByBusinessEntityId(bentityId);
			if (!businessDocList.isEmpty()) {
				for (int k = 0; k < businessDocList.size(); k++) {
					BusinessEntityDocument beDocfromDb = businessDocList.get(k);
					Optional<BusinessEntityDocument> businessdoc = beDocRepository.findById(beDocfromDb.get_id());
					if (businessdoc.isPresent()) {// to update existing documents
						beDocfromDb.setData(files.get(k).getBytes());
						beDocfromDb.setFileName(files.get(k).getOriginalFilename());
						beDocfromDb.setFileType(files.get(k).getContentType());
						beDocfromDb.setDocUpdateDate(new Date());
						beDocfromDb.setDocId(beDocfromDb.getDocId());
						beDocRepository.save(beDocfromDb);
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ BusinessEntityDocument ^^^ %s", e.getMessage()));
		}
	}

	public void insertBusinessDocList(List<MultipartFile> files, HttpSession session) {
		String docBusEntityId = null;
		String docId = null;
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (MultipartFile multipartFile : files) {
				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docBusEntityId = entry.getValue() + "B1";
					}
				}

				String fileName = multipartFile.getOriginalFilename();

				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}
				// to save new document
				BusinessEntityDocument dbFile = new BusinessEntityDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setBusinessEntityId(docBusEntityId);
				dbFile.setDocId(docId);
				beDocRepository.insert(dbFile);

			}
		} catch (Exception e) {
			logger.error(String.format("!!!! BusinessEntityDocument !!!! %s", e.getMessage()));
		}
	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */
	public void saveAndUpdateMultipleFiles(List<MultipartFile> files, HttpSession session) {
		String docBusEntityId = null;
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
						docBusEntityId = entry.getValue() + "B1";
					}
				}
			}

			// to update files by id
			List<BusinessEntityDocument> businessDocList = beDocRepository
					.getBusinessDocListByBusinessEntityId(docBusEntityId);
			if (!businessDocList.isEmpty()) {
				updateBusinessDocList(files, docBusEntityId);

			} else { // to insert new files
				insertBusinessDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ InvestmentDocument ^^^ %s", e.getMessage()));
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
				BusinessEntityDocument dbFile = new BusinessEntityDocument(fileName, multipartFile.getContentType(),
						multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String docBusEntityId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docBusEntityId = entry.getValue() + "B1";
					}
				}

				dbFile.setBusinessEntityId(docBusEntityId);
				dbFile.setDocId(docId);
				beDocRepository.save(dbFile);

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
			String docBusEntityId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					docBusEntityId = entry.getValue() + "B1";
				}
			}

			if (beDocRepository.findByBusinessEntityId(docBusEntityId) != null) {
				BusinessEntityDocument beDocfromDb = beDocRepository.findByBusinessEntityId(docBusEntityId);
				Optional<BusinessEntityDocument> apdoc = beDocRepository.findById(beDocfromDb.get_id());
				if (apdoc.isPresent()) {// to update existing documents
					beDocfromDb.setData(multipartFile.getBytes());
					beDocfromDb.setFileName(multipartFile.getOriginalFilename());
					beDocfromDb.setFileType(multipartFile.getContentType());
					beDocfromDb.setDocUpdateDate(new Date());
					beDocfromDb.setBusinessEntityId(docBusEntityId);
					beDocfromDb.setDocId(docId);
					beDocRepository.save(beDocfromDb);
				}

			} else {// to insert new documents
				BusinessEntityDocument dbFile = new BusinessEntityDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setBusinessEntityId(docBusEntityId);
				dbFile.setDocId(docId);
				beDocRepository.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(BusinessEntityDocument beDocument, List<MultipartFile> files,
			HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String docBusEntityId = null;

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
						docBusEntityId = entry.getValue() + "B1";
					}
				}

				if (beDocRepository.findByBusinessEntityId(docBusEntityId) != null) {
					BusinessEntityDocument beDocfromDb = beDocRepository.findByBusinessEntityId(docBusEntityId);
					Optional<BusinessEntityDocument> apdoc = beDocRepository.findById(beDocfromDb.get_id());
					if (apdoc.isPresent()) {// to update existing documents
						beDocfromDb.setData(multipartFile.getBytes());
						beDocfromDb.setFileName(fileName);
						beDocfromDb.setFileType(multipartFile.getContentType());
						beDocfromDb.setDocUpdateDate(new Date());
						beDocfromDb.setBusinessEntityId(docBusEntityId);
						beDocfromDb.setDocId(docId);
						beDocRepository.save(beDocfromDb);
					}

				} else {// to insert new documents
					BusinessEntityDocument dbFile = new BusinessEntityDocument(fileName, multipartFile.getContentType(),
							multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setBusinessEntityId(docBusEntityId);
					dbFile.setDocId(docId);
					beDocRepository.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

}
