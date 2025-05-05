package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.dao.GenericDao;

public interface ResourceDao extends GenericDao{
	public List retrieveMenu(Long parentRsrcId, List<String> grantedAuthorities);
	public List<SatbResource> getAll();
	public List<SatbResource> get(Long parentRsrcId);
	public SatbResource get(String rsrcName);
	public List<SatbResource> getReportCategories(SatbRole role);
	public List<SatbResource> getReports(SatbResource reportCategory, SatbRole role);
}
