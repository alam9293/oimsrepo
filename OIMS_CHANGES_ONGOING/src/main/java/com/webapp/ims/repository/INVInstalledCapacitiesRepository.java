package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.INVInstalledCapacities;

@Repository
public interface INVInstalledCapacitiesRepository extends JpaRepository<INVInstalledCapacities, String> {
	public List<INVInstalledCapacities> findAllByinvId(String invId);
}
