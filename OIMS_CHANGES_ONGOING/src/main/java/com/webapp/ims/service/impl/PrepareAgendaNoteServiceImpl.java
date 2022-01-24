package com.webapp.ims.service.impl;

import static com.webapp.ims.exception.GlobalConstants.LARGE;
import static com.webapp.ims.exception.GlobalConstants.MEDIUM;
import static com.webapp.ims.exception.GlobalConstants.MEGA;
import static com.webapp.ims.exception.GlobalConstants.MEGAPLUS;
import static com.webapp.ims.exception.GlobalConstants.SMALL;
import static com.webapp.ims.exception.GlobalConstants.SUPERMEGA;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.webapp.ims.controller.ApplicationDetailsViewController;
import com.webapp.ims.controller.PrepareAgendaNoteController;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.model.EvaluateMeanOfFinance;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.EvaluationAuditTrail;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.ApplicationFwdRepository;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.repository.ScrutinyDocumentRepo;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateCapInvestService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateMeanofFinanceService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;

@Service
@Transactional
public class PrepareAgendaNoteServiceImpl implements PrepareAgendaNoteService {

	private final Logger logger = LoggerFactory.getLogger(PrepareAgendaNoteController.class);

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepository;

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public ApplicationDetailsViewController applicantViewController;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;

	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPwInvRepository;

	@Autowired
	public EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;

	@Autowired
	private DeptBusinessEntityDetailsRepository deptBusEntRepository;

	@Autowired
	private DeptProposedEmploymentDetailsRepository deptPrpsEmplRepository;

	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	private ExistingProjectDetailsRepository existProjRepository;

	@Autowired
	private EvaluateCapInvestService evalCapInvService;

	@Autowired
	InvestmentDetailsRepository investRepository;
	@Autowired

	BusinessEntityDetailsService businessService;
	@Autowired
	ProprietorDetailsService proprietorService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;
	@Autowired
	AvailCustmisedDetailsService availCustmisedDetailsService;
	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	private EvaluateMeanofFinanceService evalMOf;

	@Autowired
	ScrutinyDocumentRepo scrutinyDocumentRepo;

	@Autowired
	GlobalServiceUtil globalServiceUtil;

	@Autowired
	ApplicationFwdRepository applicationFwdRepository;

	public List<ExistingProjectDetails> existProjList = new ArrayList<>();
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();
	private List<EvaluateMeanOfFinance> evalMeanofFinanceList = new LinkedList<>();

	public List<PrepareAgendaNotes> getPrepareAgendaNote(HttpSession session) {
		String role = (String) session.getAttribute("userr");
		List<PrepareAgendaNotes> prepareAgendaList = new ArrayList<>();
		PrepareAgendaNotes prepareAgendaNotes;
		boolean status = true;
		List<String> appId = applicationFwdRepository.searchApplicationByRole(role, status);
		for (String appliId : appId) {
			if (!appliId.isEmpty()) {
				prepareAgendaNotes = new PrepareAgendaNotes();
				prepareAgendaNotes = prepareAgendaNoteRepository.findByAppliId(appliId);
				if (prepareAgendaNotes != null) {
					prepareAgendaList.add(prepareAgendaNotes);

				}
			}

		}
		return prepareAgendaList;
	}

	@Override
	public List<PrepareAgendaNotes> getPrepareAgendaNoteSubmit(HttpSession session) {
		String role = "Submit"; // (String) session.getAttribute("role");
		//String role = (String) session.getAttribute("userr");
		List<PrepareAgendaNotes> prepareAgendaList = new ArrayList<>();

		boolean status = true;
		List<String> appId = applicationFwdRepository.searchApplicationByRole(role, status);
		for (String appliId : appId) {
			if (!appliId.isEmpty()) {
				PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
				prepareAgendaNotes = prepareAgendaNoteRepository.findByAppliId(appliId);
				if (prepareAgendaNotes != null) {
					prepareAgendaList.add(prepareAgendaNotes);
				}
			}

		}
		return prepareAgendaList;
	}

	@Override
	public List<PrepareAgendaNotes> getPreAgendaNote(HttpSession session) {
		String role = "PICUP Processing Officer"; // (String) session.getAttribute("PICUP Processing Officer");
		//String role = (String) session.getAttribute("userr");
		List<PrepareAgendaNotes> prepareAgendaList = new ArrayList<>();
		PrepareAgendaNotes prepareAgendaNotes;
		boolean status = true;
		List<String> appId = applicationFwdRepository.searchApplicationByRole(role, status);
		for (String appliId : appId) {
			if (!appliId.isEmpty()) {
				prepareAgendaNotes = new PrepareAgendaNotes();
				prepareAgendaNotes = prepareAgendaNoteRepository.findByAppliId(appliId);
				if (prepareAgendaNotes != null) {
					prepareAgendaList.add(prepareAgendaNotes);
				}
			}

		}
		return prepareAgendaList;
	}

	@Override
	public List<PrepareAgendaNotes> getPrepareViewAgendaNoteList(HttpSession session) {
		String role = "Send for Approval"; // (String) session.getAttribute("PICUP Processing Officer");
		//String role = (String) session.getAttribute("userr");
		List<PrepareAgendaNotes> prepareAgendaList = new ArrayList<>();
		PrepareAgendaNotes prepareAgendaNotes;
		boolean status = true;
		List<String> appId = applicationFwdRepository.searchApplicationByRole(role, status);
		for (String appliId : appId) {
			if (!appliId.isEmpty()) {
				prepareAgendaNotes = new PrepareAgendaNotes();
				prepareAgendaNotes = prepareAgendaNoteRepository.findByAppliId(appliId);
				if (prepareAgendaNotes != null) {
					prepareAgendaList.add(prepareAgendaNotes);
				}
			}

		}
		return prepareAgendaList;
	}

	@Override
	public List<PrepareAgendaNotes> getPrepareAgendaNoteBySendforApproval(HttpSession session) {
		String role = "Send for Approval";
		//String role = (String) session.getAttribute("userr");
		List<PrepareAgendaNotes> prepareAgendaList = new ArrayList<>();
		PrepareAgendaNotes prepareAgendaNotes;
		boolean status = true;
		List<String> appId = applicationFwdRepository.searchApplicationBySendByApproval(role, status);
		for (String appliId : appId) {
			if (!appliId.isEmpty()) {
				prepareAgendaNotes = new PrepareAgendaNotes();
				prepareAgendaNotes = prepareAgendaNoteRepository.findByAppliId(appliId);
				if (prepareAgendaNotes != null) {
					prepareAgendaList.add(prepareAgendaNotes);
				}
			}

		}
		return prepareAgendaList;
	}

	@Override
	public List<PrepareAgendaNotes> getAllPrepareAgendaNote() {

		return prepareAgendaNoteRepository.findAll();
	}

	@Override
	@Query(value = "select PrepareAgendaNotes where appliId = :appliId ")
	public PrepareAgendaNotes getPrepareByAppliId(String appliId) {
		return prepareAgendaNoteRepository.getPrepareByAppliId(appliId);
	}

	@Override
	public List<PrepareAgendaNotes> findPrepAgendaNotesListByUserId(String userid) {
		return prepareAgendaNoteRepository.findPrepAgendaNotesListByUserId(userid);
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

	private void totalSkilledAndUnSkilledEmploment(Model model) {
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;

		if (!skillUnskillEmplList.isEmpty()) {

			for (SkilledUnSkilledEmployemnt count : skillUnskillEmplList) {
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
		model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
		model.addAttribute("grossTotalMaleEmployment", (skilledEmploymentMale + unSkilledEmploymentMale));
		model.addAttribute("grossTotalFemaleEmployment", (skilledEmploymentFemale + unSkilledEmploymentFemale));
		model.addAttribute("grossTotalMaleandFemaleEmployment", ((skilledEmploymentFemale + unSkilledEmploymentFemale)
				+ (skilledEmploymentMale + unSkilledEmploymentMale)));
	}

	@Override
	public void common(Model model, HttpServletRequest request, HttpSession session) {

		String appId = (String) session.getAttribute("appId");
		String businId = "";
		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		String projId = appStr + "P1";

		if (appId != null) {
			PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
			if (prepareAgendaNotesList != null) {
				model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
			} else {
				model.addAttribute("enableIncludeAgenda", "");
			}

		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			model.addAttribute("newProject", evaluateProjectDetails.getNewProject());
			model.addAttribute("region", evaluateProjectDetails.getResionName().trim());
			model.addAttribute("district", evaluateProjectDetails.getDistrictName().trim());
			model.addAttribute("prodDetailObserv", evaluateProjectDetails.getProdDetailObserv());
			model.addAttribute("propsProdtDetailObserv", evaluateProjectDetails.getPropsProdtDetailObserv());
			model.addAttribute("listAssets", evaluateProjectDetails.getListAssets());
			model.addAttribute("listAssetsObserv", evaluateProjectDetails.getListAssetsObserv());
			model.addAttribute("anexurUndertkObserv", evaluateProjectDetails.getAnexurUndertkObserv());
			model.addAttribute("expDivfObserv", evaluateProjectDetails.getExpDivfObserv());
			model.addAttribute("locationObserv", evaluateProjectDetails.getLocationObserv());
			model.addAttribute("projectObserv", evaluateProjectDetails.getProjectObserv());
			model.addAttribute("fullAddrs", evaluateProjectDetails.getFullAddress());
			model.addAttribute("solarCaptivePower", evaluateProjectDetails.getSolarCaptivePower());
			model.addAttribute("solarCaptivePowerObserv", evaluateProjectDetails.getSolarCaptivePowerObserv());

			String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion()
					: "";
			String diversification = evaluateProjectDetails.getDiversification() != null
					? evaluateProjectDetails.getDiversification()
					: "";

			if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				// fetch documents from MongoDB
				applicantViewController.existProjDocsFromMongoDB(evaluateProjectDetails.getId(), model);
				model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());
				model.addAttribute("natureOfProject", expension);
				if (!expension.isEmpty() && !diversification.isEmpty()) {
					model.addAttribute("natureOfProject", expension + "/ " + diversification);
				} else if (!diversification.isEmpty()) {
					model.addAttribute("natureOfProject", diversification);
				}
				List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
						.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
				model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);
			} else {
				// fetch documents from MongoDB
				applicantViewController.newProjDocMongoDB(evaluateProjectDetails.getId(), model);

				model.addAttribute("natureOfProject", evaluateProjectDetails.getNatureOfProject());
				model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());

			}

			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}
		model.addAttribute("raiseQuery", new RaiseQuery());
		String userId = (String) session.getAttribute("userId");
		List<EvaluationAuditTrail> EvaluationAuditTrailList = evaluateAuditTrialRepository
				.getEvaluAudTraByAppliIdUserId(appId, userId);
		model.addAttribute("EvaluationAuditTrailList", EvaluationAuditTrailList);

		// Propriter Details
		List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
		model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appId);
			if (emplDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
						.getSkilledUnSkilledEmployemnt();
				model.addAttribute("proposedEmploymentDetails", emplDetail);
				skillUnskillEmplList.clear();
				skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
				model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
				totalSkilledAndUnSkilledEmploment(model);
				model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

			} else {
				skillUnskillEmplList.clear();
				model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (!deptPwInvRepository.findByPwApcId(appId).isEmpty()) {
				List<Dept_PhaseWiseInvestmentDetails> deptPwInvList = deptPwInvRepository.findByPwApcId(appId);
				model.addAttribute("deptPwInvList", deptPwInvList);
			}

			EvaluateInvestmentDetails evaluateInvestMentDetails = null;
			if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null) {
				evaluateInvestMentDetails = evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId);
			}

			// fetch documents from MongoDB
			applicantViewController.investMongoDBDoc(evaluateInvestMentDetails.getInvId(), model);

			model.addAttribute("InvFCI", evaluateInvestMentDetails.getInvFci());
			model.addAttribute("optedcufoffdate", evaluateInvestMentDetails.getInvCommenceDate());
			model.addAttribute("invPropCommProdDate", evaluateInvestMentDetails.getPropCommProdDate());
			model.addAttribute("category", evaluateInvestMentDetails.getInvIndType());
			model.addAttribute("InvTPC", evaluateInvestMentDetails.getInvTotalProjCost());
			model.addAttribute("InvLC", evaluateInvestMentDetails.getInvLandCost());
			model.addAttribute("InvBuiildC", evaluateInvestMentDetails.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", evaluateInvestMentDetails.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			evaluateProjectDetails.setInvFci(evaluateInvestMentDetails.getInvFci());
			evaluateProjectDetails.setInvTotalProjCost(evaluateInvestMentDetails.getInvTotalProjCost());
			evaluateProjectDetails.setInvIndType(evaluateInvestMentDetails.getInvIndType());

			model.addAttribute("invIemNumber", evaluateInvestMentDetails.getInvIemNumber());
			model.addAttribute("invGovtEquity", evaluateInvestMentDetails.getInvGovtEquity());
			model.addAttribute("invEligcapInvest", evaluateInvestMentDetails.getInvEligcapInvest());
			model.addAttribute("invCAStatutoryAmount", evaluateInvestMentDetails.getInvCAStatutoryAmt());

			model.addAttribute("invLandcostFci", evaluateInvestMentDetails.getLandcostFci());
			model.addAttribute("invBuildingFci", evaluateInvestMentDetails.getBuildingFci());
			model.addAttribute("invPlantAndMachFci", evaluateInvestMentDetails.getPlantAndMachFci());
			model.addAttribute("invFixedAssetFci", evaluateInvestMentDetails.getFixedAssetFci());
			model.addAttribute("invLandcostIIEPP", evaluateInvestMentDetails.getLandcostIIEPP());
			if (evaluateInvestMentDetails.getBuildingIIEPP() != null) {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getBuildingIIEPP());
			} else {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getInvLandCost());
			}
			if (evaluateInvestMentDetails.getPlantAndMachIIEPP() != null) {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getPlantAndMachIIEPP());
			} else {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getInvPlantAndMachCost());
			}
			if (evaluateInvestMentDetails.getFixedAssetIIEPP() != null) {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getFixedAssetIIEPP());
			} else {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getInvOtherCost());
			}

			model.addAttribute("invcatundtakObserv", evaluateInvestMentDetails.getCatIndusUndtObserv());
			model.addAttribute("propsPlntMcnryCostObserv", evaluateInvestMentDetails.getPropsPlntMcnryCostObserv());
			model.addAttribute("optcutofdateObserv", evaluateInvestMentDetails.getOptcutofdateobserv());
			model.addAttribute("dateofcumProdObserv", evaluateInvestMentDetails.getDateofComProdObserv());
			model.addAttribute("projreportObserv", evaluateInvestMentDetails.getDetailProjReportObserv());
			model.addAttribute("propCapInvObserv", evaluateInvestMentDetails.getPropCapInvObserv());
			model.addAttribute("totlcostprojObserv", evaluateInvestMentDetails.getTotlCostProjObserv());
			model.addAttribute("mofObserv", evaluateInvestMentDetails.getMofObserv());
			model.addAttribute("IemRegObserv", evaluateInvestMentDetails.getIemRegObserv());
			model.addAttribute("IndusUndrtkObserv", evaluateInvestMentDetails.getIndusUntkObserv());

			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblInvPerdMegaObserv", evaluateInvestMentDetails.getEligblInvPerdMegaObserv());
			model.addAttribute("eligblInvPerdSupermegaObserv",
					evaluateInvestMentDetails.getEligblInvPerdSupermegaObserv());
			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblCapInvObserv", evaluateInvestMentDetails.getEligblCapInvObserv());
			model.addAttribute("projPhasesObserv", evaluateInvestMentDetails.getProjPhasesObserv());
			model.addAttribute("caStatutoryObserv", evaluateInvestMentDetails.getCaStatutoryObserv());
			model.addAttribute("authSignatoryObserv", evaluateInvestMentDetails.getAuthSignatoryObserv());
			model.addAttribute("appformatObserv", evaluateInvestMentDetails.getAppformatObserv());

			if (evaluateInvestMentDetails.getInvCAStatutoryDate() != null) {
				try {
					DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
					// parse the date string into Date object
					Date date = srcDf.parse(evaluateInvestMentDetails.getInvCAStatutoryDate().toString());
					DateFormat destDf = new SimpleDateFormat("dd/MM/yyyy");
					// format the date into another format
					String dateStr = destDf.format(date);
					model.addAttribute("invCAStatutoryDate", dateStr);
				} catch (ParseException e) {
					logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
				}
			} else {
				model.addAttribute("invCAStatutoryDate", evaluateInvestMentDetails.getInvCAStatutoryDate());
			}

			// EvaluateApplication Breakup of proposed capital Investment Remarks
			model.addAttribute("invFixedAssetRemarks", evaluateInvestMentDetails.getFixedAssetRemarks());
			model.addAttribute("invPlantAndMachRemarks", evaluateInvestMentDetails.getPlantAndMachRemarks());
			model.addAttribute("invBuildingRemarks", evaluateInvestMentDetails.getBuildingRemarks());
			model.addAttribute("invLandcostRemarks", evaluateInvestMentDetails.getLandcostRemarks());

			String categorytype = evaluateInvestMentDetails.getInvIndType();

			if ((categorytype.equalsIgnoreCase(SMALL)) || (categorytype.equalsIgnoreCase(MEDIUM))) {
				request.setAttribute("ctypeSMALL", "Yes");
			} else {
				request.setAttribute("ctypeSMALL", "No");
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				request.setAttribute("ctypeLARGE", "Yes");
			} else {
				request.setAttribute("ctypeLARGE", "No");
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				request.setAttribute("ctypeMEGA", "Yes");
			} else {
				request.setAttribute("ctypeMEGA", "No");
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				request.setAttribute("ctypeSUPERMEGA", "Yes");
			} else {
				request.setAttribute("ctypeSUPERMEGA", "No");
			}

			String phsWsAply = evaluateInvestMentDetails.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				request.setAttribute("phsWsAply", "Yes");
			} else {
				request.setAttribute("phsWsAply", "No");
			}

			Object LCasperIEPP = additionalInterest.getProposedFCI(appId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			// BusinessEntityDetails records fetch from table

			DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);
			model.addAttribute("authSignName", deptBusEntFromDb.getAuthorisedSignatoryName());
			model.addAttribute("GSTno", deptBusEntFromDb.getGstin());
			model.addAttribute("companyPANno", deptBusEntFromDb.getCompanyPanNo());

			model.addAttribute("businessAddress", deptBusEntFromDb.getBusinessAddress());

			model.addAttribute("isPresFormat", deptBusEntFromDb.getPresFormat());
			model.addAttribute("isDocAuthorized", deptBusEntFromDb.getDocAuthorized());
			model.addAttribute("dirDetailObserv", deptBusEntFromDb.getDirDetailsObserv());
			model.addAttribute("supprtdocObserv", deptBusEntFromDb.getSupprtdocObserv());
			model.addAttribute("gstinObserv", deptBusEntFromDb.getGstinObserv());
			model.addAttribute("panObserv", deptBusEntFromDb.getPanObserv());

			// PropsedEmpDetails
			DeptProposedEmploymentDetails deptProEmploymentDetails = deptPrpsEmplRepository.findByappId(appId);
			model.addAttribute("totalEmpDetailObserv", deptProEmploymentDetails.getTotalDetailObserv());

			// fetch BusinessEntity documents from MongoDB
			applicantViewController.businessDocFromMongoDB(deptBusEntFromDb.getId(), model);

			// incentive details
			String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);

			if (IncentiveDetail != null) {
				// PICUP Officer's Remark
				incentiveSpecificDetails.setSgstRemark(deptIncDetails.getSgstRemark());
				incentiveSpecificDetails.setScstRemark(deptIncDetails.getScstRemark());
				incentiveSpecificDetails.setStampDutyExemptRemark(deptIncDetails.getStampDutyExemptRemark());
				incentiveSpecificDetails.setStampDutyReimRemark(deptIncDetails.getStampDutyReimRemark());
				incentiveSpecificDetails.setCapIntSubRemark(deptIncDetails.getCapIntSubRemark());
				incentiveSpecificDetails.setInfraIntSubRemark(deptIncDetails.getInfraIntSubRemark());
				incentiveSpecificDetails.setLoanIntSubRemark(deptIncDetails.getLoanIntSubRemark());
				incentiveSpecificDetails.setInputTaxRemark(deptIncDetails.getInputTaxRemark());
				incentiveSpecificDetails.setElecDutyCapRemark(deptIncDetails.getElecDutyCapRemark());
				incentiveSpecificDetails.setElecDutyDrawnRemark(deptIncDetails.getElecDutyDrawnRemark());
				incentiveSpecificDetails.setDiffAbleWorkRemark(deptIncDetails.getDiffAbleWorkRemark());
				incentiveSpecificDetails.setMandiFeeRemark(deptIncDetails.getMandiFeeRemark());
				incentiveSpecificDetails.setFwRemark(deptIncDetails.getFwRemark());
				incentiveSpecificDetails.setBplRemark(deptIncDetails.getBplRemark());
				incentiveSpecificDetails.setDivyangSCSTRemark(deptIncDetails.getDivyangSCSTRemark());
				incentiveSpecificDetails.setEpfDvngSCSTRemark(deptIncDetails.getEpfDvngSCSTRemark());
				incentiveSpecificDetails.setEpfSklUnsklRemark(deptIncDetails.getEpfSklUnsklRemark());
				incentiveSpecificDetails.setEpfUnsklRemark(deptIncDetails.getEpfUnsklRemark());
				incentiveSpecificDetails.setAciSubsidyRemark(deptIncDetails.getAciSubsidyRemark());
				incentiveSpecificDetails.setAiiSubsidyRemark(deptIncDetails.getAiiSubsidyRemark());

				incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
				incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
				incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
				incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
				incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

				incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
				incentiveSpecificDetails
						.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
				incentiveSpecificDetails.setISF_Amt_Stamp_Duty_Reim(IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
				incentiveSpecificDetails.setISF_Ttl_Stamp_Duty_EX(IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());

				incentiveSpecificDetails.setISF_Epf_Reim_UW(IncentiveDetail.getISF_Epf_Reim_UW());
				incentiveSpecificDetails.setISF_Add_Epf_Reim_SkUkW(IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
				incentiveSpecificDetails.setISF_Add_Epf_Reim_DIVSCSTF(IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
				incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetail.getISF_Ttl_EPF_Reim());// 1

				incentiveSpecificDetails.setISF_Cis(IncentiveDetail.getISF_Cis());
				incentiveSpecificDetails.setISF_ACI_Subsidy_Indus(IncentiveDetail.getISF_ACI_Subsidy_Indus());
				incentiveSpecificDetails.setISF_Infra_Int_Subsidy(IncentiveDetail.getISF_Infra_Int_Subsidy());
				incentiveSpecificDetails.setISF_AII_Subsidy_DIVSCSTF(IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
				incentiveSpecificDetails.setISF_Loan_Subsidy(IncentiveDetail.getISF_Loan_Subsidy());
				incentiveSpecificDetails.setISF_Total_Int_Subsidy(IncentiveDetail.getISF_Total_Int_Subsidy());

				incentiveSpecificDetails.setISF_Tax_Credit_Reim(IncentiveDetail.getISF_Tax_Credit_Reim());
				incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetail.getISF_EX_E_Duty());
				incentiveSpecificDetails.setISF_EX_E_Duty_PC(IncentiveDetail.getISF_EX_E_Duty_PC());
				incentiveSpecificDetails.setISF_EX_Mandee_Fee(IncentiveDetail.getISF_EX_Mandee_Fee());
				incentiveSpecificDetails.setISF_Indus_Payroll_Asst(IncentiveDetail.getISF_Indus_Payroll_Asst());
				incentiveSpecificDetails.setTotal_Other_Incentive(IncentiveDetail.getTotal_Other_Incentive());

				incentiveSpecificDetails.setISF_Cstm_Inc_Status(IncentiveDetail.getISF_Cstm_Inc_Status());
				incentiveSpecificDetails.setIsfCustIncDocName(IncentiveDetail.getIsfCustIncDocName());
				incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail.getIsfCustIncDocName().getBytes());

				incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail.getIsfSgstComment());
				incentiveSpecificDetails.setIsfScstComment(IncentiveDetail.getIsfScstComment());
				incentiveSpecificDetails.setIsffwComment(IncentiveDetail.getIsffwComment());
				incentiveSpecificDetails.setIsfBplComment(IncentiveDetail.getIsfBplComment());
				incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail.getIsfElecdutyComment());
				incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail.getIsfMandiComment());
				incentiveSpecificDetails.setIsfStampComment(IncentiveDetail.getIsfStampComment());

				incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail.getIsfStampremComment());
				incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail.getIsfStampscstComment());
				incentiveSpecificDetails.setIsfepfComment(IncentiveDetail.getIsfepfComment());
				incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail.getIsfepfaddComment());
				incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail.getIsfepfscComment());
				incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail.getIsfcapisComment());
				incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail.getIsfcapisaComment());
				incentiveSpecificDetails.setIsfinfComment(IncentiveDetail.getIsfinfComment());
				incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail.getIsfinfaComment());
				incentiveSpecificDetails.setIsfloanComment(IncentiveDetail.getIsfloanComment());
				incentiveSpecificDetails.setIsfdisComment(IncentiveDetail.getIsfdisComment());
				incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail.getIsfelepodownComment());
				incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail.getIsfdifferabilComment());

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail.getId());

				if (deptIncDetails.getModify_Date() != null) {
					try {
						DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						// parse the date string into Date object
						Date date = srcDf.parse(deptIncDetails.getModify_Date().toString());
						DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
						// format the date into another format
						String dateStr = destDf.format(date);
						model.addAttribute("onlineSubmitDate", dateStr);
					} catch (ParseException e) {
						logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
					}
				} else {
					model.addAttribute("onlineSubmitDate", deptIncDetails.getModify_Date());
				}

				model.addAttribute("subDateAppObserv", deptIncDetails.getSubDateAppObserv());
				try {
					long totOther = incentiveSpecificDetails.getTotal_Other_Incentive() == null ? 0
							: incentiveSpecificDetails.getTotal_Other_Incentive();

					long totIntSub = incentiveSpecificDetails.getISF_Total_Int_Subsidy() == null ? 0
							: incentiveSpecificDetails.getISF_Total_Int_Subsidy();
					long totStamp = incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX() == null ? 0
							: incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX();
					long totSGST = incentiveSpecificDetails.getISF_Ttl_SGST_Reim() == null ? 0
							: incentiveSpecificDetails.getISF_Ttl_SGST_Reim();
					long totEPF = incentiveSpecificDetails.getISF_Ttl_EPF_Reim() == null ? 0
							: incentiveSpecificDetails.getISF_Ttl_EPF_Reim();

					Long total = totOther + totIntSub + totStamp + totSGST + totEPF;

					model.addAttribute("total", total);
				} catch (Exception e) {
					logger.error(String.format("#### evaluateApplication exception #### %s", e.getMessage()));
				}
			}

			List<Object[]> productsList = existProjRepository.findProductsByProjId(projId);
			StringBuilder products = new StringBuilder();
			if (!productsList.isEmpty()) {
				for (Object[] prodArr : productsList) {
					if (prodArr.length > 0) {
						for (int k = 0; k < prodArr.length; k++) {
							products = products.append(prodArr[k].toString()).append(", ");
						}
					}
				}
				model.addAttribute("products", products.toString().substring(0, products.toString().lastIndexOf(',')));
			}
			model.addAttribute("products", "Dont have Product");
			List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
			model.addAttribute("evalObjList", evalObjList);

			model.addAttribute("appId", appId);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			model.addAttribute("EvaluateProjectDetails", evaluateProjectDetails);
		} catch (Exception e) {
			logger.error(String.format("^^^^common evaluateApplication exception ^^^^^ %s", e.getMessage()));
		}

		List<Object[]> productsList = existProjRepository.findProductsByProjId(projId);
		StringBuilder products = new StringBuilder();
		if (!productsList.isEmpty()) {
			for (Object[] prodArr : productsList) {
				if (prodArr.length > 0) {
					for (int k = 0; k < prodArr.length; k++) {
						products = products.append(prodArr[k].toString()).append(", ");
					}
				}
			}
			model.addAttribute("products", products.toString().substring(0, products.toString().lastIndexOf(',')));
		}

		evalCapitalInvList = evalCapInvService.getEvalCapitalInvListByApplId(appId);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);

		evalMeanofFinanceList = evalMOf.getEvalMeanofFinanceListByApplId(appId);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);

		List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
				.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
		model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);

	}

	/*
	 * @Override public void downloadFiles(String fileName, HttpServletResponse
	 * response, HttpSession session) { String appId = (String)
	 * session.getAttribute("appId"); String ScruDocId = appId + "SCRU";
	 * 
	 * }
	 */
	/*
	 * @Override public void downloadFiles(String fileName, HttpServletResponse
	 * response, HttpSession session) { String appId = (String)
	 * session.getAttribute("appId"); String ScruDocId = appId + "SCRU";
	 * 
	 * <<<<<<< .mine List<ScrutinyDocument> scrutinyDocumentList =
	 * scrutinyDocumentRepo.getListByScruDocId(ScruDocId); if
	 * (!scrutinyDocumentList.isEmpty()) { for (ScrutinyDocument scruDoc :
	 * scrutinyDocumentList) { if (fileName.equals(scruDoc.getFileName())) { String
	 * scruDocName = scruDoc.getFileName(); byte[] scruDocData = scruDoc.getData();
	 * globalServiceUtil.downloadMongoDBDoc(scruDocName, scruDocData, response);
	 * break; } } } }
	 */

}
