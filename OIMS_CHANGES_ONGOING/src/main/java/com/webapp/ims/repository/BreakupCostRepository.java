package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.InvBreakupCost;


@Repository
public interface BreakupCostRepository extends JpaRepository<InvBreakupCost, String> {	
	@Query("Select invbkc From InvBreakupCost invbkc where invbkc.brkupId=:brkupId")	
	public InvBreakupCost getInvBreakupCostById(@Param(value = "brkupId")String brkupId);

}
