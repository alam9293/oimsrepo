package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.dao.GenericDao;

public interface RoleDao extends GenericDao{
	public List<SatbRole> getRoles();
	public List<SatbRole> getActiveRoles();
	public boolean isNameUsed(String name);
	public List<SatbRole> searchRole(String name, String status);
	public SatbRole get(Long roleId);
	public SatbRole getActiveRole(String name);
}
