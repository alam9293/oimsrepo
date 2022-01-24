package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodTQMprojectDetails;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodTQMprojectDetailsRepository extends JpaRepository<FoodTQMprojectDetails, Identifier> {

	@Query(value = "from FoodTQMprojectDetails ftpd")
	public List<FoodTQMprojectDetailsRepository> getAll();

	
}
