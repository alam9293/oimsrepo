package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisEvaluationDocument;

@Repository
public interface DisEvaluationDocumentRepository extends MongoRepository<DisEvaluationDocument, String> {

	@Query("Select evalDoc from DisEvaluationDocument evalDoc where evalDoc.disEvalId =: disEvalId")
	DisEvaluationDocument getDocBydisEvalId(@Param(value = "disEvalId") String disEvalId);

	@Query("Select evalDoc from DisEvaluationDocument evalDoc where evalDoc.docId =: docId")
	DisEvaluationDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select evalDoc from DisEvaluationDocument evalDoc where evalDoc.disEvalId =: disEvalId")
	List<DisEvaluationDocument> getListBydisEvalId(@Param(value = "disEvalId") String disEvalId);

	DisEvaluationDocument findBydisEvalId(String disEvalId);

	DisEvaluationDocument findByDocId(String docId);

}
