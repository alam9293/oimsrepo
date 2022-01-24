package com.webapp.ims.service.impl;

import static com.webapp.ims.exception.GlobalConstants.EXISTING_PROJECT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.impl.CapitalInvestServiceImpl;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;

@Service
public class AdditionalInterest {
	@Autowired
	ProjectService projectService;

	@Autowired
	ProposedEmploymentDetailsService proposedEmploymentDetailsService;

	@Autowired
	BusinessEntityDetailsService businessEntityDetailsService;

	@Autowired
	ProprietorDetailsService proprietorService;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepository;

	@Autowired
	CapitalInvestServiceImpl capitalInvestServiceImpl;

	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;
	@Autowired
	ProjDisburseRepository projDisburseRepository;
	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;

	String region;
	long totalemp;
	long obc;
	long propeq;
	long unSkilled;

	public String getSgstEligibility(String appId)

	{

		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();
		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		region = d.getResionName();

		ProposedEmploymentDetails peDetails = proposedEmploymentDetailsService
				.getProposedEmploymentDetailsByappId(appId);

		String peid = peDetails.getId();

		Optional<ProposedEmploymentDetails> k = proposedEmploymentDetailsService.getProposedEmploymentById(peid);
		ProposedEmploymentDetails l = k.get();
		List<SkilledUnSkilledEmployemnt> skills = l.getSkilledUnSkilledEmployemnt();
		String value = null;
		long sfemale = 0;
		long ssc = 0;
		long sst = 0;
		long smale = 0;
		long sbpl = 0;
		long male = 0;
		long female = 0;
		long sc = 0;
		long st = 0;
		long bpl = 0;

		for (SkilledUnSkilledEmployemnt skill : skills) {
			if (skill.getProposedEmploymentDetails().getId() == peid) {

				smale = skill.getNumberofMaleEmployees();
				sfemale = skill.getNumberOfFemaleEmployees();
				ssc = skill.getNumberOfSc();
				sst = skill.getNumberOfSt();

				sbpl = skill.getNumberOfBpl();

				male += smale;
				female += sfemale;

				if (skill.getSkilledUnskilled().equals("Unskilled")) {
					unSkilled = male + female;
				}

				sc += ssc;
				st += sst;
				bpl += sbpl;

			}

		}

		totalemp = male + female;
		long femaleemp = female;
		long totalSCST = sc + st;
		long totalbpl = bpl;

		// to check which options are available for selection ie: where the conditions
		// where satisfied
		StringBuilder marker = new StringBuilder();
		boolean flag = false;

		long pscstEmp = totalemp / 4;
		long pfemaleEmp = (long) (totalemp * 0.4);

		if ((region.equals("Paschimanchal"))
				&& (region.equals("Bundelkhand") || region.equals("Poorvanchal") || region.equals("Madhyanchal"))
				&& ((totalSCST) >= pscstEmp && totalemp >= 400) || ((totalSCST) >= pscstEmp && totalemp >= 200)) {
			marker.append(1); // at zero index
			flag = true;
		} else
			marker.append(0); // at zero index

		if ((region.equals("Paschimanchal"))
				&& (region.equals("Bundelkhand") || region.equals("Poorvanchal") || region.equals("Madhyanchal"))
				&& ((femaleemp) >= pfemaleEmp && totalemp >= 400) || ((femaleemp) >= pfemaleEmp && totalemp >= 200)) {
			marker.append(1); // at first index
			flag = true;
		} else
			marker.append(0); // at first index

		if ((region.equals("Paschimanchal"))
				&& (region.equals("Bundelkhand") || region.equals("Poorvanchal") || region.equals("Madhyanchal"))
				&& ((totalbpl) >= pscstEmp && totalemp >= 400) || ((totalbpl) >= pscstEmp && totalemp >= 200)) {
			marker.append(1); // at Second index
			flag = true;
		} else
			marker.append(0); // at Second index

		if (!flag)// not eligible
			marker.append(1); // at third index
		else
			marker.append(0); // at third index

		return marker.toString();
	}

	public String getStampEligibility(String appId) {
		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();

		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		region = d.getResionName();

		BusinessEntityDetails bedByappli = businessEntityDetailsService.getBusinessEntityByapplicantDetailId(appId);
		String besiId = bedByappli.getId();

		List<ProprietorDetails> list = proprietorService.findAllByBusinessId(besiId);

		long equity = 0;
		for (ProprietorDetails prop : list) {
			if (prop.getBusinessEntityDetails().equals(besiId)) {
				if ((prop.getDiv_Status().equals("Yes"))
						|| ((prop.getCategory().equals("SC")) || (prop.getCategory().equals("ST")))
						|| (prop.getCategory().equals("General") && prop.getGender().equals("Female"))) {
					equity += prop.getEquity();
				}
				/*
				 * if((prop.getCategory().equals("SC"))){ equity += prop.getEquity();
				 * 
				 * } if ((prop.getCategory().equals("ST"))){ equity += prop.getEquity();
				 * 
				 * } if (prop.getCategory().equals("General") &&
				 * prop.getGender().equals("Female")) { equity += prop.getEquity();
				 * 
				 * }
				 */ }

		}

		// According to Table No:3 Bundelkhand & Poorvanchal Stamp Duty already eligible
		// for 100% exemption.

		if (!(region.equals("Bundelkhand") || region.equals("Poorvanchal")) && equity >= 75) {

			return "eligible";
		}
		return "neligible";
	}

	public String getepfa(String appId) {
		if (totalemp >= 200 && unSkilled >= 100) {
			return "eligible";
		}
		return "neligible";
	}

	public String getepfaa(String appId) {

		BusinessEntityDetails bedByappli = businessEntityDetailsService.getBusinessEntityByapplicantDetailId(appId);
		String besiId = bedByappli.getId();

		List<ProprietorDetails> list = proprietorService.findAllByBusinessId(besiId);

		long equity = 0;
		for (ProprietorDetails prop : list) {
			if (prop.getBusinessEntityDetails().equals(besiId)) {
				if ((prop.getDiv_Status().equals("Yes"))
						|| ((prop.getCategory().equals("SC")) || (prop.getCategory().equals("ST")))
						|| (prop.getCategory().equals("General") && prop.getGender().equals("Female"))) {
					equity += prop.getEquity();
				}
			}

		}

		if (equity >= 75) {

			return "eligible";
		}

		return "neligible";
	}

	public String getaddCIS(String appId) {

		BusinessEntityDetails bedByappli = businessEntityDetailsService.getBusinessEntityByapplicantDetailId(appId);
		String besiId = bedByappli.getId();

		List<ProprietorDetails> list = proprietorService.findAllByBusinessId(besiId);

		long equity = 0;
		for (ProprietorDetails prop : list) {
			if (prop.getBusinessEntityDetails().equals(besiId)) {
				if ((prop.getDiv_Status().equals("Yes"))
						|| ((prop.getCategory().equals("SC")) || (prop.getCategory().equals("ST")))
						|| (prop.getCategory().equals("General") && prop.getGender().equals("Female"))) {
					equity += prop.getEquity();
				}
			}

		}
		if (equity >= 75) {
			return "eligible";

		} else {
			return "neligible";
		}

	}

	public String getregion(String appId)

	{

		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();
		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		return region = d.getResionName();

	}

	public long getunskilled(String appId)

	{

		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();
		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		region = d.getResionName();

		ProposedEmploymentDetails peDetails = proposedEmploymentDetailsService
				.getProposedEmploymentDetailsByappId(appId);

		String peid = peDetails.getId();

		Optional<ProposedEmploymentDetails> k = proposedEmploymentDetailsService.getProposedEmploymentById(peid);
		ProposedEmploymentDetails l = k.get();
		List<SkilledUnSkilledEmployemnt> skills = l.getSkilledUnSkilledEmployemnt();

		long male = 0;
		long female = 0;

		for (SkilledUnSkilledEmployemnt skill : skills) {
			if (skill.getProposedEmploymentDetails().getId() == peid) {
				if (skill.getSkilledUnskilled().equals("Unskilled")) {
					long smale = skill.getNumberofMaleEmployees();
					long sfemale = skill.getNumberOfFemaleEmployees();

					unSkilled = smale + sfemale;

				}

			}
		}
		return unSkilled;
	}

	public String getgrossBlock(String appId)

	{

		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();
		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		Long existgb = d.getExistingGrossBlock() == null ? 0 : d.getExistingGrossBlock();
		Long propgb = d.getProposedGrossBlock() == null ? 0 : d.getProposedGrossBlock();

		long exist = existgb / 4;

		if (propgb > exist) {
			return "Yes";
		}
		return "NO";

	}

	public Object getProposedFCI(String appId)

	{

		if (appId != null) {
			InvestmentDetails investmentDetailsid = investmentDetailsRepository.getInvestmentDetailsByapplId(appId);

			String invid = investmentDetailsid.getInvId();

			InvestmentDetails p = investmentDetailsService.getInvestmentDetailsById(invid);
			Long propFCI = p.getInvFci();

			long LCasperIEPP = propFCI / 4;

			if (propFCI > LCasperIEPP) {
				return LCasperIEPP;
			}
		}
		return null;

	}

	public String getregiOrLicense1(String appId)

	{

		InvestmentDetails investmentDetails = investmentDetailsRepository.getInvestmentDetailsByapplId(appId);

		if ((investmentDetails.getRegiOrLicense().equalsIgnoreCase("EncloseUAM"))
				|| (investmentDetails.getRegiOrLicense().equalsIgnoreCase("IEMcopy"))) {
			return "Yes";
		}
		return "No";

	}

	public String getregiOrLicense(String appId)

	{

		InvestmentDetails investmentDetails = investmentDetailsRepository.getInvestmentDetailsByapplId(appId);

		String invid = investmentDetails.getInvId();

		// Optional<ProjectDetails> p1 = projectService.getProjectById(projectid);
		InvestmentDetails p = investmentDetailsService.getInvestmentDetailsById(invid);
		// InvestmentDetails d = p.get();
		String regorlic = p.getRegiOrLicense();

		if (regorlic != null && !regorlic.isEmpty()
				&& ((regorlic.equalsIgnoreCase("EncloseUAM")) || (regorlic.equalsIgnoreCase("IEMcopy")))) {
			return "Yes";
		}
		return "No";

	}

	public String getCategory(String appId)

	{
		InvestmentDetails investmentDetails = investmentDetailsRepository.getInvestmentDetailsByapplId(appId);

		String invid = investmentDetails.getInvId();

		InvestmentDetails investmentDetails1 = investmentDetailsService.getInvestmentDetailsById(invid);

		String category = investmentDetails1.getInvIndType();

		/*
		 * if (category.equalsIgnoreCase("Small") || category.equalsIgnoreCase("Medium")
		 * || category.equalsIgnoreCase("Large") || category.equalsIgnoreCase("Mega") ||
		 * category.equalsIgnoreCase("Mega Plus") ||
		 * category.equalsIgnoreCase("Super Mega")) { return "Yes"; }
		 */
		return category;

	}

	public String getprojecttype(String appId)

	{

		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);

		String projectid = projectdetailid.getId();
		Optional<ProjectDetails> p = projectService.getProjectById(projectid);
		ProjectDetails d = p.get();
		String natureProject = d.getNatureOfProject();
		return natureProject;

	}

	// get LOC Number
	public String getLOCNumber(String appId) {
		PrepareAgendaNotes prepareappid = prepareAgendaNoteRepository.getPrepareByAppliId(appId);
		String locnumber = prepareappid.getLocNumber();
		return locnumber;
	}

	public long getDISEligibleFixedCapitalInvestment(String appId) {

		CapitalInvestmentDetails capitalinvest = capitalInvestServiceImpl.getDetailsBycapInvApcId(appId);
		long EligibleFixedCapitalInvestment = capitalinvest.getCapInvDPRBC() + capitalinvest.getCapInvDPRLC()
				+ capitalinvest.getCapInvDPRPMC() + capitalinvest.getCapInvDPRMFA();

		return EligibleFixedCapitalInvestment;
	}

	public long getDISPlantnMachineAmount(String appId) {

		CapitalInvestmentDetails capitalinvest = capitalInvestServiceImpl.getDetailsBycapInvApcId(appId);
		// long EligibleFixedCapitalInvestment =
		// capitalinvest.getCapInvDPRBC()+capitalinvest.getCapInvDPRLC()+capitalinvest.getCapInvDPRPMC()+capitalinvest.getCapInvDPRMFA();

		return capitalinvest.getCapInvDPRPMC();
	}

	public long getDISInfra(String appId) {
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		NewProjDisbursement newproject = newProjDisburseRepository.getDetailsBynewprojApcId(appId);
		ExistProjDisbursement existproject = projDisburseRepository.getDetailsByprojApcId(appId);

		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {

			return existproject.getProjDisInfra();
		} else {
			System.out.println(newproject.getNewprojInfra());
			return newproject.getNewprojInfra();

		}
	}

}