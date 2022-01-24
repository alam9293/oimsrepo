package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.webapp.ims.model.ScrutinyDocument;



public interface ScrutinyDocumentRepo extends MongoRepository<ScrutinyDocument, Integer> {

	@Query("Select scruDoc from ScrutinyDocument scruDoc where scruDoc.scruDocId =: scruDocId")
	List<ScrutinyDocument> getListByScruDocId(String scruDocId);
}
