package com.webapp.ims.service;

import static com.webapp.ims.exception.GlobalConstants.Unit_Id;
import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;
import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;

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
import com.webapp.ims.model.InvestmentDocument;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.InvestmentDocumentRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

/**
 * Service class to perform CRUD operations on InvestmentDetails documents in
 * MongoDB.
 * 
 * @author Hemant kumar
 */

@Service
@Transactional
public class InvestmentDocumentService {
	private final Logger logger = LoggerFactory.getLogger(InvestmentDocumentService.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();
	@Autowired
	private InvestmentDocumentRepository invDocRepository;
	private AtomicInteger atomicInteger = new AtomicInteger();

	public InvestmentDocument getInvestDocByInvestId(String investId) {
		return invDocRepository.findByInvestId(investId);
	}

	public void updateInvDocList(List<MultipartFile> files, String investId) {
		try {
			List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
			if (!invDocList.isEmpty()) {
				for (int k = 0; k < invDocList.size(); k++) {
					InvestmentDocument invDocfromDb = invDocList.get(k);
					Optional<InvestmentDocument> invdoc = invDocRepository.findById(invDocfromDb.get_id());
					if (invdoc.isPresent()) {
						invDocfromDb.setData(files.get(k).getBytes());
						invDocfromDb.setFileName(files.get(k).getOriginalFilename());
						invDocfromDb.setFileType(files.get(k).getContentType());
						invDocfromDb.setDocUpdateDate(new Date());
						invDocfromDb.setDocId(invDocfromDb.getDocId());
						invDocRepository.save(invDocfromDb);
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ InvestmentDocument ^^^ %s", e.getMessage()));
		}
	}

	public void insertInvDocList(List<MultipartFile> files, HttpSession session) {
		String docId = null;
		String investId = null;
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (MultipartFile multipartFile : files) {
				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						investId = entry.getValue() + "I1";
					}
				}

				String fileName = multipartFile.getOriginalFilename();
				
				System.out.println("fileName>>>>>>>>>>   "+fileName);

				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				// to save new document
				InvestmentDocument dbFile = new InvestmentDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setInvestId(investId);
				dbFile.setDocId(docId);
				invDocRepository.insert(dbFile);
			}
		} catch (Exception e) {
			logger.error(String.format("!!!! InvestmentDocument !!!! %s", e.getMessage()));
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
		String investId = "";
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
						investId = entry.getValue() + "I1";
					}
				}
			}

			// to update files by id
			List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
			if (!invDocList.isEmpty()) {
				updateInvDocList(files, investId);

			} else { // to insert new files
				insertInvDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ InvestmentDocument ^^^ %s", e.getMessage()));
		}
	}

	/**
	 * This method is responsible to save and/or update single file at a time.
	 *
	 * @param (multipartFile, session)
	 */
	public void saveAndUpdateFile(MultipartFile multipartFile, HttpSession session) {
		//String docId = "";
		String investId = "";
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
						//docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						investId = entry.getValue() + "I1";
					}
				}
			}

			if (invDocRepository.findByInvestId(investId) != null) {// to update existing document
				InvestmentDocument invDocfromDb = invDocRepository.findByInvestId(investId);
				Optional<InvestmentDocument> invdoc = invDocRepository.findById(invDocfromDb.get_id());
				if (invdoc.isPresent()) {
					invDocfromDb.setData(multipartFile.getBytes());
					invDocfromDb.setFileName(multipartFile.getOriginalFilename());
					invDocfromDb.setFileType(multipartFile.getContentType());
					invDocfromDb.setDocUpdateDate(new Date());
					invDocRepository.save(invDocfromDb);
				}

			} else {// to save new document
				InvestmentDocument dbFile = new InvestmentDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setInvestId(investId);
				//dbFile.setDocId(docId);
				invDocRepository.insert(dbFile);
			}
		} catch (Exception e) {
			logger.error(String.format("***** InvestmentDocument saveAndUpdateFile  **** %s", e.getMessage()));
		}
	}

}
