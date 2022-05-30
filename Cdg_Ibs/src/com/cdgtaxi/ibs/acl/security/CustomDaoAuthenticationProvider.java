package com.cdgtaxi.ibs.acl.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.DisabledException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.UserDetails;

import com.cdgtaxi.ad.ADServiceException;
import com.cdgtaxi.ad.ComfortDelgroADService;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.dao.UserDao;
import com.cdgtaxi.ibs.acl.model.SatbSession;
import com.cdgtaxi.ibs.acl.model.SatbUser;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider{
	
	private static final Logger logger = Logger.getLogger(CustomDaoAuthenticationProvider.class);
	@SuppressWarnings("rawtypes")
	private Map adProperties;
	
	protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		logger.info("additionalAuthenticationChecks()");
		
//		logger.info(this.getPasswordEncoder().encodePassword("password1", null));
		
		CustomAuthenticationToken customAuthToken = (CustomAuthenticationToken) authentication;
		UserDao dao = (UserDao)this.getUserDetailsService();
        WebAuthenticationDetails details = (WebAuthenticationDetails) customAuthToken.getDetails();
        String sessionId = details.getSessionId();
        String result = Constants.RESULT_FAILURE;
        
        SatbUser user = 
        	(SatbUser) dao.loadUserByUsernameAndDomain(
				(String)customAuthToken.getPrincipal(), customAuthToken.getDomainName());
        
        if(user == null)
			throw new BadCredentialsException(
					"SatbUser ['" + (String)customAuthToken.getPrincipal() + "', '" 
					+ customAuthToken.getDomainName() + "'] not found...");
        else
        	customAuthToken.setAuthorities(user.getAuthorities());
        
        //All roles are inactive
        if(user.getAuthorities().length==0)
        	throw new DisabledException("None of the roles are active!");
        
        if(!user.getStatus().equals(Constants.STATUS_ACTIVE))
        	throw new DisabledException("User is inactive!");
        
        try {
        	/** Database Authentication **/
//            super.additionalAuthenticationChecks(userDetails, authentication);
        	/** AD Authentication using given login id & password **/
//        	if(((String)authentication.getCredentials()).equals(Constants.SINGLE_SIGN_ON)==false)
//        		ADUtil.authenticateLDAPServer((String)authentication.getPrincipal(), (String)authentication.getCredentials());
        	/** ComfortDelGro AD Authentication using given login id & password **/
        	if(((String)customAuthToken.getCredentials()).equals(Constants.SINGLE_SIGN_ON)==false){
				logger.info("authenticateLDAPServer():"+
						(String)customAuthToken.getPrincipal()+", "+customAuthToken.getDomainName());
				
				String disableAD = (String)adProperties.get("ad.disable");
				if(disableAD==null || !disableAD.equals("true")){		
					//ComfortDelgroADService
					boolean authResult = ComfortDelgroADService.authenticateUser(
							customAuthToken.getDomainName(),
							(String)customAuthToken.getPrincipal(), 
							(String)customAuthToken.getCredentials());
					if(!authResult)
						throw new BadCredentialsException("Bad Credentials");
				}
        	}
			
            result = Constants.RESULT_SUCCESS;
        }
        catch(ADServiceException ase){
        	ase.printStackTrace();
        	throw new AccessDeniedException(ase.getMessage());
        }
        //Catch login failure then accumulate locking count
        catch (BadCredentialsException e) {
            
        	/*
        	 	This is a common error ldap authentication failure message:
        	 		LDAP: error code 49 - 80090308: LdapErr: DSID-0C090334, comment: AcceptSecurityContext error, data 775, vece
        	 	Take note at the part "data XXX, vece" where the number there represents certain information listed below:
        	 		525	user not found
					52e	invalid credentials
					530	not permitted to logon at this time
					531	not permitted to logon at this workstation
					532	password expired
					533	account disabled
					701	account expired
					773	user must reset password
					775	user account locked
        	 */
        	
        	if(user!=null){
	        	String locked = user.getLocked();
	            try {
	                int count = Integer.parseInt(locked)+1;
	                if (count < Constants.LOGIN_RETRIES) locked = String.valueOf(count);
	                else locked = Constants.BOOLEAN_YES;
	            } catch(NumberFormatException nfe) {
	                locked = "1";
	            }
	            user.setLocked(locked);
        	}
        	
            sessionId = "Bad Credentials";
            throw e;
        }
        catch (AuthenticationException e) {
            sessionId = "Unknown Authentication Failure";
            throw e;
        }
        finally {
            Timestamp now = new Timestamp(new Date().getTime());
            SatbSession session = new SatbSession(user.getLoginId(), now, details.getRemoteAddress(), result, now, sessionId);
            user = (SatbUser)dao.merge(user);
            if(result.equals(Constants.RESULT_SUCCESS)) user.setLocked(Constants.BOOLEAN_NO);
            dao.update(user);
            dao.save(session);
        }
	}

	public Map getAdProperties() {
		return adProperties;
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		CustomAuthenticationToken customAuthToken = (CustomAuthenticationToken) authentication;
		
		//Instead of getting the authorities from UserDetails, I got it from token which i manaually set it in.
		//Reason is that the default method loadByUsername only accepts username therefore I cannot override to accept another argument.
		//So ended up i have to override that method to always return valid UserDetails with null authorities then manually create my own.
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
				customAuthToken.getCredentials(), customAuthToken.getAuthorities());
		result.setDetails(customAuthToken.getDetails());
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public void setAdProperties(Map adProperties) {
		this.adProperties = adProperties;
	}
}
