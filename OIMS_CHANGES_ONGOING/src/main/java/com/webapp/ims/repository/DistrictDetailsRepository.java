package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DistrictDetails;

@Repository
public interface DistrictDetailsRepository extends JpaRepository<DistrictDetails, Long> {

	public DistrictDetails getDistrictByDistrictCode(long districtCode);

	@Override
	public List<DistrictDetails> findAll();
}
