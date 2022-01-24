package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodDocumentFMP_PMKSY;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodDocumentPMKSYRepositoryFMP extends JpaRepository<FoodDocumentFMP_PMKSY, Identifier> {

}
