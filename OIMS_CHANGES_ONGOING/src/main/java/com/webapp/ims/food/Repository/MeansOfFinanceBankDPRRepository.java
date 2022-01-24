package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.MeansofFinanceBankDPR;

@Repository
public interface MeansOfFinanceBankDPRRepository extends JpaRepository<MeansofFinanceBankDPR, Identifier> {

	@Query("from MeansofFinanceBankDPR pid where pid.unit_id =:unitId")
	public List<MeansofFinanceBankDPR> findAllByunit_id(String unitId);
}
