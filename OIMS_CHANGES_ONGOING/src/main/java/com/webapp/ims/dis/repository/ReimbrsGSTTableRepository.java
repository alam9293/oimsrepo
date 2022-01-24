package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;

@Repository
public interface ReimbrsGSTTableRepository extends JpaRepository<DisReimbrsDepositeGstTable, String> {

	public List<DisReimbrsDepositeGstTable> findBydisAppId(String applicantId);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	public List<DisReimbrsDepositeGstTable> save(List<DisReimbrsDepositeGstTable> reimbrsGStTableList);

}
