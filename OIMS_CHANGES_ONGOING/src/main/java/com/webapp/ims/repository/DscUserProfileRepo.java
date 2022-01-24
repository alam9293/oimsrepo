package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.DscUserProfileEntity;

public interface DscUserProfileRepo extends CrudRepository<DscUserProfileEntity, Integer> {

	@Query("select a from DscUserProfileEntity a where a.serialNo =(:serialNo)")
	DscUserProfileEntity searchUserBySerialNo(String serialNo);

}
