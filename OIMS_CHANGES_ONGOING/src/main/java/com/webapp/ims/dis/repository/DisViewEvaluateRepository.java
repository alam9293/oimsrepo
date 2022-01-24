/**
 * Author:: Gyan
* Created on:: 
* Created date::03/02/2021
 */

package com.webapp.ims.dis.repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisViewEvaluate;

@Repository
public interface DisViewEvaluateRepository extends JpaRepository<DisViewEvaluate, String> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value) Create by Pankajll
	 */
	DisViewEvaluate getDetailsByEvaluateId(String evaluateId);
	
	
	public static List<DisViewEvaluate> filterDisViewEvaluate(List<DisViewEvaluate> disViewEvaluateList,
			Predicate<DisViewEvaluate> predicate) {
		return disViewEvaluateList.stream().filter(predicate).collect(Collectors.toList());
	}

	@Query(value="select a from DisViewEvaluate a where  a.apcId=:apcId")
 	 DisViewEvaluate  findDisViewEvaluateToAppId( String apcId);

}
