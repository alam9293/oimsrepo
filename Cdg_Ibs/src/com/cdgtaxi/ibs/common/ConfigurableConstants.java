package com.cdgtaxi.ibs.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.master.dao.ConstantDao;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public class ConfigurableConstants {
	//MAX QUERY RESULT
	public static final String MAX_QUERY_RESULT = "MQR";
	public static final String MAX_REPORT_RECORD = "MRR";

	public static final String INDUSTRY_MASTER_CODE = "IND";
	public static final String COUNTRY_MASTER_CODE = "CTRY";
	public static final String COUNTRY_MASTER_CODE_SG = "SG";
	public static final String INFORMATION_SOURCE_MASTER_CODE = "IS";
	public static final String VEHICLE_TYPE_MASTER_CODE = "VT";
	public static final String JOB_STATUS_MASTER_CODE = "EJS";
	public static final String SALUTATION_MASTER_CODE = "SAL";
	public static final String RACE_MASTER_CODE = "CRACE";
	public static final String APPROVER_LIMIT = "AL";
	public static final String RELATIONSHIP_TO_ACCOUNT_CODE = "RP";
	public static final String VEHICLE_TRIP_TYPE = "VTT";
	public static final String SERVICE_PROVIDER = "SPR";
	public static final String DOWNLOAD_TRIP_MAP_VEHICLE_TYPE = "DTMVT";

	// accounts
	public static final String ACCT_SUSPEND_REASON = "AS";
	public static final String ACCT_REACTIVATE_REASON = "AR";
	public static final String ACCT_TERMINATE_REASON = "AT";
	public static final String ACCT_TERMINATE_GRACE_PERIOD ="TGP";

	//PRODUCTS, type column in the master table
	public static final String REPLACEMENT_REASON = "RR";
	public static final String PRODUCT_SUSPEND_REASON ="SR";
	public static final String PRODUCT_REACTIVATE_REASON ="RAR";
	public static final String RECYCLE_REASON_TYPE ="RCLR";
	public static final String PRODUCT_TERMINATE_REASON ="TR";
	public static final String PRODUCT_USED_REASON = "USD";
	public static final String PRODUCT_TERMINATE_CODE = "TBR";
	public static final String PRODUCT_OTU_REASON = "OTU";
	public static final String ISSUE_REASON_TYPE = "IR";
	public static final String ISSUE_REASON_CODE = "IC";

	//PRODUCTS, code column in the master table
	public static final String DURATION_LENGTH_CODE = "DUR";
	public static final String RECYCLE_CODE ="RCLR";
	public static final String PRODUCT_USED_CODE = "USD";
	public static final String PRODUCT_OTU_CODE = "OTU";

	public static final String SORTING_PAGING_SIZE = "SP";
	public static final String REPORT = "RP";

	//Billing
	public static final String PAYMENT_MODE = "PM";
	public static final String CANCELLATION_REASON = "CR";
	public static final String NOTE_REASON = "NR";
	public static final String MISC_INV_ITEM = "MII";
	public static final String TAX_TYPE = "TT";
	public static final String TAX_GST_MASTER_CODE = "GST";

	// email fields
	public static final String EMAIL_SUBJECT	= "SUBJ";
	public static final String EMAIL_CONTENT	= "CONT";
	public static final String EMAIL_RECIPIENT	= "EMAIL";
	// email types
	public static final String EMAIL_TYPE_APP_REQUEST_SUBMIT				= "EMARS";
	public static final String EMAIL_TYPE_APP_REQUEST_RECOMMAND				= "EMAR2";
	public static final String EMAIL_TYPE_APP_REQUEST_APPROVED				= "EMARA";
	public static final String EMAIL_TYPE_APP_REQUEST_REJECTED				= "EMARR";
	public static final String EMAIL_TYPE_BILL_REQUEST_SUBMIT				= "EMBRS";
	public static final String EMAIL_TYPE_BILL_REQUEST_APPROVED				= "EMBRA";
	public static final String EMAIL_TYPE_BILL_REQUEST_REJECTED				= "EMBRR";
	public static final String EMAIL_TYPE_CREDIT_REQUEST_SUBMIT				= "EMCRS";
	public static final String EMAIL_TYPE_CREDIT_REQUEST_APPROVED			= "EMCRA";
	public static final String EMAIL_TYPE_CREDIT_REQUEST_REJECTED			= "EMCRR";
	public static final String EMAIL_TYPE_TRIPS_REQUEST_SUBMIT				= "EMTRS";
	public static final String EMAIL_TYPE_TRIPS_REQUEST_APPROVED			= "EMTRA";
	public static final String EMAIL_TYPE_TRIPS_REQUEST_REJECTED			= "EMTRR";
	public static final String EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_SUBMIT	= "EMIRS";
	public static final String EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_APPROVED	= "EMIRA";
	public static final String EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_REJECTED	= "EMIRR";
	public static final String EMAIL_TYPE_REWARDS_ADJ_REQUEST_SUBMIT		= "EMRAS";
	public static final String EMAIL_TYPE_REWARDS_ADJ_REQUEST_APPROVED		= "EMRAA";
	public static final String EMAIL_TYPE_REWARDS_ADJ_REQUEST_REJECTED		= "EMRAR";
	public static final String EMAIL_TYPE_EINVOICE							= "EMEI";
	public static final String EMAIL_TYPE_NOTE_REQUEST_SUBMIT				= "EMNRS";
	public static final String EMAIL_TYPE_NOTE_REQUEST_APPROVED				= "EMNRA";
	public static final String EMAIL_TYPE_NOTE_REQUEST_REJECTED				= "EMNRR";
	public static final String EMAIL_TYPE_SUBSCRIPTION_REQUEST_SUBMIT		= "EMSRS";
	public static final String EMAIL_TYPE_SUBSCRIPTION_REQUEST_APPROVED		= "EMSRA";
	public static final String EMAIL_TYPE_SUBSCRIPTION_REQUEST_REJECTED		= "EMSRR";

	public static final String EMAIL_TYPE_PREPAID_CREDIT_REQUEST_APPROVED	= "EMPCRA";
	public static final String EMAIL_TYPE_PREPAID_ISSUE_REQUEST_SUBMIT		= "EMPIRS";
	public static final String EMAIL_TYPE_PREPAID_ISSUE_REQUEST_APPROVED	= "EMPIRA";
	public static final String EMAIL_TYPE_PREPAID_ISSUE_REQUEST_REJECTED	= "EMPIRR";

	public static final String EMAIL_TYPE_PREPAID_TOPUP_REQUEST_SUBMIT		= "EMPTRS";
	public static final String EMAIL_TYPE_PREPAID_TOPUP_REQUEST_APPROVED	= "EMPTRA";
	public static final String EMAIL_TYPE_PREPAID_TOPUP_REQUEST_REJECTED	= "EMPTRR";

	public static final String EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_SUBMIT	= "EMPXRS";
	public static final String EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_APPROVED	= "EMPXRA";
	public static final String EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_REJECTED	= "EMPXRR";

	public static final String EMAIL_TYPE_PREPAID_EXTEND_REQUEST_SUBMIT		= "EMPERS";
	public static final String EMAIL_TYPE_PREPAID_EXTEND_REQUEST_APPROVED	= "EMPERA";
	public static final String EMAIL_TYPE_PREPAID_EXTEND_REQUEST_REJECTED	= "EMPERR";

	public static final String EMAIL_TYPE_PREPAID_ADJUST_REQUEST_SUBMIT		= "EMPARS";
	public static final String EMAIL_TYPE_PREPAID_ADJUST_REQUEST_APPROVED	= "EMPARA";
	public static final String EMAIL_TYPE_PREPAID_ADJUST_REQUEST_REJECTED	= "EMPARR";

	public static final String EMAIL_TYPE_BILLGEN_START_PROCESSING			= "EMBSP";
	public static final String EMAIL_TYPE_BILLGEN_END_PROCESSING			= "EMBEP";
	public static final String EMAIL_TYPE_PDF_START_PROCESSING				= "EMPSP";
	public static final String EMAIL_TYPE_PDF_END_PROCESSING				= "EMPEP";
	public static final String EMAIL_TYPE_AGING_REPORT						= "EMAG";
	public static final String EMAIL_TYPE_BILLGEN_START_ADHOC_DRAFT			= "EMSAD";
	public static final String EMAIL_TYPE_BILLGEN_END_ADHOC_DRAFT			= "EMEAD";
	public static final String EMAIL_TYPE_PDF_START_ADHOC_DRAFT				= "EPSAD";
	public static final String EMAIL_TYPE_PDF_END_ADHOC_DRAFT				= "EPEAD";
	public static final String EMAIL_TYPE_BILLGEN_START_REGEN				= "EMSRG";
	public static final String EMAIL_TYPE_BILLGEN_END_REGEN					= "EMERG";
	public static final String EMAIL_TYPE_PDF_START_REGEN					= "EPSRG";
	public static final String EMAIL_TYPE_PDF_END_REGEN						= "EPERG";

	public static final String DT 											= "DT";
	public static final String SS											= "SS";

	public static final String EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_SUBMIT		= "EMIERS";
	public static final String EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_APPROVED	= "EMIERA";
	public static final String EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_REJECTED	= "EMIERR";

	public static final String EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_SUBMIT			= "EMISRS";
	public static final String EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_APPROVED		= "EMISRA";
	public static final String EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_REJECTED		= "EMISRR";

	public static final String EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_SUBMIT		= "EMIRRS";
	public static final String EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_APPROVED		= "EMIRRA";
	public static final String EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_REJECTED		= "EMIRRR";

	public static final String EMAIL_TYPE_INVENTORY_VOID_REQUEST_SUBMIT				= "EMIVRS";
	public static final String EMAIL_TYPE_INVENTORY_VOID_REQUEST_APPROVED			= "EMIVRA";
	public static final String EMAIL_TYPE_INVENTORY_VOID_REQUEST_REJECTED			= "EMIVRR";

	public static final String EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_SUBMIT		= "EMICRS";
	public static final String EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_APPROVED	= "EMICRA";
	public static final String EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_REJECTED	= "EMICRR";

	public static final String EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_SUBMIT			= "EMIXRS";
	public static final String EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_APPROVED		= "EMIXRA";
	public static final String EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_REJECTED		= "EMIXRR";

	public static final String EMAIL_TYPE_ACE_INTERFACE		= "EMACE";
	public static final String EMAIL_TYPE_ACE_INTERFACE_RECEIVER_LIST		= "EMACERL";
	
	//Coupa
	public static final String EMAIL_TYPE_COUPA_SEND_START = "RECSS";
	public static final String EMAIL_TYPE_COUPA_SEND_COMPLETE = "RECSC";
	public static final String EMAIL_TYPE_COUPA_RECEIVE_START = "RECRS";
	public static final String EMAIL_TYPE_COUPA_RECEIVE_COMPLETE = "RECRC";

	public static final String EMPLOYEE_ID_NATURE_OF_BUSINESS = "EINOB";

	//printers
	public static final String PRINTERS = "PRT";

	//promo
	public static final String JOB_TYPE = "JT";
	public static final String VEHICLE_MODEL = "VM";

	//Non Billable
	public static final String ACQUIRER_PYMT_TYPE = "APT";
	public static final String CHARGEBACK_TYPE = "CBT";
	public static final String CHARGEBACK_REASON = "CBR";
	public static final String COMMISSION_TYPE = "COMM";

	//Rewards
	public static final String REWARDS_PRICE_PTS_RATIO = "RPPR";
	public static final String REWARDS_GRACE_PERIOD = "RGP";
	public static final String REWARDS_ADJUSTMENT_REASON = "RADJR";

	public static final String HANDLING_FEE_DESCRIPTION = "PF";
	public static final String DELIVERY_CHARGES_DESCRIPTION = "DC";

	//GIRO UOB Interface Rejection Code
	public static final String GIRO_UOB_REJECTION_CODE = "GURC";
	public static final String GIRO_IBS_REJECTION_CODE = "GIRC";

	//GIRO UOB OUTGOING EMAILS
	public static final String EMAIL_TYPE_GIRO_UOB_OUTGOING = "EMGUO";
	//GIRO UOB INCOMING EMAILS
	public static final String EMAIL_TYPE_GIRO_UOB_INCOMING = "EMGUI";

	//INVENTORY REASONS FOR THE ACTIONS E.G. SUSPENSION
	public static final String INVENTORY_SUSPENSION_REASONS = "IVSR";
	public static final String INVENTORY_REACTIVATION_REASONS = "IVRR";
	public static final String INVENTORY_VOID_REASONS = "IVVR";
	public static final String INVENTORY_CHANGE_OF_REDEMPTION_REASONS = "IVCR";
	public static final String INVENTORY_REMOVE_REDEMPTION_REASONS = "IVMR";
	public static final String INVENTORY_EXPIRY_GRACE_PERIOD = "IVGP";
	public static final String INVENTORY_EDIT_EXPIRY_DATE_REASONS = "IVER";

	//Balance EXPIRY grace period
	public static final String BALANCE_EXPIRY_GRACE_PERIOD_MASTER_TYPE = "BLGP";
	public static final String BALANCE_EXPIRY_GRACE_PERIOD_GP = "GP";

	public static final String APPROVER_LIMIT_LEVEL_1 = "1";

	//NRIC PDPA
	public static final String NRIC_PDPA_CONFIG_MASTER_TYPE = "NRICPD";
	
	//TRIP DOWNLOAD X DAY no process
	public static final String DOWNLOAD_TRIP_X_DAY = "DTRIPD";
	
	//GOVT EINVOICE
	public static final String GOVT_EINVOICE_BG_SETUP_MASTER_TYPE = "GEBGS";
	public static final String GOVT_EINVOICE_BG_SETUP_FILE_SIZE_MASTER_CODE = "GEFZ";
	public static final String GOVT_EINVOICE_BG_SETUP_MAX_LINE_MASTER_CODE = "GEML";
	public static final String GOVT_EINVOICE_BG_SETUP_LINE_DESCRIPTION = "GELD";
	public static final String GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE = "GEBU";

	//PUBBS 2
	public static final String PUBBS_BG_SETUP_MASTER_TYPE = "PBGS";
	public static final String PUBBS_BG_SETUP_FILE_SIZE_MASTER_CODE = "PFZ";
	public static final String PUBBS_BG_SETUP_MAX_LINE_MASTER_CODE = "PML";
	public static final String PUBBS_BG_SETUP_LINE_DESCRIPTION = "PLD";
	public static final String PUBBS_BUSINESS_UNIT_MASTER_TYPE = "PBU";

	//Email Invoice Reminder
	public static final String EMAIL_INVOICE_REMINDER_DUE_SOON = "EMIRDS";
	public static final String EMAIL_INVOICE_REMINDER_OVERDUE = "EMIROD";
	public static final String BCC_EMAIL_LIST = "BCCEM";
	public static final String EMAIL_EXCEPTIONAL_ACCOUNT = "EMEXAC";

	//Recurring Invoice
	public static final String EMAIL_TYPE_RECURRING_INVOICE = "RERL";
	public static final String EMAIL_TYPE_TOKENIZE_RECIPENT = "TERL";
	public static final String EMAIL_TYPE_TOKENIZE_NOTIFICATION_RECIPENT = "TENR";

	public static final String OTHERS_TYPE_RECURRING_BANK_IN = "RBANK";
	
	//Recurring Generic Email
	public static final String EMAIL_TYPE_RECURRING_START = "RERS";
	public static final String EMAIL_TYPE_RECURRING_COMPLETED = "RERC";	
	
	public static final String EMAIL_TYPE_RECURRING_DOWNLOAD_START = "RERDS";
	public static final String EMAIL_TYPE_RECURRING_DOWNLOAD_COMPLETED = "RERDC";	
	
	//Ayden Settlement Email
	public static final String EMAIL_TYPE_AYDEN_SETTLEMENT = "ETAS";
	public static final String EMAIL_TYPE_AYDEN_SETTLEMENT_ERROR = "ETASE";
	public static final String EMAIL_TYPE_AYDEN_SETTLEMENT_COMPLETED = "ETASC";	
	
	//Lazada Settlement Email
	public static final String EMAIL_TYPE_LAZADA_SETTLEMENT = "ETLS";
	public static final String EMAIL_TYPE_LAZADA_SETTLEMENT_ERROR = "ETLSE";
	public static final String EMAIL_TYPE_LAZADA_SETTLEMENT_COMPLETED = "ETLSC";	

	//Virtual CabCharge Email
	public static final String EMAIL_VIRTUAL_CARD_EMAIL = "EMVCE";
	public static final String EMAIL_VIRTUAL_CARD_SUMMARY_EMAIL = "EMVCSE";
	public static final String VCS_EMAIL_RECEIVER_LIST = "EMVCSER";

	//Email Alert Credit Limit, Email Suspend Reactivate Acct
	public static final String EMAIL_SUSPEND_REACTIVATE_ACCOUNT = "EMFSRA";
	public static final String EMAIL_ALERT_CREDIT_LIMIT_LOW = "EMACLL";

	//Email PUBBS Email
	public static final String EMAIL_PUBBS_SUMMARY_EMAIL = "EMPSN";

	//Email FI Email
	public static final String EMAIL_FI_SUMMARY_EMAIL = "EMFIN";

	//Email EInvoice Email
	public static final String EMAIL_EINVOICE_EMAIL_RECEIVER_LIST = "EERL";
	public static final String EMAIL_EINVOICE_EMAIL_SUMMARY_LIST = "EESL";

	//Other Additional Surcharge Column Trip Detail Report
	public static final String OTHERS_TYPE_ADDITIONAL_SURCHARGE_COLUMN_TRIP_DETAIL_REPORT = "ASCTDR";

	//Other Contact Race
	public static final String OTHERS_TYPE_CONTACT_RACE = "CRACE";

	//Other Map Generic Vehicle Type
	public static final String OTHERS_DOWNLOAD_TRIP_MAP_VEHICLE_TYPE = "DTMVT";

	//Credit Cap
	public static final String CREDIT_LIMIT_CAP ="CLC";
	public static final String CREDIT_BALANCE_CAP = "CBC";

	//Invoice Reminder
	public static final String INVOICE_DUE_SOON_FREQUENCY = "IDS";
	public static final String INVOICE_OVERDUE_REMINDER_FREQUENCY = "IORY";
	public static final String INVOICE_OVERDUE_REMINDER_UNTIL_FREQUENCY = "IORZ";

	//Generic Mass Email
	public static final String GENERIC_MASS_EMAIL_MASTER_TYPE = "GME";
	public static final String GENERIC_MASS_EMAIL_FILE_SIZE_MASTER_CODE = "MFZ";

	//SMS
	public static final String TXSMS_MASTER_TYPE = "TXSMS";
	public static final String EXSMS_MASTER_TYPE = "EXSMS";
	public static final String TUSMS_MASTER_TYPE = "TUSMS";
	public static final String EXPBJ_MASTER_TYPE = "EXPBJ";
	public static final String TUBJ_MASTER_TYPE = "TUBJ";

	public static final String SMS_STATUS_MASTER_TYPE = "SMSST";

	public static final String FAX = "FAX";
	public static final String TEL = "TEL";

	//Ayden
	
	private static List<MstbMasterTable> activeMasterTables = Collections.synchronizedList(new ArrayList<MstbMasterTable>());
	private static List<MstbMasterTable> masterTables = Collections.synchronizedList(new ArrayList<MstbMasterTable>());
	private static Logger logger = Logger.getLogger(ConfigurableConstants.class);
	private static ConstantDao constantDao;

	private static Map<String, String> typeNames = new HashMap<String, String>();

	static {
		typeNames.put(MAX_QUERY_RESULT, "Max. Query Result");
		typeNames.put(MAX_REPORT_RECORD, "Max. Report Record");
		typeNames.put(INDUSTRY_MASTER_CODE, "Industry");
		typeNames.put(COUNTRY_MASTER_CODE, "Country");
		typeNames.put(INFORMATION_SOURCE_MASTER_CODE, "Information Source");
		typeNames.put(VEHICLE_TYPE_MASTER_CODE, "Vehicle Type");
		typeNames.put(JOB_STATUS_MASTER_CODE, "Employee Type");
		typeNames.put(SALUTATION_MASTER_CODE, "Salutation");
		typeNames.put(APPROVER_LIMIT, "Approval Limit");
		typeNames.put(RELATIONSHIP_TO_ACCOUNT_CODE, "Relationship to Account");
		typeNames.put(VEHICLE_TRIP_TYPE, "Vehicle Trip Type");
		typeNames.put(SERVICE_PROVIDER, "Service Provider");
		typeNames.put(ACCT_SUSPEND_REASON, "Account Suspension Reason");
		typeNames.put(ACCT_REACTIVATE_REASON, "Account Reactivation Reason");
		typeNames.put(ACCT_TERMINATE_REASON, "Account Termination Reason");
		typeNames.put(ACCT_TERMINATE_GRACE_PERIOD, "Account Termination Grace Period");
		typeNames.put(REPLACEMENT_REASON, "Card Replacement Reason");
		typeNames.put(PRODUCT_SUSPEND_REASON, "Product Suspension Reason");
		typeNames.put(PRODUCT_REACTIVATE_REASON, "Product Reactivation Reason");
		typeNames.put(RECYCLE_REASON_TYPE, "Recycle Reason Type");
		typeNames.put(PRODUCT_TERMINATE_REASON, "Product Termination Reason");
		typeNames.put(PRODUCT_USED_REASON, "Product Used Reason");
		typeNames.put(PRODUCT_TERMINATE_CODE, "Product Terminate Code");
		typeNames.put(PRODUCT_OTU_REASON, "Product OTU Reason");
		typeNames.put(ISSUE_REASON_TYPE, "Issue Reason Type");
		typeNames.put(ISSUE_REASON_CODE, "Issue Reason Code");

		typeNames.put(DURATION_LENGTH_CODE, "Duration");
		typeNames.put(RECYCLE_CODE, "Recycle Code");
		typeNames.put(PRODUCT_USED_CODE, "Product Used Code");
		typeNames.put(PRODUCT_OTU_CODE, "Product OTU Code");
		typeNames.put(SORTING_PAGING_SIZE, "Sorting Page Size");
		typeNames.put(PAYMENT_MODE, "Payment Mode");
		typeNames.put(CANCELLATION_REASON, "Note Cancellation Reason");
		typeNames.put(NOTE_REASON, "Note Issuance Reason");
		typeNames.put(MISC_INV_ITEM, "Miscellaneous Invoice Item");
		typeNames.put(TAX_TYPE, "Tax Type");

		typeNames.put(EMAIL_TYPE_APP_REQUEST_SUBMIT, "Email: Application Request (Submit)");
		typeNames.put(EMAIL_TYPE_APP_REQUEST_RECOMMAND, "Email: Application Request (Recommend)");
		typeNames.put(EMAIL_TYPE_APP_REQUEST_APPROVED, "Email: Application Request (Approve)");
		typeNames.put(EMAIL_TYPE_APP_REQUEST_REJECTED, "Email: Application Request (Reject)");
		typeNames.put(EMAIL_TYPE_BILL_REQUEST_SUBMIT, "Email: Billing Request (Submit)");
		typeNames.put(EMAIL_TYPE_BILL_REQUEST_APPROVED, "Email: Billing Request (Approve)");
		typeNames.put(EMAIL_TYPE_BILL_REQUEST_REJECTED, "Email: Billing Request (Reject)");
		typeNames.put(EMAIL_TYPE_CREDIT_REQUEST_SUBMIT, "Email: Credit Request (Submit)");
		typeNames.put(EMAIL_TYPE_CREDIT_REQUEST_APPROVED, "Email: Credit Request (Approve)");
		typeNames.put(EMAIL_TYPE_CREDIT_REQUEST_REJECTED, "Email: Credit Request (Reject)");
		typeNames.put(EMAIL_TYPE_SUBSCRIPTION_REQUEST_SUBMIT, "Email: Subscription Request (Submit)");
		typeNames.put(EMAIL_TYPE_SUBSCRIPTION_REQUEST_APPROVED, "Email: Subscription Request (Approve)");
		typeNames.put(EMAIL_TYPE_SUBSCRIPTION_REQUEST_REJECTED, "Email: Subscription Request (Reject)");
		typeNames.put(EMAIL_TYPE_TRIPS_REQUEST_SUBMIT, "Email: Trips Request (Submit)");
		typeNames.put(EMAIL_TYPE_TRIPS_REQUEST_APPROVED, "Email: Trips Request (Approve)");
		typeNames.put(EMAIL_TYPE_TRIPS_REQUEST_REJECTED, "Email: Trips Request (Reject)");
		typeNames.put(EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_SUBMIT, "Email: Inventory Issuance Request (Submit)");
		typeNames.put(EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_APPROVED, "Email: Inventory Issuance Request (Approve)");
		typeNames.put(EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_REJECTED, "Email: Inventory Issuance Request (Reject)");
		typeNames.put(EMAIL_TYPE_REWARDS_ADJ_REQUEST_SUBMIT, "Email: Rewards Adjustment Request (Submit)");
		typeNames.put(EMAIL_TYPE_REWARDS_ADJ_REQUEST_APPROVED, "Email: Rewards Adjustment Request (Approve)");
		typeNames.put(EMAIL_TYPE_REWARDS_ADJ_REQUEST_REJECTED, "Email: Rewards Adjustment Request (Reject)");
		typeNames.put(EMAIL_TYPE_EINVOICE, "Email: E-Invoice");
		typeNames.put(EMAIL_TYPE_PREPAID_CREDIT_REQUEST_APPROVED, "Email: Prepaid Credit Card Topup Request Approved");

		typeNames.put(EMAIL_TYPE_BILLGEN_START_PROCESSING, "Email: Billgen Start Processing");
		typeNames.put(EMAIL_TYPE_BILLGEN_END_PROCESSING, "Email: Billgen End Processing");
		typeNames.put(EMAIL_TYPE_PDF_START_PROCESSING, "Email: PDF Start Processing");
		typeNames.put(EMAIL_TYPE_PDF_END_PROCESSING, "Email: PDF End Processing");
		typeNames.put(EMAIL_TYPE_AGING_REPORT, "Email: Aging Report");
		typeNames.put(EMAIL_TYPE_BILLGEN_START_ADHOC_DRAFT, "Email: Billgen Start Adhoc/Draft");
		typeNames.put(EMAIL_TYPE_BILLGEN_END_ADHOC_DRAFT, "Email: Billgen End Adhoc/Draft");
		typeNames.put(EMAIL_TYPE_PDF_START_ADHOC_DRAFT, "Email: PDF Start Adhoc/Draft");
		typeNames.put(EMAIL_TYPE_PDF_END_ADHOC_DRAFT, "Email: PDF End Adhoc/Draft");

		typeNames.put(EMAIL_TYPE_BILLGEN_START_REGEN, "Email: Billgen Start REGEN");
		typeNames.put(EMAIL_TYPE_BILLGEN_END_ADHOC_DRAFT, "Email: Billgen End REGEN");
		typeNames.put(EMAIL_TYPE_PDF_START_ADHOC_DRAFT, "Email: PDF Start REGEN");
		typeNames.put(EMAIL_TYPE_PDF_END_ADHOC_DRAFT, "Email: PDF End REGEN");


		typeNames.put(PRINTERS, "Printers for Printings Invoices");

		typeNames.put(JOB_TYPE, "Job Type");
		typeNames.put(VEHICLE_MODEL, "Vehicle Model");

		typeNames.put(ACQUIRER_PYMT_TYPE, "Acquirer Payment Type");
		typeNames.put(CHARGEBACK_TYPE, "Chargeback Type");
		typeNames.put(CHARGEBACK_REASON, "Chargeback Reason");
		typeNames.put(HANDLING_FEE_DESCRIPTION, "Inventory Misc Invoice Handling Fee Description");
		typeNames.put(DELIVERY_CHARGES_DESCRIPTION, "Inventory Misc Invoice Delivery Charges Description");

		typeNames.put(REWARDS_PRICE_PTS_RATIO, "Rewards Price Points Ratio");
		typeNames.put(REWARDS_GRACE_PERIOD, "Rewards Grace Period (Months)");
		typeNames.put(REWARDS_ADJUSTMENT_REASON, "Rewards Adjustment Reason");

		typeNames.put(GIRO_UOB_REJECTION_CODE, "GIRO UOB Rejection Code");
		typeNames.put(GIRO_IBS_REJECTION_CODE, "GIRO IBS Rejection Code");
		typeNames.put(EMAIL_TYPE_GIRO_UOB_OUTGOING, "Email: GIRO UOB Outgoing");
		typeNames.put(EMAIL_TYPE_GIRO_UOB_INCOMING, "Email: GIRO UOB Incoming");

		typeNames.put(INVENTORY_SUSPENSION_REASONS, "Inventory Suspension Reasons");
		typeNames.put(INVENTORY_REACTIVATION_REASONS, "Inventory Reactivation Reasons");
		typeNames.put(INVENTORY_VOID_REASONS, "Inventory Void Reasons");
		typeNames.put(INVENTORY_CHANGE_OF_REDEMPTION_REASONS, "Inventory Change of Redemption Reasons");
		typeNames.put(INVENTORY_CHANGE_OF_REDEMPTION_REASONS, "Inventory Change of Redemption Reasons");
		typeNames.put(INVENTORY_REMOVE_REDEMPTION_REASONS, "Inventory Remove Redemption Reasons");
		typeNames.put(INVENTORY_EXPIRY_GRACE_PERIOD, "Inventory Expiry Grace Period");
		typeNames.put(INVENTORY_EDIT_EXPIRY_DATE_REASONS, "Inventory Edit Expiry Date Reasons");

		typeNames.put(NRIC_PDPA_CONFIG_MASTER_TYPE, "NRIC PDPA Mask Config");

		typeNames.put(DOWNLOAD_TRIP_X_DAY, "Download Trip X Days No Process");
		
		typeNames.put(GOVT_EINVOICE_BG_SETUP_MASTER_TYPE, "Govt eInvoice Bill Gen Setup");
		typeNames.put(GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, "Govt eInvoice Business Unit");

		typeNames.put(EMAIL_INVOICE_REMINDER_DUE_SOON, "Email: Invoice Reminder Due Soon");
		typeNames.put(EMAIL_INVOICE_REMINDER_OVERDUE, "Email: Invoice Reminder Overdue");
		typeNames.put(BCC_EMAIL_LIST, "BCC Email List");
		typeNames.put(EMAIL_EXCEPTIONAL_ACCOUNT, "Email: Invoice Reminder Exceptional Account");

		typeNames.put(EMAIL_VIRTUAL_CARD_EMAIL, "Email: Virtual Card Email");
		typeNames.put(EMAIL_VIRTUAL_CARD_SUMMARY_EMAIL, "Email: Virtual Card Summary Email");
		typeNames.put(VCS_EMAIL_RECEIVER_LIST, "Virtual Card Summary Email Receiver List");

		typeNames.put(EMAIL_PUBBS_SUMMARY_EMAIL, "Email: PUBBS Account Maint Email");
		typeNames.put(EMAIL_FI_SUMMARY_EMAIL, "Email: FI@Gov Email");

		typeNames.put(EMAIL_SUSPEND_REACTIVATE_ACCOUNT, "Email: Suspend Reactivate Account");
		typeNames.put(EMAIL_ALERT_CREDIT_LIMIT_LOW, "Email: Alert Credit Limit Low");

		typeNames.put(CREDIT_BALANCE_CAP, "Credit Balance Cap");
		typeNames.put(CREDIT_LIMIT_CAP, "Credit Limit Cap");

		typeNames.put(INVOICE_DUE_SOON_FREQUENCY, "Invoice Due Soon Frequency");
		typeNames.put(INVOICE_OVERDUE_REMINDER_FREQUENCY, "Invoice Overdue Reminder Frequency");
		typeNames.put(INVOICE_OVERDUE_REMINDER_UNTIL_FREQUENCY, "Invoice Overdue Reminder Til Frequency");

		typeNames.put(GENERIC_MASS_EMAIL_MASTER_TYPE, "Generic Mass Email Setup");
		typeNames.put(TXSMS_MASTER_TYPE, "SMS: Txn Notification");
		typeNames.put(EXSMS_MASTER_TYPE, "SMS: Expiry Notification");
		typeNames.put(TUSMS_MASTER_TYPE, "SMS: Top Up Notification");
		typeNames.put(EXPBJ_MASTER_TYPE, "BATCH JOB: Expiry Notification");
		typeNames.put(TUBJ_MASTER_TYPE, "BATCH JOB: Top-up Notification");

		typeNames.put(SMS_STATUS_MASTER_TYPE, "SMS Status");

		typeNames.put(BALANCE_EXPIRY_GRACE_PERIOD_MASTER_TYPE, "Balance Expiry Grace Period (Months)");

		typeNames.put(EMAIL_TYPE_PREPAID_ISSUE_REQUEST_SUBMIT, "Email: Prepaid Issue Request Submit");
		typeNames.put(EMAIL_TYPE_PREPAID_ISSUE_REQUEST_REJECTED, "Email: Prepaid Issue Request Rejected");
		typeNames.put(EMAIL_TYPE_PREPAID_ISSUE_REQUEST_APPROVED, "Email: Prepaid Issue Request Approved");

		typeNames.put(EMAIL_TYPE_PREPAID_TOPUP_REQUEST_SUBMIT, "Email: Prepaid Topup Request Submit");
		typeNames.put(EMAIL_TYPE_PREPAID_TOPUP_REQUEST_REJECTED, "Email: Prepaid Topup Request Rejected");
		typeNames.put(EMAIL_TYPE_PREPAID_TOPUP_REQUEST_APPROVED, "Email: Prepaid Topup Request Approved");

		typeNames.put(EMAIL_TYPE_PREPAID_ADJUST_REQUEST_SUBMIT, "Email: Prepaid Adjust Request Submit");
		typeNames.put(EMAIL_TYPE_PREPAID_ADJUST_REQUEST_REJECTED, "Email: Prepaid Adjust Request Rejected");
		typeNames.put(EMAIL_TYPE_PREPAID_ADJUST_REQUEST_APPROVED, "Email: Prepaid Adjust Request Approved");

		typeNames.put(EMAIL_TYPE_PREPAID_EXTEND_REQUEST_SUBMIT ,"Email: Prepaid Extend Request Submit");
		typeNames.put(EMAIL_TYPE_PREPAID_EXTEND_REQUEST_REJECTED ,"Email: Prepaid Extend Request Rejected");
		typeNames.put(EMAIL_TYPE_PREPAID_EXTEND_REQUEST_APPROVED ,"Email: Prepaid Extend Request Approved");

		typeNames.put(EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_SUBMIT ,"Email: Prepaid Transfer Request Submit");
		typeNames.put(EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_REJECTED ,"Email: Prepaid Transfer Request Rejected");
		typeNames.put(EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_APPROVED ,"Email: Prepaid Transfer Request Approved");

		typeNames.put(EMAIL_TYPE_NOTE_REQUEST_SUBMIT ,"Email: Note Request Submit");
		typeNames.put(EMAIL_TYPE_NOTE_REQUEST_REJECTED ,"Email: Note Request Rejected");
		typeNames.put(EMAIL_TYPE_NOTE_REQUEST_APPROVED ,"Email: Note Request Approved");

		typeNames.put(EMAIL_TYPE_PDF_START_REGEN, "Email: PDF START REGEN");
		typeNames.put(EMAIL_TYPE_PDF_END_REGEN, "Email: PDF End REGEN");

		typeNames.put(EMAIL_TYPE_BILLGEN_START_REGEN, "Email: Billgen Start REGEN");
		typeNames.put(EMAIL_TYPE_BILLGEN_END_REGEN, "Email: Billgen End REGEN");

		typeNames.put(FAX, "FAX");
		typeNames.put(TEL, "TEL");

		typeNames.put(SS, "Product Status Issue Reason");
		typeNames.put(DT, "DT"); //Pls Update DT

		typeNames.put(EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_SUBMIT, "Email: Taxi Voucher Amendment of Expiry Date Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_REJECTED,"Email: Taxi Voucher Amendment of Expiry Date Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_APPROVED, "Email: Taxi Voucher Amendment of Expiry Date Request Approved");

		typeNames.put(EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_SUBMIT, "Email: Taxi Voucher Suspension Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_REJECTED,"Email: Taxi Voucher Suspension Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_APPROVED, "Email: Taxi Voucher Suspension Request Approved");

		typeNames.put(EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_SUBMIT, "Email: Taxi Voucher Reactivation Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_REJECTED,"Email: Taxi Voucher Reactivation Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_APPROVED, "Email: Taxi Voucher Reactivation Request Approved");

		typeNames.put(EMAIL_TYPE_INVENTORY_VOID_REQUEST_SUBMIT, "Email: Taxi Voucher Void Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_VOID_REQUEST_REJECTED,"Email: Taxi Voucher Void Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_VOID_REQUEST_APPROVED, "Email: Taxi Voucher Void Request Approved");

		typeNames.put(EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_SUBMIT, "Email: Taxi Voucher Change of Redemption Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_REJECTED,"Email: Taxi Voucher Change of Redemption Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_APPROVED, "Email: Taxi Voucher Change of Redemption Request Approved");

		typeNames.put(EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_SUBMIT, "Email: Taxi Voucher Remove Redemption Request Submit");
		typeNames.put(EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_REJECTED,"Email: Taxi Voucher Remove Redemption Request Rejected");
		typeNames.put(EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_APPROVED, "Email: Taxi Voucher Remove Redemption Request Approved");

		typeNames.put(EMAIL_TYPE_ACE_INTERFACE, "Email: Ace Interface Email");
		typeNames.put(EMAIL_TYPE_ACE_INTERFACE_RECEIVER_LIST, "Ace Interface Email Receiever List");

		typeNames.put(EMAIL_EINVOICE_EMAIL_RECEIVER_LIST, "Email: eInvoice Email Receiver List");
		typeNames.put(EMAIL_EINVOICE_EMAIL_SUMMARY_LIST, "Email: eInvoice Email Summary List");

		typeNames.put(EMPLOYEE_ID_NATURE_OF_BUSINESS, "Employee ID Nature of Business");

		typeNames.put(OTHERS_TYPE_ADDITIONAL_SURCHARGE_COLUMN_TRIP_DETAIL_REPORT, "Additional Surcharge Column Trip Details Report");

		typeNames.put(OTHERS_TYPE_CONTACT_RACE, "Contact Person Race Config");

		typeNames.put(OTHERS_DOWNLOAD_TRIP_MAP_VEHICLE_TYPE, "Download Trip Map Generic Vehicle Type");

		typeNames.put(EMAIL_TYPE_AYDEN_SETTLEMENT,"Email: Ayden Settlement");
		typeNames.put(EMAIL_TYPE_AYDEN_SETTLEMENT_ERROR,"Email: Ayden Settlement Failure");
		typeNames.put(EMAIL_TYPE_AYDEN_SETTLEMENT_COMPLETED,"Email: Ayden Settlement Completed");

		typeNames.put(EMAIL_TYPE_RECURRING_INVOICE, "Email: Recurring Email Receiver List ");
		typeNames.put(EMAIL_TYPE_TOKENIZE_RECIPENT, "Email: Recurring Tokenization Email Receiver List ");
		typeNames.put(EMAIL_TYPE_TOKENIZE_NOTIFICATION_RECIPENT, "Email: Recurring Tokenization Notification Email Receiver List ");
		typeNames.put(EMAIL_TYPE_RECURRING_START, "Email: Recurring Upload Start Notification List");
		typeNames.put(EMAIL_TYPE_RECURRING_COMPLETED, "Email: Recurring Upload Completion Notification List");	
		typeNames.put(EMAIL_TYPE_RECURRING_DOWNLOAD_START, "Email: Recurring Download Start Notification List");
		typeNames.put(EMAIL_TYPE_RECURRING_DOWNLOAD_COMPLETED, "Email: Recurring Download Completion Notification List");	

		typeNames.put(OTHERS_TYPE_RECURRING_BANK_IN, "Others: Recurring Default Bank In Code No.");
		
		typeNames.put(EMAIL_TYPE_LAZADA_SETTLEMENT,"Email: Lazada Settlement");
		typeNames.put(EMAIL_TYPE_LAZADA_SETTLEMENT_ERROR,"Email: Lazada Settlement Failure");
		typeNames.put(EMAIL_TYPE_LAZADA_SETTLEMENT_COMPLETED,"Email: Lazada Settlement Completed");
		
		//Coupa Mail
		typeNames.put(EMAIL_TYPE_COUPA_SEND_START,"Email: Email Type Coupa Send Start");
		typeNames.put(EMAIL_TYPE_COUPA_SEND_COMPLETE,"Email: Email Type Coupa Send Complete");
		typeNames.put(EMAIL_TYPE_COUPA_RECEIVE_START,"Email: Email Type Coupa Receive Start");
		typeNames.put(EMAIL_TYPE_COUPA_RECEIVE_COMPLETE,"Email: Email Type Coupa Receive Complete");

}

	public static void refresh(){
		activeMasterTables.clear();
		masterTables.clear();
		init();
	}
	public static void init(){
		logger.info("initializing");
		// getting everything from master table
		activeMasterTables.addAll(constantDao.getAllActiveMasterTable());
		masterTables.addAll(constantDao.getAllMasterTable());
	}
	public ConstantDao getConstantDao() { return constantDao; }
	public void setConstantDao(ConstantDao constantDao) {
		ConfigurableConstants.constantDao = constantDao;
	}
	@Deprecated
	public void addConstants(String type, String code, String value){
		// adding of constants.
		MstbMasterTable newMaster = new MstbMasterTable();
		newMaster.setMasterType(type);
		newMaster.setMasterCode(code);
		newMaster.setMasterValue(value);
		constantDao.save(newMaster);
		// reloading constants
		init();
	}
	/**
	 * method to get master table with primary key masterNo
	 * @param masterNo
	 * @return
	 * @Deprecated - use getMasterTable(String masterType, String masterCode) instead
	 */
	@Deprecated
	public static MstbMasterTable getMasterTable(Integer masterNo){
		if(masterNo!=null){
			Iterator<MstbMasterTable> iter = activeMasterTables.iterator();
			while(iter.hasNext()){
				MstbMasterTable temp = iter.next();
				if(masterNo.equals(temp.getMasterNo())){
					return temp;
				}
			}
		}
		return null;
	}
	/**
	 * method to get masters of a type
	 * @param type - the type of master
	 * @return - a map with <Code, Value>
	 */
	public static Map<String, String> getMasters(String type){
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for (MstbMasterTable masterTable : activeMasterTables) {
			if(masterTable.getMasterType().equals(type)){
				returnMap.put(masterTable.getMasterCode(), masterTable.getMasterValue());
			}
		}
		return returnMap;
	}

	public static Map<String, String> getAllMasters(String type){
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for (MstbMasterTable masterTable : masterTables) {
			if(masterTable.getMasterType().equals(type)){
				returnMap.put(masterTable.getMasterCode(), masterTable.getMasterValue());
			}
		}
		return returnMap;
	}

	public static Map<String, String> getIndustries(){
		return getMasters(INDUSTRY_MASTER_CODE);
	}
	public static Map<String, String> getPaymentModes(){
		return getMasters(PAYMENT_MODE);
	}
	public static Map<String, String> getCancellationReasons(){
		return getMasters(CANCELLATION_REASON);
	}
	public static Map<String, String> getReplaceReasons(){
		return getMasters(REPLACEMENT_REASON);
	}
	public static Map<String, String> getProductSuspendReasons(){
		return getMasters(PRODUCT_SUSPEND_REASON);
	}
	public static Map<String, String> getProductTerminateReasons(){
		return getMasters(PRODUCT_TERMINATE_REASON);
	}
	public static Map<String, String> getProductReactivateReasons(){
		return getMasters(PRODUCT_REACTIVATE_REASON);
	}
	public static Map<String, String> getRecycleReasons(){
		return getMasters(RECYCLE_REASON_TYPE);
	}
	public static Map<String, String> getCountries(){
		return getMasters(COUNTRY_MASTER_CODE);
	}
	public static Map<String, String> getInformationSources(){
		return getMasters(INFORMATION_SOURCE_MASTER_CODE);
	}
	public static Map<String, String> getVehicleTypes(){
		return getMasters(VEHICLE_TYPE_MASTER_CODE);
	}
	public static Map<String, String> getJobStatuses(){
		return getMasters(JOB_STATUS_MASTER_CODE);
	}
	public static Map<String, String> getSalutations(){
		return getMasters(SALUTATION_MASTER_CODE);
	}
	public static Map<String, String> getRace(){
		return getMasters(RACE_MASTER_CODE);
	}
	public static Map<String, String> getApprovalLimits(){
		return getMasters(APPROVER_LIMIT);
	}
	public static Map<String, String> getRelationships(){
		return getMasters(RELATIONSHIP_TO_ACCOUNT_CODE);
	}
	public static Map<String, String> getVehicleTripTypes(){
		return getMasters(VEHICLE_TRIP_TYPE);
	}

	public static Map<String, String> getAccountSuspendReasons(){
		return getMasters(ACCT_SUSPEND_REASON);
	}
	public static Map<String, String> getAccountReactivateReasons(){
		return getMasters(ACCT_REACTIVATE_REASON);
	}
	public static Map<String, String> getAccountTerminateReasons(){
		return getMasters(ACCT_TERMINATE_REASON);
	}
	public static Map<String, String> getServiceProvider(){
		return getMasters(SERVICE_PROVIDER);
	}
	public static Map<String, String> getNoteReasons(){
		return getMasters(NOTE_REASON);
	}
	public static Map<String, String> getMiscInvoiceItems(){
		return getMasters(MISC_INV_ITEM);
	}
	public static Map<String, String> getPrinters(){
		return getMasters(PRINTERS);
	}
	public static Map<String, String> getJobType(){
		return getMasters(JOB_TYPE);
	}
	public static Map<String, String> getVehicleModel(){
		return getMasters(VEHICLE_MODEL);
	}
	public static Map<String, String> getBusinessUnits(){
		Map<String, String> sortedByValueBusinessUnits = getMasters(GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE);
		Map<String, String> sortedByCodeBusinessUnits = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});
		sortedByCodeBusinessUnits.putAll(sortedByValueBusinessUnits);
		return sortedByCodeBusinessUnits;
	}

	public static Map<String, String> getSMSFormat(){
		Map<String, String> sortedByValueSMSFormat = getMasters(TXSMS_MASTER_TYPE);
		Map<String, String> sortedByCodeSMSFormat = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});

		sortedByCodeSMSFormat.putAll(sortedByValueSMSFormat);
		return sortedByCodeSMSFormat;
	}

	public static Map<String, String> getSMSExpiryFormat(){
		Map<String, String> sortedByValueSMSFormat = getMasters(EXSMS_MASTER_TYPE);
		Map<String, String> sortedByCodeSMSFormat = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});

		sortedByCodeSMSFormat.putAll(sortedByValueSMSFormat);
		return sortedByCodeSMSFormat;
	}

	public static Map<String, String> getSMSTopUpFormat(){
		Map<String, String> sortedByValueSMSFormat = getMasters(TUSMS_MASTER_TYPE);
		Map<String, String> sortedByCodeSMSFormat = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});

		sortedByCodeSMSFormat.putAll(sortedByValueSMSFormat);
		return sortedByCodeSMSFormat;
	}

	public static Integer getSortPagingSize(){
		Map<String, String> pageSize = getMasters(SORTING_PAGING_SIZE);
		for(String page : pageSize.keySet()){
			try{
				return Integer.parseInt(pageSize.get(page));
			}catch(NumberFormatException nfe){
				logger.error(nfe);
				return 10;
			}
		}
		return 10;
	}
	public static Integer getMaxQueryResult(){
		Map<String, String> maxSize = getMasters(MAX_QUERY_RESULT);
		for(String max : maxSize.keySet()){
			try{
				return Integer.parseInt(maxSize.get(max));
			}catch(NumberFormatException nfe){
				logger.error(nfe);
				return 200;
			}
		}
		return 200;
	}

	public static Integer getMaxReportRecord(){
		Map<String, String> maxSize = getMasters(MAX_REPORT_RECORD);
		for(String max : maxSize.keySet()){
			try{
				return Integer.parseInt(maxSize.get(max));
			}catch(NumberFormatException nfe){
				logger.error(nfe);
				return 200;
			}
		}
		return 200;
	}

	public static Integer getAccountTerminateGracePeriod(){
		Map<String, String> gracePeriod = getMasters(ACCT_TERMINATE_GRACE_PERIOD);
		for(String grace : gracePeriod.keySet()){
			try{
				return Integer.parseInt(gracePeriod.get(grace));
			}catch(NumberFormatException nfe){
				logger.error(nfe);
				return 6;
			}
		}
		return 6;
	}

	public static List<MstbMasterTable> getAllMasterTable() {
		return masterTables;
	}

	/**
	 * method to get master table object using type and code
	 * @param masterType - the type
	 * @param masterCode - the code
	 * @return
	 */
	public static MstbMasterTable getMasterTable(String masterType, String masterCode){
		if(masterType!=null && masterCode!=null && !masterType.equals("") && !masterCode.equals("")){
			for(MstbMasterTable masterTable : activeMasterTables){
				if(masterType.equals(masterTable.getMasterType()) && masterCode.equals(masterTable.getMasterCode())){
					return masterTable;
				}
			}
		}
		return null;
	}

	public static MstbMasterTable getMasterTableByMasterNo(String masterType, Integer masterNo){
		if(masterType!=null && masterNo!=null && !masterType.equals("") && masterNo != 0){
			for(MstbMasterTable masterTable : masterTables){
				if(masterType.equals(masterTable.getMasterType()) && masterNo.equals(masterTable.getMasterNo())){
					return masterTable;
				}
			}
		}
		return null;
	}

	public static MstbMasterTable getMasterTableByMasterValue(String masterType, String masterValue){
		if(masterType!=null && masterValue!=null && !masterType.equals("") && !masterValue.equals("")){
			for(MstbMasterTable masterTable : masterTables){
				if(masterType.equals(masterTable.getMasterType()) && masterValue.equals(masterTable.getMasterValue())){
					return masterTable;
				}
			}
		}
		return null;
	}

	public static MstbMasterTable getAllMasterTable(String masterType, String masterCode){
		if(masterType!=null && masterCode!=null && !masterType.equals("") && !masterCode.equals("")){
			for(MstbMasterTable masterTable : masterTables){
				if(masterType.equals(masterTable.getMasterType()) && masterCode.equals(masterTable.getMasterCode())){
					return masterTable;
				}
			}
		}
		return null;
	}

	public static MstbMasterTable getMasterTableByInterfaceMapping(String masterType, String interfaceCode){
		if(masterType!=null && interfaceCode!=null && !masterType.equals("") && !interfaceCode.equals("")){
			for(MstbMasterTable masterTable : activeMasterTables){
				if(masterType.equals(masterTable.getMasterType()) && interfaceCode.equals(masterTable.getInterfaceMappingValue())){
					return masterTable;
				}
			}
		}
		return null;
	}

	public static String getEmailText(String emailType, String emailField){
		MstbMasterTable masterTable = getMasterTable(emailType, emailField);
		if(masterTable==null){
			return "";
		}else{
			return masterTable.getMasterValue();
		}
	}

	public static String getTypeName(String type) {
		return typeNames.get(type);
	}
}
