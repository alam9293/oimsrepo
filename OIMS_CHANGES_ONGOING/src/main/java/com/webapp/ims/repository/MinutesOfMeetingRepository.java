package com.webapp.ims.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.CirculateToDepartment;
import com.webapp.ims.model.MinutesOfMeeting;

@Repository
public interface MinutesOfMeetingRepository extends JpaRepository<MinutesOfMeeting, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	List<MinutesOfMeeting> getByuserId(String userId);
	List<MinutesOfMeeting> getBygosAppID(String appId);
	//MinutesOfMeeting getBygosAppID(String appId);

	@Query(value = "select mom.id, mom.gosAppID, mom.minutesOfMeetingOrGos, mom.committeeDepartments, mom.dateOfMeeting, mom.committeeName, mom.gosName, cirdept.deptName, cirdept.noteReportStatus, mom.gosNo from MinutesOfMeeting mom join CirculateToDepartment cirdept on mom.gosAppID = cirdept.AppId where cirdept.deptName =:deptName")
	public List<Object> findAllMOMbyStatus(String deptName);
	
	
	@Query(value = "select mom.id from  MinutesOfMeeting mom  where mom.gosAppID =:correctedappId and mom.minutesOfMeetingOrGos =:commetieeType")
	public String searchByGosAppIdAndCommitee(String correctedappId, String commetieeType);
}
