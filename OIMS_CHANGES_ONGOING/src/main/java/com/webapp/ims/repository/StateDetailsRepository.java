package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.StateDetails;

@Repository
public interface StateDetailsRepository extends JpaRepository<StateDetails, Long> {

	public StateDetails getStateBystateCode(long stateCode);

	@Override
	public List<StateDetails> findAll();
	
	@Query("Select std.stateName from StateDetails std where std.stateCode=:stateCode")
	public String findByStateCode(@Param(value = "stateCode")Long statecode);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */

}
