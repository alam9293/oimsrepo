package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.BankTermLoanDetails;
import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ImplementationSchedule;

@Repository
public interface BankTermLoanDetailsRepository extends JpaRepository<BankTermLoanDetails, Identifier> {


	@Query(value = "from BankTermLoanDetails bedf")
	public List<BankTermLoanDetails> getAll();
	
	@Query("from BankTermLoanDetails pid where pid.unit_id =:unitId")
	public List<BankTermLoanDetails> findAllByunit_id(String unitId);
}
