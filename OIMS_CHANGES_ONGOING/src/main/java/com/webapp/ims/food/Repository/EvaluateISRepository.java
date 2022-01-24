/**
 * Author:: Pankaj Sahu
* Created date::06/05O/2021
 */

package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvalutionViewIS;

@Repository
public interface EvaluateISRepository extends JpaRepository<FoodEvalutionViewIS, String> {

	public FoodEvalutionViewIS getDetailsByunitid(String unitid);
}
