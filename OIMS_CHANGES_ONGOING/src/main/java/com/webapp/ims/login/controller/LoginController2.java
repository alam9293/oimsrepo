package com.webapp.ims.login.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.login.model.Login;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblGroupService;
import com.webapp.ims.uam.service.TblRoleService;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class LoginController2 {
	private final Logger logger = LoggerFactory.getLogger(LoginController2.class);

	@Autowired
	private TblUsersService loginService;
	
	@Autowired
	private TblDepartmentService tblDepartmentService;
	
	@Autowired
	private TblRoleService roleService;
	
	@Autowired
	private TblGroupService groupService;

	@Autowired
	private TblFormService tblFormService;
	
	public LinkedHashMap<String,String> getFormActionMap(HttpSession session) 
	{
		
		LinkedHashMap<String,String>Tblformactiondsplname = new LinkedHashMap<String,String>();
		Tblformactiondsplname = tblFormService.getActionandDsplayLbl((String) session.getAttribute("allowedform"));
		return Tblformactiondsplname;
	}
	
	@RequestMapping(value = "/login2", method = RequestMethod.GET)
	public ModelAndView login(Model model) {

		logger.info("Initilize show login Process!");

		model.addAttribute("login2", new Login());
		logger.info("Exited login Process!");
		return new ModelAndView("login2");

	}

	@RequestMapping(value = "/UserLogin2", method = RequestMethod.POST)
	public ModelAndView UserLogin(@ModelAttribute("login2") @Validated TblUsers login, BindingResult result, Model model,
			HttpSession session,HttpServletRequest request) {

		logger.debug("Initilize Login Processing!");
		String userName = login.getUserName().trim();
		String password = login.getPassword();
		
		
		String allowedform = login.getAllowedForms();
		
		
		System.out.println("login.getRoleId() :"+login.getRoleId());
		System.out.println("userName :"+userName);
		System.out.println("password :"+password);
		
		TblUsers obj = loginService.getLoginUsrDetail(userName);
		
		if(obj==null)
		{
			model.addAttribute("message", "User Id not Exist");
			return new ModelAndView("login2");
		}
		else
		{
			TblUsers loginUser = loginService.getLoginByuserName(userName);
			
			String department = loginUser.getDepartment();
			System.out.println("department :"+department);
			String usrstatus          = obj.getStatus();
			String usractivestatus    = (obj.getUsrActiveStatus()==null?"N":obj.getUsrActiveStatus());
			int curattempt            = (obj.getCurrentAttempts()==null?0:Integer.parseInt(obj.getCurrentAttempts()));
			int maxloginattempt       = 3;
			
			String dblockedstatus  = obj.getLockedStatus();
			String dbloggedstatus  = obj.getLoggedStatus();
			
			if("D".equalsIgnoreCase(usrstatus) && "N".equalsIgnoreCase(usractivestatus))
			{
				model.addAttribute("message", "User Id is dormant. Please contact OIMS Support Team");
				return new ModelAndView("login2");
			}
			if("D".equalsIgnoreCase(usrstatus) && "X".equalsIgnoreCase(usractivestatus))
			{
				model.addAttribute("message", "User Id is disabled. Please contact OIMS Support Team");
				return new ModelAndView("login2");
			}
			if("A".equalsIgnoreCase(usrstatus) && "N".equalsIgnoreCase(usractivestatus))
			{
				model.addAttribute("message", "User Id is not Mapped to Any Role. Please contact OIMS Support Team");
				return new ModelAndView("login2");
			}
			if("T".equalsIgnoreCase(usrstatus))
			{
				model.addAttribute("message", "User Id is Terminated. Please contact OIMS Support Team");
				return new ModelAndView("login2");
			}
			if(curattempt>maxloginattempt)
			{
				model.addAttribute("message", "Login Failed : Maximum Attempts Reached, UserID has been locked. Please contact OIMS Support Team");
				loginService.executeNativeQuery("Update loc.\"Tbl_Users\" set \"Locked_Status\" ='9' where \"User_Name\" = '"+userName+"'");
				return new ModelAndView("login2");
			}
			if("9".equalsIgnoreCase(dblockedstatus))
			{
				model.addAttribute("message", "User Id is Locked. Please contact OIMS Support Team");
				return new ModelAndView("login2");
			}
			/*
			 * if("9".equalsIgnoreCase(dbloggedstatus)) { model.addAttribute("message", //To be uncommented for same id login issue
			 * "User Id already logged In!"); return new ModelAndView("login2"); }
			 */
	        
			if (loginUser != null && userName.equalsIgnoreCase(loginUser.getUserName().trim())
					&& password.equalsIgnoreCase(loginUser.getPassword().trim())
					) 
			{

				loginService.executeNativeQuery("Update loc.\"Tbl_Users\" set \"Logged_Status\" = '9',\"Locked_Status\"= '1', \"Current_Attempts\" = '0', \"Logout_Date\" = null   where \"User_Name\" = '"+userName+"'");
				System.out.println("RoleId :"+loginUser.getRoleId());
				System.out.println("Role :" +roleService.getRoleNamebyRoleId(loginUser.getRoleId()));
				session.setAttribute("userName", loginUser.getFirstName() + " " + loginUser.getLastName());
				session.setAttribute("userId", String.valueOf(loginUser.getid()));
				session.setAttribute("allowedform", loginUser.getAllowedForms());
				session.setAttribute("userEmaiId", userName);
				session.setAttribute("userr", roleService.getRoleNamebyRoleId(loginUser.getRoleId()));  
				session.setAttribute("userg", groupService.getGroupNamebyGroupId(loginUser.getGroupId()));
				session.setAttribute("userd", department);
				session.setAttribute("ipaddress", request.getRemoteAddr());
				session.setAttribute("loginsuccessFlag", "true");
				session.setAttribute("Tblformactiondsplname", getFormActionMap(session));
				
				model.addAttribute("login2", new TblUsers());
				System.out.println("****************");
				
				if (department.trim().equalsIgnoreCase("Admin Department")) {
					return new ModelAndView("redirect:/DashboardUAM");
				}
				//return new ModelAndView("redirect:/DashboardUAM");  //Designed By Mohd Alam
				
				if (department.trim().equalsIgnoreCase("PICUP Processing Team")) {
					return new ModelAndView("redirect:/dashboard");
				} else if (department.trim().equalsIgnoreCase("JMD PICUP")) {
					return new ModelAndView("redirect:/dashboardJMDPICUP");
				}

				else if (department.trim().equalsIgnoreCase("DIC")) {
					return new ModelAndView("redirect:/dicDepartmentDashboard");
				} else if (department.trim().equalsIgnoreCase("MD PICUP")) {
					return new ModelAndView("redirect:/dashboardMDPICUP");
				} else if (department.trim().equalsIgnoreCase("Head of Nodal Department IIEPP-2017")) {
					return new ModelAndView("redirect:/nodalDeptHeadDashboard");
				} else if (department.trim().equalsIgnoreCase("Concerned JCI IIEPP-2017")) {
					return new ModelAndView("redirect:/dashboardCONCERJCIDEPARTMENT");
				} else if (department.trim().equalsIgnoreCase("PSI")) {
					return new ModelAndView("redirect:/dashboardPSI");

				} else if (department.trim().equalsIgnoreCase("CS")) {
					return new ModelAndView("redirect:/dashboardCS");
				}  if (department.trim().equalsIgnoreCase("ID6")) {
					return new ModelAndView("redirect:/dashboardID6");

				}
				else
				{
					model.addAttribute("message", "User not Belongs to Any Department");
					return new ModelAndView("login2");
				}
				/*
					 * else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role
					 * != null && role != "NA") if (role.contains("ConDept25")) {
					 * 
					 * return new ModelAndView("redirect:/electricityDashboard"); }
					 * 
					 * else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role
					 * != null && role != "NA") if (role.contains("ConDept2")) { return new
					 * ModelAndView("redirect:/departmentDashboard"); } else if
					 * (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
					 * && role != "NA") if (role.contains("ConDept0")) { return new
					 * ModelAndView("redirect:/aggricultureDashboard"); } else if
					 * (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
					 * && role != "NA") if (role.contains("ConDept4")) { return new
					 * ModelAndView("redirect:/labourDashboard"); } else if
					 * (department.trim().equalsIgnoreCase("ConcernedDepartments") && role != null
					 * && role != "NA") if (role.contains("ConDept4")) { return new
					 * ModelAndView("redirect:/labourDashboard"); }
					 * 
					 * else if (department.trim().equalsIgnoreCase("ConcernedDepartments") && role
					 * != null && role != "NA") if (role.contains("ConDept5")) { return new
					 * ModelAndView("redirect:/stampDutyDashboard"); }
					 */		
			}
			else 
			{
				loginService.executeNativeQuery("Update loc.\"Tbl_Users\" set  \"Current_Attempts\" = (COALESCE(\"Current_Attempts\",0)+1) where \"User_Name\" = '"+userName+"'");
				model.addAttribute("userName", "");
				model.addAttribute("password", "");
				model.addAttribute("captcha", "");
				model.addAttribute("department", "");
				model.addAttribute("message", "Password is Incorrect.");
				return new ModelAndView("login2");
			}

		}
	}

	@RequestMapping(value = "/userLogout2", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		
		loginService.executeNativeQuery("Update loc.\"Tbl_Users\" set \"Logged_Status\" = '0',\"Locked_Status\"= '0',\"Current_Attempts\" ='0', \"Logout_Date\" = now()   where \"User_Name\" = '"+(String) session.getAttribute("userEmaiId")+"'");
		session.removeAttribute("userName");   
		session.removeAttribute("userId");     
		session.removeAttribute("allowedform");
		session.removeAttribute("userEmaiId"); 
		session.removeAttribute("userr");     
		session.removeAttribute("userg");      
		session.removeAttribute("ipaddress");
		session.removeAttribute("Tblformactiondsplname");
		return new ModelAndView("redirect:/login2");
	}
}
