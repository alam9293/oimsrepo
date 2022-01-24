package com.webapp.ims.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.InvestmentDetails;

@Repository
public interface InvestmentDetailsRepository extends JpaRepository<InvestmentDetails, Long> {
	@Query("Select invdtl From InvestmentDetails invdtl where invdtl.invId=:investId")
	public InvestmentDetails getInvestmentDetailsById(@Param(value = "investId") String investId);

	@Query("Select invdtl.invId from InvestmentDetails invdtl where invdtl.applId=:applicantId ")
	public String getInvDetailIdByapplId(@Param(value = "applicantId") String appId);

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName, isf.status from InvestmentDetails c join IncentiveDetails isf on  isf.isfapcId = c.applId and isf.status IN ('Submit') join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Object> getAllDetailsByAppId();

	
	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName, isf.status from InvestmentDetails c join IncentiveDetails isf on  isf.isfapcId = c.applId and isf.status IN ('Submit') join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Tuple> getAllDetailsByAppIdTuple();
	
	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName, circt.deptName from InvestmentDetails c join CirculateToDepartment circt on  circt.AppId = c.applId and circt.noteReportStatus IN ('Draft LoC') join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Tuple> getAllLocDetailsByAppIdTuple();

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName, isf.status from InvestmentDetails c join IncentiveDetails isf on  isf.isfapcId = c.applId and isf.status IN ('Submit') join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Small','Medium')")
	public List<Object> getAllDetailsSMEByAppId();

	public InvestmentDetails getInvestmentDetailsByapplId(String appId);

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType from InvestmentDetails c join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Mega', 'Mega Plus', 'Super Mega')")
	public List<InvestmentDetails> getAllMSMMPIndusByAppId();

	@Query(value = "select c.applId, b.businessEntityName, c.invFci, p.districtName,p.resionName from InvestmentDetails c join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Mega', 'Mega Plus', 'Super Mega') join ProjectDetails p on c.applId=p.applicantDetailId where c.applId=:applId ")
	public List<InvestmentDetails> getInvestmentByapplId(String applId);

	@Query(value = "select c.applId, b.businessEntityName, pd.resionName, pd.districtName,  c.invFci, c.invIndType from InvestmentDetails c join  ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId where c.applId=:applicantId")
	public List<Object> getEvalDetailsByApplId(@Param(value = "applicantId") String appId);
	
	public InvestmentDetails findByApplId(String apcid);
	
	//sachin
	@Query(value = "select c.applId, b.businessEntityName, c.invFci, c.invIndType, pd.resionName, pd.districtName, isf.dis_status from InvestmentDetails c join IncentiveDetails isf on  isf.isfapcId = c.applId and isf.dis_status IN ('Submit') join ProjectDetails pd on  pd.applicantDetailId = c.applId join BusinessEntityDetails b on c.applId = b.applicantDetailId and c.invIndType IN ('Large','Mega', 'Mega Plus', 'Super Mega')")
	public List<Tuple> getAllDetailsByAppIdTupleDis();
	
	@Modifying
	@Query(value = "Update InvestmentDetails inv set inv.invIndType=:invIndType ,inv.invCommenceDate=:invCommenceDate ,inv.propCommProdDate=:propCommProdDate ,inv.regiOrLicense=:regiOrLicense")
	public void updateInvestmentBasicDetails(@Param(value = "invIndType") String invIndType ,@Param(value = "invCommenceDate") Date invCommenceDate ,@Param(value = "propCommProdDate") Date propCommProdDate ,@Param(value = "regiOrLicense") String regiOrLicense);
	
}
