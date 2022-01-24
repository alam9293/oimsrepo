/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.math.BigInteger;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblForm;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class AddFormsControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddFormsControlller.class);
	
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	private TblFormService tblFormService;
	
	@ModelAttribute("DeaprtmentList")
	public List<TblDepartment> getDepartmentList() 
	{
	      List<TblDepartment> DeaprtmentList = tblDepartmentService.getEnableDepartmentName();
	      return DeaprtmentList;
	}
	
	public LinkedHashMap<String,String> getFormActionMap(HttpSession session) 
	{
		
		LinkedHashMap<String,String>Tblformactiondsplname = new LinkedHashMap<String,String>();
		Tblformactiondsplname = tblFormService.getActionandDsplayLbl((String) session.getAttribute("allowedform"));
		return Tblformactiondsplname;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamedeptIDandIdMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		formNamedeptIDandIdMap = tblFormService.getformNameDeptCodeMap();
		return formNamedeptIDandIdMap;
	}
	
	@PostMapping(value = "/AddNewForm")
	public ModelAndView AddNewRole(
	   @ModelAttribute("addFormmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("actionName") String actionName,	   
	   @RequestParam("displayName") String displayName,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println("actionName :"+actionName);
		System.out.println("displayName :"+displayName);
		System.out.println("DepartmentId :"+tblDepartment.getDeptId());
		String deptCode = tblDepartment.getDeptId();
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
		
		String verifydisplaystring ="";
		
		if(!actionName.equalsIgnoreCase(""))
		{
			verifydisplaystring +="Action Name-"+actionName;
		}
		if(!displayName.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Display Name-"+displayName;
		}
		verifydisplaystring +="|Department Name-"+DepartmentMap.get(tblDepartment.getDeptId());
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		BigInteger Id = tblFormService.getSequenceId();
		String taskquery = "INSERT INTO loc.\"Tbl_Form\"(\"Id\",\"Action_Name\", \"Display_Name\", \"Created_By\",\"Created_Date\",\"Creator_Ip_Address\", \"Status\",\"Display_Order\",\"Dept_Code\") VALUES ('"+Id+"','./"+actionName+"', '"+displayName+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','"+(String)session.getAttribute("ipaddress")+"','E',(select max(\"Display_Order\") from loc.\"Tbl_Form\" where \"Dept_Code\" ='"+deptCode+"'),'"+deptCode+"')";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Form "+displayName+" added by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ADD NEW FORM");//task_subject
		tblApprovalWorks.setActivity("ADD FORM");//activity
		tblApprovalWorks.setModuleName("ADD FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Id\" = '"+Id+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		
		System.out.println("DepartmentList :"+getDepartmentList());
		System.out.println("DeptCodeList :"+DeptCodeList);
		System.out.println("formNameDeptCodeMap :"+formNameDeptCodeMap);
		
		model.addAttribute("Tblformactiondsplname", getFormActionMap(session));
		model.addAttribute("DepartmentMap", DepartmentMap);
		model.addAttribute("DeptCodeList", DeptCodeList);
		model.addAttribute("formNameDeptCodeMap", formNameDeptCodeMap);
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Form have been Added and Queued for Approval");
		return new ModelAndView("/uam/Add_Forms");
	}
	
	@RequestMapping(value = "/checkActionnameduplicacy", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String checkActionName(@RequestBody String actionname) {
		
		System.out.println("Check checkActionName Called..........."+actionname);
		String ret ="";
		String action = tblFormService.getFormsbyActionName(actionname).replace("./", "");
		System.out.println("ActionName  "+action.replace("./", ""));
		if(action !=null)
		{	
			if(action.equalsIgnoreCase(actionname))
			{
				ret = "duplicate";
			}
			else
			{
				ret = "Success";
			}	
		}
		return ret;
	}
	
}


