package com.cdgtaxi.ibs.common.ui;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public class AccountLabelMacroComponent extends HtmlMacroComponent{

	protected Label accountNoLabel, accountNameLabel, divisionLabel,departmentLabel;
	protected Label divisionTitleLabel, departmentTitleLabel;
	
	@Override
	public void afterCompose() {
		super.afterCompose();
		Components.wireVariables(this, this);
	}
	
	public void populateDetails(AmtbAccount acct){
		
		divisionLabel.setValue("-");
		departmentLabel.setValue("-");
		
		String acctCategory = acct.getAccountCategory();
		if(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(acctCategory) 
				|| NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(acctCategory)
				|| NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(acctCategory)) {
			
			departmentLabel.setVisible(true);
			departmentTitleLabel.setVisible(true);
			divisionTitleLabel.setValue("Division");
			
		} else if(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(acctCategory)
				|| NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(acctCategory)){
			
			departmentLabel.setVisible(false);
			departmentTitleLabel.setVisible(false);
			divisionTitleLabel.setValue("Sub Applicant");

			
		} else {
			throw new WrongValueException("Unsupported account category: " + acctCategory);
		}
		
		
		AmtbAccount runAcct = acct;
		while(runAcct!=null){
			String category = runAcct.getAccountCategory();
			if(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(category)){
				departmentLabel.setValue(runAcct.getAccountName() + " (" + runAcct.getCode() + ")");
			}else if(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(category)){
				divisionLabel.setValue(runAcct.getAccountName() + " (" + runAcct.getCode() + ")");
			}else if(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(category)){
				accountNoLabel.setValue(runAcct.getCustNo());
				accountNameLabel.setValue(runAcct.getAccountName());
			} else if(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(category)){
				accountNoLabel.setValue(runAcct.getCustNo());
				accountNameLabel.setValue(runAcct.getAccountName());
			} else if(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(category)){
				divisionLabel.setValue(runAcct.getAccountName());
			}
			
			else {
				throw new WrongValueException("Unsupported account category: " + category);
			}
			runAcct = runAcct.getAmtbAccount();
		}
		
	}
	
	
	public void reset(){
		
		ComponentUtil.reset(accountNoLabel, accountNameLabel, divisionLabel,departmentLabel);
		
		
	}
}
