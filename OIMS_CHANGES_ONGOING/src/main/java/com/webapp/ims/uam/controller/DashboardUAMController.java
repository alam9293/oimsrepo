package com.webapp.ims.uam.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblForm;
import com.webapp.ims.uam.model.TblGroup;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblGroupService;
import com.webapp.ims.uam.service.TblPendingWorksService;
import com.webapp.ims.uam.service.TblRoleService;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class DashboardUAMController {

	private final Logger logger = LoggerFactory.getLogger(DashboardUAMController.class);
	
	@Autowired
	private TblFormService tblFormService;
	
	@Autowired
	TblDepartmentService tblDepartmentService;

	@Autowired
	TblRoleService tblRoleService;
	
	@Autowired
	TblGroupService tblgroupService;
	
	@Autowired
	TblUsersService tblUsersService;
	
	@Autowired
	TblRoleService TblroleService;
	
	@Autowired
	TblGroupService TblgroupService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@ModelAttribute("DeaprtmentList")
	public List<TblDepartment> getDepartmentList() 
	{
	      List<TblDepartment> DeaprtmentList = tblDepartmentService.getEnableDepartmentName();
	      return DeaprtmentList;
	}
	
	public List<TblUsers> getUsersList(String loginUser) 
	{
	      List<TblUsers> RoleIdsList = tblUsersService.getEnableUserName(loginUser);
	      return RoleIdsList;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		
		LinkedHashMap<String,LinkedHashMap<String,String>>Tblformactiondsplname = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		Tblformactiondsplname = tblFormService.getformNameDeptCodeMap();
		return Tblformactiondsplname;
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
		
		
		//roleIdAllowedformsMap = tblFormService.getformNameDeptCodeMap();
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
	
	public LinkedHashMap<String,String> getgroupIdGroupNameMap() 
	{
		
		LinkedHashMap<String,String>groupIdGroupNameMap = new LinkedHashMap<String,String>();
		groupIdGroupNameMap.put("NA","Select User Group");
		List<TblGroup>groupslist = tblgroupService.getGroups();
		for(TblGroup listgroupes: groupslist)
		{
			groupIdGroupNameMap.put(listgroupes.getGroupId(), listgroupes.getGroup());
		}
		return groupIdGroupNameMap;
	}
	
	public List<TblApprovalWorks> getListofDataTobeApproved(HttpSession session)
	{
		System.out.println("(String)(session.getAttribute(\"userEmaiId\"))"+(String)(session.getAttribute("userEmaiId")));
		List<TblApprovalWorks>datalist = tblPendingWorksService.getListofPendingDataTobeApproved((String)(session.getAttribute("userEmaiId")));
		return datalist;
	}
	
	public List<TblDepartment> getenblDepartmentlist() 
	{
		return tblDepartmentService.getEnableDepartmentName();
	}
	
	public List<TblDepartment> getdsblDepartmentlist() 
	{
		return tblDepartmentService.getDisableDepartmentName();
	}
	
	public List<TblUsers> getenblUserlist(String loginUser) 
	{
		return tblUsersService.getEnableUserName(loginUser);
	}
	
	public List<TblUsers> getdisableblUserlist(String loginUser) 
	{
		return tblUsersService.getDisableUserName(loginUser);
	}
	
	public List<TblUsers> getLockUserlist(String loginUser) 
	{
		return tblUsersService.getLockUserName(loginUser);
	}
	
	public List<TblRole> getEnableRoleslist() 
	{
		return TblroleService.getEnableRoles();
	}
	
	public List<TblRole> getDisableRoleslist() 
	{
		return TblroleService.getDisableRoles();
	}
	
	public List<TblGroup> getEnableGroupslist() 
	{
		return TblgroupService.getEnableGroups();
	}
	
	public List<TblGroup> getDisableGroupslist() 
	{
		return TblgroupService.getDisableGroups();
	}
	
	@RequestMapping(value = "/DashboardUAM", method = RequestMethod.GET)
	public ModelAndView DashboardUAM(Model model, HttpSession session) 
	{
		logger.debug("Render DashboardUAM");

		String userid = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		model.addAttribute("userName", userName);
		return new ModelAndView("/uam/DashboardUAM");
	}
	
	@GetMapping(value = "/AddRole")
	public ModelAndView AddRole(@ModelAttribute("userRolemodel") @Validated TblDepartment tblDepartment, BindingResult result,Model model, HttpSession session)
	{
		LinkedHashMap<String,String>DepartmentMap =new LinkedHashMap<String,String>();
		List<String>DeptCodeList =new ArrayList<String>();
		LinkedHashMap<String,LinkedHashMap<String,String>>formNameDeptCodeMap = getformNameDeptCodeMap();
		DepartmentMap.put("NA","Select Department");
		for(TblDepartment l:getDepartmentList())
		{
			DepartmentMap.put(l.getDeptId(), l.getDeptName());
		}
		for(TblDepartment l:getDepartmentList())
		{
			DeptCodeList.add(l.getDeptId());
		}
		System.out.println("DepartmentList :"+getDepartmentList());
		System.out.println("DeptCodeList :"+DeptCodeList);
		System.out.println("formNameDeptCodeMap :"+formNameDeptCodeMap);
		
		model.addAttribute("DepartmentMap", DepartmentMap);
		model.addAttribute("DeptCodeList", DeptCodeList);
		model.addAttribute("formNameDeptCodeMap", formNameDeptCodeMap);
		return new ModelAndView("/uam/Add_Role");
	}
	
	@GetMapping(value = "/AddGroup")
	public ModelAndView AddGroup(@ModelAttribute("userGroupmodel") @Validated TblDepartment tblDepartment, BindingResult result,Model model, HttpSession session)
	{
		System.out.println(model.containsAttribute("Tblformactiondsplname"));
		return new ModelAndView("/uam/Add_Group");
	}
	
	@GetMapping(value = "/AddUser")
	public ModelAndView AddUser(@ModelAttribute("userDtlmodel") @Validated TblDepartment tblDepartment, BindingResult result,Model model, HttpSession session)
	{
		List<String>DepartmentList =new ArrayList<String>();
		DepartmentList.add("Select Department");
		for(TblDepartment l:getDepartmentList())
		{
			DepartmentList.add(l.getDeptName());
		}
		model.addAttribute("DepartmentList", DepartmentList);
		ModelAndView mdlview = new ModelAndView("/uam/Add_User");
		return mdlview;
	}
	
	@GetMapping(value = "/UsrToRoleMapping")
	public ModelAndView UsrToRoleMapping(@ModelAttribute("UsrToRoleMappingModel") @Validated TblUsers tblUser, BindingResult result,Model model, HttpSession session)
	  
	{
		String loginUser = (String) session.getAttribute("userEmaiId");
		LinkedHashMap<String,String>userNameRoleIdMap =new LinkedHashMap<String,String>();
		LinkedHashMap<String,ArrayList<String>>roleIdAllowedformsMap = getroleIdAllowedformsMap();
		LinkedHashMap<String,String>roleIdRoleNameMap = getroleIdRoleNameMap();
		for(TblUsers r:getUsersList(loginUser))
		{
			userNameRoleIdMap.put((r.getRoleId()==null?"":r.getRoleId())+"$"+r.getid(),r.getUserName());
		}
		System.out.println("userNameRoleIdMap :"+userNameRoleIdMap);
		System.out.println("roleIdRoleNameMap :"+roleIdRoleNameMap);
		System.out.println("roleIdAllowedformsMap :"+roleIdAllowedformsMap);
		model.addAttribute("userNameRoleIdMap", userNameRoleIdMap);
		model.addAttribute("roleIdRoleNameMap", roleIdRoleNameMap);
		model.addAttribute("roleIdAllowedformsMap", roleIdAllowedformsMap);
		return new ModelAndView("/uam/User_Role_Mapping");
	}
	
	
	@GetMapping(value = "/UsrToGrpMapping")
	public ModelAndView UsrToGrpMapping(@ModelAttribute("UsrToGroupMappingModel") @Validated TblUsers tblUser, BindingResult result,Model model, HttpSession session)
	  
	{
		String loginUser = (String) session.getAttribute("userEmaiId");
		LinkedHashMap<String,String>userNameGroupIdMap =new LinkedHashMap<String,String>();
		LinkedHashMap<String,String>groupIdGroupNameMap = getgroupIdGroupNameMap();
		for(TblUsers r:getUsersList(loginUser))
		{
			userNameGroupIdMap.put((r.getGroupId()==null?"":r.getGroupId())+"$"+r.getid(),r.getUserName());
		}
		System.out.println("userNameGroupIdMap :"+userNameGroupIdMap);
		System.out.println("grouIdGroupNameMap :"+groupIdGroupNameMap);
		model.addAttribute("userNameGroupIdMap", userNameGroupIdMap);
		model.addAttribute("grouIdGroupNameMap", groupIdGroupNameMap);
		return new ModelAndView("/uam/User_Group_Mapping");
	}

	
	@GetMapping(value = "/PendingworksApproval")
	public ModelAndView PendingworksApproval(@ModelAttribute("datalistmodel") @Validated TblApprovalWorks tblApprovalWorks, BindingResult result, Model model, HttpSession session)
	{
		model.addAttribute("ListofDataTobeApproved", getListofDataTobeApproved(session));
		return new ModelAndView("/uam/Unsettled_Works_Approval");
	}
     
	@GetMapping(value = "/addDepartment")
	public ModelAndView addDepartment(Model model, HttpSession session)
	{
		return new ModelAndView("/uam/Add_Department");
	}
    
	@GetMapping(value = "/modifyDepartment")
	public ModelAndView modifyDepartment(@ModelAttribute("enbldsbldeptmodel") @Validated TblDepartment tblDepartment, BindingResult result,Model model, HttpSession session)
	{
		model.addAttribute("modifyDepartmentlist", getenblDepartmentlist());
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		model.addAttribute("dsblDepartmentlist", getdsblDepartmentlist());
		
		return new ModelAndView("/uam/modifyDepartment");
	}
	
	@GetMapping(value = "/ModfUser")
	public ModelAndView ModfUser(@ModelAttribute("enbldsblusermodel") @Validated TblUsers tblUser, BindingResult result,Model model, HttpSession session)
	{
		String loginUser = (String) session.getAttribute("userEmaiId");
		model.addAttribute("enblUserlist", getenblUserlist(loginUser));
		model.addAttribute("dsblUserlist", getdisableblUserlist(loginUser));
		model.addAttribute("lockUserlist", getLockUserlist(loginUser));
		model.addAttribute("dltUserlist", getenblUserlist(loginUser));
		return new ModelAndView("/uam/modifyUser");
	}
	
	@GetMapping(value = "/ModfRole")
	public ModelAndView ModfRole(@ModelAttribute("enbldsblrolemodel") @Validated TblRole tblRole, BindingResult result,Model model, HttpSession session)
	{
		model.addAttribute("enblRolelist", getEnableRoleslist());
		model.addAttribute("dsblRolelist", getDisableRoleslist());
		return new ModelAndView("/uam/modifyRole");
	}
	
	@GetMapping(value = "/ModfGroup")
	public ModelAndView ModfGroup(@ModelAttribute("enbldsblgroupmodel") @Validated TblGroup tblgroup, BindingResult result,Model model, HttpSession session)
	{
		model.addAttribute("enblGrouplist", getEnableGroupslist());
		model.addAttribute("dsblGrouplist", getDisableGroupslist());
		return new ModelAndView("/uam/modifyGroup");
	}
	
	@GetMapping(value = "/modifyForm")
	public ModelAndView modifyForm
	(
	   @ModelAttribute("enbldsblformmodel") @Validated TblForm tblform, BindingResult result,
	   Model model, HttpSession session
	)
	{
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		
		return new ModelAndView("/uam/modifyForms");
	}

	@GetMapping(value = "/addForm")
	public ModelAndView Add_Forms(@ModelAttribute("addFormmodel") @Validated TblDepartment tblDepartment, BindingResult result,Model model, HttpSession session)
	{
		LinkedHashMap<String,String>DepartmentMap =new LinkedHashMap<String,String>();
		List<String>DeptCodeList =new ArrayList<String>();
		LinkedHashMap<String,LinkedHashMap<String,String>>formNameDeptCodeMap = getformNameDeptCodeMap();
		DepartmentMap.put("NA","Select Department");
		for(TblDepartment l:getDepartmentList())
		{
			DepartmentMap.put(l.getDeptId(), l.getDeptName());
		}
		for(TblDepartment l:getDepartmentList())
		{
			DeptCodeList.add(l.getDeptId());
		}
		System.out.println("DepartmentList :"+getDepartmentList());
		System.out.println("DeptCodeList :"+DeptCodeList);
		System.out.println("formNameDeptCodeMap :"+formNameDeptCodeMap);
		
		model.addAttribute("DepartmentMap", DepartmentMap);
		model.addAttribute("DeptCodeList", DeptCodeList);
		model.addAttribute("formNameDeptCodeMap", formNameDeptCodeMap);
		return new ModelAndView("/uam/Add_Forms");
	}

}
