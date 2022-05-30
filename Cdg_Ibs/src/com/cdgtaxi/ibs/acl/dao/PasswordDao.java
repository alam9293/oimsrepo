package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbPassword;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.dao.GenericDao;

public interface PasswordDao extends GenericDao{
	public List<SatbPassword> getPreviousUsedPasswords(SatbUser user);
}
