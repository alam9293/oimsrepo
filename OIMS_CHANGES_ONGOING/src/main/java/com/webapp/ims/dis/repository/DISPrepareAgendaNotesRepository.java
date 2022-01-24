package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.model.PrepareAgendaNotes;

@Repository
public interface DISPrepareAgendaNotesRepository extends JpaRepository<DISPrepareAgendaNotes, String> {

	public PrepareAgendaNotes findPrepAgendaNotesByAppliId(String appId);

	public DISPrepareAgendaNotes findDisPrepAgendaNotesByAppliId(String appId);

	@Query(value = "From DISPrepareAgendaNotes dptAgenda where dptAgenda.status IN ('Submitted To Committee') and dptAgenda.categoryIndsType in ('Mega', 'Super Mega', 'Mega Plus')")
	public List<DISPrepareAgendaNotes> findAllPrepareAgendaNotebyStatus();

	@Query(value = "From DISPrepareAgendaNotes dptAgenda where dptAgenda.status IN ('Submitted To Committee') and dptAgenda.categoryIndsType in ('Large')")
	public List<DISPrepareAgendaNotes> findLargePrepareAgendaNotebyStatus();

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@Override
	public DISPrepareAgendaNotes save(DISPrepareAgendaNotes disPrepareAgendaNotes);

	@Query("Select pan from DISPrepareAgendaNotes pan where pan.userId=:userId")
	public List<DISPrepareAgendaNotes> findDisPrepAgendaNotesByUserId(@Param(value = "userId") String userid);

	// public List<DISPrepareAgendaNotes> getAllDISPrepareAgendaNote();

	public DISPrepareAgendaNotes getPrepareByAppliId(String appliId);

	@Query("Select status From DISPrepareAgendaNotes dpt where dpt.appliId=:appliId")
	public String findStatusByappliId(String appliId);

	@Query(value = "select  p.appliId, p.companyName, p.investment, p.region, p.district, p.status  from DISPrepareAgendaNotes p where p.status = ('Sent To Concerned Department')")
	public List<DISPrepareAgendaNotes> getDisAllPrepareAgendaNotebyStatus();
	
	@Query(value = "select danot.appliId, danot.status, danot.submissionDate, danot.acsapprovalDate, cirdept.deptName, cirdept.noteReportStatus, cirdept.fileId from DISPrepareAgendaNotes danot join CirculateToDepartmentDis cirdept on danot.appliId = cirdept.AppId and danot.categoryIndsType in ('Mega', 'Super Mega', 'Mega Plus')")
	public List<Object> findAllDisMegaPrepareAgendaNotebyStatus();
	
	@Query(value = "select danot.appliId, danot.status, danot.submissionDate, danot.approvalDate, cirdept.deptName, cirdept.noteReportStatus, cirdept.fileId from DISPrepareAgendaNotes danot join CirculateToDepartmentDis cirdept on danot.appliId = cirdept.AppId and danot.categoryIndsType in ('Large')")
	public List<Object> findAllDisLargePrepareAgendaNotebyStatus();
	
	
}
