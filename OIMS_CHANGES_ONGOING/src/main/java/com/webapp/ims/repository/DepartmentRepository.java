package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

	@Query("Select dept.deptId from Department dept where dept.newDeptName=:newDeptName")
	public String findDeptIdBynewDeptName(@Param(value = "newDeptName") String deptName);

}
