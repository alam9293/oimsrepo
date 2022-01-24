/**
 * Author:: Pankaj
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.EPFComputationAndEligibility;

@Repository
public interface DisEPFCompAndElgblRepository extends JpaRepository<EPFComputationAndEligibility, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankaj
	 */
	List<EPFComputationAndEligibility> getDetailsByepfComputeId(String epfComputeId);

	@Override
	public EPFComputationAndEligibility save(EPFComputationAndEligibility epfComputationAndEligibility);
	
	@Query(value = "from EPFComputationAndEligibility where epfApcId = :epfApcId")
	public List<EPFComputationAndEligibility> findAllByEPFApcId(String epfApcId);
	
	@Query(value="from EPFComputationAndEligibility where epfComputeId=:epfComputeId")
	EPFComputationAndEligibility findByEPFId(String epfComputeId);

}
