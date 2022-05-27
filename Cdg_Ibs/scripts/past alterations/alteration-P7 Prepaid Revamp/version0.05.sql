--added from 14-April-2014

alter table PMTB_ISSUANCE_REQ_CARD add PRODUCT_NO number(19,0) ;

alter table PMTB_ISSUANCE_REQ_CARD add constraint FKE4AACF81EF972F58 foreign key (PRODUCT_NO) references PMTB_PRODUCT ;

alter table PMTB_ISSUANCE_REQ drop column remarks;

alter table PMTB_TRANSFER_REQ drop column remarks;

alter table PMTB_ADJUSTMENT_REQ drop column remarks;

alter table PMTB_TOP_UP_REQ drop column remarks;

alter table PMTB_PREPAID_REQ add REQUEST_REMARKS varchar2(255 char);

alter table PMTB_PREPAID_REQ add APPROVER number(19,0) ;

alter table PMTB_PREPAID_REQ add APPROVAL_DATE timestamp ;

alter table PMTB_PREPAID_REQ add APPROVAL_STATUS varchar2(255 char) ;

alter table PMTB_PREPAID_REQ add APPROVAL_REMARKS varchar2(255 char) ;

alter table PMTB_PREPAID_REQ add constraint FK1CCE02FA62BC7500 foreign key (APPROVER) references SATB_USER ;

alter table PMTB_EXT_BAL_EXP_DATE_REQ add OLD_BAL_EXP_DATE timestamp;

alter table pmtb_prepaid_txn rename column created_dt to txn_date;

alter table PMTB_PREPAID_REQ add PROCESS_DATE timestamp ;

alter table PMTB_PREPAID_TXN add PMTB_PREPAID_REQ number(19,0);

alter table PMTB_PREPAID_TXN add apply_card_value number(19,2);

alter table PMTB_PREPAID_TXN add apply_cashplus number(19,2);

alter table PMTB_PREPAID_TXN add GLABLE_FLAG varchar2(10 char);

alter table PMTB_PREPAID_TXN add constraint FK1CCE0CC67896EF11 foreign key (PMTB_PREPAID_REQ) 
references PMTB_PREPAID_REQ;

alter table PMTB_PREPAID_TXN add ACQUIRE_TXN_NO number(10,0) 

alter table PMTB_PREPAID_TXN add constraint FK1CCE0CC6C5072117 foreign key (ACQUIRE_TXN_NO) 
references TMTB_ACQUIRE_TXN;

alter table FMTB_GL_LOG_DETAIL add PREPAID_TXN_NO number(19,2);

alter table FMTB_GL_LOG_DETAIL add constraint FKE2762C1CF8F923A9 foreign key (PREPAID_TXN_NO) 
references PMTB_PREPAID_TXN;


alter table bmtb_draft_inv_summary  MODIFY summary_type varchar2(10);
 
alter table bmtb_draft_inv_detail  MODIFY invoice_detail_type varchar2(10);

alter table bmtb_invoice_summary  MODIFY summary_type varchar2(10);
 
alter table bmtb_invoice_detail  MODIFY invoice_detail_type varchar2(10);

ALTER TABLE bmtb_invoice_header ADD prepaid_flag varchar(2) DEFAULT 'N';
 
ALTER TABLE bmtb_draft_inv_header ADD prepaid_flag varchar(2) DEFAULT 'N';

alter table pmtb_product add expired_flag varchar2(10) default 'N';

alter table FMTB_TRANSACTION_CODE MODIFY TXN_TYPE varchar2(10);

create sequence PMTB_BALANCE_FORFEITURE_SQ1;

create table PMTB_BALANCE_FORFEITURE (BALANCE_FORFEITURE_NO number(19,0) not null, CARD_VALUE 
number(19,2), CASHPLUS number(19,2), FORFEITED_DATE timestamp, PRODUCT_NO number(19,0),
primary key (BALANCE_FORFEITURE_NO));

 alter table PMTB_BALANCE_FORFEITURE add VERSION number(10,0) 

 alter table PMTB_BALANCE_FORFEITURE add constraint FK186EFF0CEF972F58 foreign key (PRODUCT_NO) 
references PMTB_PRODUCT;

alter table PMTB_BALANCE_FORFEITURE add CARD_VALUE_GST_RATE number(19,2);

alter table PMTB_BALANCE_FORFEITURE add CASHPLUS_GST_RATE number(19,2);


alter table PMTB_PRODUCT add LAST_BALANCE_FORFEITURE number(19,0) 

alter table PMTB_PRODUCT add constraint FK36F42DBBCEC9D30A foreign key (LAST_BALANCE_FORFEITURE) 
references PMTB_BALANCE_FORFEITURE ;


alter table pmtb_issuance_req_card drop (name_on_card, position, mobile, telephone, email, expiry_date_time, sms_expiry_flag, sms_topup_flag, value, value_expiry_date, cashplus, waive_issuance_fee_flag);

alter table pmtb_issuance_req_card add (
  BALANCE_EXPIRY_DATE                    DATE,
  NAME_ON_PRODUCT                        VARCHAR2(80),
  CREDIT_LIMIT                           NUMBER(12,2),
  EXPIRY_DATE                            DATE,             
  FIXED_VALUE                            NUMBER(12,2),   
  CARD_HOLDER_NAME                       VARCHAR2(80),     
  CARD_HOLDER_TITLE                      VARCHAR2(80),     
  CARD_HOLDER_TEL                        VARCHAR2(20),     
  CARD_HOLDER_SALUTATION                 VARCHAR2(80),     
  CARD_HOLDER_MOBILE                     VARCHAR2(20),     
  CARD_HOLDER_EMAIL                      VARCHAR2(255),  
  WAIVE_SUBSC_FEE_FLAG                   VARCHAR2(1 CHAR), 
  IS_INDIVIDUAL_CARD                     VARCHAR2(1),      
  EMBOSS_FLAG                            VARCHAR2(1),      
  EXPIRY_TIME                            TIMESTAMP(6),   
  EMBOSS_NAME_ON_CARD                    VARCHAR2(1),      
  WAIVE_ISSUANCE_FEE_FLAG                VARCHAR2(1),      
  OFFLINE_COUNT                          NUMBER(6),        
  OFFLINE_TXN_AMOUNT                     NUMBER(6,2),      
  OFFLINE_AMOUNT                         NUMBER(6,2),  
  SMS_EXPIRY_FLAG                        VARCHAR2(1),      
  SMS_TOPUP_FLAG                         VARCHAR2(1), 
  CASHPLUS                               NUMBER(19,2),     
  CARD_VALUE                             NUMBER(19,2)     
);


CREATE TABLE PMTB_CARD_NO_SEQUENCE
  (
    SEQ_ID          NUMBER(10,0) NOT NULL,
    VERSION         NUMBER(10,0) NOT NULL,
    NUMBER_OF_DIGIT NUMBER(10,0),
    BIN_RANGE       VARCHAR2(255 CHAR),
    SUB_BIN_RANGE   VARCHAR2(255 CHAR),
    COUNT           NUMBER(19,2),
    PRIMARY KEY (SEQ_ID),
    CONSTRAINT CARD_NO_SEQUENCE_UNIQUE UNIQUE (BIN_RANGE, NUMBER_OF_DIGIT,SUB_BIN_RANGE)
  );

create sequence PMTB_CARD_NO_SEQUENCE_SQ1 ;
  
  
alter table BMTB_INVOICE_HEADER add DELIVERY_CHARGE_TXN_CODE number(10,0) ;

alter table PMTB_ISSUANCE_REQ add DELIVERY_CHARGE_TXN_CODE number(10,0) ;

alter table BMTB_INVOICE_HEADER add constraint FK50C11D059D6BD3B foreign key (DELIVERY_CHARGE_TXN_CODE) 
references FMTB_TRANSACTION_CODE;

alter table PMTB_ISSUANCE_REQ add constraint FK22B7E92E9D6BD3B foreign key (DELIVERY_CHARGE_TXN_CODE) 
references FMTB_TRANSACTION_CODE;


alter table PMTB_ADJUSTMENT_REQ add ADJUST_VALUE_TXN_CODE number(10,0);

alter table PMTB_ADJUSTMENT_REQ add ADJUST_CASHPLUS_TXN_CODE number(10,0);

alter table PMTB_PREPAID_TXN add FMTB_TRANSACTION_CODE number(10,0); 

alter table PMTB_ADJUSTMENT_REQ add constraint FK9F3DD040D34DB65D foreign key (ADJUST_CASHPLUS_TXN_CODE) 
references FMTB_TRANSACTION_CODE ;

alter table PMTB_ADJUSTMENT_REQ add constraint FK9F3DD040BD26D019 foreign key (ADJUST_VALUE_TXN_CODE) 
references FMTB_TRANSACTION_CODE ;

alter table PMTB_PREPAID_TXN add constraint FK1CCE0CC686447F1 foreign key (FMTB_TRANSACTION_CODE) 
references FMTB_TRANSACTION_CODE ;
  
  

--prepaid usage report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Usage Report',
    'U',
    '/report/prepaid_usage_report.zul?rsrcId=',
    6,
    'Prepaid Usage Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Usage Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Report'), 'CSV');


--prepaid usage detail report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Usage Detail Report',
    'U',
    '/report/prepaid_usage_detail_report.zul?rsrcId=',
    7,
    'Prepaid Usage Detail Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Usage Detail Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Detail Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Detail Report'), 'CSV');


--top up report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Top Up Report',
    'U',
    '/report/top_up_report.zul?rsrcId=',
    8,
    'Top Up Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Top Up Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Top Up Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Top Up Report'), 'CSV');



--prepaid approval report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Approval Report',
    'U',
    '/report/prepaid_approval_report.zul?rsrcId=',
    9,
    'Prepaid Approval Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Approval Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Approval Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Approval Report'), 'CSV');


--prepaid card transaction report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Card Transaction Report',
    'U',
    '/report/prepaid_card_transaction_report.zul?rsrcId=',
    10,
    'Prepaid Card Transaction Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Card Transaction Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Card Transaction Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Card Transaction Report'), 'CSV');




