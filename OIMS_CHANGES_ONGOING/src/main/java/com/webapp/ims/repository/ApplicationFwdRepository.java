package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.ApplicationFwdEntity;

@Repository
public interface ApplicationFwdRepository extends JpaRepository<ApplicationFwdEntity, Integer> {

	@Query("Select appdtl from ApplicationFwdEntity appdtl where appdtl.appId = :appId ")
	List<ApplicationFwdEntity> findByAppId(@Param(value = "appId") String appId);

	@Query("Select appdtl from ApplicationFwdEntity appdtl where appdtl.appId = :appId and appdtl.status=:status")
	List<ApplicationFwdEntity> findByAppId(@Param(value = "appId") String appId,
			@Param(value = "status") boolean status);

	@Query("Select appdtl.appId from ApplicationFwdEntity appdtl where appdtl.rolname = :rolname and appdtl.status=:status")
	List<String> searchApplicationByRole(@Param(value = "rolname") String rolname,
			@Param(value = "status") boolean status);

	@Query("Select appdtl.appId from ApplicationFwdEntity appdtl where appdtl.rolname = :rolname and appdtl.status=:status")
	List<String> searchApplicationBySendByApproval(@Param(value = "rolname") String rolname,
			@Param(value = "status") boolean status);

}
