package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.EPFDocument;

@Repository
public interface EPFDocumentRepository extends MongoRepository<EPFDocument, String> {

	@Query("Select epfDoc from EPFDocument epfDoc where epfDoc.disEpfId =: disEpfId")
	EPFDocument getDocBydisEpfId(@Param(value = "disEpfId") String disEpfId);

	@Query("Select epfDoc from EPFDocument epfDoc where epfDoc.docId =: docId")
	EPFDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select epfDoc from EPFDocument epfDoc where epfDoc.disEpfId =: disEpfId")
	List<EPFDocument> getListBydisEpfId(@Param(value = "disEpfId") String disEpfId);

	EPFDocument findBydisEpfId(String disEpfId);

	EPFDocument findByDocId(String docId);

}
