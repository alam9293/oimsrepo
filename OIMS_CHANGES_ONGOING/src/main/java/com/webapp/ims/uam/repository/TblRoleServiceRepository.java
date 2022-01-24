package com.webapp.ims.uam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblRole;

@Repository
public interface TblRoleServiceRepository extends JpaRepository<TblRole, Integer> {
	
	@Query(" from TblRole tbl where tbl.roleId=:roleid ")
	TblRole getRoleNamebyRoleId(String roleid);

	@Query(" from TblRole tbl where tbl.status='E' ")
	List<TblRole> getEnableRoles();
	
	@Query(" from TblRole tbl where tbl.status='D' ")
	List<TblRole> getDisableRoles();

}