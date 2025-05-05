package com.cdgtaxi.ibs.util;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class AccountUtil {

	
	public static AmtbAccount getTopLevelAccount(AmtbAccount account){
		
		AmtbAccount acct = account;
		while(acct!=null){
			
			String acctCategory = acct.getAccountCategory();
			
			if(acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) || acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
				return acct;
			}
			
			acct = acct.getAmtbAccount();
		}
		
		return null;
		
	}
	
	
}
