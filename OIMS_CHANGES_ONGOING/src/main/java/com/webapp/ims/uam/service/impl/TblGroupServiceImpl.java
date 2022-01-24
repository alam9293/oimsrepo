package com.webapp.ims.uam.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.DeptApplicantDetailsRepository;
import com.webapp.ims.uam.model.TblGroup;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.repository.TblGroupServiceRepository;
import com.webapp.ims.uam.repository.TblRoleServiceRepository;
import com.webapp.ims.uam.service.TblGroupService;
import com.webapp.ims.uam.service.TblRoleService;

@Service
@Transactional
public class TblGroupServiceImpl implements TblGroupService {
	
	
	@Autowired
	TblGroupServiceRepository tblgroupServiceRepository;

	@Autowired
	EntityManager em;
	
	@Override
	public List<TblGroup> getGroups() {
		return tblgroupServiceRepository.findAll();
	}


	@Override
	public TblGroup getGroupNamebyGroupId(String groupId) {
		
		 return tblgroupServiceRepository.getGroupNamebyGroupId(groupId);
	}
	
	@Override
	public List<TblGroup> getEnableGroups() {
		// TODO Auto-generated method stub
		return tblgroupServiceRepository.getEnableGroups();
	}


	@Override
	public List<TblGroup> getDisableGroups() {
		// TODO Auto-generated method stub
		return tblgroupServiceRepository.getDisableGroups();
	}
	
	@Override
	public int executeNativeQuery(String taskquery) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(taskquery).executeUpdate();
	}
}
