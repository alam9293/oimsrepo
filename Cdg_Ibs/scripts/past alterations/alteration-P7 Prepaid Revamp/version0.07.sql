insert into mstb_master_table (master_no, master_type,master_code,interface_mapping_value,master_value,master_status) values (mstb_master_table_sq1.nextval, 'PM','DR',null ,'DIRECT RECEIPT','A');


alter table pmtb_prepaid_txn rename to pmtb_prepaid_card_txn;

alter table FMTB_GL_LOG_DETAIL rename column PREPAID_TXN_NO to PREPAID_CARD_TXN_NO;

alter table BMTB_PAYMENT_RECEIPT_DETAIL add PREPAID_CARD_TXN_NO number(19,0);

alter table BMTB_PAYMENT_RECEIPT_DETAIL add constraint FKB68E32D7F8F923A9 foreign key (PREPAID_CARD_TXN_NO) 
references PMTB_PREPAID_TXN;


ALTER TABLE BMTB_PAYMENT_RECEIPT ADD prepaid_flag varchar(2) DEFAULT 'N';

alter table BMTB_INVOICE_HEADER add CANCEL_DT timestamp;


alter table pmtb_adjustment_req add ADJUST_VALUE_GST number(19,2);

alter table pmtb_adjustment_req add ADJUST_CASHPLUS_GST number(19,2);


alter table PMTB_BALANCE_FORFEITURE rename column card_value to forfeit_card_value;

alter table PMTB_BALANCE_FORFEITURE rename column cashplus to forfeit_cashplus;

alter table PMTB_BALANCE_FORFEITURE rename column card_value_gst_rate to forfeit_card_value_gst_rate;

alter table PMTB_BALANCE_FORFEITURE rename column cashplus_gst_rate to forfeit_cashplus_gst_rate;










