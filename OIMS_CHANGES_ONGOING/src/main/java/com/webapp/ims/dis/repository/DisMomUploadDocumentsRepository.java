package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.MomUploadDocumentsDis;

@Repository
public interface DisMomUploadDocumentsRepository extends MongoRepository<MomUploadDocumentsDis, String> {

	@Query("Select mud from MomUploadDocumentsDis mud where mud.momId=:momId")
	MomUploadDocumentsDis getMomByMomId(@Param(value = "momId") String momId);
}
