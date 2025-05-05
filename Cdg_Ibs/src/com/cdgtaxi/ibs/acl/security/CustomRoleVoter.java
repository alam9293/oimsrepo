package com.cdgtaxi.ibs.acl.security;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.vote.RoleVoter;

public class CustomRoleVoter extends RoleVoter{
	
	private static final Logger logger = Logger.getLogger(CustomRoleVoter.class);
	
	public CustomRoleVoter() {
        setRolePrefix("");
    }

    public int vote(Authentication authentication, Object object,
			ConfigAttributeDefinition config) {
    	
		int result = 0; // abstain
		
		//The url has no roles tied to it, this means everyone can access
		if(config.getConfigAttributes().size()==0)
			return 1;
		
		// Will abstain if this ConfigAttributeDefinition (uri) has no
		// ConfigAttributes (roles) associated with it
		for (Iterator iter = config.getConfigAttributes().iterator(); iter.hasNext();) {
			ConfigAttribute attribute = (ConfigAttribute) iter.next();
			
			if (supports(attribute)) {
				result = -1;
				
				logger.debug("ConfigAttribute: "+attribute.getAttribute());
				
				if(attribute.getAttribute().equals(""))
					return 1;
				
                for (int i = 0; i < authentication.getAuthorities().length; i++)
                	if(attribute.getAttribute().equals(
                    		authentication.getAuthorities()[i].getAuthority()))
                        return 1;
			}
		}
		
		return result;
	}
}
