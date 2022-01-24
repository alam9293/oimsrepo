package com.webapp.ims.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.model.RaiseQueryDocument;
import com.webapp.ims.model.ResponseQueryDocument;
import com.webapp.ims.repository.ResponseQueryDocRepository;

/**
 * Service class to perform CRUD operations on ResponseQuery documents in
 * MongoDB.
 * 
 * @author Hemant kumar
 */

@Service
@Transactional
public class ResponseQueryDocService {
	private final Logger logger = LoggerFactory.getLogger(ResponseQueryDocService.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ResponseQueryDocRepository respDocRepository;

	/**
	 * This method is responsible to save and/or update single file at a time.
	 */
	public void saveAndUpdateRespQueryDoc(MultipartFile multipartFile, HttpSession session) {

		String docId = "";
		String appId = null;

		appId = (String) session.getAttribute("appId");
		docId = appId.substring(0, appId.length() - 2) + "RS";

		try {

			if (respDocRepository.findByDocAppId(appId) != null) {
				// to update existing document
				ResponseQueryDocument respDocfromDb = respDocRepository.findByDocAppId(appId);
				Optional<ResponseQueryDocument> respdoc = respDocRepository.findById(respDocfromDb.get_id());
				if (respdoc.isPresent()) {
					respDocfromDb.setData(multipartFile.getBytes());
					respDocfromDb.setFileName(multipartFile.getOriginalFilename());
					respDocfromDb.setFileType(multipartFile.getContentType());
					respDocfromDb.setDocUpdateDate(new Date());
					mongoTemplate.save(respDocfromDb);
				}

			} else {
				// to save new document
				ResponseQueryDocument respDoc = new ResponseQueryDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				respDoc.setDocCreatedBy("User");
				respDoc.setDocUpdateDate(new Date());
				respDoc.setDocCreatedDate(new Date());
				respDoc.setDocAppId(appId);
				respDoc.setDocId(docId);
				mongoTemplate.insert(respDoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<ResponseQueryDocument> getResponseQueryDocList() {
		return respDocRepository.findAll();
	}

	

}
