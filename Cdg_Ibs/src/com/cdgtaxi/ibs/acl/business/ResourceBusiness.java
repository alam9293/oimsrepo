package com.cdgtaxi.ibs.acl.business;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.common.business.GenericBusiness;

public interface ResourceBusiness extends GenericBusiness {
	public List retrieveMenu(Long parentRsrcId, List<String> grantedAuthorities);
	public List<SatbResource> get(Long parentRsrcId);
}
