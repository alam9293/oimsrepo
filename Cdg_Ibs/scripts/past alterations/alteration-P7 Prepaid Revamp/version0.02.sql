--promotion cashplus
create table MSTB_PROMOTION_CASHPLUS (PROMO_CODE varchar2(255 char) not null, CREATED_BY varchar2(255 
char), CREATED_DT timestamp, UPDATED_BY varchar2(255 char), UPDATED_DT timestamp, 
VERSION number(10,0), EFFECTIVE_DT_FROM timestamp, EFFECTIVE_DT_TO timestamp, CASHBACK number(19,2), 
REMARKS varchar2(255 char), STATUS varchar2(255 char), primary key (PROMO_CODE));

--pmtb product
alter table pmtb_product add BALANCE_EXPIRY_DATE DATE;

--prepaid common request
CREATE SEQUENCE "PMTB_PREPAID_REQ_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;


create table PMTB_PREPAID_REQ (REQ_NO number(19,0) not null, ACCOUNT_NO number(10,0), REQUESTOR 
number(19,0), REQUEST_DATE timestamp, STATUS varchar2(255 char), REQUEST_TYPE varchar2(255 
char), CREATED_BY varchar2(255 char), CREATED_DT timestamp, UPDATED_BY varchar2(255 char), 
UPDATED_DT timestamp, VERSION number(10,0), primary key (REQ_NO));

alter table PMTB_PREPAID_REQ add constraint FK1CCE02FA6A17C549 foreign key (ACCOUNT_NO) references 
AMTB_ACCOUNT;

alter table PMTB_PREPAID_REQ add constraint FK1CCE02FA4573A6ED foreign key (REQUESTOR) references 
SATB_USER;


--prepaid issuance request
create table PMTB_ISSUANCE_REQ (REQ_NO number(19,0) not null, DISCOUNT number(19,2), DELIVERY_CHARGE 
number(19,2), REMARKS varchar2(255 char), CREDIT_TERM varchar2(255 char), primary key (REQ_NO));


alter table PMTB_ISSUANCE_REQ add BMTB_INVOICE_HEADER number(19,0) 

alter table pmtb_issuance_req drop column credit_term; 

alter table PMTB_ISSUANCE_REQ add CREDIT_TERM_PLAN_NO number(10,0);


alter table PMTB_ISSUANCE_REQ add constraint FK22B7E92E2AB490D9 foreign key (REQ_NO) references 
PMTB_PREPAID_REQ;


--prepaid issuance request card
CREATE SEQUENCE "PMTB_ISSUANCE_REQ_CARD_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

create table PMTB_ISSUANCE_REQ_CARD (REQ_CARD_NO number(19,0) not null, NAME_ON_CARD varchar2(255 
char), POSITION varchar2(255 char), MOBILE varchar2(255 char), TELEPHONE varchar2(255 char), 
EMAIL varchar2(255 char), EXPIRY_DATE_TIME date, SMS_EXPIRY_FLAG varchar2(255 char), SMS_TOPUP_FLAG 
varchar2(255 char), VALUE number(19,2), VALUE_EXPIRY_DATE date, CASHBACK number(19,2),
PRODUCT_TYPE_ID varchar2(255 char), ACCOUNT_NO number(10,0), PMTB_ISSUANCE_REQ 
number(19,0), primary key (REQ_CARD_NO));

alter table PMTB_ISSUANCE_REQ_CARD add ISSUANCE_FEE number(19,2);

alter table PMTB_ISSUANCE_REQ_CARD add constraint FKE4AACF816A17C549 foreign key (ACCOUNT_NO) 
references AMTB_ACCOUNT;

alter table PMTB_ISSUANCE_REQ_CARD add constraint FKE4AACF814DF4E111 foreign key (PRODUCT_TYPE_ID) 
references PMTB_PRODUCT_TYPE;

alter table PMTB_ISSUANCE_REQ_CARD add constraint FKE4AACF814F6FE2E9 foreign key (PMTB_ISSUANCE_REQ) 
references PMTB_ISSUANCE_REQ;

alter table PMTB_ISSUANCE_REQ_CARD add WAIVE_ISSUANCE_FEE_FLAG varchar2(64 CHAR);

alter table PMTB_ISSUANCE_REQ add constraint FK22B7E92E546C234B foreign key (CREDIT_TERM_PLAN_NO) 
references MSTB_CREDIT_TERM_MASTER;


--prepaid issuance request card promotion
create table PMTB_ISSU_REQ_CARD_PROMOTION (PMTB_ISSUANCE_REQ_CARD number(19, 0) not null, 
MSTB_PROMOTION_CASH_PLUS varchar2(255 char) not null);

alter table PMTB_ISSU_REQ_CARD_PROMOTION add constraint FKEF93B12C4FAC095 foreign key (MSTB_PROMOTION_CASH_PLUS) 
references MSTB_PROMOTION_CASHPLUS;

alter table PMTB_ISSU_REQ_CARD_PROMOTION add constraint FKEF93B12C62C406AC foreign key (PMTB_ISSUANCE_REQ_CARD) 
references pmtb_issuance_req_card;


--prepaid top up request
create table PMTB_TOP_UP_REQ (REQ_NO number(19,0) not null, REMARKS varchar2(255 
char), CREDIT_TERM varchar2(255 char), primary key (REQ_NO)) ;

alter table pmtb_top_up_req drop column credit_term;

alter table PMTB_TOP_UP_REQ add CREDIT_TERM_PLAN_NO number(10,0);


alter table PMTB_TOP_UP_REQ add constraint FK1A9506B82AB490D9 foreign key (REQ_NO) references 
PMTB_PREPAID_REQ;


alter table PMTB_TOP_UP_REQ add constraint FK1A9506B8546C234B foreign key (CREDIT_TERM_PLAN_NO) 
references MSTB_CREDIT_TERM_MASTER;

alter table PMTB_TOP_UP_REQ add BMTB_INVOICE_HEADER number(19,0);



--prepaid top up request card
CREATE SEQUENCE "PMTB_TOP_UP_REQ_CARD_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

create table PMTB_TOP_UP_REQ_CARD (REQ_CARD_NO number(19,0) not null, WAIVE_TOP_UP_FEE_FLAG 
varchar2(255 char), NEW_BALANCE_EXPIRY_DATE timestamp, TOP_UP_VALUE number(19,2), PMTB_TOP_UP_REQ 
number(19,0), PMTB_PRODUCT number(19,0), primary key (REQ_CARD_NO));

alter table PMTB_TOP_UP_REQ_CARD add constraint FKB6F49837FC74360E foreign key (PMTB_TOP_UP_REQ) 
references PMTB_TOP_UP_REQ ;

alter table PMTB_TOP_UP_REQ_CARD add constraint FKB6F4983737CA0E22 foreign key (PMTB_PRODUCT) 
references PMTB_PRODUCT ;

alter table PMTB_TOP_UP_REQ_CARD add TOP_UP_CASHBACK number(19,2);

alter table PMTB_TOP_UP_REQ_CARD add TOP_UP_FEE number(19,2);


--prepaid top up request card promotion
create table PMTB_TOP_UP_REQ_CARD_PROMOTION (PMTB_TOP_UP_REQ_CARD number(19,0) not null, MSTB_PROMOTION_CASH_PLUS 
varchar2(255 char) not null) ;

alter table PMTB_TOP_UP_REQ_CARD_PROMOTION add constraint FK7AEF067B4FAC095 foreign key (MSTB_PROMOTION_CASH_PLUS) 
references MSTB_PROMOTION_CASHPLUS ;


alter table PMTB_TOP_UP_REQ_CARD_PROMOTION add constraint FK7AEF067B4231F3BD foreign key (PMTB_TOP_UP_REQ_CARD) 
references PMTB_TOP_UP_REQ_CARD ;


--prepaid transfer request
create table PMTB_TRANSFER_REQ (REQ_NO number(19,0) not null, FROM_PMTB_PRODUCT number(19,0), 
TO_PMTB_PRODUCT number(19,0), WAIVE_TRANSFER_FEE_FLAG varchar2(255 char), TRANSFER_FEE number(19,2), 
REMARKS varchar2(255 char), primary key (REQ_NO)) ;


alter table PMTB_TRANSFER_REQ add constraint FK523084BE61000966 foreign key (TO_PMTB_PRODUCT) 
references PMTB_PRODUCT ;

alter table PMTB_TRANSFER_REQ add constraint FK523084BE729EA497 foreign key (FROM_PMTB_PRODUCT) 
references PMTB_PRODUCT ;

alter table PMTB_TRANSFER_REQ add constraint FK523084BE2AB490D9 foreign key (REQ_NO) references 
PMTB_PREPAID_REQ;


--prepaid extend balance expire date request
create table PMTB_EXT_BAL_EXP_DATE_REQ (REQ_NO number(19,0) not null, NEW_BAL_EXP_DATE_DUR_TYPE 
varchar2(255 char), NEW_BAL_EXP_DATE_DUR_LEN number(10,0), NEW_BAL_EXP_DATE date, PMTB_PRODUCT 
number(19,0), primary key (REQ_NO));


alter table PMTB_EXT_BAL_EXP_DATE_REQ add constraint FK1F6050932AB490D9 foreign key (REQ_NO) 
references PMTB_PREPAID_REQ;


alter table PMTB_EXT_BAL_EXP_DATE_REQ add constraint FK1F60509337CA0E22 foreign key (PMTB_PRODUCT) 
references PMTB_PRODUCT;


--prepaid adjustment request
create table PMTB_ADJUSTMENT_REQ (REQ_NO number(19,0) not null, ADJUST_VALUE_AMOUNT number(19,2), 
ADJUST_CASHBACK_AMOUNT number(19,2), REMARKS varchar2(255 char), PMTB_PRODUCT number(19,0), primary 
key (REQ_NO)) ;


alter table PMTB_ADJUSTMENT_REQ add constraint FK9F3DD0402AB490D9 foreign key (REQ_NO) references 
PMTB_PREPAID_REQ;

alter table PMTB_ADJUSTMENT_REQ add constraint FK9F3DD04037CA0E22 foreign key (PMTB_PRODUCT) 
references PMTB_PRODUCT ;


