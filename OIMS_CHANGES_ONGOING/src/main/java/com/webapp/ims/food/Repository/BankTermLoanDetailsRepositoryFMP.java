package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.BankTermLoanDetailsFMP;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface BankTermLoanDetailsRepositoryFMP extends JpaRepository<BankTermLoanDetailsFMP, Identifier> {

	@Query("from BankTermLoanDetailsFMP pid where pid.unit_id =:unitId")
	public List<BankTermLoanDetailsFMP> findAllByunit_id(String unitId);
}
