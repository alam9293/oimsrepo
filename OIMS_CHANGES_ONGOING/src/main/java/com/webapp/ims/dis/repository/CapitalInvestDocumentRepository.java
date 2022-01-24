package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.CapitalInvestmentDocument;

@Repository
public interface CapitalInvestDocumentRepository extends MongoRepository<CapitalInvestmentDocument, String> {

	@Query("Select capInvDoc from CapitalInvestmentDocument capInvDoc where capInvDoc.capInvId =: capInvId")
	CapitalInvestmentDocument getDocBycapInvId(@Param(value = "capInvId") String capInvId);

	@Query("Select capInvDoc from CapitalInvestmentDocument capInvDoc where capInvDoc.docId =: docId")
	CapitalInvestmentDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select capInvDoc from CapitalInvestmentDocument capInvDoc where capInvDoc.capInvId =: capInvId")
	List<CapitalInvestmentDocument> getListBycapInvId(@Param(value = "capInvId") String capInvId);

	CapitalInvestmentDocument findBycapInvId(String capInvId);

	CapitalInvestmentDocument findByDocId(String docId);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	CapitalInvestmentDocument getCapitalInvestDocumentBycapInvId(String capInvId);

}
