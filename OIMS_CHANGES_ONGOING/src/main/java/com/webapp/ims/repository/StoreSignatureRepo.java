package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.webapp.ims.model.StoreSignatureEntity;

public interface StoreSignatureRepo extends CrudRepository<StoreSignatureEntity, Integer> {

	@Query("select a from StoreSignatureEntity a where a.appId =(:appId)")
	List<StoreSignatureEntity> findSignatureByAppId(String appId);

	@Query("select a from StoreSignatureEntity a where a.id =(:id)")
	StoreSignatureEntity findSignatureById(int id);
	
	

}
