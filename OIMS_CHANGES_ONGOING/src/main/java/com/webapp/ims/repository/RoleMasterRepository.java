package com.webapp.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.RoleMasterEntity;


@Repository
public interface RoleMasterRepository   extends JpaRepository<RoleMasterEntity, Integer> {

	
}
