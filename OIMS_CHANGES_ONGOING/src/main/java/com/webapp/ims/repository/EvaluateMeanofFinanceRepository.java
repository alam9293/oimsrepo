package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.EvaluateMeanOfFinance;

@Repository
public interface EvaluateMeanofFinanceRepository extends JpaRepository<EvaluateMeanOfFinance, String> {

	public List<EvaluateMeanOfFinance> findByemfApplcId(String applicantId);

}
