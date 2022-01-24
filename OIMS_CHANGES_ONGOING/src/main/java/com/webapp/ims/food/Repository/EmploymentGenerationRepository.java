package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.food.Model.EmploymentGeneration;
import com.webapp.ims.food.Model.Identifier;

public interface EmploymentGenerationRepository extends JpaRepository<EmploymentGeneration, Identifier> {

}
