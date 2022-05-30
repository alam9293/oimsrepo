package com.cdgtaxi.ibs.acl.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 1L;
    private String domainName;
    private GrantedAuthority[] authorities;
    
    public CustomAuthenticationToken(String domainName, Object principal, Object credentials) {
        super(principal, credentials);
        this.setDomainName(domainName);
    }

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainName() {
		return domainName;
	}
	
	public void setAuthorities(GrantedAuthority[] authorities){
		this.authorities = authorities;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}
}
