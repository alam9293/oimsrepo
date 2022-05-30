package com.cdgtaxi.ibs.acl.business;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.business.GenericBusiness;

public interface RoleBusiness extends GenericBusiness {
	public List<SatbRole> getActiveRoles();
	public boolean isNameUsed(String name);
	public SatbRole createNewRole(String name, String createdBy);
	public List<SatbRole> searchRole(String name, String status);
	public SatbRole get(Long roleId);
	public void deactivateRole(SatbRole role, String updatedBy);
	public void activateRole(SatbRole role, String updatedBy);
	public void updateRole(SatbRole role, List resourceRows, String updatedBy);
	public List<SatbRole> getRoles();
	public boolean isDuplicateRole(String name);
}
