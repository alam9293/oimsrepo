package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.MeansofFinancingTQM;

public interface MeansofFinancingTQMRepository extends JpaRepository<MeansofFinancingTQM, Identifier>{

	
	
	@Query("from MeansofFinancingTQM mftqm where mftqm.unit_id =:unitId")
	public List<MeansofFinancingTQM> findAllByunit_id(String unitId);
}
