package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.PrepareAgendaNotes;

@Repository
public interface PrepareAgendaNoteRepository extends JpaRepository<PrepareAgendaNotes, String> {

	@Override
	List<PrepareAgendaNotes> findAll();

	public PrepareAgendaNotes getPrepareByAppliId(String appliId);

	@Query(value = "From PrepareAgendaNotes dptAgenda where dptAgenda.status IN ('Submitted To Committee') and dptAgenda.categoryIndsType in ('Mega', 'Super Mega', 'Mega Plus')")
	public List<PrepareAgendaNotes> findAllPrepareAgendaNotebyStatus();

	@Query(value = "From PrepareAgendaNotes dptAgenda where dptAgenda.status IN ('Submitted To Committee') and dptAgenda.categoryIndsType in ('Large')")
	public List<PrepareAgendaNotes> findLargePrepareAgendaNotebyStatus();

	@Query(value = "select danot.appliId, danot.companyName, danot.investment, danot.categoryIndsType, danot.region, danot .district, mom.gosNo from PrepareAgendaNotes danot join MinutesOfMeeting mom on danot.appliId = mom.gosAppID and mom.gosNo IS NOT NULL and danot.status in ('Submitted To Committee') and danot.categoryIndsType in ('Mega', 'Super Mega', 'Mega Plus')")
	public List<PrepareAgendaNotes> AllPrepareAgendaNotebyStatus();

	@Query(value = "select danot.appliId, danot.companyName, danot.investment, danot.categoryIndsType, danot.region, danot .district, mom.gosNo from PrepareAgendaNotes danot join MinutesOfMeeting mom on danot.appliId = mom.gosAppID and mom.gosNo IS NULL and danot.status in ('Submitted To Committee') and danot.categoryIndsType in ('Large')")
	public List<PrepareAgendaNotes> getPrepareAgendaNotebyStatus();

	@Query("From PrepareAgendaNotes pan where pan.appliId=:appliId")
	public PrepareAgendaNotes findByAppliId(String appliId);

	@Query("From PrepareAgendaNotes pan where pan.appliId=:appliId")
	public List<PrepareAgendaNotes> findPrepAgendaNotesListByApplId(@Param(value = "appliId") String appliId);

	@Query("Select pan from PrepareAgendaNotes pan where pan.userId=:userId")
	public List<PrepareAgendaNotes> findPrepAgendaNotesListByUserId(@Param(value = "userId") String userid);

	@Query(value = "select  p.appliId, p.companyName, p.investment, p.region, p.district, p.status  from PrepareAgendaNotes p where p.status = ('Sent To Concerned Department')")
	public List<PrepareAgendaNotes> getAllPrepareAgendaNotebyStatus();

	@Query(value = "select danot.appliId, danot.status, danot.submissionDate, danot.approvalDate, cirdept.deptName, cirdept.noteReportStatus, cirdept.fileId from PrepareAgendaNotes danot join CirculateToDepartment cirdept on danot.appliId = cirdept.AppId and danot.categoryIndsType in ('Large')")
	public List<Object> findAllLargePrepareAgendaNotebyStatus();

	@Query(value = "select danot.appliId, danot.status, danot.submissionDate, danot.acsapprovalDate, cirdept.deptName, cirdept.noteReportStatus, cirdept.fileId from PrepareAgendaNotes danot join CirculateToDepartment cirdept on danot.appliId = cirdept.AppId and danot.categoryIndsType in ('Mega', 'Super Mega', 'Mega Plus')")
	public List<Object> findAllMegaPrepareAgendaNotebyStatus();

}
