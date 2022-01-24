package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ProductDetail;

@Repository
public interface ProductDetailRepository extends CrudRepository<ProductDetail, String> {

	@Query("from ProductDetail pdtqm where pdtqm.unit_id =:unitid")
	public List<ProductDetail> findAllByunit_id(String unitid);

}
