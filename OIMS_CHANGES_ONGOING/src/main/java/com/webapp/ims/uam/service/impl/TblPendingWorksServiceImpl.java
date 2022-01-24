package com.webapp.ims.uam.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.repository.TblPendingWorksServiceRepository;
import com.webapp.ims.uam.service.TblPendingWorksService;

@Service
@Transactional
public class TblPendingWorksServiceImpl implements TblPendingWorksService {
	
	
	@Autowired
	TblPendingWorksServiceRepository tblPendingWorksServiceRepository;
	
	@Autowired
	EntityManager em;

	@Override
	public void saveDatatobeApprove(TblApprovalWorks tblApprovalWorks) 
	{
		tblPendingWorksServiceRepository.save(tblApprovalWorks);// TODO Auto-generated method stub
		
	}

	@Override
	public List<TblApprovalWorks> getListofPendingDataTobeApproved(String creator)
	{
		ArrayList<String> idmsstatsList = new ArrayList<String>();
		idmsstatsList.add("M");
		idmsstatsList.add("F");
		idmsstatsList.add("R");
		idmsstatsList.add("G");
		idmsstatsList.add("A");
		return tblPendingWorksServiceRepository.getListofPendingDataTobeApproved(creator,idmsstatsList);
		//return tblPendingWorksServiceRepository.getListofPendingDataTobeApproved(creator);
		//return tblPendingWorksServiceRepository.findAll();
	}

	public LinkedHashMap<String, String> getAprovalDataMap(long taskId) 
	{
		// TODO Auto-generated method stub
		LinkedHashMap<String, String>dataMap = new LinkedHashMap<String, String>();
		TblApprovalWorks tblApprovalWorks = tblPendingWorksServiceRepository.getRecord(taskId);
		String aproverdisplaystring = "",descstring ="";
		String ap[]  = tblApprovalWorks.getApproverDisplayString().split("#");
		if(ap.length==2){aproverdisplaystring = ap[0];descstring = ap[1];}else{aproverdisplaystring = ap[0];}
		String []displayrow = aproverdisplaystring.split("\\|");
		for(String str:displayrow)
		{
			dataMap.put(str.split("-")[0], str.split("-")[1]);
		}
		return dataMap;
	}

	@Override
	public void approveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession s) 
	{
		// TODO Auto-generated method stub
		String updateapproversql ="";
		String wherecluse = tblApprovalWorks.getWhereClause();
		String taskquery = tblApprovalWorks.getTaskQuery();
		int updatestatus = 0;
		
		System.out.println("wherecluse "+wherecluse);
		System.out.println("taskquery "+taskquery);
		updatestatus+=em.createNativeQuery(taskquery).executeUpdate();
		if(!wherecluse.equalsIgnoreCase("0"))
		{
			if(taskquery.startsWith("INSERT INTO"))
			{
				if((taskquery.substring(12, taskquery.indexOf("("))).contains("\"Tbl_Users\""))
				{
					updateapproversql = "update "+taskquery.substring(12, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"', \"A_SessionId\" = '"+s.getId().replace(".undefined","")+"' "+wherecluse+" ";
				}
				else
				{
					updateapproversql = "update "+taskquery.substring(12, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"' "+wherecluse+" ";
				}
			}
			else if(taskquery.startsWith("update"))
			{
				if((taskquery.substring(7, taskquery.indexOf(" set"))).contains("\"Tbl_Users\""))
				{
					updateapproversql = "update "+taskquery.substring(7, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"', \"A_SessionId\" = '"+s.getId().replace(".undefined","")+"' "+wherecluse+" ";
				}
				else
				{
					updateapproversql = "update "+taskquery.substring(7, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"' "+wherecluse+" ";
				}
			}
			System.out.println("updateapproversql :"+updateapproversql);
			em.createNativeQuery(updateapproversql).executeUpdate();
		}
		if(updatestatus == 0)
		{
			
		}
		else
		{
			//Admin Audit Log
		}
		String query ="update loc.\"Tbl_Approval_Works\" set \"Task_Status\" = 0, \"Task_Approver_Role\" = '"+s.getAttribute("userr")+"', \"Approver\" = '"+s.getAttribute("userEmaiId")+"',\"Approver_Date\" = now()  where \"Task_Id\" ='"+tblApprovalWorks.getTaskId() +"'";
		em.createNativeQuery(query).executeUpdate();
		
	}
	
	@Override
	public void disApproveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession s) 
	{
		// TODO Auto-generated method stub
		String updateapproversql ="";
		String wherecluse = tblApprovalWorks.getWhereClause();
		String taskquery = tblApprovalWorks.getTaskQuery();
		int updatestatus = 0;
		
		System.out.println("wherecluse "+wherecluse);
		System.out.println("taskquery "+taskquery);
		if(!wherecluse.equalsIgnoreCase("0"))
		{
			if(taskquery.startsWith("INSERT INTO"))
			{
				if((taskquery.substring(12, taskquery.indexOf("("))).contains("\"Tbl_Users\""))
				{
					updateapproversql = "update "+taskquery.substring(12, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"', \"A_SessionId\" = '"+s.getId().replace(".undefined","")+"' "+wherecluse+" ";
				}
				else
				{
					updateapproversql = "update "+taskquery.substring(12, taskquery.indexOf("("))+" set \"Approved_By\" = '', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '' "+wherecluse+" ";
				}
			}
			else if(taskquery.startsWith("update"))
			{
				if((taskquery.substring(7, taskquery.indexOf(" set"))).contains("\"Tbl_Users\""))
				{
					updateapproversql = "update "+taskquery.substring(7, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"', \"A_SessionId\" = '"+s.getId().replace(".undefined","")+"' "+wherecluse+" ";
				}
				else
				{
					updateapproversql = "update "+taskquery.substring(7, taskquery.indexOf("("))+" set \"Approved_By\" = '"+s.getAttribute("userEmaiId")+"', \"Approved_Date\" = now(), \"Approver_Ip_Address\" = '"+(String)s.getAttribute("ipaddress")+"' "+wherecluse+" ";
				}
			}
			System.out.println("updateapproversql :"+updateapproversql);
			em.createNativeQuery(updateapproversql).executeUpdate();
		}
		if(updatestatus == 0)
		{
			
		}
		else
		{
			//Admin Audit Log
		}
		String query ="update loc.\"Tbl_Approval_Works\" set \"Task_Status\" = 1, \"Task_Approver_Role\" = '"+s.getAttribute("userr")+"', \"Approver\" = '"+s.getAttribute("userEmaiId")+"',\"Approver_Date\" = now()  where \"Task_Id\" ='"+tblApprovalWorks.getTaskId() +"'";
		em.createNativeQuery(query).executeUpdate();
		
	}

	@Override
	public TblApprovalWorks getApprovalDataDetailsBytaskId(long taskId) {
		
		return tblPendingWorksServiceRepository.getRecord(taskId);
		// TODO Auto-generated method stub
	}
	
}
