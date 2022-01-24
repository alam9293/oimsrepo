package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.EligibleCostOfCivilWorkPMKSY;
import com.webapp.ims.food.Model.EligiblePlantMachEvalutionPMKSY;

@Repository
public interface EligibleCostOfCivilWorkPMKSYRepository extends JpaRepository<EligibleCostOfCivilWorkPMKSY, String> {

	@Query("from EligibleCostOfCivilWorkPMKSY epnm where epnm.unitId =:unitId")
	public List<EligibleCostOfCivilWorkPMKSY> findAllByunitId(String unitId);
}
