package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ApplicantDocument;

@Repository
public interface ApplicantDocumentRepository extends MongoRepository<ApplicantDocument, String> {

	@Query("Select appDoc from ApplicantDocument appDoc where appDoc.docAppId=:docAppId")
	ApplicantDocument getApplicantDocumentByDocAppId(@Param(value = "docAppId") String docAppId);

	@Query("Select appDoc from ApplicantDocument appDoc where appDoc.docId=:docId")
	ApplicantDocument getApplicantDocumentByDocId(@Param(value = "docId") String docId);

	ApplicantDocument findByDocAppId(String docAppId);

	ApplicantDocument findByDocId(String docId);

}
