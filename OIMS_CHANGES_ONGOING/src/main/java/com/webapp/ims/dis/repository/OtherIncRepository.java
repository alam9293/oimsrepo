package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.OtherIncentive;

@Repository
public interface OtherIncRepository extends JpaRepository<OtherIncentive,String>{
	OtherIncentive getDetailsByothApcid(String id);
	
	OtherIncentive getDetailsByOthApcid(String othApcid);
	
}
