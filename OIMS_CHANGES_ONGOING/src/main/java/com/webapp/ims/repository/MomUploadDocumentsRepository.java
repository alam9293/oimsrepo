package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.MomUploadDocuments;

@Repository
public interface MomUploadDocumentsRepository extends MongoRepository<MomUploadDocuments, String>{

	@Query("Select mud from MomUploadDocuments mud where mud.momId=:momId")
	MomUploadDocuments getMomByMomId(@Param(value = "momId")String momId);
}
