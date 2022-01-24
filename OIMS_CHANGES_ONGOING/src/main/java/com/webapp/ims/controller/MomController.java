package com.webapp.ims.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.ims.dis.model.CirculateToDepartmentDis;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.MinutesOfMeetingDis;
import com.webapp.ims.dis.model.MomCirculateNoteDis;
import com.webapp.ims.dis.model.MomUploadDocumentsDis;
import com.webapp.ims.dis.repository.CirculateToDepartmentDisRepository;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.DisMinutesOfMeetingRepository;
import com.webapp.ims.dis.repository.DisMomUploadDocumentsRepository;
import com.webapp.ims.dis.service.CirculateToDepartmentServiceDis;
import com.webapp.ims.dis.service.DisMinutesOfMeetingService;
import com.webapp.ims.dis.service.DisMomUploadDocumentsService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.CirculateToDepartment;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.MomCirculateNote;
import com.webapp.ims.model.MomUploadDocuments;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.CirculateToDepartmentRepository;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.MomUploadDocumentsRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.CirculateToDepartmentService;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MinutesOfMeetingService;
import com.webapp.ims.service.MomUploadDocumentsService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class MomController {

	private final Logger logger = LoggerFactory.getLogger(MomController.class);

	@Autowired
	private TblUsersService loginService;
	@Autowired
	private LoginRepository loginRepository;
	private List<Department> deptList = new LinkedList<>();
	private String[] deptId = new String[50];
	@Autowired
	TblUsersService loginservice;
	@Autowired
	SendingEmail sendingEmail;
	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;
	@Autowired
	DISPrepareAgendaNotesRepository prepareAgendaNotesRepository;
	@Autowired
	private MinutesOfMeetingService minutesOfMeetingService;
	@Autowired
	private MinutesOfMeetingRepository minutesOfMeetingRepository;

	@Autowired
	DisMinutesOfMeetingService disMinutesOfMeetingService;
	

	private AtomicInteger atomicInteger = new AtomicInteger();
	private AtomicInteger atomicInteger1 = new AtomicInteger();

	@Autowired
	private MomUploadDocumentsService momUploadDocumentsService;

	@Autowired
	DisMomUploadDocumentsService dismomUploadDocumentsService;

	@Autowired
	private MomUploadDocumentsRepository momUploadDocumentsRepository;

	@Autowired
	private DisMomUploadDocumentsRepository dismomUploadDocumentsRepository;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaRepo;

	@Autowired
	DISPrepareAgendaNotesRepository disprepareAgendaRepos;

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	CirculateToDepartmentDisRepository circulateToDepartmentDisRepository;

	@Autowired
	CirculateToDepartmentService circulateToDepartmentService;
	@Autowired
	CirculateToDepartmentRepository circulateToDepartmentRepository;
	
	@Autowired
	DisMinutesOfMeetingRepository disMinutesOfMeetingRepository;
	
	@Autowired
	CirculateToDepartmentServiceDis circulateToDepartmentServiceDis;

	@GetMapping(value = "/momgo")
	public ModelAndView list(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MinutesOfMeeting> minutesOfMeetingEmpowerCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingSactionCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingGosCommitList = new ArrayList<MinutesOfMeeting>();

			List<PrepareAgendaNotes> listpreAgendaNotesMega = prepareAgendaRepo.findAllPrepareAgendaNotebyStatus();
			List<PrepareAgendaNotes> listpreAgendaNotesLarge = prepareAgendaRepo.findLargePrepareAgendaNotebyStatus();

			List<MinutesOfMeeting> minutesOfMeetingList = minutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			if (minutesOfMeetingList.size() > 0) {
				for (MinutesOfMeeting list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && !(list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs"))) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
			model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);
			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}

		model.addAttribute("department", new Department());
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		// model.addAttribute("concernDepartment", new Department());
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("minutesOfMeetings");
	}

	// DIS Circulate MOM

	@PostMapping("/momconcerncirculateem")
	public ModelAndView momconcerncirculate(@Validated @ModelAttribute("department") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		// String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/*
			 * System.out.println(str);
			 */
			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}

			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment = new CirculateToDepartment();
				String mom = "MOM Report";
				
				List<CirculateToDepartment> cirList = circulateToDepartmentRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setCirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("MOM Report");
						circulateToDepartmentService.save(circulateToDepartment);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgo");

	}

	@PostMapping("/momconcerncirculateemDis")
	public ModelAndView momconcerncirculateDis(@Validated @ModelAttribute("departmentDis") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {
		// String applId = (String) session.getAttribute("appId");
				List<String> newlist = new ArrayList<>();
				// String applId = (String) session.getAttribute("appId");
				// String mail = null;
				try {
					// String str=department.getNewDeptName().toString();
					// String[] strarr = str.split(",");
					/*
					 * for(String mail1:strarr) { newlist.add(mail1); }
					 */
					String str = department.getNewDeptName().toString();
					String deptName = "";

					List<String> aalist = new ArrayList<>();

					/* System.out.println(str); */

					String[] strarr = str.split(",");
					for (String a : strarr) {
						if (a.contains("@")) {
							deptName += a;

							newlist.add(a);
						} else {
							deptName += a;
							aalist.add(deptName);
							deptName = "";
						}

						if (deptName != "") {
							deptName += ",";
						}

					}

					for (String aa : aalist) {
						String data[] = aa.split(",");
						CirculateToDepartmentDis circulateToDepartment = new CirculateToDepartmentDis();
						String mom = "MOM Report";
						
						List<CirculateToDepartmentDis> cirList = circulateToDepartmentDisRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

						if (data[0] != null) {
							circulateToDepartment.setDeptEmail(data[0]);
						}
						if (data[1] != null) {
							circulateToDepartment.setDeptName(data[1]);
							if (cirList.size()==0) {
								circulateToDepartment.setDiscirculateId("CD" + atomicInteger.getAndIncrement());
								circulateToDepartment.setAppId(concernDept.getConDeptApplId());
								circulateToDepartment.setNoteReportStatus("MOM Report");
								circulateToDepartmentServiceDis.save(circulateToDepartment);
							}

						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				List<TblUsers> loginList = new ArrayList<>();

				try {
					String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
					newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

					loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
							x.getUserName()));

					// save into login

					// End
					model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
					model.addAttribute("viewInvestDetails", new InvestmentDetails());
					model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
					model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
					model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

				}

				catch (Exception e) {
					logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
					e.printStackTrace();
				}
				return new ModelAndView("redirect:/momgo");

			}

	// DIS Circulate Sannction

	@PostMapping("/momconcerncirculatest")
	public ModelAndView momconcerncirculatesan(@Validated @ModelAttribute("department") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/* System.out.println(str); */

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment = new CirculateToDepartment();
				String mom = "MOMs Report";
				
				List<CirculateToDepartment> cirList = circulateToDepartmentRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setCirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("MOMs Report");
						circulateToDepartmentService.save(circulateToDepartment);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgo");

	}

	@PostMapping("/momconcerncirculateSanDis")
	public ModelAndView momconcerncirculateSanctionDis(@Validated @ModelAttribute("departmentDis") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		// String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/* System.out.println(str); */

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}

			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartmentDis circulateToDepartment = new CirculateToDepartmentDis();
				String mom = "MOMs Report";
				
				List<CirculateToDepartmentDis> cirList = circulateToDepartmentDisRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setDiscirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("MOMs Report");
						circulateToDepartmentServiceDis.save(circulateToDepartment);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgoDis");

	}

	// DIS Circulate Go

	@PostMapping("/momconcerncirculatego")
	public ModelAndView momconcerncirculatego(@Validated @ModelAttribute("department") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		// String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/* System.out.println(str); */

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment = new CirculateToDepartment();
				String mom = "GO Report";
				
				List<CirculateToDepartment> cirList = circulateToDepartmentRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setCirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("GO Report");
						circulateToDepartmentService.save(circulateToDepartment);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgo");

	}

	@PostMapping("/momconcerncirculateGosDis")
	public ModelAndView momconcerncirculateGos(@Validated @ModelAttribute("departmentDis") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		// String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/* System.out.println(str); */

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartmentDis circulateToDepartment = new CirculateToDepartmentDis();
				String mom = "GOs Report";
				
				List<CirculateToDepartmentDis> cirList = circulateToDepartmentDisRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setDiscirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("GOs Report");
						circulateToDepartmentServiceDis.save(circulateToDepartment);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgoDis");

	}
	
	@PostMapping("/momconcerncirculateCabinet")
	public ModelAndView momconcerncirculateCabinet(@Validated @ModelAttribute("departmentDis") Department department, @Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept,
			BindingResult result, HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		// String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			System.out.println(str);

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartmentDis circulateToDepartment = new CirculateToDepartmentDis();
				String mom = "Cabinet Report";
				
				List<CirculateToDepartmentDis> cirList = circulateToDepartmentDisRepository.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size()==0) {
						circulateToDepartment.setDiscirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("Cabinet Report");
						circulateToDepartmentServiceDis.save(circulateToDepartment);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TblUsers> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId() + ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created TblUsers credential for Department", "your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/momgoDis");

	}
	
	
	// DIS Minuts of Meeting Get
	@GetMapping(value = "/momgoDis")
	public ModelAndView momgoDislist(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING DIS......");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MinutesOfMeetingDis> minutesOfMeetingEmpowerCommitList = new ArrayList<MinutesOfMeetingDis>();
			List<MinutesOfMeetingDis> minutesOfMeetingSactionCommitList = new ArrayList<MinutesOfMeetingDis>();
			List<MinutesOfMeetingDis> minutesOfMeetingGosCommitList = new ArrayList<MinutesOfMeetingDis>();
			List<MinutesOfMeetingDis> minutesOfMeetingCabinetCommitList = new ArrayList<MinutesOfMeetingDis>();

			List<DISPrepareAgendaNotes> listpreAgendaNotesMega = disprepareAgendaRepos.findAllPrepareAgendaNotebyStatus();
			List<DISPrepareAgendaNotes> listpreAgendaNotesLarge = disprepareAgendaRepos.findLargePrepareAgendaNotebyStatus();

			List<MinutesOfMeetingDis> minutesOfMeetingList = disMinutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			if (minutesOfMeetingList.size() > 0) {
				for (MinutesOfMeetingDis list : minutesOfMeetingList) {

					if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("CabinetNote")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadCabinetFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingCabinetCommitList.add(list);
					}

					else if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && !(list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs"))) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingEmpowerCommitList.add(list);

					}

					else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					}

				}
			}

			model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
			model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);
			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);
			model.addAttribute("minutesOfMeetingCabinetCommitList", minutesOfMeetingCabinetCommitList);
		}
		model.addAttribute("departmentDis", new Department());
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
		return new ModelAndView("/Disbursement/minutesOfMeetingsDis");
	}

	@RequestMapping(value = "/minutesOfMeetings", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("minutesOfMeeting") @Validated MinutesOfMeeting minutesOfMeeting, BindingResult result, Model model, HttpSession httpSession,
			@RequestParam("uploadMomFile") MultipartFile uploadMomFile, @RequestParam("uploadGosFile") MultipartFile uploadGosFile) throws IOException {
		logger.debug("MINUTES OF MEETING STARTING..................");

		MinutesOfMeeting minutesOfMeetings = new MinutesOfMeeting();
		MomUploadDocuments momUploadDocument = new MomUploadDocuments();
		String userid = (String) httpSession.getAttribute("userId");
		
		String gosAppId = minutesOfMeeting.getGosAppID();
		
		String correctedappId=gosAppId.replaceAll("[^a-zA-Z0-9]", "");
		String commetieeType=minutesOfMeeting.getMinutesOfMeetingOrGos();
	String momid=minutesOfMeetingRepository.searchByGosAppIdAndCommitee(correctedappId, commetieeType);
				
     if(momid != null) {
		    minutesOfMeetings.setId(momid);
		           }
     else {
		 minutesOfMeetings.setId("MOM" + atomicInteger.getAndIncrement());
         }
			
		
		if (minutesOfMeeting.getCommitteeDepartments() == null || minutesOfMeeting.getCommitteeDepartments().isEmpty()) {
			minutesOfMeetings.setCommitteeDepartments("Empowered Committee");
		} else {
			minutesOfMeetings.setCommitteeDepartments(minutesOfMeeting.getCommitteeDepartments());
		}

		minutesOfMeetings.setMinutesOfMeetingOrGos(minutesOfMeeting.getMinutesOfMeetingOrGos());
		minutesOfMeetings.setCommitteeName(minutesOfMeeting.getCommitteeName());
		minutesOfMeetings.setDateOfMeeting(minutesOfMeeting.getDateOfMeeting());
		String GoAppOldId = minutesOfMeeting.getGosAppID();
		
		minutesOfMeetings.setGosAppID(GoAppOldId.replaceAll("[^a-zA-Z0-9]", ""));
		if (minutesOfMeeting.getGosNo().isEmpty()) {
			minutesOfMeetings.setGosNo(null);
		} else {
			minutesOfMeetings.setGosNo(minutesOfMeeting.getGosNo());
		}
		minutesOfMeetings.setGosName(minutesOfMeeting.getGosName());
		minutesOfMeetings.setGosDate(minutesOfMeeting.getGosDate());
		if (userid != null && !userid.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
			minutesOfMeetings.setUserId(loginUser.get().getid());
			minutesOfMeetings.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
		}
		minutesOfMeetings.setCreateDate(new Date());
		minutesOfMeetings.setActive("active");
		minutesOfMeetingService.saveMinutesOfMeeting(minutesOfMeetings);

		if (uploadMomFile != null && !uploadMomFile.isEmpty()) {

			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());

			momUploadDocument.setFileName(uploadMomFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadMomFile.getContentType());
			momUploadDocument.setFileData(uploadMomFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
			momUploadDocument.setAppId(minutesOfMeetings.getGosAppID());
		} else {
			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());
			momUploadDocument.setFileName(uploadGosFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadGosFile.getContentType());
			momUploadDocument.setFileData(uploadGosFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
			momUploadDocument.setAppId(minutesOfMeetings.getGosAppID());
		}

		MomUploadDocuments momUploadDocuments2 = momUploadDocumentsRepository.getMomByMomId(minutesOfMeetings.getId());

		if (momUploadDocuments2 == null) {
			momUploadDocumentsService.saveMomDocuments(momUploadDocument);
		} else {
			momUploadDocument.set_id(momUploadDocuments2.get_id());
			// momUploadDocumentsService.deleteMomDocuments(momUploadDocument);
			momUploadDocumentsService.saveMomDocuments(momUploadDocument);
		}

		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("redirect:/momgo");
	}

	// DIS Minuts of Meeting Save
	@RequestMapping(value = "/minutesOfMeetingsDis", method = RequestMethod.POST)
	public ModelAndView minutesOfMeetingsDis(@ModelAttribute("minutesOfMeeting") @Validated MinutesOfMeetingDis minutesOfMeeting, BindingResult result, Model model, HttpSession httpSession,
			@RequestParam("uploadMomFile") MultipartFile uploadMomFile, @RequestParam("uploadGosFile") MultipartFile uploadGosFile, @RequestParam("uploadCabinetFile") MultipartFile uploadCabinetFile)
			throws IOException {
		logger.debug("MINUTES OF MEETING STARTING..................");

		MinutesOfMeetingDis minutesOfMeetings = new MinutesOfMeetingDis();
		MomUploadDocumentsDis momUploadDocument = new MomUploadDocumentsDis();
		String userid = (String) httpSession.getAttribute("userId");
		
		String oldAppId = minutesOfMeeting.getGosAppID();
		String commetieeType =minutesOfMeeting.getMinutesOfMeetingOrGos();
		String correctedappId =oldAppId.replaceAll("[^a-zA-Z0-9]", "");
		String momdisId=disMinutesOfMeetingRepository.searchByGosAppIdAndCommitee(correctedappId, commetieeType);
		if(momdisId != null)
		{
			minutesOfMeetings.setId(momdisId);
		}
		else
		{
			minutesOfMeetings.setId("MOM" + atomicInteger.getAndIncrement());
		}

		
		if (minutesOfMeeting.getCommitteeDepartments() == null || minutesOfMeeting.getCommitteeDepartments().isEmpty()) {
			minutesOfMeetings.setCommitteeDepartments("Empowered Committee");
		} else {
			minutesOfMeetings.setCommitteeDepartments(minutesOfMeeting.getCommitteeDepartments());
		}

		minutesOfMeetings.setMinutesOfMeetingOrGos(minutesOfMeeting.getMinutesOfMeetingOrGos());
		minutesOfMeetings.setCommitteeName(minutesOfMeeting.getCommitteeName());
		minutesOfMeetings.setDateOfMeeting(minutesOfMeeting.getDateOfMeeting());
		String GoAppId = minutesOfMeeting.getGosAppID();
		
		minutesOfMeetings.setGosAppID(GoAppId.replaceAll("[^a-zA-Z0-9]", ""));
		if (minutesOfMeeting.getGosNo().isEmpty()) {
			minutesOfMeetings.setGosNo(null);
		} else {
			minutesOfMeetings.setGosNo(minutesOfMeeting.getGosNo());
		}
		minutesOfMeetings.setGosName(minutesOfMeeting.getGosName());
		minutesOfMeetings.setGosDate(minutesOfMeeting.getGosDate());
		if (userid != null && !userid.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
			minutesOfMeetings.setUserId(loginUser.get().getid());
			minutesOfMeetings.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
		}
		minutesOfMeetings.setCreateDate(new Date());
		minutesOfMeetings.setActive("active");
		disMinutesOfMeetingService.saveMinutesOfMeeting(minutesOfMeetings);

		if (uploadMomFile != null && !uploadMomFile.isEmpty()) {

			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());

			momUploadDocument.setFileName(uploadMomFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadMomFile.getContentType());
			momUploadDocument.setFileData(uploadMomFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
		} else if (uploadCabinetFile != null && !uploadCabinetFile.isEmpty()) {
			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());
			momUploadDocument.setFileName(uploadCabinetFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadCabinetFile.getContentType());
			momUploadDocument.setFileData(uploadCabinetFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
		}

		else if(uploadGosFile != null && !uploadGosFile.isEmpty()){
			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());
			momUploadDocument.setFileName(uploadGosFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadGosFile.getContentType());
			momUploadDocument.setFileData(uploadGosFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
		}

		MomUploadDocumentsDis momUploadDocuments2 = dismomUploadDocumentsRepository.getMomByMomId(minutesOfMeetings.getId());

		if (momUploadDocuments2 == null) {
			dismomUploadDocumentsService.saveMomDocuments(momUploadDocument);
		} else {
			momUploadDocument.set_id(momUploadDocuments2.get_id());
			// momUploadDocumentsService.deleteMomDocuments(momUploadDocument);
			dismomUploadDocumentsService.saveMomDocuments(momUploadDocument);
		}

		model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
		return new ModelAndView("redirect:/momgoDis");
	}

	@GetMapping(value = "/momgobyDIC")
	public ModelAndView listbyDIC(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIC..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MinutesOfMeeting> minutesOfMeetingEmpowerCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingSactionCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingGosCommitList = new ArrayList<MinutesOfMeeting>();

			
			List<MinutesOfMeeting> minutesOfMeetingList = minutesOfMeetingService.getByuserId(userId);
			if (minutesOfMeetingList.size() > 0) {
				for (MinutesOfMeeting list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getCommitteeDepartments().equals("")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("minutesOfMeetingsbyDIC");
	}

	@GetMapping(value = "/momgobyMDPICUP")
	public ModelAndView listbyMDPICUP(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIC..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNote> minutesOfMeetingEmpowerCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingSactionCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingGosCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingList = new ArrayList<>();
			
			List<Object> minutesOfMeetingObjectList = minutesOfMeetingRepository.findAllMOMbyStatus("MDPICUP");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNote momNote = new MomCirculateNote();
			for (Object object : minutesOfMeetingObjectList) {
				momNote = new MomCirculateNote();
				row = (Object[]) object;
				
				HashMap<String, Object> map = new HashMap<>();
				
				if (row[0] != null)
					map.put("id", row[0].toString());
				if (row[1] != null)
					map.put("gosAppID", row[1].toString());
				if (row[2] != null)
					map.put("minutesOfMeetingOrGos", row[2]);
				if (row[3] != null)
					map.put("committeeDepartments", row[3].toString());
				if (row[4] != null)
					map.put("dateOfMeeting", row[4]);
				if (row[5] != null)
					map.put("committeeName", row[5].toString());
				if (row[6] != null)
					map.put("gosName", row[6].toString());
				if (row[7] != null)
					map.put("deptName", row[7].toString());
				if (row[8] != null)
					map.put("noteReportStatus", row[8].toString());
				if (row[9] != null)
					map.put("noteReportStatus", row[9].toString());
				momNote = mapper.convertValue(map, MomCirculateNote.class);
				minutesOfMeetingList.add(momNote);
			}

			if (minutesOfMeetingList.size() > 0) {
				for (MomCirculateNote list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs") && list.getNoteReportStatus().equalsIgnoreCase("Go Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("minutesOfMeetingsbyMDPICUP");
	}

	@GetMapping(value = "/disMomgobyMDPICUP")
	public ModelAndView listbyMDPICUPDis(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIS MD..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNoteDis> minutesOfMeetingEmpowerCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingSactionCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingGosCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingCabinetCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingmdList = new ArrayList<MomCirculateNoteDis>();
	
			List<Object> minutesOfMeetingObjectListDis = disMinutesOfMeetingRepository.findAllDisMOMbyStatus("MDPICUP");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNoteDis momNote = new MomCirculateNoteDis();
			for (Object object : minutesOfMeetingObjectListDis) {
				momNote = new MomCirculateNoteDis();
				row = (Object[]) object;
			
				HashMap<String, Object> map = new HashMap<>();
				/* System.out.println("Element " + Arrays.toString(row)); */

				if (row[0] != null)
					map.put("id", row[0].toString());
				if (row[1] != null)
					map.put("gosAppID", row[1].toString());
				if (row[2] != null)
					map.put("minutesOfMeetingOrGos", row[2]);
				if (row[3] != null)
					map.put("committeeDepartments", row[3].toString());
				if (row[4] != null)
					map.put("dateOfMeeting", row[4]);
				if (row[5] != null)
					map.put("committeeName", row[5].toString());
				if (row[6] != null)
					map.put("gosName", row[6].toString());
				if (row[7] != null)
					map.put("deptName", row[7].toString());
				if (row[8] != null)
					map.put("noteReportStatus", row[8].toString());
				if (row[9] != null)
					map.put("gosNo", row[9].toString());
				                                                                                      
				momNote = mapper.convertValue(map, MomCirculateNoteDis.class);
				minutesOfMeetingmdList.add(momNote);
			}

			//List<MinutesOfMeetingDis> minutesOfMeetingList = disMinutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			if (minutesOfMeetingmdList.size() > 0) {
				for (MomCirculateNoteDis list : minutesOfMeetingmdList) {

					if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("CabinetNote") && list.getNoteReportStatus().equalsIgnoreCase("Cabinet Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadCabinetFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingCabinetCommitList.add(list);
					}

					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs") && list.getNoteReportStatus().equalsIgnoreCase("GOs Report")) {
						MomUploadDocumentsDis momUploadDocuments =
								  dismomUploadDocumentsRepository.getMomByMomId(list.getId()); if
								  (momUploadDocuments != null && momUploadDocuments.getFileName() != null &&
								  !momUploadDocuments.getFileName().isEmpty()) {
								  list.setUploadGosFile(momUploadDocuments.getFileName()); }
								  minutesOfMeetingGosCommitList.add(list); }
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);
			model.addAttribute("minutesOfMeetingCabinetCommitList", minutesOfMeetingCabinetCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
		return new ModelAndView("/Disbursement/disMinutesOfMeetingsbyMDPICUP");
	}

	@RequestMapping(value = "/minutesOfMeetingsbyDIC", method = RequestMethod.POST)
	public ModelAndView savebyDIC(@ModelAttribute("minutesOfMeeting") @Validated MinutesOfMeeting minutesOfMeeting, BindingResult result, Model model, HttpSession httpSession,
			@RequestParam("uploadMomFile") MultipartFile uploadMomFile, @RequestParam("uploadGosFile") MultipartFile uploadGosFile) throws IOException {
		logger.debug("MINUTES OF MEETING STARTING BY DIC..................");

		MinutesOfMeeting minutesOfMeetings = new MinutesOfMeeting();
		MomUploadDocuments momUploadDocument = new MomUploadDocuments();
		String userid = (String) httpSession.getAttribute("userId");

		minutesOfMeetings.setId("MOM" + atomicInteger.getAndIncrement());
		minutesOfMeetings.setCommitteeDepartments(minutesOfMeeting.getCommitteeDepartments());
		minutesOfMeetings.setMinutesOfMeetingOrGos(minutesOfMeeting.getMinutesOfMeetingOrGos());
		minutesOfMeetings.setCommitteeName(minutesOfMeeting.getCommitteeName());
		minutesOfMeetings.setDateOfMeeting(minutesOfMeeting.getDateOfMeeting());
		minutesOfMeetings.setGosName(minutesOfMeeting.getGosName());
		minutesOfMeetings.setGosDate(minutesOfMeeting.getGosDate());
		if (userid != null && !userid.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userid);
			minutesOfMeetings.setUserId(loginUser.get().getid());
			minutesOfMeetings.setCreatedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
		}
		minutesOfMeetings.setCreateDate(new Date());
		minutesOfMeetings.setActive("active");
		minutesOfMeetingService.saveMinutesOfMeeting(minutesOfMeetings);

		if (uploadMomFile != null && !uploadMomFile.isEmpty()) {

			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());
			momUploadDocument.setFileName(uploadMomFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadMomFile.getContentType());
			momUploadDocument.setFileData(uploadMomFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
		} else {
			momUploadDocument.setDocumentId("MOMDOC" + atomicInteger1.getAndIncrement());
			momUploadDocument.setFileName(uploadGosFile.getOriginalFilename());
			momUploadDocument.setFileType(uploadGosFile.getContentType());
			momUploadDocument.setFileData(uploadGosFile.getBytes());
			momUploadDocument.setDocCreatedBy("admin");
			momUploadDocument.setDocUpdateDate(new Date());
			momUploadDocument.setDocCreatedDate(new Date());
			momUploadDocument.setMomId(minutesOfMeetings.getId());
		}

		MomUploadDocuments momUploadDocuments2 = momUploadDocumentsRepository.getMomByMomId(minutesOfMeetings.getId());
		if (momUploadDocuments2 == null) {
			momUploadDocumentsService.saveMomDocuments(momUploadDocument);
		} else {
			momUploadDocument.set_id(momUploadDocuments2.get_id());
			// momUploadDocumentsService.deleteMomDocuments(momUploadDocument);
			momUploadDocumentsService.saveMomDocuments(momUploadDocument);
		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("redirect:/momgobyDIC");
	}

	@GetMapping(value = "/momgobyJCI")
	public ModelAndView listbyJCI(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIC..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MinutesOfMeeting> minutesOfMeetingEmpowerCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingSactionCommitList = new ArrayList<MinutesOfMeeting>();
			List<MinutesOfMeeting> minutesOfMeetingGosCommitList = new ArrayList<MinutesOfMeeting>();

			// List<MinutesOfMeeting> minutesOfMeetingList =
			// minutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			List<MinutesOfMeeting> minutesOfMeetingList = minutesOfMeetingService.getAlluserId();
			if (minutesOfMeetingList.size() > 0) {
				for (MinutesOfMeeting list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("minutesOfMeetingsbyJCI");
	}

	@RequestMapping(value = "/downloadDocMom", method = RequestMethod.GET)
	public void downloadDocMom(@RequestParam(value = "fileNameMomId", required = false) String fileNameMomId, Model model, HttpServletResponse response) {

		MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(fileNameMomId);
		String fileName = momUploadDocuments.getFileName();
		byte[] fileNameData = momUploadDocuments.getFileData();
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");
		InputStream is = new ByteArrayInputStream(fileNameData);
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			is.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@GetMapping(value = "/momgoid6Dis")
	public ModelAndView momgoID6Dis(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING ID6 DIS......");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MinutesOfMeetingDis> minutesOfMeetingEmpowerCommitList = new ArrayList<MinutesOfMeetingDis>();
			List<MinutesOfMeetingDis> minutesOfMeetingSactionCommitList = new ArrayList<MinutesOfMeetingDis>();
			List<MinutesOfMeetingDis> minutesOfMeetingGosCommitList = new ArrayList<MinutesOfMeetingDis>();

			List<DISPrepareAgendaNotes> listpreAgendaNotesMega = disprepareAgendaRepos.findAllPrepareAgendaNotebyStatus();
			List<DISPrepareAgendaNotes> listpreAgendaNotesLarge = disprepareAgendaRepos.findLargePrepareAgendaNotebyStatus();

			List<MinutesOfMeetingDis> minutesOfMeetingList = disMinutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			if (minutesOfMeetingList.size() > 0) {
				for (MinutesOfMeetingDis list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && !(list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs"))) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
			model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);
			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
		return new ModelAndView("/Disbursement/minutesOfMeetingsID6Dis");
	}

}
