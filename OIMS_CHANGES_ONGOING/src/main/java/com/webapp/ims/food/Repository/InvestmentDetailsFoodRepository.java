package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.BusinessEntityDetailsFood;
import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.InvestmentDetailsFood;
import com.webapp.ims.food.Model.MeansofFinancingTQM;

@Repository
public interface InvestmentDetailsFoodRepository extends JpaRepository<InvestmentDetailsFood, String>{

	@Query(value = "from InvestmentDetailsFood bedf")
	public List<InvestmentDetailsFood> getAll();
	
	@Query("from InvestmentDetailsFood mftqm where mftqm.unit_id =:unit_id")
	public InvestmentDetailsFood findAllByunit_id(String unit_id);
}
