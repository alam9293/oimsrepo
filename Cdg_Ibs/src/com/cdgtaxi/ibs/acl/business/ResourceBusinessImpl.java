package com.cdgtaxi.ibs.acl.business;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;

public class ResourceBusinessImpl extends GenericBusinessImpl implements ResourceBusiness {
	public List retrieveMenu(Long parentRsrcId, List<String> grantedAuthorities){
		return this.daoHelper.getResourceDao().retrieveMenu(parentRsrcId, grantedAuthorities);
	}

	public List<SatbResource> get(Long parentRsrcId) {
		return this.daoHelper.getResourceDao().get(parentRsrcId);
	}

}
