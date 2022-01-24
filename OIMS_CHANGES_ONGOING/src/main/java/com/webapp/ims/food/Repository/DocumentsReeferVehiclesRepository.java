package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DocumentsReeferVehicles;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface DocumentsReeferVehiclesRepository extends JpaRepository<DocumentsReeferVehicles, Identifier>{

}
