package com.webapp.ims.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.RaiseQuery;

@Repository
public interface RaiseQueryRepository extends JpaRepository<RaiseQuery, String> {

	@Query("Select rq From RaiseQuery rq where rq.rqApplId=:applicationId")
	public RaiseQuery findRaiseQueryByApplicationId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq From RaiseQuery rq where rq.rqId=:rqId")
	public RaiseQuery findRaiseQueryById(@Param(value = "rqId") String rqId);
	
	@Query("Select rq From RaiseQuery rq where rq.id=:id")
	public RaiseQuery getRaiseQueryById(int id);

	@Query("Select rq.rqApplId from RaiseQuery rq where rq.rqUserId=:userId")
	public List<String> getApplicationIdList(@Param(value = "userId") String userId);

	@Query("Select rq.rqId from RaiseQuery rq where rq.rqApplId=:applicationId")
	public String findQueryIdByApplId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq.rqId from RaiseQuery rq where rq.rqApplId=:applicationId")
	public List<String> getQueryIdListByApplId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq from RaiseQuery rq where rq.rqUserId=:rqUserId")
	public List<RaiseQuery> getRaiseQueryListByUserId(@Param(value = "rqUserId") String userId);

	@Query("Select u.department from Login u where u.id=:userId")
	public String findDepartmentByUserId(@Param(value = "userId") String userid);

	@Query(value = "select resq.rqApplId, resq.rqClarifySought, resq.rqMissdocdtl , resq.rqFilename , resq.rqFiledata, resp.respClarifyDtl, resp.respFilename, resp.respFiledata "
			+ "  from RaiseQuery resq join ResponseQuery resp on resq.rqApplId = resp.respApplId")
	public List<Tuple> getAllDetailsByAppIdTuple1();

	
	@Query("Select rq from RaiseQuery rq where rq.rqStatus=:rqStatus")
	public List<RaiseQuery> findRaiseQueryActive(@Param(value = "rqStatus")String rqStatus);
	
	@Query("Select rq from RaiseQuery rq where rq.rqApplId=:applId and rq.rqStatus=:rqStatus")
	public List<RaiseQuery> searchByApplicantId(@Param(value = "applId")String applId ,@Param(value = "rqStatus")String rqStatus);

	@Modifying
	@Query("update RaiseQuery r set r.rqStatus =:status where r.rqApplId=:appId")
	public List<RaiseQuery> updateStatusByApplicantId(String appId, String status);

}
