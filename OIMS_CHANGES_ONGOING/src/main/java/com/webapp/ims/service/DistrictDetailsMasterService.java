package com.webapp.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.DistrictDetails1;

@Service
public interface DistrictDetailsMasterService {

	public List<DistrictDetails1> findAllByDistrictName();

}
