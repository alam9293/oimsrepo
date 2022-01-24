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

import com.webapp.ims.dis.model.EmploymentdetailsDocument;
import com.webapp.ims.dis.repository.EmploymentDetailsDocumentRepository;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on InvestmentDetails documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */

@Service
@Transactional
public class EmployementDetailsDocumentService {
	private final Logger logger = LoggerFactory.getLogger(EmployementDetailsDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	EmploymentDetailsDocumentRepository emplDocRepos;

	private AtomicInteger atomicInteger = new AtomicInteger();

	public void updateEmploymentdeatilsDocList(List<EmploymentdetailsDocument> files, String emplDtlId) {
		try {
			List<EmploymentdetailsDocument> emplDetalDocList = emplDocRepos.getListByemplDtlId(emplDtlId);
			if (!emplDetalDocList.isEmpty()) {

				for (EmploymentdetailsDocument objEmploymentdetailsDocument : files) {

					for (int k = 0; k < emplDetalDocList.size(); k++) {
						EmploymentdetailsDocument empDetlDocfromDb = emplDetalDocList.get(k);
						if (empDetlDocfromDb.getFieldName().equals(objEmploymentdetailsDocument.getFieldName())) {
							// to update existing documents
							empDetlDocfromDb.setData(objEmploymentdetailsDocument.getData());
							empDetlDocfromDb.setFileName(objEmploymentdetailsDocument.getFileName());
							empDetlDocfromDb.setFileType(objEmploymentdetailsDocument.getFileType());

							empDetlDocfromDb.setDocUpdateDate(new Date());
							empDetlDocfromDb.setDocId(empDetlDocfromDb.getDocId());
							emplDocRepos.save(empDetlDocfromDb);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ Capital Investment GST Document ^^^ %s", e.getMessage()));
		}
	}

	public void insertEmploymentdeatilsDocList(List<EmploymentdetailsDocument> files, HttpSession session) {
		String emplDtlId = null;
		String docId = null;
		String appId = (String) session.getAttribute("appId");
		emplDtlId = appId + "EMP";

		/*
		 * try { Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		 * Map<String, String> responce = ((SoapDataModel)
		 * niveshResponse).getNiveshSoapResponse();
		 */
		for (EmploymentdetailsDocument objEmploymentdetailsDocument : files) {

			docId = appId + "DC" + atomicInteger.getAndIncrement();

			objEmploymentdetailsDocument.setEmplDtlId(emplDtlId);
			objEmploymentdetailsDocument.setDocId(docId);
			emplDocRepos.insert(objEmploymentdetailsDocument);

		}

		/*
		 * } catch (Exception e) {
		 * logger.error(String.format("!!!! DIS CAP INV Document !!!! %s",
		 * e.getMessage())); }
		 */
	}

	/**
	 * This method is responsible to save and/or update multiple files. If files
	 * exist, then update documents else insert new documents in the MongoDB
	 * collection.
	 *
	 * @param (files, session)
	 */

	public void storeMultipleFiles(List<MultipartFile> files, HttpSession session) {
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			try {
				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
				}
				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(fileName,
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());

				String docId = "";
				String emplDtlId = "";

				Object niveshResponse = session.getAttribute("niveshSoapResponse");
				Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						emplDtlId = entry.getValue() + "A1EMP";
					}
				}

				dbFile.setEmplDtlId(emplDtlId);
				dbFile.setDocId(docId);
				emplDocRepos.save(dbFile);

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
			String emplDtlId = null;

			for (Map.Entry<String, String> entry : responce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
					emplDtlId = entry.getValue() + "A1EMP";
				}
			}

			if (emplDocRepos.getListByemplDtlId(emplDtlId) != null) {
				EmploymentdetailsDocument empDetlDocfromDb = emplDocRepos.getDocByemplDtlId(emplDtlId);
				Optional<EmploymentdetailsDocument> emplDetlDoc = emplDocRepos.findById(empDetlDocfromDb.get_id());
				if (emplDetlDoc.isPresent()) {// to update existing documents
					empDetlDocfromDb.setData(multipartFile.getBytes());
					empDetlDocfromDb.setFileName(multipartFile.getOriginalFilename());
					empDetlDocfromDb.setFileType(multipartFile.getContentType());
					empDetlDocfromDb.setDocUpdateDate(new Date());
					empDetlDocfromDb.setEmplDtlId(emplDtlId);
					empDetlDocfromDb.setDocId(docId);
					emplDocRepos.save(empDetlDocfromDb);
				}

			} else {// to insert new documents
				EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setEmplDtlId(emplDtlId);
				dbFile.setDocId(docId);
				emplDocRepos.insert(dbFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(EmploymentdetailsDocument emplDetlDocument, List<MultipartFile> files,
			HttpSession session) {
		String docId = "";

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		// String appId = responce.get("appID");
		String emplDtlId = null;

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
						emplDtlId = entry.getValue() + "A1EMP";
					}
				}

				if (emplDocRepos.findByemplDtlId(emplDtlId) != null) {
					EmploymentdetailsDocument emplDetlDocfromDb = emplDocRepos.findByemplDtlId(emplDtlId);
					Optional<EmploymentdetailsDocument> empDetaldoc = emplDocRepos.findById(emplDetlDocfromDb.get_id());
					if (empDetaldoc.isPresent()) {// to update existing documents
						emplDetlDocfromDb.setData(multipartFile.getBytes());
						emplDetlDocfromDb.setFileName(fileName);
						emplDetlDocfromDb.setFileType(multipartFile.getContentType());
						emplDetlDocfromDb.setDocUpdateDate(new Date());
						emplDetlDocfromDb.setEmplDtlId(emplDtlId);
						emplDetlDocfromDb.setDocId(docId);
						emplDocRepos.save(emplDetlDocfromDb);
					}

				} else {// to insert new documents
					EmploymentdetailsDocument dbFile = new EmploymentdetailsDocument(fileName,
							multipartFile.getContentType(), multipartFile.getBytes());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setEmplDtlId(emplDtlId);
					dbFile.setDocId(docId);
					emplDocRepos.insert(dbFile);
				}
			} catch (IOException ex) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			}
		}
	}

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	public void saveAndUpdateMultipleFiles(List<EmploymentdetailsDocument> multipartFileList, HttpSession session) {
		String emplDtlId = null;
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
						emplDtlId = entry.getValue() + "A1EMP";
					}
				}
			}
			String appId = (String) session.getAttribute("appId");
			emplDtlId = appId + "EMP";
			// to update files by id
			List<EmploymentdetailsDocument> empDetlDocList = emplDocRepos.getListByemplDtlId(emplDtlId);

			if (!empDetlDocList.isEmpty()) {
				updateEmploymentdeatilsDocList(multipartFileList, emplDtlId);

			} else { // to insert new files
				insertEmploymentdeatilsDocList(multipartFileList, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ CAp Inv Document ^^^ %s", e.getMessage()));
		}

	}

	// Pankaj Mongo File Get and Download
	public void employmentDetailsDocFromMongoDB(String docemplDetlId, Model model) {
		try {
			List<EmploymentdetailsDocument> emplDetalDocList = emplDocRepos.getListByemplDtlId(docemplDetlId);
			if (emplDetalDocList != null) {

				for (int i = 0; i < emplDetalDocList.size(); i++) {
					EmploymentdetailsDocument emplDetlDoc = emplDetalDocList.get(i);
					if (emplDetlDoc != null) {
						if (i == 0) {
							model.addAttribute("noBPLEmplDoc", emplDetlDoc.getFileName());
						}
						if (i == 1) {
							model.addAttribute("noSCEmplDoc", emplDetlDoc.getFileName());
						}

						if (i == 2) {
							model.addAttribute("noSTEmplDoc", emplDetlDoc.getFileName());
						}

						if (i == 3) {
							model.addAttribute("noFemaleEmplDoc", emplDetlDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ Employment details download Document $$$$ %s", e.getMessage()));
		}
	}

}
