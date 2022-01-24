/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
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
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class AddDepartmentControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddDepartmentControlller.class);
	
	
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
	
	@PostMapping(value = "/AddNewDepartment")
	public ModelAndView AddNewRole(
	   @ModelAttribute("addDepartmentmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("department") String deptname,	   
	   @RequestParam("deptDesc") String deptDesc,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println("Department :"+deptname);
		System.out.println("DeptDesc :"+deptDesc);
		
		String verifydisplaystring ="";
		if(!deptname.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Department-"+deptname.toUpperCase();
		}
		if(!deptDesc.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Department Description-"+deptDesc.toUpperCase();
		}
		
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		BigInteger deptId = tblDepartmentService.getSequenceId();
		
		//INSERT INTO loc."Tbl_Department"("Dept_Id", "Dept_Name", "Dept_Desc", "Created_By", "Created_Date", "Creator_Ip_Address", "Approved_By", "Approved_Date", "Approver_Ip_Address", "Status");
		
		String taskquery = "INSERT INTO loc.\"Tbl_Department\"(\"Dept_Id\",\"Dept_Name\", \"Dept_Desc\", \"Created_By\",\"Created_Date\",\"Creator_Ip_Address\", \"Status\") VALUES ('"+deptId+"','"+deptname+"', '"+deptDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','"+(String)session.getAttribute("ipaddress")+"','E')";
		
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Department  "+deptname+" added by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ADD NEW DEPARTMENT");//task_subject
		tblApprovalWorks.setActivity("ADD DEPARTMENT");//activity
		tblApprovalWorks.setModuleName("ADD DEPARTMENT FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Dept_Id\" = '"+deptId+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("Tblformactiondsplname", getFormActionMap(session));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Department have been Added and Queued for Approval");
		return new ModelAndView("/uam/Add_Department");
	}
	
}


