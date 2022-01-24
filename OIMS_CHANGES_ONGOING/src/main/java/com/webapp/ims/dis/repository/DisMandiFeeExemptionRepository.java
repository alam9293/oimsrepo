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

import com.webapp.ims.dis.model.MandiFeeExemption;
import com.webapp.ims.dis.model.StampDutyExemption;

@Repository
public interface DisMandiFeeExemptionRepository extends JpaRepository<MandiFeeExemption, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankaj
	 */
	MandiFeeExemption getDetailsBymandiFeeExeId(String epfComputeId);

	@SuppressWarnings("unchecked")
	@Override
	public MandiFeeExemption save(MandiFeeExemption mandiFeeExemption);
	
	@Query(value="from MandiFeeExemption where apcId = :apcId")
	List<MandiFeeExemption> getDetailsByApcId(String apcId);
	
	@Query(value = "from MandiFeeExemption where mandiFeeExeId = :mandiFeeExeId ")
	MandiFeeExemption findByMandiFeeExeId(String mandiFeeExeId); 

}
