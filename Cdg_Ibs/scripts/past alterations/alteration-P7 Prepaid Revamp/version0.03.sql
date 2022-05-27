--prepaid transaction
CREATE SEQUENCE "PMTB_PREPAID_TXN_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

create table PMTB_PREPAID_TXN (TXN_NO number(19,0) not null, AMOUNT number(19,2), TXN_TYPE 
varchar2(255 char), PMTB_PRODUCT number(19,0), REMARKS varchar2(255 char), CREATED_DT timestamp, 
VERSION number(10,0), primary key (TXN_NO));

alter table PMTB_PREPAID_TXN add constraint FK1CCE0CC637CA0E22 foreign key (PMTB_PRODUCT) references 
PMTB_PRODUCT;

--issuance request card txn

CREATE SEQUENCE "PMTB_ISSUANCE_REQ_CARD_TXN_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

create table PMTB_ISSUANCE_REQ_CARD_TXN (TXN_NO number(19,2) not null, AMOUNT number(19,2), 
TXN_TYPE varchar2(255 char), PMTB_ISSUANCE_REQ_CARD number(19,2), VERSION number(10,0), primary 
key (TXN_NO)) ;


alter table PMTB_ISSUANCE_REQ_CARD_TXN add constraint FKEBF19C8C62C406AC foreign key (PMTB_ISSUANCE_REQ_CARD) 
references PMTB_ISSUANCE_REQ_CARD ;


--top up request card txn
CREATE SEQUENCE "PMTB_TOP_UP_REQ_CARD_TXN_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

create table PMTB_TOP_UP_REQ_CARD_TXN (TXN_NO number(19,2) not null, AMOUNT number(19,2), TXN_TYPE 
varchar2(255 char), PMTB_TOP_UP_REQ_CARD number(19,2), VERSION number(10,0), primary key (TXN_NO)) ;

alter table PMTB_TOP_UP_REQ_CARD_TXN add constraint FK7E1C1A424231F3BD foreign key (PMTB_TOP_UP_REQ_CARD) 
references PMTB_TOP_UP_REQ_CARD ;


--manage product types
alter table PMTB_PRODUCT_TYPE add TOP_UP_FEE number(19,2);

alter table PMTB_PRODUCT_TYPE add TRANSFER_FEE number(19,2);

alter table PMTB_PRODUCT_TYPE add DEFAULT_BALANCE_EXP_MONTHS number(2);


--populate default data for product types
update PMTB_PRODUCT_TYPE set top_up_fee=0, transfer_fee=0, default_balance_exp_months=3;


--modification as on 31-March-2014
alter table PMTB_ISSUANCE_REQ add TOTAL_AMOUNT number(19,2);

alter table PMTB_TOP_UP_REQ add TOTAL_AMOUNT number(19,2);

alter table pmtb_product add CASHBACK number(19,2);

alter table bmtb_invoice_header modify  (invoice_format varchar2(2));


alter table PMTB_PREPAID_REQ add BMTB_INVOICE_HEADER number(19,0) 

alter table PMTB_PREPAID_REQ add constraint FK1CCE02FAA3389649 foreign key (BMTB_INVOICE_HEADER) 
references BMTB_INVOICE_HEADER 

alter table PMTB_TOP_UP_REQ drop column BMTB_INVOICE_HEADER;

alter table PMTB_ISSUANCE_REQ drop column BMTB_INVOICE_HEADER;

alter table PMTB_PREPAID_REQ add APPROVAL_REQUIRED varchar2(255 char);

alter table PMTB_PREPAID_REQ add APPROVAL_STATUS varchar2(255 char);

alter table PMTB_ISSUANCE_REQ_CARD_TXN add REMARKS varchar2(255 char);

alter table PMTB_TOP_UP_REQ_CARD_TXN add REMARKS varchar2(255 char);


