package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.BusinessEntityDocument;
@Repository
public interface BusinessEntityDocumentRepository extends MongoRepository<BusinessEntityDocument, String> {
	
	@Query("Select beDoc from BusinessEntityDocument beDoc where beDoc.docBusEntityId=:docBusEntityId")
	BusinessEntityDocument getBusinessEntityDocByBusinessEntityId(@Param(value = "docBusEntityId")String docBusEntityId);
	@Query("Select beDoc from BusinessEntityDocument beDoc where beDoc.docId=:docId")
	BusinessEntityDocument getBusinessEntityDocByDocId(@Param(value = "docId")String docId);
	
	@Query("Select beDoc from BusinessEntityDocumen beDoc where beDoc.businessEntityId=:businessEntityId")
	List<BusinessEntityDocument> getBusinessDocListByBusinessEntityId(@Param(value = "businessEntityId")String businessEntityId);
	
	BusinessEntityDocument findByBusinessEntityId(String businessEntityId);
	BusinessEntityDocument findByDocId(String docId);
	
}
