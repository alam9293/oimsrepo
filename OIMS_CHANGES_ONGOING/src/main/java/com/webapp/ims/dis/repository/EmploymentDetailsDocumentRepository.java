package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.EmploymentdetailsDocument;

@Repository
public interface EmploymentDetailsDocumentRepository extends MongoRepository<EmploymentdetailsDocument, String> {

	@Query("Select emplDetlDoc from EmploymentdetailsDocument emplDetlDoc where emplDetlDoc.emplDtlId =: emplDtlId")
	EmploymentdetailsDocument getDocByemplDtlId(@Param(value = "emplDtlId") String emplDtlId);

	@Query("Select emplDetlDoc from EmploymentdetailsDocument emplDetlDoc where emplDetlDoc.docId =: docId")
	EmploymentdetailsDocument getDocByDocId(@Param(value = "docId") String docId);

	@Query("Select emplDetlDoc from EmploymentdetailsDocument emplDetlDoc where emplDetlDoc.emplDtlId =: emplDtlId")
	List<EmploymentdetailsDocument> getListByemplDtlId(@Param(value = "emplDtlId") String emplDtlId);

	EmploymentdetailsDocument findByemplDtlId(String emplDtlId);

	EmploymentdetailsDocument findByDocId(String docId);

}
