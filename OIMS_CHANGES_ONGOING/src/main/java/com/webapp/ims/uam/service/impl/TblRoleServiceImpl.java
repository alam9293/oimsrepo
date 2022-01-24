package com.webapp.ims.uam.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.DeptApplicantDetailsRepository;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.repository.TblRoleServiceRepository;
import com.webapp.ims.uam.service.TblRoleService;

@Service
@Transactional
public class TblRoleServiceImpl implements TblRoleService {
	
	
	@Autowired
	TblRoleServiceRepository tblRoleServiceRepository;

	@Autowired
	EntityManager em;
	
	@Override
	public List<TblRole> getRoles() {
		return tblRoleServiceRepository.findAll();
	}


	@Override
	public String getRoleNamebyRoleId(String roleId) {
		
		 return tblRoleServiceRepository.getRoleNamebyRoleId(roleId).getRole();
	}


	@Override
	public List<TblRole> getEnableRoles() {
		// TODO Auto-generated method stub
		return tblRoleServiceRepository.getEnableRoles();
	}


	@Override
	public List<TblRole> getDisableRoles() {
		// TODO Auto-generated method stub
		return tblRoleServiceRepository.getDisableRoles();
	}
	
	@Override
	public int executeNativeQuery(String taskquery) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(taskquery).executeUpdate();
	}
}
