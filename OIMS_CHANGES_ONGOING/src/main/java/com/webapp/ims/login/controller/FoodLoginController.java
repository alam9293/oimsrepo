package com.webapp.ims.login.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.login.model.Login;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class FoodLoginController {
	private final Logger logger = LoggerFactory.getLogger(FoodLoginController.class);

	@Autowired
	private TblUsersService loginService;

	@GetMapping(value = "/foodLogin")
	public ModelAndView foodLogin(Model model) {

		logger.info("Get Food login Start ");
		model.addAttribute("login", new Login());
		logger.info("Get Food login End!");
		return new ModelAndView("/foodPolicy/foodLogin");
	}

	@RequestMapping(value = "/foodUserLogin", method = RequestMethod.POST)
	public ModelAndView foodUserLogin(@ModelAttribute("login") @Validated Login login, BindingResult result,
			Model model, HttpSession session) {

		logger.debug("Initilize Login Processing!");
		String userName = login.getUserName().trim();
		String password = login.getPassword();
		String department = login.getDepartment();
		String role = ""; // login.getRole();
		TblUsers loginUser = loginService.getLoginByuserName(userName);

		if (loginUser != null && userName.equalsIgnoreCase(loginUser.getUserName().trim())
				&& password.equalsIgnoreCase(loginUser.getPassword().trim())) {
			session.setAttribute("userName", loginUser.getFirstName() + " " + loginUser.getLastName());
			session.setAttribute("userId", String.valueOf(loginUser.getid()));
			role = loginUser.getRoleId();
			session.setAttribute("role", loginUser.getRoleId());

			model.addAttribute("login", new Login());
			if (department.trim().equalsIgnoreCase("Nodal Officer")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("Joint Director")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("Finance Controller")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("Director")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("ACS")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("APC")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");
			} else if (department.trim().equalsIgnoreCase("FoodProcessingOfficer")) {
				return new ModelAndView("redirect:/foodPolicyDashboard");

			} else if (department.trim().equalsIgnoreCase("CS")) {
				return new ModelAndView("redirect:/dashboardCS");
			} else if (department.trim().equalsIgnoreCase("ID6")) {
				return new ModelAndView("redirect:/dashboardID6");

			} else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null && role != "NA")
				if (role.contains("ConDept25")) {
					return new ModelAndView("redirect:/electricityDashboard");
				} else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null && role != "NA")
					if (role.contains("ConDept2")) {
						return new ModelAndView("redirect:/departmentDashboard");
					} else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
							&& role != "NA")
						if (role.contains("ConDept0")) {
							return new ModelAndView("redirect:/aggricultureDashboard");
						} else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
								&& role != "NA")
							if (role.contains("ConDept4")) {
								return new ModelAndView("redirect:/labourDashboard");
							} else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
									&& role != "NA")
								if (role.contains("ConDept4")) {
									return new ModelAndView("redirect:/labourDashboard");
								}

								else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
										&& role != "NA")
									if (role.contains("ConDept5")) {
										return new ModelAndView("redirect:/stampDutyDashboard");
									} else {
										model.addAttribute("userName", "");
										model.addAttribute("password", "");
										model.addAttribute("captcha", "");
										model.addAttribute("department", "");
										model.addAttribute("message", "User name and Password is Incorrect.");
										return new ModelAndView("/foodPolicy/foodLogin");
									}
		} else {

			model.addAttribute("userName", "");
			model.addAttribute("password", "");
			model.addAttribute("captcha", "");
			model.addAttribute("department", "");
			model.addAttribute("message", "User name and Password is Incorrect.");
			return new ModelAndView("/foodPolicy/foodLogin");
		}
		return new ModelAndView("/foodPolicy/foodLogin");

	}

	@RequestMapping(value = "/fooduserLogout", method = RequestMethod.GET)
	public ModelAndView foodlogout(HttpSession session) {
		session.removeAttribute("userName");
		return new ModelAndView("redirect:/foodLogin");
	}
}
