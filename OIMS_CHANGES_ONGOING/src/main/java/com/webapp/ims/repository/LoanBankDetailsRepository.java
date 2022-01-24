package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.dis.model.DisEvaluationBankDetails;

public interface LoanBankDetailsRepository extends JpaRepository<DisEvaluationBankDetails, String> {

	public List<DisEvaluationBankDetails> findByapcId(String ApcId);
}
