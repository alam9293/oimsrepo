package com.cdgtaxi.ibs.common.model.email;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.ImtbItemType;

public class ItemRequestEmail {

	public SatbUser requestor;
	public AmtbAccount account; // customer account (corp, div, dept, supp, or sub app)
	public ImtbItemType itemType;
	public String submitter; // to display on email, follow the requestor field
	public String custNo; // the main account no, also known as custno in db
	public String emailType;
	
	public SatbUser getRequestor() {
		return requestor;
	}
	public void setRequestor(SatbUser requestor) {
		this.requestor = requestor;
	}
	public AmtbAccount getAccount() {
		return account;
	}
	public void setAccount(AmtbAccount account) {
		this.account = account;
	}
	public ImtbItemType getItemType() {
		return itemType;
	}
	public void setItemType(ImtbItemType itemType) {
		this.itemType = itemType;
	}
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public boolean equals(Object obj){
		if(obj instanceof ItemRequestEmail){
			ItemRequestEmail comparedItem = (ItemRequestEmail) obj;
			return comparedItem.getCustNo().equals(this.custNo) && 
					comparedItem.getAccount().equals(this.account) &&
					comparedItem.getEmailType().equals(this.emailType) &&
					comparedItem.getItemType().equals(this.itemType) &&
					comparedItem.getRequestor().equals(this.requestor);
		}
		return false;
	}
}
