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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.LoadFormsNameRequestParam;
import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblForm;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class ModifyFormsControlller 
{
	private final Logger logger = LoggerFactory.getLogger(ModifyFormsControlller.class);
	
	
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
	
	public List<TblForm> getenblFormlist() 
	{
		return tblFormService.getEnableForms();
	}
	
	@PostMapping(value = "/enblForm")
	public ModelAndView EnableDepartment(
	  @ModelAttribute("enbldsblformmodel") @Validated TblForm tblForm, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("dsblformactionName") String actionName,
	   @RequestParam("dsblformdisplayName") String formDsplName
	)  
	{
		
		System.out.println("Action Name :"+actionName);
		System.out.println("DisplayName :"+formDsplName);
		
		String verifydisplaystring ="";
		if(!actionName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Action Name-"+actionName;
		}
		if(!formDsplName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Form DisplayName-"+formDsplName;
		}
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Form\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'E' WHERE \"Action_Name\" ='./"+actionName+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Form "+formDsplName+" Enabled by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ENABLE FORM");//task_subject
		tblApprovalWorks.setActivity("ENABLE FORM");//activity
		tblApprovalWorks.setModuleName("ENABLE FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Action_Name\" = './"+actionName+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Form have been Enabled and Queued for Approval");
		return new ModelAndView("/uam/modifyForms");
	}
	
	@PostMapping(value = "/dsblForm")
	public ModelAndView dsblForm(
	   @ModelAttribute("enbldsblformmodel") @Validated TblForm tblform, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("enblformactionName") String actionName,
	   @RequestParam("enblformdisplayName") String formDsplName
	)  
	{
		
		System.out.println("Action Name :"+actionName);
		System.out.println("DisplayName :"+formDsplName);
		
		String verifydisplaystring ="";
		if(!actionName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Action Name-"+actionName;
		}
		if(!formDsplName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Form DisplayName-"+formDsplName;
		}
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		
		String taskquery = "UPDATE loc.\"Tbl_Form\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'D' WHERE \"Action_Name\" ='./"+actionName+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Form "+formDsplName+" Disabled by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("DISABLE FORM");//task_subject
		tblApprovalWorks.setActivity("DISABLE FORM");//activity
		tblApprovalWorks.setModuleName("DISABLE FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Action_Name\" = '"+actionName+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Form have been Disabled and Queued for Approval");
		return new ModelAndView("/uam/modifyForms");
	}
	
	@PostMapping(value = "/updateForm")
	public ModelAndView ModifyDepartment(
	   @ModelAttribute("enbldsblformmodel") @Validated TblForm tblform, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("modifyformactionName") String newactionName,
	   @RequestParam("modifyformdisplayName") String newformDsplName,
	   @RequestParam("id") String id
	)  
	{
		System.out.println("id :"+id);
		System.out.println("Action Name :"+newactionName);
		System.out.println("DisplayName :"+newformDsplName);
		TblForm obj = tblFormService.getFormsbyId(id);
		String oldactionName   = obj.getactionName().replace("./", "");
		String oldformDsplName = obj.getdisplayName();
		if(newactionName.equalsIgnoreCase(oldactionName)&&newformDsplName.equalsIgnoreCase(oldformDsplName))
		{
			model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
			model.addAttribute("Done", "done");
			model.addAttribute("Action", "M");
			model.addAttribute("Message", "Please Modify Some Detail");
			return new ModelAndView("/uam/modifyForms");
		}
		
		String verifydisplaystring ="";
		if(!newactionName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "Action Name-"+newactionName;
		}
		if(!newformDsplName.equalsIgnoreCase(""))
		{
			verifydisplaystring += "|Form DisplayName-"+newformDsplName;
		}
		//
		String descString  =  "";
		verifydisplaystring += "#"+descString;
		                                                                                                                                                                                                                                                                     //"+(newDepartmentName.equalsIgnoreCase(oldDepartmentName)?"\"Dept_Name\"= \"Dept_Name\",":("\"Dept_Name\" = '"+newDepartmentName+"', ")) +(newDepartmentdesc.equalsIgnoreCase(oldDepartmentdesc)?"\"Dept_Desc\"=\"Dept_Desc\"":("\"Dept_Desc\" = '"+newDepartmentdesc+"'")) + "
		String taskquery = "UPDATE loc.\"Tbl_Form\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"',"+(newactionName.equalsIgnoreCase(oldactionName)?"\"Action_Name\"= \"Action_Name\",":("\"Action_Name\" = '"+newactionName+"', ")) +(newformDsplName.equalsIgnoreCase(oldformDsplName)?"\"Display_Name\"=\"Display_Name\"":("\"Display_Name\" = '"+newformDsplName+"'")) + " WHERE \"Id\" ='"+id+"'";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Form "+newformDsplName+" Modified by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("UPDATE FORM");//task_subject
		tblApprovalWorks.setActivity("UPDATE FORM");//activity
		tblApprovalWorks.setModuleName("UPDATE FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Id\" = '"+id+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("enblDepartmentlist", getenblDepartmentlist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Form have been Modified and Queued for Approval");
		return new ModelAndView("/uam/modifyForms");
	}
	
	@RequestMapping(value = "/loadFormsName", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody LoadFormsNameRequestParam loadFormsName(@RequestBody LoadFormsNameRequestParam obj)
	{
		
		String departmentID   = obj.getDepartmentID();
		String FormdropdownId = obj.getFormdropdownId();
		System.out.println("loadFormsName Action Called..........."+departmentID+"   "+FormdropdownId);
		List<TblForm>formNamelist = tblFormService.getFormsbyDeptId(departmentID,FormdropdownId);
		System.out.println(formNamelist.size());
		if(formNamelist.size()>0)
		{	
			System.out.println(formNamelist.size());
		}
		LoadFormsNameRequestParam resobj = new LoadFormsNameRequestParam();
		resobj.setDepartmentID(departmentID);
		resobj.setFormdropdownId(FormdropdownId);
		resobj.setFormNamelist(formNamelist);
		resobj.setStatus("Success");
		return resobj;
	}

	
}


