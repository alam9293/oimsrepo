package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.RaiseQueryDocument;
import com.webapp.ims.model.ScrutinyDocument;

@Repository
public interface RaiseQueryDocRepository extends MongoRepository<RaiseQueryDocument, String> {

	RaiseQueryDocument findByDocAppId(String docAppId);
	@Query("Select rq.docAppId from RaiseQueryDocument rq where rq.docUserId=:userId")
	public List<String> getApplIdListByDocUserId(@Param(value = "userId") String userId);
	
	RaiseQueryDocument findByDocId(String docId);
	
	
	@Query("Select rqDoc from RaiseQueryDocument rqDoc where scruDoc.docUserId =: docUserId")
	List<RaiseQueryDocument> getListByDocUserId(String docUserId);
	
	
	

}
