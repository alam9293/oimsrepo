package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.ResponseQuery;

@Repository
public interface ResponseQueryRepository extends JpaRepository<ResponseQuery, Long> {
	@Query("Select rq.respApplId from ResponseQuery rq where rq.respUserId=:userId")
	public List<String> getApplicanttionIdList(@Param(value = "userId") String userId);

	@Query("Select rq From ResponseQuery rq where rq.respId=:responseId")
	public ResponseQuery findResponseQueryById(@Param(value = "responseId") String responseId);

	@Query("Select rq From ResponseQuery rq where rq.respApplId=:applicationId")
	public ResponseQuery findResponseQueryByApplicationId(@Param(value = "applicationId") String applicationId);

	@Query("Select rq from ResponseQuery rq where rq.respUserId=:respUserId")
	public List<ResponseQuery> getResponseQueryListByUserId(@Param(value = "respUserId") String userId);
	
	@Query("Select rq from RaiseQuery rq where rq.rqId=:rqId")
	public List<RaiseQuery> findRaisedQueryListByQueryId(@Param(value = "rqId") String respRqid);
	

}
