package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;

public interface DeptPhaseWiseInvestmentDetailsRepository
		extends JpaRepository<Dept_PhaseWiseInvestmentDetails, String> {

	List<Dept_PhaseWiseInvestmentDetails> findByPwApcId(String applicantId);

	@Query(value = "Select pwProductName from Dept_PhaseWiseInvestmentDetails where pwApcId=:pwApcId ")
	public List<String> findProductListByApplId(@Param(value = "pwApcId") String appId);
	
	@Query("select   dpid from Dept_PhaseWiseInvestmentDetails dpid where dpid.pwApcId=:pwApcId")
	public List<Dept_PhaseWiseInvestmentDetails> findbyApplicationId(@Param(value = "pwApcId") String appId);

}
