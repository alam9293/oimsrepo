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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.NewProjDetailsDocument;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.NewProjDocRepository;
import com.webapp.ims.webservices.SoapConsumeEx;

@Service
@Transactional
public class ProjectDocumentService {
	private final Logger logger = LoggerFactory.getLogger(ProjectDocumentService.class);
	SoapConsumeEx soapdetails = new SoapConsumeEx();
	@Autowired
	private ExistProjDocRepository existProjDocRepository;
	@Autowired
	private NewProjDocRepository newProjDocRepository;
	private AtomicInteger atomicInteger = new AtomicInteger();

	public ExistProjDetailsDocument storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			ExistProjDetailsDocument dbFile = new ExistProjDetailsDocument(fileName, file.getContentType(),
					file.getBytes());
			return existProjDocRepository.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * This method is responsible to save and update single file in MongoDb
	 * collection.
	 */
	public void saveAndUpdateFile(MultipartFile multipartFile, HttpSession session) {
		String docId = null;
		Object niveshResponse = null;
		Map<String, String> response = null;
		String docProjectId = null;
		try {
			if (session != null) {
				niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			}
			if (niveshResponse != null) {
				response = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			}
			if (response != null) {
				for (Map.Entry<String, String> entry : response.entrySet()) {
					if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						docProjectId = entry.getValue() + "P1";
					}
				}

			}

			if (newProjDocRepository.findByProjectId(docProjectId) != null) {
				NewProjDetailsDocument pdDocfromDb = newProjDocRepository.findByProjectId(docProjectId);
				Optional<NewProjDetailsDocument> apdoc = newProjDocRepository.findById(pdDocfromDb.get_id());
				if (apdoc.isPresent()) {// to update existing documents

					if (multipartFile.getOriginalFilename().isEmpty()) {
						pdDocfromDb.setData(pdDocfromDb.getData());
						pdDocfromDb.setFileName(pdDocfromDb.getFileName());
						pdDocfromDb.setFileType(pdDocfromDb.getFileType());
					} else {
						pdDocfromDb.setData(multipartFile.getBytes());
						pdDocfromDb.setFileName(multipartFile.getOriginalFilename());
						pdDocfromDb.setFileType(multipartFile.getContentType());
					}

					pdDocfromDb.setDocUpdateDate(new Date());
					pdDocfromDb.setProjectId(docProjectId);
					pdDocfromDb.setDocId(docId);
					newProjDocRepository.save(pdDocfromDb);
				}

			} else {// to insert new documents
				NewProjDetailsDocument dbFile = new NewProjDetailsDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setProjectId(docProjectId);
				dbFile.setDocId(docId);
				newProjDocRepository.insert(dbFile);
			}

		} catch (Exception e) {
			logger.error(String.format("5555  NewProjDetailsDocument 5555 %s", e.getMessage()));
		}

	}

	public void updateProjDocList(List<MultipartFile> files, String projectId) {
		try {
			List<ExistProjDetailsDocument> projDocList = existProjDocRepository.getProjDocListByProjectId(projectId);
			if (!projDocList.isEmpty()) {
				for (int k = 0; k < projDocList.size(); k++) {
					ExistProjDetailsDocument projDocfromDb = projDocList.get(k);
					Optional<ExistProjDetailsDocument> invdoc = existProjDocRepository.findById(projDocfromDb.get_id());
					if (invdoc.isPresent()) {
						projDocfromDb.setData(files.get(k).getBytes());
						projDocfromDb.setFileName(files.get(k).getOriginalFilename());
						projDocfromDb.setFileType(files.get(k).getContentType());
						projDocfromDb.setDocUpdateDate(new Date());
						projDocfromDb.setDocId(projDocfromDb.getDocId());
						existProjDocRepository.save(projDocfromDb);
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ ExistProjDetailsDocument ^^^ %s", e.getMessage()));
		}
	}

	public void insertProjDocList(List<MultipartFile> files, HttpSession session) {
		String docId = null;
		String projectId = null;
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (MultipartFile multipartFile : files) {
				for (Map.Entry<String, String> entry : responce.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
						docId = entry.getValue() + "DC" + atomicInteger.getAndIncrement();
						projectId = entry.getValue() + "P1";
					}
				}

				String fileName = multipartFile.getOriginalFilename();

				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				// to save new document
				ExistProjDetailsDocument dbFile = new ExistProjDetailsDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setProjectId(projectId);
				dbFile.setDocId(docId);
				existProjDocRepository.insert(dbFile);
			}
		} catch (Exception e) {
			logger.error(String.format("!!!! ExistProjDetailsDocument !!!! %s", e.getMessage()));
		}
	}

	// This method is used to save and/or update documents in the table.
	public void saveAndUpdateMultipleFiles(List<MultipartFile> files, HttpSession session) {
		String projectId = "";
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
						projectId = entry.getValue() + "P1";
					}
				}
			}

			// to update files by id
			List<ExistProjDetailsDocument> projDocList = existProjDocRepository.getProjDocListByProjectId(projectId);
			if (!projDocList.isEmpty()) {
				updateProjDocList(files, projectId);

			} else { // to insert new files
				insertProjDocList(files, session);
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ ExistProjDetailsDocument ^^^ %s", e.getMessage()));
		}
	}

}
