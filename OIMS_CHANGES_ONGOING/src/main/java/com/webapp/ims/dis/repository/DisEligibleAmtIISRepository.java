/**
 * Author:: Pankaj
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisEligibleAmtIIS;

@Repository
public interface DisEligibleAmtIISRepository extends JpaRepository<DisEligibleAmtIIS, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankaj
	 */
	DisEligibleAmtIIS getDetailsByeligibleAmtIISId(String eligibleAmtIISId);

	@Query(value="select a from DisEligibleAmtIIS a where  a.apcIdIIS=:apcIdIIS")
	DisEligibleAmtIIS findDisEligibleAmtIISByAPPId(String apcIdIIS);

}

