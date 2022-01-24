package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodProposeProductsByProducts;

@Repository
public interface FodProposedProductsByProductsRepository extends JpaRepository<FoodProposeProductsByProducts, Integer>{

}
