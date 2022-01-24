package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ProposedEmploymentDetails;

@Repository
public interface ProposedEmploymentDetailsRepository extends JpaRepository<ProposedEmploymentDetails, String> {

	ProposedEmploymentDetails getProposedEmploymentDetailsByappId(String appId);

	public ProposedEmploymentDetails getPropEmpById(String propId);

	@Query("Select propempl.id from  ProposedEmploymentDetails propempl where propempl.appId=:appId ")
	public String getPropEmplIdByapplId(@Param(value = "appId") String appId);

}
