package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.INTERNAL_FILE;
import static com.webapp.ims.exception.GlobalConstants.INVESTMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_LIST;
import static com.webapp.ims.exception.GlobalConstants.Unit_Id;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.INVInstalledCapacities;
import com.webapp.ims.model.INVOthers;
import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.InvestmentDocument;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.model.Unit;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.InvestmentDocumentRepository;
import com.webapp.ims.repository.UnitRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.INVInstalledCapacitiesService;
import com.webapp.ims.service.INVOthersService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.InvestmentDocumentService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
public class InvestmentDetailsController {
	private final Logger logger = LoggerFactory.getLogger(InvestmentDetailsController.class);
	List<PhaseWiseInvestmentDetails> pwInvList = new LinkedList<>();
	@Autowired
	private ApplicantDetailsService applicantDetailsService;
	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	InvestmentDocumentService invDocService;
	@Autowired
	InvestmentDocumentRepository invDocRepository;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;

	private InvestmentDetailsService investDs;
	private PhaseWiseInvestmentDetailsService pwInvestDs;
	private int pwIndex = 0;
	private boolean isPwRowRecord = false;
	private AtomicInteger atomicInteger = new AtomicInteger();
	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	AdditionalInterest additionalInterest;
	@Autowired
	private ProjectService projectService;
	@Autowired
	UnitRepository unitRepository;

	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	private INVOthersService invOthersService;

	@Autowired
	private MeansOfFinanceService meansOfFinanceService;

	@Autowired
	private INVInstalledCapacitiesService iNVInstalledCapacitiesService;

	@Autowired
	public InvestmentDetailsController(BreakupCostService brkupService, InvestmentDetailsService investDs,
			PhaseWiseInvestmentDetailsService pwInvestDs, ApplicantDetailsService applDs,
			MeansOfFinanceService mofService) {
		super();
		this.investDs = investDs;
		this.pwInvestDs = pwInvestDs;
	}

	@InitBinder(PW_INVESTMENT_DETAILS)
	public void pwDateCustomize(WebDataBinder binder) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "pwPropProductDate", new CustomDateEditor(dateFormatter, true));
	}

	@InitBinder(INVESTMENT_DETAILS)
	public void invDateCustomize(WebDataBinder binder) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			binder.registerCustomEditor(Date.class, "propCommProdDate", new CustomDateEditor(dateFormatter, true));
			binder.registerCustomEditor(Date.class, "invCommenceDate", new CustomDateEditor(dateFormatter, true));
			binder.registerCustomEditor(Date.class, "pwPropProductDate", new CustomDateEditor(dateFormatter, true));
		} catch (Exception e) {
			logger.error(String.format("#### data format Investment exception $$$ %s", e.getMessage()));
		}
	}

	/*
	 * This method is responsible to save InvestmentDetails form data in the
	 * respective tables.
	 */
	public void commonSaveInvestmentDetails(InvestmentDetails invDetl, BindingResult result, Model model,
			HttpSession session, MultipartFile invMultipartFile, MultipartFile regiOrLicenseFileName) {

		try {
			model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);

			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			String appId = investResponce.get("appID");
			String investId = investResponce.get(Unit_Id) + "I1";
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));

			invDetl.setApplId(appId);
			invDetl.setInvStatus("active");
			invDetl.setInvCreatedBy("User");
			invDetl.setInvId(investId);
			invDetl.setInvModifiyDate(new Date());
			Date a = invDetl.getInvCommenceDate();
			String b = invDetl.getInvIndType();
			Date c = invDetl.getPropCommProdDate();
			String d = invDetl.getRegiOrLicense();

			investDs.updateInvestmentBasicDetails(b, a, c, d);
			// save InvestmentDetails record in table
			Long sumInvFci = 0L;
			Long sumLandCost = 0L;
			Long sumBuildingCost = 0L;
			Long sumPNM = 0L;
			Long sumMAsset = 0L;
		
			
			List<PhaseWiseInvestmentDetails> pwApcId=pwInvestDs.findByPwApcId(appId);
			for(PhaseWiseInvestmentDetails pw:pwApcId)
			{
				sumLandCost +=pw.getInvLandCost();
				sumInvFci += pw.getInvFci();
				
				sumBuildingCost +=pw.getInvBuildingCost();
				sumPNM += pw.getInvPlantAndMachCost();
				sumMAsset +=pw.getInvOtherCost();
			
				
			}
			
			invDetl.setInvLandCost(sumLandCost);
			invDetl.setInvBuildingCost(sumBuildingCost);
			invDetl.setInvPlantAndMachCost(sumPNM);
			invDetl.setInvOtherCost(sumMAsset);
			invDetl.setInvFci(sumInvFci);
			invDetl.setInvTotalProjCost(sumLandCost+sumBuildingCost+sumPNM+sumMAsset);
			investDs.saveInvestmentDetails(invDetl);
			
//			try {
//				if (!pwInvestDs.getPwInvDetailListById(investId).isEmpty()) {
//					List<PhaseWiseInvestmentDetails> pwList1 = pwInvestDs.getPwInvDetailListById(investId);
//					for (PhaseWiseInvestmentDetails pw : pwList1) {
//						pwInvestDs.deletePwInvDetailsById(pw.getPwId());
//UPSWP21000192301PW1
//					}
//				}
//			} catch (Exception e) {
//				logger.error(String.format("***** commonSaveInvestmentDetails exception ***** %s", e.getMessage()));
//			}

			// save phasewiseInvestment list in table
//			if (!pwInvList.isEmpty()) {
//				pwInvestDs.savePwInvListDetails(pwInvList);
//			}

			// save and update multiple documents in MongoDB collection
			List<MultipartFile> multipartFileList = new LinkedList<>();
			if ((!invMultipartFile.getOriginalFilename().isEmpty() && invMultipartFile.getOriginalFilename() != null)
					&& invMultipartFile.getBytes() != null) {
				multipartFileList.add(invMultipartFile);
			}
			if ((!regiOrLicenseFileName.getOriginalFilename().isEmpty()
					&& regiOrLicenseFileName.getOriginalFilename() != null)
					&& regiOrLicenseFileName.getBytes() != null) {
				multipartFileList.add(regiOrLicenseFileName);
			}

			if (invMultipartFile.getOriginalFilename().isEmpty() || invMultipartFile.getOriginalFilename() == null) {
				List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
				if (invDocList != null && !invDocList.isEmpty()) {
					InvestmentDocument invDocObj = invDocRepository.findByDocId(invDocList.get(0).getDocId());
					InvestmentDocument dbFile = new InvestmentDocument();
					dbFile.set_id(invDocObj.get_id());
					dbFile.setFileName(invDocObj.getFileName());
					dbFile.setFileType(invDocObj.getFileType());
					dbFile.setData(invDocObj.getData());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());

					dbFile.setInvestId(investId);
					dbFile.setDocId(invDocObj.getDocId());
					invDocRepository.save(dbFile);
				}

			}

			if (regiOrLicenseFileName.getOriginalFilename() == null
					|| regiOrLicenseFileName.getOriginalFilename().isEmpty()) {
				List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
				InvestmentDocument invDocObj = invDocRepository.findByDocId(invDocList.get(1).getDocId());

				InvestmentDocument dbFile = new InvestmentDocument();
				dbFile.setFileName(invDocObj.getFileName());
				dbFile.setFileType(invDocObj.getFileType());
				dbFile.setData(invDocObj.getData());
				dbFile.setDocCreatedBy("User");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				dbFile.setInvestId(investId);
				dbFile.setDocId(invDocObj.getDocId());
				invDocRepository.save(dbFile);
			}

			invDocService.saveAndUpdateMultipleFiles(multipartFileList, session);

			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
			// soapRestData(model, invDetl);
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("#### commonSaveInvestmentDetails exception $$$ %s", e.getMessage()));
		}

	}

	@PostMapping(value = "/saveInvestmentDetails")
	public ModelAndView saveInvestmentDetails(@Validated @ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("invFilename") MultipartFile multipartFile,
			@RequestParam("regiOrLicenseFileName") MultipartFile regiOrLicenseFileName,
			@RequestParam("invCommenceDate") String invCommenceDate,
			@RequestParam("propCommProdDate") String propCommProdDate,
			RedirectAttributes redirectAttribute) {
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		Date date1;
		
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		
		try {
			date1 = formatter1.parse(invCommenceDate);
			Date date2 = formatter1.parse(propCommProdDate);
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date1.getTime());
			java.sql.Timestamp timestamp2 = new java.sql.Timestamp(date2.getTime());

			invDetl.setInvCommenceDate(timestamp);
			invDetl.setPropCommProdDate(timestamp2);
			
			
			List<PhaseWiseInvestmentDetails> pwInvList = pwInvestDs.getPwInvDetailListById(investId);
			
			if (pwInvList.size() <= 0) {
				redirectAttribute.addFlashAttribute("pwInvestDtlMsg",
						"Please Fill the Phase wise Investment Details.");
				return new ModelAndView("redirect:/investmentDetails");
			}
		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		commonSaveInvestmentDetails(invDetl, result, model, session, multipartFile, regiOrLicenseFileName);

		return new ModelAndView("redirect:/skilUnskEmplDet");
	}

	@PostMapping(value = "/saveInvOthers")
	public ModelAndView saveInvOthers(@Validated @ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, Model model, HttpSession session) {
		// commonSaveInvestmentDetails(invDetl, result, model, session, multipartFile,
		// regiOrLicenseFileName);

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String appId = investResponce.get("appID");
		String investId = investResponce.get(Unit_Id) + "I1";
		model.addAttribute("invIndType", investResponce.get("Unit_Category"));

		INVOthers invOthers = new INVOthers();
		invOthers.setApcid(appId);
		invOthers.setInvid(investId);

		if (invDetl.getOtid() != null && !invDetl.getOtid().isEmpty()) {
			invOthers.setMofId(invDetl.getOtid());
		} else {
			invOthers.setMofId(UUID.randomUUID().toString());
		}

		invOthers.setParticulars(invDetl.getParticulars());
		invOthers.setPhaseNumber(invDetl.getPwPhaseNoOT());
		invOthers.setProposedInvestmentInProject(invDetl.getProposedInvestmentInProject());
		invOthersService.save(invOthers);
		model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
		InvestmentDetails inv = new InvestmentDetails();
		inv.setInvIndType(invDetl.getInvIndType());
		model.addAttribute(INVESTMENT_DETAILS, inv);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@PostMapping(value = "/editInvOthers")
	public ModelAndView editInvOthers(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @RequestParam(value = "selectedRow", required = false) String index, Model model,
			HttpSession session) {
		INVOthers pwd = null;
		String investId = "";
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			pwd = invOthersService.findById(index).get();

			invDetl.setParticulars(pwd.getParticulars());
			invDetl.setProposedInvestmentInProject(pwd.getProposedInvestmentInProject());
			invDetl.setPwPhaseNoOT(invDetl.getPwPhaseNoOT());
			invDetl.setOtid(pwd.getMofId());
			// invDetl.setInvFciIc(pwd.getInvFci());

			investId = investResponce.get(Unit_Id) + "I1";
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
			model.addAttribute("edit", "edit");
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
		} catch (Exception e) {
			logger.error(String.format("#### editPhaseWiseInvestData exception $$$ %s", e.getMessage()));
			e.printStackTrace();
		}

		investMongoDBDoc(invDetl, investId);
		try {
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute("phasewiselist", "phasewiselist");
		} catch (Exception e) {
			logger.error(String.format("^^^^ editPhaseWiseInvestData ^^^ %s", e.getMessage()));
		}
		return new ModelAndView("investment_details");

	}

	@PostMapping("/delInvOthers")
	public ModelAndView delInvOthers(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @RequestParam(value = "selectedRow", required = false) String index, Model model,
			HttpSession session) {
		INVInstalledCapacities pwd = null;
		String investId = "";
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			invOthersService.deleteById(index);

			investId = investResponce.get(Unit_Id) + "I1";
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
			model.addAttribute("edit", "edit");
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		investMongoDBDoc(invDetl, investId);
		try {
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute("phasewiselist", "phasewiselist");
		} catch (Exception e) {
			logger.error(String.format("^^^^ editPhaseWiseInvestData ^^^ %s", e.getMessage()));
		}
		return new ModelAndView("redirect:/investmentDetails");

	}

	@PostMapping(value = "/saveInvInstaldCap")
	public ModelAndView saveInvInstaldCap(@Validated @ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, Model model, HttpSession session) {
		// commonSaveInvestmentDetails(invDetl, result, model, session, multipartFile,
		// regiOrLicenseFileName);

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String appId = investResponce.get("appID");
		String investId = investResponce.get(Unit_Id) + "I1";
		model.addAttribute("invIndType", investResponce.get("Unit_Category"));

		INVInstalledCapacities iNVInstalledCapacities = new INVInstalledCapacities();
		iNVInstalledCapacities.setApcId(appId);
		iNVInstalledCapacities.setInvId(investId);
		if (invDetl.getPwic() != null && !invDetl.getPwic().isEmpty()) {
			iNVInstalledCapacities.setPwId(invDetl.getPwic());
		} else {
			iNVInstalledCapacities.setPwId(UUID.randomUUID().toString());
		}

		iNVInstalledCapacities.setCapacity(invDetl.getPwCapacity());
		iNVInstalledCapacities.setInvFci(invDetl.getInvFciIc());
		iNVInstalledCapacities.setProductName(invDetl.getPwProductName());
		iNVInstalledCapacities.setPropProductDate(invDetl.getPwPropProductDate());
		iNVInstalledCapacities.setPhaseNo(invDetl.getPhasenoic());
		iNVInstalledCapacities.setUnit(invDetl.getPwUnit());
		iNVInstalledCapacitiesService.save(iNVInstalledCapacities);
		model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
		InvestmentDetails inv = new InvestmentDetails();
		inv.setInvIndType(invDetl.getInvIndType());
		model.addAttribute(INVESTMENT_DETAILS, inv);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@PostMapping("/editInvInstaldCap")
	public ModelAndView editInvInstaldCap(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @RequestParam(value = "selectedRow", required = false) String index, Model model,
			HttpSession session) {
		INVInstalledCapacities pwd = null;
		String investId = "";
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			pwd = iNVInstalledCapacitiesService.findById(index).get();

			invDetl.setPwCapacity(pwd.getCapacity());
			invDetl.setPwProductName(pwd.getProductName());
			invDetl.setPwUnit(pwd.getUnit());
			invDetl.setPwPropProductDate(pwd.getPropProductDate());
			invDetl.setPhasenoic(pwd.getPhaseNo());
			invDetl.setInvFciIc(pwd.getInvFci());
			invDetl.setPwic(pwd.getPwId());

			investId = investResponce.get(Unit_Id) + "I1";
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
			model.addAttribute("edit", "edit");
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
		} catch (Exception e) {
			logger.error(String.format("#### editPhaseWiseInvestData exception $$$ %s", e.getMessage()));
			e.printStackTrace();
		}

		investMongoDBDoc(invDetl, investId);
		try {
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute("phasewiselist", "phasewiselist");
		} catch (Exception e) {
			logger.error(String.format("^^^^ editPhaseWiseInvestData ^^^ %s", e.getMessage()));
		}
		return new ModelAndView("investment_details");

	}

	@PostMapping("/delInvInstaldCap")
	public ModelAndView delInvInstaldCap(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @RequestParam(value = "selectedRow", required = false) String index, Model model,
			HttpSession session) {
		try {
			iNVInstalledCapacitiesService.deleteById(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/investmentDetails");

	}

	@PostMapping(value = "/saveInvMom")
	public ModelAndView saveInvMom(@Validated @ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, Model model, HttpSession session) {
		// commonSaveInvestmentDetails(invDetl, result, model, session, multipartFile,
		// regiOrLicenseFileName);

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";

		InvMeansOfFinance invMeansOfFinance = new InvMeansOfFinance();
		invMeansOfFinance.setMofAmount(invDetl.getProposedInvestmentInProjectmom());

		invMeansOfFinance.setMofParameter(invDetl.getParticularmom());
		invMeansOfFinance.setMofInvId(investId);

		if (invDetl.getPwmom() != null && !invDetl.getPwmom().isEmpty()) {
			invMeansOfFinance.setMofId(invDetl.getPwmom());
		} else {
			invMeansOfFinance.setMofId(UUID.randomUUID().toString());
		}

		meansOfFinanceService.saveMeansOfFinance(invMeansOfFinance);
		InvestmentDetails inv = new InvestmentDetails();
		inv.setInvIndType(invDetl.getInvIndType());
		model.addAttribute(INVESTMENT_DETAILS, inv);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@PostMapping(value = "/editInvMom")
	public ModelAndView editInvMom(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl, BindingResult result,
			@RequestParam(value = "selectedRow", required = false) String index, Model model, HttpSession session) {
		InvMeansOfFinance pwd = null;
		String investId = "";
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			pwd = meansOfFinanceService.findById(index).get();

			invDetl.setParticularmom(pwd.getMofParameter());
			invDetl.setProposedInvestmentInProjectmom(pwd.getMofAmount());
			// invDetl.setPwPhaseNoOT(pwd.get);
			invDetl.setPwmom(pwd.getMofId());
			// invDetl.setInvFciIc(pwd.getInvFci());

			investId = investResponce.get(Unit_Id) + "I1";
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
			model.addAttribute("edit", "edit");
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
			model.addAttribute("momlist", meansOfFinanceService.getMeansOfFinanceList());
		} catch (Exception e) {
			logger.error(String.format("#### editPhaseWiseInvestData exception $$$ %s", e.getMessage()));
			e.printStackTrace();
		}

		investMongoDBDoc(invDetl, investId);
		try {
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute("phasewiselist", "phasewiselist");
		} catch (Exception e) {
			logger.error(String.format("^^^^ editPhaseWiseInvestData ^^^ %s", e.getMessage()));
		}
		return new ModelAndView("investment_details");

	}

	@PostMapping("/delInvMom")
	public ModelAndView delInvMom(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl, BindingResult result,
			@RequestParam(value = "selectedRow", required = false) String index, Model model, HttpSession session) {
		try {
			meansOfFinanceService.deleteById(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/investmentDetails");

	}

	@PostMapping(value = "/autosaveInvestmentDetails")
	public ModelAndView autosaveInvestmentDetails(
			@Validated @ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl, BindingResult result, Model model,
			HttpSession session, @RequestParam("invFilename") MultipartFile multipartFile,
			@RequestParam("regiOrLicenseFileName") MultipartFile regiOrLicenseFileName) {

		commonSaveInvestmentDetails(invDetl, result, model, session, multipartFile, regiOrLicenseFileName);

		return new ModelAndView("investment_details");
	}

	@PostMapping("/addPwInvestment")
	public ModelAndView addPhaseWiseInvestData(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, HttpSession session, Model model,
			@RequestParam("invFilename") MultipartFile multipartFile,
			@RequestParam("pwPropProductDate") String pwPropProductDate) {
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		try {

			PhaseWiseInvestmentDetails pwInvDetl = new PhaseWiseInvestmentDetails();
			pwInvDetl.setPwFci(invDetl.getPwFci());

			pwInvDetl.setPwPhaseNo(invDetl.getPwPhaseNo());
			// pwInvDetl.setPwCapacity(invDetl.getPwCapacity());
			// pwInvDetl.setPwProductName(invDetl.getPwProductName());
			// pwInvDetl.setPwUnit(invDetl.getPwUnit());
			// pwInvDetl.setPwPropProductDate(invDetl.getPwPropProductDate());
			// pwInvDetl.setPwCutoffProdAmt((invDetl.getPwCutoffProdAmt()));
			pwInvDetl.setPwApply(invDetl.getPwApply());
			pwInvDetl.setPwStatus("active");
			pwInvDetl.setPwCreatedBy("User");
			pwInvDetl.setPwModifiedDate(new Date());
			pwInvDetl.setInvLandCost(invDetl.getInvLandCost());
			pwInvDetl.setInvBuildingCost(invDetl.getInvBuildingCost());
			pwInvDetl.setInvPlantAndMachCost(invDetl.getInvPlantAndMachCost());
			pwInvDetl.setInvOtherCost(invDetl.getInvOtherCost());
			pwInvDetl.setInvFci(invDetl.getInvFci());

			pwInvDetl.setPwCapacity(invDetl.getPwCapacity());
			pwInvDetl.setPwProductName(invDetl.getPwProductName());
			pwInvDetl.setPwUnit(invDetl.getPwUnit());
			pwInvDetl.setPwFci(invDetl.getPwFci());
			// pwInvDetl.setPwPropProductDate(invDetl.getPwPropProductDate());
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = formatter1.parse(pwPropProductDate);

			java.sql.Timestamp timestamp = new java.sql.Timestamp(date1.getTime());

			pwInvDetl.setPwPropProductDate(timestamp);

			invDetl.setInvLandCost(0l);

			/*
			 * invDetl.setPwCapacity(null); invDetl.setPwProductName(null);
			 * invDetl.setPwUnit(null); invDetl.setPwFci(null);
			 * invDetl.setPwPropProductDate(null); invDetl.setInvLandCost(0l);
			 */

			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			String investId = investResponce.get(Unit_Id) + "I1";
			String pwId = investResponce.get(Unit_Id) + "PW";
			String appId = investResponce.get(Unit_Id) + "A1";
			/*
			 * for (Map.Entry<String, String> entry : investResponce.entrySet()) { if
			 * (entry.getKey().equalsIgnoreCase(Unit_Id)) { investId = entry.getValue() +
			 * "I1"; } if (entry.getKey().equalsIgnoreCase(Unit_Id)) { appId =
			 * entry.getValue() + "A1"; } if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
			 * pwId = entry.getValue() + "PW"; } }
			 */

			PhaseWiseInvestmentDetails pw = new PhaseWiseInvestmentDetails();
			pw.setPwId(pwId + invDetl.getPwPhaseNo());

			try {
				pwInvestDs.deletePwInvDetails(pw);
			} catch (Exception e) {

			}

			List<PhaseWiseInvestmentDetails> pwInvList1 = pwInvestDs.getPwInvDetailListById(investId);

			pwInvDetl.setPwApcId(appId);
			pwInvDetl.setPwId(pwId + invDetl.getPwPhaseNo());
			pwInvDetl.setPwInvId(investId);

			/*
			 * invDetl.setInvTotalProjCost(0l); invDetl.setApplId(appId);
			 * invDetl.setInvStatus("active"); invDetl.setInvCreatedBy("User");
			 * invDetl.setInvId(investId); invDetl.setInvModifiyDate(new Date());
			 * 
			 * SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
			 * 
			 * date1 = formatter1.parse(invCommenceDate); Date date2 =
			 * formatter1.parse(propCommProdDate); java.sql.Timestamp timestamp = new
			 * java.sql.Timestamp(date1.getTime()); java.sql.Timestamp timestamp2 = new
			 * java.sql.Timestamp(date2.getTime());
			 * 
			 * invDetl.setInvCommenceDate(timestamp);
			 * invDetl.setPropCommProdDate(timestamp2); //invDetl.setPropCommProdDate(new
			 * Date()); //invDetl.setInvCommenceDate(new Date()); // save InvestmentDetails
			 * record in table investDs.saveInvestmentDetails(invDetl);
			 */

			pwInvestDs.savePwInvDetails(pwInvDetl);
			pwInvList1.add(pwInvDetl);
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList1);

			InvestmentDetails inv = new InvestmentDetails();
			inv.setInvIndType(invDetl.getInvIndType());
			// model.addAttribute(INVESTMENT_DETAILS, inv);
			model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
			investMongoDBDoc(invDetl, investResponce.get(Unit_Id) + "I1");
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
		} catch (Exception e) {
			logger.error(String.format("#### addPhaseWiseInvestData exception $$$ %s", e.getMessage()));
		}

		// model.addAttribute(INVESTMENT_DETAILS, invDetl);
		model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);

		model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
		model.addAttribute("phasewiselist", "phasewiselist");
		return new ModelAndView("redirect:/investmentDetails");
	}

	@GetMapping("/addPwInvestment")
	public ModelAndView refreshAfterAddPhaseWise(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, HttpSession session, Model model) {

		fetchRecordsFromDB(model, session);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@GetMapping("/deletePwRow")
	public ModelAndView refreshAfterDeletePhaseWise(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, HttpSession session, Model model) {

		fetchRecordsFromDB(model, session);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@GetMapping("/editPwRow")
	public ModelAndView refreshAfterEditPhaseWise(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, HttpSession session, Model model) {

		fetchRecordsFromDB(model, session);
		return new ModelAndView("redirect:/investmentDetails");
	}

	@PostMapping("/deletePwRow")
	public ModelAndView deletePhaseWiseInvestData(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @ModelAttribute(PW_INVESTMENT_DETAILS) PhaseWiseInvestmentDetails pwInvDetl,
			Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session,
			@RequestParam("invFilename") MultipartFile multipartFile) {
		PhaseWiseInvestmentDetails pwd = null;
		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			String pwId = "";
			String investId = "";
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					investId = entry.getValue() + "I1";
				}

				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					pwId = entry.getValue() + "PW";
				}
			}

			PhaseWiseInvestmentDetails pw = new PhaseWiseInvestmentDetails();
			pw.setPwId(pwId + index);
			pwInvestDs.deletePwInvDetails(pw);

			InvestmentDetails invD = investDs.getInvestmentDetailsById(investId);
			model.addAttribute(INVESTMENT_DETAILS, invD);

			List<PhaseWiseInvestmentDetails> pwInvList1 = pwInvestDs.getPwInvDetailListById(investId);

			// if (!pwInvList.isEmpty() && pwInvList.get(index) != null) {
			// PhaseWiseInvestmentDetails pwInvDetl2 = pwInvList.get(index);
			// try {
			// pwInvestDs.deletePwInvDetailsById(pwInvDetl2.getPwId());
			// } catch (Exception e) {
			// }
			// pwInvList.remove(index);
			// model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			// model.addAttribute(INVESTMENT_DETAILS, invDetl);
			// }
			// model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList1);
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
			// model.addAttribute(INVESTMENT_DETAILS, invDetl);
		} catch (Exception e) {
			logger.error(String.format("#### deletePhaseWiseInvestData exception $$$ %s", e.getMessage()));
		}

		String investId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : investResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				investId = entry.getValue() + "I1";
			}
		}

		investMongoDBDoc(invDetl, investId);

		model.addAttribute("phasewiselist", "phasewiselist");
		return new ModelAndView("investment_details");

	}

	@PostMapping("/editPwRow")
	public ModelAndView editPhaseWiseInvestData(@ModelAttribute(INVESTMENT_DETAILS) InvestmentDetails invDetl,
			BindingResult result, @RequestParam("invFilename") MultipartFile multipartFile,
			@RequestParam(value = "selectedRow", required = false) int index, Model model, HttpSession session) {

		// PhaseWiseInvestmentDetails pwinv = null;
		PhaseWiseInvestmentDetails pwd = null;

		try {
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			String pwId = "";
			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					pwId = entry.getValue() + "PW";
				}
			}

			pwd = pwInvestDs.getPwInvDetailById(pwId + index);

			// if (!pwInvList.isEmpty() && pwInvList.get(index) != null) {
			// pwinv = pwInvList.get(index);

			invDetl.setPwCapacity(pwd.getPwCapacity());
			invDetl.setPwProductName(pwd.getPwProductName());
			invDetl.setPwUnit(pwd.getPwUnit());
			invDetl.setPwFci(pwd.getPwFci());
			invDetl.setPwPropProductDate(pwd.getPwPropProductDate());
			invDetl.setInvLandCost(pwd.getInvLandCost());
			invDetl.setInvBuildingCost(pwd.getInvBuildingCost());
			invDetl.setInvPlantAndMachCost(pwd.getInvPlantAndMachCost());
			invDetl.setInvOtherCost(pwd.getInvOtherCost());
			invDetl.setInvFci(pwd.getInvFci());
			invDetl.setPwPhaseNo(pwd.getPwPhaseNo());
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phases", pwInvestDs.getPhasesById(pwId));
			model.addAttribute("edit", "edit");
			pwIndex = index;
			isPwRowRecord = true;
			model.addAttribute("invIndType", investResponce.get("Unit_Category"));
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
		} catch (Exception e) {
			logger.error(String.format("#### editPhaseWiseInvestData exception $$$ %s", e.getMessage()));
			e.printStackTrace();
		}

		String investId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : investResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				investId = entry.getValue() + "I1";
			}
		}

		investMongoDBDoc(invDetl, investId);
		try {
			model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
			model.addAttribute(INVESTMENT_DETAILS, invDetl);
			model.addAttribute(PW_INVESTMENT_DETAILS, pwd);
			model.addAttribute("phasewiselist", "phasewiselist");

		} catch (Exception e) {
			logger.error(String.format("^^^^ editPhaseWiseInvestData ^^^ %s", e.getMessage()));
		}
		return new ModelAndView("investment_details");

	}

	public void updatePwRow(int index, PhaseWiseInvestmentDetails pwinv) {
		pwInvList.add(index, pwinv);
	}

	/**
	 * This method is responsible to fetch InvestmentDetails documents from MongoDB
	 * collection.
	 */
	public void investMongoDBDoc(InvestmentDetails invdtlFromDb, String investId) {
		try {
			List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
			for (int i = 0; i < invDocList.size(); i++) {
				InvestmentDocument investDoc = invDocList.get(i);

				if (investDoc != null) {

					if (i == 0) {
						byte[] encodeBase64 = Base64.encodeBase64(investDoc.getData());
						String base64Encoded = null;
						base64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						invdtlFromDb.setInvbase64File(base64Encoded);
						invdtlFromDb.setInvFilename(investDoc.getFileName());
					}
					if (i == 1) {
						byte[] regiOrLicenseBase64 = Base64.encodeBase64(investDoc.getData());
						String regiOrLicensebase64Encoded = null;
						regiOrLicensebase64Encoded = new String(regiOrLicenseBase64, StandardCharsets.UTF_8);
						invdtlFromDb.setRegiOrLicenseFileName(investDoc.getFileName());
						invdtlFromDb.setInvregorlicbase64File(regiOrLicensebase64Encoded);
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ investMongoDBDoc exception ^^^ %s", e.getMessage()));
		}
	}

	/**
	 * This method is responsible to fetch records from table.
	 */
	private void fetchRecordsFromDB(Model model, HttpSession session) {
		Object niveshResponse = null;
		String projId = null;
		String region = null;
		if (session != null) {
			niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		}

		try {
			String investId = null;
			Map<String, String> investResponce = null;
			if (niveshResponse != null) {
				investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			}

			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					investId = entry.getValue() + "I1";
					projId = entry.getValue() + "P1";
				}
			}

			String appId = investResponce.get("appID");
			region = additionalInterest.getregion(appId);
			InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
			if (invdtlFromDb != null) {
				if ((!invdtlFromDb.getApplId().isEmpty() && invdtlFromDb.getApplId() != null)) {
					pwInvList = pwInvestDs.getPwInvDetailListById(investId);

					// retrieve documents from MongoDB
					investMongoDBDoc(invdtlFromDb, investId);

					model.addAttribute(PW_INVESTMENT_LIST, pwInvList);
					model.addAttribute("invType", invdtlFromDb.getInvIndType());
					// model.addAttribute("regisType", invdtlFromDb.getRegiOrLicense());

					InvestmentDetails inv = new InvestmentDetails();
					inv.setInvId(invdtlFromDb.getInvId());
					// inv.setInvIndType(invdtlFromDb.getInvIndType());
					inv.setRegiOrLicense(invdtlFromDb.getRegiOrLicense());
					inv.setRegiOrLicenseFileName(invdtlFromDb.getRegiOrLicenseFileName());
					inv.setInvFilename(invdtlFromDb.getInvFilename());
					// model.addAttribute(INVESTMENT_DETAILS, inv);

					// model.addAttribute(INVESTMENT_DETAILS, new InvestmentDetails());
					model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
					model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
					model.addAttribute("investmentId", invdtlFromDb.getInvId());
					model.addAttribute("invIndType", investResponce.get("Unit_Category"));
					model.addAttribute("phases", pwInvestDs.getPhasesById(investId));
					model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
					model.addAttribute("momlist", meansOfFinanceService.getMeansOfFinanceList());
					model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));

					String formattedDate;
					DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					formattedDate = formatter.format(invdtlFromDb.getInvCommenceDate());
					String formattedDate2 = formatter.format(invdtlFromDb.getPropCommProdDate());
					model.addAttribute("invDate", formattedDate);
					model.addAttribute("invDate2", formattedDate2);
					invdtlFromDb.setInvLandCost(null);
					invdtlFromDb.setInvBuildingCost(null);
					invdtlFromDb.setInvPlantAndMachCost(null);
					invdtlFromDb.setInvOtherCost(null);
					invdtlFromDb.setInvFci(null);

					model.addAttribute(INVESTMENT_DETAILS, invdtlFromDb);
					/*
					 * ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService
					 * .getProposedEmploymentDetailsByappId(invdtlFromDb.getApplId()); if
					 * (proposedEmploymentDetails != null) { model.addAttribute("PEflag",
					 * proposedEmploymentDetails.getId()); }
					 */

				}
			} else {
				pwInvList.clear();
				model.addAttribute(INVESTMENT_DETAILS, new InvestmentDetails());
				model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
				InvestmentDetails investmentDetails = new InvestmentDetails();
				model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
				model.addAttribute("invIndType", investResponce.get("Unit_Category"));
				model.addAttribute(INVESTMENT_DETAILS, investmentDetails);
			}

			ProjectDetails projectDetail = projectService.getProjDetById(projId);
			String district = projectDetail.getDistrictName();
			model.addAttribute("eligiblereg", region);
			model.addAttribute("eligibledis", district);
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);

		} catch (Exception e) {
			logger.error(String.format("^^^^ fetchRecordsFromDB exception ^^^ %s", e.getMessage()));
		}
	}

	@GetMapping(value = "/investmentDetails")
	public ModelAndView investmentDetailsForm(Model model, HttpSession session) {
		logger.debug("investmentDetailsForm called");

		fetchRecordsFromDB(model, session);
		return new ModelAndView("investment_details");
	}

	@PostMapping(value = "/investmentDetails")
	public ModelAndView investmentDetails(Model model, HttpSession session) {
		logger.debug("investmentDetailsForm called");

		fetchRecordsFromDB(model, session);
		return new ModelAndView("investment_details");
	}

	/**
	 * Method for handling file download request from client
	 */
	@GetMapping(value = "/downloadmofFile/{type}")
	public void downloadTemplate(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
		File file = null;
		try {
			if (type.equalsIgnoreCase("doc")) {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				file = new File(classloader.getResource(INTERNAL_FILE).getFile());
			}

			if (!file.exists()) {
				String errorMessage = "The file you are looking for does not exist";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			}

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/msword";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			logger.error(String.format("#### downloadTemplate Investment exception $$$ %s", e.getMessage()));
		}
	}

	// private void soapRestData(Model model, InvestmentDetails investmentDetails) {
	// try {
	// Map<String, String> investResponce = new
	// HashMap<>(soapdetails.soapwebservice());
	// for (Map.Entry<String, String> entry : investResponce.entrySet()) {
	// if (entry.getKey().equalsIgnoreCase("Unit_Category")) {
	// model.addAttribute("invIndType", entry.getValue());
	// }
	// }
	// model.addAttribute("invIndType", investResponce.get("Unit_Category"));
	// model.addAttribute(INVESTMENT_DETAILS, investmentDetails);
	// } catch (Exception e) {
	// logger.error(String.format("#### soapRestData Investment exception $$$ %s",
	// e.getMessage()));
	// }
	// }

	/*-----------------------------tabs-------------------------------------*/

	@GetMapping(value = "/getIdByTabs33")
	public ModelAndView getIdByTabs33(Model model, HttpSession session,
			@RequestParam(value = "authoTab") String authoTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);

		if (applicantDetail != null && authoTab.equalsIgnoreCase("authoTab")) {
			return new ModelAndView("redirect:/editApplicantForm");
		}
		return null;

	}

	@GetMapping(value = "/getIdByTabs34")
	public ModelAndView getIdByTabs34(Model model, HttpSession session,
			@RequestParam(value = "busiTab") String busiTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		if (businessEntityDetails != null && busiTab.equalsIgnoreCase("busiTab")) {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}
		return null;

	}

	@GetMapping(value = "/getIdByTabs35")
	public ModelAndView getIdByTabs35(Model model, HttpSession session,
			@RequestParam(value = "projTab") String projTab) {

		String projId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				projId = entry.getValue() + "P1";
			}
		}
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		if (ProjectDetail != null && projTab.equalsIgnoreCase("projTab")) {
			return new ModelAndView("redirect:/projectDetails");
		}
		return null;

	}

	@GetMapping(value = "/getIdByTabs36")
	public ModelAndView getIdByTabs25(Model model, HttpSession session,
			@RequestParam(value = "propoTab") String propoTab) {

		String propoId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				propoId = entry.getValue() + "PE1";
			}

		}

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propoId);
		if (proposedEmploymentDetail != null && propoTab.equalsIgnoreCase("propoTab")) {
			return new ModelAndView("redirect:/skilUnskEmplDet");
		} else {
			return new ModelAndView("redirect:/investmentDetails");
		}

	}

	/**
	 * This method is responsible to fetch unit list from Unit_Master table and put
	 * into HashMap.
	 */
	@ModelAttribute("unitListMap")
	public Map<String, String> getUnitList() {
		Map<String, String> unitListMap = new LinkedHashMap<>();
		try {
			List<Unit> unitList = unitRepository.findAll();
			List<Unit> sortedList = unitList.stream().sorted(Comparator.comparing(Unit::getUnitName))
					.collect(Collectors.toList());

			for (Unit unit : sortedList) {
				unitListMap.put(unit.getUnitName(), unit.getUnitName());
			}

		} catch (Exception e) {
			logger.error(String.format("#### getUnitList Investment exception $$$ %s", e.getMessage()));
		}
		return unitListMap;

	}

	@RequestMapping(value = "/addPhaseWiseInvestment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PhaseWiseInvestmentDetails> addPhaseWiseInvestment(
			@RequestBody PhaseWiseInvestmentDetails invDetl, BindingResult result, Model model, HttpSession session) {

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		String pwId = investResponce.get(Unit_Id) + "PW";
		String appId = investResponce.get(Unit_Id) + "A1";
		
		session.setAttribute("investId", investId);

		List<PhaseWiseInvestmentDetails> pwInvList1 = new ArrayList<PhaseWiseInvestmentDetails>();
		try {

			PhaseWiseInvestmentDetails pwInvDetl = new PhaseWiseInvestmentDetails();

			pwInvDetl.setPwPhaseNo(invDetl.getPwPhaseNo());
			pwInvDetl.setInvLandCost(invDetl.getInvLandCost());
			pwInvDetl.setInvBuildingCost(invDetl.getInvBuildingCost());
			pwInvDetl.setInvPlantAndMachCost(invDetl.getInvPlantAndMachCost());
			pwInvDetl.setInvOtherCost(invDetl.getInvOtherCost());
			pwInvDetl.setInvFci(invDetl.getInvFci());
		
			pwInvDetl.setPwProductName(invDetl.getPwProductName());
			pwInvDetl.setPwCapacity(invDetl.getPwCapacity());
			pwInvDetl.setPwUnit(invDetl.getPwUnit());

			pwInvDetl.setPwApply(invDetl.getPwApply());
			//pwInvDetl.setPwPropProductDate(invDetl.getPwPropProductDate());
			/*
			 * SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy"); Date date1
			 * = formatter1.parse(invDetl.getDateInString());
			 * 
			 * java.sql.Timestamp timestamp = new java.sql.Timestamp(date1.getTime());
			 */
			
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = formatter1.parse(invDetl.getDateInString());
			pwInvDetl.setPwPropProductDate(formatter1.parse(formatter1.format(date1)));
			 

			pwInvDetl.setPwStatus("active");
			pwInvDetl.setPwCreatedBy("User");
			pwInvDetl.setPwModifiedDate(new Date());
			
			if (invDetl.getPwId() == null || invDetl.getPwId().isEmpty()
					|| invDetl.getPwId() == "") {

				Random random = new Random();
				// Generates random integers 0 to 49
				int x = random.nextInt(50);
				pwInvDetl.setPwId(pwId+ x);

			} else {
				pwInvDetl.setPwId(invDetl.getPwId());
				
			}

			/*
			 * PhaseWiseInvestmentDetails pw = new PhaseWiseInvestmentDetails();
			 * pw.setPwId(pwId + invDetl.getPwPhaseNo());
			 */
			pwInvDetl.setPwApcId(appId);
			pwInvDetl.setPwId(pwId + invDetl.getPwPhaseNo());
			pwInvDetl.setPwInvId(investId);

			pwInvestDs.savePwInvDetails(pwInvDetl);
			pwInvList1 = pwInvestDs.getPwInvDetailListById(investId);
			
		} catch (Exception e) {
			logger.error(String.format("#### addPhaseWiseInvestData exception $$$ %s", e.getMessage()));
		}


		return pwInvList1;
	}

	@RequestMapping(value="/getAllPWDtlsOnLoad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PhaseWiseInvestmentDetails> getAllPWDtlsOnLoad(HttpSession session)
	{

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		String pwId = investResponce.get(Unit_Id) + "PW";
		String appId = investResponce.get(Unit_Id) + "A1";
		List<PhaseWiseInvestmentDetails> pwInvList1 = new ArrayList<PhaseWiseInvestmentDetails>();
		
		
		pwInvList1 = pwInvestDs.findByPwApcId(appId);
		pwInvList1.sort(Comparator.comparing(PhaseWiseInvestmentDetails::getPwId));
		Comparator<PhaseWiseInvestmentDetails> pwlist = (list1, list2) -> list1.getPwId().compareTo(
				list2.getPwId());
		pwInvList1.sort(pwlist);
		return pwInvList1;
		
	}
	@RequestMapping(value="/deletePWDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PhaseWiseInvestmentDetails> deletePWDetails(@RequestBody PhaseWiseInvestmentDetails pwDtls, HttpSession session)
	{
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		
		String appId = investResponce.get(Unit_Id) + "A1";
		List<PhaseWiseInvestmentDetails> pwInvList1 = new ArrayList<PhaseWiseInvestmentDetails>();
		
		pwInvestDs.deleteBypwId(pwDtls.getPwId());
		pwInvList1 = pwInvestDs.findByPwApcId(appId);
		pwInvList1.sort(Comparator.comparing(PhaseWiseInvestmentDetails::getPwId));
		return pwInvList1;
		
	}
	
	
	
	@RequestMapping(value = "/addOtherIfAnyInvestmentDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<INVOthers> saveInvOthers(
			@RequestBody INVOthers invOtherIfAny, BindingResult result, Model model, HttpSession session)
{
		
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String appId = investResponce.get("appID");
		String investId = investResponce.get(Unit_Id) + "I1";
		model.addAttribute("invIndType", investResponce.get("Unit_Category"));

		INVOthers invOthers = new INVOthers();
		invOthers.setApcid(appId);
		invOthers.setInvid(investId);

		if (invOtherIfAny.getMofId() != null && !invOtherIfAny.getMofId().isEmpty()) {
			invOthers.setMofId(invOtherIfAny.getMofId());
		} else {
			invOthers.setMofId(UUID.randomUUID().toString());
		}

		invOthers.setParticulars(invOtherIfAny.getParticulars());
		invOthers.setPhaseNumber(invOtherIfAny.getPhaseNumber());
		invOthers.setProposedInvestmentInProject(invOtherIfAny.getProposedInvestmentInProject());
		invOthersService.save(invOthers);
		
		
		List<INVOthers> invOthers2=invOthersService.getAllByINV_ID(investId);
		
		return invOthers2;
	}
	
	@RequestMapping(value="/getOIAListOnLoad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<INVOthers> getOIAListOnLoad(HttpSession session)
	{Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

	Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
	String investId = investResponce.get(Unit_Id) + "I1";

	List<INVOthers> oiaList1 = new ArrayList<INVOthers>();
	
	oiaList1 = invOthersService.getAllByINV_ID(investId);
		//proprietorDtlList.sort(Comparator.comparing(ProprietorDetails::getPropId));
		//Comparator<INVOthers> propId = (list1, list2) -> list1.getMofId().compareTo(
				//list2.getMofId());
		//oiaList1.sort(mofId);
		return oiaList1;
		
	}
	
	@RequestMapping(value="/deleteOIAById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<INVOthers> deleteOIAById(@RequestBody INVOthers invOthers, HttpSession session)
	{
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		List<INVOthers> oiaList1 = new ArrayList<INVOthers>();
		
		invOthersService.deleteById(invOthers.getMofId());
		oiaList1 = invOthersService.getAllByINV_ID(investId);
		oiaList1.sort(Comparator.comparing(INVOthers::getMofId));
		return oiaList1;
		
	}
	
	@RequestMapping(value = "/addMOFInvestmentDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<InvMeansOfFinance> saveInvMOF(@RequestBody InvMeansOfFinance invDtlsMOF,Model model, HttpSession session) 
	{
		List<InvMeansOfFinance> mofList=new ArrayList<InvMeansOfFinance>();
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		String appId = investResponce.get(Unit_Id) + "A1";
		
		InvMeansOfFinance invMeansOfFinance = new InvMeansOfFinance();
		invMeansOfFinance.setMofAmount(invDtlsMOF.getMofAmount());

		invMeansOfFinance.setMofParameter(invDtlsMOF.getMofParameter());
		invMeansOfFinance.setMofInvId(investId);
		invMeansOfFinance.setPwPhaseNoMOM(invDtlsMOF.getPwPhaseNoMOM());
		invMeansOfFinance.setPwApcId(appId);
		
		if (invDtlsMOF.getMofId() != null && !invDtlsMOF.getMofId().isEmpty()) {
			invMeansOfFinance.setMofId(invDtlsMOF.getMofId());
		} else {
			invMeansOfFinance.setMofId(UUID.randomUUID().toString());
		}

		meansOfFinanceService.saveMeansOfFinance(invMeansOfFinance);
		mofList=meansOfFinanceService.getAllByINV_ID(investId);
		
		return mofList;
	}

	@RequestMapping(value="/getMOFDtlsListOnLoad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<InvMeansOfFinance> getMOFListOnLoad(HttpSession session)
	{Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

	Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
	String investId = investResponce.get(Unit_Id) + "I1";

	List<InvMeansOfFinance> mofList1 = new ArrayList<InvMeansOfFinance>();
	
	mofList1 = meansOfFinanceService.getAllByINV_ID(investId);
		//proprietorDtlList.sort(Comparator.comparing(ProprietorDetails::getPropId));
		//Comparator<INVOthers> propId = (list1, list2) -> list1.getMofId().compareTo(
				//list2.getMofId());
		//oiaList1.sort(mofId);
		return mofList1;
		
	}
	
	@RequestMapping(value="/deleteMOFInvestmentDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<InvMeansOfFinance> deleteMOFById(@RequestBody InvMeansOfFinance invMOF, HttpSession session)
	{
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String investId = investResponce.get(Unit_Id) + "I1";
		List<InvMeansOfFinance> mofList1 = new ArrayList<InvMeansOfFinance>();
		
		meansOfFinanceService.deleteById(invMOF.getMofId());
		mofList1 = meansOfFinanceService.getAllByINV_ID(investId);
		//oiaList1.sort(Comparator.comparing(INVOthers::getMofId));
		return mofList1;
		
	}
	
	@RequestMapping(value="/getPWPhaseNoList", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PhaseWiseInvestmentDetails> getPhaseNoList(HttpSession session)
	{
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		//List<String> phList=new ArrayList<String>();
		List<PhaseWiseInvestmentDetails>phaseWiseInvestmentDetails=new ArrayList<PhaseWiseInvestmentDetails>();
	Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
	String investId = investResponce.get(Unit_Id) + "I1";
	try {
	 //phList=pwInvestDs.getPhasesById(investId);
		phaseWiseInvestmentDetails=pwInvestDs.getPhaseNoObj(investId);
	 
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return phaseWiseInvestmentDetails;
		
	}
	
}
