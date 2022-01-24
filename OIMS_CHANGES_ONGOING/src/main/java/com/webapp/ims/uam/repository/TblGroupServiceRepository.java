package com.webapp.ims.uam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblGroup;

@Repository
public interface TblGroupServiceRepository extends JpaRepository<TblGroup, Integer> {
	
	@Query(" from TblGroup tbl where tbl.groupId=:groupid ")
	TblGroup getGroupNamebyGroupId(String groupid);
	
	@Query(" from TblGroup tbl where tbl.status='E' ")
	List<TblGroup> getEnableGroups();
	
	@Query(" from TblGroup tbl where tbl.status='D' ")
	List<TblGroup> getDisableGroups();

}