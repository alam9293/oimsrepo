--modification as at 3-April-2014

--rename cashback to cashplus
alter table mstb_promotion_cashplus rename column cashback to cashplus;

alter table pmtb_issuance_req_card rename column cashback to cashplus;

alter table pmtb_top_up_req_card rename column top_up_cashback to top_up_cashplus;

alter table pmtb_product rename column cashback to cashplus;

alter table pmtb_adjustment_req rename column adjust_cashback_amount to adjust_cashplus_amount;


--populate transaction code of Admin Fee GST
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO,
    ENTITY_NO,
    PRODUCT_TYPE_ID,
    TXN_CODE,
    DESCRIPTION,
    TXN_TYPE,
    GL_CODE,
    COST_CENTRE,
    DISCOUNTABLE,
    DISCOUNT_GL_CODE,
    DISCOUNT_COST_CENTRE,
    EFFECTIVE_DATE,
    TAX_TYPE,
    VERSION,
    IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval,
    '1',
     null,
    'AFPPG',
    'PREPAID ADMIN FEE GST',
    'AF',
    '0',
    'XXXXXXXX',
    'N',
    '',
    '',
    DATE '2009-01-01',
    45,0,
    'Y'
  );

  
--modification for prepaid txn 
alter table pmtb_prepaid_txn add GST number(19,2);  
  
--drop card txn
drop table PMTB_ISSUANCE_REQ_CARD_TXN;

drop table PMTB_TOP_UP_REQ_CARD_TXN;

drop sequence PMTB_ISSUANCE_REQ_CARD_TXN_SQ1;

drop sequence PMTB_TOP_UP_REQ_CARD_TXN_SQ1;
  
--create issuance invoice txn
create sequence BMTB_ISSUANCE_INVOICE_TXN_SQ1;

create table BMTB_ISSUANCE_INVOICE_TXN (TXN_NO number(19,0) not null, AMOUNT number(19,2), 
DISCOUNT number(19,2), GST number(19,2), TXN_TYPE varchar2(255 char), REMARKS varchar2(255 
char), PMTB_ISSUANCE_REQ_CARD number(19,0), PMTB_ISSUANCE_REQ number(19,0), BMTB_INVOICE_SUMMARY 
number(19,0), VERSION number(10,0), primary key (TXN_NO));

alter table BMTB_ISSUANCE_INVOICE_TXN add constraint FK8B8BCBA62C406AC foreign key (PMTB_ISSUANCE_REQ_CARD) 
references PMTB_ISSUANCE_REQ_CARD ;

alter table BMTB_ISSUANCE_INVOICE_TXN add constraint FK8B8BCBA8798A3FD foreign key (BMTB_INVOICE_SUMMARY) 
references BMTB_INVOICE_SUMMARY ;

alter table BMTB_ISSUANCE_INVOICE_TXN add constraint FK8B8BCBA4F6FE2E9 foreign key (PMTB_ISSUANCE_REQ) 
references PMTB_ISSUANCE_REQ ;

--create top up invoice txn
create sequence BMTB_TOP_UP_INVOICE_TXN_SQ1 ; 

create table BMTB_TOP_UP_INVOICE_TXN (TXN_NO number(19,0) not null, AMOUNT number(19,2), DISCOUNT 
number(19,2), GST number(19,2), TXN_TYPE varchar2(255 char), REMARKS varchar2(255 char), PMTB_TOP_UP_REQ_CARD 
number(19,0), BMTB_INVOICE_SUMMARY number(19,0), VERSION number(10,0), primary key (TXN_NO));

alter table BMTB_TOP_UP_INVOICE_TXN add constraint FKACFA84C48798A3FD foreign key (BMTB_INVOICE_SUMMARY) 
references BMTB_INVOICE_SUMMARY ;

alter table BMTB_TOP_UP_INVOICE_TXN add constraint FKACFA84C44231F3BD foreign key (PMTB_TOP_UP_REQ_CARD) 
references PMTB_TOP_UP_REQ_CARD ;

--modification on product
alter table pmtb_product add card_value NUMBER(19,2);

--temporary set all existing prepaid product's value and cashplus to zero, this value will be replaced during migration
UPDATE pmtb_product
SET cashplus=0, card_value=0
WHERE product_no IN
  (
    SELECT product_no
    FROM pmtb_product a
    LEFT JOIN pmtb_product_type b
    ON a.product_type_id=b.product_type_id
    WHERE b.prepaid     ='Y'
  );

--populate all balance expiry date of existing prepaid product same with card expiry date
update pmtb_product a
SET a.balance_expiry_date=a.expiry_time
WHERE product_no IN
  (
    SELECT product_no
    FROM pmtb_product a
    LEFT JOIN pmtb_product_type b
    ON a.product_type_id=b.product_type_id
    where b.prepaid     ='Y'
  );
  
--modification on transfer req table
alter table PMTB_TRANSFER_REQ add TXFERABLE_CARD_VALUE number(19,2);

alter table PMTB_TRANSFER_REQ add TXFERABLE_CASHPLUS number(19,2);

alter table PMTB_TRANSFER_REQ add FROM_CARD_VALUE number(19,2);

alter table PMTB_TRANSFER_REQ add TO_CARD_VALUE number(19,2);

alter table PMTB_TRANSFER_REQ add FROM_CASHPLUS number(19,2);

alter table PMTB_TRANSFER_REQ add TO_CASHPLUS number(19,2);

--modification on prepaid req table
alter table pmtb_prepaid_req drop column approval_status;

--modification on adjustment req table
alter table PMTB_ADJUSTMENT_REQ add ORI_VALUE_AMOUNT number(19,2);

alter table PMTB_ADJUSTMENT_REQ add ORI_CASHPLUS_AMOUNT number(19,2);

--master table for balance expiry grace period
insert into mstb_master_table (master_no,master_type,master_code,interface_mapping_value,master_value,master_status,version) 
values (mstb_master_table_sq1.nextval,'BLGP','GP',null, '3', 'A',0);


