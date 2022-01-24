package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;
import com.webapp.ims.food.Model.SubsidyCalForFobValueTQM;

@Repository
public interface SubCalFOBValueRepository extends JpaRepository<SubsidyCalForFobValueTQM, String>{
	
	public List<SubsidyCalForFobValueTQM> getDetailsByunitId(String unitId);

}
