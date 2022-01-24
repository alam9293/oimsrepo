package com.webapp.ims.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.Dept_InvestmentDetails;

@Repository
public interface DeptInvestmentDetailsRepository extends JpaRepository<Dept_InvestmentDetails, Long> {
	@Query("Select invdtl From Dept_InvestmentDetails invdtl where invdtl.invId=:investId")
	public Dept_InvestmentDetails getDept_InvestmentDetailsById(@Param(value = "investId") String investId);

	@Query("Select invdtl.invId from Dept_InvestmentDetails invdtl where invdtl.applId=:applicantId ")
	public String getInvDetailIdByapplId(@Param(value = "applicantId") String appId);

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName from Dept_InvestmentDetails c join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Object> getAllDetailsByAppId();

	// gopal
	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName from Dept_InvestmentDetails c join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Tuple> getAllDetailsByAppIdTuple();

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName from Dept_InvestmentDetails c join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Small','Medium')")
	public List<Object> getAllDetailsSMEByAppId();

	public Dept_InvestmentDetails getDept_InvestmentDetailsByapplId(String appId);

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType from Dept_InvestmentDetails c join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Mega', 'Mega Plus', 'Super Mega')")
	public List<Dept_InvestmentDetails> getAllMSMMPIndusByAppId();

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, p.districtName,p.resionName from Dept_InvestmentDetails c join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Mega', 'Mega Plus', 'Super Mega') join ProjectDetails p on c.applId=p.applicantDetailId where c.applId=:applId ")
	public List<Dept_InvestmentDetails> getInvestmentByapplId(String applId);


	
	@Query("from Dept_InvestmentDetails a where a.applId =:applId")
	List<Dept_InvestmentDetails> searchInvDetailIdByapplId(String applId);
	
}
