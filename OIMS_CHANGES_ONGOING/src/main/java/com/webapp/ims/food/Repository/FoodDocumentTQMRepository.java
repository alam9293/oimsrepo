package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.food.Model.FoodDocumentTQM;
import com.webapp.ims.food.Model.Identifier;

public interface FoodDocumentTQMRepository extends JpaRepository<FoodDocumentTQM, Identifier>{

}
