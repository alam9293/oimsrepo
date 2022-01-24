package com.webapp.ims.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.login.model.Login;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.DPTAgendaRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class ConcernDepartmentController {
	private final Logger logger = LoggerFactory.getLogger(ConcernDepartmentController.class);

	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;
	
	@Autowired
	PrepareAgendaNoteRepository repoPreapareagendanote;
	
	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	
	@Autowired
	DPTAgendaRepository dptAgendaRepository;
	
	@Autowired
	DISPrepareAgendaNotesRepository disPrepareAgendaNoteRepos;
	

	@Autowired
	private DepartmentService deptService;
	private AtomicInteger atomicInteger = new AtomicInteger();
	@Autowired
	private LoginRepository loginRepository;
	private List<Department> deptList = new LinkedList<>();
	private String[] deptId = new String[50];

	// send to cerncen
	@Autowired
	TblUsersService loginService;
	@Autowired
	TblUsersService loginservice;
	@Autowired
	SendingEmail sendingEmail;

	@SuppressWarnings("unchecked")
	@PostMapping("/addDept")
	@ResponseBody
	public void addNewDepartment(@RequestParam("name") String name, @RequestParam("email") String email,
			HttpSession session) {

		try {
			String userid = (String) session.getAttribute("userId");
			Login login = loginRepository.findById(userid);

			String key = login.getUserName() + "##" + "addDept";
			List<Department> list = (List<Department>) session.getAttribute(key);
			if (list == null) {
				list = new ArrayList<Department>();
			}

			Department newDept = new Department();
			//newDept.setNewDeptName(name);
			newDept.setDeptEmail(email);
			newDept.setDeptStatus("active");
			newDept.setDeptCreatedBy(login.getUserName());
			list.add(newDept);
			session.setAttribute(key, list);
		} catch (Exception e) {
			logger.error("concrenDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}

	}

	@PostMapping("/addDepartment")
	public ModelAndView addDepartment(@Validated @ModelAttribute("department") Department department,
			BindingResult result, HttpSession session, Model model) {
		String applId = (String) session.getAttribute("appId");
		try {
			String userid = (String) session.getAttribute("userId");
			Login login = loginRepository.findById(userid);
			Department newDept = new Department();
			newDept.setNewDeptName(department.getNewDeptName());
			newDept.setDeptEmail(department.getDeptEmail());
			newDept.setDeptStatus("active");
			newDept.setDeptCreatedBy(login.getUserName());
			/// String deptId = "OIMS" + atomicInteger.getAndIncrement();
			// newDept.setDeptId(deptId);
			// newDept.setDeptModifiyDate(new Date());

			if (result.hasErrors()) {
				System.out.println("::::: concren dept error::::");
				model.addAttribute("deptList", deptList);
			}

			deptList.add(newDept);
			//newDept.setDeptName(null);
			//newDept.setDeptEmail(null);
			model.addAttribute("deptList", deptList);
			model.addAttribute("department", newDept);
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		} catch (Exception e) {
			logger.error("concrenDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/viewApplicationDetails?applicantId=" + applId);

	}

	@PostMapping("/deleteDepartment")
	public ModelAndView deleteDepartment(@ModelAttribute("department") Department department, BindingResult result,
			Model model, @RequestParam(value = "selectedRow", required = false) int index) {
		try {
			if (!deptList.isEmpty() && deptList.get(index) != null) {
				Department dept = deptList.get(index);
				deptList.remove(index);
				model.addAttribute("deptList", deptList);
				model.addAttribute("Department", dept);
			}
		} catch (Exception e) {
			logger.error("deleteDepartment exception ##### " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("view_all_application_details");
	}

	@PostMapping("/saveConcernDepartment")
	public ModelAndView saveConcernDepartment(@Validated @ModelAttribute("department") Department department,
			@Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept, BindingResult result,
			HttpSession session, Model model) {
		List<String> newlist = new ArrayList<>();
		String applId = (String) session.getAttribute("appId");
		String mail = null;
		String str=department.getNewDeptName().toString();
		String[] strarr = str.split(",");
		for(String mail1:strarr) {
			newlist.add(mail1);
		}
		
		List<String> emailList1 = new ArrayList<>(Arrays.asList(department.getNewDeptName()));
		List<TblUsers> loginList = new ArrayList<>();
		
		try {
			/*
			 * ConcernDepartment conDept = new ConcernDepartment(); String condeptId =
			 * "FILD" + atomicInteger.getAndIncrement(); conDept.setConDeptId(condeptId);
			 * conDept.setConDeptStatus("Active"); conDept.setConDeptApplId(applId);
			 * conDept.setConDeptUserId(userid);
			 * conDept.setConDeptCreatedBy(login.getUserName());
			 * conDept.setDeptId(Arrays.toString(deptId)); conDept.setConDeptModifiyDate(new
			 * Date());
			 */

			if (result.hasErrors()) {
				System.out.println("::::: concren dept error::::");
				model.addAttribute("deptList", deptList);

			}
			
			String userid = (String) session.getAttribute("userId");
			Login login = loginRepository.findById(userid);
			String key = login.getUserName() + "##" + "addDept";
			List<Department> list = (List<Department>) session.getAttribute(key);
			if (list != null) {
				list = deptService.saveDepartmentList(list);
				if (list.size() > 0) {
					loginList = new ArrayList<TblUsers>();
					for (Department temp : list) {
						TblUsers obj = new TblUsers();
						obj.setUserName(temp.getDeptEmail());
						//obj.setDepartment(temp.getNewDeptName());
						obj.setPassword(loginservice.getRandomPassword().toString());
						loginService.insertWithQuery(obj);
						loginList.add(obj);
					}
				}
			}

			String msg = "Department Name: dept name \r\n Application Id" +applId +": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: loc";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" +applId + " Ready for your comments ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created Login credential for Department",
					"your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// Start Change Status Send to concern department
			if (!emailList1.isEmpty()) {
				PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
				// String appId = (String) session.getAttribute("appId");
				if (applId != null && !applId.isEmpty()) {
					PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applId);
					prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
					prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
					prepareAgendaNotes.setId(prepareAgendaNote.getId());
					prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
					prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
					prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
					prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
					prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
					prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
					prepareAgendaNotes.setComments(prepareAgendaNote.getComments());
					prepareAgendaNotes.setStatus("Sent To Concerned Department");
					prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
					prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);
					model.addAttribute("saveConfirmation", "Concerned Department Submitted sucessfully");
				}
			}

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
		

		} catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/viewApplicationDetails?applicantId=" + applId);
	}
	
	@GetMapping(value = "/deptApplicationForLoc")
	public String deptApplicationConcern(ModelMap model, HttpSession session) {
		
		
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			String[] str = new String[10];
			str[0] = tuple.get(0).toString();
			str[1] = tuple.get(1).toString();
			str[2] = tuple.get(2).toString();
			str[3] = tuple.get(3).toString();
			str[4] = tuple.get(4).toString();
			str[5] = tuple.get(5).toString();
			String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
			str[6] = temp == null ? "Pending for Evaluation" : temp;
			finalList.add(str);
		}
		model.addAttribute("investmentDetailsList", finalList);

		List<String[]> finalListdis = new ArrayList<>();

		List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
		for (Tuple tuple : listdis) {
			String[] str = new String[10];
			str[0] = tuple.get(0).toString();
			str[1] = tuple.get(1).toString();
			str[2] = tuple.get(2).toString();
			str[3] = tuple.get(3).toString();
			str[4] = tuple.get(4).toString();
			str[5] = tuple.get(5).toString();
			String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
			str[6] = temp == null ? "Pending for Evaluation" : temp;
			finalListdis.add(str);
		}
		model.addAttribute("investmentDetailsListdis", finalListdis);

		
		return "view-incentive-concern";
	}


@GetMapping(value = "/aggriCulturedeptApplicationForLoc")
public String aggriculturedeptApplicationConcern(ModelMap model, HttpSession session) {
	
	
	List<String[]> finalList = new ArrayList<>();

	List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
	for (Tuple tuple : list) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalList.add(str);
	}
	model.addAttribute("investmentDetailsList", finalList);

	List<String[]> finalListdis = new ArrayList<>();

	List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
	for (Tuple tuple : listdis) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalListdis.add(str);
	}
	model.addAttribute("investmentDetailsListdis", finalListdis);

	
	return "view-aggriculture-incentive-concern";
}

@GetMapping(value = "/electricitydeptApplicationForLoc")
public String electricityApplicationConcern(ModelMap model, HttpSession session) {
	
	
	List<String[]> finalList = new ArrayList<>();

	List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
	for (Tuple tuple : list) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalList.add(str);
	}
	model.addAttribute("investmentDetailsList", finalList);

	List<String[]> finalListdis = new ArrayList<>();

	List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
	for (Tuple tuple : listdis) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalListdis.add(str);
	}
	model.addAttribute("investmentDetailsListdis", finalListdis);

	
	return "view-electricity-incentive-concern";
}
@GetMapping(value = "/labourdeptApplicationForLoc")
public String labourApplicationConcern(ModelMap model, HttpSession session) {
	
	
	List<String[]> finalList = new ArrayList<>();

	List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
	for (Tuple tuple : list) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalList.add(str);
	}
	model.addAttribute("investmentDetailsList", finalList);

	List<String[]> finalListdis = new ArrayList<>();

	List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
	for (Tuple tuple : listdis) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalListdis.add(str);
	}
	model.addAttribute("investmentDetailsListdis", finalListdis);

	
	return "view-labour-incentive-concern";
}

@GetMapping(value = "/stampDutyApplicationForLoc")
public String stampDutyApplicationConcern(ModelMap model, HttpSession session) {
	
	
	List<String[]> finalList = new ArrayList<>();

	List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
	for (Tuple tuple : list) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalList.add(str);
	}
	model.addAttribute("investmentDetailsList", finalList);

	List<String[]> finalListdis = new ArrayList<>();

	List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
	for (Tuple tuple : listdis) {
		String[] str = new String[10];
		str[0] = tuple.get(0).toString();
		str[1] = tuple.get(1).toString();
		str[2] = tuple.get(2).toString();
		str[3] = tuple.get(3).toString();
		str[4] = tuple.get(4).toString();
		str[5] = tuple.get(5).toString();
		String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
		str[6] = temp == null ? "Pending for Evaluation" : temp;
		finalListdis.add(str);
	}
	model.addAttribute("investmentDetailsListdis", finalListdis);

	
	return "view-stampDuty-incentive-concern";
}


}
