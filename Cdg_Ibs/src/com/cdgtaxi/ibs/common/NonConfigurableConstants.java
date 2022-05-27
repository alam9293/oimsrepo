package com.cdgtaxi.ibs.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NonConfigurableConstants {

	//Hibernate Configuration
	public static final int HIBERNATE_MINIMUM_SIZE = 1000;

	//Bill Gen Created By
	public static final String BILL_GEN_CREATED_BY = "BILL GEN";


	//MAX QUERY RESULT
	// moved to configurable constant.
	//public static final int MAX_QUERY_RESULT = 200;
	public static final int NO_OF_DECIMAL = 2;

	public static final String SUCCESS_FLAG = "success";
	public static final String TRUE_FLAG = "true";
	public static final String IGNORE_FLAG = "ignore";
	public static final String NA_FLAG = "NA";
	// Error Codes for TRIPS Interfaces
	public static final String SUCCESS = "Success";
	public static final String DUPLICATE_JOB_NO = "Duplicated job no";
	public static final String NUMBER_TO_BIG = "Number too big. Please set it as text in excel";
	
	//19/12/2021 trip enhance x config day
	public static final String TRIPDATEIGNORE = "Trip start date is more than X config days from today date";
	
	public static final String MANDATORY_TRIP_START = "Trip start date should be mandatory";
	public static final String MANDATORY_JOB_NO = "Job No should be mandatory";
	public static final String MANDATORY_TRIP_END = "Trip end date should be mandatory";
	public static final String MANDATORY_ENTITY = "Entity should be mandatory";
	public static final String MANDATORY_TXN_AMOUNT = "Fare amount should be mandatory";
	public static final String NO_SUCH_ACCOUNT = "Account does not exist";
	public static final String MANDATORY_VEHICLE_TYPE = "Vehicle Type should be mandatory";
	public static final String MANDATORY_TRIP_TYPE = "Trip Type should be mandatory";
	public static final String NO_SUCH_PRODUCT = "Product does not exist";
	public static final String INVALID_PAYMENT_MODE = "Payment mode does not exist";
	public static final String UNHANDLED_EXCEPTION = "Unhandled exception";
	public static final String USED_PRODUCT = "Product is used";
	public static final String TERMINATED_PRODUCT = "Product is terminated";
	public static final String TERMINATED_ACCOUNT = "Account is terminated";
	public static final String FUTURE_TRIP = "Future Trip";
	public static final String CONCURRENT_EXCEPTION = "Concurrent exception";
	public static final String JOB_TYPE_NOT_FOUND = "Job type master not found";
	public static final String VEHICLE_MODEL_NOT_FOUND = "Vehicle model master not found";
	public static final String TRIP_TYPE_NOT_FOUND = "Trip Type not found in master";
	public static final String TRIP_TYPE_NOT_ACTIVE = "Trip Type not found active";
	public static final String NO_SUCH_EXTERNAL_PRODUCT_TYPE_ACCOUNT = "External Product Type Account does not exist";
	public static final String MORE_THAN_ONE_EXTERNAL_PRODUCT_TYPE_ACCOUNT ="More than one External Product Type Account found";
	public static final String DELETE_CARD_ASSIGNMENT_FAIL ="Error : Unable to delete Card Assignment. Only able to delete future assignment";

	public static final String COD_DESC = "CASH ON DELIVERY";

	public static final String IGNORE = "Ignore";

	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	public static final String STATUS_PENDING = "P";

	public static final String FMS_SERVICE_NORMAL = "NORM";
	public static final String FMS_SERVICE_PREMIER = "PMSV";
	public static final String FMS_REFUND = "R";
	public static final String FMS_COLLECT = "C";
	public static final String JOB_TYPE_STREET = "STREET";

	public static final String INTERFACE_USER = "IBS";
	public static final String FMS_DRIVER_VALIDATION_INTERFACE_ERROR		= "FMS_IE";

	//COMMON ERROR MESSAGE
	public static final String HIBERNATE_ERROR_MESSAGE 	= "Row was updated or deleted by another transaction.\nPlease repeat process.";
	public static final String COMMON_ERROR_MESSAGE 	= "Exception occurred, please inform administrator.";
	public static final String ACCESS_DENIED			= "Access denied, please contact administrator.";
	public static final String ZERO_PDF_RESULT_ERROR = "Unable to Generate PDF. No records found.";

	//COMMON WARNING MESSAGE
	public static String getExceedMaxResultMessage(){
		return "There are more than "+ConfigurableConstants.getMaxQueryResult()+" records found but only "+ConfigurableConstants.getMaxQueryResult()+" records can be displayed";
	}

	//SG Country code
	public static final String SG_COUNTRY_CODE = "SG";

	//Account Category
	public static final LinkedHashMap<String, String> ACCOUNT_CATEGORY	= new LinkedHashMap<String, String>();
	public static final String ACCOUNT_CATEGORY_CORPORATE		= "CORP";
	public static final String ACCOUNT_CATEGORY_DIVISION		= "DIV";
	public static final String ACCOUNT_CATEGORY_DEPARTMENT		= "DEPT";
	public static final String ACCOUNT_CATEGORY_APPLICANT		= "APP";
	public static final String ACCOUNT_CATEGORY_SUB_APPLICANT	= "SAPP";

	//Account Category
	public static final LinkedHashMap<String, String> AS_ACCOUNT_CATEGORY	= new LinkedHashMap<String, String>();
	public static final String AS_ACCOUNT_CATEGORY_CORPORATE		= "CO";
	public static final String AS_ACCOUNT_CATEGORY_DIVISION			= "DI";
	public static final String AS_ACCOUNT_CATEGORY_DEPARTMENT		= "DP";
	public static final String AS_ACCOUNT_CATEGORY_APPLICANT		= "PS";
	public static final String AS_ACCOUNT_CATEGORY_SUB_APPLICANT	= "SA";

	//CARD TYPE
	public static final String CORPORATE_CARD_ID	= "CC"; //hard-code for Product issue to account

	// Premier service type is defaulted to PS
	public static final String PREMIER_SERVICE = "PS";
	// CNII request event type
	public static final String CNII_REQUEST_EVENT_TYPE_NEW			= "N";
	public static final String CNII_REQUEST_EVENT_TYPE_UPDATE		= "U";
	public static final String CNII_REQUEST_EVENT_TYPE_ACTIVATE		= "A";
	public static final String CNII_REQUEST_EVENT_TYPE_DEACTIVATE	= "D";
	// CNII request status
	public static final String CNII_REQUEST_STATUS_PENDING	= "P";
	// CNII request UPDATE
	public static final String CNII_REQUEST_UPDATED_BY_IBS	= "IBS";
	// AS product status
	public static final String AS_REQUEST_PRODUCT_STATUS_ACTIVE 	= "A";
	public static final String AS_REQUEST_PRODUCT_STATUS_INACTIVE 	= "I";
	public static final String AS_PRODUCT_INACTIVE = "INACTIVE";
	public static final String AS_PRODUCT_ACTIVE = "ACTIVE";
	public static final String AS_TERMINATED_EVOUCHER_REASON_CODE = "TC";
	public static final String AS_PRODUCT_STATUS_USED = "Y";
	public static final String AS_PRODUCT_STATUS_NOT_USED = "N";
	// AS transfer mode
	public static final String AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS = "A";

	// Trips
	public static final String TRIPS_REQUEST_STATUS_PENDING	 	= "P";
	public static final String TRIPS_REQUEST_STATUS_IN_PROGRESS = "G";
	public static final String TRIPS_REQUEST_STATUS_COMPLETED	= "C";
	public static final String TRIPS_REQUEST_STATUS_ERROR		= "E";

	//STATUS REMARKS FOR ISSUING PRODUCT
	public static final String STATUS_REMARKS_ISSUE	= "ISSUE CARDS";
	public static final String STATUS_REMARKS_REPLACED	= "REPLACED BY NEW CARD";

	//MANAGE CARD TYPE SORT BY
	public static final String SORT_BY_ISSUE_DATE				= "ISSUE";
	public static final String SORT_BY_EXPIRY_DATE				= "EXPIRY";
	public static final String SORT_BY_SUSPENSION_START_DATE	= "SUSPEND";
	public static final LinkedHashMap<String, String> SORT_BY	= new LinkedHashMap<String, String>();
	// embossing file sort by
	public static final LinkedHashMap<String, String> PRODUCT_EMBOSS_SORT_BY	= new LinkedHashMap<String, String>();
	public static final String PRODUCT_EMBOSS_SORT_BY_ACCT_NO	=	"ACCT";
	public static final String PRODUCT_EMBOSS_SORT_BY_CARD_NO	=	"CARD";
	// ACCOUNT LEVEL
	public static final int CORPORATE_LEVEL = 0;
	public static final int DIVISION_LEVEL = 1;
	public static final int DEPARTMENT_LEVEL = 2;
	public static final int APPLICANT_LEVEL = 0;
	public static final int SUB_APPLICANT_LEVEL = 1;
	//MAIN CONTACT TYPE
	public static final LinkedHashMap<String, String> MAIN_CONTACT		= new LinkedHashMap<String, String>();
	public static final String MAIN_CONTACT_TYPE_BILLING	= "B";
	public static final String MAIN_CONTACT_TYPE_SHIPPING	= "S";
	//MAIN REPORT PURPOSE
	public static final LinkedHashMap<String, String> REPORT_PURPOSE	= new LinkedHashMap<String, String>();
	public static final String REPORT_PURPOSE_CONTACT_PERSON			= "CP";
	public static final String REPORT_PURPOSE_MAIL_MERGE				= "MM";
	// Master Status
	public static final LinkedHashMap<String, String> MASTER_STATUS		= new LinkedHashMap<String, String>();
	public static final String MASTER_STATUS_ACTIVE						= "A";
	public static final String MASTER_STATUS_INACTIVE					= "I";
	// Account Templates
	public static final LinkedHashMap<String, String> ACCOUNT_TEMPLATES		= new LinkedHashMap<String, String>();
	public static final String ACCOUNT_TEMPLATES_CORPORATE					= "C";
	public static final String ACCOUNT_TEMPLATES_PERSONAL					= "P";
	// Billing Cycles
	public static final LinkedHashMap<String, String> BILLING_CYCLES	= new LinkedHashMap<String, String>();
	public static final String BILLING_CYCLES_MONTHLY					= "M";
	public static final String BILLING_CYCLES_BIWEEKLY					= "W";
	// Rewards Type
	public static final LinkedHashMap<String, String> REWARDS_TYPE		= new LinkedHashMap<String, String>();
	public static final String REWARDS_TYPE_PER_DOLLAR					= "D";
	public static final String REWARDS_TYPE_PER_TRIP					= "T";
	// Rewards Redemption
	public static final LinkedHashMap<String, String> REWARDS_REDEEM_FROM	= new LinkedHashMap<String, String>();
	public static final String REWARDS_REDEEM_FROM_CURR						= "C";
	public static final String REWARDS_REDEEM_FROM_PREV						= "P";
	// Stock Txn Type
	public static final LinkedHashMap<String, String> STOCK_TXN_TYPE	= new LinkedHashMap<String, String>();
	public static final String STOCK_TXN_TYPE_IN						= "I";
	public static final String STOCK_TXN_TYPE_OUT						= "O";
	public static final String STOCK_TXN_TYPE_ISSUED					= "S";
	public static final String STOCK_TXN_TYPE_REDEEMED					= "R";
	// Inventory Item Status
	public static final LinkedHashMap<String, String> INVENTORY_ITEM_STATUS	= new LinkedHashMap<String, String>();
	public static final LinkedHashMap<String, String> INVENTORY_ITEM_APPROVAL_STATUS	= new LinkedHashMap<String, String>();
	public static final String INVENTORY_ITEM_STATUS_ISSUED					= "I";
	public static final String INVENTORY_ITEM_STATUS_REDEEMED				= "R";
	public static final String INVENTORY_ITEM_STATUS_SUSPENDED				= "S";
	public static final String INVENTORY_ITEM_STATUS_VOID					= "V";
	public static final String INVENTORY_ITEM_STATUS_PENDING_SUSPENSION		= "PS";//"Z";
	public static final String INVENTORY_ITEM_STATUS_PENDING_VOID			= "PV";//"W";
	public static final String INVENTORY_ITEM_STATUS_PENDING_REACTIVATION	= "PR";//"X";
	public static final String INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION = "PE";//"Y";
	public static final String INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION = "PD";//"U";
	public static final String INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE = "PX";

	// Inventory Request Status
	public static final LinkedHashMap<String, String> INVENTORY_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String INVENTORY_REQUEST_STATUS_NEW						= "N";
	public static final String INVENTORY_REQUEST_STATUS_PENDING					= "P";
	public static final String INVENTORY_REQUEST_STATUS_APPROVED				= "A";
	public static final String INVENTORY_REQUEST_STATUS_REJECTED				= "R";
	public static final String INVENTORY_REQUEST_STATUS_ISSUED					= "I";

	// Promotion Event
	public static final LinkedHashMap<String, String> PROMOTION_EVENT	= new LinkedHashMap<String, String>();
	public static final String PROMOTION_EVENT_CREATE	= "C";
	public static final String PROMOTION_EVENT_EDIT		= "E";
	public static final String PROMOTION_EVENT_DELETE	= "D";

	// Promotion Request Status
	public static final LinkedHashMap<String, String> PROMOTION_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String PROMOTION_REQUEST_STATUS_NEW			= "N";
	public static final String PROMOTION_REQUEST_STATUS_PENDING		= "P";
	public static final String PROMOTION_REQUEST_STATUS_APPROVED	= "A";
	public static final String PROMOTION_REQUEST_STATUS_REJECTED	= "R";

	// Promotion Status
	public static final LinkedHashMap<String, String> PROMOTION_STATUS	= new LinkedHashMap<String, String>();
	public static final String PROMOTION_STATUS_NEW							= "N";
	public static final String PROMOTION_STATUS_ACTIVE						= "A";
	public static final String PROMOTION_STATUS_DELETED						= "D";
	public static final String PROMOTION_STATUS_PENDING_APPROVAL_CREATE		= "PC";
	public static final String PROMOTION_STATUS_PENDING_APPROVAL_EDIT		= "PE";
	public static final String PROMOTION_STATUS_PENDING_APPROVAL_DELETE		= "PD";


	// Application Status
	public static final LinkedHashMap<String, String> APPLICATION_STATUS		= new LinkedHashMap<String, String>();
	public static final String APPLICATION_STATUS_NEW							= "N";
	public static final String APPLICATION_STATUS_DRAFT							= "D";
	public static final String APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL	= "P1";
	public static final String APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL	= "P2";
	public static final String APPLICATION_STATUS_APPROVED						= "A";
	public static final String APPLICATION_STATUS_REJECTED						= "R";
	// Account Status
	public static final LinkedHashMap<String, String> ACCOUNT_STATUS		= new LinkedHashMap<String, String>();
	public static final String ACCOUNT_STATUS_PENDING_ACTIVATION			= "PA";
	public static final String ACCOUNT_STATUS_ACTIVE						= "A";
	public static final String ACCOUNT_STATUS_SUSPENDED						= "S";
	public static final String ACCOUNT_STATUS_PARENT_SUSPENDED				= "PS";
	public static final String ACCOUNT_STATUS_CLOSED						= "C";
	public static final String ACCOUNT_STATUS_TERMINATED					= "T";
	// billing change request status
	public static final LinkedHashMap<String, String> BILLING_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String BILLING_REQUEST_STATUS_NEW						= "N";
	public static final String BILLING_REQUEST_STATUS_PENDING					= "P";
	public static final String BILLING_REQUEST_STATUS_APPROVED					= "A";
	public static final String BILLING_REQUEST_STATUS_REJECTED					= "R";
	public static final String BILLING_REQUEST_STATUS_REQ_APPROVED				= "RA";
	public static final String BILLING_REQUEST_STATUS_REQ_REJECTED				= "RR";
	// billing change events
	public static final String BILLING_REQUEST_EVENT_CREATE	= "C";
	public static final String BILLING_REQUEST_EVENT_UPDATE	= "U";
	public static final String BILLING_REQUEST_EVENT_DELETE	= "D";
	// credit review request status
	public static final LinkedHashMap<String, String> CREDIT_REVIEW_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String CREDIT_REVIEW_REQUEST_STATUS_NEW						= "N";
	public static final String CREDIT_REVIEW_REQUEST_STATUS_PENDING					= "P";
	public static final String CREDIT_REVIEW_REQUEST_STATUS_APPROVED				= "A";
	public static final String CREDIT_REVIEW_REQUEST_STATUS_REJECTED				= "R";

	// Product Status
	public static final LinkedHashMap<String, String> PRODUCT_STATUS	= new LinkedHashMap<String, String>();
	public static final String PRODUCT_STATUS_NEW						= "N";
	public static final String PRODUCT_STATUS_ACTIVE					= "A";
	public static final String PRODUCT_STATUS_USED						= "U";
	public static final String PRODUCT_STATUS_SUSPENDED					= "S";
	public static final String PRODUCT_STATUS_PARENT_SUSPENDED			= "PS";
	public static final String PRODUCT_STATUS_TERMINATED				= "T";
	public static final String PRODUCT_STATUS_RECYCLED					= "R";
	public static final String PRODUCT_STATUS_ALL						= "ALL";
	public static final String PRODUCT_RECYCLED_REMARKS					= "RECYCLED";

	//Subscription Approve Status
	public static final LinkedHashMap<String, String> SUBSCRIPTION_APPROVE_STATUS	= new LinkedHashMap<String, String>();
	public static final LinkedHashMap<String, String> SUBSCRIPTION_ACTION_STATUS	= new LinkedHashMap<String, String>();
	public static final String SUBSCRIPTION_APPROVE_STATUS_REJECTED				= "R";
	public static final String SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED		= "PA";
	public static final String SUBSCRIPTION_APPROVE_STATUS_APPROVE				= "A";

	public static final String SUBSCRIPTION_ACTION_STATUS_SUBSCRIBE				= "S";
	public static final String SUBSCRIPTION_ACTION_STATUS_UNSUBSCRIBE				= "U";

	public static final String SUBSCRIPTION_STATUS_UNSUBSCRIBE				= "U";
	public static final String SUBSCRIPTION_STATUS_SUBSCRIBE				= "S";
	public static final String SUBSCRIPTION_STATUS_SUBSCRIBE_EDIT				= "E";
	public static final String SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT				= "SD";
	public static final String SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT				= "UD";

	//Added by vani 05.01.11 for UAM Report
	public static final LinkedHashMap<String, String> USER_STATUS	= new LinkedHashMap<String, String>();
	public static final String USER_STATUS_ACTIVE			= "A";
	public static final String USER_STATUS_INACTIVE			= "I";

	//DURATION TYPES FOR PRODUCT RENEW
	public static final LinkedHashMap<String, String> PRODUCT_RENEW_DURATION_TYPE	= new LinkedHashMap<String, String>();
	public static final String PRODUCT_RENEW_DURATION_TYPE_DURATION	= "DURA";
	public static final String PRODUCT_RENEW_DURATION_TYPE_DATE	= "DATE";
	//DURATION LENGTH FOR PRODUCT RENEW
	public static final LinkedHashMap<String, String> PRODUCT_RENEW_DURATION_LENGTH	= new LinkedHashMap<String, String>();
	public static final String PRODUCT_RENEW_DURATION_LENGTH_TYPE1	= "1";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_TYPE2	= "2";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_TYPE3	= "3";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_TYPE4	= "6";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_TYPE5	= "12";
	//REVIEW TYPE FOR PRODUCT, UPDATE CREDIT LIMIT
	public static final LinkedHashMap<String, String> REVIEW_TYPES	= new LinkedHashMap<String, String>();
	public static final String REVIEW_TYPE_PERMANENT  	= "P";
	public static final String REVIEW_TYPE_TEMPORARY	= "T";
	// product renewal duration
	public static final String PRODUCT_RENEW_DURATION_LENGTH_1_MONTH	= "1";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_2_MONTHS	= "2";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_3_MONTHS	= "3";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_6_MONTHS	= "6";
	public static final String PRODUCT_RENEW_DURATION_LENGTH_12_MONTHS	= "12";

	//Email Address checking Reg Expression
	public static final String EMAIL_REGEX = "^[ _A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z ]{2,})$";

	// Boolean
	public static final LinkedHashMap<String, String> BOOLEAN		= new LinkedHashMap<String, String>();
	public static final String BOOLEAN_YES							= "Y";
	public static final String BOOLEAN_NO							= "N";
	public static final String BOOLEAN_NA							= "NA";
	//Boolean Yes and No for Product Type
	public static final LinkedHashMap<String, String> BOOLEAN_YN	= new LinkedHashMap<String, String>();
	public static final String BOOLEAN_YN_YES						= "Y";
	public static final String BOOLEAN_YN_NO						= "N";

	//SMS Flag
	public static final LinkedHashMap<String, String> SMS_FLAG	= new LinkedHashMap<String, String>();
	public static final String SMS_FLAG_YES 				= "Y";
	public static final String SMS_FLAG_NO	 				= "N";
	public static final String SMS_FLAG_SAME_AS_ACCOUNT 	= "S";

	//Issue Type List (Count and Range)
	public static final String ISSUE_TYPE_COUNT						= "C";
	public static final String ISSUE_TYPE_RANGE						= "R";
	public static final LinkedHashMap<String, String> ISSUE_TYPE	= new LinkedHashMap<String, String>();
	// INVOICE Status
	public static final LinkedHashMap<String, String> INVOICE_STATUS	= new LinkedHashMap<String, String>();
	public static final String INVOICE_STATUS_OUTSTANDING				= "O";
	public static final String INVOICE_STATUS_CLOSED					= "C";
	public static final String INVOICE_STATUS_CANCELLED					= "D";
	//Invoice Format
	public static final LinkedHashMap<String, String> INVOICE_FORMAT	= new LinkedHashMap<String, String>();
	public static final String INVOICE_FORMAT_ACCOUNT		= "A";
	public static final String INVOICE_FORMAT_SUBACCOUNT	= "S";
	public static final String INVOICE_FORMAT_PERSONAL		= "P";
	public static final String INVOICE_FORMAT_MISC			= "M";
	public static final String INVOICE_FORMAT_DEPOSIT		= "D";
	//Invoice Sorting
	public static final LinkedHashMap<String, String> INVOICE_SORTING	= new LinkedHashMap<String, String>();
	public static final String INVOICE_SORTING_CARD			= "C";
	public static final String INVOICE_SORTING_TXN_DATE		= "T";
	//Invoice Sorting
	public static final LinkedHashMap<String, String> INVOICE_PRINTING	= new LinkedHashMap<String, String>();
	public static final String INVOICE_PRINTING_DOUBLE_SIDED	= "D";
	public static final String INVOICE_PRINTING_SINGLE_SIDED	= "S";
	//Invoice Charge to
	public static final LinkedHashMap<String, String> INVOICE_CHARGE_TO	= new LinkedHashMap<String, String>();
	public static final String INVOICE_CHARGE_TO_PARENT		= "P";
	public static final String INVOICE_CHARGE_TO_SELF		= "S";
	//Credit Limit Type
	public static final String CREDIT_LIMIT_PERMANENT		= "P";
	public static final String CREDIT_LIMIT_TEMPORARY		= "T";
	//Early_Payment_Flag
	public static final String EARLY_PAYMENT_FLAG_YES 		= "Y";
	public static final String EARLY_PAYMENT_FLAG_NO	 	= "N";
	public static final String EARLY_PAYMENT_FLAG_BILLED 	= "B";
	//Late Payment Flag
	public static final String LATE_PAYMENT_FLAG_YES	= "Y";
	public static final String LATE_PAYMENT_FLAG_NO		= "N";
	//Sumary Type
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_CARD_REPLACEMENT 	= "RF"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_CREDIT_DEBIT_NOTE 	= "N";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT				= "DP"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_EARLY_PAYMENT	 	= "EP"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_LATE_PAYMENT			= "LP"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_SUBSCRIPTION_FEE		= "SF"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_ISSUANCE_FEE			= "IF"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_VOLUME_DISCOUNT		= "VD"; //*
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_MISC					= "MS";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREMIER_SERVICE		= "PS";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_NORMAL				= "NM";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_VALUE	 		= "P_ISVL";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_CASHPLUS		= "P_ISCP";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_VALUE	 		= "P_TUVL";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_CASHPLUS		= "P_TUCP";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_DISCOUNT	 			= "P_DC";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE	 		= "P_IF";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE_WAIVAL	= "P_IFW";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE	 		= "P_TF";
	public static final String INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE_WAIVAL	= "P_TFW";

	//Summary Name
	public static final String INVOICE_SUMMARY_NAME_DELIVERY_FEE 	= "DELIVERY FEE"; //*

	public static final HashMap<String, String> INVOICE_SUMMARY_TYPE_GROUP_NAME = new HashMap<String, String>();

	// Transaction Type
	public static final LinkedHashMap<String,String> TRANSACTION_TYPE = new LinkedHashMap<String, String>();
	public static final String TRANSACTION_TYPE_REPLACEMENT_FEE 				= "RF";
	public static final String TRANSACTION_TYPE_FARE 							= "FA";
	public static final String TRANSACTION_TYPE_ADMIN_FEE 						= "AF";
	public static final String TRANSACTION_TYPE_DEPOSIT 						= "DP";
	public static final String TRANSACTION_TYPE_SUBSCRIPTION_FEE 				= "SF";
	public static final String TRANSACTION_TYPE_ISSUANCE_FEE 					= "IF";
	public static final String TRANSACTION_TYPE_VOLUME_DISCOUNT 				= "VD";
	public static final String TRANSACTION_TYPE_PROMOTION_DISCOUNT 				= "PR";
	public static final String TRANSACTION_TYPE_LATE_PAYMENT 					= "LP";
	public static final String TRANSACTION_TYPE_EARLY_PAYMENT 					= "EP";
	public static final String TRANSACTION_TYPE_MISC							= "MS";
	public static final String TRANSACTION_TYPE_NON_BILLABLE_CHARGEBACK			= "NC";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_CHARGEBACK			= "BC";
	public static final String TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT		= "NF";
	public static final String TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT		= "NA";

	public static final String TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT		= "NP";
	
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR					= "MR";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR_MARKUP			= "MRM";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR_COMMISSION 		= "MRC";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR_INTERCHANGE		= "MRI";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR_SCHEME_FEE		= "MRS";
	public static final String TRANSACTION_TYPE_BANK_ADVICE_MDR_ADJUSTMENT		= "MA";
	public static final String TRANSACTION_TYPE_CONTRA_AND_CLEARING				= "CC";

	public static final String TRANSACTION_TYPE_PREPAID_ISSUANCE_FEE			= "PIF";
	public static final String TRANSACTION_TYPE_PREPAID_TOPUP_FEE				= "PTF";
	public static final String TRANSACTION_TYPE_PREPAID_TRANSFER_FEE			= "PXF";
	public static final String TRANSACTION_TYPE_PREPAID_REPLACEMENT_FEE			= "PRF";
	public static final String TRANSACTION_TYPE_PREPAID_DISCOUNT				= "PDI";
	public static final String TRANSACTION_TYPE_PREPAID_DEFERRED_INCOME			= "PDR";
	public static final String TRANSACTION_TYPE_PREPAID_PREPAYMENT_CASHPLUS		= "PPC";
	public static final String TRANSACTION_TYPE_PREPAID_PROMOTION_EXPENSE		= "PPE";
	public static final String TRANSACTION_TYPE_PREPAID_PAYMENT_TO_DRIVER		= "PPM";
	public static final String TRANSACTION_TYPE_PREPAID_PAYABLE_TO_DRIVER		= "PPB";
	public static final String TRANSACTION_TYPE_PREPAID_ADMIN_FEE_FORFEIT		= "PAF";

	// Transaction Code
	public static final String TRANSACTION_CODE_REPLACEMENT_FEE					= "CRPLFEE";

	public static final HashMap<String, String> TXN_TYPE_TO_INV_SUMM_TYPE_MAP = new HashMap<String, String>();

	// Trip Status
	public static final LinkedHashMap<String, String> TXN_STATUS = new LinkedHashMap<String, String>();

	public static final String TRANSACTION_STATUS_BILLED = "B";
	public static final String TRANSACTION_STATUS_ACTIVE = "A";
	public static final String TRANSACTION_STATUS_REFUND = "R";
	public static final String TRANSACTION_STATUS_VOID = "V";
	public static final String TRANSACTION_STATUS_PENDING_EDIT_ACTIVE = "PA";
	public static final String TRANSACTION_STATUS_PENDING_EDIT_BILLED = "PB";

	public static final String TRANSACTION_SCREEN_VOID = "VOID";
	public static final String TRANSACTION_SCREEN_EDIT = "EDIT";

	public static final LinkedHashMap<String, String> TRANSACTION_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String TRANSACTION_REQUEST_STATUS_NEW						= "N";
	public static final String TRANSACTION_REQUEST_STATUS_PENDING_VOID				= "V"; // PENDING APPR(ACTIVE)
	public static final String TRANSACTION_REQUEST_STATUS_PENDING_REFUND			= "F"; // PENDING APPR(BILLED)
	public static final String TRANSACTION_REQUEST_STATUS_APPROVED					= "A";
	public static final String TRANSACTION_REQUEST_STATUS_REJECTED					= "R";


	// Deposit Txn Type
	public static final String DEPOSIT_TXN_TYPE_REQUEST 	= "R";
	public static final String DEPOSIT_TXN_TYPE_REFUND 		= "F";

	// bill gen request status
	public static final LinkedHashMap<String,String> BILL_GEN_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String BILL_GEN_REQUEST_STATUS_PENDING	 			= "P";
	public static final String BILL_GEN_REQUEST_STATUS_PENDING_PROGRESS	 	= "N";
	public static final String BILL_GEN_REQUEST_STATUS_IN_PROGRESS 			= "G";
	public static final String BILL_GEN_REQUEST_STATUS_COMPLETED			= "C";
	public static final String BILL_GEN_REQUEST_STATUS_DELETED				= "D";
	public static final String BILL_GEN_REQUEST_STATUS_ERROR				= "E";
	public static final String BILL_GEN_REQUEST_STATUS_REGENERATED			= "R";

	// account activation error codes
	public static final String ACCOUNT_ACTIVATION_NO_ERROR				= "NE";
	public static final String ACCOUNT_ACTIVATION_ERROR_MAIN_BILLING 	= "MB";
	public static final String ACCOUNT_ACTIVATION_ERROR_MAIN_SHIPPING 	= "MS";
	public static final String ACCOUNT_ACTIVATION_ERROR_BILLING 		= "B";
	public static final String ACCOUNT_ACTIVATION_INTERFACE_ERROR		= "IE";

	// Volume Discount Type
	public static final LinkedHashMap<String, String> VOLUME_DISCOUNT_TYPE	= new LinkedHashMap<String, String>();
	public static final String VOLUME_DISCOUNT_TYPE_DOLLAR 	= "D";
	public static final String VOLUME_DISCOUNT_TYPE_TRIP 	= "T";

	//Payment Modes
	public static final String PAYMENT_MODE_CREDIT_CARD		= "CC";
	public static final String PAYMENT_MODE_MEMO			= "MEMO";
	public static final String PAYMENT_MODE_CHEQUE			= "CQ";
	public static final String PAYMENT_MODE_GIRO			= "GR";
	public static final String PAYMENT_MODE_CASH			= "CA";
	public static final String PAYMENT_MODE_CONTRA			= "CT";
	public static final String PAYMENT_MODE_AXS				= "AXS";
	public static final String PAYMENT_MODE_BANK_TRANSFER	= "BT";
	public static final String PAYMENT_MODE_CONTRACT		= "CON";
	public static final String PAYMENT_MODE_OTHERS			= "OTHERS";
	public static final String PAYMENT_MODE_DIRECT_RECEIPT  = "DR";
	public static final String PAYMENT_MODE_INTERBANK_GIRO	= "IG";
	public static final Map<String,String> specificPaymentModes = new HashMap<String,String>();
	static {
		specificPaymentModes.put(PAYMENT_MODE_CREDIT_CARD,PAYMENT_MODE_CREDIT_CARD);
		specificPaymentModes.put(PAYMENT_MODE_MEMO,PAYMENT_MODE_MEMO);
		specificPaymentModes.put(PAYMENT_MODE_CHEQUE,PAYMENT_MODE_CHEQUE);
		specificPaymentModes.put(PAYMENT_MODE_GIRO,PAYMENT_MODE_GIRO);
		specificPaymentModes.put(PAYMENT_MODE_CASH,PAYMENT_MODE_CASH);
		specificPaymentModes.put(PAYMENT_MODE_CONTRA,PAYMENT_MODE_CONTRA);
		specificPaymentModes.put(PAYMENT_MODE_AXS,PAYMENT_MODE_AXS);
		specificPaymentModes.put(PAYMENT_MODE_BANK_TRANSFER,PAYMENT_MODE_BANK_TRANSFER);
		specificPaymentModes.put(PAYMENT_MODE_CONTRACT,PAYMENT_MODE_CONTRACT);
	};

	//Receipt Status
	public static final LinkedHashMap<String, String> RECEIPT_STATUS = new LinkedHashMap<String, String>();
	public static final String RECEIPT_STATUS_CLOSED	= "C";
	public static final String RECEIPT_STATUS_EXCESS	= "E";
	public static final String RECEIPT_STATUS_CANCELLED	= "D";
	public static final String RECEIPT_STATUS_PENDING	= "P";

	//Note Type
	public static final LinkedHashMap<String, String> NOTE_TYPE = new LinkedHashMap<String, String>();
	public static final String NOTE_TYPE_CREDIT 	= "C";
	public static final String NOTE_TYPE_DEBIT	 	= "D";

	// Note Status
	public static final LinkedHashMap<String, String> NOTE_STATUS = new LinkedHashMap<String, String>();
	public static final String NOTE_STATUS_ACTIVE					= "A";
	public static final String NOTE_STATUS_BILLED 					= "B";
	public static final String NOTE_STATUS_CANCELLED 				= "C";
	public static final String NOTE_STATUS_PENDING 					= "P";
	public static final String NOTE_STATUS_PENDING_CANCELLATION 	= "L";
	public static final String NOTE_STATUS_REJECTED					= "R";
	public static final String NOTE_STATUS_NEW					= "N";

	//Invoice Txn Note Type
	public static final String INVOICE_TXN_NOTE_TYPE_CREDIT = "CREDIT NOTE";
	public static final String INVOICE_TXN_NOTE_TYPE_DEBIT = "DEBIT NOTE";

	//Note Txn Type
	public static final String NOTE_TXN_TYPE_TRIP_BASED = "T";
	public static final String NOTE_TXN_TYPE_MISC_INV 	= "M";
	public static final String NOTE_TXN_TYPE_CANCEL_INV = "C";

	// Report Name
	public static final String REPORT_NAME_INV_ACCT_FORMAT = "Invoice Account Format";
	public static final String REPORT_NAME_INV_PERS_FORMAT = "Invoice Personal Format";
	public static final String REPORT_NAME_INV_SUB_ACCT_FORMAT = "Invoice Sub Account Format";
	public static final String REPORT_NAME_INV_MISC = "Invoice Miscellaneous";
	public static final String REPORT_NAME_INV_DEPOSIT = "Invoice Deposit";
	public static final String REPORT_NAME_MEMO_DEPOSIT_REFUND = "Deposit Refund Memo";
	public static final String REPORT_NAME_MEMO_OVERPAYMENT_REFUND = "Overpayment Refund Memo";
	public static final String REPORT_NAME_NOTE_CREDIT_DEBIT_RECEIPT = "Credit Debit Note Receipt";
	public static final String REPORT_NAME_INV_MISC_PREPAID = "Invoice Miscellaneous Prepaid";


	public static final String REPORT_NAME_TRIP_RECEIPT = "Trip Receipt";
	//Report Category
	public static final String REPORT_CATEGORY_MEMO = "Memo";
	public static final String REPORT_CATEGORY_NOTE = "Note";
	public static final String REPORT_CATEGORY_TRIPS = "Trips";

	//Bill Gen Setup
	public static final LinkedHashMap<Integer, String> BILL_GEN_SETUP = new LinkedHashMap<Integer, String>();
	public static final Integer BILL_GEN_SETUP_BIWEEKLY_1	= new Integer(1);
	public static final Integer BILL_GEN_SETUP_BIWEEKLY_2 	= new Integer(2);
	public static final Integer BILL_GEN_SETUP_MONTHLY		= new Integer(3);
	public static final Integer BILL_GEN_SETUP_AD_HOC	 	= new Integer(4);
	public static final Integer BILL_GEN_SETUP_DRAFT	 	= new Integer(5);

	//Month
	public static final LinkedHashMap<Integer, String> CALENDAR_MONTH = new LinkedHashMap<Integer, String>();
	public static final Integer JANUARY = new Integer(1);
	public static final Integer FEBRUARY = new Integer(2);
	public static final Integer MARCH = new Integer(3);
	public static final Integer APRIL = new Integer(4);
	public static final Integer MAY = new Integer(5);
	public static final Integer JUNE = new Integer(6);
	public static final Integer JULY = new Integer(7);
	public static final Integer AUGUST = new Integer(8);
	public static final Integer SEPTEMBER = new Integer(9);
	public static final Integer OCTOBER = new Integer(10);
	public static final Integer NOVEMBER = new Integer(11);
	public static final Integer DECEMBER = new Integer(12);

	//INVOICE TYPE
	public static final String INVOICE_TYPE_TAXI_VOUCHER = "TV";

	//Report Lookup Map
	public static final HashMap<String, Map> REPORT_MAP = new HashMap<String, Map>();
	//
	public static final LinkedHashMap<String, String> CDRD_STATUS = new LinkedHashMap<String, String>();
	public static final String CDRD_STATUS_OUSTANDING		= "OS";
	public static final String CDRD_STATUS_RECEIVED			= "RC";
	public static final String CDRD_STATUS_OFFSET			= "OFF";
	public static final String CDRD_STATUS_REFUNDED			= "RF";
	public static final LinkedHashMap<String, String> CDRD_ORDER = new LinkedHashMap<String, String>();
	public static final String CDRD_ORDER_BY_STATUS			= "STATUS";
	public static final String CDRD_ORDER_BY_INVOICE_DATE	= "INVOICE_DATE";
	//ReceiptByPeriodDetailed Report
	public static final LinkedHashMap<String, String> RBPD_ORDER = new LinkedHashMap<String, String>();
	public static final String RBPD_ORDER_BY_CUSTOMER_NAME	= "CN";
	public static final String RBPD_ORDER_BY_RECEIPT_DATE	= "RD";
	public static final String RBPD_ORDER_BY_PAYMENT_MODE	= "PM";
	//Customer Report
	public static final LinkedHashMap<String, String> CUSTOMER_ORDER = new LinkedHashMap<String, String>();
	public static final String CUSTOMER_ORDER_BY_ACCOUNT_NAME		= "AN";
	public static final String CUSTOMER_ORDER_BY_BUSINESS_NATURE	= "BN";
	public static final LinkedHashMap<String, String> ACCOUNT_TYPE = new LinkedHashMap<String, String>();
	public static final String ACCOUNT_TYPE_CORPORATE	= "C";
	public static final String ACCOUNT_TYPE_PERSONAL	= "P";

	//Premier Service Trip Reconciliation Report
	public static final LinkedHashMap<String, String> PSTR_ORDER = new LinkedHashMap<String, String>();
	public static final String PSTR_ORDER_BY_ACCOUNT_NO = "AO";
	public static final String PSTR_ORDER_BY_ACOUNT_NAME = "AN";
	public static final String PSTR_ORDER_BY_TRIP_DATE = "TD";
	public static final String PSTR_ORDER_BY_UPLOAD_DATE = "UD";
	// CustomerUsageComparsion Report
	public static final LinkedHashMap<String, String> CUC_ORDER = new LinkedHashMap<String, String>();
	public static final String CUC_ORDER_BY_CUSTOMER_NAME = "CN";
	public static final String CUC_ORDER_BY_TOTAL = "TOTAL";
	// SalesReportBySalesPerson Report
	public static final LinkedHashMap<String, String> SR_ORDER = new LinkedHashMap<String, String>();
	public static final String SR_ORDER_BY_SALESPERSON_NAME = "SPN";
	public static final String SR_ORDER_BY_TOTAL = "TOTAL";

	//public static  Map<String, String> CUSTOMER_INDUSTRIES = new HashMap<String, String>();
	public static  Map<String, String> SALES_PERSONS = new HashMap<String, String>();
	//Card Holder Report
	public static final LinkedHashMap<String, String> CARD_HOLDER_ORDER = new LinkedHashMap<String, String>();
	public static final String CARD_HOLDER_ORDER_BY_ACCOUNT	= "ACCT";
	public static final String CARD_HOLDER_ORDER_BY_CARD_NO	= "CARD_NO";
	//CreditDebitNote Report
	public static final LinkedHashMap<String, String> CDN_ORDER = new LinkedHashMap<String, String>();
	public static final String CDN_ORDER_BY_CUSTOMER_NAME	= "CN";
	public static final String CDN_ORDER_BY_NOTE_DATE		= "ND";
	public static final String CDN_ORDER_BY_INVOICE_NO		= "INVNO";
	public static final String CDN_ORDER_BY_NOTE_TYPE		= "NT";
	//CreditDebitNote Report
	public static final LinkedHashMap<String, String> DCDL_ORDER = new LinkedHashMap<String, String>();
	public static final String DCDL_ORDER_BY_BANK			= "BANK";
	public static final String DCDL_ORDER_BY_BRANCH		= "BRANCH";
	public static final String DCDL_ORDER_BY_CHEQUE_NO		= "CHEQUE_NO";
	//CreditBalance Report
	public static final LinkedHashMap<String, String> CREDIT_BALANCE_ORDER = new LinkedHashMap<String, String>();
	public static final String CREDIT_BALANCE_ORDER_BY_ACCOUNT_NAME			= "CN";
	public static final String CREDIT_BALANCE_ORDER_BY_CREDIT_BALANCE		= "CR";
	//ContactPerson Report
	public static final LinkedHashMap<String, String> CONTACT_PERSON_ORDER = new LinkedHashMap<String, String>();
	public static final String CONTACT_PERSON_ORDER_BY_ACCOUNT_NAME			= "AN";
	public static final String CONTACT_PERSON_ORDER_BY_CONTACT_PERSON		= "CP";
	//CorporateCustomerUsageBreakdown Report
	public static final LinkedHashMap<String, String> CORP_CUST_USAGE_BREAKDOWN_ORDER = new LinkedHashMap<String, String>();
	public static final String CORP_CUST_USAGE_BREAKDOWN_ORDER_BY_INVOICE_AMOUNT			= "IA";
	//CardInProduction Report
	public static final LinkedHashMap<String, String> CARD_IN_PRODUCTION_ORDER = new LinkedHashMap<String, String>();
	public static final String CARD_IN_PRODUCTION_BY_ACCOUNT_NAME	= "AN";
	public static final String CARD_IN_PRODUCTION_BY_CARD_NO		= "CN";
	public static final String CARD_IN_PRODUCTION_BY_CARD_STATUS	= "CS";
	public static final String CARD_IN_PRODUCTION_BY_CARD_REASON	= "CR";
	//Bank Chargeback Report
	public static final LinkedHashMap<String, String> BANK_CHARGEBACK_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String BANK_CHARGEBACK_REPORT_BY_CARD_NUMBER	= "CN";
	public static final String BANK_CHARGEBACK_REPORT_BY_TRIP_DATE		= "TD";
	//Cashless Aging Report Detailed
	public static final LinkedHashMap<String, String> CASHLESS_AGING_REPORT_DETAILED = new LinkedHashMap<String, String>();
	public static final String CASHLESS_AGING_REPORT_DETAILED_BY_BATCH_DATE	= "SD";
	public static final String CASHLESS_AGING_REPORT_DETAILED_BY_BATCH_NO	= "BN";
	//Cashless Bank Collection Detailed
	public static final LinkedHashMap<String, String> CASHLESS_BANK_COLLECTION_DETAILED = new LinkedHashMap<String, String>();
	public static final String CASHLESS_BANK_COLLECTION_DETAILED_BY_BATCH_DATE	= "BDBNCNTD";
	public static final String CASHLESS_BANK_COLLECTION_DETAILED_BY_DRIVER_IC	= "DICSR";
	//Cashless Txn By Amt Range
	public static final LinkedHashMap<String, String> CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE = new LinkedHashMap<String, String>();
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_ALL		= "0";
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_IN_HOUSE	= "1";
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_CC		= "2";
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_NETS		= "3";
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_EZLINK	= "4";
	public static final String CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_DASH		= "5";
	//Invoice Report
	public static final LinkedHashMap<String, String> INVOICE_REPORT_TYPES = new LinkedHashMap<String, String>();
	public static final String INVOICE_REPORT_TYPE_ALL		= "0";
	public static final String INVOICE_REPORT_TYPE_DEPOSIT	= "1";
	public static final String INVOICE_REPORT_TYPE_MISC		= "2";
	public static final String INVOICE_REPORT_TYPE_TRIPS	= "3";
	//Invoice Report
	public static final LinkedHashMap<String, String> INVOICE_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String INVOICE_REPORT_ORDER_CUST_NO		= "CNIN";
	public static final String INVOICE_REPORT_ORDER_CUST_NAME	= "CNCN";
	public static final String INVOICE_REPORT_ORDER_INVOICE_NO	= "IN";

	//PREPAID Approval Report
	public static final LinkedHashMap<String, String> PREPAID_APPROVAL_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String PREPAID_APPROVAL_REPORT_ORDER_REQUEST_DATE		= "RD";

	//PREPAID Top Up Report
	public static final LinkedHashMap<String, String> PREPAID_TOP_UP_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String PREPAID_TOP_UP_REPORT_ORDER_ACCOUNT_NAME		= "AN";
	public static final String PREPAID_TOP_UP_REPORT_ORDER_CARD_NO		= "CN";

	//PREPAID Usage Report
	public static final LinkedHashMap<String, String> PREPAID_USAGE_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String PREPAID_USAGE_REPORT_ORDER_NUM_OF_MONTHS_SUBSCRIBED_DESC		= "NOM";
	public static final String PREPAID_USAGE_REPORT_ORDER_NUM_OF_TOP_UP_DESC				= "TU";
	public static final String PREPAID_USAGE_REPORT_ORDER_PURCHASE_AMOUNT_DESC				= "PA";


	// Financial Memo Report
	public static final LinkedHashMap<String, String> FINANCIAL_MEMO_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String FINANCIAL_MEMO_REPORT_ORDER_MEMO_NO		= "MN";
	public static final String FINANCIAL_MEMO_REPORT_ORDER_MEMO_DATE	= "MD";
	public static final LinkedHashMap<String, String> FINANCIAL_MEMO_REPORT_TYPE = new LinkedHashMap<String, String>();
	public static final String FINANCIAL_MEMO_REPORT_TYPE_DEPOSIT	= "DEPOSIT";
	public static final String FINANCIAL_MEMO_REPORT_TYPE_EXCESS	= "EXCESS";
	//stock take for inventory item Report
	public static final LinkedHashMap<String, String> STOCK_TAKE_ORDER = new LinkedHashMap<String, String>();
	public static final String STOCK_TAKE_ORDER_ITEM_TYPE	= "IT";
	public static final String STOCK_TAKE_ORDER_VOUCHER_NO	= "VN";

	//stock take for inventory item Report
	public static final LinkedHashMap<String, String> MOVEMENT_REPORT_TYPE = new LinkedHashMap<String, String>();
	public static final String MOVEMENT_REPORT_TYPE_SUMMARY		= "S";
	public static final String MOVEMENT_REPORT_TYPE_DETAILED	= "D";

	//Timely Payment Statistics Detailed Report
	public static final LinkedHashMap<String, String> TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE = new LinkedHashMap<String, String>();
	public static final String TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE_PAID_BY_DUE_DATE	= "P";
	public static final String TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE_OVERDUE			= "O";

	//debts reminder letter
	public static final LinkedHashMap<String, String> DEBTS_REMINDER_LETTER_SEQUENCE = new LinkedHashMap<String, String>();
	public static final String DEBTS_REMINDER_LETTER_FIRST		= "First";
	public static final String DEBTS_REMINDER_LETTER_SECOND		= "Second";
	public static final String DEBTS_REMINDER_LETTER_THIRD		= "Third";
	public static final String DEBTS_REMINDER_LETTER_FOURTH		= "Fourth";
	public static final String DEBTS_REMINDER_LETTER_FIFTH		= "Fifth";
	public static final String DEBTS_REMINDER_LETTER_SIXTH		= "Sixth";
	public static final String DEBTS_REMINDER_LETTER_SEVENTH	= "Seventh";
	public static final String DEBTS_REMINDER_LETTER_EIGHTH		= "Eighth";
	public static final String DEBTS_REMINDER_LETTER_NINTH		= "Ninth";
	public static final String DEBTS_REMINDER_LETTER_TENTH		= "Tenth";

	//debts reminder letter
	public static final LinkedHashMap<String, String> DEBTS_REMINDER_LETTER_AGING = new LinkedHashMap<String, String>();
	public static final String DEBTS_REMINDER_LETTER_AGING_GT_0		= "1";
	public static final String DEBTS_REMINDER_LETTER_AGING_GT_30		= "2";
	public static final String DEBTS_REMINDER_LETTER_AGING_GT_60		= "3";
	public static final String DEBTS_REMINDER_LETTER_AGING_GT_90	= "4";

	//loyalty program report
	public static final LinkedHashMap<String, String> LOYALTY_PROGRAM_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String LOYALTY_PROGRAM_REPORT_ORDER_ACCOUNT_NAME		= "AN";
	public static final String LOYALTY_PROGRAM_REPORT_ORDER_CLOSING_BALANCE		= "CB";

	//Credit card promo details
	public static final LinkedHashMap<String, String> CREDIT_CARD_PROMO_DETAIL_ORDER = new LinkedHashMap<String, String>();
	public static final String CREDIT_CARD_PROMO_DETAIL_ORDER_CARD_NO		= "CN";
	public static final String CREDIT_CARD_PROMO_DETAIL_ORDER_TRIP_DATE		= "TD";

	//Trip adjustment report
	public static final LinkedHashMap<String, String> TRIP_ADJUSTMENT_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String TRIP_ADJUSTMENT_REPORT_ORDER_PAYMENT_TYPE		= "PT";
	public static final String TRIP_ADJUSTMENT_REPORT_ORDER_CREATION_DATE		= "CD";

	//Promotion
	public static final LinkedHashMap<String, String> PROMO_ACCT_TYPE = new LinkedHashMap<String, String>();
	public static final String PROMO_ACCT_TYPE_GLOBAL		= "G";
	public static final String PROMO_ACCT_TYPE_ACCOUNT		= "A";
	public static final LinkedHashMap<String, String> PROMO_TYPE = new LinkedHashMap<String, String>();
	public static final String PROMO_TYPE_DOLLAR		= "D";
	public static final String PROMO_TYPE_PERCENTAGE	= "P";

	//Non Billable Batch
	public static final LinkedHashMap<String, String> NON_BILLABLE_BATCH_STATUS = new LinkedHashMap<String, String>();
	public static final String NON_BILLABLE_BATCH_STATUS_OPEN	= "O";
	public static final String NON_BILLABLE_BATCH_STATUS_CLOSED	= "C";

	//Non Billable Txn
	public static final LinkedHashMap<String, String> NON_BILLABLE_TXN_STATUS = new LinkedHashMap<String, String>();
	public static final String NON_BILLABLE_TXN_OPEN		= "O";
	public static final String NON_BILLABLE_TXN_CLOSED		= "C";
	public static final String NON_BILLABLE_TXN_REJECTED	= "R";
	public static final String NON_BILLABLE_TXN_CHARGEBACK	= "B";
	public static final String NON_BILLABLE_TXN_REFUNDED 	= "F";
//	public static final String NON_BILLABLE_TXN_SECOND_CHARGEBACK = "2C";
//	public static final String NON_BILLABLE_TXN_REFUNDED_REVERSE = "FR";
//	public static final String NON_BILLABLE_TXN_SECOND_CHARGEBACK_REVERSE = "2CR";
//	public static final String NON_BILLABLE_TXN_CHARGEBACK_REVERSE = "CR";

	//Rewards PRICE / PTS Ration Master Code (Fixed and cannot be changed)
	public static final String REWARDS_PRICE_PTS_RATIO_MASTER_CODE = "RATIO";
	//Rewards Grace Period Master Code (Fixed and cannot be changed)
	public static final String REWARDS_GRACE_PERIOD_MASTER_CODE = "GRACE";
	public static final String REWARDS_IBS_GRACE_PERIOD_MASTER_CODE = "IBSGR";

	//Validity Period for Product Type
	public static final LinkedHashMap<String, String> VALIDITY_PERIOD_FLAGS = new LinkedHashMap<String, String>();
	public static final String VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM = "T";
	public static final String VALIDITY_PERIOD_FLAG_BY_MMYY = "Y";
	public static final String VALIDITY_PERIOD_FLAG_NO = "N";

	//AS Interface External Product Status
	public static final String EXTERNAL_PRODUCT_STATUS_NEW = "N";
	public static final String EXTERNAL_PRODUCT_STATUS_DELETE = "D";

	//REWARDS TXN
	public static final String REWARDS_TXN_BILLED_FLAG_YES 		= "Y";
	public static final String REWARDS_TXN_BILLED_FLAG_NO	 	= "N";
	public static final String REWARDS_TXN_BILLED_FLAG_BILLED 	= "B";

	//REWARDS REQUEST TYPE
	public static final LinkedHashMap<String, String> REWARDS_ADJ_REQUEST_TYPES = new LinkedHashMap<String, String>();
	public static final String REWARDS_ADJ_REQUEST_TYPE_AWARD 	= "A";
	public static final String REWARDS_ADJ_REQUEST_TYPE_DEDUCT 	= "D";
	public static final LinkedHashMap<String, String> REWARDS_ADJ_REQUEST_STATUS	= new LinkedHashMap<String, String>();
	public static final String REWARDS_ADJ_REQUEST_STATUS_NEW						= "N";
	public static final String REWARDS_ADJ_REQUEST_STATUS_PENDING					= "P";
	public static final String REWARDS_ADJ_REQUEST_STATUS_APPROVED					= "A";
	public static final String REWARDS_ADJ_REQUEST_STATUS_REJECTED					= "R";

	//giro request status
	public static final LinkedHashMap<String,String> GIRO_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String GIRO_REQUEST_STATUS_PENDING	 			= "P";
	public static final String GIRO_REQUEST_STATUS_PENDING_PROGRESS	 	= "N";
	public static final String GIRO_REQUEST_STATUS_IN_PROGRESS 			= "G";
	public static final String GIRO_REQUEST_STATUS_COMPLETED			= "C";
	public static final String GIRO_REQUEST_STATUS_DELETED				= "D";
	public static final String GIRO_REQUEST_STATUS_ERROR				= "E";

	//giro clear fate
	public static final LinkedHashMap<String,String> GIRO_UOB_CLEAR_FATE = new LinkedHashMap<String, String>();
	public static final String GIRO_UOB_CLEAR_FATE_ACCEPTED	= "0";
	public static final String GIRO_UOB_CLEAR_FATE_REJECTED	= "1";

	//giro rejected by
	public static final LinkedHashMap<String,String> GIRO_REJECTED_BY = new LinkedHashMap<String, String>();
	public static final String GIRO_REJECTED_BY_BANK	= "BANK";
	public static final String GIRO_REJECTED_BY_IBS		= "IBS";

	//Error Transaction Report
	public static final LinkedHashMap<String,String> ERROR_TRANSACTION_REPORT_ORDER = new LinkedHashMap<String, String>();
	public static final String ERROR_TRANSACTION_REPORT_ORDER_BY_UPLOAD_DATE	= "UD";
	public static final String ERROR_TRANSACTION_REPORT_ORDER_BY_TXN_DATE		= "TD";
	public static final String ERROR_TRANSACTION_REPORT_ORDER_BY_JOB_NO			= "JN";
	public static final String ERROR_TRANSACTION_REPORT_ORDER_BY_CARD_NO		= "CN";

	// Bill Gen Setup Govt eInv
	public static final String BILL_GEN_SETUP_GOVT_EINV_OFF = "OFF";
	public static final String BILL_GEN_SETUP_GOVT_EINV_ON = "ON";

	// Govt eInv Flag
	public static final LinkedHashMap<String,String> GOVT_EINV_FLAGS = new LinkedHashMap<String, String>();
	public static final String GOVT_EINV_FLAG_INV = "I";
	public static final String GOVT_EINV_FLAG_TRIP = "T";
	public static final String GOVT_EINV_FLAG_NO = "N";

	//  Govt eInv Request status
	public static final LinkedHashMap<String,String> GOVT_EINV_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String GOVT_EINV_REQUEST_STATUS_PENDING	 			= "P";
	public static final String GOVT_EINV_REQUEST_STATUS_PENDING_PROGRESS	= "N";
	public static final String GOVT_EINV_REQUEST_STATUS_IN_PROGRESS 		= "G";
	public static final String GOVT_EINV_REQUEST_STATUS_COMPLETED			= "C";
	public static final String GOVT_EINV_REQUEST_STATUS_ERROR				= "E";
	public static final String GOVT_EINV_REQUEST_STATUS_UPLOADED			= "U";
	public static final String GOVT_EINV_REQUEST_STATUS_DELETED				= "D";

	// Govt eInv Return Status
	public static final LinkedHashMap<String,String> GOVT_EINV_RETURN_STATUS = new LinkedHashMap<String, String>();
	public static final String GOVT_EINV_RETURN_STATUS_SUCCESS = "S";
	public static final String GOVT_EINV_RETURN_STATUS_REJECT = "R";

	// Commission Type
	public static final LinkedHashMap<String, String> COMMISSION_TYPE	= new LinkedHashMap<String, String>();
	public static final String COMMISSION_TYPE_BUSINESS_PARTNER_COMMISSION				= "PCT";
	public static final String COMMISSION_TYPE_FIXED_VALUE_BUSINESS_PARTNER_COMMISSION	= "FIX";


	// Bill Gen Setup PUBBS
	public static final String BILL_GEN_SETUP_PUBBS_OFF = "OFF";
	public static final String BILL_GEN_SETUP_PUBBS_ON = "ON";

	// PUBBS Flag
	public static final LinkedHashMap<String,String> PUBBS_FLAGS = new LinkedHashMap<String, String>();
	public static final String PUBBS_FLAG_YES = "Y";
	public static final String PUBBS_FLAG_NO = "N";

	//  PUBBS Request status
	public static final LinkedHashMap<String,String> PUBBS_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String PUBBS_REQUEST_STATUS_PENDING	 			= "P";
	public static final String PUBBS_REQUEST_STATUS_PENDING_PROGRESS	= "N";
	public static final String PUBBS_REQUEST_STATUS_IN_PROGRESS 		= "G";
	public static final String PUBBS_REQUEST_STATUS_COMPLETED			= "C";
	public static final String PUBBS_REQUEST_STATUS_ERROR				= "E";
	public static final String PUBBS_REQUEST_STATUS_UPLOADED			= "U";
	public static final String PUBBS_REQUEST_STATUS_DELETED				= "D";

	// PUBBS Return Status
	public static final LinkedHashMap<String,String> PUBBS_RETURN_STATUS = new LinkedHashMap<String, String>();
	public static final String PUBBS_RETURN_STATUS_NO_ERROR = "N";
	public static final String PUBBS_RETURN_STATUS_ERROR = "Y";
	public static final String PUBBS_RETURN_STATUS_SUCCESS = "S";
	public static final String PUBBS_RETURN_STATUS_REJECT = "R";

	// FI Flag
	public static final LinkedHashMap<String,String> FI_FLAGS = new LinkedHashMap<String, String>();
	public static final String FI_FLAG_YES = "Y";
	public static final String FI_FLAG_NO = "N";

	//  Recurring Request status
	public static final LinkedHashMap<String,String> RECURRING_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String RECURRING_REQUEST_STATUS_PENDING	 			= "P";
	public static final String RECURRING_REQUEST_STATUS_PENDING_PROGRESS	= "N";
	public static final String RECURRING_REQUEST_STATUS_IN_PROGRESS 		= "G";
	public static final String RECURRING_REQUEST_STATUS_COMPLETED			= "C";
	public static final String RECURRING_REQUEST_STATUS_ERROR				= "E";
	public static final String RECURRING_REQUEST_STATUS_ERROR_NOFILE		= "H";
	public static final String RECURRING_REQUEST_STATUS_COMPLETED_NO_RECORD	= "F";
	public static final String RECURRING_REQUEST_STATUS_UPLOADED			= "U";
	public static final String RECURRING_REQUEST_STATUS_DELETED				= "D";

	// Recurring Return Status
	public static final LinkedHashMap<String,String> RECURRING_RETURN_STATUS = new LinkedHashMap<String, String>();
	public static final String RECURRING_RETURN_STATUS_NO_ERROR = "N";
	public static final String RECURRING_RETURN_STATUS_ERROR = "Y";
	public static final String RECURRING_RETURN_STATUS_SUCCESS = "S";
	public static final String RECURRING_RETURN_STATUS_REJECT = "R";

	/**
	 Added by CDG
	 */

	// jtrauc updated 16/03/2011
	//AS TXN STATUS
	public static final LinkedHashMap<String, String> AS_TXN_STATUS	= new LinkedHashMap<String, String>();
	public static final String AS_ACTIVE				= "A";
	public static final String AS_VOID					= "V";
	public static final String AS_REVERSAL				= "R";

	// AS TXN ORDER
	public static final LinkedHashMap<String, String> AS_OFFLINE_TXN_ORDER	= new LinkedHashMap<String, String>();
	public static final String AS_OFFLINE_TXN_ORDER_TXNDATE				= "TD";
	public static final String AS_OFFLINE_TXN_ORDER_SYSTD				= "ST";

	// AS OFFLINE MESSAGE TYPE
	public static final LinkedHashMap<String, String> AS_OFFLINE_MSG_TYPE	= new LinkedHashMap<String, String>();
	public static final String AS_OFFLINE_SALES				= "000000";
	public static final String AS_OFFLINE_VOID				= "020000";

	public static final String AS_OFFLINE_MSG_TYP_ID = "0220";

	// PREPAID request status
	public static final LinkedHashMap<String,String> PREPAID_REQUEST_STATUS = new LinkedHashMap<String, String>();
	public static final String PREPAID_REQUEST_STATUS_PENDING_APPROVAL	 	= "PA";
	public static final String PREPAID_REQUEST_STATUS_REJECTED	 			= "R";
	public static final String PREPAID_REQUEST_STATUS_PENDING_PAYMENT	 	= "PP";
	public static final String PREPAID_REQUEST_STATUS_COMPLETED				= "C";
	public static final String PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT	= "CWOP";
	public static final String PREPAID_REQUEST_RED_DOT_FAIL	= "F";

	// PREPAID approval status
	public static final LinkedHashMap<String,String> PREPAID_APPROVAL_STATUS = new LinkedHashMap<String, String>();
	public static final String PREPAID_APPROVAL_STATUS_PENDING 				= "P";
	public static final String PREPAID_APPROVAL_STATUS_APPROVED		 		= "A";
	public static final String PREPAID_APPROVAL_STATUS_REJECTED	 			= "R";


	// PREPAID request type
	public static final LinkedHashMap<String,String> PREPAID_REQUEST_TYPE = new LinkedHashMap<String, String>();
	public static final String PREPAID_REQUEST_TYPE_ISSUANCE	 				= "IS";
	public static final String PREPAID_REQUEST_TYPE_TOP_UP	 					= "TU";
	public static final String PREPAID_REQUEST_TYPE_TRANSFER 					= "TR";
	public static final String PREPAID_REQUEST_TYPE_EXTEND_BALANCE_EXPIRY_DATE	= "EE";
	public static final String PREPAID_REQUEST_TYPE_ADJUSTEMENT					= "AD";

	// PREPAID top up type
	public static final LinkedHashMap<String,String> PREPAID_TOP_UP_TYPE = new LinkedHashMap<String, String>();
	public static final String PREPAID_TOP_UP_TYPE_ALL 							= "A";
	public static final String PREPAID_TOP_UP_TYPE_PROMOTION_ONLY 				= "P";
	public static final String PREPAID_TOP_UP_TYPE_NON_PROMOTION		 		= "N";

	// PREPAID transaction type
	public static final LinkedHashMap<String,String> PREPAID_TXN_TYPE = new LinkedHashMap<String, String>();
	public static final String PREPAID_TXN_TYPE_ISSUE	 				= "ISSUE";
	public static final String PREPAID_TXN_TYPE_TOP_UP					= "TOP_UP";
	public static final String PREPAID_TXN_TYPE_ADJUST_VALUE	 		= "ADJ_VL";
	public static final String PREPAID_TXN_TYPE_ADJUST_CASHPLUS	 		= "ADJ_CP";

	public static final String PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_IN	= "EX_TR_IN";
	public static final String PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_OUT	= "EX_TR_OUT";

	public static final String PREPAID_TXN_TYPE_INTERNAL_TRANSFER_IN	= "IN_TR_IN";
	public static final String PREPAID_TXN_TYPE_INTERNAL_TRANSFER_OUT	= "IN_TR_OUT";
	public static final String PREPAID_TXN_TYPE_TRANSFER_FEE			= "TR_FEE";
	public static final String PREPAID_TXN_TYPE_TRIP					= "TRIP";
	public static final String PREPAID_TXN_TYPE_EDIT_TRIP				= "EDIT_TRIP";
	public static final String PREPAID_TXN_TYPE_VOID_TRIP				= "VOID_TRIP";
	public static final String PREPAID_TXN_TYPE_REPLACEMENT_FEE			= "RP_FEE";
	public static final String PREPAID_TXN_TYPE_FORFEIT_VALUE			= "FF_VL";
	public static final String PREPAID_TXN_TYPE_FORFEIT_CASHPLUS		= "FF_CP";
	public static final String PREPAID_TXN_TYPE_FORFEIT_ADJUST_VALUE	= "FF_ADJ_VL";
	public static final String PREPAID_TXN_TYPE_FORFEIT_ADJUST_CASHPLUS	= "FF_ADJ_CP";

	public static final String PREPAID_TXN_TYPE_TRANSFER_ADJUSTMENT		= "TR_ADJ";

	// PRODUCT subscription approval status
	public static final LinkedHashMap<String,String> PRODUCT_SUBSCRIPTION_APPROVAL_STATUS = new LinkedHashMap<String, String>();
	public static final String PRODUCT_SUBSCRIPTION_APPROVAL_STATUS_PENDING	 	= "PSPE";
	public static final String PRODUCT_SUBSCRIPTION_APPROVAL_STATUS_REJECTED	 			= "PSRE";
	public static final String PRODUCT_SUBSCRIPTION_APPROVAL_STATUS_APPROVED	 	= "PSAP";

	//Top Up New Balance Expired Date Duration Type
	public static final LinkedHashMap<String,String> NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE = new LinkedHashMap<String, String>();
	public static final String NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DATE	 			= "DATE";
	public static final String NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION	 		= "DURATION";

	public static final LinkedHashMap<Integer,String> HOURS = new LinkedHashMap<Integer, String>();
	public static final LinkedHashMap<Integer,String> MINUTES = new LinkedHashMap<Integer, String>();
	public static final LinkedHashMap<String,String> DAYSwithSuffix = new LinkedHashMap<String, String>();


	public static final String MODE_NEW = "NEW";
	public static final String MODE_VIEW = "VIEW";
	public static final String MODE_EDIT = "EDIT";

	//E Invoice Attachment Selection
	public static final LinkedHashMap<String,String> E_INVOICE_ATTACHMENT_SELECTION = new LinkedHashMap<String, String>();
	public static final String E_INVOICE_EMAIL_ATTACHMENT_ONE = "O";
	public static final String E_INVOICE_EMAIL_ATTACHMENT_MULTIPLE = "M";

	//E Invoice Page Selection
	public static final LinkedHashMap<String,String> E_INVOICE_PAGE_SELECTION = new LinkedHashMap<String, String>();
	public static final String E_INVOICE_EMAIL_PAGE_ALL = "A";
	public static final String E_INVOICE_EMAIL_PAGE_NO_REPORT = "N";


	public static final LinkedHashMap<String,String> HLA_POLICY_STATUS = new LinkedHashMap<String, String>();
	
	public static final String HLA_POLICY_STATUS_ACTIVE = "A";
	public static final String HLA_POLICY_STATUS_CANCELLED = "C";
	public static final String HLA_POLICY_STATUS_CANCELLED_MANUAL = "M";
	
	//Ayden Payment Matching Status
	public static final LinkedHashMap<String,String> AYDEN_MATCHING_STATUS = new LinkedHashMap<String, String>();
	public static final LinkedHashMap<String,String> AYDEN_COMPLETE_STATUS = new LinkedHashMap<String, String>();

	public static final String AYDEN_MATCHING_STATUS_ALL = "A";
	public static final String AYDEN_MATCHING_STATUS_MATCHED = "M";
	public static final String AYDEN_MATCHING_STATUS_ERROR = "E";
	public static final String AYDEN_MATCHING_STATUS_PENDING = "P";
	public static final String AYDEN_MATCHING_STATUS_TRANSFERED = "T";
	public static final String AYDEN_STATUS_COMPLETED = "C";
	public static final String AYDEN_STATUS_INCOMPLETE = "I";
	public static final String AYDEN_RECORD_STATUS_CHARGEBACK = "Chargeback";
	public static final String AYDEN_RECORD_STATUS_SECOND_CHARGEBACK = "SecondChargeback";
	public static final String AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE = "ChargebackReversed";
	public static final String AYDEN_RECORD_STATUS_SETTLED = "Settled";
	public static final String AYDEN_RECORD_STATUS_REFUNDED = "Refunded";
	public static final String AYDEN_RECORD_STATUS_REFUNDED_REVERSE = "RefundedReversed";
	public static final String AYDEN_RECORD_STATUS_MERCHANT_PAYOUT ="MerchantPayout";
	public static final String AMEX_RECORD_STATUS_CHARGEBACK = "CHARGEBACK";
	public static final String AMEX_STATUS_ADJUSTMENT = "ADJUSTMENT";
	public static final String AMEX_RECORD_STATUS_TXNPRICING ="TXNPRICING";
	public static final String AMEX_RECORD_STATUS_TRANSACTION ="TRANSACTN";
	public static final String AYDEN_RECORD_TYPE_MC = "mc";
	public static final String AYDEN_RECORD_TYPE_MASTERCARD = "mastercard";

	public static final String FROM_AYDEN = "AYDEN";
	public static final String FROM_AMEX = "AMEX";
	public static final String FROM_LAZADA = "LAZ";
	
	
	public static final String PAYMENT_MODE_LAZADA = "LAZ";
	public static final String PAYMENT_MODE_CRCA = "CRCA";
	/**

	 Added by CDG
	 */

	static {
		// Contact Person
		REPORT_PURPOSE.put(REPORT_PURPOSE_CONTACT_PERSON, "CONTACT PERSON");
		REPORT_PURPOSE.put(REPORT_PURPOSE_MAIL_MERGE, "MAIL MERGE");
		// Contact Person
		MAIN_CONTACT.put(MAIN_CONTACT_TYPE_BILLING, "BILLING");
		MAIN_CONTACT.put(MAIN_CONTACT_TYPE_SHIPPING, "SHIPPING");
		// Master Status
		MASTER_STATUS.put(MASTER_STATUS_ACTIVE, "ACTIVE");
		MASTER_STATUS.put(MASTER_STATUS_INACTIVE, "INACTIVE");
		// Account Templates
		ACCOUNT_TEMPLATES.put(ACCOUNT_TEMPLATES_CORPORATE, "CORPORATE");
		ACCOUNT_TEMPLATES.put(ACCOUNT_TEMPLATES_PERSONAL, "PERSONAL");
		// Billing Cycles
		BILLING_CYCLES.put(BILLING_CYCLES_MONTHLY, "MONTHLY");
		BILLING_CYCLES.put(BILLING_CYCLES_BIWEEKLY, "BI-WEEKLY");
		// Rewards Type
		REWARDS_TYPE.put(REWARDS_TYPE_PER_DOLLAR, "PER DOLLAR");
		REWARDS_TYPE.put(REWARDS_TYPE_PER_TRIP, "PER TRIP");
		// Stock Type
		STOCK_TXN_TYPE.put(STOCK_TXN_TYPE_IN, "STOCK IN");
		STOCK_TXN_TYPE.put(STOCK_TXN_TYPE_OUT, "STOCK OUT");
		STOCK_TXN_TYPE.put(STOCK_TXN_TYPE_ISSUED, "ISSUED");
		STOCK_TXN_TYPE.put(STOCK_TXN_TYPE_REDEEMED, "REDEEMED");
		// Inventory Item Status
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_ISSUED, "ISSUED");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_REDEEMED, "REDEEMED");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_SUSPENDED, "SUSPENDED");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_VOID, "VOID");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_SUSPENSION, "PENDING SUSPENSION");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_VOID, "PENDING VOID");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION, "PENDING REMOVE REDEMPTION");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_REACTIVATION, "PENDING REACTIVATION");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION, "PENDING CHANGE OF REDEMPTION");
		INVENTORY_ITEM_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE, "PENDING CHANGE OF EXPIRY DATE");

		// Inventory Approval Status
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_SUSPENSION, "PENDING SUSPENSION");
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_VOID, "PENDING VOID");
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION, "PENDING REMOVE REDEMPTION");
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_REACTIVATION, "PENDING REACTIVATION");
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION, "PENDING CHANGE OF REDEMPTION");
		INVENTORY_ITEM_APPROVAL_STATUS.put(INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE, "PENDING CHANGE OF EXPIRY DATE");

		// Inventory Request Status
		INVENTORY_REQUEST_STATUS.put(INVENTORY_REQUEST_STATUS_APPROVED, "APPROVED");
		INVENTORY_REQUEST_STATUS.put(INVENTORY_REQUEST_STATUS_ISSUED, "APPROVED & ISSUED");
		INVENTORY_REQUEST_STATUS.put(INVENTORY_REQUEST_STATUS_NEW, "NEW");
		INVENTORY_REQUEST_STATUS.put(INVENTORY_REQUEST_STATUS_PENDING, "PENDING");
		INVENTORY_REQUEST_STATUS.put(INVENTORY_REQUEST_STATUS_REJECTED, "REJECTED");

		//Promotion Event
		PROMOTION_EVENT.put(PROMOTION_EVENT_CREATE, "CREATE");
		PROMOTION_EVENT.put(PROMOTION_EVENT_DELETE, "DELETE");
		PROMOTION_EVENT.put(PROMOTION_EVENT_EDIT, "EDIT");

		// Promotion Request Status
		PROMOTION_REQUEST_STATUS.put(PROMOTION_REQUEST_STATUS_APPROVED, "APPROVED");
		PROMOTION_REQUEST_STATUS.put(PROMOTION_REQUEST_STATUS_PENDING, "PENDING");
		PROMOTION_REQUEST_STATUS.put(PROMOTION_REQUEST_STATUS_REJECTED, "REJECTED");

		// Promotion Status
		PROMOTION_STATUS.put(PROMOTION_STATUS_ACTIVE, "ACTIVE");
		PROMOTION_STATUS.put(PROMOTION_STATUS_DELETED, "DELETED");
		PROMOTION_STATUS.put(PROMOTION_STATUS_PENDING_APPROVAL_CREATE, "PENDING APPROVAL (CREATE)");
		PROMOTION_STATUS.put(PROMOTION_STATUS_PENDING_APPROVAL_EDIT, "PENDING APPROVAL (EDIT)");
		PROMOTION_STATUS.put(PROMOTION_STATUS_PENDING_APPROVAL_DELETE, "PENDING APPROVAL (DELETE)");


		// Application Status
		APPLICATION_STATUS.put(APPLICATION_STATUS_NEW, "NEW");
		APPLICATION_STATUS.put(APPLICATION_STATUS_DRAFT, "DRAFT");
		APPLICATION_STATUS.put(APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL, "PENDING 1ST LEVEL APPROVAL");
		APPLICATION_STATUS.put(APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL, "PENDING 2ND LEVEL APPROVAL");
		APPLICATION_STATUS.put(APPLICATION_STATUS_APPROVED, "APPROVED");
		APPLICATION_STATUS.put(APPLICATION_STATUS_REJECTED, "REJECTED");
		// Account Status
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_PENDING_ACTIVATION, "PENDING ACTIVATION");
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_ACTIVE, "ACTIVE");
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_SUSPENDED, "SUSPENDED");
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_PARENT_SUSPENDED, "PARENT SUSPENDED");
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_CLOSED, "CLOSED");
		ACCOUNT_STATUS.put(ACCOUNT_STATUS_TERMINATED, "TERMINATED");
		// billing change request status
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_NEW, "NEW");
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_PENDING, "PENDING");
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_APPROVED, "APPROVED");
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_REJECTED, "REJECTED");
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_REQ_APPROVED, "REQUEST APPROVED");
		BILLING_REQUEST_STATUS.put(BILLING_REQUEST_STATUS_REQ_REJECTED, "REQUEST REJECTED");
		// credit review request status
		CREDIT_REVIEW_REQUEST_STATUS.put(CREDIT_REVIEW_REQUEST_STATUS_NEW, "NEW");
		CREDIT_REVIEW_REQUEST_STATUS.put(CREDIT_REVIEW_REQUEST_STATUS_PENDING, "PENDING");
		CREDIT_REVIEW_REQUEST_STATUS.put(CREDIT_REVIEW_REQUEST_STATUS_APPROVED, "APPROVED");
		CREDIT_REVIEW_REQUEST_STATUS.put(CREDIT_REVIEW_REQUEST_STATUS_REJECTED, "REJECTED");
		// subscription request status
		SUBSCRIPTION_APPROVE_STATUS.put(SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED, "PENDING");
		SUBSCRIPTION_APPROVE_STATUS.put(SUBSCRIPTION_APPROVE_STATUS_APPROVE, "APPROVED");
		SUBSCRIPTION_APPROVE_STATUS.put(SUBSCRIPTION_APPROVE_STATUS_REJECTED, "REJECTED");

		SUBSCRIPTION_ACTION_STATUS.put(SUBSCRIPTION_ACTION_STATUS_SUBSCRIBE, "SUBSCRIBE");
		SUBSCRIPTION_ACTION_STATUS.put(SUBSCRIPTION_ACTION_STATUS_UNSUBSCRIBE, "UNSUBSCRIBE");
		// Product Status
		PRODUCT_STATUS.put(PRODUCT_STATUS_NEW, "NEW");
		PRODUCT_STATUS.put(PRODUCT_STATUS_ACTIVE, "ACTIVE");
		PRODUCT_STATUS.put(PRODUCT_STATUS_USED, "USED");
		PRODUCT_STATUS.put(PRODUCT_STATUS_SUSPENDED, "SUSPENDED");
		PRODUCT_STATUS.put(PRODUCT_STATUS_PARENT_SUSPENDED, "PARENT SUSPENDED");
		PRODUCT_STATUS.put(PRODUCT_STATUS_TERMINATED, "TERMINATED");
		// Boolean
		BOOLEAN.put(BOOLEAN_YES, "YES");
		BOOLEAN.put(BOOLEAN_NO, "NO");
		BOOLEAN.put(BOOLEAN_NA, "NA");
		//BooleanYN (Yes,No)
		BOOLEAN_YN.put(BOOLEAN_YN_YES, "YES");
		BOOLEAN_YN.put(BOOLEAN_YN_NO, "NO");
		//SMS_FLAG (Yes,No, Same As Account)
		SMS_FLAG.put(SMS_FLAG_SAME_AS_ACCOUNT, "SAME AS ACCOUNT");
		SMS_FLAG.put(SMS_FLAG_YES, "YES");
		SMS_FLAG.put(SMS_FLAG_NO, "NO");
		//Issue type
		ISSUE_TYPE.put(ISSUE_TYPE_COUNT, "COUNT");
		ISSUE_TYPE.put(ISSUE_TYPE_RANGE, "RANGE");
		//DURATION TYPE
		PRODUCT_RENEW_DURATION_TYPE.put(PRODUCT_RENEW_DURATION_TYPE_DURATION,"DURATION");
		PRODUCT_RENEW_DURATION_TYPE.put(PRODUCT_RENEW_DURATION_TYPE_DATE,"DATE");
		//DURATION LENGTH
		PRODUCT_RENEW_DURATION_LENGTH.put(PRODUCT_RENEW_DURATION_LENGTH_TYPE1,"1 MONTH");
		PRODUCT_RENEW_DURATION_LENGTH.put(PRODUCT_RENEW_DURATION_LENGTH_TYPE2,"2 MONTHS");
		PRODUCT_RENEW_DURATION_LENGTH.put(PRODUCT_RENEW_DURATION_LENGTH_TYPE3,"3 MONTHS");
		PRODUCT_RENEW_DURATION_LENGTH.put(PRODUCT_RENEW_DURATION_LENGTH_TYPE4,"5 MONTHS");
		PRODUCT_RENEW_DURATION_LENGTH.put(PRODUCT_RENEW_DURATION_LENGTH_TYPE5,"12 MONTHS");
		//UPDATE CREDIT LIMIT
		REVIEW_TYPES.put(REVIEW_TYPE_PERMANENT,"PERMANENT");
		REVIEW_TYPES.put(REVIEW_TYPE_TEMPORARY,"TEMPORARY");
		// INVOICE STATUS
		INVOICE_STATUS.put(INVOICE_STATUS_OUTSTANDING, "OUTSTANDING");
		INVOICE_STATUS.put(INVOICE_STATUS_CLOSED, "CLOSED");
		INVOICE_STATUS.put(INVOICE_STATUS_CANCELLED, "CANCELLED");
		// INVOICE FORMAT
		INVOICE_FORMAT.put(INVOICE_FORMAT_ACCOUNT, "ACCOUNT FORMAT");
		INVOICE_FORMAT.put(INVOICE_FORMAT_SUBACCOUNT, "SUB ACCOUNT FORMAT");
		// INVOICE SORTING
		INVOICE_SORTING.put(INVOICE_SORTING_CARD, "CARD NO");
		INVOICE_SORTING.put(INVOICE_SORTING_TXN_DATE, "TRANSACTION DATE");
		// INVOICE PRINTING
		INVOICE_PRINTING.put(INVOICE_PRINTING_DOUBLE_SIDED, "DOUBLE SIDED");
		INVOICE_PRINTING.put(INVOICE_PRINTING_SINGLE_SIDED, "SINGLE SIDED");
		//MANAGE PRODUCT SORT BY
		SORT_BY.put(SORT_BY_ISSUE_DATE, "ISSUE DATE");
		SORT_BY.put(SORT_BY_EXPIRY_DATE, "EXPIRY DATE");
		SORT_BY.put(SORT_BY_SUSPENSION_START_DATE, "SUSPENSION DATE");
		// embossing sort by
		PRODUCT_EMBOSS_SORT_BY.put(PRODUCT_EMBOSS_SORT_BY_ACCT_NO, "ACCOUNT");
		PRODUCT_EMBOSS_SORT_BY.put(PRODUCT_EMBOSS_SORT_BY_CARD_NO, "CARD NO");
		// Contact Person sort by
		CONTACT_PERSON_ORDER.put(CONTACT_PERSON_ORDER_BY_ACCOUNT_NAME, "ACCOUNT NAME");
		CONTACT_PERSON_ORDER.put(CONTACT_PERSON_ORDER_BY_CONTACT_PERSON, "CONTACT PERSON");
		// Corporate Customer Usage Breakdown sort by
		CORP_CUST_USAGE_BREAKDOWN_ORDER.put(CORP_CUST_USAGE_BREAKDOWN_ORDER_BY_INVOICE_AMOUNT, "INVOICE AMOUNT");
		// INVOICE_CHARGE_TO
		INVOICE_CHARGE_TO.put(INVOICE_CHARGE_TO_PARENT, "PARENT");
		INVOICE_CHARGE_TO.put(INVOICE_CHARGE_TO_SELF, "SELF");
		//TODO: TO CHANGE INTO TABLE MAPPING FOR EASY CONFIG
		//INVOICE SUMMARY TYPE MAPS TO SUMMARY GROUP NAME
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_CARD_REPLACEMENT, "CARD REPLACEMENT FEE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_CREDIT_DEBIT_NOTE, "CREDIT/DEBIT NOTE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_EARLY_PAYMENT, "EARLY PAYMENT DISCOUNT");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_LATE_PAYMENT, "LATE PAYMENT INTEREST");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_VOLUME_DISCOUNT, "VOLUME DISCOUNT");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT, "DEPOSIT");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_SUBSCRIPTION_FEE, "SUBSCRIPTION FEE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_ISSUANCE_FEE, "CARD ISSUANCE FEE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_MISC, "MISCELLANEOUS");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREMIER_SERVICE, "PREMIER SERVICE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_NORMAL, "NORMAL");

		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_VALUE, "ISSUE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_CASHPLUS, "CASHPLUS");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_VALUE, "TOPUP");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_CASHPLUS, "CASHPLUS");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_DISCOUNT, "DISCOUNT");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE, "ISSUANCE FEE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE_WAIVAL, "ISSUANCE FEE WAIVAL");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE, "TOPUP FEE");
		INVOICE_SUMMARY_TYPE_GROUP_NAME.put(INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE_WAIVAL, "TOPUP FEE WAIVAL");

		//Account Category
		ACCOUNT_CATEGORY.put(ACCOUNT_CATEGORY_CORPORATE, "CORPORATE");
		ACCOUNT_CATEGORY.put(ACCOUNT_CATEGORY_DIVISION, "DIVISION");
		ACCOUNT_CATEGORY.put(ACCOUNT_CATEGORY_DEPARTMENT, "DEPARTMENT");
		ACCOUNT_CATEGORY.put(ACCOUNT_CATEGORY_APPLICANT, "APPLICANT");
		ACCOUNT_CATEGORY.put(ACCOUNT_CATEGORY_SUB_APPLICANT, "SUB-APPLICANT");

		//Account Category
		AS_ACCOUNT_CATEGORY.put(AS_ACCOUNT_CATEGORY_CORPORATE, "CORPORATE");
		AS_ACCOUNT_CATEGORY.put(AS_ACCOUNT_CATEGORY_DIVISION, "DIVISION");
		AS_ACCOUNT_CATEGORY.put(AS_ACCOUNT_CATEGORY_DEPARTMENT, "DEPARTMENT");
		AS_ACCOUNT_CATEGORY.put(AS_ACCOUNT_CATEGORY_APPLICANT, "APPLICANT");
		AS_ACCOUNT_CATEGORY.put(AS_ACCOUNT_CATEGORY_SUB_APPLICANT, "SUB-APPLICANT");

		// TXN TYPE
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_ADMIN_FEE, "ADMIN FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_CHARGEBACK, "BANK ADVICE CHARGEBACK");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR, "BANK ADVICE MDR");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR_ADJUSTMENT, "BANK ADVICE MDR ADJUSTMENT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_ISSUANCE_FEE, "CARD ISSUANCE FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_REPLACEMENT_FEE, "CARD REPLACEMENT FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_CONTRA_AND_CLEARING, "CONTRA & CLEARING");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_DEPOSIT, "DEPOSIT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_EARLY_PAYMENT, "EARLY PAYMENT DISCOUNT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_FARE, "FARE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_LATE_PAYMENT, "LATE PAYMENT INTEREST");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_MISC, "MISCELLANEOUS");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT, "NON BILLABLE ADMIN AMOUNT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_NON_BILLABLE_CHARGEBACK, "NON BILLABLE CHARGEBACK");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT, "NON BILLABLE FARE AMOUNT");

		TRANSACTION_TYPE.put(TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT, "NON BILLABLE PREMIUM AMOUNT");
		
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PROMOTION_DISCOUNT, "PROMOTION DISCOUNT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_SUBSCRIPTION_FEE, "SUBSCRIPTION FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_VOLUME_DISCOUNT, "VOLUME DISCOUNT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR_MARKUP, "BANK ADVICE MDR MARKUP");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR_COMMISSION, "BANK ADVICE MDR COMMISSION");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR_INTERCHANGE, "BANK ADVICE MDR INTERCHANGE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_BANK_ADVICE_MDR_SCHEME_FEE, "BANK ADVICE MDR SCHEME FEE");

		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_ISSUANCE_FEE, "PREPAID ISSUANCE FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_TOPUP_FEE, "PREPAID TOP UP FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_TRANSFER_FEE, "PREPAID TRANSFER FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_REPLACEMENT_FEE, "PREPAID REPLACEMENT FEE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_DISCOUNT, "PREPAID DISCOUNT");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_DEFERRED_INCOME, "PREPAID DEFERRED INCOME");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_PREPAYMENT_CASHPLUS, "PREPAID REPAYMENT CASHPLUS");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_PROMOTION_EXPENSE, "PREPAID PROMOTION EXPENSE");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_PAYMENT_TO_DRIVER, "PREPAID PAYMENT TO DRIVER");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_PAYABLE_TO_DRIVER, "PREPAID PAYABLE TO DRIVER");
		TRANSACTION_TYPE.put(TRANSACTION_TYPE_PREPAID_ADMIN_FEE_FORFEIT, "PREPAID ADMIN FEE FORFEIT");

		TXN_STATUS.put(TRANSACTION_STATUS_BILLED, "BILLED");
		TXN_STATUS.put(TRANSACTION_STATUS_ACTIVE, "ACTIVE");
		TXN_STATUS.put(TRANSACTION_STATUS_REFUND, "REFUND");
		TXN_STATUS.put(TRANSACTION_STATUS_VOID, "VOID");
		TXN_STATUS.put(TRANSACTION_STATUS_PENDING_EDIT_ACTIVE, "PENDING APPR (ACTIVE)");
		TXN_STATUS.put(TRANSACTION_STATUS_PENDING_EDIT_BILLED, "PENDING APPR (BILLED)");

		AYDEN_MATCHING_STATUS.put(AYDEN_MATCHING_STATUS_MATCHED,"MATCHED");
		AYDEN_MATCHING_STATUS.put(AYDEN_MATCHING_STATUS_ERROR, "ERROR (TXN_AMOUNT)");
		AYDEN_MATCHING_STATUS.put(AYDEN_MATCHING_STATUS_PENDING, "PENDING");

		AYDEN_COMPLETE_STATUS.put(AYDEN_STATUS_COMPLETED,"COMPLETED");
		AYDEN_COMPLETE_STATUS.put(AYDEN_STATUS_INCOMPLETE,"INCOMPLETE");

		HLA_POLICY_STATUS.put(HLA_POLICY_STATUS_ACTIVE,"ACTIVE");
		HLA_POLICY_STATUS.put(HLA_POLICY_STATUS_CANCELLED, "CANCELLED");
		HLA_POLICY_STATUS.put(HLA_POLICY_STATUS_CANCELLED_MANUAL, "CANCELLED (MANUAL)");
		
		// billing change request status
		TRANSACTION_REQUEST_STATUS.put(TRANSACTION_REQUEST_STATUS_NEW, "NEW");
		TRANSACTION_REQUEST_STATUS.put(TRANSACTION_REQUEST_STATUS_PENDING_VOID, "PENDING APPR");
		TRANSACTION_REQUEST_STATUS.put(TRANSACTION_REQUEST_STATUS_PENDING_REFUND, "PENDING APPR");
		TRANSACTION_REQUEST_STATUS.put(TRANSACTION_REQUEST_STATUS_APPROVED, "APPROVED");
		TRANSACTION_REQUEST_STATUS.put(TRANSACTION_REQUEST_STATUS_REJECTED, "REJECTED");

		// Volume Discount Type
		VOLUME_DISCOUNT_TYPE.put(VOLUME_DISCOUNT_TYPE_DOLLAR, "DOLLAR");
		VOLUME_DISCOUNT_TYPE.put(VOLUME_DISCOUNT_TYPE_TRIP, "TRIP");

		//Payment Status
		RECEIPT_STATUS.put(RECEIPT_STATUS_CANCELLED, "CANCELLED");
		RECEIPT_STATUS.put(RECEIPT_STATUS_CLOSED, "CLOSED");
		RECEIPT_STATUS.put(RECEIPT_STATUS_EXCESS, "EXCESS");
		RECEIPT_STATUS.put(RECEIPT_STATUS_PENDING, "PENDING");
		//Bill Gen Setup
		BILL_GEN_SETUP.put(BILL_GEN_SETUP_AD_HOC, "AD HOC");
		BILL_GEN_SETUP.put(BILL_GEN_SETUP_BIWEEKLY_1, "BI-WEEKLY 1");
		BILL_GEN_SETUP.put(BILL_GEN_SETUP_BIWEEKLY_2, "BI-WEEKLY 2");
		BILL_GEN_SETUP.put(BILL_GEN_SETUP_DRAFT, "DRAFT");
		BILL_GEN_SETUP.put(BILL_GEN_SETUP_MONTHLY, "MONTHLY");
		//CALENDAR MONTH
		CALENDAR_MONTH.put(JANUARY, "JANUARY");
		CALENDAR_MONTH.put(FEBRUARY, "FEBRUARY");
		CALENDAR_MONTH.put(MARCH, "MARCH");
		CALENDAR_MONTH.put(APRIL, "APRIL");
		CALENDAR_MONTH.put(MAY, "MAY");
		CALENDAR_MONTH.put(JUNE, "JUNE");
		CALENDAR_MONTH.put(JULY, "JULY");
		CALENDAR_MONTH.put(AUGUST, "AUGUST");
		CALENDAR_MONTH.put(SEPTEMBER, "SEPTEMBER");
		CALENDAR_MONTH.put(OCTOBER, "OCTOBER");
		CALENDAR_MONTH.put(NOVEMBER, "NOVEMBER");
		CALENDAR_MONTH.put(DECEMBER, "DECEMBER");
		//Bill Gen Request Status
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_COMPLETED, "COMPLETED");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_DELETED, "DELETED");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_ERROR, "ERROR");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_IN_PROGRESS, "IN PROGRESS");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_PENDING, "PENDING");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_PENDING_PROGRESS, "PENDING PROGRESS");
		BILL_GEN_REQUEST_STATUS.put(BILL_GEN_REQUEST_STATUS_REGENERATED, "REGENERATED");

		//Note Type
		NOTE_TYPE.put(NOTE_TYPE_CREDIT, "CREDIT");
		NOTE_TYPE.put(NOTE_TYPE_DEBIT, "DEBIT");

		// Note Status
		NOTE_STATUS.put(NOTE_STATUS_ACTIVE, "ACTIVE");
		NOTE_STATUS.put(NOTE_STATUS_BILLED, "BILLED");
		NOTE_STATUS.put(NOTE_STATUS_CANCELLED, "CANCELLED");
		NOTE_STATUS.put(NOTE_STATUS_PENDING, "PENDING");
		NOTE_STATUS.put(NOTE_STATUS_PENDING_CANCELLATION, "PENDING CANCELLATION");
		NOTE_STATUS.put(NOTE_STATUS_REJECTED, "REJECTED");

		//User Status
		USER_STATUS.put(USER_STATUS_ACTIVE, "ACTIVE");
		USER_STATUS.put(USER_STATUS_INACTIVE, "INACTIVE");

		//Promo
		PROMO_ACCT_TYPE.put(PROMO_ACCT_TYPE_GLOBAL, "GLOBAL");
		PROMO_ACCT_TYPE.put(PROMO_ACCT_TYPE_ACCOUNT, "ACCOUNT");
		PROMO_TYPE.put(PROMO_TYPE_DOLLAR, "DOLLAR");
		PROMO_TYPE.put(PROMO_TYPE_PERCENTAGE, "PERCENTAGE");

		//Non Billable Batch
		NON_BILLABLE_BATCH_STATUS.put(NON_BILLABLE_BATCH_STATUS_OPEN, "OPEN");
		NON_BILLABLE_BATCH_STATUS.put(NON_BILLABLE_BATCH_STATUS_CLOSED, "CLOSED");

		//Non Billable Txn
		NON_BILLABLE_TXN_STATUS.put(NON_BILLABLE_TXN_OPEN, "OPEN");
		NON_BILLABLE_TXN_STATUS.put(NON_BILLABLE_TXN_CLOSED, "CLOSED");
		NON_BILLABLE_TXN_STATUS.put(NON_BILLABLE_TXN_REJECTED, "REJECTED");
		NON_BILLABLE_TXN_STATUS.put(NON_BILLABLE_TXN_CHARGEBACK, "CHARGEBACK");
		NON_BILLABLE_TXN_STATUS.put(NON_BILLABLE_TXN_REFUNDED, "REFUNDED");

		//VALIDITY_PERIOD FOR PRODUCT TYPE
		VALIDITY_PERIOD_FLAGS.put(VALIDITY_PERIOD_FLAG_BY_MMYY, "YES (MM/YY)");
		VALIDITY_PERIOD_FLAGS.put(VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM, "YES (DD/MM/YYYY 24HH:MI)");
		VALIDITY_PERIOD_FLAGS.put(VALIDITY_PERIOD_FLAG_NO, "NO");

		//Rewards request type
		REWARDS_ADJ_REQUEST_TYPES.put(REWARDS_ADJ_REQUEST_TYPE_AWARD, "AWARD");
		REWARDS_ADJ_REQUEST_TYPES.put(REWARDS_ADJ_REQUEST_TYPE_DEDUCT, "DEDUCT");

		REWARDS_REDEEM_FROM.put(REWARDS_REDEEM_FROM_PREV, "PREVIOUS, THEN CURRENT");
		REWARDS_REDEEM_FROM.put(REWARDS_REDEEM_FROM_CURR, "CURRENT, THEN PREVIOUS");

		REWARDS_ADJ_REQUEST_STATUS.put(REWARDS_ADJ_REQUEST_STATUS_NEW, "NEW");
		REWARDS_ADJ_REQUEST_STATUS.put(REWARDS_ADJ_REQUEST_STATUS_PENDING, "PENDING");
		REWARDS_ADJ_REQUEST_STATUS.put(REWARDS_ADJ_REQUEST_STATUS_APPROVED, "APPROVED");
		REWARDS_ADJ_REQUEST_STATUS.put(REWARDS_ADJ_REQUEST_STATUS_REJECTED, "REJECTED");

		// Govt eInv Flag
		GOVT_EINV_FLAGS.put(GOVT_EINV_FLAG_NO, "NO");
		GOVT_EINV_FLAGS.put(GOVT_EINV_FLAG_INV, "INVOICE");
		GOVT_EINV_FLAGS.put(GOVT_EINV_FLAG_TRIP, "TRIP");

		//Govt eInv Request Status
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_PENDING, "PENDING");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_PENDING_PROGRESS, "PENDING PROGRESS");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_IN_PROGRESS, "IN PROGRESS");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_ERROR, "ERROR");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_COMPLETED, "COMPLETED");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_UPLOADED, "UPLOADED RETURN FILE");
		GOVT_EINV_REQUEST_STATUS.put(GOVT_EINV_REQUEST_STATUS_DELETED, "DELETED");

		//Govt eInv Return Status
		GOVT_EINV_RETURN_STATUS.put(GOVT_EINV_RETURN_STATUS_SUCCESS, "SUCCESS");
		GOVT_EINV_RETURN_STATUS.put(GOVT_EINV_RETURN_STATUS_REJECT, "REJECT");


		//PUBBS Request Status
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_PENDING, "PENDING");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_PENDING_PROGRESS, "PENDING PROGRESS");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_IN_PROGRESS, "IN PROGRESS");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_ERROR, "ERROR");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_COMPLETED, "COMPLETED");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_UPLOADED, "UPLOADED RETURN FILE");
		PUBBS_REQUEST_STATUS.put(PUBBS_REQUEST_STATUS_DELETED, "DELETED");

		PUBBS_RETURN_STATUS.put(PUBBS_RETURN_STATUS_NO_ERROR, "NO ERROR");
		PUBBS_RETURN_STATUS.put(PUBBS_RETURN_STATUS_ERROR, "ERROR");


		//RECURRING Request Status
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_PENDING, "PENDING");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_PENDING_PROGRESS, "PENDING PROGRESS");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_IN_PROGRESS, "IN PROGRESS");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_ERROR_NOFILE, "ERROR - NO RECORDS");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_ERROR, "ERROR");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_COMPLETED, "COMPLETE-UPLOADED");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_UPLOADED, "COMPLETE-WITH RETURN FILE");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_DELETED, "DELETED");
		RECURRING_REQUEST_STATUS.put(RECURRING_REQUEST_STATUS_COMPLETED_NO_RECORD, "COMPLETE - NO RECORDS");
		RECURRING_REQUEST_STATUS.put(RECURRING_RETURN_STATUS_NO_ERROR, "NO ERROR");
		RECURRING_REQUEST_STATUS.put(RECURRING_RETURN_STATUS_ERROR, "ERROR");


		// PUBBS Flag
		PUBBS_FLAGS.put(PUBBS_FLAG_NO, "NO");
		PUBBS_FLAGS.put(PUBBS_FLAG_YES, "YES");

		// Commission Type
		COMMISSION_TYPE.put(COMMISSION_TYPE_BUSINESS_PARTNER_COMMISSION, "PERCENTAGE");
		COMMISSION_TYPE.put(COMMISSION_TYPE_FIXED_VALUE_BUSINESS_PARTNER_COMMISSION, "FIXED VALUE");

		/* ****************************************************************** */
		/* ************ LEAVE THE BOTTOM PORTION FOR REPORT ***************** */
		/* ****************************************************************** */
		CDRD_STATUS.put(CDRD_STATUS_OUSTANDING, "OUTSTANDING");
		CDRD_STATUS.put(CDRD_STATUS_RECEIVED, "RECEIVED");
		CDRD_STATUS.put(CDRD_STATUS_OFFSET, "OFFSET");
		CDRD_STATUS.put(CDRD_STATUS_REFUNDED, "REFUND");
		// Customer Deposit Report Detailed
		CDRD_ORDER.put(CDRD_ORDER_BY_STATUS, "STATUS");
		CDRD_ORDER.put(CDRD_ORDER_BY_INVOICE_DATE, "INVOICE_DATE");
		// SalesReportBySalesPerson
		SR_ORDER.put(SR_ORDER_BY_SALESPERSON_NAME, "SALESPERSON NAME");
		SR_ORDER.put(SR_ORDER_BY_TOTAL, "TOTAL");
		// CustomerUsageComparsion Report
		CUC_ORDER.put(CUC_ORDER_BY_CUSTOMER_NAME, "ACCOUNT NAME");
		CUC_ORDER.put(CUC_ORDER_BY_TOTAL, "CURRENT MONTH TOTAL");
		//ReceiptByPeriodDetailed Report
		RBPD_ORDER.put(RBPD_ORDER_BY_RECEIPT_DATE, "RECEIPT DATE");
		RBPD_ORDER.put(RBPD_ORDER_BY_CUSTOMER_NAME, "ACCOUNT NAME");
		RBPD_ORDER.put(RBPD_ORDER_BY_PAYMENT_MODE, "PAYMENT MODE");
		//CreditDebitNote report
		CDN_ORDER.put(CDN_ORDER_BY_NOTE_DATE, "NOTE DATE");
		CDN_ORDER.put(CDN_ORDER_BY_CUSTOMER_NAME, "ACCOUNT NAME");
		CDN_ORDER.put(CDN_ORDER_BY_INVOICE_NO, "INVOICE NO");
		CDN_ORDER.put(CDN_ORDER_BY_NOTE_TYPE, "NOTE TYPE");
		//Daily Cheque Deposit Listing report
		DCDL_ORDER.put(DCDL_ORDER_BY_BANK, "BANK");
		DCDL_ORDER.put(DCDL_ORDER_BY_BRANCH, "BRANCH");
		DCDL_ORDER.put(DCDL_ORDER_BY_CHEQUE_NO, "CHEQUE NO");
		//Credit Balance report
		CREDIT_BALANCE_ORDER.put(CREDIT_BALANCE_ORDER_BY_ACCOUNT_NAME, "ACCOUNT NAME");
		CREDIT_BALANCE_ORDER.put(CREDIT_BALANCE_ORDER_BY_CREDIT_BALANCE, "CREDIT BALANCE");
		//Card In Production Report
		CARD_IN_PRODUCTION_ORDER.put(CARD_IN_PRODUCTION_BY_ACCOUNT_NAME, "ACCOUNT NAME");
		CARD_IN_PRODUCTION_ORDER.put(CARD_IN_PRODUCTION_BY_CARD_NO, "CARD NO");
		CARD_IN_PRODUCTION_ORDER.put(CARD_IN_PRODUCTION_BY_CARD_STATUS, "CARD STATUS");
		CARD_IN_PRODUCTION_ORDER.put(CARD_IN_PRODUCTION_BY_CARD_REASON, "CARD REASON");
		// Contact Person Report
		CONTACT_PERSON_ORDER.put(CONTACT_PERSON_ORDER_BY_ACCOUNT_NAME, "ACCOUNT NAME");
		CONTACT_PERSON_ORDER.put(CONTACT_PERSON_ORDER_BY_CONTACT_PERSON, "CONTACT PERSON");
		// Corporate Customer Usage Breakdown sort by
		CORP_CUST_USAGE_BREAKDOWN_ORDER.put(CORP_CUST_USAGE_BREAKDOWN_ORDER_BY_INVOICE_AMOUNT, "INVOICE AMOUNT");
		//Card In Production Report
		BANK_CHARGEBACK_REPORT_ORDER.put(BANK_CHARGEBACK_REPORT_BY_CARD_NUMBER, "CARD NUMBER");
		BANK_CHARGEBACK_REPORT_ORDER.put(BANK_CHARGEBACK_REPORT_BY_TRIP_DATE, "TRIP DATE");
		// Cashless Aging report detailed
		CASHLESS_AGING_REPORT_DETAILED.put(CASHLESS_AGING_REPORT_DETAILED_BY_BATCH_DATE, "BATCH DATE");
		CASHLESS_AGING_REPORT_DETAILED.put(CASHLESS_AGING_REPORT_DETAILED_BY_BATCH_NO, "BATCH NO");
		// Cashless Bank Collection Detailed
		CASHLESS_BANK_COLLECTION_DETAILED.put(CASHLESS_BANK_COLLECTION_DETAILED_BY_BATCH_DATE, "BATCH DATE");
		CASHLESS_BANK_COLLECTION_DETAILED.put(CASHLESS_BANK_COLLECTION_DETAILED_BY_DRIVER_IC, "DRIVER IC");
		// Cashless Txn By Amt Range
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_ALL, "ALL");
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_IN_HOUSE, "IN HOUSE PRODUCTS");
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_CC, "CREDIT CARDS");
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_NETS, "NETS/FLASHPAY/CUP CARDS");
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_EZLINK, "EZLINK/EPINS");
		CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE.put(CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE_DASH, "DASH");
		// invoice report
		INVOICE_REPORT_TYPES.put(INVOICE_REPORT_TYPE_ALL, "ALL");
		INVOICE_REPORT_TYPES.put(INVOICE_REPORT_TYPE_DEPOSIT, "DEPOSIT");
		INVOICE_REPORT_TYPES.put(INVOICE_REPORT_TYPE_MISC, "MISC");
		INVOICE_REPORT_TYPES.put(INVOICE_REPORT_TYPE_TRIPS, "TRIPS");
		INVOICE_REPORT_ORDER.put(INVOICE_REPORT_ORDER_CUST_NO, "ACCOUNT NO");
		INVOICE_REPORT_ORDER.put(INVOICE_REPORT_ORDER_CUST_NAME, "ACCOUNT NAME");
		INVOICE_REPORT_ORDER.put(INVOICE_REPORT_ORDER_INVOICE_NO, "INVOICE NO");
		// financial memo report
		FINANCIAL_MEMO_REPORT_ORDER.put(FINANCIAL_MEMO_REPORT_ORDER_MEMO_NO, "MEMO NO");
		FINANCIAL_MEMO_REPORT_ORDER.put(FINANCIAL_MEMO_REPORT_ORDER_MEMO_DATE, "MEMO DATE");
		FINANCIAL_MEMO_REPORT_TYPE.put(FINANCIAL_MEMO_REPORT_TYPE_DEPOSIT, "DEPOSIT");
		FINANCIAL_MEMO_REPORT_TYPE.put(FINANCIAL_MEMO_REPORT_TYPE_EXCESS, "EXCESS");
		// stock take for inventory items report
		STOCK_TAKE_ORDER.put(STOCK_TAKE_ORDER_ITEM_TYPE, "ITEM TYPE / VOUCHER NO");
		STOCK_TAKE_ORDER.put(STOCK_TAKE_ORDER_VOUCHER_NO, "VOUCHER NO");
		// stock take for inventory items report
		MOVEMENT_REPORT_TYPE.put(MOVEMENT_REPORT_TYPE_SUMMARY, "SUMMARY");
		MOVEMENT_REPORT_TYPE.put(MOVEMENT_REPORT_TYPE_DETAILED, "DETAILED");

		//Timely Payment Statistics Detailed Report
		TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE.put(TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE_PAID_BY_DUE_DATE, "ACCOUNTS PAID BY DUE DATE");
		TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE.put(TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE_OVERDUE, "ACCOUNTS OVERDUE");

		// Debts Reminder Letter
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_FIRST, "FIRST");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_SECOND, "SECOND");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_THIRD, "THIRD");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_FOURTH, "FOURTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_FIFTH, "FIFTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_SIXTH, "SIXTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_SEVENTH, "SEVENTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_EIGHTH, "EIGHTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_NINTH, "NINTH");
		DEBTS_REMINDER_LETTER_SEQUENCE.put(DEBTS_REMINDER_LETTER_TENTH, "TENTH");
		// Debts Reminder Letter
		DEBTS_REMINDER_LETTER_AGING.put(DEBTS_REMINDER_LETTER_AGING_GT_0, "> 0 Days");
		DEBTS_REMINDER_LETTER_AGING.put(DEBTS_REMINDER_LETTER_AGING_GT_30, "> 30 Days");
		DEBTS_REMINDER_LETTER_AGING.put(DEBTS_REMINDER_LETTER_AGING_GT_60, "> 60 Days");
		DEBTS_REMINDER_LETTER_AGING.put(DEBTS_REMINDER_LETTER_AGING_GT_90, "> 90 Days");
		// Loyalty Program Report
		LOYALTY_PROGRAM_REPORT_ORDER.put(LOYALTY_PROGRAM_REPORT_ORDER_ACCOUNT_NAME, "ACCOUNT NAME");
		LOYALTY_PROGRAM_REPORT_ORDER.put(LOYALTY_PROGRAM_REPORT_ORDER_CLOSING_BALANCE, "CLOSING BALANCE");
		// Credit card promo detail
		CREDIT_CARD_PROMO_DETAIL_ORDER.put(CREDIT_CARD_PROMO_DETAIL_ORDER_CARD_NO, "CREDIT CARD NO");
		CREDIT_CARD_PROMO_DETAIL_ORDER.put(CREDIT_CARD_PROMO_DETAIL_ORDER_TRIP_DATE, "TRIP DATE");
		// Trip adjustment report
		TRIP_ADJUSTMENT_REPORT_ORDER.put(TRIP_ADJUSTMENT_REPORT_ORDER_PAYMENT_TYPE, "PAYMENT TYPE");
		TRIP_ADJUSTMENT_REPORT_ORDER.put(TRIP_ADJUSTMENT_REPORT_ORDER_CREATION_DATE, "CREATION DATE");
		// error transaction report
		ERROR_TRANSACTION_REPORT_ORDER.put(ERROR_TRANSACTION_REPORT_ORDER_BY_UPLOAD_DATE, "UPLOAD DATE");
		ERROR_TRANSACTION_REPORT_ORDER.put(ERROR_TRANSACTION_REPORT_ORDER_BY_TXN_DATE, "TXN DATE");
		ERROR_TRANSACTION_REPORT_ORDER.put(ERROR_TRANSACTION_REPORT_ORDER_BY_JOB_NO, "JOB NO");
		ERROR_TRANSACTION_REPORT_ORDER.put(ERROR_TRANSACTION_REPORT_ORDER_BY_CARD_NO, "CARD NO");
		//FMS action
		Map<String, String> FMS_ACTION = new LinkedHashMap<String, String>();
		FMS_ACTION.put(FMS_COLLECT, "COLLECT");
		FMS_ACTION.put(FMS_REFUND, "REFUND");
		//Report Map
		REPORT_MAP.put("CDRD_STATUS", CDRD_STATUS);
		REPORT_MAP.put("CDRD_ORDER", CDRD_ORDER);
		REPORT_MAP.put("SR_ORDER", SR_ORDER);
		REPORT_MAP.put("CUC_ORDER", CUC_ORDER);
		REPORT_MAP.put("REPORT_PURPOSE", REPORT_PURPOSE); //For Contact Person Report
		REPORT_MAP.put("CONTACT_TYPE", MAIN_CONTACT); //For Contact Person Report
		REPORT_MAP.put("MASTER_STATUS", MASTER_STATUS); //For Sample Report
		REPORT_MAP.put("ACCOUNT_STATUS",ACCOUNT_STATUS); //For Customer Report
		REPORT_MAP.put("RBPD_ORDER",RBPD_ORDER); //ReceiptByPeriodDetailed Report
		REPORT_MAP.put("CDN_ORDER",CDN_ORDER); //Credit Debit Note report
		REPORT_MAP.put("NOTE_TYPE",NOTE_TYPE); //Credit Debit Note report
		REPORT_MAP.put("DCDL_ORDER",DCDL_ORDER); //Daily Cheque Deposit Listing Report
		REPORT_MAP.put("BOOLEAN", BOOLEAN_YN);
		REPORT_MAP.put("CREDIT_BALANCE_ORDER", CREDIT_BALANCE_ORDER);
		REPORT_MAP.put("CIPR_ORDER", CARD_IN_PRODUCTION_ORDER);
		REPORT_MAP.put("CONTACT_PERSON_ORDER", CONTACT_PERSON_ORDER);
		REPORT_MAP.put("CORP_CUST_USAGE_BREAKDOWN_ORDER", CORP_CUST_USAGE_BREAKDOWN_ORDER);
		REPORT_MAP.put("BCBR_ORDER", BANK_CHARGEBACK_REPORT_ORDER);
		REPORT_MAP.put("CARD_ORDER", CASHLESS_AGING_REPORT_DETAILED);
		REPORT_MAP.put("CBCD_ORDER", CASHLESS_BANK_COLLECTION_DETAILED);
		REPORT_MAP.put("NON_BILLABLE_TXN_STATUS", NON_BILLABLE_TXN_STATUS);
		REPORT_MAP.put("PT", CASHLESS_TXN_BY_AMT_RANGE_PAYMENT_TYPE);
		REPORT_MAP.put("InvType", INVOICE_REPORT_TYPES);
		REPORT_MAP.put("IR_ORDER", INVOICE_REPORT_ORDER);
		REPORT_MAP.put("TXN_STATUS", TXN_STATUS);
		REPORT_MAP.put("PSTR_ORDER", PSTR_ORDER);
		REPORT_MAP.put("FMR_ORDER", FINANCIAL_MEMO_REPORT_ORDER);
		REPORT_MAP.put("FMR_TYPE", FINANCIAL_MEMO_REPORT_TYPE);
		REPORT_MAP.put("STII_ORDER", STOCK_TAKE_ORDER);
		REPORT_MAP.put("MR_TYPE", MOVEMENT_REPORT_TYPE);
		REPORT_MAP.put("TPSDR_TYPE", TIMELY_PAYMENT_STAT_DETAIL_REPORT_TYPE);
		REPORT_MAP.put("DRL_SEQUENCE", DEBTS_REMINDER_LETTER_SEQUENCE);
		REPORT_MAP.put("DRL_AGING", DEBTS_REMINDER_LETTER_AGING);
		REPORT_MAP.put("LPR_ORDER", LOYALTY_PROGRAM_REPORT_ORDER);
		REPORT_MAP.put("CCPD_ORDER", CREDIT_CARD_PROMO_DETAIL_ORDER);
		REPORT_MAP.put("TRS", TRANSACTION_REQUEST_STATUS);
		REPORT_MAP.put("FMS_ACTION", FMS_ACTION);
		REPORT_MAP.put("TAR_ORDER", TRIP_ADJUSTMENT_REPORT_ORDER);
		REPORT_MAP.put("ETR_ORDER", ERROR_TRANSACTION_REPORT_ORDER);
		//Card Holder report
		CARD_HOLDER_ORDER.put(CARD_HOLDER_ORDER_BY_ACCOUNT, "ACCOUNT");
		CARD_HOLDER_ORDER.put(CARD_HOLDER_ORDER_BY_CARD_NO, "CARD NO");

		CUSTOMER_ORDER.put(CUSTOMER_ORDER_BY_ACCOUNT_NAME, "ACCOUNT NAME");
		CUSTOMER_ORDER.put(CUSTOMER_ORDER_BY_BUSINESS_NATURE, "BUSINESS NATURE");

		ACCOUNT_TYPE.put(ACCOUNT_TYPE_CORPORATE,"CORPORATE");
		ACCOUNT_TYPE.put(ACCOUNT_TYPE_PERSONAL,"PERSONAL");

		REPORT_MAP.put("CUSTOMER_ORDER", CUSTOMER_ORDER);
		REPORT_MAP.put("CHL_ORDER", CARD_HOLDER_ORDER);
		REPORT_MAP.put("PRODUCT_STATUS", PRODUCT_STATUS);
		REPORT_MAP.put("SALES_PERSONS", SALES_PERSONS);
		REPORT_MAP.put("ACCOUNT_TYPE", ACCOUNT_TYPE);
		REPORT_MAP.put("ACCOUNT_STATUS", ACCOUNT_STATUS);
		REPORT_MAP.put("NON_BILLABLE_BATCH_STATUS", NON_BILLABLE_BATCH_STATUS);
		REPORT_MAP.put("PREPAID_APPROVAL_STATUS", PREPAID_APPROVAL_STATUS);
		REPORT_MAP.put("PREPAID_APPROVAL_REPORT_ORDER", PREPAID_APPROVAL_REPORT_ORDER);
		REPORT_MAP.put("PREPAID_TOP_UP_REPORT_ORDER", PREPAID_TOP_UP_REPORT_ORDER);
		REPORT_MAP.put("PREPAID_USAGE_REPORT_ORDER", PREPAID_USAGE_REPORT_ORDER);
		REPORT_MAP.put("PREPAID_REQUEST_TYPE", PREPAID_REQUEST_TYPE);
		REPORT_MAP.put("PREPAID_TOP_UP_TYPE", PREPAID_TOP_UP_TYPE);

		//UAM Report added by vani 05.01.2011
		REPORT_MAP.put("USER_STATUS", USER_STATUS);

		// Premier Service Trip Reconciliation
		PSTR_ORDER.put(PSTR_ORDER_BY_ACCOUNT_NO, "ACCOUNT NUMBER");
		PSTR_ORDER.put(PSTR_ORDER_BY_ACOUNT_NAME, "ACCOUNT NAME");
		PSTR_ORDER.put(PSTR_ORDER_BY_TRIP_DATE, "TRIP DATE");
		PSTR_ORDER.put(PSTR_ORDER_BY_UPLOAD_DATE, "UPLOAD DATE");

		//GIRO Request Status
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_PENDING, "PENDING");
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_PENDING_PROGRESS, "PENDING PROGRESS");
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_IN_PROGRESS, "IN PROGRESS");
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_COMPLETED, "COMPLETED");
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_DELETED, "DELETED");
		GIRO_REQUEST_STATUS.put(GIRO_REQUEST_STATUS_ERROR, "ERROR");

		//Giro UOB Clear Fate
		GIRO_UOB_CLEAR_FATE.put(GIRO_UOB_CLEAR_FATE_ACCEPTED, "ACCEPTED");
		GIRO_UOB_CLEAR_FATE.put(GIRO_UOB_CLEAR_FATE_REJECTED, "REJECTED");

		//GIRO rejected by
		GIRO_REJECTED_BY.put(GIRO_REJECTED_BY_BANK, "BANK");
		GIRO_REJECTED_BY.put(GIRO_REJECTED_BY_IBS, "IBS");

		//AS TXN STATUS jtaruc 16/03/2011
		AS_TXN_STATUS.put(AS_ACTIVE, "ACTIVE");
		AS_TXN_STATUS.put(AS_VOID, "VOID");
		AS_TXN_STATUS.put(AS_REVERSAL, "REVERSAL");

		// JTARUC 16/03/2011
		REPORT_MAP.put("OFFLINE_ORDER", AS_OFFLINE_TXN_ORDER);
		AS_OFFLINE_TXN_ORDER.put(AS_OFFLINE_TXN_ORDER_TXNDATE, "TXN DATE");
		AS_OFFLINE_TXN_ORDER.put(AS_OFFLINE_TXN_ORDER_SYSTD, "SYSTEM TRACE NO / TXN DATE");

		// JTARUC 16/03/2011
		REPORT_MAP.put("OFFLINE_MSGTYPE", AS_OFFLINE_MSG_TYPE);
		AS_OFFLINE_MSG_TYPE.put(AS_OFFLINE_SALES, "OFFLINE SALES");
		AS_OFFLINE_MSG_TYPE.put(AS_OFFLINE_VOID, "OFFLINE VOID");

		//Prepaid Request Status
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_STATUS_PENDING_APPROVAL, "Pending Approval");
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_STATUS_REJECTED, "Rejected");
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_STATUS_PENDING_PAYMENT, "Pending Payment");
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_RED_DOT_FAIL, "Red Dot Failed Transaction");
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_STATUS_COMPLETED, "Completed");
		PREPAID_REQUEST_STATUS.put(PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT, "Completed w/o Payment");

		//PREPAID Approval Status
		PREPAID_APPROVAL_STATUS.put(PREPAID_APPROVAL_STATUS_PENDING, "Pending");
		PREPAID_APPROVAL_STATUS.put(PREPAID_APPROVAL_STATUS_APPROVED, "Approved");
		PREPAID_APPROVAL_STATUS.put(PREPAID_APPROVAL_STATUS_REJECTED, "Rejected");


		//PREPAID Request Type
		PREPAID_REQUEST_TYPE.put(PREPAID_REQUEST_TYPE_ISSUANCE, "Issuance");
		PREPAID_REQUEST_TYPE.put(PREPAID_REQUEST_TYPE_TOP_UP, "Top Up");
		PREPAID_REQUEST_TYPE.put(PREPAID_REQUEST_TYPE_TRANSFER, "Transfer");
		PREPAID_REQUEST_TYPE.put(PREPAID_REQUEST_TYPE_EXTEND_BALANCE_EXPIRY_DATE, "Extend Balance Expiry Date");
		PREPAID_REQUEST_TYPE.put(PREPAID_REQUEST_TYPE_ADJUSTEMENT, "Adjustment");

		//PREPAID Top Up Type
		PREPAID_TOP_UP_TYPE.put(PREPAID_TOP_UP_TYPE_ALL, "All");
		PREPAID_TOP_UP_TYPE.put(PREPAID_TOP_UP_TYPE_PROMOTION_ONLY, "Promotion Only");
		PREPAID_TOP_UP_TYPE.put(PREPAID_TOP_UP_TYPE_NON_PROMOTION, "Non Promotion");

		//Top Up New Balance ExpiryDate Duration Type
		NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE.put(NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DATE , "Date");
		NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE.put(NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION, "Duration");

		//PREPAID Approval Report Order
		PREPAID_APPROVAL_REPORT_ORDER.put(PREPAID_APPROVAL_REPORT_ORDER_REQUEST_DATE, "Request Date");

		//PREPAID Top Up Report Order
		PREPAID_TOP_UP_REPORT_ORDER.put(PREPAID_TOP_UP_REPORT_ORDER_ACCOUNT_NAME, "Account Name");
		PREPAID_TOP_UP_REPORT_ORDER.put(PREPAID_TOP_UP_REPORT_ORDER_CARD_NO, "Card No");

		//PREPAID Usage Report Order
		PREPAID_USAGE_REPORT_ORDER.put(PREPAID_USAGE_REPORT_ORDER_NUM_OF_MONTHS_SUBSCRIBED_DESC, "Number of Months (Desc)");
		PREPAID_USAGE_REPORT_ORDER.put(PREPAID_USAGE_REPORT_ORDER_NUM_OF_TOP_UP_DESC, "Number of Top Up (Desc)");
		PREPAID_USAGE_REPORT_ORDER.put(PREPAID_USAGE_REPORT_ORDER_PURCHASE_AMOUNT_DESC, "Purchase Amount (Desc)");

		// PREPAID transaction type
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_ISSUE, "Issue");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_TOP_UP, "Top Up");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_ADJUST_VALUE, "Adjust Value");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_ADJUST_CASHPLUS, "Adjust CashPlus");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_IN, "Transfer In");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_OUT, "Transfer Out");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_INTERNAL_TRANSFER_IN, "Transfer In");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_INTERNAL_TRANSFER_OUT, "Transfer Out");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_TRANSFER_FEE, "Transfer Fee");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_TRIP, "Trip");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_EDIT_TRIP, "Trip");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_VOID_TRIP, "Void Trip");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_REPLACEMENT_FEE, "Replace Fee");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_FORFEIT_VALUE, "Forfeit Card Value");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_FORFEIT_CASHPLUS, "Forfeit CashPlus");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_FORFEIT_ADJUST_VALUE, "Forfeit Adjust Card Value");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_FORFEIT_ADJUST_CASHPLUS, "Forfeit Adjust CashPlus");
		PREPAID_TXN_TYPE.put(PREPAID_TXN_TYPE_TRANSFER_ADJUSTMENT, "Transfer Adjustment");

		//E Invoice Attachment Selection
		E_INVOICE_ATTACHMENT_SELECTION.put(E_INVOICE_EMAIL_ATTACHMENT_ONE, "ONE");
		E_INVOICE_ATTACHMENT_SELECTION.put(E_INVOICE_EMAIL_ATTACHMENT_MULTIPLE, "MULTIPLE");

		//E Invoice Page Selection
		E_INVOICE_PAGE_SELECTION.put(E_INVOICE_EMAIL_PAGE_ALL, "ALL");
		E_INVOICE_PAGE_SELECTION.put(E_INVOICE_EMAIL_PAGE_NO_REPORT, "NO TRANSACTION DETAIL REPORT");



		//Hours
		for(int i=0; i<24; i++){
			HOURS.put(i, String.valueOf(i));
		}

		//Minutes
		for(int i=0; i<60; i++){
			MINUTES.put(i, String.valueOf(i));
		}

		final String[] daySuffixes = {"st","nd","rd","th"};
		//Minutes
		DAYSwithSuffix.put(null, "-");
		for(int i=1; i<=31; i++){
			if(i>=4) {
				DAYSwithSuffix.put(String.valueOf(i),i+daySuffixes[3]);
			}
			else if(i==1) {
				DAYSwithSuffix.put(String.valueOf(i),i+daySuffixes[0]);
			}else if(i==2) {
				DAYSwithSuffix.put(String.valueOf(i),i+daySuffixes[1]);
			}else if(i==3) {
				DAYSwithSuffix.put(String.valueOf(i), i+daySuffixes[2]);
			}

		}
	}
	/**
	 * @author Tan Yiming
	 * A enum to get account templates. Too complex to map back to UI
	 */
	/*public enum ACCOUNT_TEMPLATES {
		CORPORATE("C"), PERSONAL("P");
		private static final HashMap<String, ACCOUNT_TEMPLATES> templates = new HashMap<String, ACCOUNT_TEMPLATES>();
		static {
			for(ACCOUNT_TEMPLATES s : EnumSet.allOf(ACCOUNT_TEMPLATES.class))
				templates.put(s.getCode(), s);
		}
		private String code;
		private ACCOUNT_TEMPLATES(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
		public static ACCOUNT_TEMPLATES get(String code){
			return templates.get(code);
		}
	}*/


	public static boolean getBoolean(String value){

		return BOOLEAN_YES.equals(value)?true:false;

	}

	public static String getBooleanFlag(boolean bool){

		return bool ? BOOLEAN_YES : BOOLEAN_NO;

	}

}
