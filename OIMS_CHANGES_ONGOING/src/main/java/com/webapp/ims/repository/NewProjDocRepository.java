package com.webapp.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.NewProjDetailsDocument;

@Repository
public interface NewProjDocRepository extends MongoRepository<NewProjDetailsDocument, String> {

	NewProjDetailsDocument findByProjectId(String projectId);
	NewProjDetailsDocument findByDocId(String docId);
}
