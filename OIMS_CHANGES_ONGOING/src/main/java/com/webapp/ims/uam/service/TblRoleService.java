package com.webapp.ims.uam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.model.TblUsers;

@Service
public interface TblRoleService {
	
	public List<TblRole> getRoles();
	
	public String getRoleNamebyRoleId(String roleId);

	public List<TblRole> getEnableRoles();

	public List<TblRole> getDisableRoles();

	public int executeNativeQuery(String taskquery);

}