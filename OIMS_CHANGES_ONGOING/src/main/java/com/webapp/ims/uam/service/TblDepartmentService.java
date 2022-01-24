package com.webapp.ims.uam.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblDepartment;

@Service
public interface TblDepartmentService {

	
	public List<TblDepartment> getEnableDepartmentName();
	public List<TblDepartment> getDisableDepartmentName();
	public BigInteger getSequenceId();
	public TblDepartment getDepartmentbyId(String id);
	
}
