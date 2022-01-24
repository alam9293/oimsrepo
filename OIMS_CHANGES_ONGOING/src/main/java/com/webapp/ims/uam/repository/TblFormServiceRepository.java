package com.webapp.ims.uam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblForm;

@Repository
public interface TblFormServiceRepository extends JpaRepository<TblForm, Integer> {
	
	@Query(" from TblForm where id IN (:allowedformIdList)")
	ArrayList<TblForm> getActionandLabel(ArrayList<String> allowedformIdList);
	
	@Query(" from TblForm where deptCode=:deptcode and status=:status")
	List<TblForm> getFormsbyDeptId(String deptcode,String status);
	
	@Query(" from TblForm where status='E'")
	List<TblForm> getEnableForms();
	
	@Query(" from TblForm where status='D'")
	List<TblForm> getDisableForms();
	
	@Query(" from TblForm where id=:id")
	List<TblForm> getFormsbyId(String id);
	

}