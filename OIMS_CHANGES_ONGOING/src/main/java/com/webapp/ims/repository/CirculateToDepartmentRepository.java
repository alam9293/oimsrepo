/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.CirculateToDepartment;

@Repository
public interface CirculateToDepartmentRepository extends JpaRepository<CirculateToDepartment, String> {

	@Query(value = "select dscpdf.file from CirculateToDepartment cirdept join DSCPdfUploadEntity dscpdf on dscpdf.id = cirdept.fileId where cirdept.AppId =:AppId")
	List<byte[]> getAgendaReportData(String AppId);

	@Query(value = "select cirdept from  CirculateToDepartment cirdept  where cirdept.AppId =:AppId and cirdept.deptName=:deptName and cirdept.noteReportStatus=:noteReportStatus")
	public List<CirculateToDepartment> searchExsiting(String AppId, String deptName, String noteReportStatus);

	@Query(value = "select cirdept from  CirculateToDepartment cirdept  where cirdept.AppId =:AppId and cirdept.deptId=:deptId")
	List<CirculateToDepartment> SearchByAppIdAnddeptId(String AppId, String deptId);
}
