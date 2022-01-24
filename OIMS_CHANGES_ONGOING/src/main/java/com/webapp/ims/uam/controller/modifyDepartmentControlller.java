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

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class modifyDepartmentControlller 
{
	private final Logger logger = LoggerFactory.getLogger(modifyDepartmentControlller.class);
	
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	private TblFormService tblFormService;
	
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamedeptIDandIdMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		formNamedeptIDandIdMap = tblFormService.getformNameDeptCodeMap();
		return formNamedeptIDandIdMap;
	}
	public List<TblDepartment> getenblDepartmentlist() 
	{
		return tblDepartmentService.getEnableDepartmentName();
	}
	public List<TblDepartment> getdsblDepartmentlist() 
	{
		return tblDepartmentService.getDisableDepartmentName();
	}
	
	@PostMapping(value = "/enblDepartment")
	public ModelAndView EnableDepartment(
	   @ModelAttribute("enbldsbldeptmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
        TblDepartment obj = tblDepartmentService.getDepartmentbyId(tblDepartment.getDeptId().split("seprator")[0].substring(1));
        System.out.println("DepartmentId :"+tblDepartment.getDeptId().split("seprator")[0].substring(1));
		String DepartmentId = obj.getDeptId();
		String DepartmentName = obj.getDeptName();
		String Departmentdesc = obj.getDeptDesc();
		String verifydisplaystring ="";
		if(!DepartmentName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "DepartmentName-"+DepartmentName;
		}
		if(!Departmentdesc.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Department Description-"+Departmentdesc;
		}
		
		System.out.println("DepartmentId :"+DepartmentId);
		System.out.println("DepartmentName :"+DepartmentName);
		System.out.println("Departmentdesc :"+Departmentdesc);
		
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Department\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'E' WHERE \"Dept_Id\" ='"+DepartmentId+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("DepartmentName "+DepartmentName+" Enabled by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ENABLE DEPARTMENT");//task_subject
		tblApprovalWorks.setActivity("ENABLE DEPARTMENT");//activity
		tblApprovalWorks.setModuleName("ENABLE DEPARTMENT FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Dept_Id\" = '"+DepartmentId+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("modifyDepartmentlist", getenblDepartmentlist());
		model.addAttribute("dsblDepartmentlist", getdsblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Department have been Enabled and Queued for Approval");
		return new ModelAndView("/uam/modifyDepartment");
	}
	
	@PostMapping(value = "/dsblDepartment")
	public ModelAndView DisableDepartment(
	   @ModelAttribute("enbldsbldeptmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		
		TblDepartment obj = tblDepartmentService.getDepartmentbyId(tblDepartment.getDeptId().split("seprator")[0]);
		System.out.println("DepartmentId :"+tblDepartment.getDeptId().split("seprator")[0]);
		String DepartmentId = obj.getDeptId();
		String DepartmentName = obj.getDeptName();
		String Departmentdesc = obj.getDeptDesc();
		String verifydisplaystring ="";
		if(!DepartmentName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "DepartmentName-"+DepartmentName;
		}
		if(!Departmentdesc.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Department Description-"+Departmentdesc;
		}
		
		System.out.println("DepartmentId :"+DepartmentId);
		System.out.println("DepartmentName :"+DepartmentName);
		System.out.println("Departmentdesc :"+Departmentdesc);
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Department\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'D' WHERE \"Dept_Id\" ='"+DepartmentId+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("DepartmentName "+DepartmentName+" Disabled by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ENABLE DEPARTMENT");//task_subject
		tblApprovalWorks.setActivity("ENABLE DEPARTMENT");//activity
		tblApprovalWorks.setModuleName("ENABLE DEPARTMENT FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Dept_Id\" = '"+DepartmentId+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("modifyDepartmentlist", getenblDepartmentlist());
		model.addAttribute("dsblDepartmentlist", getdsblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Department have been Disabled and Queued for Approval");
		return new ModelAndView("/uam/modifyDepartment");
	}
	
	@PostMapping(value = "/modifyDepartment")
	public ModelAndView ModifyDepartment(
	   @ModelAttribute("enbldsbldeptmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("modifydeptName") String modifydeptName,	   
	   @RequestParam("modifydeptDesc") String modifydeptDesc,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		
		System.out.println("DepartmentId :"+tblDepartment.getDeptId().split("seprator")[0].replace(",", ""));
		TblDepartment obj = tblDepartmentService.getDepartmentbyId(tblDepartment.getDeptId().split("seprator")[0].replace(",", ""));
		
		String DepartmentId = obj.getDeptId();
		String oldDepartmentName = obj.getDeptName();
		String oldDepartmentdesc = obj.getDeptDesc();
		String newDepartmentName = modifydeptName;
		String newDepartmentdesc = modifydeptDesc;
		
		if(oldDepartmentName.equalsIgnoreCase(newDepartmentName)&&oldDepartmentdesc.equalsIgnoreCase(newDepartmentdesc))
		{
			model.addAttribute("modifyDepartmentlist", getenblDepartmentlist());
			model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
			model.addAttribute("dsblDepartmentlist", getdsblDepartmentlist());
			model.addAttribute("Done", "done");
			model.addAttribute("Message", "Please Modify Some Detail");
			return new ModelAndView("/uam/modifyDepartment");
			
		}
		
		String verifydisplaystring ="";
		if(!oldDepartmentName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "DepartmentName-"+oldDepartmentName;
		}
		if(!oldDepartmentdesc.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Department Description-"+oldDepartmentdesc;
		}
		if(!oldDepartmentName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|newDepartmentName-"+newDepartmentName;
		}
		if(!oldDepartmentdesc.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|newDepartment Description-"+newDepartmentdesc;
		}
		
		System.out.println("DepartmentId :"+DepartmentId);
		System.out.println("DepartmentName :"+oldDepartmentName);
		System.out.println("Departmentdesc :"+oldDepartmentdesc);
		System.out.println("DepartmentName :"+newDepartmentName);
		System.out.println("Departmentdesc :"+newDepartmentdesc);
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Department\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"',"+(newDepartmentName.equalsIgnoreCase(oldDepartmentName)?"\"Dept_Name\"= \"Dept_Name\",":("\"Dept_Name\" = '"+newDepartmentName+"', ")) +(newDepartmentdesc.equalsIgnoreCase(oldDepartmentdesc)?"\"Dept_Desc\"=\"Dept_Desc\"":("\"Dept_Desc\" = '"+newDepartmentdesc+"'")) + " WHERE \"Dept_Id\" ='"+DepartmentId+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Old DepartmentName "+oldDepartmentName+" Modified to "+newDepartmentName+" Disabled by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("MODIFY DEPARTMENT");//task_subject
		tblApprovalWorks.setActivity("MODIFY DEPARTMENT");//activity
		tblApprovalWorks.setModuleName("MODIFY DEPARTMENT FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Dept_Id\" = '"+DepartmentId+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("modifyDepartmentlist", getenblDepartmentlist());
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		model.addAttribute("dsblDepartmentlist", getdsblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Department have been Modified and Queued for Approval");
		return new ModelAndView("/uam/modifyDepartment");
	}
	
}


