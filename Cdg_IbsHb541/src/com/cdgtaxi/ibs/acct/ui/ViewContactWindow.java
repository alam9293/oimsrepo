package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;

public class ViewContactWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewContactWindow.class);
	private String custNo, contactPersonNo, acctStatus;
	@SuppressWarnings("unchecked")
	public ViewContactWindow() throws InterruptedException{
		logger.info("ViewContactWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
		// getting contact person id
		contactPersonNo = map.get("contactPersonNo");
		// checking account status. if null, show error and back
		if(contactPersonNo==null || contactPersonNo.trim().length()==0){
			Messagebox.show("No Contact Person!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		acctStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");

		if(!this.checkUriAccess(Uri.EDIT_CONTACT))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		
		Map<String, String> contactDetails = this.businessHelper.getAccountBusiness().getContact(contactPersonNo);
		if(contactDetails==null){// deletion
			this.back();
			return;
		}
		if(contactDetails.get("mainBilling")!=null){
			((Checkbox)this.getFellow("isMainBilling")).setChecked(true);
		}
		if(contactDetails.get("mainShipping")!=null){
			((Checkbox)this.getFellow("isMainShipping")).setChecked(true);
		}
		if(contactDetails.get("updateCostCentre")!=null){
			if(contactDetails.get("updateCostCentre").equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				((Checkbox)this.getFellow("updateCostCentre")).setChecked(true);
			}
		}
		if(contactDetails.get("mainContactSalCode")!=null){
			String mainSal = ConfigurableConstants.getSalutations().get(contactDetails.get("mainContactSalCode"));
			((Label)this.getFellow("mainContactSal")).setValue(mainSal);
		}else{
			((Label)this.getFellow("mainContactSal")).setValue("-");
		}
		((Label)this.getFellow("mainContactName")).setValue(contactDetails.get("mainContactName"));
		if(contactDetails.get("mainContactTitle")!=null){
			((Label)this.getFellow("mainContactTitle")).setValue(contactDetails.get("mainContactTitle"));
		}else{
			((Label)this.getFellow("mainContactTitle")).setValue("-");
		}
		((Label)this.getFellow("mainContactTel")).setValue(contactDetails.get("mainContactTel"));
		if(contactDetails.get("mainContactMobile")!=null){
			((Label)this.getFellow("mainContactMobile")).setValue(contactDetails.get("mainContactMobile"));
		}else{
			((Label)this.getFellow("mainContactMobile")).setValue("-");
		}
		if(contactDetails.get("mainContactFax")!=null){
			((Label)this.getFellow("mainContactFax")).setValue(contactDetails.get("mainContactFax"));
		}else{
			((Label)this.getFellow("mainContactFax")).setValue("-");
		}
		if(contactDetails.get("mainContactEmail")!=null){
			((Label)this.getFellow("mainContactEmail")).setValue(contactDetails.get("mainContactEmail"));
		}else{
			((Label)this.getFellow("mainContactEmail")).setValue("-");
		}
		if(contactDetails.get("mainContactRace")!=null){
			String mainRace = ConfigurableConstants.getRace().get(contactDetails.get("mainContactRace"));
			((Label)this.getFellow("mainContactRace")).setValue(mainRace);
		}else{
			((Label)this.getFellow("mainContactRace")).setValue("-");
		}
		if(contactDetails.get("subContactSalCode")!=null){
			String subSal = ConfigurableConstants.getSalutations().get(contactDetails.get("subContactSalCode"));
			((Label)this.getFellow("subContactSal")).setValue(subSal);
		}else{
			((Label)this.getFellow("subContactSal")).setValue("-");
		}
		if(contactDetails.get("subContactName")!=null){
			((Label)this.getFellow("subContactName")).setValue(contactDetails.get("subContactName"));
		}else{
			((Label)this.getFellow("subContactName")).setValue("-");
		}
		if(contactDetails.get("subContactTitle")!=null){
			((Label)this.getFellow("subContactTitle")).setValue(contactDetails.get("subContactTitle"));
		}else{
			((Label)this.getFellow("subContactTitle")).setValue("-");
		}
		if(contactDetails.get("subContactTel")!=null){
			((Label)this.getFellow("subContactTel")).setValue(contactDetails.get("subContactTel"));			
		}else{
			((Label)this.getFellow("subContactTel")).setValue("-");
		}
		if(contactDetails.get("subContactMobile")!=null){
			((Label)this.getFellow("subContactMobile")).setValue(contactDetails.get("subContactMobile"));
		}else{
			((Label)this.getFellow("subContactMobile")).setValue("-");
		}
		if(contactDetails.get("subContactFax")!=null){
			((Label)this.getFellow("subContactFax")).setValue(contactDetails.get("subContactFax"));
		}else{
			((Label)this.getFellow("subContactFax")).setValue("-");
		}
		if(contactDetails.get("subContactEmail")!=null){
			((Label)this.getFellow("subContactEmail")).setValue(contactDetails.get("subContactEmail"));
		}else{
			((Label)this.getFellow("subContactEmail")).setValue("-");
		}
		if(contactDetails.get("subContactRace")!=null){
			String subRace = ConfigurableConstants.getRace().get(contactDetails.get("subContactRace"));
			((Label)this.getFellow("subContactRace")).setValue(subRace);
		}else{
			((Label)this.getFellow("subContactRace")).setValue("-");
		}
		if(contactDetails.get("blkNo")!=null){
			((Label)this.getFellow("blkNo")).setValue(contactDetails.get("blkNo"));
		}else{
			((Label)this.getFellow("blkNo")).setValue("-");
		}
		if(contactDetails.get("unitNo")!=null){
			((Label)this.getFellow("unitNo")).setValue(contactDetails.get("unitNo"));
		}else{
			((Label)this.getFellow("unitNo")).setValue("-");
		}
		((Label)this.getFellow("street")).setValue(contactDetails.get("street"));
		if(contactDetails.get("building")!=null){
			((Label)this.getFellow("building")).setValue(contactDetails.get("building"));
		}else{
			((Label)this.getFellow("building")).setValue("-");
		}
		if(contactDetails.get("area")!=null){
			((Label)this.getFellow("area")).setValue(contactDetails.get("area"));
		}else{
			((Label)this.getFellow("area")).setValue("-");
		}
		String country = ConfigurableConstants.getCountries().get(contactDetails.get("countryCode"));
		((Label)this.getFellow("country")).setValue(country);
		if(contactDetails.get("city")!=null){
			((Label)this.getFellow("city")).setValue(contactDetails.get("city"));
		}else{
			((Label)this.getFellow("city")).setValue("-");
		}
		if(contactDetails.get("state")!=null){
			((Label)this.getFellow("state")).setValue(contactDetails.get("state"));
		}else{
			((Label)this.getFellow("state")).setValue("-");
		}
		((Label)this.getFellow("postal")).setValue(contactDetails.get("postal"));
		if(contactDetails.get("updatedBy")!=null){
			((Label)this.getFellow("lastUpdatedBy")).setValue(contactDetails.get("updatedBy"));
		}
		if(contactDetails.get("updatdDate")!=null){
			((Label)this.getFellow("lastUpdatedDate")).setValue(contactDetails.get("updatdDate"));
		}
		if(contactDetails.get("updateTime")!=null){
			((Label)this.getFellow("lastUpdatedTime")).setValue(contactDetails.get("updateTime"));
		}
		
		Label contactPersonIdLabel = (Label)this.getFellow("contactPersonId");
		contactPersonIdLabel.setValue(contactPersonNo);
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
	public void edit() throws InterruptedException{
		logger.info("edit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("contactPersonNo", contactPersonNo);
		this.forward(Uri.EDIT_CONTACT, params, this.getParent());
	}
}
