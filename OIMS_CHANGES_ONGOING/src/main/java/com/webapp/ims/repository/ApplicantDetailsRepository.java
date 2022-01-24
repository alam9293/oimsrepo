package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ApplicantDetails;

@Repository
public interface ApplicantDetailsRepository extends JpaRepository<ApplicantDetails, Integer> {
	
	@Query("Select appdtl from ApplicantDetails appdtl where appdtl.appId = :appId ")
	ApplicantDetails getApplicantDetailsByAppId(@Param(value = "appId") String appId);

	/*
	 * @Query("select pd.newProject ,pd.diversification ,pd.resionName ,pd.districtName,pd.natureOfProject,depd.epdExisProducts ,depd.epdExisInstallCapacity ,depd.epdPropProducts,depd.epdExisGrossBlock ,depd.epdPropoGrossBlock, did.invTotalProjCost ,did.propCommProdDate ,did.invPlantAndMachCost,did.invOtherCost    ,did.landcostFci ,did.buildingFci  ,did.plantAndMachFci, did.fixedAssetFci   ,did.invLandCost  , did.invBuildingCost ,did.landcostIIEPP ,did.buildingIIEPP, did.fixedAssetIIEPP ,did.plantAndMachIIEPP ,did.landcostRemarks ,did.buildingRemarks ,did.plantAndMachRemarks ,did.fixedAssetRemarks,did.invGovtEquity,did.invIemNumber ,did.pwApply ,did.capInvAmt ,be.authorisedSignatoryName,be.gstin ,be.companyPanNo ,difd.CreatedDate,difd.ISF_Reim_SCST,difd.ISF_Reim_FW ,difd.ISF_Reim_BPLW ,difd.ISF_Ttl_SGST_Reim, difd.ISF_Stamp_Duty_EX ,difd.ISF_Epf_Reim_UW,difd.sgstRemark ,difd.scstRemark ,difd.stampDutyExemptRemark,difd.stampDutyReimRemark ,difd.isfSgstComment ,difd.isfScstComment ,difd.isffwComment,difd.isfBplComment ,difd.isfElecdutyComment ,be.businessEntityName ,be.businessAddress, did.invFci,did.invIndType ,did.catIndusUndtObserv,did.IemRegObserv ,did.invCommenceDate,did.optcutofdateobserv ,did.propsPlntMcnryCostObserv,did.dateofComProdObserv,did.appformatObserv,did.authSignatoryObserv,did.detailProjReportObserv,epd.listAssetsObserv,epd.listAssets,epd.anexurUndertkObserv,did.eligblInvPerdMegaObserv,did.projPhasesObserv,ped.totalDetailObserv ,sse.numberofMaleEmployees,sse.numberOfFemaleEmployees,be.supprtdocObserv ,be.docAuthorized,difd.ISF_Claim_Reim,difd.ISF_Amt_Stamp_Duty_Reim ,difd.ISF_Cis,difd.capIntSubRemark ,difd.ISF_Infra_Int_Subsidy,difd.infraIntSubRemark,difd.ISF_Loan_Subsidy,difd.inputTaxRemark,difd.ISF_Tax_Credit_Reim,difd.ISF_Indus_Payroll_Asst,difd.diffAbleWorkRemark,epd.solarCaptivePower,epd.solarCaptivePowerObserv from ApplicantDetails adm  left  join\r\n"
	 * +
	 * "DeptBusinessEntityDetails be on be.applicantDetailId=adm.appId  left join \r\n"
	 * + "DeptProjectDetails pd on pd.applicantDetailId=adm.appId  left  join \r\n"
	 * +
	 * "DeptExistingProjectDetails depd on depd.epdProjDtlId=pd.id  left join \r\n"
	 * +
	 * "EvaluateProjectDetails epd on  epd.applicantDetailId=adm.appId  left join \r\n"
	 * +
	 * "DeptProposedEmploymentDetails ped on ped.appId=adm.appId    left join \r\n"
	 * + "SkilledUnSkilledEmployemnt sse on sse.id= ped.id    left join \r\n" +
	 * "EvaluateInvestmentDetails did on did.applId=adm.appId  left join\r\n" +
	 * //"Dept_PhaseWiseInvestmentDetails dpid on adm.appId= dpid.pwApcId  left join\r\n"
	 * + "DeptIncentiveDetails difd  on difd.isfapcId=adm.appId  \r\n" +
	 * "  where adm.appId =:applicationId")
	 */

	@Query("select pd.newProject ,pd.diversification ,pd.resionName ,pd.districtName,pd.natureOfProject,depd.epdExisProducts ,depd.epdExisInstallCapacity ,depd.epdPropProducts,depd.epdExisGrossBlock ,depd.epdPropoGrossBlock, did.invTotalProjCost ,did.propCommProdDate ,did.invPlantAndMachCost,did.invOtherCost    ,did.landcostFci ,did.buildingFci  ,did.plantAndMachFci, did.fixedAssetFci   ,did.invLandCost  , did.invBuildingCost ,did.landcostIIEPP ,did.buildingIIEPP, did.fixedAssetIIEPP ,did.plantAndMachIIEPP ,did.landcostRemarks ,did.buildingRemarks ,did.plantAndMachRemarks ,did.fixedAssetRemarks,did.invGovtEquity,did.invIemNumber ,did.pwApply ,did.capInvAmt ,be.authorisedSignatoryName,be.gstin ,be.companyPanNo ,difd.CreatedDate,difd.ISF_Reim_SCST,difd.ISF_Reim_FW ,difd.ISF_Reim_BPLW ,difd.ISF_Ttl_SGST_Reim, difd.ISF_Stamp_Duty_EX ,difd.ISF_Epf_Reim_UW,difd.sgstRemark ,difd.scstRemark ,difd.stampDutyExemptRemark,difd.stampDutyReimRemark ,difd.isfSgstComment ,difd.isfScstComment ,difd.isffwComment,difd.isfBplComment ,difd.isfElecdutyComment ,be.businessEntityName ,be.businessAddress, did.invFci,did.invIndType ,did.catIndusUndtObserv,did.IemRegObserv ,did.invCommenceDate,did.optcutofdateobserv ,did.propsPlntMcnryCostObserv,did.dateofComProdObserv,did.appformatObserv,did.authSignatoryObserv,did.detailProjReportObserv,epd.listAssetsObserv,epd.listAssets,epd.anexurUndertkObserv,did.eligblInvPerdMegaObserv,did.projPhasesObserv,ped.totalDetailObserv ,be.supprtdocObserv ,be.docAuthorized,difd.ISF_Claim_Reim,difd.ISF_Amt_Stamp_Duty_Reim ,difd.ISF_Cis,difd.capIntSubRemark ,difd.ISF_Infra_Int_Subsidy,difd.infraIntSubRemark,difd.ISF_Loan_Subsidy,difd.inputTaxRemark,difd.ISF_Tax_Credit_Reim,difd.ISF_Indus_Payroll_Asst,difd.diffAbleWorkRemark,epd.solarCaptivePower,epd.solarCaptivePowerObserv,pd.expansion,pd.diversification,difd.modify_Date,"
			+ "epd.propsProdtDetailObserv,pwInvstDtl.pwPhaseNo,pwInvstDtl.pwProductName,pwInvstDtl.pwCapacity,pwInvstDtl.pwUnit,pwInvstDtl.invFci,pwInvstDtl.pwPropProductDate,pwInvstDtl.pwApcId from ApplicantDetails adm  left  join\r\n"
			+ "DeptBusinessEntityDetails be on be.applicantDetailId=adm.appId  left join \r\n"
			+ "DeptProjectDetails pd on pd.applicantDetailId=adm.appId  left  join \r\n"
			+ "DeptExistingProjectDetails depd on depd.epdProjDtlId=pd.id  left join \r\n"
			+ "EvaluateProjectDetails epd on  epd.applicantDetailId=adm.appId  left join \r\n"
			+ "DeptProposedEmploymentDetails ped on ped.appId=adm.appId    left join \r\n" +

			"EvaluateInvestmentDetails did on did.applId=adm.appId  left join\r\n" +
			// "Dept_PhaseWiseInvestmentDetails dpid on adm.appId= dpid.pwApcId left
			// join\r\n" +
			"PhaseWiseInvestmentDetails pwInvstDtl  on pwInvstDtl.pwApcId =adm.appId left join\r\n" + 
			"DeptIncentiveDetails difd  on difd.isfapcId=adm.appId  \r\n" + 
			"where adm.appId =:applicationId")

	List<Object> findAgendaReportData(String applicationId);

}