package com.cdgtaxi.ibs.product.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewAccountAgingWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewAccountAgingWindow.class);
	private String accountNo;
	
	public ViewAccountAgingWindow(){
		Map params = Executions.getCurrent().getArg();
		accountNo = (String)params.get("accountNo");
	}
	
	@Override
	public void refresh() {
		
	}

	public void afterCompose() {
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		
		//Retrieve the selected account object first
		AmtbAccount selectedAccount = this.businessHelper.getProductBusiness().getAccountWithParentAndChildren(accountNo);
		//Get Top Level Account First
		AmtbAccount topLevelAccount = null;
		if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT) ||
				selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE))
			topLevelAccount = selectedAccount;
		else if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) ||
				selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
			topLevelAccount = selectedAccount.getAmtbAccount();
		else if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
			topLevelAccount = selectedAccount.getAmtbAccount().getAmtbAccount();
		
		Listitem item = new Listitem();
		item.appendChild(newListcell(topLevelAccount.getAccountName()));
		item.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(topLevelAccount.getAccountCategory())));
		item.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess30DaysTotalOutstandingAmount(topLevelAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
		item.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess60DaysTotalOutstandingAmount(topLevelAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
		item.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess90DaysTotalOutstandingAmount(topLevelAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
		item.appendChild(newListcell(this.businessHelper.getProductBusiness().getMore90DaysTotalOutstandingAmount(topLevelAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
		resultListBox.appendChild(item);
		
		if(selectedAccount.getInvoiceFormat()!=null){
			if(!(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT) ||
					selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE))){
				Listitem nextItem = new Listitem();
				nextItem.appendChild(newListcell(selectedAccount.getAccountName()));
				nextItem.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(selectedAccount.getAccountCategory())));
				nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess30DaysTotalOutstandingAmount(selectedAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
				nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess60DaysTotalOutstandingAmount(selectedAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
				nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess90DaysTotalOutstandingAmount(selectedAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
				nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getMore90DaysTotalOutstandingAmount(selectedAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
				resultListBox.appendChild(nextItem);
			}
		}
		else{
			AmtbAccount parentAccount = selectedAccount.getAmtbAccount();
			if(parentAccount.getInvoiceFormat()!=null){
				if(!(parentAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT) ||
						parentAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE))){
					Listitem nextItem = new Listitem();
					nextItem.appendChild(newListcell(parentAccount.getAccountName()));
					nextItem.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(parentAccount.getAccountCategory())));
					nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess30DaysTotalOutstandingAmount(parentAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
					nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess60DaysTotalOutstandingAmount(parentAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
					nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getLess90DaysTotalOutstandingAmount(parentAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
					nextItem.appendChild(newListcell(this.businessHelper.getProductBusiness().getMore90DaysTotalOutstandingAmount(parentAccount), StringUtil.GLOBAL_DECIMAL_FORMAT));
					resultListBox.appendChild(nextItem);
				}
			}
		}
	}
}
