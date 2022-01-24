package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.InvMeansOfFinance;

@Repository
public interface MeansOfFinanceRepository extends JpaRepository<InvMeansOfFinance, String> {
	@Query("Select mof from InvMeansOfFinance mof where mof.mofId=:mofId")
	public InvMeansOfFinance getInvMeansOfFinanceById(@Param(value = "mofId") String mofId);
	
	public List<InvMeansOfFinance> findByMofInvId(String mofInvId);
	
	@Query("Select mof from InvMeansOfFinance mof where mof.pwApcId=:pwApcId")
	public List<InvMeansOfFinance> getInvMeansOfFinanceByApcId(@Param(value = "pwApcId") String pwApcId);
}
