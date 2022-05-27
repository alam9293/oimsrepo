package com.cdgtaxi.ibs.acl;

import java.util.LinkedHashMap;

public class Constants {
	//DEFAULT ACL SETTINGS
	public static final int SESSION_TIMEOUT_SETTING = 40; //SESSION TIMEOUT in Mins
	public static final int LOGIN_RETRIES = 3;

	//PASSWORD SETTINGS
	public static final int PASSWORD_HISTORY = 5;

	//SESSION ATTRIBUTE
	public static final String SINGLE_SIGN_ON		= "SINGLE_SIGN_ON";
	public static final String USERNAME 			= "USERNAME";
	public static final String LOGINID 				= "LOGINID";
	public static final String USERID 				= "USERID";
	public static final String SESSION_TIME 		= "SESSION_TIME";
	public static final String GRANTED_AUTHORITIES	= "GRANTED_AUTHORITIES";
	public static final String GRANTED_ROLES		= "GRANTED_ROLES";
	public static final String DOMAIN 				= "DOMAIN";

	//SUPERADMIN ROLE & ROLE ID
	public static final Long SUPERADMIN_ROLE_ID 	= new Long(1);
	public static final String SUPERADMIN 			= "SuperAdmin";

	//RESOURCE
	public static final Long ROOT_ID = new Long(0);
	public static final String COMMON = "Common";
	public static final String COMMON_PUBLIC = "Common Public";

	//RESOURCE TYPE
	public static final String RESOURCE_TYPE_ADMIN 	= "A";
	public static final String RESOURCE_TYPE_USER 	= "U";

	//USER STATUS
	public static final String STATUS_ACTIVE 		= "A";
	public static final String STATUS_INACTIVE 		= "I";
	public static final LinkedHashMap<String,String> STATUS_MAP = new LinkedHashMap<String,String>(){
		{
			put(STATUS_ACTIVE, 		"ACTIVE");
			put(STATUS_INACTIVE, 	"INACTIVE");
		}
	};

	//ROLE STATUS
	public static final String ROLE_STATUS_ACTIVE 		= "A";
	public static final String ROLE_STATUS_INACTIVE 	= "I";
	public static final LinkedHashMap<String,String> ROLE_STATUS_MAP = new LinkedHashMap<String,String>(){
		{
			put(ROLE_STATUS_ACTIVE, 	"ACTIVE");
			put(ROLE_STATUS_INACTIVE, 	"INACTIVE");
		}
	};

	//BOOLEAN YES NO
	public static final String BOOLEAN_YES = "Y";
	public static final String BOOLEAN_NO = "N";
	public static final LinkedHashMap<String,String> YES_NO_MAP = new LinkedHashMap<String,String>(){
		{
			put(BOOLEAN_YES,	"YES");
			put(BOOLEAN_NO,		"NO");
		}
	};

	//RESULT SUCCESS FAILURE
	public static final String RESULT_SUCCESS = "S";
	public static final String RESULT_FAILURE = "F";
	public static final LinkedHashMap<String,String> SUCCESS_FAILURE_MAP = new LinkedHashMap<String,String>(){
		{
			put(RESULT_SUCCESS,	"SUCCESS");
			put(RESULT_FAILURE,	"FAILURE");
		}
	};
}
