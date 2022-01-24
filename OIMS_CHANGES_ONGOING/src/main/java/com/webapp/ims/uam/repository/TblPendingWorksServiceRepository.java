package com.webapp.ims.uam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblApprovalWorks;

@Repository
public interface TblPendingWorksServiceRepository extends JpaRepository<TblApprovalWorks, String>
{
    
	@Query(" from TblApprovalWorks tbl where tbl.taskId=:taskId")
	TblApprovalWorks getRecord(long taskId);
    
	@Query("select tbl from TblApprovalWorks tbl where tbl.creator!=:creator and tbl.taskStatus=9 and  tbl.idmsStatus IN (:idmsstatsList) order by tbl.taskId ")
	List<TblApprovalWorks> getListofPendingDataTobeApproved(String creator,ArrayList<String> idmsstatsList);
	
	/*@Query("select tbl.Task_Query,tbl.task_Message,tbl.Task_Subject,tbl.Activity,tbl.Module_Name,tbl.Task_Purpose,	tbl.Task_Status,tbl.Task_Creator_Role,tbl.Task_Approver_Role,tbl.Creator,tbl.Creator_Date,tbl.Approver,tbl.Approver_Date,tbl.Approver_Display_String,tbl.Idms_Status,tbl.Whereclause,tbl.Creator_Ip_Address,tbl.Approver_Ip_Address from TblApprovalWorks tbl where tbl.Creator!=:creator and tbl.Task_Status='9'")
	List<TblApprovalWorks> getListofPendingDataTobeApproved(String creator);*/

}