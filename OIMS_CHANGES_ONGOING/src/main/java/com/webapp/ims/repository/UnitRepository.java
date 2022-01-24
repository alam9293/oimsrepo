package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, String> {

	
}
