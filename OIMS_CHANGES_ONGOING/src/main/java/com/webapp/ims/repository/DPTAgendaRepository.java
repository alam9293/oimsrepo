package com.webapp.ims.repository;

//gopal
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DPTAgendaNotes;

@Repository
public interface DPTAgendaRepository extends JpaRepository<DPTAgendaNotes, String> {
	@Query("Select status From DPTAgendaNotes dpt where dpt.dptApcId=:dptApcId")
	public String getStatusByDptActId(@Param(value = "dptApcId") String dptApcId);
	
	

}
