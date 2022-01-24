package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisReimDisallowedInput;

@Repository
public interface ReimDisAllowedRepository extends JpaRepository<DisReimDisallowedInput, String>{
	
	@Query(value="from DisReimDisallowedInput where apcId =:apcId")
	List<DisReimDisallowedInput> findByApcId(String apcId);
	
	@Query(value="from DisReimDisallowedInput where disallowedId =:disallowedId")
    DisReimDisallowedInput findByDisallowedId(String disallowedId);

}
