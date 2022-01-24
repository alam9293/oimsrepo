package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.dis.model.DisEvaluationPandMBankDetails;

public interface PandMBankDetailsRepository extends JpaRepository<DisEvaluationPandMBankDetails, String> {

	public List<DisEvaluationPandMBankDetails> findByapcId(String ApcId);
}
