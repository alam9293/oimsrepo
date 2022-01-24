package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.EvaluateExistNewProjDetails;

@Repository
public interface EvaluateExistNewProjDetailsRepository extends JpaRepository<EvaluateExistNewProjDetails, String>{

	public List<EvaluateExistNewProjDetails> getEvalExistProjListByepdProjDtlId(String id);
}
