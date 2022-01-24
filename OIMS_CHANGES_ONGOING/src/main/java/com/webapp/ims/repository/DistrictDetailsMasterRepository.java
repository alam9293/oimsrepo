package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DistrictDetails1;

@Repository
public interface DistrictDetailsMasterRepository extends JpaRepository<DistrictDetails1, Long> {

}
