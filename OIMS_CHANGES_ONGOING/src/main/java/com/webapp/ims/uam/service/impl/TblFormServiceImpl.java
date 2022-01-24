package com.webapp.ims.uam.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.uam.model.TblForm;
import com.webapp.ims.uam.repository.TblFormServiceRepository;
import com.webapp.ims.uam.service.TblFormService;

@Service
@Transactional
public class TblFormServiceImpl implements TblFormService {
	
	@Autowired
	TblFormServiceRepository tblFormServiceRepository;
	
	@Autowired
	EntityManager em;

	@Override
	public LinkedHashMap<String,String> getActionandDsplayLbl(String allowedform)
	{
		LinkedHashMap<String,String>formdataMap = new LinkedHashMap<String,String>();
		
		if(allowedform!=null)
		{
			ArrayList<String>idsList = new ArrayList();
			idsList.addAll(Arrays.asList(allowedform.split(",")));
			
			//System.out.println("allowedform :"+allowedform);
			ArrayList<TblForm>TblFormlist  = tblFormServiceRepository.getActionandLabel(idsList);
			
			for(TblForm tblform:TblFormlist)
			{
				formdataMap.put(tblform.getactionName(), tblform.getdisplayName());
			}
			System.out.println("formdataMap :"+formdataMap);
		}
		return formdataMap;
		
	}
	
	@Override
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap()
	{
		
		//System.out.println("allowedform :"+allowedform);
		List<TblForm>TblFormlist  = tblFormServiceRepository.getEnableForms();
		LinkedHashMap<String,String>formNameIdMap = new LinkedHashMap<String,String>();
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamewithDeptCodeMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		String deptCode ="",olddeptCode="";
		for(TblForm tblform:TblFormlist)
		{
			deptCode =tblform.getDeptCode();
			if(deptCode.equalsIgnoreCase(olddeptCode))
			{
				formNameIdMap.put(tblform.getId(),tblform.getdisplayName());
			}
			else if(!olddeptCode.equalsIgnoreCase(""))
			{
				formNamewithDeptCodeMap.put(olddeptCode, formNameIdMap);
				formNameIdMap = new LinkedHashMap<String,String>();
				formNameIdMap.put(tblform.getId(),tblform.getdisplayName());
				System.out.println("olddeptCode"+olddeptCode);
			}
			olddeptCode =deptCode;
		}
		formNamewithDeptCodeMap.put(olddeptCode, formNameIdMap);
		return formNamewithDeptCodeMap;
		
	}

	@Override
	public BigInteger getSequenceId() {
		// TODO Auto-generated method stub
		return  (BigInteger) em.createNativeQuery("select nextval('loc.tbl_form_id_seq')").getSingleResult();
	}

	@Override
	public String getFormsbyActionName(String actionname) {
		// TODO Auto-generated method stub
		List<Object> dataset =
				em.createNativeQuery("select \"Action_Name\" from loc.\"Tbl_Form\" where \"Action_Name\" = './"+actionname+"'").getResultList();
		System.out.println("dataset.size() :"+dataset.size());
		if(dataset.size()==1)
		return (String)em.createNativeQuery("select \"Action_Name\" from loc.\"Tbl_Form\" where \"Action_Name\" = './"+actionname+"' and \"Status\"= 'E' ").getSingleResult();
		else
			return "";
	}

	public List<TblForm> getFormsbyDeptId(String deptId,String FormdropdownId) {
		
		List<TblForm>TblFormlist  = tblFormServiceRepository.getFormsbyDeptId(deptId,FormdropdownId);
		return TblFormlist;
	}
	
    public List<TblForm> getEnableForms() {
		
		List<TblForm>TblFormlist  = tblFormServiceRepository.getEnableForms();
		return TblFormlist;
	}

    public List<TblForm> getDisableForms() {
	
	     List<TblForm>TblFormlist  = tblFormServiceRepository.getDisableForms();
	     return TblFormlist;
    }
    
    public TblForm getFormsbyId(String id) {
    	
	     List<TblForm>TblFormlist  = tblFormServiceRepository.getFormsbyId(id);
	     if(TblFormlist.size()==1)
	     {
	    	 return TblFormlist.get(0);
	     }
	     return null;
   }


	 

}
