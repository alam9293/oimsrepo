package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DigitalSignatureEntity;

@Repository
public interface DigitalSignatureRepository extends JpaRepository<DigitalSignatureEntity, Integer> {

}