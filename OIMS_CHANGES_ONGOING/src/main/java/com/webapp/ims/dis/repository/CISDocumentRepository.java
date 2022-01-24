package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.CISDocument;

@Repository
public interface CISDocumentRepository extends MongoRepository<CISDocument, String> {

	@Query("Select cisDoc from CISDocument cisDoc where cisDoc.disCisId =: disCisId")
	CISDocument getDocBydisCisId(@Param(value = "disCisId") String disCisId);

	@Query("Select cisDoc from CISDocument cisDoc where cisDoc.docId =: docId")
	CISDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select cisDoc from CISDocument cisDoc where cisDoc.disCisId =: disCisId")
	List<CISDocument> getListBydisCisId(@Param(value = "disCisId") String disCisId);

	CISDocument findBydisCisId(String disCisId);

	CISDocument findByDocId(String docId);

}
