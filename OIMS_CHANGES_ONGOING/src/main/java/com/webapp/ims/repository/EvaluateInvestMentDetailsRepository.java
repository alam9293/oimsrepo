package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.EvaluateInvestmentDetails;

@Repository
public interface EvaluateInvestMentDetailsRepository extends JpaRepository<EvaluateInvestmentDetails, String>{

	public EvaluateInvestmentDetails getEvalInvestDetByapplId(String appId);
}
