package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import org.springframework.security.userdetails.UserDetails;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.dao.GenericDao;

public interface UserDao extends GenericDao{
	public UserDetails loadUserByUsernameAndDomain(String loginId, String domain);
	public SatbUser getUserWithRoles(Long userId);
	public List searchUser(String loginId, String domain, String name, String email, String status, String locked);
	public boolean isLoginIdAndDomainUsed(String loginId, String domain);
	public List<SatbUser> searchUser(String uri);
	public SatbUser getUser(String loginId, String domain);
	public List<String> getDomains();
}
