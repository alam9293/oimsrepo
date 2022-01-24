/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;
import com.webapp.ims.uam.service.TblRoleService;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 */
@Controller
public class UsertoRoleMappingControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddRoleControlller.class);
	
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	private TblFormService tblFormService;
	
	@Autowired
	TblUsersService tblUsersService;
	
	@Autowired
	TblRoleService tblRoleService;
	
	public List<TblDepartment> getDepartmentList() 
	{
	      List<TblDepartment> DeaprtmentList = tblDepartmentService.getEnableDepartmentName();
	      return DeaprtmentList;
	}
	
	public LinkedHashMap<String,String> getFormActionMap(String allowedform) 
	{
		
		LinkedHashMap<String,String>Tblformactiondsplname = new LinkedHashMap<String,String>();
		Tblformactiondsplname = tblFormService.getActionandDsplayLbl(allowedform);
		return Tblformactiondsplname;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamedeptIDandIdMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		formNamedeptIDandIdMap = tblFormService.getformNameDeptCodeMap();
		return formNamedeptIDandIdMap;
	}
	
	public List<TblUsers> getUserNameandRoleList(String loginUser) 
	{
		  
	      List<TblUsers> RoleIdsList = tblUsersService.getEnableUserName(loginUser);
	      return RoleIdsList;
	}
	
	public LinkedHashMap<String,ArrayList<String>> getroleIdAllowedformsMap() 
	{
		
		LinkedHashMap<String,ArrayList<String>>roleIdAllowedformsMap = new LinkedHashMap<String,ArrayList<String>>();
		ArrayList<String>formsNamelist = new ArrayList<String>();
		List<TblRole>roleslist = tblRoleService.getRoles();
		for(TblRole listroles: roleslist)
		{
			String roleId = listroles.getRoleId();
			LinkedHashMap<String,String>Tblformactiondsplname =tblFormService.getActionandDsplayLbl(listroles.getAllowed_Forms());
			Iterator<String> formNames = Tblformactiondsplname.keySet().iterator();
			while(formNames.hasNext())
			{
				formsNamelist.add(Tblformactiondsplname.get(formNames.next()));
			}
			roleIdAllowedformsMap.put(roleId, formsNamelist);
			formsNamelist = new ArrayList<String>();
		}
		return roleIdAllowedformsMap;
	}
	
	public LinkedHashMap<String,String> getroleIdRoleNameMap() 
	{
		
		LinkedHashMap<String,String>roleIdRoleNameMap = new LinkedHashMap<String,String>();
		roleIdRoleNameMap.put("NA","Select User Role");
		List<TblRole>roleslist = tblRoleService.getRoles();
		for(TblRole listroles: roleslist)
		{
			roleIdRoleNameMap.put(listroles.getRoleId(), listroles.getRole());
		}
		return roleIdRoleNameMap;
	}
	
	public LinkedHashMap<String,String> getroleIdAllowedFormsMap() 
	{
		
		LinkedHashMap<String,String>roleIdRoleNameMap = new LinkedHashMap<String,String>();
		roleIdRoleNameMap.put("NA","Select User Role");
		List<TblRole>roleslist = tblRoleService.getRoles();
		for(TblRole listroles: roleslist)
		{
			roleIdRoleNameMap.put(listroles.getRoleId(), listroles.getAllowed_Forms());
		}
		return roleIdRoleNameMap;
	}
	
	public ArrayList<String> getFormNamebyIds(String allowedform) 
	{
		
		LinkedHashMap<String,String>Tblformactiondsplname = new LinkedHashMap<String,String>();
		Tblformactiondsplname = tblFormService.getActionandDsplayLbl(allowedform);
		ArrayList<String> formsNamelist = new ArrayList<String>();
		Iterator <String> itr = Tblformactiondsplname.keySet().iterator();
		while(itr.hasNext())
		{
			formsNamelist.add(Tblformactiondsplname.get(itr.next()));
		}
		return formsNamelist;
	}
	
	@PostMapping(value = "/mapUserwithSelectedRole")
	public ModelAndView mapUserwithSelectedRole(
	   @ModelAttribute("UsrToRoleMappingModel") @Validated TblRole tblrole, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("userName") String userName,
	   @RequestParam("userRole") String userRoleId,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		String loginUser = (String) session.getAttribute("userEmaiId");
		userName = userName.substring(userName.indexOf("$")+1);
		System.out.println("userName :"+userName);
		System.out.println("userRole :"+userRoleId);
		LinkedHashMap<String,String>userNameRoleIdMap =new LinkedHashMap<String,String>();
		LinkedHashMap<String,ArrayList<String>>roleIdAllowedformsMap = getroleIdAllowedformsMap();
		LinkedHashMap<String,String>roleIdRoleNameMap = getroleIdRoleNameMap();
		for(TblUsers r:getUserNameandRoleList(loginUser))
		{
			userNameRoleIdMap.put((r.getRoleId()==null?"":r.getRoleId())+"$"+r.getid(),r.getUserName());
		}
		
		ArrayList<String>displayformslist= new ArrayList<String>();
		String displayforms ="",allowedFormsIds ="";
		for(String str:roleIdAllowedformsMap.get(userRoleId))
		{
			displayforms+=str.substring(str.indexOf("id")+1)+"\r\n,";
			displayformslist.add(str.substring(str.indexOf("id")+1));
		}
		displayforms =displayforms.substring(0, displayforms.length()-1);
		System.out.println("displayforms :"+displayforms);
		
		allowedFormsIds = getroleIdAllowedFormsMap().get(userRoleId);
		
		String verifydisplaystring ="";
		if(!userName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Selected User Name-"+userName;
		}
		if(!userRoleId.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Selected User Role-"+roleIdRoleNameMap.get(userRoleId);
		}
		if(!displayforms.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Allowed Forms-"+displayforms;
		}
		//
		String descString  =  "Role Assigned  : "+userRoleId;
		
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\"= '"+session.getAttribute("userEmaiId")+"', \"Created_Date\"= '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', \"Creator_Ip_Address\"= '"+(String)session.getAttribute("ipaddress")+"',\"Usr_Active_Status\" = 'Y',\"Role_id\"='"+userRoleId+"',\"Current_Session_Id\"= '"+session.getId().replace(".undefined","")+"',\"Allowed_Forms\"= '"+allowedFormsIds+"' WHERE \"User_Name\"= '"+userName+"'";
		
		//"INSERT INTO loc.\"Tbl_Role\"(\"Role\", \"Role_Description\", \"Creator\",\"Creator_Date\", \"Allowed_Forms\", \"Creator_Ip_Address\", \"Active\") VALUES ('"+role+"', '"+roleDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', '"+AllowedformIds+"', '"+(String)session.getAttribute("ipaddress")+"','E')";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage(userName +" User With Role Name "+roleIdRoleNameMap.get(userRoleId)+" Assigned by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("USER TO ROLE MAP");//task_subject
		tblApprovalWorks.setActivity("ROLE MAP");//activity
		tblApprovalWorks.setModuleName("ROLE MAP FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"User_Name\" = '"+userName+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		
		
		System.out.println("userNameRoleIdMap :"+userNameRoleIdMap);
		System.out.println("roleIdRoleNameMap :"+roleIdRoleNameMap);
		System.out.println("roleIdAllowedformsMap :"+roleIdAllowedformsMap);
		
		model.addAttribute("Tblformactiondsplname", getFormActionMap((String) session.getAttribute("allowedform")));
		model.addAttribute("userNameRoleIdMap", userNameRoleIdMap);
		model.addAttribute("roleIdRoleNameMap", roleIdRoleNameMap);
		model.addAttribute("roleIdAllowedformsMap", roleIdAllowedformsMap);
		model.addAttribute("Done", "done");
		model.addAttribute("Message","User have been Mapped with Selected Role and Queued for Approval");
		return new ModelAndView("/uam/User_Role_Mapping");
	}
	
}


