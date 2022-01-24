package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.SGSTReimTurnOver;

@Repository
public interface SGSTReimClaimTurnOverRepo extends JpaRepository<SGSTReimTurnOver, String> {

@Query(value="from SGSTReimTurnOver WHERE sgstTurnOverBaseProduction = (SELECT MAX(sgstTurnOverBaseProduction) FROM SGSTReimTurnOver)")
SGSTReimTurnOver findMaxTurnOverBaseProduction();
}
