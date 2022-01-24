package com.webapp.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.DistrictDetails;

@Service
public interface DistrictDetailsService {

	public DistrictDetails getDistrictByDistrictCode(long districtCode);

	public List<DistrictDetails> findAllByDistrictName();

	public DistrictDetails findlDistrictNameByStateCode(Long stateCode);

}
