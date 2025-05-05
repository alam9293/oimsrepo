package com.cdgtaxi.ibs.acl.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.cdgtaxi.ad.ADServiceException;
import com.cdgtaxi.ad.ComfortDelgroADService;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.business.BusinessHelper;

public class CustomAuthenticationProcessingFilter extends AuthenticationProcessingFilter{
	
	private static final Logger logger = Logger.getLogger(CustomAuthenticationProcessingFilter.class);
	private BusinessHelper businessHelper;
	private Map adProperties;
	
	public Authentication attemptAuthentication(HttpServletRequest request) 
		throws AuthenticationException {
		logger.info("attemptAuthentication()");
		
		try{
			String username = obtainUsername(request);
	        String password = obtainPassword(request);
	        String domain = request.getParameter("j_domain");
	        
	        /*
	         * Using WizVision AD Auth Method
	         */
	        boolean windowsDomainAuthResult = false;
			if(username.equals("") && password.equals("")){
				request.getSession().setAttribute(Constants.SINGLE_SIGN_ON, "Y");
				windowsDomainAuthResult = ADUtil.authenticateWindowsLogonDomain();
				if(windowsDomainAuthResult){
					logger.info("Windows Domain authenticated!");
					username = ADUtil.getWindowsLogonName();
					password = Constants.SINGLE_SIGN_ON;
				}
				else logger.info("Windows Domain not authenticated!");
			}
			else{
				request.getSession().setAttribute(Constants.SINGLE_SIGN_ON, "N");
			}
	        
	        /*
	         * Using ComfortDelGro AD Auth Method
	         */
	        String domainUsername = request.getParameter("domainUsername");
	        String domainName = request.getParameter("domainName");
	        String disableAD = (String)adProperties.get("ad.disable");
			if(domainUsername!=null && domainName!=null){
				request.getSession().setAttribute(Constants.SINGLE_SIGN_ON, "Y");
				logger.info("User Domain:"+domainName);
				
				if(disableAD==null || !disableAD.equals("true")){
					//Pull Out AD Properties
					try{
						String[] legalDomains = ComfortDelgroADService.getAllSupportedDomains();
						logger.info("Legal Domains:"+legalDomains.toString());
						
						//Authenticate Domain if not valid, later on authenticate the username, password and domain
						if(ComfortDelgroADService.isDomainSupported(domainName)){
							logger.info("Windows Domain authenticated!");
							username = domainUsername;
							password = Constants.SINGLE_SIGN_ON;
							domain = domainName;
						}
						else logger.info("Windows Domain not authenticated!");
					}
					catch(ADServiceException ase){
						ase.printStackTrace();
						throw new AccessDeniedException(ase.getMessage());
					}
				}
			}
			else{
				request.getSession().setAttribute(Constants.SINGLE_SIGN_ON, "N");
				if(disableAD!=null && disableAD.equals("true"))
					domain = "TestDomain";
			}
			
	        //Init authentication token
	        CustomAuthenticationToken authRequest = new CustomAuthenticationToken(domain, username, password);
	        setDetails(request, authRequest);
	        
	        //authenticate
	        logger.info("authenticate()");
	        Authentication authentication = getAuthenticationManager().authenticate(authRequest);
	        
	        logger.info("User login:"+username);
			logger.info("Loading user information from database...");
			//Don need to check for null here as authenticate(authRequest) should have done so and throw exception if null
			SatbUser user = (SatbUser)this.businessHelper.getUserBusiness().loadUserByUsernameAndDomain(username, domain);
			logger.info("User information loaded!");
			
	        request.getSession().setAttribute(Constants.USERNAME, user.getName());
		    request.getSession().setAttribute(Constants.LOGINID, user.getLoginId());
		    request.getSession().setAttribute(Constants.USERID, user.getUserId());
		    request.getSession().setAttribute(Constants.DOMAIN, user.getDomain());
	        request.getSession().setMaxInactiveInterval(Constants.SESSION_TIMEOUT_SETTING * 60);
	        request.getSession().setAttribute(Constants.SESSION_TIME, Constants.SESSION_TIMEOUT_SETTING);
	        //request.getSession().setMaxInactiveInterval(Integer.MAX_VALUE);
	        
	        //set session attribute for granted authorities
	        List<String> grantedAuthorities = new ArrayList<String>();
	        GrantedAuthority[] authorities = user.getAuthorities();
	        for(GrantedAuthority authority : authorities){
	        	grantedAuthorities.add(authority.getAuthority());
	        }
	        request.getSession().setAttribute(Constants.GRANTED_AUTHORITIES, grantedAuthorities);
	        
	        //set session attribute for granted roles
	        List<String> grantedRoles = new ArrayList<String>();
	        Set<SatbRole> roles = user.getSatbRoles();
	        for(SatbRole role : roles){
	        	grantedRoles.add(role.getName());
	        }
	        request.getSession().setAttribute(Constants.GRANTED_ROLES, grantedRoles);
	        
	        return authentication;
		}
		catch(AuthenticationException ae){
			ae.printStackTrace();
			throw ae;
		}
	}

	public BusinessHelper getBusinessHelper() {
		return businessHelper;
	}

	public void setBusinessHelper(BusinessHelper businessHelper) {
		this.businessHelper = businessHelper;
	}

	public Map getAdProperties() {
		return adProperties;
	}

	public void setAdProperties(Map adProperties) {
		this.adProperties = adProperties;
	}
}
