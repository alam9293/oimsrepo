package com.webapp.ims.uam.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.repository.TblDepartmentServiceRepository;
import com.webapp.ims.uam.service.TblDepartmentService;

@Service
@Transactional
public class TblDepartmentServiceImpl implements TblDepartmentService {

	@Autowired
	TblDepartmentServiceRepository tblDepartmentServiceRepository;

	@Autowired
	EntityManager em;
	
	@Override
	public BigInteger getSequenceId() {
		// TODO Auto-generated method stub
		return  (BigInteger) em.createNativeQuery("select nextval('loc.\"Dept_ID_seq\"')").getSingleResult();
	}

	@Override
	public List<TblDepartment> getEnableDepartmentName() {
		return tblDepartmentServiceRepository.getEnableDepartmentName();
	}

	@Override
	public List<TblDepartment> getDisableDepartmentName() {
		return tblDepartmentServiceRepository.getDisableDepartmentName();
	}

	@Override
	public TblDepartment getDepartmentbyId(String id) {
		
		return tblDepartmentServiceRepository.getDepartmentbyId(id);// TODO Auto-generated method stub
		
	}

}
