package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.InvestmentDocument;
@Repository
public interface InvestmentDocumentRepository extends MongoRepository<InvestmentDocument, String> {

	@Query("Select invDoc from InvestmentDocument invDoc where invDoc.investId=:investId")
	InvestmentDocument getInvDocByInvestId(@Param(value = "investId")String investId);
	@Query("Select invDoc from InvestmentDocument invDoc where invDoc.docId=:docId")
	InvestmentDocument getInvDocByDocId(@Param(value = "docId")String docId);
	
	@Query("Select invDoc from InvestmentDocument invDoc where invDoc.investId=:investId")
	List<InvestmentDocument> getInvDocListByInvestId(@Param(value = "investId")String investId);
	
	InvestmentDocument findByInvestId(String investId);
	InvestmentDocument findByDocId(String docId);
	
}
