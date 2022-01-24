package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ProductDetails;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails, String> {


	@Query("from ProductDetails pd where pd.unit_id =:unitid")
	public List<ProductDetails> findAllByunit_id(String unitid);

}
