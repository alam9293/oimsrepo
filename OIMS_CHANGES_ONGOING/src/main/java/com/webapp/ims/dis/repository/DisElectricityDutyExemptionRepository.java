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

import com.webapp.ims.dis.model.ElectricityDutyExemption;
import com.webapp.ims.dis.model.StampDutyExemption;

@Repository
public interface DisElectricityDutyExemptionRepository extends JpaRepository<ElectricityDutyExemption, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankaj
	 */
	ElectricityDutyExemption getDetailsByelectricDutyExeId(String electricDutyExeId);
	
	

	List<ElectricityDutyExemption> findByapcIdAndElectricityTypeStatus(String apcId, String electricityTypeStatus);

	
	@Query(value = "from ElectricityDutyExemption where electricDutyExeId = :electricDutyExeId ")
	ElectricityDutyExemption findAllByelectricDutyExeId(String electricDutyExeId); 
	
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public ElectricityDutyExemption save(ElectricityDutyExemption
	 * electricityDutyExemption);
	 */

}
