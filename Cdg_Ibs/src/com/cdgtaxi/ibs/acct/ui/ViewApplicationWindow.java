package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.EntityMasterManager;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewApplicationWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewApplicationWindow.class);
	private Map<String, String> industries = new HashMap<String, String>();
	private Map<Integer, String> accountTypes = new HashMap<Integer, String>();
	private Map<String, String> countries = new HashMap<String, String>();
	private Map<String, String> salutations = new HashMap<String, String>();
	private Map<String, String> infoSources = new HashMap<String, String>();
	private Map<Integer, String> entities = new HashMap<Integer, String>();
	private Map<String, String> productTypes = new HashMap<String, String>();
	private Map<Integer, String> productDiscounts = new HashMap<Integer, String>();
	private Map<Integer, String> rewards = new HashMap<Integer, String>();
	private Map<Integer, String> subscriptionFees = new HashMap<Integer, String>();
	private Map<Integer, String> issuanceFees = new HashMap<Integer, String>();
	private Map<Integer, String> salespersons = new HashMap<Integer, String>();
	private Map<Integer, String> volumeDiscounts = new HashMap<Integer, String>();
	private Map<Integer, String> adminFees = new HashMap<Integer, String>();
	private Map<Integer, String> earlyPayments = new HashMap<Integer, String>();
	private Map<Integer, String> lateInterests = new HashMap<Integer, String>();
	private Map<String, String> booleanList = new HashMap<String, String>();
	private Map<String, String> jobStatus = new HashMap<String, String>();
	private String acctTemplate = null;
	private String appNo = null;
	@SuppressWarnings("unchecked")
	public ViewApplicationWindow() throws InterruptedException{
		// getting params from current desktop
		Map<String, String> map = Executions.getCurrent().getArg();
		// getting application no from params
		String appNo = map.get("appNo");
		// if it is not null or nothing
		if(appNo!=null && appNo.length()!=0){
			this.appNo = appNo;
			// getting data from non configurable constants
			// getting boolean
			booleanList.put(NonConfigurableConstants.BOOLEAN_NO, NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO));
			booleanList.put(NonConfigurableConstants.BOOLEAN_YES, NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES));
			// getting data from master table
			// getting industry
			Map<String, String> masterIndustry = ConfigurableConstants.getIndustries();
			for(String masterCode : masterIndustry.keySet()){
				this.industries.put(masterCode, masterIndustry.get(masterCode));
			}
			// getting job status
			Map<String, String> masterJobStatus = ConfigurableConstants.getJobStatuses();
			for(String masterCode : masterJobStatus.keySet()){
				this.jobStatus.put(masterCode, masterJobStatus.get(masterCode));
			}
			// getting country
			Map<String, String> masterCountry = ConfigurableConstants.getCountries();
			for(String masterCode : masterCountry.keySet()){
				this.countries.put(masterCode, masterCountry.get(masterCode));
			}
			// getting salutation
			salutations.put(null, "-");
			Map<String, String> masterSalutation = ConfigurableConstants.getSalutations();
			for(String masterCode : masterSalutation.keySet()){
				this.salutations.put(masterCode, masterSalutation.get(masterCode));
			}
			// getting information source
			infoSources.put(null, "-");
			Map<String, String> masterInfo = ConfigurableConstants.getInformationSources();
			for(String masterCode : masterInfo.keySet()){
				this.infoSources.put(masterCode, masterInfo.get(masterCode));
			}
			// getting entities
			Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
			for(Integer em : ems.keySet()){
				this.entities.put(em, ems.get(em));
			}
			// getting product discount
			productDiscounts.put(null, "-");
			Map<Integer, String> pdms = MasterSetup.getProductDiscountManager().getAllMasters();
			for(Integer pdm : pdms.keySet()){
				this.productDiscounts.put(pdm, pdms.get(pdm));
			}
			// getting subscription fee
			subscriptionFees.put(null, "-");
			Map<Integer, String> sfms = MasterSetup.getSubscriptionManager().getAllMasters();
			for(Integer sfm : sfms.keySet()){
				this.subscriptionFees.put(sfm, sfms.get(sfm));
			}
			// getting issuance fee
			issuanceFees.put(null, "-");
			Map<Integer, String> ifms = MasterSetup.getIssuanceManager().getAllMasters();
			for(Integer ifm : ifms.keySet()){
				this.issuanceFees.put(ifm, ifms.get(ifm));
			}
			// getting rewards plans
			rewards.put(null, "-");
			Map<Integer, String> rms = MasterSetup.getRewardsManager().getAllMasters();
			for(Integer rm : rms.keySet()){
				this.rewards.put(rm, rms.get(rm));
			}
			// getting salesperson
			Map<Integer, String> sms = MasterSetup.getSalespersonManager().getAllMasters();
			for(Integer sm : sms.keySet()){
				this.salespersons.put(sm, sms.get(sm));
			}
			// getting volume discount
			volumeDiscounts.put(null, "-");
			Map<Integer, String> vdms = MasterSetup.getVolumeDiscountManager().getAllMasters();
			for(Integer vdm : vdms.keySet()){
				this.volumeDiscounts.put(vdm, vdms.get(vdm));
			}
			// getting admin fees
			Map<Integer, String> afms = MasterSetup.getAdminFeeManager().getAllMasters();
			for(Integer afm : afms.keySet()){
				this.adminFees.put(afm, afms.get(afm));
			}
			// getting early payment
			earlyPayments.put(null, "-");
			Map<Integer, String> epms = MasterSetup.getEarlyPaymentManager().getAllMasters();
			for(Integer epm : epms.keySet()){
				this.earlyPayments.put(epm, epms.get(epm));
			}
			// getting late interest
			Map<Integer, String> lpms = MasterSetup.getLatePaymentManager().getAllMasters();
			for(Integer lpm : lpms.keySet()){
				this.lateInterests.put(lpm, lpms.get(lpm));
			}
			// getting product types
			Map<String, String> productTypes = this.businessHelper.getApplicationBusiness().getAllProductTypes();
			// adding back the product types
			for(String productTypeId : productTypes.keySet()){
				this.productTypes.put(productTypeId, productTypes.get(productTypeId));
			}
		}else{// if null or nothing
			// show error message and redirect use back to search window
			Messagebox.show("No application number found! Redirecting back to search window.", "View Corporate Application", Messagebox.OK, Messagebox.ERROR);
			this.back();
		}
	}
	public void loadApp(String acctTemplate) throws InterruptedException{
		logger.info("checkExistingApp()");
		this.acctTemplate = acctTemplate;
		// getting account types
		Map<Integer, String> acctTypes = this.businessHelper.getApplicationBusiness().getAccountTypes(this.acctTemplate);
		for(Integer acctTypeNo : acctTypes.keySet()){
			this.accountTypes.put(acctTypeNo, acctTypes.get(acctTypeNo));
		}
		if(this.acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){

			if(!this.checkUriAccess(Uri.CREATE_CORP_APP))
				((Button)this.getFellow("copyBtn")).setDisabled(true);
			
			// getting the application from business layer
			Map<String, Object> corpApp = this.businessHelper.getApplicationBusiness().getCorpApplication(appNo);
			// if null, throw error to user and redirect user to search window
			if(corpApp != null){
				// now showing the application.
				displayCommonFields(corpApp);
				if(corpApp.get("rcbNo")!=null){
					Label rcbNo = (Label)this.getFellow("rcbNo");
					rcbNo.setValue((String)corpApp.get("rcbNo"));
				}
				if(corpApp.get("rcbDate")!=null){
					Label rcbDate = (Label)this.getFellow("rcbDate");
					rcbDate.setValue(DateUtil.convertTimestampToStr((Timestamp)corpApp.get("rcbDate"), DateUtil.GLOBAL_DATE_FORMAT));
				}
				if(corpApp.get("corpDate")!=null){
					Label corpDate = (Label)this.getFellow("corpDate");
					corpDate.setValue(DateUtil.convertTimestampToStr((Timestamp)corpApp.get("corpDate"), DateUtil.GLOBAL_DATE_FORMAT));
				}
				if(corpApp.get("capital")!=null){
					String capital = StringUtil.bigDecimalToString((BigDecimal)corpApp.get("capital"), StringUtil.GLOBAL_DECIMAL_FORMAT);
					((Label)this.getFellow("capital")).setValue(capital);
				}
				if(corpApp.get("fax")!=null){
					Label fax = (Label)this.getFellow("fax");
					fax.setValue((String)corpApp.get("fax"));
				}
				if(corpApp.get("projectCode")!=null){
					((Label)this.getFellow("projectCode")).setValue(NonConfigurableConstants.BOOLEAN.get(corpApp.get("projectCode")));
				}
				Label salutation = (Label)this.getFellow("salutation");
				salutation.setValue(salutations.get(corpApp.get("authSalCode")));
				if(corpApp.get("authPerson")!=null){
					Label authPerson = (Label)this.getFellow("authPerson");
					authPerson.setValue((String)corpApp.get("authPerson"));
				}
				if(corpApp.get("authTitle")!=null){
					Label authTitle = (Label)this.getFellow("authTitle");
					authTitle.setValue((String)corpApp.get("authTitle"));
				}
				
				if(corpApp.get("invoiceFormat") != null) 
					((Label) this.getFellow("invoiceFormat")).setValue( this.businessHelper.getApplicationBusiness().getInvoiceFullString((String)corpApp.get("invoiceFormat"), "invoiceFormat"));
				if(corpApp.get("invoiceSorting") != null)
					((Label) this.getFellow("invoiceSorting")).setValue(this.businessHelper.getApplicationBusiness().getInvoiceFullString((String)corpApp.get("invoiceSorting"), "invoiceSorting"));
				if(corpApp.get("gvtEInvoiceFlag") != null)
					((Label) this.getFellow("govtEInvoice")).setValue(this.businessHelper.getApplicationBusiness().getInvoiceFullString((String)corpApp.get("gvtEInvoiceFlag"), "govtEInvoice"));
				if(corpApp.get("businessUnit") != null)
					((Label) this.getFellow("businessUnit")).setValue(this.businessHelper.getApplicationBusiness().getInvoiceFullString((String)corpApp.get("businessUnit"), "businessUnit"));
				if(corpApp.get("aceIndicator") != null)
					((Label) this.getFellow("aceIndicator")).setValue(booleanList.get(corpApp.get("aceIndicator")));
				if(corpApp.get("coupaIndicator") != null)
					((Label) this.getFellow("coupaIndicator")).setValue(booleanList.get(corpApp.get("coupaIndicator")));
				//pubbs flag
				if(corpApp.get("pubbsFlag")!= null)
					((Label)this.getFellow("pubbs")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(corpApp.get("pubbsFlag")));
				//fi flag
				if(corpApp.get("fiFlag")!= null)
					((Label)this.getFellow("fiFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(corpApp.get("fiFlag")));
			
			}else{
				Messagebox.show("No matching application found! Redirecting back to search window.", "View Corporate Application", Messagebox.OK, Messagebox.ERROR);
				this.back();
			}
		}else{
			if(!this.checkUriAccess(Uri.CREATE_PERS_APP))
				((Button)this.getFellow("copyBtn")).setDisabled(true);
			
			Map<String, Object> persApp = this.businessHelper.getApplicationBusiness().getPersApplication(appNo);
			// if null, throw error to user and redirect user to search window
			if(persApp != null){
				// now showing the application.
				displayCommonFields(persApp);
				
				Label salutation = (Label)this.getFellow("salutation");
				salutation.setValue(salutations.get(persApp.get("salutationCode")));
				if(persApp.get("nric")!=null){
					Label nric = (Label)this.getFellow("nric");
					nric.setValue(StringUtil.maskNric((String)persApp.get("nric")));
				}
				if(persApp.get("birthdate")!=null){
					Label birthdate = (Label)this.getFellow("birthdate");
					birthdate.setValue(DateUtil.convertTimestampToStr((Timestamp)persApp.get("birthdate"), DateUtil.GLOBAL_DATE_FORMAT));
				}
				if(persApp.get("email")!=null){
					Label email = (Label)this.getFellow("email");
					email.setValue((String)persApp.get("email"));
				}
				if(persApp.get("mobile")!=null){
					Label mobile = (Label)this.getFellow("mobile");
					mobile.setValue((String)persApp.get("mobile"));	
				}
				if(persApp.get("office")!=null){
					Label office = (Label)this.getFellow("office");
					office.setValue((String)persApp.get("office"));
				}
				// billing address
				Label billAdd = (Label)this.getFellow("billAdd");
				billAdd.setValue(booleanList.get(persApp.get("billAdd")));
				if(persApp.get("billAdd").equals(NonConfigurableConstants.BOOLEAN_NO)){
					// display all billing address fields
					Rows billAddress = (Rows)this.getFellow("billAddress");
					for(Object row : billAddress.getChildren()){
						((Row)row).setVisible(true);
					}
					if(persApp.get("billBlkNo")!=null){
						Label billBlkNo = (Label)this.getFellow("billBlkNo");
						billBlkNo.setValue((String)persApp.get("billBlkNo"));
					}
					if(persApp.get("billUnitNo")!=null){
						Label billUnitNo = (Label)this.getFellow("billUnitNo");
						billUnitNo.setValue((String)persApp.get("billUnitNo"));
					}
					Label billStreet = (Label)this.getFellow("billStreet");
					billStreet.setValue((String)persApp.get("billStreet"));
					if(persApp.get("billBuilding")!=null){
						Label billBuilding = (Label)this.getFellow("billBuilding");
						billBuilding.setValue((String)persApp.get("billBuilding"));
					}
					if(persApp.get("billArea")!=null){
						Label billArea = (Label)this.getFellow("billArea");
						billArea.setValue((String)persApp.get("billArea"));
					}
					Label billCountry = (Label)this.getFellow("billCountry");
					billCountry.setValue(countries.get(persApp.get("billCountryListCode")));
					Label billCity = (Label)this.getFellow("billCity");
					Label billState = (Label)this.getFellow("billState");
					if(persApp.get("billCountryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
						billCity.getParent().setVisible(false);
					}else{
						billCity.setValue((String)persApp.get("billCity"));
						billState.setValue((String)persApp.get("billState"));
					}
					Label billPostal = (Label)this.getFellow("billPostal");
					billPostal.setValue((String)persApp.get("billPostal"));
				}
				// shipping address
				Label shipAdd = (Label)this.getFellow("shipAdd");
				shipAdd.setValue(booleanList.get(persApp.get("shipAdd")));
				if(persApp.get("shipAdd").equals(NonConfigurableConstants.BOOLEAN_NO)){
					// display all billing address fields
					Rows shipAddress = (Rows)this.getFellow("shipAddress");
					for(Object row : shipAddress.getChildren()){
						((Row)row).setVisible(true);
					}
					if(persApp.get("shipBlkNo")!=null){
						Label shipBlkNo = (Label)this.getFellow("shipBlkNo");
						shipBlkNo.setValue((String)persApp.get("shipBlkNo"));
					}
					if(persApp.get("shipUnitNo")!=null){
						Label shipUnitNo = (Label)this.getFellow("shipUnitNo");
						shipUnitNo.setValue((String)persApp.get("shipUnitNo"));
					}
					Label shipStreet = (Label)this.getFellow("shipStreet");
					shipStreet.setValue((String)persApp.get("shipStreet"));
					if(persApp.get("shipBuilding")!=null){
						Label shipBuilding = (Label)this.getFellow("shipBuilding");
						shipBuilding.setValue((String)persApp.get("shipBuilding"));
					}
					if(persApp.get("shipArea")!=null){
						Label shipArea = (Label)this.getFellow("shipArea");
						shipArea.setValue((String)persApp.get("shipArea"));
					}
					Label shipCountry = (Label)this.getFellow("shipCountry");
					shipCountry.setValue(countries.get(persApp.get("shipCountryListCode")));
					Label shipCity = (Label)this.getFellow("shipCity");
					Label shipState = (Label)this.getFellow("shipState");
					if(persApp.get("shipCountryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
						shipCity.getParent().setVisible(false);
					}else{
						shipCity.setValue((String)persApp.get("shipCity"));
						shipState.setValue((String)persApp.get("shipState"));
					}
					Label shipPostal = (Label)this.getFellow("shipPostal");
					shipPostal.setValue((String)persApp.get("shipPostal"));
				}
				//employment details
				Label jobStatus = (Label)this.getFellow("jobStatus");
				jobStatus.setValue(this.jobStatus.get(persApp.get("jobStatusListCode")));
				if(persApp.get("occupation")!=null){
					Label occupation = (Label)this.getFellow("occupation");
					occupation.setValue((String)persApp.get("occupation"));
				}
				if(persApp.get("employerName")!=null){
					Label employerName = (Label)this.getFellow("employerName");
					employerName.setValue((String)persApp.get("employerName"));
				}
				// employee address
				if(persApp.get("empBlkNo")!=null){
					Label empBlkNo = (Label)this.getFellow("empBlkNo");
					empBlkNo.setValue((String)persApp.get("empBlkNo"));
				}
				if(persApp.get("empUnitNo")!=null){
					Label empUnitNo = (Label)this.getFellow("empUnitNo");
					empUnitNo.setValue((String)persApp.get("empUnitNo"));
					
				}
				if(persApp.get("empStreet")!=null){
					Label empStreet = (Label)this.getFellow("empStreet");
					empStreet.setValue((String)persApp.get("empStreet"));
				}
				if(persApp.get("empBuilding")!=null){
					Label empBuilding = (Label)this.getFellow("empBuilding");
					empBuilding.setValue((String)persApp.get("empBuilding"));
				}
				if(persApp.get("empArea")!=null){
					Label empArea = (Label)this.getFellow("empArea");
					empArea.setValue((String)persApp.get("empArea"));
				}
				Label empCountry = (Label)this.getFellow("empCountry");
				empCountry.setValue(countries.get(persApp.get("empCountryListCode")));
				Label empCity = (Label)this.getFellow("empCity");
				Label empState = (Label)this.getFellow("empState");
				if(persApp.get("empCountryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
					empCity.getParent().setVisible(false);
				}else{
					empCity.setValue((String)persApp.get("empCity"));
					empState.setValue((String)persApp.get("empState"));
				}
				if(persApp.get("empPostal")!=null){
					Label empPostal = (Label)this.getFellow("empPostal");
					empPostal.setValue((String)persApp.get("empPostal"));
				}
				if(persApp.get("monthlyIncome")!=null){
					Label monthlyIncome = (Label)this.getFellow("monthlyIncome");
					monthlyIncome.setValue(StringUtil.bigDecimalToString((BigDecimal)persApp.get("monthlyIncome"), StringUtil.GLOBAL_DECIMAL_FORMAT));
				}
				if(persApp.get("empLength")!=null){
					Label empLength = (Label)this.getFellow("empLength");
					empLength.setValue(persApp.get("empLength").toString());
				}
			}else{
				Messagebox.show("No matching application found! Redirecting back to search window.", "View Corporate Application", Messagebox.OK, Messagebox.ERROR);
				this.back();
			}
		}
	}
	private void displayCommonFields(Map<String, Object> appDetails){
		Label appNo = (Label)this.getFellow("appNo");
		appNo.setValue((String)appDetails.get("appNo"));
		if(appDetails.get("appDate")!=null){
			Label appDate = (Label)this.getFellow("appDate");
			appDate.setValue(DateUtil.convertTimestampToStr((Timestamp)appDetails.get("appDate"), DateUtil.GLOBAL_DATE_FORMAT));
		}
		Label appStatus = (Label)this.getFellow("appStatus");
		appStatus.setValue(NonConfigurableConstants.APPLICATION_STATUS.get(appDetails.get("appStatus")));
		Label custNo = (Label)this.getFellow("custNo");
		custNo.setValue((String)appDetails.get("custNo"));
		Label acctType = (Label)this.getFellow("acctType");
		acctType.setValue(accountTypes.get(appDetails.get("acctTypeListNo")));
		if(appDetails.get("acctName")!=null){
			Label acctName = (Label)this.getFellow("acctName");
			acctName.setValue((String)appDetails.get("acctName"));
		}
		if(appDetails.get("nameOnCard")!=null){
			Label nameOnCard = (Label)this.getFellow("nameOnCard");
			nameOnCard.setValue((String)appDetails.get("nameOnCard"));
		}
		if(appDetails.get("blkNo")!=null){
			Label blkNo = (Label)this.getFellow("blkNo");
			blkNo.setValue((String)appDetails.get("blkNo"));
		}
		if(appDetails.get("unitNo")!=null){
			Label unitNo = (Label)this.getFellow("unitNo");
			unitNo.setValue((String)appDetails.get("unitNo"));
		}
		if(appDetails.get("street")!=null){
			Label street = (Label)this.getFellow("street");
			street.setValue((String)appDetails.get("street"));
		}
		if(appDetails.get("building")!=null){
			Label building = (Label)this.getFellow("building");
			building.setValue((String)appDetails.get("building"));
		}
		if(appDetails.get("area")!=null){
			Label area = (Label)this.getFellow("area");
			area.setValue((String)appDetails.get("area"));
		}
		if(appDetails.get("eInvoice")!=null){
			((Label)this.getFellow("eInvoice")).setValue(NonConfigurableConstants.BOOLEAN.get(appDetails.get("eInvoice")));
		}
		Label country = (Label)this.getFellow("country");
		country.setValue(countries.get(appDetails.get("countryListCode")));
		Label city = (Label)this.getFellow("city");
		Label state = (Label)this.getFellow("state");
		if(appDetails.get("countryListCode").equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			city.getParent().setVisible(false);
		}else{
			city.setValue((String)appDetails.get("city"));
			state.setValue((String)appDetails.get("state"));
		}
		if(appDetails.get("postal")!=null){
			Label postal = (Label)this.getFellow("postal");
			postal.setValue((String)appDetails.get("postal"));
		}
		if(appDetails.get("tel")!=null){
			Label tel = (Label)this.getFellow("tel");
			tel.setValue((String)appDetails.get("tel"));
		}
		Label industry = (Label)this.getFellow("industry");
		industry.setValue(industries.get(appDetails.get("industryListCode")));
		Label infoSource = (Label)this.getFellow("infoSource");
		infoSource.setValue(this.infoSources.get(appDetails.get("infoSourceCode")));
		if(appDetails.get("requester")!=null){
			((Label)this.getFellow("requester")).setValue((String)appDetails.get("requester"));
		}
		if(appDetails.get("reqDate")!=null){
			((Label)this.getFellow("reqDate")).setValue((String)appDetails.get("reqDate"));
		}
		if(appDetails.get("reqTime")!=null){
			((Label)this.getFellow("reqTime")).setValue((String)appDetails.get("reqTime"));
		}
		if(appDetails.get("remarks")!=null){
			Label remarks = (Label)this.getFellow("remarks");
			remarks.setValue((String)appDetails.get("remarks"));
		}
		if(NonConfigurableConstants.BOOLEAN_YES.equals(appDetails.get("hasLvl1"))){
			Label approver1Remarks = (Label)this.getFellow("approver1Remarks");
			if(appDetails.get("approver1Remarks")!=null){
				approver1Remarks.setValue((String)appDetails.get("approver1Remarks"));
			}else{
				approver1Remarks.setValue("-");
			}
			approver1Remarks.getParent().setVisible(true);
			Label approver1Label = (Label)this.getFellow("approver1Label");
			approver1Label.setValue(approver1Label.getValue() + "\n(" + appDetails.get("approver1") + ")");
		}
		if(NonConfigurableConstants.BOOLEAN_YES.equals(appDetails.get("hasLvl2"))){
			Label approver2Remarks = (Label)this.getFellow("approver2Remarks");
			if(appDetails.get("approver2Remarks")!=null){
				approver2Remarks.setValue((String)appDetails.get("approver2Remarks"));
			}else{
				approver2Remarks.setValue("-");
			}
			approver2Remarks.getParent().setVisible(true);
			Label approver2Label = (Label)this.getFellow("approver2Label");
			approver2Label.setValue(approver2Label.getValue() + "\n(" + appDetails.get("approver2") + ")");
		}
		Label outsourcePrinting = (Label) this.getFellow("outsourcePrinting");
		outsourcePrinting.setValue(booleanList.get(appDetails.get("outsourcePrinting")));
		
		Label printTaxInvOnly = (Label) this.getFellow("printTaxInvOnly");
		printTaxInvOnly.setValue(booleanList.get(appDetails.get("printTaxInvOnly")));
		
		if(appDetails.get("recurring")!=null){
			((Label)this.getFellow("recurring")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(appDetails.get("recurring")));
		}else{
			((Label)this.getFellow("recurring")).setValue("-");
		}
		
		displayAssessment(appDetails);
		displaySubscription(appDetails);
	}
	private void displayAssessment(Map<String, Object> appDetails){
		Label approveOfficers = (Label)this.getFellow("approveOfficers");
		if(appDetails.get("appStatus").equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL)){
			approveOfficers.setValue(this.businessHelper.getApplicationBusiness().getApplicationApprovers(acctTemplate, 2));
		}else{
			approveOfficers.setValue(this.businessHelper.getApplicationBusiness().getApplicationApprovers(acctTemplate, 1));
		}
		if(appDetails.get("creditLimit")!=null){
			Label creditLimit = (Label)this.getFellow("creditLimit");
			creditLimit.setValue(StringUtil.bigDecimalToString((BigDecimal)appDetails.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		if(appDetails.get("deposit")!=null){
			Label deposit = (Label)this.getFellow("deposit");
			deposit.setValue(StringUtil.bigDecimalToString((BigDecimal)appDetails.get("deposit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		Label salesPerson = (Label)this.getFellow("salesPerson");
		salesPerson.setValue(this.salespersons.get(appDetails.get("salesPersonNo")));
		Label volumeDiscount = (Label)this.getFellow("volumeDiscount");
		volumeDiscount.setValue(this.volumeDiscounts.get(appDetails.get("volumeDiscountNo")));
		Label adminFees = (Label)this.getFellow("adminFees");
		adminFees.setValue(this.adminFees.get(appDetails.get("adminFeeNo")));
		Label earlyDiscount = (Label)this.getFellow("earlyDiscount");
		earlyDiscount.setValue(this.earlyPayments.get(appDetails.get("earlyPaymentNo")));
		Label lateInterest = (Label)this.getFellow("lateInterest");
		lateInterest.setValue(this.lateInterests.get(appDetails.get("latePaymentNo")));
		Label entity = (Label)this.getFellow("entity");
		Label arAcct = (Label)this.getFellow("arAcct");
		// to hold selected entity
		String selectedEntity = null;
		// to hold selected ar control
		String selectedArControl = null;
		// getting all entities
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		// looping thru all entities
		for(Integer entityNo : entities.keySet()){
			// getting all ar controls
			Map<Integer, Map<String, String>> arControls = MasterSetup.getEntityManager().getAllDetails(entityNo);
			// looping thru all ar controls
			for(Integer arControlNo : arControls.keySet()){
				// if ar control is found
				if(arControlNo.equals(appDetails.get("arControlCodeNo"))){
					selectedEntity = entities.get(entityNo);
					selectedArControl = arControls.get(arControlNo).get(EntityMasterManager.DETAIL_AR_CODE);
					break;
				}
			}
			if(selectedEntity!=null && selectedArControl!=null){
				// setting entity list box
				entity.setValue(selectedEntity);
				// setting ar control list box
				arAcct.setValue(selectedArControl);break;
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void displaySubscription(Map<String, Object> appDetails){
		//prodSubscriptions
		Rows prodSubscribe = (Rows)this.getFellow("prodSubscribe");
		Set<Map<String, Object>> prodSubscriptions = (Set<Map<String, Object>>)appDetails.get("prodSubscriptions");
		for(Iterator<Map<String, Object>> prodIter = prodSubscriptions.iterator();prodIter.hasNext();){
			Map<String, Object> prodSubscription = prodIter.next();
			Row productSubscribe = new Row();
			productSubscribe.appendChild(new Label((prodSubscribe.getChildren().size()+1)+""));
			productSubscribe.appendChild(new Label(productTypes.get(prodSubscription.get("productTypeId"))));
			productSubscribe.appendChild(new Label(productDiscounts.get(prodSubscription.get("productDiscountPlanMasterNo"))));
			// adding [?] to row
			Image prodDiscountImage = new Image("/images/question.png");
			// adding style to prodDiscountInfo
			prodDiscountImage.setStyle("cursor: help");
			// creating script for info
			ZScript showInfo = ZScript.parseContent("viewAppWindow.displayProdDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			// adding script to label
			prodDiscountImage.addEventHandler("onClick", pdEvent);
			// adding label to row
			productSubscribe.appendChild(prodDiscountImage);
			productSubscribe.appendChild(new Label(rewards.get(prodSubscription.get("rewardsPlanMasterNo"))));
			// adding [?] to row
			Image loyaltyImage = new Image("/images/question.png");
			// adding style to loyaltyInfo
			loyaltyImage.setStyle("cursor: help");
			// creating script for info
			showInfo = ZScript.parseContent("viewAppWindow.displayLoyalty()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			// adding script to label
			loyaltyImage.addEventHandler("onClick", pdEvent);
			// adding label to row
			productSubscribe.appendChild(loyaltyImage);
			
			productSubscribe.appendChild(new Label(subscriptionFees.get(prodSubscription.get("subscriptionFeeMasterNo"))));
			// adding [?] to row
			Image subscriptionImage = new Image("/images/question.png");
			// adding style to subscriptionInfo
			subscriptionImage.setStyle("cursor: help");
			// creating script for info
			showInfo = ZScript.parseContent("viewAppWindow.displaySubscription()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			// adding script to label
			subscriptionImage.addEventHandler("onClick", pdEvent);
			productSubscribe.appendChild(subscriptionImage);
			
			productSubscribe.appendChild(new Label(issuanceFees.get(prodSubscription.get("issuanceFeeMasterNo"))));
			// adding [?] to row
			Image issuanceImage = new Image("/images/question.png");
			// adding style to subscriptionInfo
			issuanceImage.setStyle("cursor: help");
			// creating script for info
			showInfo = ZScript.parseContent("viewAppWindow.displayIssuance()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			// adding script to label
			issuanceImage.addEventHandler("onClick", pdEvent);
			productSubscribe.appendChild(issuanceImage);
			
			
			prodSubscribe.appendChild(productSubscribe);
		}
	}
	public void copy() throws InterruptedException{
		logger.info("copy()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("copyAppNo", appNo);
		if(this.acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			this.forward(Uri.CREATE_CORP_APP, params);
		}else{
			this.forward(Uri.CREATE_PERS_APP, params);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		this.back();
	}
}
