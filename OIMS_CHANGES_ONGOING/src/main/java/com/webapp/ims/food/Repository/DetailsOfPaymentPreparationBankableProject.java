package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL;

@Repository
public interface DetailsOfPaymentPreparationBankableProject extends JpaRepository<DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL, String>{

	@Query("from DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL mftqm where mftqm.unit_id =:unitId")
	List<DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL> findDetailsByUnitId(String unitId);
}
