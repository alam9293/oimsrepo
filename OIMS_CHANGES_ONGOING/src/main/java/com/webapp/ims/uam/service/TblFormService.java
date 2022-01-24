package com.webapp.ims.uam.service;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblForm;

@Service
public interface TblFormService {
	
	
	public LinkedHashMap<String,String> getActionandDsplayLbl(String allowedform);
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap();
	public BigInteger getSequenceId();
	public String getFormsbyActionName(String actionname);
	public List<TblForm> getFormsbyDeptId(String deptId,String FormdropdownId);
	public List<TblForm> getEnableForms();
	public List<TblForm> getDisableForms();
	public TblForm getFormsbyId(String id);
	

}