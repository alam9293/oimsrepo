package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.EligiblePlantMachEvalutionPMKSY;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;

@Repository
public interface EligibleCostOfPlantMachPMKSYRepository extends JpaRepository<EligiblePlantMachEvalutionPMKSY, String>{

	@Query("from EligiblePlantMachEvalutionPMKSY epnm where epnm.unitId =:unitId")
	public List<EligiblePlantMachEvalutionPMKSY> findAllByunitId(String unitId);
}
