package com.webapp.ims.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.RaiseQueryDocument;
import com.webapp.ims.model.ScrutinyDocument;
import com.webapp.ims.repository.RaiseQueryDocRepository;

/**
 * Service class to perform CRUD operations on RaiseQuery documents in MongoDB.
 * 
 * @author Hemant kumar
 */
@Service
@Transactional
public class RaiseQueryDocService {
	private final Logger logger = LoggerFactory.getLogger(RaiseQueryDocService.class);

	private AtomicInteger atomicInteger = new AtomicInteger();
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private RaiseQueryDocRepository rqDocRepository;

	/**
	 * This method is responsible to save and/or update single file at a time.
	 */
	public void saveAndUpdateRaiseQueryDoc(MultipartFile multipartFiles, HttpSession session) {

		String docId = "";
		String appId = null;
		String userId = (String) session.getAttribute("userId");

		appId = (String) session.getAttribute("appId");
		docId = appId.substring(0, appId.length() - 2) + "RQ";
		//List<RaiseQueryDocument> multipartFileList = new LinkedList<RaiseQueryDocument>();

		try {

			/*if (rqDocRepository.findByDocAppId(appId) != null) {*/// to update existing document
				//RaiseQueryDocument rqDocfromDb = rqDocRepository.findByDocAppId(appId);
				List<RaiseQueryDocument> apdoc = rqDocRepository.getListByDocUserId(userId);
				
				for (RaiseQueryDocument rqDocument : apdoc) {
					rqDocRepository.delete(rqDocument);
				}
		
				/*
				 * if (apdoc.isPresent()) { rqDocfromDb.setData(multipartFile.getBytes());
				 * rqDocfromDb.setFileName(multipartFile.getOriginalFilename());
				 * rqDocfromDb.setFileType(multipartFile.getContentType());
				 * rqDocfromDb.setDocUpdateDate(new Date()); //
				 * rqDocRepository.save(rqDocfromDb); mongoTemplate.save(rqDocfromDb); }
				 */

			// to save new document
				
					docId = appId + "DC" + atomicInteger.getAndIncrement();
				RaiseQueryDocument rqDoc = new RaiseQueryDocument(multipartFiles.getOriginalFilename(),
						multipartFiles.getContentType(), multipartFiles.getBytes());
				rqDoc.setDocCreatedBy("User");
				rqDoc.setDocUpdateDate(new Date());
				rqDoc.setDocCreatedDate(new Date());
				rqDoc.setDocAppId(appId);
				rqDoc.setDocId(docId);
				rqDoc.setFileName(multipartFiles.getOriginalFilename());
				rqDoc.setDocUserId(userId);
				rqDocRepository.insert(rqDoc);
				// rqDocRepository.insert(rqDoc);
				
			}
		 catch (Exception e) {
			logger.error(String.format("### RaiseQueryDocService exception ### %s", e.getMessage()));
		}
	}

	public List<RaiseQueryDocument> getRaiseQueryDocList() {
		return rqDocRepository.findAll();
	}

}
