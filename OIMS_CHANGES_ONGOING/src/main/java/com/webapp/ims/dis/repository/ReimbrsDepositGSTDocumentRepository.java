package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.ReimbrsmentGSTDocument;

@Repository
public interface ReimbrsDepositGSTDocumentRepository extends MongoRepository<ReimbrsmentGSTDocument, String> {

	@Query("Select reimbrsDoc from ReimbrsmentGSTDocument reimbrsDoc where reimbrsDoc.reimbrsGSTId =: reimbrsGSTId")
	ReimbrsmentGSTDocument getDocByreimbrsGSTId(@Param(value = "reimbrsGSTId") String reimbrsGSTId);

	@Query("Select reimbrsDoc from ReimbrsmentGSTDocument reimbrsDoc where reimbrsDoc.docId =: docId")
	ReimbrsmentGSTDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select reimbrsDoc from ReimbrsmentGSTDocument reimbrsDoc where reimbrsDoc.reimbrsGSTId =: reimbrsGSTId")
	List<ReimbrsmentGSTDocument> getListByreimbrsGSTId(@Param(value = "reimbrsGSTId") String reimbrsGSTId);

	ReimbrsmentGSTDocument findByreimbrsGSTId(String reimbrsGSTId);

	ReimbrsmentGSTDocument findByDocId(String docId);

}
