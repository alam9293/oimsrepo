/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblGroup;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblGroupService;
import com.webapp.ims.uam.service.TblPendingWorksService;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 */
@Controller
public class UsertoGroupMappingControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddGroupControlller.class);
	
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	TblUsersService tblUsersService;
	
	@Autowired
	TblGroupService tblgroupService;
	
	
	public List<TblUsers> getUsersList(String loginUser) 
	{
	      List<TblUsers> GroupIdsList = tblUsersService.getEnableUserName(loginUser);
	      return GroupIdsList;
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
	
	@PostMapping(value = "/mapUserwithSelectedGroup")
	public ModelAndView mapUserwithSelectedGroup(
	   @ModelAttribute("UsrToGroupMappingModel") @Validated TblGroup tblgroup, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("userName") String userName,
	   @RequestParam("userGroup") String userGroupId,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		String loginUser = (String) session.getAttribute("userEmaiId");
		userName = userName.substring(userName.indexOf("$")+1);
		System.out.println("userName :"+userName);
		System.out.println("userGroupId :"+userGroupId);
		
		LinkedHashMap<String,String>userNameGroupIdMap =new LinkedHashMap<String,String>();
		LinkedHashMap<String,String>groupIdGroupNameMap = getgroupIdGroupNameMap();
		for(TblUsers r:getUsersList(loginUser))
		{
			userNameGroupIdMap.put((r.getGroupId()==null?"":r.getGroupId())+"$"+r.getid(),r.getUserName());
		}
		
		
		String verifydisplaystring ="";
		if(!userName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Selected User Name-"+userName;
		}
		if(!userGroupId.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Selected User Group-"+groupIdGroupNameMap.get(userGroupId);
		}
		
		//
		String descString  =  "Group Assigned  : "+userGroupId;
		
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\"= '"+session.getAttribute("userEmaiId")+"', \"Created_Date\"= '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', \"Creator_Ip_Address\"= '"+(String)session.getAttribute("ipaddress")+"',\"Usr_Active_Status\" = 'Y',\"Group_Id\"='"+userGroupId+"',\"Current_Session_Id\"= '"+session.getId().replace(".undefined","")+"' WHERE \"User_Name\"= '"+userName+"'";
		
		//"INSERT INTO loc.\"Tbl_Group\"(\"Group\", \"Group_Description\", \"Creator\",\"Creator_Date\", \"Allowed_Forms\", \"Creator_Ip_Address\", \"Active\") VALUES ('"+Group+"', '"+GroupDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', '"+AllowedformIds+"', '"+(String)session.getAttribute("ipaddress")+"','E')";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage(userName +" User With Group Name "+groupIdGroupNameMap.get(userGroupId)+" Assigned by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("USER TO Group MAP");//task_subject
		tblApprovalWorks.setActivity("Group MAP");//activity
		tblApprovalWorks.setModuleName("Group MAP FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_Group
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"User_Name\" = '"+userName+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		
		
		System.out.println("userNameGroupIdMap :"+userNameGroupIdMap);
		System.out.println("grouIdGroupNameMap :"+groupIdGroupNameMap);
		
		model.addAttribute("userNameGroupIdMap", userNameGroupIdMap);
		model.addAttribute("grouIdGroupNameMap", groupIdGroupNameMap);
		model.addAttribute("Done", "done");
		model.addAttribute("Message","User have been Mapped with Selected Group and Queued for Approval");
		return new ModelAndView("/uam/User_Group_Mapping");
	}
	
}


