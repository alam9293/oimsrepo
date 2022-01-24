package com.webapp.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ResponseQueryDocument;

@Repository
public interface ResponseQueryDocRepository extends MongoRepository<ResponseQueryDocument, String> {

	ResponseQueryDocument findByDocAppId(String docAppId);

	ResponseQueryDocument findByDocId(String docId);
}
