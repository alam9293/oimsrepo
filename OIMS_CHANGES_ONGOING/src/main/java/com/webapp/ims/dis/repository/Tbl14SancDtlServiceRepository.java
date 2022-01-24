package com.webapp.ims.dis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14SancdDtl;


@Repository
public interface Tbl14SancDtlServiceRepository extends JpaRepository<EvaluateApplicationDisTbl14SancdDtl, String>
{
   
	//@Query("SELECT d.Tbl14_Sancd_LndrBank,d.Tbl14_Sancd_Date,d.Tbl14_Sancd_Amt,d.Tbl14_Sancd_IntrstRate FROM EvaluateApplicationDisTbl14SancdDtl d JOIN d.DisViewEvaluate e where d.Evaluate_Id = :AppId")
	@Query("SELECT d FROM EvaluateApplicationDisTbl14SancdDtl d JOIN d.DisViewEvaluate e where d.Evaluate_Id = :AppId")
	List<EvaluateApplicationDisTbl14SancdDtl> fetchTbl14SancDataJoin(String AppId);
	
	@Transactional(readOnly = false)
	@Query(nativeQuery = true,value ="select now()")
	public Date getCurrentTime();
}
