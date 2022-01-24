package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodDocumentsMDBP;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodDocumentMDBPRepository extends JpaRepository<FoodDocumentsMDBP, Identifier> {

}
