package com.cdgtaxi.ibs.acl.security;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.security.BadCredentialsException;

import com.cdgtaxi.ibs.acl.exception.LdapAuthenticationException;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.sun.security.auth.module.NTSystem;

public class ADUtil {
	
	private static final Logger logger = Logger.getLogger(ADUtil.class);
	
	public static boolean authenticateWindowsLogonDomain(){
		logger.info("authenticateWindowsLogonDomain()");
		
		String windowsLogonName = ADUtil.getWindowsLogonName();
		String windowsLogonDomain = ADUtil.getWindowsLogonDomain();
		logger.info("Windows Logon Name: "+windowsLogonName);
		logger.info("Windows Logon Domain: "+windowsLogonDomain);
		
		/** For testing therefore the below are commented **/
		if(windowsLogonName==null || windowsLogonName.equals("") ||
				windowsLogonDomain==null || windowsLogonDomain.equals(""))
			return false;
		
		if(!windowsLogonDomain.equals("2ICHIGO2"))
			return false;
		
		return true;
	}
	
	public static String getWindowsLogonName(){
		NTSystem ntSystem = new NTSystem();
		return ntSystem.getName();
	}
	
	public static String getWindowsLogonDomain(){
		NTSystem ntSystem = new NTSystem();
		return ntSystem.getDomain();
	}
	
	public static void authenticateLDAPServer(String loginId, String password){
		logger.info("authenticateLDAPServer()");
		//TODO: Current method is not using loginId and password argument to do authentication
		try {
			// connect to the server
			LDAPConnection conn = new LDAPConnection();
			conn.connect("10.0.0.131", LDAPConnection.DEFAULT_PORT);

			// authenticate to the server with the connection method
			try {
				String hardcodedDN 			= "CN=administrator,cn=users,DC=wizvision,DC=local";
				String hardcodedPassword 	= password;
				
				conn.bind(LDAPConnection.LDAP_V3, hardcodedDN, hardcodedPassword.getBytes("UTF8"));
			} 
			catch (UnsupportedEncodingException u) {
				throw new LdapAuthenticationException("Unsupported Encoding Exception!");
			}

			if(conn.isBound() == false) throw new BadCredentialsException("Bad Credentials");
			
			// disconnect with the server
			conn.disconnect();
		}
		catch (LDAPException e) {
			throw new LdapAuthenticationException("Unable to connect ldap server!");
		}
	}
}
