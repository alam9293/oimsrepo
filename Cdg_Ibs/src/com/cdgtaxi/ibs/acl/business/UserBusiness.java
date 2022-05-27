package com.cdgtaxi.ibs.acl.business;

import java.util.List;
import java.util.Set;

import org.springframework.security.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.business.GenericBusiness;

public interface UserBusiness extends GenericBusiness {
	public UserDetails loadUserByUsernameAndDomain(String loginId, String domain);
	public SatbUser getUserWithRoles(Long loginId);
	public List searchUser(String loginId, String domain, String name, String email, String status, String locked);
	public boolean isLoginIdAndDomainUsed(String loginId, String domain);
	public SatbUser createNewUser(String loginId, String domain, Set<Component> selectedRoles, String name, String email, String createdBy);
	public void unlockAccount(SatbUser user);
	public void activateAccount(SatbUser user);
	public void deactivateAccount(SatbUser user);
	public void resetPassword(String loginId, SatbUser user);
//	public SatbUser getUser(String loginId);
	public List<String> getDomains();
}
