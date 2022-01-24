/**
 * Author:: Pankaj
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisEligibleAmtCIS;

@Repository
public interface DisEligibleAmtCISRepository extends JpaRepository<DisEligibleAmtCIS, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankaj
	 */
	DisEligibleAmtCIS getDetailsByeligibleAmtId(String eligibleAmtId);
	
	@Query(value="select a from DisEligibleAmtCIS a where  a.apcId=:apcId")
	DisEligibleAmtCIS findDisEligibleAmtCISByAppId(String apcId);

}
