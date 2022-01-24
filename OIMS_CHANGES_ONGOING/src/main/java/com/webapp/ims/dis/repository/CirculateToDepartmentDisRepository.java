package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.CirculateToDepartmentDis;

@Repository
public interface CirculateToDepartmentDisRepository extends JpaRepository<CirculateToDepartmentDis, String>{

	@Query(value = "select cirdept from  CirculateToDepartmentDis cirdept  where cirdept.AppId =:AppId and cirdept.deptName=:deptName and cirdept.noteReportStatus=:noteReportStatus")
	public List<CirculateToDepartmentDis> searchExsiting(String AppId,String deptName, String noteReportStatus);
}
