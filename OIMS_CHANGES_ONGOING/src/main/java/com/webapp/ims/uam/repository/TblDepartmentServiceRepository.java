package com.webapp.ims.uam.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblDepartment;

@Repository
public interface TblDepartmentServiceRepository extends JpaRepository<TblDepartment,String> 
{
	
	@Query(" from TblDepartment tbldept  where status='E' order by deptName")
	public List<TblDepartment> getEnableDepartmentName();
	
	@Query(" from TblDepartment tbldept  where status='D' order by deptName")
	public List<TblDepartment> getDisableDepartmentName();
	
	@Query(" from TblDepartment tbldept  where deptId=:DeptId")
	public TblDepartment getDepartmentbyId(String DeptId);
	
}
