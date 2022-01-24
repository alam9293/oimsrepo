package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;
import com.webapp.ims.dis.model.OtherIncentive;
import com.webapp.ims.dis.repository.DisEPFRepository;
import com.webapp.ims.dis.repository.DisStampDeautyRepository;
import com.webapp.ims.dis.repository.DisbursmentCisIncentiveRepository;
import com.webapp.ims.dis.repository.DisbursmentIisIncentiveRepository;
import com.webapp.ims.dis.repository.DisbursmentReimbrsofDepositGSTRepository;
import com.webapp.ims.dis.repository.OtherIncRepository;
import com.webapp.ims.dis.service.OtherDetailService;
import com.webapp.ims.dis.service.StampDutyService;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.BusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.StateDetailsRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.NewProjectDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;

@Service
@Transactional
public class BusinessEntityDetailsServiceImpl implements BusinessEntityDetailsService {

	@Autowired
	BusinessEntityDetailsRepository businessEntityRepository;
	@Autowired
	private InvestmentDetailsService investDs;

	/*
	 * @Autowired FoodPolicyDetailsRepository foodRepos;
	 */

	@Autowired
	DisbursmentCisIncentiveRepository disbursmentCisIncentiveRepository;

	@Autowired
	DisbursmentIisIncentiveRepository disbursmentIisIncentiveRepository;

	@Autowired
	DisbursmentReimbrsofDepositGSTRepository disbursmentReimbrsofDepositGSTRepository;

	@Autowired
	DisEPFRepository disEPFRepository;

	@Autowired
	OtherIncRepository otherIncRepository;

	@Autowired
	DisStampDeautyRepository disStampDeautyRepository;

	@Autowired
	DeptBusinessEntityDetailsRepository deptbusinessEntityRepository;

	@Autowired
	private StateDetailsRepository stateRepository;

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	ProjectService projectService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	StampDutyService stampDutyService;

	@Autowired
	OtherDetailService otherDetailService;

	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	
	@Autowired
	ProprietorDetailsService proprietorDetailsService;
	
	@Autowired
	NewProjectDetailsService newProjectDetails;
	 
	 
	@Override
	public List<BusinessEntityDetails> getAllBusinessEntities() {
		return businessEntityRepository.findAll();
	}

	@Override
	public Iterable<BusinessEntityDetails> findAll() {
		return businessEntityRepository.findAll();
	}

	@Override
	public BusinessEntityDetails saveBusinessEntity(BusinessEntityDetails businessEntity) {
		return saveBusinessEntityExtra(businessEntity);
	}

	// vinay start
	private BusinessEntityDetails saveBusinessEntityExtra(BusinessEntityDetails businessEntity) {

		DeptBusinessEntityDetails deptbusinessentitydetails = new DeptBusinessEntityDetails();
		BeanUtils.copyProperties(businessEntity, deptbusinessentitydetails);
		deptbusinessEntityRepository.save(deptbusinessentitydetails);
		return businessEntityRepository.save(businessEntity);

	}

// vinay ends 
	@Override
	public Optional<BusinessEntityDetails> getBusinessEntityById(String id) {
		return businessEntityRepository.findById(id);
	}

	@Override
	public void deleteBusinessEntityById(Integer id) {
		businessEntityRepository.deleteById(id);
	}

	@Override
	public void deleteBusinessEntity(BusinessEntityDetails businessEntity) {
		businessEntityRepository.delete(businessEntity);

	}

	@Override
	public BusinessEntityDetails updateBusinessEntity(BusinessEntityDetails businessEntity) {
		return businessEntityRepository.save(businessEntity);
	}

	@Override
	@Query("from BusinessEntityDetails where applicantDetailId = :appId ")
	public BusinessEntityDetails getBusinessEntityByapplicantDetailId(String appId) {
		return businessEntityRepository.getBusinessEntityByapplicantDetailId(appId);
	}

	@Override
	@Query("from BusinessEntityDetails where besiId = :besiId ")
	public BusinessEntityDetails getBusinDetById(String besiId) {
		return businessEntityRepository.getBusinDetById(besiId);
	}

	// Common data get business and project
	@SuppressWarnings({ "unused", "null" })
	@Override
	public void commonDetails(String appId, HttpSession session, Model model) {

		String appStr = appId.substring(0, appId.length() - 2);
		String businId = appStr + "B1";
		String projId = appStr + "P1";
		String propId = appStr + "PE1";
		String invId = appStr + "I1";
		String propriterId = appStr + "";

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);

		if (businessEntityDetails != null) {

			model.addAttribute("businessEntityName", businessEntityDetails.getBusinessEntityName());
			model.addAttribute("businessEntityType", businessEntityDetails.getBusinessEntityType());
			model.addAttribute("bussAuthSigName", businessEntityDetails.getAuthorisedSignatoryName());
			model.addAttribute("bussDesignation", businessEntityDetails.getBusinessDesignation());
			model.addAttribute("emailId", businessEntityDetails.getEmailId());
			model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
			model.addAttribute("phoneNo", businessEntityDetails.getPhoneNo());
			model.addAttribute("fax", businessEntityDetails.getFax());
			model.addAttribute("businessAddress", businessEntityDetails.getBusinessAddress());
			model.addAttribute("businessCountryName", businessEntityDetails.getBusinessCountryName());
			model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("gstin", businessEntityDetails.getGstin());
			model.addAttribute("pan", businessEntityDetails.getCompanyPanNo());
			String statename = stateRepository
					.findByStateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));

			model.addAttribute("businessStateName", statename);
			model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
			model.addAttribute("PinCode", businessEntityDetails.getPinCode());
			List<ProprietorDetails> propriter = proprietorDetailsService.findAllByBusinessId(businId);
			model.addAttribute("propriter", propriter);

		}

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

		if (ProjectDetail != null) {
			model.addAttribute("contactPersonName", ProjectDetail.getContactPersonName());
			model.addAttribute("mobileNo", ProjectDetail.getMobileNo());
			model.addAttribute("designation", ProjectDetail.getDesignation());
			model.addAttribute("projectDescription", ProjectDetail.getProjectDescription());
			model.addAttribute("webSiteName", ProjectDetail.getWebSiteName());
			model.addAttribute("fullAddress", ProjectDetail.getFullAddress());
			model.addAttribute("districtName", ProjectDetail.getDistrictName());
			model.addAttribute("mandalName", ProjectDetail.getMandalName());
			model.addAttribute("resionName", ProjectDetail.getResionName());
			model.addAttribute("pinCode", ProjectDetail.getPinCode());
			model.addAttribute("regiOrLicense", ProjectDetail.getRegiOrLicense());
			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());
			}
		 List<NewProjectDetails> newproduct = newProjectDetails.getNewProjListById(projId);
		 model.addAttribute("newproduct", newproduct);
		InvestmentDetails investmentDetails = investDs.getInvestmentDetailsById(invId);
		if(investmentDetails != null) 
		{
		model.addAttribute("opt", investmentDetails.getInvCommenceDate());
		model.addAttribute("proposedate", investmentDetails.getPropCommProdDate());
		
		model.addAttribute("pwinfo", investmentDetails.getPwApply());
		
		}
		// start employement
		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {
			List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
					.getSkilledUnSkilledEmployemnt();
			Long bpl = (long) 0;
			Long sc = (long) 0;
			Long st = (long) 0;
			Long femaleEmp = (long) 0;
			for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntslist) {
				if (count.getNumberOfBpl() != null)
					bpl += count.getNumberOfBpl();
				sc += count.getNumberOfSc();
				st += count.getNumberOfSt();
				femaleEmp += count.getNumberOfFemaleEmployees();

			}
			model.addAttribute("totalBPL", bpl);
			model.addAttribute("totalSC", sc);
			model.addAttribute("totalSt", st);
			model.addAttribute("totalFemaleEmp", femaleEmp);

			totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
			model.addAttribute("proposedEmploymentDetails", skilledUnSkilledEmployemntslist);

		}

		// end employement

		IncentiveDetails incentive = incentiveDetailsService.getIncentiveByisfapcId(appId);
		if(incentive!= null) {
		Long epfmain = incentive.getISF_Epf_Reim_UW();
		Long addepf1 = incentive.getISF_Add_Epf_Reim_SkUkW();
		Long addepf2 = incentive.getISF_Add_Epf_Reim_DIVSCSTF();
		model.addAttribute("ISF_Epf_Reim_UW", epfmain);
		model.addAttribute("ISF_Add_Epf_Reim_SkUkW", addepf1);
		model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF", addepf2);

		Long sgst = incentive.getISF_Ttl_SGST_Reim();
		Long stamp = incentive.getISF_Ttl_Stamp_Duty_EX();
		Long epf = incentive.getISF_Ttl_EPF_Reim();
		Long infra = incentive.getISF_Total_Int_Subsidy();
		Long cis = incentive.getISF_Cis();
		Long qis = incentive.getISF_Loan_Subsidy();
		Long taxcreadit = incentive.getISF_Tax_Credit_Reim();
		Long payroll = incentive.getISF_Indus_Payroll_Asst() == null ? 0 : incentive.getISF_Indus_Payroll_Asst();

		Long elecdutyCap = incentive.getISF_EX_E_Duty() == null ? 0 : incentive.getISF_EX_E_Duty();
		Long elecdutyCapPC = incentive.getISF_EX_E_Duty_PC() == null ? 0 : incentive.getISF_EX_E_Duty_PC();
		Long mandi = incentive.getISF_EX_Mandee_Fee() == null ? 0 : incentive.getISF_EX_Mandee_Fee();
		

		Discis capinvest = disbursmentCisIncentiveRepository.getDetailsBydisAppId(appId);
		Disiis infrasub = disbursmentIisIncentiveRepository.getDetailsBydisAppId(appId);
		Disepfriem epfinv = disEPFRepository.getDetailsByappId(appId);
		DissbursmentReimbrsDepositeGST reim = disbursmentReimbrsofDepositGSTRepository.getDetailsBydisAppId(appId);
		OtherIncentive oth = otherIncRepository.getDetailsByothApcid(appId);
		DisStampDeauty stam = disStampDeautyRepository.getDetailsByStampApcId(appId);

		if (capinvest != null) {
			model.addAttribute("capinvest", capinvest);
			model.addAttribute("incentcap", "Capital Interest Subsidy As per para 3.5.2 of Rules-2017");
		}
		if (infrasub != null) {
			model.addAttribute("infrasub", infrasub);
			model.addAttribute("incentis", "Infrastructure Interest Subsidy As per para 3.5.1 of Rules-2017");
		}
		if (epfinv != null) {
			model.addAttribute("epfinv", epfinv);
			model.addAttribute("incentepf", "EPF Reimbursement As per para 3.3 of Rules-2017");
		}
		if (reim != null) {
			model.addAttribute("reim", reim);
			model.addAttribute("incentsgst", "Reimbursement of deposited GST (after deduction of Input Tax Credit) As per para 3.2");
		}
		if (oth != null) {
			model.addAttribute("oth", oth);
			model.addAttribute("incentoth","Other incentive(s) Subsidy of Rules-2017");
		}
		if (stam != null) {
			model.addAttribute("stam", stam);
			model.addAttribute("incetstamp", "Stamp Duty Exemption As per para 3.1 of Rules-2017");
		}

		Long other;
		int flag = 0;
		int marker = 0;

		if (sgst != null && sgst != 0) {
			flag++;
			model.addAttribute("sgst", sgst);
		}
		if (cis != null && cis != 0) {
			model.addAttribute("cis", cis);
			flag++;
		}
		/*
		 * if (qis != null && qis != 0) { model.addAttribute("qis", qis); flag++; }
		 */
		if (stamp != null && stamp != 0) {
			model.addAttribute("stamp", stamp);
			flag++;
		}
		if (epf != null && epf != 0) {
			model.addAttribute("epf", epf);
			flag++;
		}
		if (infra != null && infra != 0) {
			model.addAttribute("infra", infra);
			flag++;
		}
		other = elecdutyCap + elecdutyCapPC + mandi + payroll;
		if (other != null && other != 0) {
			model.addAttribute("other", other);
			flag++;
		}
		System.out.println(flag);
		int as = fillincentive(appId, session);
		if (flag == as) {
			model.addAttribute("proceed", "proceed");
		} else
			model.addAttribute("proceed", "stop");
	}
	}
	public int fillincentive(String appid, HttpSession session) {

		int marker = 0;

		String appId = (String) session.getAttribute("appId");

		Discis capinvest = disbursmentCisIncentiveRepository.getDetailsBydisAppId(appId);
		// String capinv = capinvest.getDisAppId();

		Disiis infrasub = disbursmentIisIncentiveRepository.getDetailsBydisAppId(appId);
		// String infrainv = infrasub.getDisAppId();

		Disepfriem epfinv = disEPFRepository.getDetailsByappId(appId);
		// String epfince = epfinv.getAppId();

		DisStampDeauty stamp = stampDutyService.getDetailsByStampApcId(appId);

		OtherIncentive oth = otherDetailService.getDetailsByOthApcid(appId);

		DissbursmentReimbrsDepositeGST reim = disbursmentReimbrsofDepositGSTRepository.getDetailsBydisAppId(appId);
		// String sgstreim = reim.getDisAppId();
		if (reim != null) {
			marker++;
		}
		if (epfinv != null) {
			marker++;
		}
		if (capinvest != null) {
			marker++;
		}

		if (stamp != null) {
			marker++;
		}

		if (oth != null) {
			marker++;
		}

		if (infrasub != null) {
			marker++;
		}

		return marker;
	}

	private void totalSkilledAndUnSkilledEmploment(Model model,
			List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist) {
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;
		if (skilledUnSkilledEmployemntslist.size() > 0) {

			for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntslist) {
				if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
					if (count.getNumberofMaleEmployees() != null) {
						skilledEmploymentMale = skilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						skilledEmploymentFemale = skilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalSkilledEmployment = (skilledEmploymentMale + skilledEmploymentFemale);
					model.addAttribute("skilledEmploymentMale", skilledEmploymentMale);
					model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale);
					model.addAttribute("totalSkilledEmployment", totalSkilledEmployment);
					model.addAttribute("skilledDisplay", "skilledDisplay");

				} else {
					if (count.getNumberofMaleEmployees() != null) {
						unSkilledEmploymentMale = unSkilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						unSkilledEmploymentFemale = unSkilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalUnSkilledEmployment = (unSkilledEmploymentMale + unSkilledEmploymentFemale);
					model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale);
					model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale);
					model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment);
					model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
				}
			}

		}
		model.addAttribute("grossTotalMaleEmployment", (skilledEmploymentMale + unSkilledEmploymentMale));
		model.addAttribute("grossTotalFemaleEmployment", (skilledEmploymentFemale + unSkilledEmploymentFemale));
		model.addAttribute("grossTotalMaleandFemaleEmployment", ((skilledEmploymentFemale + unSkilledEmploymentFemale)
				+ (skilledEmploymentMale + unSkilledEmploymentMale)));
	}

	/*
	 * @Override public BusinessEntityDetailsFood findByunitid(String unitid) {
	 * return foodRepos.findByunitid(unitid); }
	 */
}
