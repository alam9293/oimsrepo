package com.cdgtaxi.ibs.acl.security;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.dao.DaoHelper;

public class CustomFilterInvocationDefinitionSource extends DefaultFilterInvocationDefinitionSource{
	
	private DaoHelper daoHelper;
	private static final Logger logger = Logger.getLogger(CustomFilterInvocationDefinitionSource.class);
	private static UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static LinkedHashMap<String, ConfigAttributeDefinition> requestMap = new LinkedHashMap<String, ConfigAttributeDefinition>();
	
	public CustomFilterInvocationDefinitionSource() {
		super(urlMatcher, requestMap);
	}

	public void reload(){
		List<SatbResource> resources = this.daoHelper.getResourceDao().getAll();
		
		for (SatbResource resource : resources) {
			logger.debug("uri: "+resource.getUri());
			List<ConfigAttribute> configAttributes = new LinkedList<ConfigAttribute>();
			
			Set<SatbRole> roles = resource.getSatbRoles();
			for(SatbRole role : roles){
				ConfigAttribute config = new SecurityConfig(role.getName());
				configAttributes.add(config);
				logger.debug("security config: "+config.getAttribute());
			}
			
			ConfigAttributeDefinition configDefinition = new ConfigAttributeDefinition(configAttributes);
			requestMap.put(resource.getUri(), configDefinition);
		}
	}
	
	protected ConfigAttributeDefinition lookupAttributes(String url){
		return super.lookupAttributes(url);
	}
	
	public ConfigAttributeDefinition lookupAttributes(String url, String method){
		logger.info("url: "+url+", method: "+method);
		
		int firstQuestionMarkIndex = url.indexOf("?");
        if(firstQuestionMarkIndex != -1)
            url = url.substring(0, firstQuestionMarkIndex);
        
//        if(isConvertUrlToLowercaseBeforeComparison()) {
//            url = url.toLowerCase();
//            if(logger.isDebugEnabled())
//                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
//        }

        for(String urlPattern : requestMap.keySet()) {
        	
        	if(urlPattern == null && logger.isDebugEnabled()) {
                logger.debug("Candidate is: '" + url + "'; pattern is NULL; skipped");
                continue;
        	}
        	
        	boolean matched = urlMatcher.pathMatchesUrl(urlPattern, url);
            
//        	logger.info("Candidate is: '" + url + "'; pattern is " + urlPattern + "; matched=" + matched);
        	
        	if(logger.isDebugEnabled())
                logger.debug("Candidate is: '" + url + "'; pattern is " + urlPattern + "; matched=" + matched);
            
        	if(matched)
                return requestMap.get(urlPattern);
        }
        
        return null;
	}
	
	public DaoHelper getDaoHelper() {
		return daoHelper;
	}

	public void setDaoHelper(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
	}

}
