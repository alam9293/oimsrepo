package com.cdgtaxi.ibs.common.model.forms;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class SearchByAccountForm {
	private String customerNo;
	private String accountName;
	private AmtbAccount account;
	private AmtbAccount division;
	private AmtbAccount department;
	private boolean billable = false;
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public AmtbAccount getAccount() {
		return account;
	}
	public void setAccount(AmtbAccount account) {
		this.account = account;
	}
	public AmtbAccount getDivision() {
		return division;
	}
	public void setDivision(AmtbAccount division) {
		this.division = division;
	}
	public AmtbAccount getDepartment() {
		return department;
	}
	public void setDepartment(AmtbAccount department) {
		this.department = department;
	}
	public void setBillable(boolean billable) {
		this.billable = billable;
	}
	public boolean isBillable() {
		return billable;
	}
	public boolean isAtLeastOneCriteriaSelected() {
		return (customerNo != null && !customerNo.trim().equals(""))
		|| (accountName != null && !accountName.trim().equals(""))
		|| account != null
		|| division != null
		|| department != null;
	}
}
