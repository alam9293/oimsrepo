package com.webapp.ims.service;

import static com.webapp.ims.exception.GlobalConstants.Unit_Id;
import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.exception.FileNotFoundException;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.ApplicantDocumentRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on Applicant documents in MongoDB.
 * 
 * @author Hemant kumar
 */
@Service
@Transactional
public class ApplicantDocumentService {
	private final Logger logger = LoggerFactory.getLogger(ApplicantDocumentService.class);
	SoapConsumeEx soapdetails = new SoapConsumeEx();
	@Autowired
	private ApplicantDocumentRepository dbFileRepository;
	
	
	public ApplicantDocument getFile(String fileId) {
		return dbFileRepository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
	}

	public ApplicantDocument getApplicantDocumentByDocAppId(String docAppId) {
		return dbFileRepository.getApplicantDocumentByDocAppId(docAppId);
	}

	/**
	 * This method is responsible to save and/or update single file at a time.
	 *
	 * @param (multipartFile, session)
	 */
	public void saveAndUpdateFile(MultipartFile multipartFile, HttpSession session) {

		String docId = "";
		String appId = null;
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
						docId = entry.getValue() + "DC1";
						appId = entry.getValue() + "A1";
					}
				}
			}

			if (dbFileRepository.findByDocAppId(appId) != null) {// to update existing document
				ApplicantDocument applicantDocfromDb = dbFileRepository.findByDocAppId(appId);
				Optional<ApplicantDocument> apdoc = dbFileRepository.findById(applicantDocfromDb.get_id());
				if (apdoc.isPresent()) {
					applicantDocfromDb.setData(multipartFile.getBytes());
					applicantDocfromDb.setFileName(multipartFile.getOriginalFilename());
					applicantDocfromDb.setFileType(multipartFile.getContentType());
					applicantDocfromDb.setDocUpdateDate(new Date());
					dbFileRepository.save(applicantDocfromDb);
				}

			} else {// to save new document
				ApplicantDocument dbFile = new ApplicantDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setDocAppId(appId);
				dbFile.setDocId(docId);
				dbFileRepository.insert(dbFile);
			}
		} catch (Exception e) {
			logger.error(String.format("### ApplicantDocument exception ### %s", e.getMessage()));
		}
	}

}
