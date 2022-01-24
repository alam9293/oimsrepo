package com.webapp.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;


@Repository
public interface AgendaReportRepository {

	//@Query("select  pd.newProject ,pd.diversification  ,pd.districtName,pd.resionName ,pd.districtName,pd.natureOfProject from DeptProjectDetails pd where  pd.applicantDetailId =:applicationId")	
	@Query("select pd.newProject ,pd.diversification  ,pd.districtName,pd.resionName ,pd.districtName,pd.natureOfProject,depd.epdExisProducts ,depd.epdExisProducts,depd.epdExisInstallCapacity ,depd.epdPropProducts,depd.epdExisGrossBlock ,depd.epdPropoGrossBlock, did.invTotalProjCost ,did.propCommProdDate ,did.invPlantAndMachCost,did.invOtherCost    ,did.landcostFci ,did.buildingFci  ,did.plantAndMachFci, did.fixedAssetFci   ,did.invLandCost  ,did.invPlantAndMachCost,did.invOtherCost, did.invBuildingCost ,did.landcostIIEPP ,did.buildingIIEPP, did.fixedAssetIIEPP ,did.plantAndMachIIEPP ,did.landcostRemarks ,did.buildingRemarks ,did.plantAndMachRemarks ,did.fixedAssetRemarks,did.invGovtEquity,did.invIemNumber ,did.pwApply ,did.capInvAmt ,dpid.pwPhaseNo ,dpid.pwProductName ,dpid.pwCapacity ,dpid.pwUnit ,dpid.pwFci ,dpid.pwPropProductDate ,be.authorisedSignatoryName,be.gstin ,be.companyPanNo ,difd.createDate,difd.ISF_Reim_SCST,difd.ISF_Reim_FW ,difd.ISF_Reim_BPLW ,difd.ISF_Ttl_SGST_Reim , difd.ISF_Stamp_Duty_EX ,difd.ISF_Epf_Reim_UW,difd.sgstRemark ,difd.scstRemark ,difd.stampDutyExemptRemark,difd.stampDutyReimRemark ,difd.isfSgstComment ,difd.isfScstComment ,difd.isffwComment,difd.isfBplComment ,difd.isfElecdutyComment  from ApplicantDetails adm  left  join\r\n" + 
			"BusinessEntityDetails be on adm.appId=be.applicantDetailId  left join \r\n" + 
			"DeptProjectDetails pd on adm.appId=pd.applicantDetailId  left  join \r\n" + 
			"DeptExistingProjectDetails depd on adm.appId =depd.epdProjDtlId  left join \r\n" + 
			"ProposedEmploymentDetails ped on adm.appId=ped.appId    left join \r\n" + 
			"EvaluateInvestmentDetails did on adm.appId= did.applId  left join\r\n" + 
			"Dept_PhaseWiseInvestmentDetails dpid on adm.appId= dpid.pwApcId  left join\r\n" + 
			"DeptIncentiveDetails difd  on adm.appId=difd.isfapcId \r\n" + 
			"where adm.appId =:applicationId")
	List<Object> findAgendaReportData(String applicationId);
	
	
	
	
}
