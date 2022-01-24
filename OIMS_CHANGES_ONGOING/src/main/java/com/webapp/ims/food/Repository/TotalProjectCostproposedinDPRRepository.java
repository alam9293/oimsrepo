package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.TotalProjectCostproposedinDPR;

@Repository
public interface TotalProjectCostproposedinDPRRepository
		extends JpaRepository<TotalProjectCostproposedinDPR, Identifier> {

}
