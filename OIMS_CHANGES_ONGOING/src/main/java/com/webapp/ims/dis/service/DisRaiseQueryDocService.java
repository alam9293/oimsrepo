package com.webapp.ims.dis.service;

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

import com.webapp.ims.dis.model.DisRaiseQueryDocument;
import com.webapp.ims.dis.repository.DisRaiseQueryDocRepository;

/**
 * Service class to perform CRUD operations on DisRaiseQuery documents in
 * MongoDB.
 * 
 * @author Pankaj Sahu
 */
@Service
@Transactional
public class DisRaiseQueryDocService {
	private final Logger logger = LoggerFactory.getLogger(DisRaiseQueryDocService.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private DisRaiseQueryDocRepository disRqDocRepository;

	/**
	 * This method is responsible to save and/or update single file at a time.
	 */
	public void saveAndUpdateRaiseQueryDoc(MultipartFile multipartFile, HttpSession session) {

		String docId = "";
		String appId = null;
		String userId = (String) session.getAttribute("userId");

		appId = (String) session.getAttribute("appId");
		docId = appId.substring(0, appId.length() - 2) + "RQ";

		try {

			if (disRqDocRepository.findByDocAppId(appId) != null) {// to update existing document
				DisRaiseQueryDocument rqDocfromDb = disRqDocRepository.findByDocAppId(appId);
				Optional<DisRaiseQueryDocument> apdoc = disRqDocRepository.findById(rqDocfromDb.get_id());
				if (apdoc.isPresent()) {
					rqDocfromDb.setData(multipartFile.getBytes());
					rqDocfromDb.setFileName(multipartFile.getOriginalFilename());
					rqDocfromDb.setFileType(multipartFile.getContentType());
					rqDocfromDb.setDocUpdateDate(new Date());
					// rqDocRepository.save(rqDocfromDb);
					mongoTemplate.save(rqDocfromDb);
				}

			} else {// to save new document
				DisRaiseQueryDocument rqDoc = new DisRaiseQueryDocument(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes());
				rqDoc.setDocCreatedBy("User");
				rqDoc.setDocUpdateDate(new Date());
				rqDoc.setDocCreatedDate(new Date());
				rqDoc.setDocAppId(appId);
				rqDoc.setDocId(docId);
				rqDoc.setDocUserId(userId);
				// rqDocRepository.insert(rqDoc);
				mongoTemplate.insert(rqDoc);
			}
		} catch (Exception e) {
			logger.error(String.format("### Dis RaiseQueryDocService exception ### %s", e.getMessage()));
		}
	}

	public List<DisRaiseQueryDocument> getRaiseQueryDocList() {
		return disRqDocRepository.findAll();
	}

}
