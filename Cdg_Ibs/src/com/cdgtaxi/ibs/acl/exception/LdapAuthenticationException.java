package com.cdgtaxi.ibs.acl.exception;

import org.springframework.security.AuthenticationException;

public class LdapAuthenticationException extends AuthenticationException{
	private static final long serialVersionUID = 1L;

	public LdapAuthenticationException(String errorMessage){
		super(errorMessage);
	}
}
