package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.model.DeptBusinessEntityDetails;

public interface DeptBusinessEntityDetailsRepository extends JpaRepository<DeptBusinessEntityDetails, Integer> {

	public DeptBusinessEntityDetails findByApplicantDetailId(String applId);

	@Query("select dpid.pwPhaseNo ,dpid.pwProductName ,dpid.pwCapacity ,dpid.pwUnit ,dpid.pwFci ,dpid.pwPropProductDate  ,be.authorisedSignatoryName,be.gstin ,be.companyPanNo ,be.businessEntityName ,be.businessAddress from DeptBusinessEntityDetails be  left join Dept_PhaseWiseInvestmentDetails dpid on be.applicantDetailId= dpid.pwApcId  where be.applicantDetailId=:appId")
	public List<Object> findByAppIdIndex(String appId);

}
