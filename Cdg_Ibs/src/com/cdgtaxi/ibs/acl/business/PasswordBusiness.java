package com.cdgtaxi.ibs.acl.business;

import com.cdgtaxi.ibs.acl.exception.InvalidPasswordException;
import com.cdgtaxi.ibs.acl.exception.PasswordUsedException;
import com.cdgtaxi.ibs.common.business.GenericBusiness;

public interface PasswordBusiness extends GenericBusiness {
	public void changePassword(String loginId, String oldPassword, String newPassword) throws PasswordUsedException, InvalidPasswordException;
}
