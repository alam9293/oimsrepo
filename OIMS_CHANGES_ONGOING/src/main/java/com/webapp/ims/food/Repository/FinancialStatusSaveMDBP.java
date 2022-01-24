package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FinancialStatusEvaluateViewMDBP;

@Repository
public interface FinancialStatusSaveMDBP extends JpaRepository<FinancialStatusEvaluateViewMDBP, String> {
	
	public List<FinancialStatusEvaluateViewMDBP> getDetailsByUnitId(String unitId);

}
