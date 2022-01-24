package com.webapp.ims.dis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11DisDtl;


@Repository
public interface Tbl11DisDtlServiceRepository extends JpaRepository<EvaluateApplicationDisTbl11DisDtl, String>
{
   
	//@Query("SELECT d.Tbl11_Sancd_LndrBank,d.Tbl11_Sancd_Date,d.Tbl11_Sancd_Amt,d.Tbl11_Sancd_IntrstRate FROM EvaluateApplicationDisTbl11SancdDtl d JOIN d.DisViewEvaluate e where d.Evaluate_Id = :AppId")
	@Query("SELECT d FROM EvaluateApplicationDisTbl11DisDtl d JOIN d.DisViewEvaluate e where d.Evaluate_Id = :AppId")
	List<EvaluateApplicationDisTbl11DisDtl> fetchTbl11DisDataJoin(String AppId);
	
	@Transactional(readOnly = false)
	@Query(nativeQuery = true,value ="select now()")
	public Date getCurrentTime();

}
