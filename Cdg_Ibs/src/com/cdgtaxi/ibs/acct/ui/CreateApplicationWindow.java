package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.EntityMasterManager;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.constraint.CreditLimitConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredAmountConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredZeroAmountConstraint;

public class CreateApplicationWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateApplicationWindow.class);
	private List<Listitem> industries = new ArrayList<Listitem>();
	private List<Listitem> accountTypes = new ArrayList<Listitem>();
	private List<Listitem> countries = new ArrayList<Listitem>();
	private List<Listitem> salutations = new ArrayList<Listitem>();
	private List<Listitem> infoSources = new ArrayList<Listitem>();
	private List<Listitem> entities = new ArrayList<Listitem>();
	private List<Listitem> productTypes = new ArrayList<Listitem>();
	private List<Listitem> productDiscounts = new ArrayList<Listitem>();
	private List<Listitem> rewards = new ArrayList<Listitem>();
	private List<Listitem> subscriptionFees = new ArrayList<Listitem>();
	private List<Listitem> issuanceFees = new ArrayList<Listitem>();
	private List<Listitem> salespersons = new ArrayList<Listitem>();
	private List<Listitem> volumeDiscounts = new ArrayList<Listitem>();
	private List<Listitem> adminFees = new ArrayList<Listitem>();
	private List<Listitem> earlyPayments = new ArrayList<Listitem>();
	private List<Listitem> lateInterests = new ArrayList<Listitem>();
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> booleanList2 = new ArrayList<Listitem>();
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> jobStatus = new ArrayList<Listitem>();
	private List<Listitem> invoiceFormats = new ArrayList<Listitem>();
	private List<Listitem> invoiceSorts = new ArrayList<Listitem>();
	private List<Listitem> govtEInvFlags = new ArrayList<Listitem>();
	private List<Listitem> businessUnits = new ArrayList<Listitem>();
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private List<Listitem> race = ComponentUtil.convertToListitems(ConfigurableConstants.getRace(), false);
	
	private String appNo = null;
	private String copyAppNo = null;
	private String acctTemplate = null;
	@SuppressWarnings("unchecked")
	public CreateApplicationWindow(){
		logger.info("CreateApplicationWindow()");
		//application id if any
		Map<String, String> map = Executions.getCurrent().getArg();
		String appNo = map.get("appNo");
		if(appNo!=null && appNo.length()!=0){
			this.appNo = appNo;
		}
		String copyAppNo = map.get("copyAppNo");
		if(copyAppNo!=null && copyAppNo.length()!=0){
			this.copyAppNo = copyAppNo;
		}
		// getting data from non configurable constants
		// getting boolean
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		
		booleanList2.add(ComponentUtil.createRequiredDefaultListitem());
		booleanList2.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		booleanList2.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		
		// getting optional boolean
		optionalBooleanList.add((Listitem)ComponentUtil.createNotRequiredListItem());
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		// getting data from non configurable constants
		invoiceFormats.add(new Listitem(NonConfigurableConstants.INVOICE_FORMAT.get(NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT), NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT));
		invoiceFormats.add(new Listitem(NonConfigurableConstants.INVOICE_FORMAT.get(NonConfigurableConstants.INVOICE_FORMAT_SUBACCOUNT), NonConfigurableConstants.INVOICE_FORMAT_SUBACCOUNT));

		// getting data from non configurable constants
		invoiceSorts.add(new Listitem(NonConfigurableConstants.INVOICE_SORTING.get(NonConfigurableConstants.INVOICE_SORTING_CARD), NonConfigurableConstants.INVOICE_SORTING_CARD));
		invoiceSorts.add(new Listitem(NonConfigurableConstants.INVOICE_SORTING.get(NonConfigurableConstants.INVOICE_SORTING_TXN_DATE), NonConfigurableConstants.INVOICE_SORTING_TXN_DATE));
		// init data for Govt EInv Flags
		for(Entry<String, String> entry : NonConfigurableConstants.GOVT_EINV_FLAGS.entrySet()){
			Listitem item = new Listitem(entry.getValue(), entry.getKey());
			item.setValue(entry.getKey());
			govtEInvFlags.add(item);
		}
		Map<String, String> masterDays = NonConfigurableConstants.DAYSwithSuffix;
		for(String masterCode : masterDays.keySet()){
			this.daysUnits.add(new Listitem(masterDays.get(masterCode),masterCode));
		}
		// init data for Business Units
		this.businessUnits.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> businessUnitMasters = ConfigurableConstants.getBusinessUnits();
		for(Entry<String, String> entry : businessUnitMasters.entrySet()){
			this.businessUnits.add(new Listitem(entry.getKey() + " - " + entry.getValue(), entry.getKey()));
		}
		// getting data from master table
		// getting industry
		industries.add(ComponentUtil.createRequiredDefaultListitem());
		Map<String, String> masterIndustry = ConfigurableConstants.getIndustries();
		for(String masterCode : masterIndustry.keySet()){
			this.industries.add(new Listitem(masterIndustry.get(masterCode), masterCode));
		}
		// getting job status
		jobStatus.add(ComponentUtil.createRequiredDefaultListitem());
		Map<String, String> masterJobStatus = ConfigurableConstants.getJobStatuses();
		for(String masterCode : masterJobStatus.keySet()){
			this.jobStatus.add(new Listitem(masterJobStatus.get(masterCode), masterCode));
		}
		// getting country
		Map<String, String> masterCountry = ConfigurableConstants.getCountries();
		for(String masterCode : masterCountry.keySet()){
			if(masterCode.equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
				Listitem item = new Listitem(masterCountry.get(masterCode), masterCode);
				item.setSelected(true);
				this.countries.add(item);
			}else{
				this.countries.add(new Listitem(masterCountry.get(masterCode), masterCode));
			}
		}
		// getting salutation
		salutations.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterSalutation = ConfigurableConstants.getSalutations();
		for(String masterCode : masterSalutation.keySet()){
			this.salutations.add(new Listitem(masterSalutation.get(masterCode), masterCode));
		}
		// getting information source
		infoSources.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterInfo = ConfigurableConstants.getInformationSources();
		for(String masterCode : masterInfo.keySet()){
			this.infoSources.add(new Listitem(masterInfo.get(masterCode), masterCode));
		}
		// getting entities
		Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
		List<FmtbEntityMaster> entityList = this.businessHelper.getAdminBusiness().getActiveEntities();
		for(FmtbEntityMaster entity : entityList){
			this.entities.add(new Listitem(entity.getEntityName(), entity.getEntityNo()));
		}
		// getting product discount
		productDiscounts.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> pdms = MasterSetup.getProductDiscountManager().getAllMasters();
		for(Integer pdm : pdms.keySet()){
			this.productDiscounts.add(new Listitem(pdms.get(pdm), pdm));
		}
		// getting subscription fee
		subscriptionFees.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> sfms = MasterSetup.getSubscriptionManager().getAllMasters();
		for(Integer sfm : sfms.keySet()){
			this.subscriptionFees.add(new Listitem(sfms.get(sfm), sfm));
		}
		// getting issuance fee
		issuanceFees.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> ifms = MasterSetup.getIssuanceManager().getAllMasters();
		for(Integer ifm : ifms.keySet()){
			this.issuanceFees.add(new Listitem(ifms.get(ifm), ifm));
		}
		// getting rewards plans
		rewards.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> rms = MasterSetup.getRewardsManager().getAllMasters();
		for(Integer rm : rms.keySet()){
			this.rewards.add(new Listitem(rms.get(rm), rm));
		}
		// getting salesperson
		salespersons.add(ComponentUtil.createRequiredDefaultListitem());
		Map<Integer, String> sms = MasterSetup.getSalespersonManager().getAllMasters();
		for(Integer sm : sms.keySet()){
			this.salespersons.add(new Listitem(sms.get(sm), sm));
		}
		// getting volume discount
		volumeDiscounts.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> vdms = MasterSetup.getVolumeDiscountManager().getAllMasters();
		for(Integer vdm : vdms.keySet()){
			this.volumeDiscounts.add(new Listitem(vdms.get(vdm), vdm));
		}
		// getting admin fees
		Map<Integer, String> afms = MasterSetup.getAdminFeeManager().getAllMasters();
		for(Integer afm : afms.keySet()){
			this.adminFees.add(new Listitem(afms.get(afm), afm));
		}
		// getting early payment
		earlyPayments.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<Integer, String> epms = MasterSetup.getEarlyPaymentManager().getAllMasters();
		for(Integer epm : epms.keySet()){
			this.earlyPayments.add(new Listitem(epms.get(epm), epm));
		}
		// getting late interest
		Map<Integer, String> lpms = MasterSetup.getLatePaymentManager().getAllMasters();
		for(Integer lpm : lpms.keySet()){
			this.lateInterests.add(new Listitem(lpms.get(lpm), lpm));
		}
	}
	public void updateAccountType(String template) throws InterruptedException{
		logger.info("updateAccountType(String template)");
		// getting account types
		Map<Integer, String> acctTypes = this.businessHelper.getApplicationBusiness().getAccountTypes(template);
		for(Integer acctTypeNo : acctTypes.keySet()){
			this.accountTypes.add(new Listitem(acctTypes.get(acctTypeNo), acctTypeNo));
		}
	}
	@SuppressWarnings("unchecked")
	public void updateProductType() throws InterruptedException{
		logger.info("updateProductType()");
		// getting selected account type
		Listitem selectedAcctType = ((Listbox)this.getFellow("acctTypeList")).getSelectedItem();
		// getting account type number
		Integer selectedAccountTypeNo = (Integer)selectedAcctType.getValue();
		// getting product types available for account type
		Map<String, String> selectedProductTypes = this.businessHelper.getApplicationBusiness().getProductTypes(selectedAccountTypeNo);
		// if account type is found
		if(selectedProductTypes!=null){
			// clear the cached product types
			this.productTypes.clear();
			// adding back the product types
			for(String selectedProductTypeId : selectedProductTypes.keySet()){
				this.productTypes.add(new Listitem(selectedProductTypes.get(selectedProductTypeId), selectedProductTypeId));
			}
			// now refreshing all product type list
			int counter = 1;
			Listbox productType = (Listbox)this.getFellowIfAny("prodType" + counter);
			while(productType!=null){
				productType.getItems().clear();
				productType.getItems().addAll(cloneList(this.productTypes));
				if(productType.getItems().size()!=0){
					productType.setSelectedIndex(0);
				}
				productType = (Listbox)this.getFellowIfAny("prodType" + counter);
				counter++;
			}
		}else{
			Messagebox.show("Account Type not found!", "Create Corporate Application", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void updateArControl(Listitem selectedEntity) throws InterruptedException{
		logger.info("updateArControl(Listitem selectedEntity)");
		// getting the ar control list
		Listbox arControlList = (Listbox)this.getFellow("arAcct");
		// now clearing the list
		arControlList.getItems().clear();
		// now adding ar control back
		Map<Integer, Map<String, String>> arCtrlAccts = MasterSetup.getEntityManager().getAllDetails((Integer)selectedEntity.getValue());
		for(Integer arCtrlAcct : arCtrlAccts.keySet()){
			arControlList.getItems().add(new Listitem(arCtrlAccts.get(arCtrlAcct).get(EntityMasterManager.DETAIL_AR_CODE), arCtrlAcct));
		}
		if(arControlList.getItems().size()!=0){
			arControlList.setSelectedIndex(0);
		}
	}
	@SuppressWarnings("unchecked")
	public void addProductSubscriptionRow() throws InterruptedException{
		logger.info("addProductScriptionRow()");
		// getting the rows
		Rows rows = (Rows)this.getFellow("prodSubscribe");
		// creating a new row
		Row row = new Row();
		// setting the label
		row.appendChild(new Label(""+(rows.getChildren().size())));
		// creating the product type listbox
		Listbox prodType = new Listbox();
		// setting id for product type list box
		prodType.setId("prodType"+(rows.getChildren().size()));
		// setting width, rows, mold
		prodType.setWidth("100%"); prodType.setRows(1); prodType.setMold("select");
		// now adding all product types to listbox
		prodType.getItems().addAll(cloneList(this.productTypes));
		// setting selected index to first element
		prodType.setSelectedIndex(0);
		// now adding product type listbox to row
		row.appendChild(prodType);
		// creating the product discount listbox
		Listbox prodDiscount = new Listbox();
		// setting the id for product discount listbox
		prodDiscount.setId("prodDiscount"+(rows.getChildren().size()));
		// setting width, rows, mold
		prodDiscount.setWidth("100%"); prodDiscount.setRows(1); prodDiscount.setMold("select");
		// now adding all product discount to listbox
		prodDiscount.getItems().addAll(cloneList(this.productDiscounts));
		// setting selected index to first element
		prodDiscount.setSelectedIndex(0);
		// now adding product discount listbox to row
		row.appendChild(prodDiscount);
		// adding [?] to row
		Image prodDiscountImage = new Image("/images/question.png");
		// adding style to prodDiscountInfo
		prodDiscountImage.setStyle("cursor: help");
		// creating script for info
		ZScript showInfo = ZScript.parseContent("createAppWindow.displayProdDiscount()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		// adding script to label
		prodDiscountImage.addEventHandler("onClick", pdEvent);
		// adding label to row
		row.appendChild(prodDiscountImage);
		// creating the loyalty listbox
		Listbox loyalty = new Listbox();
		// setting the id for loyalty listbox
		loyalty.setId("rewards"+(rows.getChildren().size()));
		// setting width, rows, mold
		loyalty.setWidth("100%"); loyalty.setRows(1); loyalty.setMold("select");
		// now adding all loyalty to listbox
		loyalty.getItems().addAll(cloneList(this.rewards));
		// setting selected index to first element
		loyalty.setSelectedIndex(0);
		// now adding loyalty listbox to row
		row.appendChild(loyalty);
		// adding [?] to row
		Image loyaltyImage = new Image("/images/question.png");
		// adding style to loyaltyInfo
		loyaltyImage.setStyle("cursor: help");
		// creating script for info
		showInfo = ZScript.parseContent("createAppWindow.displayLoyalty()");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		// adding script to label
		loyaltyImage.addEventHandler("onClick", pdEvent);
		// adding label to row
		row.appendChild(loyaltyImage);
		// creating the subscription listbox
		Listbox subscription = new Listbox();
		// setting the id for subscription listbox
		subscription.setId("subscribeFee"+(rows.getChildren().size()));
		// setting width, rows, mold
		subscription.setWidth("100%"); subscription.setRows(1); subscription.setMold("select");
		// now adding all subscription to listbox
		subscription.getItems().addAll(cloneList(this.subscriptionFees));
		// setting selected index to first element
		subscription.setSelectedIndex(0);
		// now adding loyalty listbox to row
		row.appendChild(subscription);
		// adding [?] to row
		Image subscriptionImage = new Image("/images/question.png");
		// adding style to subscriptionInfo
		subscriptionImage.setStyle("cursor: help");
		// creating script for info
		showInfo = ZScript.parseContent("createAppWindow.displaySubscription()");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		// adding script to label
		subscriptionImage.addEventHandler("onClick", pdEvent);
		// adding label to row
		row.appendChild(subscriptionImage);
		
		// creating the issuance listbox
		Listbox issuance = new Listbox();
		// setting the id for issuance listbox
		issuance.setId("issuanceFee"+(rows.getChildren().size()));
		// setting width, rows, mold
		issuance.setWidth("100%"); issuance.setRows(1); issuance.setMold("select");
		// now adding all issuance to listbox
		issuance.getItems().addAll(cloneList(this.issuanceFees));
		// setting selected index to first element
		issuance.setSelectedIndex(0);
		// now adding loyalty listbox to row
		row.appendChild(issuance);
		// adding [?] to row
		Image issuanceImage = new Image("/images/question.png");
		// adding style to issuanceInfo
		issuanceImage.setStyle("cursor: help");
		// creating script for info
		showInfo = ZScript.parseContent("createAppWindow.displayIssuance()");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		// adding script to label
		issuanceImage.addEventHandler("onClick", pdEvent);
		// adding label to row
		row.appendChild(issuanceImage);
		
		// adding delete image
		Image deleteImage = new Image("/images/delete.png");
		// setting the style to pointer
		deleteImage.setStyle("cursor:pointer");
		// creating script for delete
		showInfo = ZScript.parseContent("createAppWindow.deleteRow((Row)self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		// adding script to image
		deleteImage.addEventHandler("onClick", pdEvent);
		// adding image to row
		row.appendChild(deleteImage);
		rows.insertBefore(row, rows.getLastChild());
	}
	/**
	 * disable city and state textbox when SG is selected
	 * @param selectedItem - selected item in country drop down
	 */
	public void checkCountry(Listitem selectedItem, Textbox city, Textbox state){
		logger.info("checkCountry(Listitem selectedItem, Textbox city, Textbox state)");
		boolean isSGSelected = false;
		if(selectedItem.getValue().equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			isSGSelected = true;
		}
		city.setDisabled(isSGSelected);
		city.setText(null);
		state.setDisabled(isSGSelected);
		state.setText(null);
	}
	/**
	 * method to save but not submit. Note that all fields that are required, need not to be filled in.
	 * @throws InterruptedException 
	 */
	public void savePers() throws InterruptedException{
		logger.info("savePers()");
		// temporary disable the constraints.
		disablePersConstraints();
		Map<String, Object> persDetails = getPersScreenInputs();
		if(appNo!=null && appNo.length()!=0){
			persDetails.put("appNo", appNo);
			this.businessHelper.getApplicationBusiness().savePersApplication(persDetails);
			Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			String appNo = this.businessHelper.getApplicationBusiness().createPersApplication(persDetails);
			if(appNo!=null){
				Messagebox.show("Application Saved! The new application no is " + appNo, "Create Application", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}else{
				Messagebox.show("Unable to save application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
			}
		}
		
	}
	/**
	 * method to save but not submit. Note that all fields that are required, need not to be filled in.
	 * @throws InterruptedException 
	 */
	public void saveCorp() throws InterruptedException{
		logger.info("saveCorp()");
		// temporary disable the constraints.
		disableCorpConstraints();
		Map<String, Object> corpDetails = getCorpScreenInputs();
		
		String businessUnit = corpDetails.get("businessUnit").toString();
		String govtEInvFlag = corpDetails.get("govtEInvFlag").toString();
		String pubbsFlag = corpDetails.get("pubbsFlag").toString();
		boolean gotError = false;
		
		if(govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit != null && businessUnit.length()>0))
		{
			Messagebox.show("Please unselect Business Unit as it is not applicable for accounts opt for NO in Government eInvoice", "Create Application", Messagebox.OK, Messagebox.ERROR);
			gotError = true;
		}
		else if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit == null || businessUnit.length()==0))
		{
			Messagebox.show("Business Unit is required for accounts opt for Government eInvoice", "Create Application", Messagebox.OK, Messagebox.ERROR);
			gotError = true;
		}
		
		if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && !pubbsFlag.equals(NonConfigurableConstants.PUBBS_FLAG_NO))
		{
			Messagebox.show("Government eInvoice & PUBBS should not double invoice", "Create Application", Messagebox.OK, Messagebox.ERROR);
			gotError = true;
		}
		if(corpDetails.get("fiFlag") == null)
		{
			Messagebox.show("Please select FI @ Govt", "Edit Corporate", Messagebox.OK, Messagebox.ERROR);
			gotError = true;
		}
		
		if(!gotError)
		{
			if(appNo!=null && appNo.length()!=0){
				
				corpDetails.put("appNo", appNo);
				this.businessHelper.getApplicationBusiness().saveCorpApplication(corpDetails);
				Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				String appNo = this.businessHelper.getApplicationBusiness().createCorpApplication(corpDetails);
				if(appNo!=null){
					Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
					this.refresh();
				}else{
					Messagebox.show("Unable to save application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		
	}
	/**
	 * method to submit. Note that all validation is needed now.
	 * @throws InterruptedException 
	 */
	public void submitCorp() throws InterruptedException{
		logger.info("submitCorp()");
		// server validation
		// validating product type
		Set<String> prodTypeIds = new HashSet<String>();
		Rows prodSubscribes = (Rows)this.getFellow("prodSubscribe");
		for(int i=0;i<prodSubscribes.getChildren().size()-1;i++){
			Row prodSubscribe = (Row)prodSubscribes.getChildren().get(i);
			// getting the product type list
			Listbox prodTypeList = (Listbox)prodSubscribe.getChildren().get(1);
			// now adding to set and check whether set contains the product type already
			if(!prodTypeIds.add((String)prodTypeList.getSelectedItem().getValue())){
				Messagebox.show("Duplicate product types in product subscription", "Create Application", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		// now checking for external cards
		if(this.businessHelper.getApplicationBusiness().hasExternalCardSubscription(prodTypeIds)){
			Messagebox.show("Unable to submit application. Account with external card subscription exists!", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// if not product type den reject
		if(prodSubscribes.getChildren().size()==1){
			Messagebox.show("No product subscriptions", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// checking for corporate name
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		if(this.businessHelper.getApplicationBusiness().checkCorporateNameExist(acctName)){
			if(Messagebox.show("Corporate Name already exist. Continue?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
				return;
			}
		}
		// now checking RCB no
		String rcbNo = ((Textbox)this.getFellow("rcbNo")).getValue();
		if(rcbNo!=null && rcbNo.length()!=0 && this.businessHelper.getApplicationBusiness().checkRCBNoExist(rcbNo)){
			if(Messagebox.show("RCB Registration No already exist. Continue?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
				return;
			}
		}
		//dont allow - 
		Listbox recurringList = (Listbox)this.getFellow("recurring");
		System.out.println(recurringList.getSelectedItem().getValue());
		if(recurringList.getSelectedItem().getValue()==""|| recurringList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select a value for Cabcharge Recurring!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			recurringList.setFocus(true);
			return;
		}
		// checking industry
		Listbox industryList = (Listbox)this.getFellow("industryList");
		if(industryList.getSelectedItem()==null || industryList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select industry!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			industryList.setFocus(true);
			return;
		}
		// checking ace indicator
		Listbox aceIndicatorList = (Listbox)this.getFellow("aceIndicator");
		if(aceIndicatorList.getSelectedItem()==null || aceIndicatorList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select Ace Interface Indicator!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			aceIndicatorList.setFocus(true);
			return;
		}
		// checking coupa indicator
		Listbox coupaIndicatorList = (Listbox)this.getFellow("coupaIndicator");
		if(coupaIndicatorList.getSelectedItem()==null || coupaIndicatorList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select Coupa Interface Indicator!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			coupaIndicatorList.setFocus(true);
			return;
		}
		// checking fi
		Listbox fiIndicatorList = (Listbox)this.getFellow("fiFlagList");
		if(fiIndicatorList.getSelectedItem()==null || fiIndicatorList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select Fi Flag!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			fiIndicatorList.setFocus(true);
			return;
		}
		// checking salesperson
		Listbox salespersonList = (Listbox)this.getFellow("salesPerson");
		if(salespersonList.getSelectedItem()==null || salespersonList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select salesperson!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			salespersonList.setFocus(true);
			return;
		}
		// saving it first. Note that all constraints are not removed
		// Constraints are still enforced in UI
		boolean saveSuccess = false;
		boolean newApp = false;
		Map<String, Object> corpDetails = getCorpScreenInputs();
		// checking country
		String govtEInvFlag = corpDetails.get("govtEInvFlag").toString();
		String pubbsFlag = corpDetails.get("pubbsFlag").toString();
		
		if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && !pubbsFlag.equals(NonConfigurableConstants.PUBBS_FLAG_NO))
		{
			Messagebox.show("Government eInvoice & PUBBS should not double invoice", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(!corpDetails.get("countryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			if(corpDetails.get("city")==null || corpDetails.get("city").equals("") || corpDetails.get("state")==null || corpDetails.get("state").equals("")){
				Messagebox.show("Please enter the city and state", "Create Application", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if(appNo!=null && appNo.length()!=0){
			corpDetails.put("appNo", appNo);
			this.businessHelper.getApplicationBusiness().saveCorpApplication(corpDetails);
			Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
			saveSuccess = true;
		}else{
			appNo = this.businessHelper.getApplicationBusiness().createCorpApplication(corpDetails);
			if(appNo!=null){
				Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
				saveSuccess = true;
				newApp = true;
			}else{
				Messagebox.show("Unable to save application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
			}
		}
		// now submitting the application
		if(saveSuccess){
			boolean submitSuccess = this.businessHelper.getApplicationBusiness().submitApplication(appNo, (String)corpDetails.get("remarks"), this.getUserLoginIdAndDomain());
			if(submitSuccess){
				Messagebox.show("Application Submitted, Application No is "+appNo, "Create Application", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				Messagebox.show("Unable to submit application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
			}
			if(newApp){
				this.refresh();
			}else{
				this.back();
			}
		}
	}
	public void submitPers() throws InterruptedException{
		logger.info("submitPers()");
		// server validation
		// validating product type
		Set<String> prodTypeIds = new HashSet<String>();
		Rows prodSubscribes = (Rows)this.getFellow("prodSubscribe");
		for(int i=0;i<prodSubscribes.getChildren().size()-1;i++){
			Row prodSubscribe = (Row)prodSubscribes.getChildren().get(i);
			// getting the product type list
			Listbox prodTypeList = (Listbox)prodSubscribe.getChildren().get(1);
			// now adding to set and check whether set contains the product type already
			if(!prodTypeIds.add((String)prodTypeList.getSelectedItem().getValue())){
				Messagebox.show("Duplicate product types in product subscription", "Create Application", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		//dont allow - 
		Listbox recurringList = (Listbox)this.getFellow("recurring");
		System.out.println(recurringList.getSelectedItem().getValue());
		if(recurringList.getSelectedItem().getValue()==""|| recurringList.getSelectedItem().getValue()==null){
			Messagebox.show("Please select a value for Cabcharge Recurring!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			recurringList.setFocus(true);
			return;
		}
		// now checking for external cards
		if(this.businessHelper.getApplicationBusiness().hasExternalCardSubscription(prodTypeIds)){
			Messagebox.show("Unable to submit application. Account with external card subscription exists!", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// if not product type den reject
		if(prodSubscribes.getChildren().size()==1){
			Messagebox.show("No product subscriptions", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Datebox birthDatebox = (Datebox)this.getFellow("birthdate");
		if(birthDatebox.getValue()!=null && birthDatebox.getValue().after(Calendar.getInstance().getTime())){
			Messagebox.show("Birth day cannot be later than today!", "Create Application", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// checking industry
		Listitem industryItem = ((Listbox)this.getFellow("industryList")).getSelectedItem();
		if(industryItem.getValue()==null){
			Messagebox.show("Please select industry!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			return;
		}
		// checking salesperson
		Listitem salespersonItem = ((Listbox)this.getFellow("salesPerson")).getSelectedItem();
		if(salespersonItem.getValue()==null){
			Messagebox.show("Please select salesperson!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			return;
		}
		// checking job status
		Listitem jobStatusItem = ((Listbox)this.getFellow("jobStatusList")).getSelectedItem();
		if(jobStatusItem.getValue()==null){
			Messagebox.show("Please select job status!", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			return;
		}
		// saving it first. Note that all constraints are not removed
		// Constraints are still enforced in UI
		boolean saveSuccess = false;
		boolean newApp = false;
		Map<String, Object> persDetails = getPersScreenInputs();
		// checking country
		if(!persDetails.get("countryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			if(persDetails.get("city")==null || persDetails.get("city").equals("") || persDetails.get("state")==null || persDetails.get("state").equals("")){
				Messagebox.show("Please enter the city and state", "Create Application", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if(!persDetails.get("empCountryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			if(persDetails.get("empCity")==null || persDetails.get("empCity").equals("") || persDetails.get("empState")==null || persDetails.get("empState").equals("")){
				Messagebox.show("Please enter the employer city and employer state", "Create Application", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if(appNo!=null && appNo.length()!=0){
			persDetails.put("appNo", appNo);
			this.businessHelper.getApplicationBusiness().savePersApplication(persDetails);
			Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
			saveSuccess = true;
		}else{
			appNo = this.businessHelper.getApplicationBusiness().createPersApplication(persDetails);
			if(appNo!=null){
				Messagebox.show("Application Saved. The application no is "+appNo+"!", "Create Application", Messagebox.OK, Messagebox.INFORMATION);
				saveSuccess = true;
				newApp = true;
			}else{
				Messagebox.show("Unable to save application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
			}
		}
		// now submitting the application
		if(saveSuccess){
			boolean submitSuccess = this.businessHelper.getApplicationBusiness().submitApplication(appNo,(String)persDetails.get("remarks"), this.getUserLoginIdAndDomain());
			if(submitSuccess){
				Messagebox.show("Application Submitted, Application No is "+appNo, "Create Application", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				Messagebox.show("Unable to submit application. Please try again later", "Create Application", Messagebox.OK, Messagebox.ERROR);
			}
			if(newApp){
				this.refresh();
			}else{
				this.back();
			}
		}
	}
	/**
	 * method to disable all constraints in the page.
	 */
	private void disableCorpConstraints(){
		logger.info("disableCorpConstraints()");
		// array of input element with constraints
		String[] constraintedInputIds = {"acctName", "nameOnCard", "tel", "street", "postal", "creditLimit", "deposit"};
		for(int i=0;i<constraintedInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(constraintedInputIds[i]);
			Constraint nullConstraint = null;
			constraintedInput.setConstraint(nullConstraint);
		}
	}
	/**
	 * method to disable all constraints in the page.
	 */
	private void disablePersConstraints(){
		logger.info("disablePersConstraints()");
		// array of input element with constraints
		String[] constraintedInputIds = {"acctName", "nameOnCard", "nric", "tel", "street", "postal", "billStreet", "billPostal", "shipStreet", "shipPostal", "empStreet", "empPostal", "monthlyIncome", "creditLimit", "deposit", "occupation"};
		for(int i=0;i<constraintedInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(constraintedInputIds[i]);
			Constraint nullConstraint = null;
			constraintedInput.setConstraint(nullConstraint);
		}
	}
	/**
	 * method to enable all constraints in the page.
	 */
	private void enablePersConstraints(){
		logger.info("enablePersConstraints()");
		String[] constraintedInputIds = {"acctName", "nameOnCard", "nric", "tel", "street", "postal", "billStreet", "billPostal", "shipStreet", "shipPostal", "empStreet", "empPostal", "occupation"};
		for(int i=0;i<constraintedInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(constraintedInputIds[i]);
			Constraint requiredConstraint = new RequiredConstraint();
			constraintedInput.setConstraint(requiredConstraint);
		}
		String[] amountInputIds = {"monthlyIncome", "creditLimit"};
		for(int i=0;i<amountInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(amountInputIds[i]);
			Constraint requiredConstraint = null;
			switch(i){
				case 0: requiredConstraint = new RequiredAmountConstraint(); break;
				case 1: requiredConstraint = new CreditLimitConstraint(); break;
				default: requiredConstraint = new RequiredAmountConstraint();
			}
			constraintedInput.setConstraint(requiredConstraint);
		}
		String[] zeroAmountInputIds = {"deposit"};
		for(int i=0;i<zeroAmountInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(zeroAmountInputIds[i]);
			Constraint requiredConstraint = new RequiredZeroAmountConstraint();
			constraintedInput.setConstraint(requiredConstraint);
		}
	}
	/**
	 * method to enable all constraints in the page.
	 */
	private void enableCorpConstraints(){
		logger.info("enableCorpConstraints()");
		String[] constraintedInputIds = {"acctName", "nameOnCard", "tel", "street", "postal"};
		for(int i=0;i<constraintedInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(constraintedInputIds[i]);
			Constraint requiredConstraint = new RequiredConstraint();
			constraintedInput.setConstraint(requiredConstraint);
		}
		String[] amountInputIds = {"creditLimit"};
		for(int i=0;i<amountInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(amountInputIds[i]);
			Constraint requiredConstraint = null;
			switch(i){
				case 0: requiredConstraint = new CreditLimitConstraint(); break;
				default: requiredConstraint = new RequiredAmountConstraint();
			}
			constraintedInput.setConstraint(requiredConstraint);
		}
		String[] zeroAmountInputIds = {"deposit"};
		for(int i=0;i<zeroAmountInputIds.length;i++){
			InputElement constraintedInput = (InputElement)this.getFellow(zeroAmountInputIds[i]);
			Constraint requiredConstraint = new RequiredZeroAmountConstraint();
			constraintedInput.setConstraint(requiredConstraint);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		// free up the app no
		this.appNo = null;
		if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			disableCorpConstraints();
		}else{
			disablePersConstraints();
		}
		logger.info("refresh()");
		((Label)this.getFellow("appNo")).setValue("-");
		((Listitem)((Listbox)this.getFellow("eInvoice")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("invoicePrinting")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("sms")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("smsExpiry")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("recurring")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("smsTopUp")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("acctTypeList")).getItemAtIndex(0)).setSelected(true);
		this.updateProductType();
		((Textbox)this.getFellow("acctName")).setValue("");
		((Textbox)this.getFellow("nameOnCard")).setValue("");
		((Listitem)((Listbox)this.getFellow("industryList")).getItemAtIndex(0)).setSelected(true);
		((Textbox)this.getFellow("tel")).setValue("");
		((Textbox)this.getFellow("blkNo")).setValue("");
		((Textbox)this.getFellow("unitNo")).setValue("");
		((Textbox)this.getFellow("street")).setValue("");
		((Textbox)this.getFellow("building")).setValue("");
		((Textbox)this.getFellow("area")).setValue("");
		// getting country
		Listbox countryLB = ((Listbox)this.getFellow("countryList"));
		for(int i=0;i<countryLB.getItemCount();i++){
			if(ConfigurableConstants.COUNTRY_MASTER_CODE_SG.equalsIgnoreCase((String)((Listitem)((Listbox)this.getFellow("countryList")).getItemAtIndex(i)).getValue()))
			{
				((Listitem)((Listbox)this.getFellow("countryList")).getItemAtIndex(i)).setSelected(true);
				break;
			}
		}
		this.checkCountry(((Listbox)this.getFellow("countryList")).getSelectedItem(), ((Textbox)this.getFellow("city")), ((Textbox)this.getFellow("state")));
		((Textbox)this.getFellow("city")).setValue("");
		((Textbox)this.getFellow("state")).setValue("");
		((Textbox)this.getFellow("postal")).setValue("");
		((Listitem)((Listbox)this.getFellow("salutation")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("infoSource")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("entity")).getItemAtIndex(0)).setSelected(true);
		this.updateArControl(((Listbox)this.getFellow("entity")).getSelectedItem());
		((Listitem)((Listbox)this.getFellow("arAcct")).getItemAtIndex(0)).setSelected(true);
		Row buttonRow = (Row)this.getFellow("prodSubscribe").getLastChild();
		this.getFellow("prodSubscribe").getChildren().clear();
		this.getFellow("prodSubscribe").appendChild(buttonRow);
		this.addProductSubscriptionRow();
		((Decimalbox)this.getFellow("creditLimit")).setValue(null);
		((Decimalbox)this.getFellow("deposit")).setValue(null);
		((Listitem)((Listbox)this.getFellow("salesPerson")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("volumeDiscount")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("adminFees")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("earlyDiscount")).getItemAtIndex(0)).setSelected(true);
		((Listitem)((Listbox)this.getFellow("lateInterest")).getItemAtIndex(0)).setSelected(true);
		((Textbox)this.getFellow("remarks")).setValue("");
		// specific fields
		if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			((Textbox)this.getFellow("rcbNo")).setValue("");
			((Datebox)this.getFellow("rcbDate")).setValue(null);
			((Decimalbox)this.getFellow("capital")).setValue(null);
			((Textbox)this.getFellow("fax")).setValue("");
			((Textbox)this.getFellow("authPerson")).setValue("");
			((Textbox)this.getFellow("authTitle")).setValue("");
			((Listitem)((Listbox)this.getFellow("invoiceFormatList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("invoiceSortingList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("govtEInvFlagList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("businessUnitList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("aceIndicator")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("coupaIndicator")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("pubbsFlagList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("fiFlagList")).getItemAtIndex(0)).setSelected(true);
			enableCorpConstraints();
		}else{
			((Textbox)this.getFellow("nric")).setValue("");
			((Datebox)this.getFellow("birthdate")).setValue(null);
			((Textbox)this.getFellow("email")).setValue("");
			((Textbox)this.getFellow("mobile")).setValue("");
			((Textbox)this.getFellow("office")).setValue("");
			// billing address
			((Textbox)this.getFellow("billBlkNo")).setValue("");
			((Textbox)this.getFellow("billUnitNo")).setValue("");
			((Textbox)this.getFellow("billStreet")).setValue("");
			((Textbox)this.getFellow("billBuilding")).setValue("");
			((Textbox)this.getFellow("billArea")).setValue("");
			for(Object item : ((Listbox)this.getFellow("billCountryList")).getChildren()){
				if(((Listitem)item).getValue().equals("SG")){
					((Listbox)this.getFellow("billCountryList")).setSelectedItem((Listitem)item);
				}
			}
			this.checkCountry(((Listbox)this.getFellow("billCountryList")).getSelectedItem(), ((Textbox)this.getFellow("billCity")), ((Textbox)this.getFellow("billState")));
			((Textbox)this.getFellow("billCity")).setValue("");
			((Textbox)this.getFellow("billState")).setValue("");
			((Textbox)this.getFellow("billPostal")).setValue("");
			((Listitem)((Listbox)this.getFellow("billAdd")).getItemAtIndex(1)).setSelected(true);
			this.checkBill(((Listbox)this.getFellow("billAdd")).getSelectedItem());
			// shipping
			((Textbox)this.getFellow("shipBlkNo")).setValue("");
			((Textbox)this.getFellow("shipUnitNo")).setValue("");
			((Textbox)this.getFellow("shipStreet")).setValue("");
			((Textbox)this.getFellow("shipBuilding")).setValue("");
			((Textbox)this.getFellow("shipArea")).setValue("");
			for(Object item : ((Listbox)this.getFellow("shipCountryList")).getChildren()){
				if(((Listitem)item).getValue().equals("SG")){
					((Listbox)this.getFellow("shipCountryList")).setSelectedItem((Listitem)item);
				}
			}
			this.checkCountry(((Listbox)this.getFellow("shipCountryList")).getSelectedItem(), ((Textbox)this.getFellow("shipCity")), ((Textbox)this.getFellow("shipState")));
			((Textbox)this.getFellow("shipCity")).setValue("");
			((Textbox)this.getFellow("shipState")).setValue("");
			((Textbox)this.getFellow("shipPostal")).setValue("");
			((Listitem)((Listbox)this.getFellow("shipAdd")).getItemAtIndex(1)).setSelected(true);
			this.checkShip(((Listbox)this.getFellow("shipAdd")).getSelectedItem());
			// employer
			((Listitem)((Listbox)this.getFellow("jobStatusList")).getItemAtIndex(0)).setSelected(true);
			((Textbox)this.getFellow("occupation")).setValue("");
			((Listitem)((Listbox)this.getFellow("industryList")).getItemAtIndex(0)).setSelected(true);
			((Textbox)this.getFellow("employerName")).setValue("");
			((Textbox)this.getFellow("empBlkNo")).setValue("");
			((Textbox)this.getFellow("empUnitNo")).setValue("");
			((Textbox)this.getFellow("empStreet")).setValue("");
			((Textbox)this.getFellow("empBuilding")).setValue("");
			((Textbox)this.getFellow("empArea")).setValue("");
			for(Object item : ((Listbox)this.getFellow("empCountryList")).getChildren()){
				if(((Listitem)item).getValue().equals("SG")){
					((Listbox)this.getFellow("empCountryList")).setSelectedItem((Listitem)item);
				}
			}
			this.checkCountry(((Listbox)this.getFellow("empCountryList")).getSelectedItem(), ((Textbox)this.getFellow("empCity")), ((Textbox)this.getFellow("empState")));
			((Textbox)this.getFellow("empCity")).setValue("");
			((Textbox)this.getFellow("empState")).setValue("");
			((Textbox)this.getFellow("empPostal")).setValue("");
			((Decimalbox)this.getFellow("monthlyIncome")).setValue(null);
			((Decimalbox)this.getFellow("empLength")).setValue(null);
			enablePersConstraints();
		}
	}
	private List<Map<String, Object>> getSubscriptions(){
		logger.info("getSubscriptions()");
		// now getting product discounts
		List<Map<String, Object>> subscriptions = new ArrayList<Map<String, Object>>();
		Rows prodSubscribes = (Rows)this.getFellow("prodSubscribe");
		for(Object prodSubscribe : prodSubscribes.getChildren()){
			Row row = (Row)prodSubscribe;
			if(!row.equals(prodSubscribes.getLastChild())){
				Map<String, Object> details = new LinkedHashMap<String, Object>();
				// getting the product type
				String prodTypeId = (String)((Listbox)row.getChildren().get(1)).getSelectedItem().getValue();
				// putting prodTypeNo to map
				details.put("prodTypeId", prodTypeId);
				// getting the product discount
				Listitem itemProdDiscountNo = ((Listbox)row.getChildren().get(2)).getSelectedItem();
				// putting prodDiscountNo to map. if not selected, put null
				if(itemProdDiscountNo.getValue().equals("")){
					details.put("prodDiscountNo", null);
				}else{
					details.put("prodDiscountNo", (Integer)itemProdDiscountNo.getValue());
				}
				// getting the rewards
				Listitem itemRewardsNo = ((Listbox)row.getChildren().get(4)).getSelectedItem();
				// putting rewardsNo to map. if not selected, put null
				if(itemRewardsNo.getValue().equals("")){
					details.put("rewardsNo", null);
				}else{
					details.put("rewardsNo", (Integer)itemRewardsNo.getValue());
				}
				// getting the subscription fee
				Listitem itemSubscriptionNo = ((Listbox)row.getChildren().get(6)).getSelectedItem();
				// putting subscriptionNo to map. if not selected, put null
				if(itemSubscriptionNo.getValue().equals("")){
					details.put("subscriptionNo", null);
				}else{
					details.put("subscriptionNo", (Integer)itemSubscriptionNo.getValue());
				}
				// getting the issuance fee
				Listitem itemIssuanceNo = ((Listbox)row.getChildren().get(8)).getSelectedItem();
				// putting issuanceNo to map. if not selected, put null
				if(itemIssuanceNo.getValue().equals("")){
					details.put("issuanceNo", null);
				}else{
					details.put("issuanceNo", (Integer)itemIssuanceNo.getValue());
				}
				// now putting product subscription into list
				subscriptions.add(details);
			}
		}
		return subscriptions;
	}
	private Map<String, Object> getPersScreenInputs(){
		logger.info("getPersScreenInputs()");
		// getting all inputs from the screen and put it into a map
		Map<String, Object> persDetails = new LinkedHashMap<String, Object>();
		// getting common details
		getCommonDetails(persDetails);
		// getting assessment details
		getAssessmentDetails(persDetails);
		// getting specific details
		String nric = ((Textbox)this.getFellow("nric")).getValue();
		persDetails.put("nric", nric);
		Date birthdate = ((Datebox)this.getFellow("birthdate")).getValue();
		persDetails.put("birthdate", birthdate);
		String email = ((Textbox)this.getFellow("email")).getValue();
		persDetails.put("email", email);
		String mobile = ((Textbox)this.getFellow("mobile")).getValue();
		persDetails.put("mobile", mobile);
		String office = ((Textbox)this.getFellow("office")).getValue();
		persDetails.put("office", office);
		Listbox mainContactRaceList = (Listbox)this.getFellow("mainContactRace");
		if(mainContactRaceList.getSelectedItem()!=null && mainContactRaceList.getSelectedItem().getValue()!=null){
			persDetails.put("mainContactRace", (String)mainContactRaceList.getSelectedItem().getValue());
		}
		
		// getting billing address
		Listbox billSame = (Listbox)this.getFellow("billAdd");
		persDetails.put("billAdd", billSame.getSelectedItem().getValue());
		if(billSame.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			String billBlkNo = ((Textbox)this.getFellow("billBlkNo")).getValue();
			persDetails.put("billBlkNo", billBlkNo);
			String billUnitNo = ((Textbox)this.getFellow("billUnitNo")).getValue();
			persDetails.put("billUnitNo", billUnitNo);
			String billStreet = ((Textbox)this.getFellow("billStreet")).getValue();
			persDetails.put("billStreet", billStreet);
			String billBuilding = ((Textbox)this.getFellow("billBuilding")).getValue();
			persDetails.put("billBuilding", billBuilding);
			String billArea = ((Textbox)this.getFellow("billArea")).getValue();
			persDetails.put("billArea", billArea);
			if(this.getFellow("billCountryList").isVisible()){
				String billCountryListCode = (String)((Listbox)this.getFellow("billCountryList")).getSelectedItem().getValue();
				persDetails.put("billCountryListCode", billCountryListCode);
			}
			String billCity = ((Textbox)this.getFellow("billCity")).getValue();
			persDetails.put("billCity", billCity);
			String billState = ((Textbox)this.getFellow("billState")).getValue();
			persDetails.put("billState", billState);
			String billPostal = ((Textbox)this.getFellow("billPostal")).getValue();
			persDetails.put("billPostal", billPostal);
		}
		// getting shipping address
		Listbox shipSame = (Listbox)this.getFellow("shipAdd");
		persDetails.put("shipAdd", shipSame.getSelectedItem().getValue());
		if(shipSame.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			String shipBlkNo = ((Textbox)this.getFellow("shipBlkNo")).getValue();
			persDetails.put("shipBlkNo", shipBlkNo);
			String shipUnitNo = ((Textbox)this.getFellow("shipUnitNo")).getValue();
			persDetails.put("shipUnitNo", shipUnitNo);
			String shipStreet = ((Textbox)this.getFellow("shipStreet")).getValue();
			persDetails.put("shipStreet", shipStreet);
			String shipBuilding = ((Textbox)this.getFellow("shipBuilding")).getValue();
			persDetails.put("shipBuilding", shipBuilding);
			String shipArea = ((Textbox)this.getFellow("shipArea")).getValue();
			persDetails.put("shipArea", shipArea);
			if(this.getFellow("shipCountryList").isVisible()){
				String shipCountryListCode = (String)((Listbox)this.getFellow("shipCountryList")).getSelectedItem().getValue();
				persDetails.put("shipCountryListCode", shipCountryListCode);
			}
			String shipCity = ((Textbox)this.getFellow("shipCity")).getValue();
			persDetails.put("shipCity", shipCity);
			String shipState = ((Textbox)this.getFellow("shipState")).getValue();
			persDetails.put("shipState", shipState);
			String shipPostal = ((Textbox)this.getFellow("shipPostal")).getValue();
			persDetails.put("shipPostal", shipPostal);
		}
		// getting employment details
		Listbox jobStatusList = (Listbox)this.getFellow("jobStatusList");
		if(jobStatusList.getSelectedItem()!=null){
			String jobStatusCode = (String)jobStatusList.getSelectedItem().getValue();
			persDetails.put("jobStatusCode", jobStatusCode);
		}
		String occupation = ((Textbox)this.getFellow("occupation")).getValue();
		persDetails.put("occupation", occupation);
		String employerName = ((Textbox)this.getFellow("employerName")).getValue();
		persDetails.put("employerName", employerName);
		String empBlkNo = ((Textbox)this.getFellow("empBlkNo")).getValue();
		persDetails.put("empBlkNo", empBlkNo);
		String empUnitNo = ((Textbox)this.getFellow("empUnitNo")).getValue();
		persDetails.put("empUnitNo", empUnitNo);
		String empStreet = ((Textbox)this.getFellow("empStreet")).getValue();
		persDetails.put("empStreet", empStreet);
		String empBuilding = ((Textbox)this.getFellow("empBuilding")).getValue();
		persDetails.put("empBuilding", empBuilding);
		String empArea = ((Textbox)this.getFellow("empArea")).getValue();
		persDetails.put("empArea", empArea);
		String empCountryListCode = (String)((Listbox)this.getFellow("empCountryList")).getSelectedItem().getValue();
		persDetails.put("empCountryListCode", empCountryListCode);
		String empCity = ((Textbox)this.getFellow("empCity")).getValue();
		persDetails.put("empCity", empCity);
		String empState = ((Textbox)this.getFellow("empState")).getValue();
		persDetails.put("empState", empState);
		String empPostal = ((Textbox)this.getFellow("empPostal")).getValue();
		persDetails.put("empPostal", empPostal);
		BigDecimal monthlyIncome = ((Decimalbox)this.getFellow("monthlyIncome")).getValue();
		persDetails.put("monthlyIncome", monthlyIncome);
		BigDecimal empLength = ((Decimalbox)this.getFellow("empLength")).getValue();
		persDetails.put("empLength", empLength);
		List<Map<String, Object>> prodSubscriptions = getSubscriptions();
		persDetails.put("prodSubscriptions", prodSubscriptions);
		return persDetails;
	}
	private Map<String, Object> getCorpScreenInputs(){
		logger.info("getCorpScreenInputs()");
		// getting all inputs from the screen and put it into a map
		Map<String, Object> corpDetails = new LinkedHashMap<String, Object>();
		// getting common details
		getCommonDetails(corpDetails);
		// getting assessment details
		getAssessmentDetails(corpDetails);
		// getting specific details
		String rcbNo = ((Textbox)this.getFellow("rcbNo")).getValue();
		corpDetails.put("rcbNo", rcbNo);
		Date rcbDate = ((Datebox)this.getFellow("rcbDate")).getValue();
		corpDetails.put("rcbDate", rcbDate);
		BigDecimal capital = ((Decimalbox)this.getFellow("capital")).getValue();
		corpDetails.put("capital", capital);
		String fax = ((Textbox)this.getFellow("fax")).getValue();
		corpDetails.put("fax", fax);
		String authPerson = ((Textbox)this.getFellow("authPerson")).getValue();
		corpDetails.put("authPerson", authPerson);
		String authTitle = ((Textbox)this.getFellow("authTitle")).getValue();
		corpDetails.put("authTitle", authTitle);
		String projectCode = (String)((Listbox)this.getFellow("projectCodeList")).getSelectedItem().getValue();
		corpDetails.put("projectCode", projectCode);
		
		String invoiceFormat = (String)((Listbox)this.getFellow("invoiceFormatList")).getSelectedItem().getValue();
		corpDetails.put("invoiceFormat", invoiceFormat);
		String invoiceSorting = (String)((Listbox)this.getFellow("invoiceSortingList")).getSelectedItem().getValue();
		corpDetails.put("invoiceSorting", invoiceSorting);
		// Govt eInv Enhancement
		String govtEInvFlag = (String)((Listbox)this.getFellow("govtEInvFlagList")).getSelectedItem().getValue();
		corpDetails.put("govtEInvFlag", govtEInvFlag);
		String businessUnit = (String)((Listbox)this.getFellow("businessUnitList")).getSelectedItem().getValue();
		corpDetails.put("businessUnit", businessUnit);

		String pubbsFlag = (String)((Listbox)this.getFellow("pubbsFlagList")).getSelectedItem().getValue();
		corpDetails.put("pubbsFlag", pubbsFlag);
		
		String fiFlag = (String)((Listbox)this.getFellow("fiFlagList")).getSelectedItem().getValue();
		corpDetails.put("fiFlag", fiFlag);
		
		Listitem aceIndicatorList = ((Listbox)this.getFellow("aceIndicator")).getSelectedItem();
		if(aceIndicatorList.getValue()!=null && !aceIndicatorList.getValue().equals("")){
			String aceIndicator = (String)aceIndicatorList.getValue();
			corpDetails.put("aceIndicator", aceIndicator);
		}else{
			corpDetails.put("aceIndicator", null);
		}
		Listitem coupaIndicatorList = ((Listbox)this.getFellow("coupaIndicator")).getSelectedItem();
		if(coupaIndicatorList.getValue()!=null && !coupaIndicatorList.getValue().equals("")){
			String coupaIndicator = (String)coupaIndicatorList.getValue();
			corpDetails.put("coupaIndicator", coupaIndicator);
		}else{
			corpDetails.put("coupaIndicator", null);
		}
		
		List<Map<String, Object>> prodSubscriptions = getSubscriptions();
		logger.info("prodSubscriptions size = " + prodSubscriptions.size());
		corpDetails.put("prodSubscriptions", prodSubscriptions);
		return corpDetails;
	}
	private void getCommonDetails(Map<String, Object> map){
		// user id
		map.put("loginId", this.getUserLoginIdAndDomain());
		Integer accountTypeNo = (Integer)((Listbox)this.getFellow("acctTypeList")).getSelectedItem().getValue();
		map.put("accountTypeNo", accountTypeNo);
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		map.put("acctName", acctName);
		Listitem salutationListitem = ((Listbox)this.getFellow("salutation")).getSelectedItem();
		if(salutationListitem.getValue()!=null && !salutationListitem.getValue().equals("")){
			String salutationCode = (String)salutationListitem.getValue();
			map.put("salutationCode", salutationCode);
		}else{
			map.put("salutationCode", null);
		}
		String nameOnCard = ((Textbox)this.getFellow("nameOnCard")).getValue();
		map.put("nameOnCard", nameOnCard);
		String industryListCode = (String)((Listbox)this.getFellow("industryList")).getSelectedItem().getValue();
		map.put("industryListCode", industryListCode);
		String tel = ((Textbox)this.getFellow("tel")).getValue();
		map.put("tel", tel);
		String blkNo = ((Textbox)this.getFellow("blkNo")).getValue();
		map.put("blkNo", blkNo);
		String unitNo = ((Textbox)this.getFellow("unitNo")).getValue();
		map.put("unitNo", unitNo);
		String street = ((Textbox)this.getFellow("street")).getValue();
		map.put("street", street);
		String building = ((Textbox)this.getFellow("building")).getValue();
		map.put("building", building);
		String area = ((Textbox)this.getFellow("area")).getValue();
		map.put("area", area);
		String countryListCode = (String)((Listbox)this.getFellow("countryList")).getSelectedItem().getValue();
		map.put("countryListCode", countryListCode);
		String city = ((Textbox)this.getFellow("city")).getValue();
		map.put("city", city);
		String state = ((Textbox)this.getFellow("state")).getValue();
		map.put("state", state);
		String postal = ((Textbox)this.getFellow("postal")).getValue();
		map.put("postal", postal);
		Listitem infoSourceListitem = ((Listbox)this.getFellow("infoSource")).getSelectedItem();
		if(infoSourceListitem.getValue()!=null && !infoSourceListitem.getValue().equals("")){
			String infoSourceCode = (String)infoSourceListitem.getValue();
			map.put("infoSourceCode", infoSourceCode);
		}else{
			map.put("infoSourceCode", null);
		}
		Integer arControlCodeNo = (Integer)((Listbox)this.getFellow("arAcct")).getSelectedItem().getValue();
		map.put("arControlCodeNo", arControlCodeNo);
		String remarks = ((Textbox)this.getFellow("remarks")).getValue();
		map.put("remarks", remarks);
		Listitem eInvoiceItem = ((Listbox)this.getFellow("eInvoice")).getSelectedItem();
		if(eInvoiceItem.getValue()!=null && !eInvoiceItem.getValue().equals("")){
			String eInvoice = (String)eInvoiceItem.getValue();
			map.put("eInvoice", eInvoice);
		}else{
			map.put("eInvoice", null);
		}
		Listitem invoicePrintingItem = ((Listbox)this.getFellow("invoicePrinting")).getSelectedItem();
		if(invoicePrintingItem.getValue()!=null && !invoicePrintingItem.getValue().equals("")){
			String invoicePrinting = (String)invoicePrintingItem.getValue();
			map.put("invoicePrinting", invoicePrinting);
		}else{
			map.put("invoicePrinting", null);
		}
		Listitem outsourcePrintingItem = ((Listbox)this.getFellow("outsourcePrinting")).getSelectedItem();
		if(outsourcePrintingItem.getValue()!=null && !outsourcePrintingItem.getValue().equals("")){
			String outsourcePrinting = (String)outsourcePrintingItem.getValue();
			map.put("outsourcePrinting", outsourcePrinting);
		}else{
			map.put("outsourcePrinting", null);
		}
		Listitem smsItem = ((Listbox)this.getFellow("sms")).getSelectedItem();
		if(smsItem.getValue()!=null && !smsItem.getValue().equals("")){
			String sms = (String)smsItem.getValue();
			map.put("sms", sms);
		}else{
			map.put("sms", null);
		}
		Listitem smsExpiryItem = ((Listbox)this.getFellow("smsExpiry")).getSelectedItem();
		if(smsExpiryItem.getValue()!=null && !smsExpiryItem.getValue().equals("")){
			String smsExpiry = (String)smsExpiryItem.getValue();
			map.put("smsExpiry", smsExpiry);
		}else{
			map.put("smsExpiry", null);
		}
		Listitem recurringItem = ((Listbox)this.getFellow("recurring")).getSelectedItem();
		if(recurringItem.getValue()!=null && !recurringItem.getValue().equals("")){
			String recurring = (String)recurringItem.getValue();
			map.put("recurring", recurring);
		}else{
			map.put("recurring", null);
		}
		Listitem smsTopUpItem = ((Listbox)this.getFellow("smsTopUp")).getSelectedItem();
		if(smsTopUpItem.getValue()!=null && !smsTopUpItem.getValue().equals("")){
			String smsTopUp = (String)smsTopUpItem.getValue();
			map.put("smsTopUp", smsTopUp);
		}else{
			map.put("smsTopUp", null);
		}
		String printTaxInvoiceOnly = (String)((Listbox)this.getFellow("printTaxInvoiceOnlyList")).getSelectedItem().getValue();
		map.put("printTaxInvoiceOnly", printTaxInvoiceOnly);
	}
	private void getAssessmentDetails(Map<String, Object> map){
		logger.info("getAssessmentDetails()");
		BigDecimal creditLimit = ((Decimalbox)this.getFellow("creditLimit")).getValue();
		map.put("creditLimit", creditLimit);
		BigDecimal deposit = ((Decimalbox)this.getFellow("deposit")).getValue();
		map.put("deposit", deposit);
		Integer salespersonNo = (Integer)((Listbox)this.getFellow("salesPerson")).getSelectedItem().getValue();
		map.put("salespersonNo", salespersonNo);
		Listitem volumeDiscountListitem = ((Listbox)this.getFellow("volumeDiscount")).getSelectedItem();
		if(volumeDiscountListitem.getValue()!=null && !volumeDiscountListitem.getValue().equals("")){
			Integer volumeDiscountNo = (Integer)volumeDiscountListitem.getValue();
			map.put("volumeDiscountNo", volumeDiscountNo);
		}else{
			map.put("volumeDiscountNo", null);
		}
		Integer adminFeesNo = (Integer)((Listbox)this.getFellow("adminFees")).getSelectedItem().getValue();
		map.put("adminFeesNo", adminFeesNo);
		Listitem earlyDiscountListitem = ((Listbox)this.getFellow("earlyDiscount")).getSelectedItem();
		if(earlyDiscountListitem.getValue()!=null && !earlyDiscountListitem.getValue().equals("")){
			Integer earlyDiscountNo = (Integer)earlyDiscountListitem.getValue();
			map.put("earlyDiscountNo", earlyDiscountNo);
		}else{
			map.put("earlyDiscountNo", null);
		}
		Integer lateInterestNo = (Integer)((Listbox)this.getFellow("lateInterest")).getSelectedItem().getValue();
		map.put("lateInterestNo", lateInterestNo);
	}
	private void displayApplication(Map<String, Object> applicationDetails) throws InterruptedException{
		logger.info("displayApplication(Map<String, Object> applicationDetails)");
		// displaying common details
		displayCommonDetails(applicationDetails);
		if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			// changing RCB number
			if(applicationDetails.get("rcbNo")!=null){
				((Textbox)this.getFellow("rcbNo")).setValue((String)applicationDetails.get("rcbNo"));
			}
			// changing RCB date
			if(applicationDetails.get("rcbDate")!=null){
				((Datebox)this.getFellow("rcbDate")).setValue((Date)applicationDetails.get("rcbDate"));
			}
			// changing capital
			if(applicationDetails.get("capital")!=null){
				((Decimalbox)this.getFellow("capital")).setValue((BigDecimal)applicationDetails.get("capital"));
			}
			// changing fax
			if(applicationDetails.get("fax")!=null){
				((Textbox)this.getFellow("fax")).setValue((String)applicationDetails.get("fax"));
			}
			// changing authorized person salutation
			if(applicationDetails.get("authSalCode")!=null){
				Listbox salutation = (Listbox)this.getFellow("salutation");
				for(Object sal : salutation.getItems()){
					if(((Listitem)sal).getValue().equals(applicationDetails.get("authSalCode"))){
						((Listitem)sal).setSelected(true);
						break;
					}
				}
			}
			// changing authorized person
			if(applicationDetails.get("authPerson")!=null){
				((Textbox)this.getFellow("authPerson")).setValue((String)applicationDetails.get("authPerson"));
			}
			// changing authorized person title
			if(applicationDetails.get("authTitle")!=null){
				((Textbox)this.getFellow("authTitle")).setValue((String)applicationDetails.get("authTitle"));
			}
			// changing project code
			if(applicationDetails.get("projectCode")!=null){
				Listbox projectCodeList = (Listbox)this.getFellow("projectCodeList");
				for(Object booleanObj : projectCodeList.getItems()){
					Listitem booleanItem = (Listitem)booleanObj;
					if(applicationDetails.get("projectCode").equals(booleanItem.getValue())){
						booleanItem.setSelected(true);
						break;
					}
				}
			}
			
			Listbox invoiceFormats = (Listbox)this.getFellow("invoiceFormatList");
			for(Object invoiceFormat : invoiceFormats.getChildren()){
				if(((Listitem)invoiceFormat).getValue().equals(applicationDetails.get("invoiceFormat"))){
					((Listitem)invoiceFormat).setSelected(true);
					break;
				}
			}
			Listbox invoiceSortings = (Listbox)this.getFellow("invoiceSortingList");
			for(Object invoiceSorting : invoiceSortings.getChildren()){
				if(((Listitem)invoiceSorting).getValue().equals(applicationDetails.get("invoiceSorting"))){
					((Listitem)invoiceSorting).setSelected(true);
					break;
				}
			}
			
			if(applicationDetails.get("gvtEInvoiceFlag") != null)
			{
				Listbox govtEInvFlagLists = (Listbox)this.getFellow("govtEInvFlagList");
				for(Object govtEInvFlagList : govtEInvFlagLists.getChildren()){
					if(((Listitem)govtEInvFlagList).getValue().equals(applicationDetails.get("gvtEInvoiceFlag"))){
						((Listitem)govtEInvFlagList).setSelected(true);
						break;
					}
				}
			}
			if(applicationDetails.get("pubbsFlag") != null)
			{
				Listbox pubbsFlagLists = (Listbox)this.getFellow("pubbsFlagList");
				for(Object pubbsFlagList : pubbsFlagLists.getChildren()){
					if(((Listitem)pubbsFlagList).getValue().equals(applicationDetails.get("pubbsFlag"))){
						((Listitem)pubbsFlagList).setSelected(true);
						break;
					}
				}
			}
			if(applicationDetails.get("fiFlag") != null)
			{
				Listbox fiFlagLists = (Listbox)this.getFellow("fiFlagList");
				for(Object fiFlagList : fiFlagLists.getChildren()){
					if(((Listitem)fiFlagList).getValue() != null)
					{
						if(((Listitem)fiFlagList).getValue().equals(applicationDetails.get("fiFlag"))){
							((Listitem)fiFlagList).setSelected(true);
							break;
						}
					}
				}
			}
			
			if(applicationDetails.get("businessUnit") != null)
			{
				Listbox businessUnitLists = (Listbox)this.getFellow("businessUnitList");
				for(Object businessUnitList : businessUnitLists.getChildren()){
					if(((Listitem)businessUnitList).getValue().equals(ConfigurableConstants.getMasterTableByMasterNo(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, Integer.parseInt(applicationDetails.get("businessUnit").toString())).getMasterCode())){
						((Listitem)businessUnitList).setSelected(true);
						break;
					}
				}
			}
			//aceIndicator
			Listbox aceIndicator = (Listbox)this.getFellow("aceIndicator");
			for(Object item : aceIndicator.getItems()){
				if(((Listitem)item).getValue() != null)
				{
					if(((Listitem)item).getValue().equals(applicationDetails.get("aceIndicator"))){
						((Listitem)item).setSelected(true);
						break;
					}
				}
			}
			
		}else{
			// changing salutation
			if(applicationDetails.get("salutationCode")!=null){
				Listbox salutation = (Listbox)this.getFellow("salutation");
				for(Object sal : salutation.getItems()){
					if(((Listitem)sal).getValue().equals(applicationDetails.get("salutationCode"))){
						((Listitem)sal).setSelected(true);
						break;
					}
				}
			}
			// changing nric
			if(applicationDetails.get("nric")!=null){
				((Textbox)this.getFellow("nric")).setValue((String)applicationDetails.get("nric"));
			}
			// changing email
			if(applicationDetails.get("email")!=null){
				((Textbox)this.getFellow("email")).setValue((String)applicationDetails.get("email"));
			}
			// changing date of birth
			if(applicationDetails.get("birthdate")!=null){
				((Datebox)this.getFellow("birthdate")).setValue((Date)applicationDetails.get("birthdate"));
			}
			// changing mobile number
			if(applicationDetails.get("mobile")!=null){
				((Textbox)this.getFellow("mobile")).setValue((String)applicationDetails.get("mobile"));
			}
			// changing office number
			if(applicationDetails.get("office")!=null){
				((Textbox)this.getFellow("office")).setValue((String)applicationDetails.get("office"));
			}
			// changing billing address same as main
			for(Object listitem : this.getFellow("billAdd").getChildren()){
				if(((Listitem)listitem).getValue().equals(applicationDetails.get("billAdd"))){
					((Listitem)listitem).setSelected(true);
					this.checkBill((Listitem)listitem);
				}
			}
			// changing billing block number
			if(applicationDetails.get("billBlkNo")!=null){
				((Textbox)this.getFellow("billBlkNo")).setValue((String)applicationDetails.get("billBlkNo"));
			}
			// changing billing unit number
			if(applicationDetails.get("billUnitNo")!=null){
				((Textbox)this.getFellow("billUnitNo")).setValue((String)applicationDetails.get("billUnitNo"));
			}
			// changing billing street
			if(applicationDetails.get("billStreet")!=null){
				((Textbox)this.getFellow("billStreet")).setValue((String)applicationDetails.get("billStreet"));
			}
			// changing billing building
			if(applicationDetails.get("billBuilding")!=null){
				((Textbox)this.getFellow("billBuilding")).setValue((String)applicationDetails.get("billBuilding"));
			}
			// changing billing area
			if(applicationDetails.get("billArea")!=null){
				((Textbox)this.getFellow("billArea")).setValue((String)applicationDetails.get("billArea"));
			}
			// changing billing country
			Listbox billCountryList = (Listbox)this.getFellow("billCountryList");
			for(Object country : billCountryList.getItems()){
				if(((Listitem)country).getValue().equals(applicationDetails.get("billCountryListCode"))){
					((Listitem)country).setSelected(true);
					this.checkCountry((Listitem)country, (Textbox)this.getFellow("billCity"), (Textbox)this.getFellow("billState"));
					break;
				}
			}
			// changing billing city
			if(applicationDetails.get("billCity")!=null){
				((Textbox)this.getFellow("billCity")).setValue((String)applicationDetails.get("billCity"));
			}
			// changing billing state
			if(applicationDetails.get("billState")!=null){
				((Textbox)this.getFellow("billState")).setValue((String)applicationDetails.get("billState"));
			}
			// changing billing postal
			if(applicationDetails.get("billPostal")!=null){
				((Textbox)this.getFellow("billPostal")).setValue((String)applicationDetails.get("billPostal"));
			}
			for(Object listitem : this.getFellow("shipAdd").getChildren()){
				if(((Listitem)listitem).getValue().equals(applicationDetails.get("shipAdd"))){
					((Listitem)listitem).setSelected(true);
					this.checkShip((Listitem)listitem);
				}
			}
			// changing shipping block number
			if(applicationDetails.get("shipBlkNo")!=null){
				((Textbox)this.getFellow("shipBlkNo")).setValue((String)applicationDetails.get("shipBlkNo"));
			}
			// changing shipping unit number
			if(applicationDetails.get("shipUnitNo")!=null){
				((Textbox)this.getFellow("shipUnitNo")).setValue((String)applicationDetails.get("shipUnitNo"));
			}
			// changing shipping street
			if(applicationDetails.get("shipStreet")!=null){
				((Textbox)this.getFellow("shipStreet")).setValue((String)applicationDetails.get("shipStreet"));
			}
			// changing shipping building
			if(applicationDetails.get("shipBuilding")!=null){
				((Textbox)this.getFellow("shipBuilding")).setValue((String)applicationDetails.get("shipBuilding"));
			}
			// changing shipping area
			if(applicationDetails.get("shipArea")!=null){
				((Textbox)this.getFellow("shipArea")).setValue((String)applicationDetails.get("shipArea"));
			}
			// changing shipping country
			Listbox shipCountryList = (Listbox)this.getFellow("shipCountryList");
			for(Object country : shipCountryList.getItems()){
				if(((Listitem)country).getValue().equals(applicationDetails.get("shipCountryListCode"))){
					((Listitem)country).setSelected(true);
					this.checkCountry((Listitem)country, (Textbox)this.getFellow("shipCity"), (Textbox)this.getFellow("shipState"));
					break;
				}
			}
			// changing shipping city
			if(applicationDetails.get("shipCity")!=null){
				((Textbox)this.getFellow("shipCity")).setValue((String)applicationDetails.get("shipCity"));
			}
			// changing shipping state
			if(applicationDetails.get("shipState")!=null){
				((Textbox)this.getFellow("shipState")).setValue((String)applicationDetails.get("shipState"));
			}
			// changing shipping postal
			if(applicationDetails.get("shipPostal")!=null){
				((Textbox)this.getFellow("shipPostal")).setValue((String)applicationDetails.get("shipPostal"));
			}
			// changing job status
			Listbox jobStatusList = (Listbox)this.getFellow("jobStatusList");
			for(Object jobStatus : jobStatusList.getItems()){
				Listitem jobStatusItem = (Listitem)jobStatus;
				if(jobStatusItem.getValue()==applicationDetails.get("jobStatusListCode")){
					jobStatusItem.setSelected(true);
					break;
				}else if(jobStatusItem.getValue()!=null && jobStatusItem.getValue().equals(applicationDetails.get("jobStatusListCode"))){
					jobStatusItem.setSelected(true);
					break;
				}
			}
			// changing occupation
			if(applicationDetails.get("occupation")!=null){
				((Textbox)this.getFellow("occupation")).setValue((String)applicationDetails.get("occupation"));
			}
			// changing employee name
			if(applicationDetails.get("employerName")!=null){
				((Textbox)this.getFellow("employerName")).setValue((String)applicationDetails.get("employerName"));
			}
			// changing employer block number
			if(applicationDetails.get("empBlkNo")!=null){
				((Textbox)this.getFellow("empBlkNo")).setValue((String)applicationDetails.get("empBlkNo"));
			}
			// changing employer unit number
			if(applicationDetails.get("empUnitNo")!=null){
				((Textbox)this.getFellow("empUnitNo")).setValue((String)applicationDetails.get("empUnitNo"));
			}
			// changing employer street
			if(applicationDetails.get("empStreet")!=null){
				((Textbox)this.getFellow("empStreet")).setValue((String)applicationDetails.get("empStreet"));
			}
			// changing employer building
			if(applicationDetails.get("empBuilding")!=null){
				((Textbox)this.getFellow("empBuilding")).setValue((String)applicationDetails.get("empBuilding"));
			}
			// changing employer area
			if(applicationDetails.get("empArea")!=null){
				((Textbox)this.getFellow("empArea")).setValue((String)applicationDetails.get("empArea"));
			}
			// changing employer country
			Listbox empCountryList = (Listbox)this.getFellow("empCountryList");
			for(Object country : empCountryList.getItems()){
				if(((Listitem)country).getValue().equals(applicationDetails.get("empCountryListCode"))){
					((Listitem)country).setSelected(true);
					this.checkCountry((Listitem)country, (Textbox)this.getFellow("empCity"), (Textbox)this.getFellow("empState"));
					break;
				}
			}
			// changing employer city
			if(applicationDetails.get("empCity")!=null){
				((Textbox)this.getFellow("empCity")).setValue((String)applicationDetails.get("empCity"));
			}
			// changing employer status
			if(applicationDetails.get("empState")!=null){
				((Textbox)this.getFellow("empState")).setValue((String)applicationDetails.get("empState"));
			}
			// changing employer postal
			if(applicationDetails.get("empPostal")!=null){
				((Textbox)this.getFellow("empPostal")).setValue((String)applicationDetails.get("empPostal"));
			}
			// changing monthly income
			if(applicationDetails.get("monthlyIncome")!=null){
				((Decimalbox)this.getFellow("monthlyIncome")).setValue((BigDecimal)applicationDetails.get("monthlyIncome"));
			}
			// changing length of employment
			if(applicationDetails.get("empLength")!=null){
				((Decimalbox)this.getFellow("empLength")).setValue(new BigDecimal((Integer)applicationDetails.get("empLength")));
			}
			
			if(applicationDetails.get("mainContactRace")!=null){
				Listbox mainContactRaceList = (Listbox)this.getFellow("mainContactRace");
				for(Object item : mainContactRaceList.getItems()){
					if(((Listitem)item).getValue().equals(applicationDetails.get("mainContactRace"))){
						((Listitem)item).setSelected(true);
						break;
					}
				}
			}
		}
	}
	private void displayCommonDetails(Map<String, Object> applicationDetails) throws InterruptedException{
		// changing application number
		((Label)this.getFellow("appNo")).setValue(((String)applicationDetails.get("appNo")));
		// changing account type
		Listbox acctTypeList = (Listbox)this.getFellow("acctTypeList");
		for(Object acctType : acctTypeList.getItems()){
			if(((Listitem)acctType).getValue().equals(applicationDetails.get("acctTypeListNo"))){
				((Listitem)acctType).setSelected(true);
				this.updateProductType();
				break;
			}
		}
		// changing application name
		if(applicationDetails.get("acctName")!=null){
			((Textbox)this.getFellow("acctName")).setValue((String)applicationDetails.get("acctName"));
		}
		// changing name on card
		if(applicationDetails.get("nameOnCard")!=null){
			((Textbox)this.getFellow("nameOnCard")).setValue((String)applicationDetails.get("nameOnCard"));
		}
		// changing telephone
		if(applicationDetails.get("tel")!=null){
			((Textbox)this.getFellow("tel")).setValue((String)applicationDetails.get("tel"));
		}
		// changing block number
		if(applicationDetails.get("blkNo")!=null){
			((Textbox)this.getFellow("blkNo")).setValue((String)applicationDetails.get("blkNo"));
		}
		// changing unit number
		if(applicationDetails.get("unitNo")!=null){
			((Textbox)this.getFellow("unitNo")).setValue((String)applicationDetails.get("unitNo"));
		}
		// changing street
		if(applicationDetails.get("street")!=null){
			((Textbox)this.getFellow("street")).setValue((String)applicationDetails.get("street"));
		}
		// changing building
		if(applicationDetails.get("building")!=null){
			((Textbox)this.getFellow("building")).setValue((String)applicationDetails.get("building"));
		}
		// changing area
		if(applicationDetails.get("area")!=null){
			((Textbox)this.getFellow("area")).setValue((String)applicationDetails.get("area"));
		}
		// changing country
		Listbox countryList = (Listbox)this.getFellow("countryList");
		for(Object country : countryList.getItems()){
			if(((Listitem)country).getValue().equals(applicationDetails.get("countryListCode"))){
				((Listitem)country).setSelected(true);
				this.checkCountry(((Listitem)country), (Textbox)this.getFellow("city"), (Textbox)this.getFellow("state"));
				break;
			}
		}
		// changing city
		if(applicationDetails.get("city")!=null){
			((Textbox)this.getFellow("city")).setValue((String)applicationDetails.get("city"));
		}
		// changing status
		if(applicationDetails.get("state")!=null){
			((Textbox)this.getFellow("state")).setValue((String)applicationDetails.get("state"));
		}
		// changing postal
		if(applicationDetails.get("postal")!=null){
			((Textbox)this.getFellow("postal")).setValue((String)applicationDetails.get("postal"));
		}
		// changing remarks
		if(applicationDetails.get("remarks")!=null){
			((Textbox)this.getFellow("remarks")).setValue((String)applicationDetails.get("remarks"));
		}
		// changing industry
		Listbox industryList = (Listbox)this.getFellow("industryList");
		for(Object industry : industryList.getItems()){
			Listitem industryItem = (Listitem)industry;
			if(industryItem.getValue()==applicationDetails.get("industryListCode")){
				industryItem.setSelected(true);
				break;
			}
			if(industryItem.getValue()!=null && industryItem.getValue().equals(applicationDetails.get("industryListCode"))){
				industryItem.setSelected(true);
				break;
			}
		}
		// changing information source
		if(applicationDetails.get("infoSourceCode")!=null){
			Listbox infoSource = (Listbox)this.getFellow("infoSource");
			for(Object info : infoSource.getItems()){
				if(((Listitem)info).getValue().equals(applicationDetails.get("infoSourceCode"))){
					((Listitem)info).setSelected(true);
					break;
				}
			}
		}
		// changing eInvoice
		Listbox eInvoice = (Listbox)this.getFellow("eInvoice");
		for(Object item : eInvoice.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("eInvoice"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		// changing eInvoice
		Listbox invoicePrinting = (Listbox)this.getFellow("invoicePrinting");
		for(Object item : invoicePrinting.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("invoicePrinting"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		// changing sms
		Listbox sms = (Listbox)this.getFellow("sms");
		for(Object item : sms.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("sms"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		// changing expiry sms
		Listbox smsExpiry = (Listbox)this.getFellow("smsExpiry");
		for(Object item : smsExpiry.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("smsExpiry"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		
		// changing recurring
		Listbox recurring = (Listbox)this.getFellow("recurring");
		for(Object item : recurring.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("recurring"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		
		// changing top up sms
		Listbox smsTopUp = (Listbox)this.getFellow("smsTopUp");
		for(Object item : smsTopUp.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("smsTopUp"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		
		// outsource printing
		Listbox outsourcePrinting = (Listbox)this.getFellow("outsourcePrinting");
		for(Object item : outsourcePrinting.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("outsourcePrinting"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		
		//printTaxinvOnly
		Listbox printTaxInvOnly = (Listbox)this.getFellow("printTaxInvoiceOnlyList");
		for(Object item : printTaxInvOnly.getItems()){
			if(((Listitem)item).getValue().equals(applicationDetails.get("printTaxInvOnly"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		
		// changing Entity and AR Control
		// specially for AR
		// to hold selected entity
		Integer selectedEntityNo = null;
		// to hold selected ar control
		Integer selectedArControlNo = null;
		// getting all entities
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		// looping thru all entities
		for(Integer entityNo : entities.keySet()){
			// to know whether ar control is found
			boolean found = false;
			// getting all ar controls
			Map<Integer, Map<String, String>> arControls = MasterSetup.getEntityManager().getAllDetails(entityNo);
			// looping thru all ar controls
			for(Integer arControlNo : arControls.keySet()){
				// if ar control is found
				if(arControlNo.equals(applicationDetails.get("arControlCodeNo"))){
					selectedEntityNo = entityNo;
					selectedArControlNo = arControlNo;
					found = true;
					break;
				}
			}
			if(found){
				break;
			}
		}
		// if both is not null
		if(selectedEntityNo!=null && selectedArControlNo!=null){
			// setting entity list box
			Listbox entityListbox = (Listbox)this.getFellow("entity");
			for(Object entity : entityListbox.getItems()){
				if(((Listitem)entity).getValue().equals(selectedEntityNo)){
					((Listitem)entity).setSelected(true);
					this.updateArControl((Listitem)entity);
					break;
				}
			}
			// setting ar control list box
			Listbox arAccts = (Listbox)this.getFellow("arAcct");
			for(Object arAcct : arAccts.getItems()){
				if(((Listitem)arAcct).getValue().equals(selectedArControlNo)){
					((Listitem)arAcct).setSelected(true);
					break;
				}
			}
		}
		displayAssessmentDetails(applicationDetails);
		displayProductSubscriptions(applicationDetails);
	}
	private void displayAssessmentDetails(Map<String, Object> applicationDetails) throws InterruptedException{
		// changing credit limit
		if(applicationDetails.get("creditLimit")!=null){
			((Decimalbox)this.getFellow("creditLimit")).setValue((BigDecimal)applicationDetails.get("creditLimit"));
		}
		// changing deposit
		if(applicationDetails.get("deposit")!=null){
			((Decimalbox)this.getFellow("deposit")).setValue((BigDecimal)applicationDetails.get("deposit"));
		}
		// changing sales person
		Listbox salesPerson = (Listbox)this.getFellow("salesPerson");
		for(Object person : salesPerson.getItems()){
			Listitem personItem = (Listitem)person;
			if(personItem.getValue()==applicationDetails.get("salesPersonNo")){
				personItem.setSelected(true);
				break;
			}
			if(personItem.getValue()!=null && personItem.getValue().equals(applicationDetails.get("salesPersonNo"))){
				personItem.setSelected(true);
				break;
			}
		}
		// changing volume discount
		if(applicationDetails.get("volumeDiscountNo")!=null){
			Listbox volumeDiscount = (Listbox)this.getFellow("volumeDiscount");
			for(Object volume : volumeDiscount.getItems()){
				if(((Listitem)volume).getValue().equals(applicationDetails.get("volumeDiscountNo"))){
					((Listitem)volume).setSelected(true);
					break;
				}
			}
		}
		// changing admin fee
		Listbox adminFees = (Listbox)this.getFellow("adminFees");
		for(Object adminFee : adminFees.getItems()){
			if(((Listitem)adminFee).getValue().equals(applicationDetails.get("adminFeeNo"))){
				((Listitem)adminFee).setSelected(true);
				break;
			}
		}
		// changing early payment
		if(applicationDetails.get("earlyPaymentNo")!=null){
			Listbox earlyDiscount = (Listbox)this.getFellow("earlyDiscount");
			for(Object early : earlyDiscount.getItems()){
				if(((Listitem)early).getValue().equals(applicationDetails.get("earlyPaymentNo"))){
					((Listitem)early).setSelected(true);
					break;
				}
			}
		}
		// changing late payment
		Listbox lateInterest = (Listbox)this.getFellow("lateInterest");
		for(Object late : lateInterest.getItems()){
			if(((Listitem)late).getValue().equals(applicationDetails.get("latePaymentNo"))){
				((Listitem)late).setSelected(true);
				break;
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void displayProductSubscriptions(Map<String, Object> applicationDetails) throws InterruptedException{
		// changing product types subscription
		Set<Map<String, Object>> prodSubscriptions = (Set<Map<String, Object>>)applicationDetails.get("prodSubscriptions");
		Rows prodSubscribes = (Rows)this.getFellow("prodSubscribe");
		int counter = 0;
		for(Map<String, Object> prodSubscription : prodSubscriptions){
			counter++;
			Row prodSubscribe;
			if(prodSubscribes.getChildren().size()== counter+1){
				prodSubscribe = (Row)prodSubscribes.getChildren().get(counter-1);
			}else{
				this.addProductSubscriptionRow();
				prodSubscribe = (Row)prodSubscribes.getChildren().get(counter-1);
			}
			// setting product type
			Listbox prodTypeListbox = (Listbox)prodSubscribe.getChildren().get(1);
			for(Object prodType : prodTypeListbox.getItems()){
				if(((Listitem)prodType).getValue().equals(prodSubscription.get("productTypeId"))){
					((Listitem)prodType).setSelected(true);
					break;
				}
			}
			// setting product discount
			if(prodSubscription.get("productDiscountPlanMasterNo")!=null){
				Listbox prodDiscountListbox = (Listbox)prodSubscribe.getChildren().get(2);
				for(Object prodDiscount : prodDiscountListbox.getItems()){
					if(((Listitem)prodDiscount).getValue().equals(prodSubscription.get("productDiscountPlanMasterNo"))){
						((Listitem)prodDiscount).setSelected(true);
						break;
					}
				}
			}
			// setting rewards
			if(prodSubscription.get("rewardsPlanMasterNo")!=null){
				Listbox rewardsListbox = (Listbox)prodSubscribe.getChildren().get(4);
				for(Object rewards : rewardsListbox.getItems()){
					if(((Listitem)rewards).getValue().equals(prodSubscription.get("rewardsPlanMasterNo"))){
						((Listitem)rewards).setSelected(true);
						break;
					}
				}
			}
			// setting subscription fee
			if(prodSubscription.get("subscriptionFeeMasterNo")!=null){
				Listbox subscribeFeeListbox = (Listbox)prodSubscribe.getChildren().get(6);
				for(Object subscribeFee : subscribeFeeListbox.getItems()){
					if(((Listitem)subscribeFee).getValue().equals(prodSubscription.get("subscriptionFeeMasterNo"))){
						((Listitem)subscribeFee).setSelected(true);
						break;
					}
				}
			}
			
			// setting issuance fee
			if(prodSubscription.get("issuanceFeeMasterNo")!=null){
				Listbox issuanceFeeListbox = (Listbox)prodSubscribe.getChildren().get(8);
				for(Object issuanceFee : issuanceFeeListbox.getItems()){
					if(((Listitem)issuanceFee).getValue().equals(prodSubscription.get("issuanceFeeMasterNo"))){
						((Listitem)issuanceFee).setSelected(true);
						break;
					}
				}
			}
			
			prodSubscribe.setValue(prodSubscription.get("applicationProductNo"));
		}
	}
	public void checkExistingApp(String acctTemplate) throws InterruptedException{
		logger.info("checkExistingApp()");
		this.acctTemplate = acctTemplate;
		if(accountTypes.isEmpty()){
			Messagebox.show("There is no account type created! Please create account types first!", "Create Application", Messagebox.OK, Messagebox.EXCLAMATION);
			((Button)this.getFellow("saveBtn")).setDisabled(true);
			((Button)this.getFellow("submitBtn")).setDisabled(true);
			return;
		}
		if(productTypes.isEmpty()){
			Messagebox.show("There is no products type available! Please subscribe product types first!", "Create Application", Messagebox.OK, Messagebox.EXCLAMATION);
			((Button)this.getFellow("saveBtn")).setDisabled(true);
			((Button)this.getFellow("submitBtn")).setDisabled(true);
			return;
		}
		if(acctTemplate.equalsIgnoreCase("C"))
		{
			((Listitem)((Listbox)this.getFellow("invoiceFormatList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("invoiceSortingList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("govtEInvFlagList")).getItemAtIndex(0)).setSelected(true);
			((Listitem)((Listbox)this.getFellow("businessUnitList")).getItemAtIndex(0)).setSelected(true);
		}
		// changing the approver
		Label approveOfficers = (Label)this.getFellow("approveOfficers");
		approveOfficers.setValue(this.businessHelper.getApplicationBusiness().getApplicationApprovers(acctTemplate ,1));
		if(this.appNo!=null && this.appNo.length()!=0){
			this.getFellow("clearButton").setVisible(false);
			if(this.acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				displayApplication(this.businessHelper.getApplicationBusiness().getCorpApplication(appNo));
			}else{
				displayApplication(this.businessHelper.getApplicationBusiness().getPersApplication(appNo));
			}
			// manage app. Enabling cancel button
			this.getFellow("cancelButton").setVisible(true);
		}else if(this.copyAppNo!=null && this.copyAppNo.length()!=0){
			this.getFellow("clearButton").setVisible(false);
			if(this.acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				displayApplication(this.businessHelper.getApplicationBusiness().getCorpApplication(copyAppNo));
			}else{
				displayApplication(this.businessHelper.getApplicationBusiness().getPersApplication(copyAppNo));
			}
			// setting appNo to "-"
			((Label)this.getFellow("appNo")).setValue("-");
			// manage app. Enabling cancel button
			this.getFellow("cancelButton").setVisible(true);
		}else{
			// new application. enabling clear button
			this.getFellow("cancelButton").setVisible(false);
			this.getFellow("clearButton").setVisible(true);
		}
	}
	public void deleteRow(Row row) throws InterruptedException{
		logger.info("deleteRow(Row row)");
		// showing confirmation page
		int reply = Messagebox.show("Confirm delete row?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		// if user selects ok
		if(reply == Messagebox.OK){
			// getting the listbox
			Rows rows = (Rows)row.getParent();
			// now removing the row
			rows.removeChild(row);
			// now rearrange the numbering
			if(rows.getChildren().size()==1){
				addProductSubscriptionRow();
			}else{
				for(int i=0;i<rows.getChildren().size()-1;i++){
					Row remainRow = (Row)rows.getChildren().get(i);
					((Label)remainRow.getChildren().get(0)).setValue((i+1) + "");
					// changing prod type id
					((Listbox)remainRow.getChildren().get(1)).setId("prodType"+(i+1));
					// changing prod discount
					((Listbox)remainRow.getChildren().get(2)).setId("prodDiscount"+(i+1));
					((Listbox)remainRow.getChildren().get(4)).setId("rewards"+(i+1));
					((Listbox)remainRow.getChildren().get(6)).setId("subscribeFee"+(i+1));
					((Listbox)remainRow.getChildren().get(8)).setId("issuanceFee"+(i+1));
				}
			}
		}
	}
	public void deleteAllRow() throws InterruptedException{
		logger.info("deleteAllRow()");
		// showing confirmation page
		int reply = Messagebox.show("Confirm delete all row?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		// if user selects ok
		if(reply == Messagebox.OK){
			// getting the button row
			Row buttonRow = (Row)this.getFellow("prodSubscribe").getLastChild();
			this.getFellow("prodSubscribe").getChildren().clear();
			this.getFellow("prodSubscribe").appendChild(buttonRow);
			this.addProductSubscriptionRow();
		}
	}
	public void checkBill(Listitem selected){
		logger.info("checkBill(Listitem selected)");
		Rows rows = (Rows)this.getFellow("billAddress");
		if(selected.getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			// displaying all rows
			for(Object eachRow : rows.getChildren()){
				((Row)eachRow).setVisible(true);
			}
		}else{
			for(int i=1;i<rows.getChildren().size();i++){
				// hiding all rows except first row
				((Row)rows.getChildren().get(i)).setVisible(false);
			}
		}
	}
	public void checkShip(Listitem selected){
		logger.info("checkShip(Listitem selected)");
		Rows rows = (Rows)this.getFellow("shipAddress");
		if(selected.getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			// displaying all rows
			for(Object eachRow : rows.getChildren()){
				((Row)eachRow).setVisible(true);
			}
		}else{
			for(int i=1;i<rows.getChildren().size();i++){
				// hiding all rows except first row
				((Row)rows.getChildren().get(i)).setVisible(false);
			}
		}
	}
	public List<Listitem> getBooleanList() {
		return cloneList(booleanList);
	}
	public void setBooleanList(List<Listitem> booleanList) {
		this.booleanList = booleanList;
	}
	public List<Listitem> getBooleanList2() {
		return cloneList(booleanList2);
	}
	public void setBooleanList2(List<Listitem> booleanList2) {
		this.booleanList2 = booleanList2;
	}
	public List<Listitem> getIndustries(){
		logger.info("getIndustries()");
		return this.industries;
	}
	public List<Listitem> getAccountTypes(){
		logger.info("getAccountTypes()");
		return this.accountTypes;
	}
	public List<Listitem> getCountries(){
		logger.info("getCountries()");
		return cloneList(this.countries);
	}
	public List<Listitem> getSalutations(){
		logger.info("getSalutations()");
		return this.salutations;
	}
	public List<Listitem> getInfoSources(){
		logger.info("getInfoSources()");
		return this.infoSources;
	}
	public List<Listitem> getEntities(){
		logger.info("getEntities()");
		return this.entities;
	}
	public List<Listitem> getProductTypes(){
		logger.info("getProductTypes()");
		return this.productTypes;
	}
	public List<Listitem> getProductDiscounts(){
		logger.info("getProductDiscounts()");
		return this.productDiscounts;
	}
	public List<Listitem> getRewards(){
		logger.info("getRewards()");
		return this.rewards;
	}
	public List<Listitem> getSubscriptionFees(){
		logger.info("getSubscriptionFees()");
		return this.subscriptionFees;
	}
	public List<Listitem> getIssuanceFees(){
		logger.info("getIssuanceFees()");
		return this.issuanceFees;
	}
	public List<Listitem> getSalespersons(){
		logger.info("getSalespersons()");
		return this.salespersons;
	}
	public List<Listitem> getVolumeDiscounts(){
		logger.info("getVolumeDiscounts()");
		return this.volumeDiscounts;
	}
	public List<Listitem> getAdminFees(){
		logger.info("getAdminFees()");
		return this.adminFees;
	}
	public List<Listitem> getEarlyPayments(){
		logger.info("getEarlyPayments()");
		return this.earlyPayments;
	}
	public List<Listitem> getLateInterests(){
		logger.info("getLateInterests()");
		return this.lateInterests;
	}
	public List<Listitem> getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(List<Listitem> jobStatus) {
		this.jobStatus = jobStatus;
	}
	public void cancel() throws InterruptedException{
		if(Messagebox.show("Continue without saving?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.back();
		}
	}
	public void clear() throws InterruptedException{
		if(Messagebox.show("Clear without saving?", "Create Application", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.refresh();
		}
	}
	public List<Listitem> getOptionalBooleanList() {
		return cloneList(optionalBooleanList);
	}
	public void setOptionalList(List<Listitem> optionalBooleanList) {
		this.optionalBooleanList = optionalBooleanList;
	}
	
	public List<Listitem> getInvoicePrintingList() {
		
		List<Listitem> invoicePrintingList = new ArrayList<Listitem>();
		invoicePrintingList.add(new Listitem(NonConfigurableConstants.INVOICE_PRINTING.get(NonConfigurableConstants.INVOICE_PRINTING_DOUBLE_SIDED), NonConfigurableConstants.INVOICE_PRINTING_DOUBLE_SIDED));
		invoicePrintingList.add(new Listitem(NonConfigurableConstants.INVOICE_PRINTING.get(NonConfigurableConstants.INVOICE_PRINTING_SINGLE_SIDED), NonConfigurableConstants.INVOICE_PRINTING_SINGLE_SIDED));
		return invoicePrintingList;
	}
	public List<Listitem> getInvoiceFormats(){
		logger.info("getInvoiceFormats()");
		return this.invoiceFormats;
	}
	public List<Listitem> getInvoiceSorts(){
		logger.info("getInvoiceSorts()");
		return this.invoiceSorts;
	}
	public List<Listitem> getGovtEInvFlags(){
		logger.info("getGovtEInvFlags()");
		return this.govtEInvFlags;
	}
	public List<Listitem> getBusinessUnits(){
		logger.info("getBusinessUnits()");
		return this.businessUnits;
	}
	public List<Listitem> getRace() {
		return cloneList(race);
	}
	public List<Listitem> getDaysUnits(){
		logger.info("getDaysUnits()");
		return this.daysUnits;
	}	
}