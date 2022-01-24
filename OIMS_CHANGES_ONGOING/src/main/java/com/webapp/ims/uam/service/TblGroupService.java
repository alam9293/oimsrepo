package com.webapp.ims.uam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblGroup;

@Service
public interface TblGroupService {
	
	public List<TblGroup> getGroups();
	
	public TblGroup getGroupNamebyGroupId(String groupId);

	public List<TblGroup> getEnableGroups();

	public List<TblGroup> getDisableGroups();

	public int executeNativeQuery(String taskquery);

}