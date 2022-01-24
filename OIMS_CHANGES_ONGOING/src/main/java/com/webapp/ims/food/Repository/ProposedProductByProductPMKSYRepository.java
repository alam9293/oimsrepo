package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ProposedProductByProductPMKSY;

@Repository
public interface ProposedProductByProductPMKSYRepository extends JpaRepository<ProposedProductByProductPMKSY, String>{

}
