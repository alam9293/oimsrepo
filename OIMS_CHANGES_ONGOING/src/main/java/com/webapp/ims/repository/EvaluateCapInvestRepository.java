package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.EvaluateCapitalInvestment;
@Repository
public interface EvaluateCapInvestRepository extends JpaRepository<EvaluateCapitalInvestment, String> {
	
 public List<EvaluateCapitalInvestment>	findByEciApplcId(String applicantId);

}
