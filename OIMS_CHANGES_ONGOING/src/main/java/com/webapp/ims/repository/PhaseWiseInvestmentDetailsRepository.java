package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.PhaseWiseInvestmentDetails;

@Repository
public interface PhaseWiseInvestmentDetailsRepository extends JpaRepository<PhaseWiseInvestmentDetails, String> {

	@Query("Select pw from PhaseWiseInvestmentDetails pw where pw.pwInvId=:pwInvId")
	public List<PhaseWiseInvestmentDetails> getPwInvDetailsById(@Param(value = "pwInvId") String id);

	@Query("Select pw from PhaseWiseInvestmentDetails pw where pw.pwId=:pwId")
	public PhaseWiseInvestmentDetails getPwInvDetailsByPwId(@Param(value = "pwId") String id);

	@Query("Select pwPhaseNo from PhaseWiseInvestmentDetails pw where pw.pwInvId=:pwInvId")
	public List<String> getPhasesBypwInvId(String pwInvId);

	@Query("from PhaseWiseInvestmentDetails where pwApcId=:apcId")
	List<PhaseWiseInvestmentDetails> findByPwApcId(String apcId);
	
	@Query("from PhaseWiseInvestmentDetails where pwApcId=:apcId")
	PhaseWiseInvestmentDetails findPWDetailsByPwApcId(String apcId);
	
	@Query("from PhaseWiseInvestmentDetails where pwInvId=:pwInvId")
	List<PhaseWiseInvestmentDetails> getPhaseNoObj(String pwInvId);

}
