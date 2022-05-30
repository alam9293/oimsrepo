package com.cdgtaxi.ibs;

public class Uri {
	// Uri for refreshing master
	public static final String REFRESH_MASTER	= "/master/refresh.zul";
	// common
	public static final String PROCESSING	= "/common/processing.zul";
	/* ************* ACL ***************** */
	//ROLE MGMT
	public static final String SEARCH_ROLE 	= "/acl/role/search_role.zul";
	public static final String EDIT_ROLE 	= "/acl/role/edit_role.zul";
	public static final String VIEW_ROLE 	= "/acl/role/view_role.zul";
	public static final String CREATE_ROLE 	= "/acl/role/create_role.zul";

	//USER MGMT
	public static final String SEARCH_USER 		= "/acl/user/search_user.zul";
	public static final String EDIT_USER 		= "/acl/user/edit_user.zul";
	public static final String VIEW_USER 		= "/acl/user/view_user.zul";
	public static final String CREATE_USER 		= "/acl/user/create_user.zul";
	public static final String CHANGE_PASSWORD	= "/acl/user/change_password.zul";

	// Account Type Mgmt
	public static final String MANAGE_ACCT_TYPE		= "/acct/type/manage.zul";
	public static final String ADD_ACCT_TYPE		= "/acct/type/add.zul";
	// Application Mgmt
	public static final String CREATE_CORP_APP		= "/acct/app/createCorp.zul";
	public static final String CREATE_PERS_APP		= "/acct/app/createPers.zul";
	public static final String MANAGE_APP			= "/acct/app/manage.zul";
	public static final String VIEW_CORP_APP		= "/acct/app/viewCorp.zul";
	public static final String VIEW_PERS_APP		= "/acct/app/viewPers.zul";
	public static final String APPROVE_CORP_APP		= "/acct/app/approveCorp.zul";
	public static final String APPROVE_PERS_APP		= "/acct/app/approvePers.zul";
	public static final String APPROVE_CORP_APP_2	= "/acct/app/approveCorp2.zul";
	public static final String APPROVE_PERS_APP_2	= "/acct/app/approvePers2.zul";
	// Account Mgmt
	public static final String VIEW_INFO			= "/acct/info.zul";
	public static final String SEARCH_ACCT			= "/acct/acct/search.zul";
	public static final String MANAGE_CORP_ACCT		= "/acct/acct/manageCorp.zul";
	public static final String MANAGE_PERS_ACCT		= "/acct/acct/managePers.zul";
	public static final String EDIT_PA_CORP_DETAILS	= "/acct/acct/editPACorp.zul";
	public static final String EDIT_PA_PERS_DETAILS	= "/acct/acct/editPAPers.zul";
	public static final String VIEW_CORP_DETAILS	= "/acct/acct/viewCorp.zul";
	public static final String VIEW_PERS_DETAILS	= "/acct/acct/viewPers.zul";
	public static final String EDIT_CORP_DETAILS	= "/acct/acct/editCorp.zul";
	public static final String EDIT_PERS_DETAILS	= "/acct/acct/editPers.zul";
	public static final String ADD_CONTACT			= "/acct/acct/addContact.zul";
	public static final String VIEW_CONTACT			= "/acct/acct/viewContact.zul";
	public static final String EDIT_CONTACT			= "/acct/acct/editContact.zul";
	public static final String SEARCH_CONTACT		= "/acct/acct/searchContact.zul";
	public static final String ADD_DIVISION			= "/acct/acct/addDivision.zul";
	public static final String VIEW_DIVISION		= "/acct/acct/viewDivision.zul";
	public static final String EDIT_DIVISION		= "/acct/acct/editDivision.zul";
	public static final String SEARCH_DIVISION		= "/acct/acct/searchDivision.zul";
	public static final String ADD_DEPT				= "/acct/acct/addDept.zul";
	public static final String VIEW_DEPT			= "/acct/acct/viewDept.zul";
	public static final String EDIT_DEPT			= "/acct/acct/editDept.zul";
	public static final String SEARCH_DEPT			= "/acct/acct/searchDept.zul";
	public static final String ADD_SUB_PERS			= "/acct/acct/addSubPers.zul";
	public static final String VIEW_SUB_PERS		= "/acct/acct/viewSubPers.zul";
	public static final String EDIT_SUB_PERS		= "/acct/acct/editSubPers.zul";
	public static final String SEARCH_SUB_PERS		= "/acct/acct/searchSubPers.zul";
	public static final String EDIT_PA_BILLING		= "/acct/acct/editPABilling.zul";
	public static final String EDIT_BILLING			= "/acct/acct/editBilling.zul";
	public static final String VIEW_BILLING			= "/acct/acct/viewBilling.zul";
	public static final String EDIT_PA_REWARDS		= "/acct/acct/editPARewards.zul";
	public static final String VIEW_REWARDS			= "/acct/acct/viewRewards.zul";
	public static final String VIEW_DEPOSIT			= "/acct/acct/viewDeposit.zul";
	public static final String SUSPEND_CORP			= "/acct/acct/suspendCorp.zul";
	public static final String SUSPEND_DIV			= "/acct/acct/suspendDivision.zul";
	public static final String SUSPEND_DEPT			= "/acct/acct/suspendDept.zul";
	public static final String SUSPEND_PERS			= "/acct/acct/suspendPers.zul";
	public static final String SUSPEND_SUB_PERS		= "/acct/acct/suspendSubPers.zul";
	public static final String REACTIVATE_CORP		= "/acct/acct/reactivateCorp.zul";
	public static final String REACTIVATE_DIV		= "/acct/acct/reactivateDivision.zul";
	public static final String REACTIVATE_DEPT		= "/acct/acct/reactivateDept.zul";
	public static final String REACTIVATE_PERS		= "/acct/acct/reactivatePers.zul";
	public static final String REACTIVATE_SUB_PERS	= "/acct/acct/reactivateSubPers.zul";
	public static final String TERMINATE_CORP		= "/acct/acct/terminateCorp.zul";
	public static final String TERMINATE_DIV		= "/acct/acct/terminateDivision.zul";
	public static final String TERMINATE_DEPT		= "/acct/acct/terminateDept.zul";
	public static final String TERMINATE_PERS		= "/acct/acct/terminatePers.zul";
	public static final String TERMINATE_SUB_PERS	= "/acct/acct/terminateSubPers.zul";
	public static final String CREDIT_REVIEW_CORP	= "/acct/acct/creditReviewCorp.zul";
	public static final String CREDIT_REVIEW_DIV	= "/acct/acct/creditReviewDivision.zul";
	public static final String CREDIT_REVIEW_DEPT	= "/acct/acct/creditReviewDept.zul";
	public static final String CREDIT_REVIEW_PERS	= "/acct/acct/creditReviewPers.zul";
	public static final String CREDIT_REVIEW_SUB_PERS = "/acct/acct/creditReviewSubPers.zul";
	public static final String OVERDUE_REMINDER_CORP  = "/acct/acct/overdueReminderCorp.zul";
	public static final String OVERDUE_REMINDER_DIV   = "/acct/acct/overdueReminderDivision.zul";
	public static final String OVERDUE_REMINDER_DEPT  = "/acct/acct/overdueReminderDept.zul";
	public static final String OVERDUE_REMINDER_PERS  = "/acct/acct/overdueReminderPers.zul";
	public static final String OVERDUE_REMINDER_SUB_PERS = "/acct/acct/overdueReminderSubPers.zul";
	public static final String MANAGE_PROD_SUBSC	= "/acct/acct/manageProdSubsc.zul";
	public static final String EDIT_PROD_SUBSC		= "/acct/acct/editProdSubsc.zul";
	public static final String ADD_PROD_SUBSC		= "/acct/acct/addProdSubsc.zul";
	public static final String VIEW_CREDIT_REVIEW	= "/acct/acct/viewCreditReview.zul";
	public static final String VIEW_STATUS_HISTORY	= "/acct/acct/viewStatus.zul";
	public static final String SEARCH_BILLING_REQ	= "/acct/acct/searchBillingRequest.zul";
	public static final String VIEW_BILLING_REQ		= "/acct/acct/viewBillingRequest.zul";	
	public static final String VIEW_BILLING_CYCLE_HISTORY	= "/acct/acct/viewBillingCycleHistory.zul";
	public static final String VIEW_CREDIT_TERM_HISTORY	= "/acct/acct/viewCreditTermHistory.zul";
	public static final String VIEW_EARLY_PAYMENT_HISTORY	= "/acct/acct/viewEarlyPaymentHistory.zul";
	public static final String VIEW_LATE_PAYMENT_HISTORY	= "/acct/acct/viewLatePaymentHistory.zul";
	public static final String VIEW_PROMOTION_HISTORY	= "/acct/acct/viewPromotionHistory.zul";
	public static final String EINVOICE_EMAIL  = "/acct/acct/eInvoiceEmail.zul";
	public static final String EINVOICE_EMAIL_CORP  = "/acct/acct/eInvoiceEmailCorp.zul";
	public static final String EINVOICE_EMAIL_DIV  = "/acct/acct/eInvoiceEmailDivision.zul";
	public static final String EINVOICE_EMAIL_DEPT  = "/acct/acct/eInvoiceEmailDept.zul";
	public static final String EINVOICE_EMAIL_PERS  = "/acct/acct/eInvoiceEmailPers.zul";
	public static final String EINVOICE_EMAIL_SUB_PERS  = "/acct/acct/eInvoiceEmailSubPers.zul";

	public static final String ISSUE_DEPOSIT_INVOICE= "/acct/acct/issueDepositInvoice.zul";
	public static final String VIEW_EXPIRED_REWARDS	= "/acct/acct/viewExpiredRewards.zul";
	
	public static final String VIEW_PENDING_BILLING	= "/acct/approval/viewPendingBilling.zul";
	public static final String SEARCH_BILLING		= "/acct/approval/searchBilling.zul";
	public static final String APPROVE_BILLING		= "/acct/approval/approveBilling.zul";

	public static final String VIEW_PENDING_CREDIT_REVIEW	= "/acct/approval/viewPendingCreditReview.zul";
	public static final String SEARCH_CREDIT_REVIEW			= "/acct/approval/searchCreditReview.zul";
	public static final String APPROVE_CREDIT_REVIEW		= "/acct/approval/approveCreditReview.zul";
	public static final String VIEW_CREDIT_REVIEW_REQ		= "/acct/approval/viewCreditReviewRequest.zul";

	public static final String APPROVE_SUBSCRIPTION		= "/acct/approval/approveSubscription.zul";
	public static final String SEARCH_SUBSCRIPTION			= "/acct/approval/searchSubscription.zul";
	public static final String VIEW_SUBSCRIPTION_REQ		= "/acct/approval/viewSubscriptionRequest.zul";
	
	public static final String TRANSFER_ACCOUNT				= "/acct/transfer/transferAcct.zul";
	public static final String VIEW_TRANSFER_ACCOUNT_REQS	= "/acct/transfer/viewTransfers.zul";
	public static final String VIEW_TRANSFER_ACCOUNT_REQ	= "/acct/transfer/viewTransfer.zul";

	/* ************* REPORT ***************** */
	public static final String REPORT			= "Report";
	public static final String REPORT_SELECTION	= "/report/report_selection.zul";
	public static final String REPORT_RESULT	= "/report/report_result.zul";

	// Billing Issue Credit Note
	public static final String VIEW_INVOICE_DETAILS	= "/billing/note_issue_credit_debit.zul";
	public static final String VIEW_NOTE			= "/billing/note/view_note.zul";
	public static final String CANCEL_NOTE			= "/billing/note/cancel_note.zul";
	public static final String ISSUE_NOTE			= "/billing/note/issue_note.zul";
	public static final String APPROVE_NOTE			= "/billing/note/search_note_req.zul";

	//Billing
	public static final String APPLY_PARTIAL_PAYMENT = "/billing/payment/apply_partial_payment.zul";
	public static final String APPLY_EXCESS_AMOUNT_PAYMENT_RECEIPT = "/billing/payment/apply_excess_amount_payment_receipt.zul";
	public static final String VIEW_PAYMENT_RECEIPT = "/billing/payment/view_payment_receipt.zul";
	public static final String CANCEL_PAYMENT_RECEIPT = "/billing/payment/cancel_payment_receipt.zul";
	public static final String AUTO_POPULATE_PAYMENT = "/billing/payment/auto_populate_payment.zul";
	public static final String REFUND_EXCESS_AMOUNT_PAYMENT_RECEIPT = "/billing/payment/refund_excess_amount_payment_receipt.zul";
	public static final String UPDATE_CHEQUE_NO = "/billing/payment/update_cheque_no.zul";
	public static final String VIEW_MEMO_RECEIPT = "/billing/payment/view_memo_receipt.zul";

	//Invoice
	public static final String VIEW_INVOICE	= "/billing/invoice/view_invoice.zul";
	public static final String REPRINT_INVOICE	= "/billing/invoice/reprint_invoice.zul";
	public static final String ISSUE_MISC_INVOICE	= "/billing/invoice/issue_misc_invoice.zul";

	// Misc. Invoice Items
	public static final String ADD_MISC_INVOICE_ITEM	= "/billing/invoice/add_misc_item.zul";
	public static final String EDIT_MISC_INVOICE_ITEM	= "/billing/invoice/edit_misc_item.zul";

	//Transaction
	public static final String VIEW_TXN = "/txn/view_txn.zul";
	public static final String EDIT_TXN = "/txn/edit_txn.zul";
	public static final String APPROVE_TXN = "/txn/app_txn_req.zul";
	public static final String APPROVE_NEW_TXN = "/txn/app_new_txn_req.zul";
	public static final String SEARCH_APPROVE_TXN = "/txn/search_txn_req.zul";

	//PRODUCT and PRODUCT TYPE
	public static final String MANAGE_PRODUCT_TYPE	= "/product/type/manage.zul";
	public static final String ADD_PRODUCT_TYPE	= "/product/type/create_product_type.zul";
	public static final String EDIT_PRODUCT_TYPE= "/product/type/edit_product_type.zul";
	public static final String VIEW_PRODUCT_TYPE= "/product/type/view_product_type.zul";
	public static final String RETAG_PRODUCT= "/product/retag_products.zul";
	public static final String REPLACE_PRODUCT= "/product/replacement_products.zul";
	public static final String RENEW_PRODUCT= "/product/renew_products.zul";
	public static final String SUSPEND_PRODUCT= "/product/suspend_products.zul";
	public static final String TERMINATE_PRODUCT= "/product/terminate_products.zul";
	public static final String REACTIVATE_PRODUCT= "/product/reactive_products.zul";
	public static final String UPDATE_CREDIT_LIMIT= "/product/update_credit_limit.zul";
	public static final String PRODUCT_HISTORY= "/product/view_product_history.zul";
	public static final String PRODUCT_ISSUE_HISTORY= "/product/view_product_issued_history.zul";
	public static final String PRODUCT_STATUS_HISTORY= "/product/view_product_status_history.zul";
	public static final String MANAGE_SPECIFIC_PRODUCT	= "/product/manage_specific_product.zul";
	public static final String VIEW_ISSUED_PRODUCTS	= "/product/view_issued_products_info.zul";
	public static final String VIEW_ISSUED_PRODUCTS_REPLACED_NEW= "/product/view_issued_products_info_replaced_new.zul";
	public static final String VIEW_ACCOUNT_AGING= "/product/view_account_aging.zul";
	public static final String EDIT_PRODUCT= "/product/edit_specific_product.zul";
	public static final String ASSIGN_CARD_PRODUCT = "/product/assign_card_products.zul";
	public static final String ISSUE_PRODUCT = "/product/issue_products.zul";
	public static final String VIEW_TOKEN= "/product/view_token.zul";

	//Bill Gen
	public static final String VIEW_BILL_GEN_REQUEST = "/billgen/view_billgen_request.zul";
	public static final String RESCHEDULE_BILL_GEN_REQUEST = "/billgen/reschedule_billgen_request.zul";

	// Loyalty & Rewards
	public static final String VIEW_LOYALTY_PLAN				= "/rewards/view_plan.zul";
	public static final String CREATE_LOYALTY_PLAN				= "/rewards/create_plan.zul";
	public static final String ADD_LOYALTY_PLAN_DETAIL			= "/rewards/add_plan_detail.zul";
	public static final String EDIT_LOYALTY_PLAN_DETAIL			= "/rewards/edit_plan_detail.zul";
	public static final String VIEW_GIFT_CATEGORY				= "/rewards/view_category.zul";
	public static final String CREATE_GIFT_CATEGORY				= "/rewards/create_category.zul";
	public static final String ADD_GIFT_ITEM					= "/rewards/add_item.zul";
	public static final String EDIT_GIFT_ITEM					= "/rewards/edit_item.zul";
	public static final String MOVE_GIFT_ITEM					= "/rewards/move_item.zul";
	public static final String STOCKUP_GIFT_ITEM				= "/rewards/stockup_item.zul";
	public static final String REDEEM_GIFT_ITEM					= "/rewards/redeem_item.zul";
	public static final String DELETE_GIFT_CATEGORY				= "Delete Gift Category";
	public static final String DELETE_GIFT_ITEM					= "Delete Gift Item";
	public static final String EDIT_GIFT_CATEGORY				= "/rewards/edit_category.zul";
	public static final String VIEW_GIFT_ITEM					= "/rewards/view_item.zul";
	public static final String DELETE_LOYALTY_PLAN				= "Delete Loyalty Plan";
	public static final String DELETE_LOYALTY_PLAN_DETAIL		= "Delete Loyalty Plan Detail";
	public static final String EDIT_LOYALTY_PLAN				= "/rewards/edit_plan.zul";
	public static final String VIEW_LOYALTY_PLAN_DETAIL			= "/rewards/view_plan_detail.zul";
	public static final String VIEW_REWARDS_PENDING_ADJUSTMENT_REQUEST	= "/rewards/adjustment/view_pending_req.zul";
	public static final String APPROVE_REWARDS_ADJUSTMENT_REQUEST		= "/rewards/adjustment/approve_req.zul";
	public static final String VIEW_REWARDS_ADJUSTMENT_REQUEST_HISTORY	= "/rewards/adjustment/search_req.zul";
	public static final String VIEW_REWARDS_ADJUSTMENT_REQUEST			= "/rewards/adjustment/view_req.zul";

	// Inventory
	public static final String VIEW_INVENTORY_ITEM_TYPE				= "/inventory/view_item_type.zul";
	public static final String CREATE_INVENTORY_ITEM_TYPE			= "/inventory/create_item_type.zul";
	public static final String ADD_INVENTORY_STOCK					= "/inventory/add_stock.zul";
	public static final String REQUEST_INVENTORY_ISSUANCE			= "/inventory/request_issuance.zul";
	public static final String VIEW_INVENTORY_REQUEST_ISSUANCE		= "/inventory/view_request.zul";
	public static final String APPROVE_INVENTORY_REQUEST_ISSUANCE	= "Approve Request";
	public static final String VIEW_INVENTORY_ISSUANCE				= "/inventory/view_issuance.zul";
	public static final String DELETE_INVENTORY_ITEM_TYPE			= "Delete Inventory Item Type";
	public static final String ISSUE_INVENTORY_INVOICE				= "Issue Invoice (Sales of Taxi Vouchers)";
	public static final String VIEW_INVENTORY_ITEM					= "/inventory/view_item.zul";
	public static final String MASS_SUBMISSION_ITEM					= "/inventory/mass_submission_item.zul";
	public static final String SUSPEND_ITEM							= "Suspend Taxi Voucher";
	public static final String REACTIVATE_ITEM						= "Reactivate Taxi Voucher";
	public static final String VOID_ITEM							= "Void Taxi Voucher";
	public static final String CHANGE_OF_ITEM_REDEMPTION			= "Change of Taxi Voucher Redemption";
	public static final String REMOVE_ITEM_REDEMPTION				= "Remove Taxi Voucher Redemption";
	public static final String VIEW_INVENTORY_ITEM_REQUEST			= "/inventory/view_item_request.zul";
	public static final String MASS_APPROVAL_ITEM_REQ				= "/inventory/mass_approval_item_req.zul";
	public static final String EDIT_ITEM_EXPIRY_DATE				= "/inventory/edit_item_expiry_date.zul";
	public static final String APPROVE_INVENTORY_ITEM_REQUEST		= "Approve Taxi Voucher Request";
	
	// Admin
	public static final String EDIT_VOLUME_DISCOUNT_PLAN			= "/admin/volume_discount/edit_volume_discount_plan.zul";
	public static final String CREATE_VOLUME_DISCOUNT_PLAN			= "/admin/volume_discount/create_volume_discount_plan.zul";
	public static final String EDIT_VOLUME_DISCOUNT_PLAN_DETAIL		= "/admin/volume_discount/edit_volume_discount_plan_detail.zul";
	public static final String CREATE_VOLUME_DISCOUNT_PLAN_DETAIL	= "/admin/volume_discount/create_volume_discount_plan_detail.zul";
	public static final String VIEW_VOLUME_DISCOUNT_PLAN_DETAIL		= "/admin/volume_discount/view_volume_discount_plan_detail.zul";
	public static final String VIEW_VOLUME_DISCOUNT_PLAN			= "/admin/volume_discount/view_volume_discount_plan.zul";
	public static final String DELETE_VOLUME_DISCOUNT_PLAN			= "Delete Volume Discount Plan";
	public static final String DELETE_VOLUME_DISCOUNT_PLAN_DETAIL	= "Delete Volume Discount Plan Detail";
	
	public static final String EDIT_PRODUCT_DISCOUNT_PLAN			= "/admin/product_discount/edit_product_discount_plan.zul";
	public static final String CREATE_PRODUCT_DISCOUNT_PLAN			= "/admin/product_discount/create_product_discount_plan.zul";
	public static final String EDIT_PRODUCT_DISCOUNT_PLAN_DETAIL	= "/admin/product_discount/edit_product_discount_plan_detail.zul";
	public static final String CREATE_PRODUCT_DISCOUNT_PLAN_DETAIL	= "/admin/product_discount/create_product_discount_plan_detail.zul";
	public static final String VIEW_PRODUCT_DISCOUNT_PLAN_DETAIL	= "/admin/product_discount/view_product_discount_plan_detail.zul";
	public static final String VIEW_PRODUCT_DISCOUNT_PLAN			= "/admin/product_discount/view_product_discount_plan.zul";
	public static final String DELETE_PRODUCT_DISCOUNT_PLAN			= "Delete Product Discount Plan";
	public static final String DELETE_PRODUCT_DISCOUNT_PLAN_DETAIL	= "Delete Product Discount Plan Detail";
	
	public static final String EDIT_ADMIN_FEE_PLAN			= "/admin/admin_fee/edit_admin_fee_plan.zul";
	public static final String CREATE_ADMIN_FEE_PLAN		= "/admin/admin_fee/create_admin_fee_plan.zul";
	public static final String EDIT_ADMIN_FEE_PLAN_DETAIL	= "/admin/admin_fee/edit_admin_fee_plan_detail.zul";
	public static final String CREATE_ADMIN_FEE_PLAN_DETAIL	= "/admin/admin_fee/create_admin_fee_plan_detail.zul";
	public static final String VIEW_ADMIN_FEE_PLAN_DETAIL	= "/admin/admin_fee/view_admin_fee_plan_detail.zul";
	public static final String VIEW_ADMIN_FEE_PLAN			= "/admin/admin_fee/view_admin_fee_plan.zul";
	public static final String DELETE_ADMIN_FEE_PLAN		= "Delete Admin Fee Plan";
	public static final String DELETE_ADMIN_FEE_PLAN_DETAIL	= "Delete Admin Fee Plan Detail";
	
	public static final String EDIT_CREDIT_TERM_PLAN			= "/admin/credit_term/edit_credit_term_plan.zul";
	public static final String CREATE_CREDIT_TERM_PLAN			= "/admin/credit_term/create_credit_term_plan.zul";
	public static final String EDIT_CREDIT_TERM_PLAN_DETAIL		= "/admin/credit_term/edit_credit_term_plan_detail.zul";
	public static final String CREATE_CREDIT_TERM_PLAN_DETAIL	= "/admin/credit_term/create_credit_term_plan_detail.zul";
	public static final String VIEW_CREDIT_TERM_PLAN_DETAIL		= "/admin/credit_term/view_credit_term_plan_detail.zul";
	public static final String VIEW_CREDIT_TERM_PLAN			= "/admin/credit_term/view_credit_term_plan.zul";
	public static final String DELETE_CREDIT_TERM_PLAN			= "Delete Credit Term Plan";
	public static final String DELETE_CREDIT_TERM_PLAN_DETAIL	= "Delete Credit Term Plan Detail";
	
	public static final String EDIT_EARLY_PAYMENT_PLAN			= "/admin/early_payment/edit_early_payment_plan.zul";
	public static final String CREATE_EARLY_PAYMENT_PLAN		= "/admin/early_payment/create_early_payment_plan.zul";
	public static final String EDIT_EARLY_PAYMENT_PLAN_DETAIL	= "/admin/early_payment/edit_early_payment_plan_detail.zul";
	public static final String CREATE_EARLY_PAYMENT_PLAN_DETAIL	= "/admin/early_payment/create_early_payment_plan_detail.zul";
	public static final String VIEW_EARLY_PAYMENT_PLAN_DETAIL	= "/admin/early_payment/view_early_payment_plan_detail.zul";
	public static final String VIEW_EARLY_PAYMENT_PLAN			= "/admin/early_payment/view_early_payment_plan.zul";
	public static final String DELETE_EARLY_PAYMENT_PLAN		= "Delete Early Payment Plan";
	public static final String DELETE_EARLY_PAYMENT_PLAN_DETAIL	= "Delete Early Payment Plan Detail";
	
	public static final String EDIT_LATE_PAYMENT_PLAN			= "/admin/late_payment/edit_late_payment_plan.zul";
	public static final String CREATE_LATE_PAYMENT_PLAN			= "/admin/late_payment/create_late_payment_plan.zul";
	public static final String EDIT_LATE_PAYMENT_PLAN_DETAIL	= "/admin/late_payment/edit_late_payment_plan_detail.zul";
	public static final String CREATE_LATE_PAYMENT_PLAN_DETAIL	= "/admin/late_payment/create_late_payment_plan_detail.zul";
	public static final String VIEW_LATE_PAYMENT_PLAN_DETAIL	= "/admin/late_payment/view_late_payment_plan_detail.zul";
	public static final String VIEW_LATE_PAYMENT_PLAN			= "/admin/late_payment/view_late_payment_plan.zul";
	public static final String DELETE_LATE_PAYMENT_PLAN			= "Delete Late Payment Plan";
	public static final String DELETE_LATE_PAYMENT_PLAN_DETAIL	= "Delete Late Payment Plan Detail";
	
	public static final String EDIT_SUBSCRIPTION_FEE_PLAN			= "/admin/subscription_fee/edit_subscription_fee_plan.zul";
	public static final String CREATE_SUBSCRIPTION_FEE_PLAN			= "/admin/subscription_fee/create_subscription_fee_plan.zul";
	public static final String EDIT_SUBSCRIPTION_FEE_PLAN_DETAIL	= "/admin/subscription_fee/edit_subscription_fee_plan_detail.zul";
	public static final String CREATE_SUBSCRIPTION_FEE_PLAN_DETAIL	= "/admin/subscription_fee/create_subscription_fee_plan_detail.zul";
	public static final String VIEW_SUBSCRIPTION_FEE_PLAN_DETAIL	= "/admin/subscription_fee/view_subscription_fee_plan_detail.zul";
	public static final String VIEW_SUBSCRIPTION_FEE_PLAN			= "/admin/subscription_fee/view_subscription_fee_plan.zul";
	public static final String DELETE_SUBSCRIPTION_FEE_PLAN			= "Delete Subscription Fee Plan";
	public static final String DELETE_SUBSCRIPTION_FEE_PLAN_DETAIL	= "Delete Subscription Fee Plan Detail";
	
	public static final String EDIT_ISSUANCE_FEE_PLAN			= "/admin/issuance_fee/edit_issuance_fee_plan.zul";
	public static final String CREATE_ISSUANCE_FEE_PLAN			= "/admin/issuance_fee/create_issuance_fee_plan.zul";
	public static final String EDIT_ISSUANCE_FEE_PLAN_DETAIL	= "/admin/issuance_fee/edit_issuance_fee_plan_detail.zul";
	public static final String CREATE_ISSUANCE_FEE_PLAN_DETAIL	= "/admin/issuance_fee/create_issuance_fee_plan_detail.zul";
	public static final String VIEW_ISSUANCE_FEE_PLAN_DETAIL	= "/admin/issuance_fee/view_issuance_fee_plan_detail.zul";
	public static final String VIEW_ISSUANCE_FEE_PLAN			= "/admin/issuance_fee/view_issuance_fee_plan.zul";
	public static final String DELETE_ISSUANCE_FEE_PLAN			= "Delete Issuance Fee Plan";
	public static final String DELETE_ISSUANCE_FEE_PLAN_DETAIL	= "Delete Issuance Fee Plan Detail";
	
	public static final String EDIT_GL_BANK		= "/admin/gl_bank/edit_gl_bank.zul";
	public static final String CREATE_GL_BANK	= "/admin/gl_bank/create_gl_bank.zul";
	public static final String EDIT_GL_BRANCH	= "/admin/gl_bank/edit_gl_branch.zul";
	public static final String CREATE_GL_BRANCH	= "/admin/gl_bank/create_gl_branch.zul";
	public static final String VIEW_GL_BANK		= "/admin/gl_bank/view_gl_bank.zul";
	public static final String DELETE_GL_BANK	= "Delete GL Bank";
	
	public static final String EDIT_GL_CONTROL_CODE				= "/admin/gl_control_code/edit_gl_control_code.zul";
	public static final String CREATE_GL_CONTROL_CODE			= "/admin/gl_control_code/create_gl_control_code.zul";
	public static final String EDIT_GL_CONTROL_CODE_DETAIL		= "/admin/gl_control_code/edit_gl_control_code_detail.zul";
	public static final String CREATE_GL_CONTROL_CODE_DETAIL	= "/admin/gl_control_code/create_gl_control_code_detail.zul";
	public static final String VIEW_GL_CONTROL_CODE_DETAIL		= "/admin/gl_control_code/view_gl_control_code_detail.zul";
	public static final String VIEW_GL_CONTROL_CODE				= "/admin/gl_control_code/view_gl_control_code.zul";
	public static final String DELETE_GL_CONTROL_CODE			= "Delete AR Control Account";
	public static final String DELETE_GL_CONTROL_CODE_DETAIL	= "Delete AR Control Account Detail";
	
	public static final String EDIT_BANK				= "/admin/bank/edit_bank.zul";
	public static final String CREATE_BANK				= "/admin/bank/create_bank.zul";
	public static final String EDIT_BANK_BRANCH			= "/admin/bank/edit_branch.zul";
	public static final String CREATE_BANK_BRANCH		= "/admin/bank/create_branch.zul";
	public static final String CREATE_NEW_BANK_BRANCH	= "/admin/bank/create_bank_branch.zul";
	public static final String EDIT_NEW_BANK_BRANCH		= "/admin/bank/edit_bank_branch.zul";
	public static final String VIEW_BANK_BRANCH			= "/admin/bank/view_branch.zul";
	public static final String VIEW_BANK				= "/admin/bank/view_bank.zul";
	public static final String DELETE_BANK_BRANCH		= "Delete Bank Detail";
	public static final String DELETE_BANK				= "Delete Bank";
	
	public static final String EDIT_GST		= "/admin/gst/edit_gst.zul";
	public static final String CREATE_GST	= "/admin/gst/create_gst.zul";
	public static final String VIEW_GST		= "/admin/gst/view_gst.zul";
	public static final String DELETE_GST	= "Delete Tax Code";
	
	public static final String EDIT_SALES_PERSON	= "/admin/sales_person/edit_sales_person.zul";
	public static final String CREATE_SALES_PERSON	= "/admin/sales_person/create_sales_person.zul";
	public static final String DELETE_SALES_PERSON	= "Delete Sales Person";
	public static final String VIEW_SALES_PERSON	= "/admin/sales_person/view_sales_person.zul";
	
	public static final String EDIT_TRANSACTION_CODE	= "/admin/transaction_code/edit_transaction_code.zul";
	public static final String CREATE_TRANSACTION_CODE	= "/admin/transaction_code/create_transaction_code.zul";
	public static final String VIEW_TRANSACTION_CODE	= "/admin/transaction_code/view_transaction_code.zul";
	public static final String DELETE_TRANSACTION_CODE	= "Delete Transaction Code";
	
	public static final String EDIT_ENTITY		= "/admin/entity/edit_entity.zul";
	public static final String CREATE_ENTITY	= "/admin/entity/create_entity.zul";
	public static final String VIEW_ENTITY		= "/admin/entity/view_entity.zul";
	public static final String DELETE_ENTITY	= "Delete Entity";
	
	public static final String CREATE_PROMOTION_PLAN = "/admin/promotion/create_promotion_plan.zul";
	public static final String EDIT_PROMOTION_PLAN = "/admin/promotion/edit_promotion_plan.zul";
	public static final String VIEW_PROMOTION_PLAN = "/admin/promotion/view_promotion_plan.zul";
	public static final String VIEW_PROMOTION_PLAN_DETAIL = "/admin/promotion/view_promotion_plan_detail.zul";
	public static final String SEARCH_PROMOTION_PLAN_HISTORY = "/admin/promotion/search_promotion_plan_history.zul";
	public static final String DELETE_PROMOTION_PLAN = "Delete Promotion Plan";
	
	public static final String VIEW_NON_BILLABLE_BATCH = "/nonbillable/view_nonbillable_batch.zul";
	public static final String CREATE_BANK_PAYMENT_ADVISE = "/nonbillable/create_bank_payment_advise.zul";
	public static final String VIEW_NON_BILLABLE_TXN = "/nonbillable/view_nonbillable_txn.zul";
	public static final String VIEW_BANK_PAYMENT_ADVISE = "/nonbillable/view_bank_payment_advise.zul";
	
	public static final String CREATE_ACQUIRER = "/admin/non_billable/create_acquirer.zul";
	public static final String EDIT_ACQUIRER = "/admin/non_billable/edit_acquirer.zul";
	public static final String VIEW_ACQUIRER = "/admin/non_billable/view_acquirer.zul";
	public static final String DELETE_ACQUIRER = "Delete Acquirer";
	
	public static final String CREATE_ACQUIRER_PAYMENT_TYPE = "/admin/non_billable/create_acquirer_pymt_type.zul";
	public static final String EDIT_ACQUIRER_PAYMENT_TYPE = "/admin/non_billable/edit_acquirer_pymt_type.zul";
	public static final String VIEW_ACQUIRER_PAYMENT_TYPE = "/admin/non_billable/view_acquirer_pymt_type.zul";
	public static final String DELETE_ACQUIRER_PAYMENT_TYPE = "Delete Acquirer Payment Type";
	
	public static final String CREATE_ACQUIRER_MDR = "/admin/non_billable/create_acquirer_mdr.zul";
	public static final String EDIT_ACQUIRER_MDR = "/admin/non_billable/edit_acquirer_mdr.zul";
	public static final String VIEW_ACQUIRER_MDR = "/admin/non_billable/view_acquirer_mdr.zul";
	public static final String DELETE_ACQUIRER_MDR = "Delete Acquirer MDR";

	public static final String CREATE_NONBILLABLE_GL = "/admin/gl_non_billable/create_non_billable.zul";
	public static final String CREATE_NONBILLABLE_GL_DETAIL = "/admin/gl_non_billable/create_non_billable_detail.zul";
	public static final String EDIT_NONBILLABLE_GL = "/admin/gl_non_billable/edit_non_billable.zul";
	public static final String EDIT_NONBILLABLE_GL_DETAIL = "/admin/gl_non_billable/edit_non_billable_detail.zul";
	public static final String VIEW_NONBILLABLE_GL_DETAIL = "/admin/gl_non_billable/view_non_billable_detail.zul";
	public static final String VIEW_NONBILLABLE_GL = "/admin/gl_non_billable/view_non_billable.zul";
	public static final String DELETE_NONBILLABLE_GL = "Delete Non Billable GL";
	public static final String DELETE_NONBILLABLE_GL_DETAIL = "Delete Non Billable GL Detail";
	
	public static final String CREATE_BANK_PAYMENT_GL = "/admin/gl_bank_payment/create_bank_payment.zul";
	public static final String CREATE_BANK_PAYMENT_DETAIL_GL = "/admin/gl_bank_payment/create_bank_payment_detail.zul";
	public static final String EDIT_BANK_PAYMENT_GL = "/admin/gl_bank_payment/edit_bank_payment.zul";
	public static final String EDIT_BANK_PAYMENT_DETAIL_GL = "/admin/gl_bank_payment/edit_bank_payment_detail.zul";
	public static final String VIEW_BANK_PAYMENT_GL = "/admin/gl_bank_payment/view_bank_payment.zul";
	public static final String VIEW_BANK_PAYMENT_DETAIL_GL = "/admin/gl_bank_payment/view_bank_payment_detail.zul";
	public static final String Delete_BANK_PAYMENT_GL = "Delete Bank Payment GL";
	public static final String Delete_BANK_PAYMENT_DETAIL_GL = "Delete Bank Payment Detail GL";
	
	//External Product
	public static final String CREATE_NEGATIVE_EXTERNAL_PRODUCT = "/product/external/create_negative_external_product.zul";
	public static final String DELETE_NEGATIVE_EXTERNAL_PRODUCT = "Delete Negative External Product";
	
	//Giro Interface
	public static final String VIEW_GIRO_REQUEST = "/admin/giro_interface/view_giro_request.zul";
	public static final String EDIT_GIRO_REQUEST = "/admin/giro_interface/edit_giro_request.zul";
	public static final String DELETE_GIRO_REQUEST = "/admin/giro_interface/delete_giro_request.zul";
	public static final String PREVIEW_GIRO_RETURN_FILE = "/admin/giro_interface/preview_giro_return_file.zul";
	public static final String CREATE_GIRO_RETURN_REQUEST = "/admin/giro_interface/create_giro_return_request.zul";
	public static final String VIEW_GIRO_RETURN_REQUEST = "/admin/giro_interface/view_giro_return_request.zul";
	public static final String EDIT_GIRO_RETURN_REQUEST = "/admin/giro_interface/edit_giro_return_request.zul";
	public static final String DELETE_GIRO_RETURN_REQUEST = "/admin/giro_interface/edit_giro_return_request.zul";
	
	//Govt eInv Interface
	public static final String VIEW_GOVT_EINV_REQUEST = "/govt_einv/view_request.zul";
	
	//pubbs
	public static final String VIEW_PUBBS_REQUEST = "/pubbs/view_pubbs_request.zul";
	
	//recurring
	public static final String VIEW_RECURRING_REQUEST = "/recurring/view_recurring_request.zul";
		
	//Prepaid
	public static final String NEW_ISSUANCE_REQ	= "/prepaid/new_issuance_req.zul";
	public static final String NEW_TOP_UP_REQ	= "/prepaid/new_top_up_req.zul";
	public static final String NEW_TRANSFER_REQ_SEARCH	= "/prepaid/new_transfer_req_search.zul";
	public static final String NEW_TRANSFER_REQ	= "/prepaid/new_transfer_req.zul";
	public static final String NEW_EXTEND_BALANCE_EXPIRY_DATE_REQ_SEARCH	= "/prepaid/new_extend_balance_expiry_date_req_search.zul";
	public static final String NEW_EXTEND_BALANCE_EXPIRY_DATE_REQ	= "/prepaid/new_extend_balance_expiry_date_req.zul";
	public static final String NEW_ADJUSTMENT_REQ_SEARCH	= "/prepaid/new_adjustment_req_search.zul";
	public static final String NEW_ADJUSTMENT_REQ	= "/prepaid/new_adjustment_req.zul";
	
	public static final String VIEW_ISSUANCE_REQ	= "/prepaid/view_issuance_req.zul";
	public static final String VIEW_TOP_UP_REQ	= "/prepaid/view_top_up_req.zul";
	public static final String VIEW_TRANSFER_REQ	= "/prepaid/view_transfer_req.zul";
	public static final String VIEW_EXTEND_BALANCE_EXPIRY_DATE_REQ	= "/prepaid/view_extend_balance_expiry_date_req.zul";
	public static final String VIEW_ADJUSTMENT_REQ	= "/prepaid/view_adjustment_req.zul";
	
	public static final String APPROVE_ISSUANCE_REQ	= "/prepaid/approve_issuance_req.zul";
	public static final String APPROVE_TOP_UP_REQ	= "/prepaid/approve_top_up_req.zul";
	public static final String APPROVE_TRANSFER_REQ	= "/prepaid/approve_transfer_req.zul";
	public static final String APPROVE_EXTEND_BALANCE_EXPIRY_DATE_REQ	= "/prepaid/approve_extend_balance_expiry_date_req.zul";
	public static final String APPROVE_ADJUSTMENT_REQ	= "/prepaid/approve_adjustment_req.zul";

	public static final String ISSUANCE_REQ_ADD_NEW_CARD = "/prepaid/issuance_req_add_card.zul";
	public static final String TOP_UP_REQ_ADD_NEW_CARD = "/prepaid/top_up_req_add_card.zul";
	public static final String ADD_PROMOTION_CASHPLUS = "/prepaid/add_promotion_cashplus.zul";
	
	public static final String CREATE_PREPAID_PROMOTION_PLAN = "/admin/prepaid_promotion/create_prepaid_promotion_plan.zul";
	public static final String EDIT_PREPAID_PROMOTION_PLAN = "/admin/prepaid_promotion/edit_prepaid_promotion_plan.zul";
	public static final String MANAGE_PREPAID_PROMOTION_PLAN = "/admin/prepaid_promotion/manage_prepaid_promotion_plan.zul";
	public static final String VIEW_PREPAID_PROMOTION_PLAN = "/admin/prepaid_promotion/view_prepaid_promotion_plan.zul";
	public static final String DELETE_PREPAID_PROMOTION_PLAN = "Delete Prepaid Promotion Plan";
	
	public static final String APPROVAL_PREPAID_REQ = "/prepaid/prepaid_request_approval_search.zul";	
	
	public static final String MANAGE_INVOICE_PROMO = "/admin/invoice_promo/manage_invoice_promo.zul";
	public static final String CREATE_INVOICE_PROMO = "/admin/invoice_promo/create_invoice_promo.zul";
	public static final String EDIT_INVOICE_PROMO = "/admin/invoice_promo/edit_invoice_promo.zul";
	public static final String DELETE_INVOICE_PROMO = "Delete Invoice Promo";
	
	public static final String CREATE_MASTER_LOGIN = "/portal/master_login_registration.zul";
	public static final String MANAGE_MASTER_LOGIN = "/portal/manage_master_login.zul";
	public static final String EDIT_MASTER_LOGIN = "/portal/edit_master_login.zul";
}