package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.Identifier;



public interface PlantAndMachinerytobeCSISRepository extends JpaRepository<DetailsofProposedPlantAndMachinerytobeInstalled, Identifier>{
	
	@Query("from DetailsofProposedPlantAndMachinerytobeInstalled dpnm where dpnm.unit_id =:unitId order by dpnm.sno")
	public List<DetailsofProposedPlantAndMachinerytobeInstalled> findAllByunit_id(String unitId);
	
}
