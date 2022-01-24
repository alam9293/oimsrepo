package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.MinutesOfMeetingDis;

@Repository
public interface DisMinutesOfMeetingRepository extends JpaRepository<MinutesOfMeetingDis, String> {


	List<MinutesOfMeetingDis> getByuserId(String userId);

	List<MinutesOfMeetingDis> getBygosAppID(String appId);

	@Query(value = "select dismom.id, dismom.gosAppID, dismom.minutesOfMeetingOrGos, dismom.committeeDepartments, dismom.dateOfMeeting, dismom.committeeName, dismom.gosName, cirdeptDis.deptName, cirdeptDis.noteReportStatus, dismom.gosNo from MinutesOfMeetingDis dismom join CirculateToDepartmentDis cirdeptDis on dismom.gosAppID = cirdeptDis.AppId where cirdeptDis.deptName =:deptName")
	public List<Object> findAllDisMOMbyStatus(String deptName);

	@Query(value = "select dismom.id from  MinutesOfMeetingDis dismom  where dismom.gosAppID =:correctedappId and dismom.minutesOfMeetingOrGos =:commetieeType")
	public String searchByGosAppIdAndCommitee(String correctedappId, String commetieeType); 
	

}
