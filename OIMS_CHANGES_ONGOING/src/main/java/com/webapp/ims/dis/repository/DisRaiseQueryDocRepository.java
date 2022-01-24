package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisRaiseQueryDocument;

@Repository
public interface DisRaiseQueryDocRepository extends MongoRepository<DisRaiseQueryDocument, String> {

	DisRaiseQueryDocument findByDocAppId(String docAppId);

	@Query("Select rq.docAppId from DisRaiseQueryDocument rq where rq.docUserId=:userId")
	public List<String> getApplIdListByDocUserId(@Param(value = "userId") String userId);

	DisRaiseQueryDocument findByDocId(String docId);

}
