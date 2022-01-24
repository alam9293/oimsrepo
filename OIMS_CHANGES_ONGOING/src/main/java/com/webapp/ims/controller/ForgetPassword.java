/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.webapp.ims.login.controller.LoginController2;
import com.webapp.ims.login.model.Login;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 *
 */
@Controller
public class ForgetPassword {
	private final Logger logger = LoggerFactory.getLogger(LoginController2.class);
	
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	SendingEmail sendingEmail;
	@Autowired
	private TblUsersService loginService;
	
	@GetMapping(value = "/forgetpassword")
	public String forgetpassword() {
		return "forgetpassword";
		
	}
	
	@PostMapping(value = "/sendpassword")
	public ModelAndView sendnewpassword(@ModelAttribute("login") @Validated Login login, BindingResult result, Model model,
			HttpSession session)
	{
		logger.debug("Initilize Login Processing!");
		//from jsp
		String userName = login.getUserName();
		String department = login.getDepartment();
		//from database
		 // Login loginUser = loginRepository.findpasswordByuserName(userName, department);
		
		  TblUsers loginUser = loginService.getLoginByuserName(userName);
		  
		  if(loginUser != null && userName.equalsIgnoreCase(loginUser.getUserName().trim())
					&& department.equalsIgnoreCase(loginUser.getDepartment())) {
			  String msg = "Hi Your Password is " + loginUser.getPassword();
			  sendingEmail.sentEmail("Your Password", msg, userName);
			  
			  
		  }
		  else {
				model.addAttribute("message", "User name and Password is Incorrect.");
				return new ModelAndView("forgetpassword");
			}
		  		model.addAttribute("message1", "Password send to registered Email.");
		  		return new ModelAndView("forgetpassword");
	}
}
