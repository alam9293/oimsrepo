package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.IISDocument;

@Repository
public interface IISDocumentRepository extends MongoRepository<IISDocument, String> {

	@Query("Select iisDoc from IISDocument iisDoc where iisDoc.disIISId =: disIISId")
	IISDocument getDocBydisIISId(@Param(value = "disIISId") String disIISId);

	@Query("Select iisDoc from IISDocument iisDoc where iisDoc.docId =: docId")
	IISDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select iisDoc from IISDocument iisDoc where iisDoc.disIISId =: disIISId")
	List<IISDocument> getListBydisIISId(@Param(value = "disIISId") String disIISId);

	IISDocument findBydisIISId(String disIISId);

	IISDocument findByDocId(String docId);

}
