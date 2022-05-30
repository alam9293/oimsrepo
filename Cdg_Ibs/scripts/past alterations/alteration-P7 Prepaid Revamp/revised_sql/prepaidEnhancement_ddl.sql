SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_prepaidEnhancement_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


--- version 0.02
--- 47 srcipts
--promotion cashplus
create table mstb_promotion_cashplus (
	promo_code 			varchar2(255 char) not null, 
	created_by 			varchar2(255 char), 
	created_dt 			timestamp, 
	updated_by 			varchar2(255 char), 
	updated_dt 			timestamp, 
	version 			number(10,0), 
	effective_dt_from 	timestamp, 
	effective_dt_to 	timestamp, 
	cashplus 			number(19,2), 
	remarks 			varchar2(255 char), 
	status 				varchar2(255 char), 
	constraint mspc_promotion_cp__promo_code primary key (promo_code)
);

comment on table mstb_promotion_cashplus is 'store the cash plus promotion info';
comment on column mstb_promotion_cashplus.cashplus is 'Cash Plus given for the promotion';

--pmtb product -- jtaruc from version 0.10 update Date to timestamp of balance_expiry_date
alter table pmtb_product add balance_expiry_date timestamp;

--prepaid common request
create sequence pmsq_prepaid_req minvalue 1 increment by 1 start with 1 cache 10 noorder nocycle ;


create table pmtb_prepaid_req (
	req_no 			number(19,0) not null, 
	account_no 		number(10,0), 
	requestor 		number(19,0), 
	request_date 	timestamp, 
	status 			varchar2(255 char), 
	request_type 	varchar2(255 char), 
	created_by 		varchar2(255 char), 
	created_dt 		timestamp, 
	updated_by 		varchar2(255 char), 
	updated_dt 		timestamp, 
	version 		number(10,0), 
	constraint pmpc_prereq__req_no primary key (req_no)
);

alter table pmtb_prepaid_req add constraint pmfc_prereq__acct_no foreign key (account_no) 
references amtb_account;
create index pmix_prereq__acct_no on pmtb_prepaid_req (account_no);


alter table pmtb_prepaid_req add constraint pmfc_prereq__requestor foreign key (requestor) 
references satb_user;
-- KMYoong: MISSING INDEX
-- Victor >> Added below:
create index pmix_prereq__requestor on pmtb_prepaid_req (requestor);


create index pmix_prereq_req_type on pmtb_prepaid_req (request_type);

alter table pmtb_prepaid_req add bmtb_invoice_header number(19,0);
alter table pmtb_prepaid_req add constraint pmfc_prereq__inv_header_no foreign key (bmtb_invoice_header) 
references bmtb_invoice_header;
create index pmix_prereq__inv_hdr on pmtb_prepaid_req (bmtb_invoice_header);


alter table pmtb_prepaid_req add approval_required varchar2(255 char);
alter table pmtb_prepaid_req add approval_status varchar2(255 char);


--modification on prepaid req table
alter table pmtb_prepaid_req drop column approval_status;


alter table pmtb_prepaid_req add request_remarks varchar2(255 char);
alter table pmtb_prepaid_req add approver number(19,0);
alter table pmtb_prepaid_req add approval_date timestamp;
alter table pmtb_prepaid_req add approval_status varchar2(255 char);
alter table pmtb_prepaid_req add approval_remarks varchar2(255 char);


alter table pmtb_prepaid_req add constraint pmfc_prereq__approver foreign key (approver) 
references satb_user;
-- KMYoong : MISSING INDEX
-- Victor >> Added below:
create index pmix_prereq__approver on pmtb_prepaid_req (approver);


alter table pmtb_prepaid_req add process_date timestamp ;


comment on table pmtb_prepaid_req is 'sstoring common information used by ISSUANCE, TOP_UP, TRANSFER, EXT_BAL_EXP, ADJUSTMENT req tables ';
comment on column pmtb_prepaid_req.account_no 		 	is 'Account to be applied by the request';
comment on column pmtb_prepaid_req.status 			 	is 'PA- Pending Approval R - Rejected PP - Pending Payment C -Completed Payment CWOP - Completed Without Payment';
comment on column pmtb_prepaid_req.request_type 	 	is 'IS - Issuance Request TU - Top Up Request TR - Transfer Request EE - Extend Balance of Expiry Date Request AD - Adjustment Request ';
comment on column pmtb_prepaid_req.process_date 	 	is 'date submitted the request or date same with approval date, if no approval required for the request';
comment on column pmtb_prepaid_req.approval_status 	 	is 'P - Pending A - Approved R - Rejected';
comment on column pmtb_prepaid_req.approval_required 	is 'To determine whether the request approval needed';
comment on column pmtb_prepaid_req.bmtb_invoice_header 	is 'Invoice no of invoices generated for the request';



--prepaid issuance request
create table pmtb_issuance_req (
	req_no 			number(19,0) not null, 
	discount 		number(19,2), 
	delivery_charge number(19,2), 
	remarks 		varchar2(255 char), 
	credit_term 	varchar2(255 char), 
	constraint pmpc_isreq_req_no primary key (req_no)
);


alter table pmtb_issuance_req add bmtb_invoice_header number(19,0);
alter table pmtb_issuance_req drop column credit_term; 
alter table pmtb_issuance_req add credit_term_plan_no number(10,0);
alter table pmtb_issuance_req add constraint pmfc_isreq_req_no foreign key (req_no) references pmtb_prepaid_req;

alter table pmtb_issuance_req add total_amount number(19,2);
alter table pmtb_issuance_req drop column bmtb_invoice_header;

alter table pmtb_issuance_req add constraint pmfc_isreq__cterm_plan_no foreign key (credit_term_plan_no) 
references mstb_credit_term_master;
create index pmix_isreq__cterm_plan_no on pmtb_issuance_req (credit_term_plan_no);

alter table pmtb_issuance_req drop column remarks;

alter table pmtb_issuance_req add delivery_charge_txn_code number(10,0) ;

alter table pmtb_issuance_req add constraint pmfc_isreq__dely_cr_txn_code foreign key (delivery_charge_txn_code) 
references fmtb_transaction_code;
create index pmix_isreq__dely_cr_txn_code on pmtb_issuance_req (delivery_charge_txn_code);

comment on table pmtb_issuance_req 	is 'table contains the information while user create a prepaid issuance request ';
comment on column pmtb_issuance_req.discount 			is 'Discount value';
comment on column pmtb_issuance_req.delivery_charge 	is 'Delivery charge value';
comment on column pmtb_issuance_req.credit_term_plan_no is 'Account to be applied by the request';
comment on column pmtb_issuance_req.total_amount 		is 'The total amount that would be reflected in the invoice.';
comment on column pmtb_issuance_req.delivery_charge_txn_code is 'Transaction code of the delivery charge';


--prepaid issuance request card
create sequence pmsq_issuance_req_card minvalue 1 increment by 1 start with 1 cache 10 noorder nocycle ;

create table pmtb_issuance_req_card (
	req_card_no 		number(19,0) not null, 
	name_on_card 		varchar2(255 char), 
	position 			varchar2(255 char), 
	mobile 				varchar2(255 char), 
	telephone 			varchar2(255 char), 
	email 				varchar2(255 char), 
	expiry_date_time 	date, 
	sms_expiry_flag 	varchar2(255 char), 
	sms_topup_flag 		varchar2(255 char), 
	value 				number(19,2), 
	value_expiry_date 	date, 
	cashplus 			number(19,2),
	product_type_id 	varchar2(255 char), 
	account_no 			number(10,0), 
	pmtb_issuance_req 	number(19,0), 
	constraint pmpc_isreq__req_card_no primary key (req_card_no)
);

alter table pmtb_issuance_req_card add issuance_fee number(19,2);

alter table pmtb_issuance_req_card add constraint pmfc_isreqcard_acct_no foreign key (account_no) 
references amtb_account;
create index pmix_isreqcard__acct_no on pmtb_issuance_req_card (account_no);

alter table pmtb_issuance_req_card add constraint pmfc_isreqcard_pd_type_id foreign key (product_type_id) 
references pmtb_product_type;
create index pmix_isreqcard__product_type on pmtb_issuance_req_card (product_type_id);

alter table pmtb_issuance_req_card add constraint pmfc_isreqcard_isreq foreign key (pmtb_issuance_req) 
references pmtb_issuance_req;
create index pmix_isreqcard__is_req on pmtb_issuance_req_card (pmtb_issuance_req);

alter table pmtb_issuance_req_card add waive_issuance_fee_flag varchar2(64 char);
alter table pmtb_issuance_req_card add product_no number(19,0) ;

alter table pmtb_issuance_req_card add constraint pmfc_isreqcard_product_no foreign key (product_no) 
references pmtb_product;
create index pmix_isreqcard__product_no on pmtb_issuance_req_card (product_no);


alter table pmtb_issuance_req_card drop (
	name_on_card, 
	position, 
	mobile, 
	telephone, 
	email, 
	expiry_date_time, 
	sms_expiry_flag, 
	sms_topup_flag, 
	value, 
	value_expiry_date, 
	cashplus, 
	waive_issuance_fee_flag
);

alter table pmtb_issuance_req_card add (
  balance_expiry_date      date,
  name_on_product          varchar2(80),
  credit_limit             number(12,2),
  expiry_date              date,             
  fixed_value              number(12,2),   
  card_holder_name         varchar2(80),     
  card_holder_title        varchar2(80),     
  card_holder_tel          varchar2(20),     
  card_holder_salutation   varchar2(80),     
  card_holder_mobile       varchar2(20),     
  card_holder_email        varchar2(255),  
  waive_subsc_fee_flag     varchar2(1), 
  is_individual_card       varchar2(1),      
  emboss_flag              varchar2(1),      
  expiry_time              timestamp(6),   
  emboss_name_on_card      varchar2(1),      
  waive_issuance_fee_flag  varchar2(1),      
  offline_count            number(6),        
  offline_txn_amount       number(6,2),      
  offline_amount           number(6,2),  
  sms_expiry_flag          varchar2(1),      
  sms_topup_flag           varchar2(1), 
  cashplus                 number(19,2),     
  card_value               number(19,2)     
);


comment on table pmtb_issuance_req_card is 'table contains the info to create product after issuance request approved';
comment on column pmtb_issuance_req_card.product_type_id 		 is 'Product type of the added card in the issuance request';
comment on column pmtb_issuance_req_card.pmtb_issuance_req 		 is 'The no of issuance request of the added card';
comment on column pmtb_issuance_req_card.product_no 			 is 'Product no of the added card';
comment on column pmtb_issuance_req_card.balance_expiry_date 	 is 'Balance expiry date of the added card';
comment on column pmtb_issuance_req_card.waive_issuance_fee_flag is 'Indicator whether issuance fee waived';
comment on column pmtb_issuance_req_card.cashplus 				 is 'Initial cash plus of the added card ';
comment on column pmtb_issuance_req_card.card_value 			 is 'Initial card value of the added card';



--prepaid issuance request card promotion
create table pmtb_issu_req_card_promotion (
	pmtb_issuance_req_card 		number(19, 0) not null, 
	mstb_promotion_cash_plus 	varchar2(255 char) not null, 
	constraint pmpc_isreqcardpromo_card$promo primary key(pmtb_issuance_req_card, mstb_promotion_cash_plus)
);


alter table pmtb_issu_req_card_promotion add constraint pmfc_isreqcardpromo_promo_cp foreign key (mstb_promotion_cash_plus) 
references mstb_promotion_cashplus;
-- KMYoong : MISING INDEX, it cannot refer primary key index
-- Victor >> Added below:
create index pmix_isreqcardpromo__promo_cp on pmtb_issu_req_card_promotion (mstb_promotion_cash_plus);

alter table pmtb_issu_req_card_promotion add constraint pmfc_isreqcardpromo_isreq_card foreign key (pmtb_issuance_req_card) 
references pmtb_issuance_req_card;
-- KMYoong : no index need for above foregin key, it can refer to primary key index

comment on table pmtb_issu_req_card_promotion is 'table store the promotion that applied to the issuance cards';
comment on column pmtb_issu_req_card_promotion.pmtb_issuance_req_card 	is 'Request card no of PMTB_ISSUANCE_REQ_CARD';
comment on column pmtb_issu_req_card_promotion.mstb_promotion_cash_plus is 'Promo code of MSTB_PROMOTION_CASHPLUS';



--prepaid top up request
create table pmtb_top_up_req (
	req_no 		number(19,0) not null, 
	remarks 	varchar2(255 char), 
	credit_term varchar2(255 char), 
	constraint pmpc_tpreq__req_no primary key (req_no)
);

alter table pmtb_top_up_req drop column credit_term;
alter table pmtb_top_up_req add credit_term_plan_no number(10,0);
alter table pmtb_top_up_req add constraint pmfc_tpreq__req_no foreign key (req_no) 
references pmtb_prepaid_req;


alter table pmtb_top_up_req add constraint pmfc_tpreq__cterm_plan_no foreign key (credit_term_plan_no) 
references mstb_credit_term_master;
create index pmix_tpreq__cterm_plan_no on pmtb_top_up_req (credit_term_plan_no);

alter table pmtb_top_up_req add bmtb_invoice_header number(19,0);
alter table pmtb_top_up_req add total_amount number(19,2);
alter table pmtb_top_up_req drop column bmtb_invoice_header;
alter table pmtb_top_up_req drop column remarks;

comment on table pmtb_top_up_req is 'table contains the information while user create a prepaid top up request';
comment on column pmtb_top_up_req.credit_term_plan_no 	is 'Credit term that apply to the top up request';
comment on column pmtb_top_up_req.total_amount 			is 'The total amount that would be reflected in the invoice.';




--prepaid top up request card
create sequence pmsq_top_up_req_card minvalue 1 increment by 1 start with 1 cache 10 noorder nocycle ;

create table pmtb_top_up_req_card (
	req_card_no 			number(19,0) not null, 
	waive_top_up_fee_flag 	varchar2(255 char), 
	new_balance_expiry_date timestamp, 
	top_up_value 			number(19,2), 
	pmtb_top_up_req 		number(19,0), 
	pmtb_product 			number(19,0), 
	constraint pmpc_tpreq_req__card_no primary key (req_card_no)
);

alter table pmtb_top_up_req_card add constraint pmfc_tpreqcard_tpreq foreign key (pmtb_top_up_req) 
references pmtb_top_up_req ;
create index pmix_tureqcard__tu_req on pmtb_top_up_req_card (pmtb_top_up_req);

alter table pmtb_top_up_req_card add constraint pmfc_tpreqcard_product_no foreign key (pmtb_product) 
references pmtb_product ;
create index pmix_tureqcard__product_no on pmtb_top_up_req_card (pmtb_product);

alter table pmtb_top_up_req_card add top_up_cashplus number(19,2);
alter table pmtb_top_up_req_card add top_up_fee number(19,2);


comment on table pmtb_top_up_req_card is 'contains the info to top up card value, cash plus of product after top up request approved.';
comment on column pmtb_top_up_req_card.waive_top_up_fee_flag is 'Indicator whether top up fee waived';
comment on column pmtb_top_up_req_card.top_up_value 	is 'Top up value of the added cards';
comment on column pmtb_top_up_req_card.pmtb_top_up_req 	is 'The no of top up request of the added card';
comment on column pmtb_top_up_req_card.pmtb_product 	is 'Product no of the added cards';
comment on column pmtb_top_up_req_card.top_up_cashplus 	is 'Top up cash plus of the added cards';
comment on column pmtb_top_up_req_card.top_up_fee 		is 'Top up fee that applied to the added card';


--prepaid top up request card promotion
create table pmtb_top_up_req_card_promotion (
	pmtb_top_up_req_card 		number(19,0) not null, 
	mstb_promotion_cash_plus 	varchar2(255 char) not null, 
	constraint pmpc_tureqcardpromo_card$promo primary key(pmtb_top_up_req_card, mstb_promotion_cash_plus)
);

alter table pmtb_top_up_req_card_promotion add constraint pmfc_tpreqcardpromo_promo_cp foreign key (mstb_promotion_cash_plus) 
references mstb_promotion_cashplus ;
-- KMYoong : MISING INDEX, it cannot refer primary key index
-- Victor >> Added below:
create index pmix_tpreqcardpromo__promo_cp on pmtb_top_up_req_card_promotion (mstb_promotion_cash_plus);

alter table pmtb_top_up_req_card_promotion add constraint pmfc_tpreqcardpromo_tpreq_card foreign key (pmtb_top_up_req_card) 
references pmtb_top_up_req_card ;
-- KMYoong : no index need for above foregin key, it can refer to primary key index


comment on table pmtb_top_up_req_card_promotion is 'table store the promotion that applied to the top up cards';
comment on column pmtb_top_up_req_card_promotion.pmtb_top_up_req_card 		is 'Request card no of PMTB_TOP_UP_REQ_CARD';
comment on column pmtb_top_up_req_card_promotion.mstb_promotion_cash_plus 	is 'Promo code of MSTB_PROMOTION_CASHPLUS';


--prepaid transfer request
create table pmtb_transfer_req (
	req_no 					number(19,0) not null, 
	from_pmtb_product 		number(19,0), 
	to_pmtb_product 		number(19,0), 
	waive_transfer_fee_flag varchar2(255 char), 
	transfer_fee 			number(19,2), 
	remarks 				varchar2(255 char), 
	constraint pmpc_trreq__req_no primary key (req_no)
) ;


alter table pmtb_transfer_req add constraint pmfc_trreq_to_product_no foreign key (to_pmtb_product) 
references pmtb_product;
create index pmix_trreq__to_product on pmtb_transfer_req (to_pmtb_product);

alter table pmtb_transfer_req add constraint pmfc_trreq_fr_product_no foreign key (from_pmtb_product) 
references pmtb_product;
create index pmix_trreq__from_product on pmtb_transfer_req (from_pmtb_product);

alter table pmtb_transfer_req add constraint pmfc_trreq_req_no foreign key (req_no) 
references pmtb_prepaid_req;

alter table pmtb_transfer_req add txferable_card_value number(19,2);
alter table pmtb_transfer_req add txferable_cashplus number(19,2);
alter table pmtb_transfer_req add from_card_value number(19,2);
alter table pmtb_transfer_req add to_card_value number(19,2);
alter table pmtb_transfer_req add from_cashplus number(19,2);
alter table pmtb_transfer_req add to_cashplus number(19,2);

alter table pmtb_transfer_req drop column remarks;

alter table pmtb_transfer_req add transfer_card_value number(19,2);
alter table pmtb_transfer_req add transfer_cashplus number(19,2);
alter table pmtb_transfer_req add transfer_all_flag varchar2(20 char);


comment on table pmtb_transfer_req is 'table contains the information while user create a prepaid transfer request';
comment on column pmtb_transfer_req.from_pmtb_product 		is 'The product no of the card that being transferred out';
comment on column pmtb_transfer_req.to_pmtb_product 		is 'The product no of the card that being transferred in';
comment on column pmtb_transfer_req.waive_transfer_fee_flag is 'Indicator whether transfer fee waived';
comment on column pmtb_transfer_req.transfer_fee 			is 'Transfer fee';
comment on column pmtb_transfer_req.txferable_card_value 	is 'The maximum card value can be transferred out';
comment on column pmtb_transfer_req.txferable_cashplus 		is 'The maximum cash plus can be transferred out';
comment on column pmtb_transfer_req.from_card_value 		is 'Snapshot of the initial card value of the transferred out card';
comment on column pmtb_transfer_req.to_card_value 			is 'Snapshot of the initial card value of the transferred in card';
comment on column pmtb_transfer_req.from_cashplus 			is 'Snapshot of the initial cash plus of the transferred out card';
comment on column pmtb_transfer_req.to_cashplus 			is 'Snapshot of the initial cash plus of the transferred in card';
comment on column pmtb_transfer_req.transfer_card_value 	is 'The transfer card value between 2 cards ';
comment on column pmtb_transfer_req.transfer_cashplus 		is 'The transfer cash plus between 2 cards';
comment on column pmtb_transfer_req.transfer_all_flag 		is 'Indicator whether full amount was transferred';




--prepaid extend balance expire date request --- jtaruc from version 0.10 update date to timestamp of new_bal_exp_date
create table pmtb_ext_bal_exp_date_req (
	req_no 						number(19,0) not null, 
	new_bal_exp_date_dur_type 	varchar2(255 char), 
	new_bal_exp_date_dur_len 	number(10,0), 
	new_bal_exp_date 			timestamp, 
	pmtb_product 				number(19,0), 
	constraint pmpk_ebreq__req_no primary key (req_no)
);

alter table pmtb_ext_bal_exp_date_req add constraint pmfc_ebreq__req_no foreign key (req_no) 
references pmtb_prepaid_req;

alter table pmtb_ext_bal_exp_date_req add constraint pmfc_ebreq__product_no foreign key (pmtb_product) 
references pmtb_product;
create index pmix_ebreq__product_no on pmtb_ext_bal_exp_date_req (pmtb_product);

alter table pmtb_ext_bal_exp_date_req add old_bal_exp_date timestamp;

comment on table pmtb_ext_bal_exp_date_req is 'contains the information while user create a extend balance expiry date request';
comment on column pmtb_ext_bal_exp_date_req.new_bal_exp_date_dur_type 	is 'Duration type: DATE, DURATION';
comment on column pmtb_ext_bal_exp_date_req.new_bal_exp_date_dur_len 	is 'Duration length';
comment on column pmtb_ext_bal_exp_date_req.new_bal_exp_date 			is 'New balance expiry date';
comment on column pmtb_ext_bal_exp_date_req.pmtb_product 				is 'Applied Product that by the request';
comment on column pmtb_ext_bal_exp_date_req.old_bal_exp_date 			is 'Snapshot of initial balance expiry date';




--prepaid adjustment request
create table pmtb_adjustment_req (
	req_no 					number(19,0) not null, 
	adjust_value_amount 	number(19,2), 
	adjust_cashplus_amount 	number(19,2), 
	remarks varchar2(255 char), 
	pmtb_product number(19,0), 
	constraint pmpc_adreq__req_no primary key (req_no)
);

alter table pmtb_adjustment_req add constraint pmfc_adreq_req_no foreign key (req_no) 
references pmtb_prepaid_req;

alter table pmtb_adjustment_req add constraint pmfc_adreq_product_no foreign key (pmtb_product) 
references pmtb_product ;
create index pmix_adreq__product_no on pmtb_adjustment_req (pmtb_product);

alter table pmtb_adjustment_req add ori_value_amount number(19,2);
alter table pmtb_adjustment_req add ori_cashplus_amount number(19,2);
alter table pmtb_adjustment_req drop column remarks;
alter table pmtb_adjustment_req add adjust_value_txn_code number(10,0);
alter table pmtb_adjustment_req add adjust_cashplus_txn_code number(10,0);

alter table pmtb_adjustment_req add constraint pmfc_adreq_ad_cp_txn_code foreign key (adjust_cashplus_txn_code) 
references fmtb_transaction_code ;
create index pmix_adreq__ad_cp_txn on pmtb_adjustment_req (adjust_cashplus_txn_code);

alter table pmtb_adjustment_req add constraint pmfc_adreq_ad_vl_txn_code foreign key (adjust_value_txn_code) 
references fmtb_transaction_code ;
create index pmix_adreq__ad_vl_txn on pmtb_adjustment_req (adjust_value_txn_code);

alter table pmtb_adjustment_req add adjust_value_gst number(19,2);
alter table pmtb_adjustment_req add adjust_cashplus_gst number(19,2);


comment on table pmtb_adjustment_req is 'the information while user create a extend adjustment request';
comment on column pmtb_adjustment_req.adjust_value_amount 		is 'Adjust card value amount';
comment on column pmtb_adjustment_req.adjust_cashplus_amount 	is 'Adjust cash plus amount';
comment on column pmtb_adjustment_req.pmtb_product 				is 'Applied Product that by the request';
comment on column pmtb_adjustment_req.ori_value_amount 			is 'Snapshot of initial card value';
comment on column pmtb_adjustment_req.ori_cashplus_amount 		is 'Snapshot of initial cash plus';
comment on column pmtb_adjustment_req.adjust_value_txn_code 	is 'Transaction code of the adjust value';
comment on column pmtb_adjustment_req.adjust_cashplus_txn_code 	is 'Transaction code of the adjust cash plus';
comment on column pmtb_adjustment_req.adjust_value_gst 			is 'GST applied in the adjust value';
comment on column pmtb_adjustment_req.adjust_cashplus_gst 		is 'GST applied in the adjust cash plus';



--- version 0.03
--- 23 scripts
--prepaid transaction
create sequence pmsq_prepaid_card_txn minvalue 1 increment by 1 start with 1 cache 10 noorder nocycle ;

create table pmtb_prepaid_txn (
	txn_no 			number(19,0) not null, 
	amount 			number(19,2), 
	txn_type 		varchar2(255 char), 
	pmtb_product 	number(19,0), 
	remarks 		varchar2(255 char), 
	txn_date 		timestamp, 
	version 		number(10,0), 
	constraint pmpc_pretxn__txn_no primary key (txn_no)
);

alter table pmtb_prepaid_txn add constraint pmfc_pretxn_product_no foreign key (pmtb_product) 
references pmtb_product;

alter table pmtb_prepaid_txn add GST number(19,2);  
alter table pmtb_prepaid_txn add pmtb_prepaid_req number(19,0);
alter table pmtb_prepaid_txn add apply_card_value number(19,2);
alter table pmtb_prepaid_txn add apply_cashplus number(19,2);
alter table pmtb_prepaid_txn add glable_flag varchar2(10 char);

alter table pmtb_prepaid_txn add constraint pmfc_pretxn__req_no foreign key (pmtb_prepaid_req) 
references pmtb_prepaid_req;
create index pmix_precardtxn__pre_req on pmtb_prepaid_card_txn(pmtb_prepaid_req);

alter table pmtb_prepaid_txn add acquire_txn_no number(10,0);
alter table pmtb_prepaid_txn add constraint pmfc_pretxn__acq_txn_no foreign key (acquire_txn_no) 
references tmtb_acquire_txn;
create index pmix_precardtxn__ac_txn_no on pmtb_prepaid_card_txn(acquire_txn_no);

alter table pmtb_prepaid_txn add fmtb_transaction_code number(10,0); 
alter table pmtb_prepaid_txn add constraint pmfc_pretxn__txn_code foreign key (fmtb_transaction_code) 
references fmtb_transaction_code ;
create index pmix_precardtxn__txn_code on pmtb_prepaid_card_txn(fmtb_transaction_code);

alter table pmtb_prepaid_txn rename to pmtb_prepaid_card_txn;


create index pmix_precardtxn__prd_no$txn_dt on pmtb_prepaid_card_txn (pmtb_product, txn_date);
create index pmix_precardtxn__txn_type on pmtb_prepaid_card_txn (txn_type);
-- KMYOONG : Why need below Index ?
-- Victor >> Inside system we need to query the table by pmtb_product and txn_no quite frequently
create index pmix_precardtxn__pr_no$txn_no on pmtb_prepaid_card_txn (pmtb_product, txn_no);




comment on table pmtb_prepaid_card_txn  is 'store each prepaid card transaction that keep track the changes of card value or cash plus of the card';
comment on column pmtb_prepaid_card_txn.amount 					is 'Amount of transaction';
comment on column pmtb_prepaid_card_txn.txn_type 				is 'ISSUE - Issue TOP_UP - Top Up ADJ_VL - Adjust Value ADJ_CP - Adjust Cash Plus EX_TR_IN - External Transfer In EX_TR_OUT - External Transfer Out IN_TR_IN -Internal Transfer Out IN_TR_OUT - Internal Transfer Out TR_FEE - Transfer Fee TRIP - Trip EDIT_TRIP - Edit Trip VOID_TRIP - Void Trip RP_FEE - Replacement Fee FF_VL - Forfeited Value FF_CP - Forfeited Cash Plus FF_ADJ_VL- Forfeit Adjust Value FF_ADJ_CP- Forfeit Adjust Cash Plus';
comment on column pmtb_prepaid_card_txn.pmtb_product 			is 'Product no that own the transaction';
comment on column pmtb_prepaid_card_txn.gst 					is 'GST applied for the transaction';
comment on column pmtb_prepaid_card_txn.pmtb_prepaid_req 		is 'The number of prepaid request that create the prepaid card transaction after requested being approved.';
comment on column pmtb_prepaid_card_txn.apply_card_value 		is 'The increased/decrease in card value in this transaction ';
comment on column pmtb_prepaid_card_txn.apply_cashplus 			is 'The increased/decrease in cash plus in this transaction';
comment on column pmtb_prepaid_card_txn.glable_flag 			is 'Indicator whether this transaction should be GL';
comment on column pmtb_prepaid_card_txn.acquire_txn_no 			is 'Trip transaction number for those TRIP, EDIT_TRIP, VOID_TRIP type transaction';
comment on column pmtb_prepaid_card_txn.fmtb_transaction_code 	is 'Transaction code for this transaction';



alter table pmtb_product_type add top_up_fee number(19,2);
alter table pmtb_product_type add transfer_fee number(19,2);
alter table pmtb_product_type add default_balance_exp_months number(2);

alter table pmtb_product add cashplus number(19,2);
alter table bmtb_invoice_header modify  (invoice_format varchar2(2));


  
--create issuance invoice txn
create sequence bmsq_issuance_invoice_txn;

create table bmtb_issuance_invoice_txn (
	txn_no 					number(19,0) not null, 
	amount 					number(19,2), 
	discount 				number(19,2), 
	gst 					number(19,2), 
	txn_type 				varchar2(255 char), 
	description 			varchar2(255 char), 
	pmtb_issuance_req_card 	number(19,0), 
	pmtb_issuance_req 		number(19,0), 
	bmtb_invoice_summary 	number(19,0), 
	version 				number(10,0), 
	constraint bmpc_isinvtxn__txn_no primary key (txn_no)
);

alter table bmtb_issuance_invoice_txn add constraint bmfc_isinvtxn__is_req_card foreign key (pmtb_issuance_req_card) 
references pmtb_issuance_req_card ;
create index bmix_invtxn__is_req_card on bmtb_issuance_invoice_txn  (pmtb_issuance_req_card);

alter table bmtb_issuance_invoice_txn add constraint bmfc_isinvtxn_inv_summary_no foreign key (bmtb_invoice_summary) 
references bmtb_invoice_summary ;
--KMYOONG : MISSING INDEX
-- Victor >> Added below:
create index bmix_isinvtxn__inv_summary_no on bmtb_issuance_invoice_txn (bmtb_invoice_summary);

alter table bmtb_issuance_invoice_txn add constraint bmfc_isinvtxn_is_req_no foreign key (pmtb_issuance_req) 
references pmtb_issuance_req ;
create index bmix_invtxn__is_req on bmtb_issuance_invoice_txn  (pmtb_issuance_req);

alter table bmtb_issuance_invoice_txn add txn_code number(10,0) ;

alter table bmtb_issuance_invoice_txn add constraint bmfc_isinvtxn_txn_code foreign key (txn_code) 
references fmtb_transaction_code ;

comment on table bmtb_issuance_invoice_txn is 'contains the transaction info of the generated invoice after issuance request approved';
comment on column bmtb_issuance_invoice_txn.amount 					is 'Amount of a transaction in the invoice';
comment on column bmtb_issuance_invoice_txn.discount 				is 'Discount applied for the invoice transaction';
comment on column bmtb_issuance_invoice_txn.gst 					is 'GST applied to the transaction';
comment on column bmtb_issuance_invoice_txn.txn_type 				is 'P_ISVL - Prepaid Issue Value P_ISCP - Prepaid Issue Cash Plus P_TUVL - Prepaid Top Up Value P_TUCP - Prepaid Top  Up Cash Plus P_DC - Prepaid Discount P_IF - Prepaid Issuance Fee P_IFW - Prepaid Issuance Fee Waival P_TF - Prepaid Top Up Fee P_TFW - Prepaid Top Up Fee Waival ';
comment on column bmtb_issuance_invoice_txn.pmtb_issuance_req_card 	is 'Request card no of the issuance request';
comment on column bmtb_issuance_invoice_txn.pmtb_issuance_req 		is 'Request no of the issuance request';
comment on column bmtb_issuance_invoice_txn.bmtb_invoice_summary 	is 'Invoice summary no';
comment on column bmtb_issuance_invoice_txn.txn_code 				is 'Transaction code of the invoice transaction';


--create top up invoice txn
create sequence bmsq_top_up_invoice_txn ; 

create table bmtb_top_up_invoice_txn (
	txn_no 					number(19,0) not null, 
	amount 					number(19,2), 
	discount 				number(19,2), 
	gst 					number(19,2), 
	txn_type 				varchar2(255 char), 
	description 			varchar2(255 char), 
	pmtb_top_up_req_card 	number(19,0), 
	bmtb_invoice_summary 	number(19,0), 
	version 				number(10,0), 
	constraint bmpc_tpinvtxn__txn_no primary key (txn_no)
);

alter table bmtb_top_up_invoice_txn add constraint bmfc_tpinvtxn_inv_summary_no foreign key (bmtb_invoice_summary) 
references bmtb_invoice_summary ;
-- KMYoong : MISSING INDEX
-- Victor >> Added below:
create index bmix_tpinvtxn__inv_summary_no on bmtb_top_up_invoice_txn (bmtb_invoice_summary);

alter table bmtb_top_up_invoice_txn add constraint bmfc_tpinvtxn_tp_req_card foreign key (pmtb_top_up_req_card) 
references pmtb_top_up_req_card ;
create index bmix_invtxn__tu_req_card on bmtb_top_up_invoice_txn   (pmtb_top_up_req_card);


comment on table bmtb_top_up_invoice_txn  is 'the transaction info of the generated invoice after top up request approved';
comment on column bmtb_top_up_invoice_txn.amount 				is 'Amount of a transaction in the invoice';
comment on column bmtb_top_up_invoice_txn.discount 				is 'Discount applied for the invoice transaction';
comment on column bmtb_top_up_invoice_txn.gst 					is 'GST applied to the transaction';
comment on column bmtb_top_up_invoice_txn.txn_type 				is 'P_ISVL - Prepaid Issue Value P_ISCP - Prepaid Issue Cash Plus P_TUVL - Prepaid Top Up Value P_TUCP - Prepaid Top  Up Cash Plus P_DC - Prepaid Discount P_IF - Prepaid Issuance Fee P_IFW - Prepaid Issuance Fee Waival P_TF - Prepaid Top Up Fee P_TFW - Prepaid Top Up Fee Waival ';
comment on column bmtb_top_up_invoice_txn.pmtb_top_up_req_card 	is 'Request card no of the top up request';
comment on column bmtb_top_up_invoice_txn.bmtb_invoice_summary 	is 'Invoice summary no';




--modification on product
alter table pmtb_product add card_value number(19,2);

alter table fmtb_gl_log_detail add prepaid_card_txn_no number(19,2);
alter table fmtb_gl_log_detail add constraint fmfc_gllogdl_pre_txn_no foreign key (prepaid_card_txn_no) 
references pmtb_prepaid_txn;
create index fmix_gllogdl__pre_card_txn on fmtb_gl_log_detail(prepaid_card_txn_no);

comment on column fmtb_gl_log_detail.prepaid_card_txn_no is 'To log the prepaid card transaction has been GL';




alter table bmtb_draft_inv_summary  modify summary_type varchar2(10); 
alter table bmtb_draft_inv_detail  modify invoice_detail_type varchar2(10);

alter table bmtb_invoice_summary  modify summary_type varchar2(10);
alter table bmtb_invoice_detail  modify invoice_detail_type varchar2(10);


alter table bmtb_invoice_header add prepaid_flag varchar(2) default 'N'; 

alter table bmtb_draft_inv_header add prepaid_flag varchar(2) default 'N';
comment on column bmtb_draft_inv_header.prepaid_flag is 'indicator whether the draft invoice was generated for prepaid products';

alter table pmtb_product add expired_flag varchar2(10) default 'N';

alter table fmtb_transaction_code modify txn_type varchar2(10);

create sequence pmsq_balance_forfeiture;


create table pmtb_balance_forfeiture (
	balance_forfeiture_no 	number(19,0) not null, 
	forfeit_card_value 		number(19,2), 
	forfeit_cashplus 		number(19,2), 
	forfeited_date 			timestamp, 
	product_no 				number(19,0),
	constraint pmpc_nalforft__bal_forf_no primary key (balance_forfeiture_no)
);

alter table pmtb_balance_forfeiture add version number(10,0);

alter table pmtb_balance_forfeiture add constraint pmfc_balfort_product_no foreign key (product_no) 
references pmtb_product;
create index pmix_blforfeit__product_no on pmtb_balance_forfeiture (product_no);

alter table pmtb_balance_forfeiture add forfeit_card_value_gst_rate number(19,2);

alter table pmtb_balance_forfeiture add forfeit_cashplus_gst_rate number(19,2);


comment on table pmtb_balance_forfeiture  is 'contains the balance forfeiture info of the card with its card balance being forfeited';
comment on column pmtb_balance_forfeiture.forfeit_card_value 			is 'Forfeited card value';
comment on column pmtb_balance_forfeiture.forfeit_cashplus 				is 'Forfeited cash plus';
comment on column pmtb_balance_forfeiture.forfeited_date 				is 'Forfeited date';
comment on column pmtb_balance_forfeiture.product_no 					is 'Product no of the card with its balance being forfeited ';
comment on column pmtb_balance_forfeiture.forfeit_card_value_gst_rate 	is 'GST rate applied at the forfeited balance';
comment on column pmtb_balance_forfeiture.forfeit_cashplus_gst_rate 	is 'GST rate applied at the forfeited cash plus';




create table pmtb_card_no_sequence
  (
    seq_id          number(10,0) not null,
    version         number(10,0) not null,
    number_of_digit number(10,0),
    bin_range       varchar2(255 char),
    sub_bin_range   varchar2(255 char),
    count           number(19,2),
    constraint pmpc_cardnoseq__seq_id primary key (seq_id),
    constraint pmuc_cardnoseq__br$nod$sbr unique (bin_range, number_of_digit,sub_bin_range)
  );

comment on table pmtb_card_no_sequence is 'current sequence number of cards by number of digit, bin range and sub bin range field ';
comment on column pmtb_card_no_sequence.count is 'Current sequence number of cards';


create sequence pmsq_card_no_sequence ;

  
alter table bmtb_invoice_header add delivery_charge_txn_code number(10,0) ;
alter table bmtb_invoice_header add constraint bmfc_invhdr_dely_cr_txn_code foreign key (delivery_charge_txn_code) 
references fmtb_transaction_code;
-- KMYoong : MISSING INDEX
-- Victor >> Added below:
create index bmix_invhdr__dely_cr_txn_code on bmtb_invoice_header (delivery_charge_txn_code);


alter table bmtb_invoice_header add cancel_dt timestamp;

comment on column bmtb_invoice_header.prepaid_flag 				is 'Indicator whether the invoice was generated for prepaid products';
comment on column bmtb_invoice_header.delivery_charge_txn_code 	is 'Delivery charge transaction code for the invoice';
comment on column bmtb_invoice_header.cancel_dt 				is 'Date when the invoice being cancelled';



alter table bmtb_payment_receipt_detail add prepaid_card_txn_no number(19,0);
alter table bmtb_payment_receipt_detail add constraint bmfc_pyrecptdl_card_txn_no foreign key (prepaid_card_txn_no) 
references pmtb_prepaid_card_txn;
create index bmix_pyreceiptdl__pre_card_txn on bmtb_payment_receipt_detail(prepaid_card_txn_no);


alter table bmtb_payment_receipt_detail modify (invoice_header_no decimal null);
alter table bmtb_payment_receipt_detail modify (invoice_detail_no decimal null);
--Victor >> Changes from decimal to number
alter table bmtb_payment_receipt_detail modify (invoice_header_no number);
alter table bmtb_payment_receipt_detail modify (invoice_detail_no number);

comment on column bmtb_payment_receipt_detail.prepaid_card_txn_no is 'To keep reference that the payment receipt created is for Replacement Fee, Transfer Fee prepaid transaction.';



alter table bmtb_payment_receipt add prepaid_flag varchar(2) default 'N';
alter table tmtb_txn_review_req add admin_fee_value number(12,2) ;
alter table tmtb_txn_review_req add gst_value number(12,2) ;
alter table mstb_promotion_cashplus drop column status;
alter table mstb_master_table modify (master_type varchar2(10));
alter table pmtb_product add balance_forfeited_flag varchar2(10 char);




comment on column pmtb_product.cashplus 			is 'Cash Plus';
comment on column pmtb_product.card_value 			is 'Card Value';
comment on column pmtb_product.expired_flag 		is 'Indicator whether the product has been expired';
comment on column pmtb_product.balance_expiry_date 	is 'Balance expiry date';

comment on column pmtb_product_type.top_up_fee 					is 'Top up fee applied when the product of the product type being top up';
comment on column pmtb_product_type.transfer_fee 				is 'Transfer fee applied when the product of the product type being transferred';
comment on column pmtb_product_type.default_balance_exp_months 	is 'Default balance expiry month';

comment on column tmtb_txn_review_req.admin_fee_value 	is 'Admin Fee value';
comment on column tmtb_txn_review_req.gst_value 		is 'GST value';






spool off



