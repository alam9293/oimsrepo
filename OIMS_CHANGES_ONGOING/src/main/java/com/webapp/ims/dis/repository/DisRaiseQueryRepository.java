package com.webapp.ims.dis.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisRaiseQuery;

@Repository
public interface DisRaiseQueryRepository extends JpaRepository<DisRaiseQuery, String> {

	@Query("Select rq From DisRaiseQuery rq where rq.rqApplId=:applicationId")
	public DisRaiseQuery findRaiseQueryByApplicationId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq From DisRaiseQuery rq where rq.rqId=:rqId")
	public DisRaiseQuery findRaiseQueryById(@Param(value = "rqId") String rqId);

	@Query("Select rq.rqApplId from DisRaiseQuery rq where rq.rqUserId=:userId")
	public List<String> getApplicationIdList(@Param(value = "userId") String userId);

	@Query("Select rq.rqId from DisRaiseQuery rq where rq.rqApplId=:applicationId")
	public String findQueryIdByApplId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq.rqId from DisRaiseQuery rq where rq.rqApplId=:applicationId")
	public List<String> getQueryIdListByApplId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq from DisRaiseQuery rq where rq.rqUserId=:rqUserId")
	public List<DisRaiseQuery> getRaiseQueryListByUserId(@Param(value = "rqUserId") String userId);

	@Query("Select u.department from Login u where u.id=:userId")
	public String findDepartmentByUserId(@Param(value = "userId") String userid);

	@Query(value = "select resq.rqApplId, resq.rqClarifySought, resq.rqMissdocdtl , resq.rqFilename , resq.rqFiledata, resp.respClarifyDtl, resp.respFilename, resp.respFiledata "
			+ "  from RaiseQuery resq join ResponseQuery resp on resq.rqApplId = resp.respApplId")
	public List<Tuple> getAllDetailsByAppIdTuple1();

}
