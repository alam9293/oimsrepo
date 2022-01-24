package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodDocument;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodDocumentRepository extends JpaRepository<FoodDocument, Identifier> {

}
