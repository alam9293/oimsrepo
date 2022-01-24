package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.EvaluationAuditTrail;

@Repository
@Transactional
public interface EvaluateAuditTrialRepository extends JpaRepository<EvaluationAuditTrail, String>
{
  
	@Query(" from EvaluationAuditTrail where appliId = :appliId and userId = :userId ")
	public List<EvaluationAuditTrail> getEvaluAudTraByAppliIdUserId(String appliId,String userId);
}
