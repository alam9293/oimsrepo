SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_augP33IssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

set define off;
--Creation of temp table
-- CREATE TABLE RCVW_BANK_ADVICE_AMEX_FOR_IBS
-- (
-- ID NUMBER ,
-- RECORD_TYPE	VARCHAR2(10), -- record_type
-- PAYEE_MERCHANT_ID	VARCHAR2(15), -- merchant_id
-- SETTLEMENT_ACCOUNT_TYPE_CODE	VARCHAR2(3), -- account_type
-- PAYMENT_NO VARCHAR2(10), --payment_number
-- PAYMENT_DATE	date, --payment_date
-- PAYMENT_CURRENCY	VARCHAR2(3), --payment_currency
-- PSP_REF_NO	VARCHAR2(30), --invoice_number
-- CARDMEMBER_ACCOUNT_NO	VARCHAR2(19), --account_number
-- TRANSACTION_AMOUNT	number(6,2), -- transaction_amount
-- TRANSACTION_DATE	date, -- transaction_date
-- TRANSACTION_ID VARCHAR2(15), -- transaction_id
-- APPROVAL_CODE VARCHAR2(6), --approval_code
-- ACQUIRER_REF_NO VARCHAR2(23), --acquirer_reference_no
-- TRANSACTION_REJECTED_IND VARCHAR2(2), -- transaction_rejected_ind
-- FEE_CODE	VARCHAR2(2), --fee_code
-- FEE_AMOUNT	number(6,2), --fee_amount
-- DISCOUNT_RATE	VARCHAR2(7), --discount_rate
-- DISCOUNT_AMOUNT	number(6,2), --discount_amount
-- BUSINESS_SUBMISSION_DATE	date, --business_submission_date
-- SUBMISSION_INVOICE_NO	VARCHAR2(15), --submission_invoice_number
-- SUBMISSION_CURRENCY	VARCHAR2(3), --submission_currency
-- CHARGEBACK_NO	VARCHAR2(30), --chargeback_no
-- CHARGEBACK_REASON_CODE	VARCHAR2(10), --chargeback_reason_code
-- CHARGEBACK_REASON_DESCRIPTION	VARCHAR2(280), --chargeback_reason_description
-- GROSS_AMOUNT	number(6,2), --gross_amount
-- SERVICE_FEE_AMOUNT	number(6,2), --service_fee_amount
-- TAX_AMOUNT	number(6,2), --tax_amount
-- NET_AMOUNT	number(6,2), --net_amount
-- SERVICE_FEE_RATE	VARCHAR2(7), --service_fee_rate
-- BATCH_CODE	VARCHAR2(3), --batch_code
-- BILL_CODE	VARCHAR2(3), --bill_code
-- ADJUSTMENT_NO	VARCHAR2(30), --adjustment_number
-- ADJUSTMENT_REASON_CODE	VARCHAR2(10), --adjustment_reason_code
-- ADJUSTMENT_REASON_DESCRIPTION VARCHAR2(280), --adjustment_reason_description
-- RETREIVED_FLAG                  VARCHAR2(1),
-- RETRIEVED_DATE                  DATE,

-- CONSTRAINT RCVW_BANK_ADVICE_AMEX_PK PRIMARY KEY (ID)
-- );

  CREATE TABLE ITTB_SETL_REPORTING_AMEX (
  ID                            NUMBER,
  FILE_NAME                     VARCHAR2(30) NOT NULL,
  UPLOAD_DATE                   DATE NOT NULL,
  RECORD_TYPE                   VARCHAR2(10),
  MERCHANT_ID                   VARCHAR2(15),
  ACCOUNT_TYPE                  VARCHAR2(3),
  PAYMENT_NUMBER                VARCHAR2(10),
  PAYMENT_DATE                  DATE,
  PAYMENT_CURRENCY              VARCHAR2(3),
  BUSINESS_SUBMISSION_DATE      DATE,
  SUBMISSION_INVOICE_NUMBER     VARCHAR2(15),
  SUBMISSION_CURRENCY           VARCHAR2(3),
  INVOICE_NUMBER                VARCHAR2(30),
  ACCOUNT_NUMBER                VARCHAR2(19),
  REFERENCE_NUMBER              VARCHAR2(30),
  SUBMISSION_GROSS_AMOUNT       NUMBER(6,2),
  TRANSACTION_AMOUNT            NUMBER(6,2),
  TRANSACTION_DATE              DATE,
  TRANSACTION_ID                VARCHAR2(15),
  APPROVAL_CODE                 VARCHAR2(6),
  ACQUIRER_REFERENCE_NUMBER     VARCHAR2(23),
  TRANSACTION_REJECTED_IND      VARCHAR2(2),
  FEE_CODE                      VARCHAR2(2),
  FEE_AMOUNT                    NUMBER(6,2),
  DICOUNT_RATE                  VARCHAR2(7),
  DISCOUNT_AMOUNT               NUMBER(6,2),
  CHARGEBACK_NUMBER             VARCHAR2(30),
  CHARGEBACK_REASON_CODE        VARCHAR2(10),
  CHARGEBACK_REASON_DESCRIPTION VARCHAR2(280),
  GROSS_AMOUNT                  NUMBER(6,2),
  SERVICE_FEE_AMOUNT            NUMBER(6,2),
  TAX_AMOUNT                    NUMBER(6,2),
  NET_AMOUNT                    NUMBER(6,2),
  SERVICE_FEE_RATE              VARCHAR2(7),
  BATCH_CODE                    VARCHAR2(3),
  BILL_CODE                     VARCHAR2(3),
  ADJUSTMENT_NUMBER             VARCHAR2(30),
  ADJUSTMENT_REASON_CODE        VARCHAR2(10),
  ADJUSTMENT_REASON_DESCRIPTION VARCHAR2(280),
  RETRIEVED_FLAG                VARCHAR2(1),
  RETRIEVED_DATE                DATE
  );

  CREATE UNIQUE INDEX "IX_ISRAX__ID" ON "ITTB_SETL_REPORTING_AMEX" ("ID");
  ALTER TABLE "ITTB_SETL_REPORTING_AMEX" ADD CONSTRAINT "PC_ISRAX__ID" PRIMARY KEY ("ID") ENABLE;

-- CREATE TABLE RCVW_BANK_ADVICE_AYDEN_FOR_IBS(
-- ID number ,
-- PSP_REF_NO	VARCHAR2(16), --pspreference
-- MERCHANT_REF_NO	VARCHAR2(80), -- merchant_reference
-- PAYMENT_METHOD	VARCHAR2(30), -- payment_method
-- CREATION_DATE	DATE, --creation_date
-- TYPE	VARCHAR2(50), -- type
-- MODIFICATION_REF_NO	VARCHAR2(16), -- modification_ref
-- GROSS_CURRENCY	VARCHAR2(3), -- gross_currency
-- GROSS_DEBIT	NUMBER(6,2), -- gross_debit
-- GROSS_CREDIT	NUMBER(6,2), --gross_credit
-- EXCHANGE_RATE	NUMBER, --exchange_rate
-- NET_CURRENCY	VARCHAR2(3), -- net_currency
-- NET_DEBIT	NUMBER(6,2), -- net_debit
-- NET_CREDIT	NUMBER(6,2), -- net_credit
-- COMMISSION	NUMBER(6,2), -- commission
-- MARKUP	NUMBER(6,2), -- markup
-- SCHEME_FEES	NUMBER(6,2), -- scheme_fees
-- INTERCHANGE	NUMBER(6,2), --interchange
-- PAYMENT_METHOD_VARIANT	VARCHAR2(50), -- payment_method_variant
-- MODIFICATION_MERCHANT_REF_NO	VARCHAR2(80), -- modification_merchant_ref
-- BATCH_NO VARCHAR(6), -- batch_no
-- RETREIVED_FLAG                  VARCHAR2(1),
-- RETRIEVED_DATE                  DATE,

--
-- CONSTRAINT RCVW_BANK_ADVICE_AYDEN_PK PRIMARY KEY (ID)
-- );

CREATE TABLE ITTB_SETL_REPORTING_ADYEN(
ID number ,
PSPREFERENCE	VARCHAR2(16), --pspreference
MERCHANT_REFERENCE	VARCHAR2(80), -- merchant_reference
PAYMENT_METHOD	VARCHAR2(30), -- payment_method
CREATION_DATE	DATE, --creation_date
TYPE	VARCHAR2(50), -- type
MODIFICATION_REFERENCE	VARCHAR2(16), -- modification_ref
GROSS_CURRENCY	VARCHAR2(3), -- gross_currency
GROSS_DEBIT	NUMBER(6,2), -- gross_debit
GROSS_CREDIT	NUMBER(6,2), --gross_credit
EXCHANGE_RATE	NUMBER, --exchange_rate
NET_CURRENCY	VARCHAR2(3), -- net_currency
NET_DEBIT	NUMBER(6,2), -- net_debit
NET_CREDIT	NUMBER(6,2), -- net_credit
COMMISSION	NUMBER(6,2), -- commission
MARKUP	NUMBER(6,2), -- markup
SCHEME_FEES	NUMBER(6,2), -- scheme_fees
INTERCHANGE	NUMBER(6,2), --interchange
PAYMENT_METHOD_VARIANT	VARCHAR2(50), -- payment_method_variant
MODIFICATION_MERCHANT_REF_NO	VARCHAR2(80), -- modification_merchant_ref
BATCH_NO VARCHAR(6), -- batch_no
RETREIVED_FLAG VARCHAR2(1),
RETRIEVED_DATE DATE,

CONSTRAINT ITTB_SETL_REPORTING_ADYEN_PK PRIMARY KEY (ID)
);



--Alter existing table to add new ayden fields
ALTER TABLE RCVW_INTF_SETL_FOR_IBS
ADD(PSP_REF_NO1 VARCHAR2(16),
PSP_REF_NO2 VARCHAR2(16),
TXN_AMOUNT1 NUMBER,
ADMIN_AMOUNT1 NUMBER,
GST_AMOUNT1 NUMBER,
TXN_AMOUNT2 NUMBER,
ADMIN_AMOUNT2 NUMBER,
GST_AMOUNT2 NUMBER);

ALTER TABLE ITTB_SETL_TXN
ADD(PSP_REF_NO1 VARCHAR2(16),
PSP_REF_NO2 VARCHAR2(16),
TXN_AMOUNT1 NUMBER,
ADMIN_AMOUNT1 NUMBER,
GST_AMOUNT1 NUMBER,
TXN_AMOUNT2 NUMBER,
ADMIN_AMOUNT2 NUMBER,
GST_AMOUNT2 NUMBER);

ALTER TABLE TMTB_NON_BILLABLE_TXN
ADD(
PSP_REF_NO1 VARCHAR2(16),
PSP_REF_NO2 VARCHAR2(16),
TXN_AMOUNT1 NUMBER,
ADMIN_AMOUNT1 NUMBER,
GST_AMOUNT1 NUMBER,
TXN_AMOUNT2 NUMBER,
ADMIN_AMOUNT2 NUMBER,
GST_AMOUNT2 NUMBER,
MATCHING_STATUS VARCHAR2(1));


ALTER TABLE TMTB_NON_BILLABLE_TXN
ADD CONSTRAINT psp_ref_no1_unique UNIQUE (PSP_REF_NO1);

ALTER TABLE TMTB_NON_BILLABLE_TXN
ADD CONSTRAINT psp_ref_no2_unique UNIQUE (PSP_REF_NO2);

--to include chargeback reverse and refund reverse
-- ALTER TABLE TMTB_NON_BILLABLE_TXN MODIFY STATUS VARCHAR2(1);


CREATE TABLE TMTB_NON_BILLABLE_TXN_CRCA(

--combined fields with amex and ayden
ID NUMBER,
PSP_REF_NO VARCHAR2(30) NOT NULL,
RECORD_TYPE VARCHAR2(50) NOT NULL,
SUBMISSION_MERCHANT_ID VARCHAR2(15),
BATCH_CODE VARCHAR2(3) NOT NULL,
PAYMENT_METHOD VARCHAR2(30) NOT NULL,
GROSS_AMOUNT NUMBER,
GROSS_DEBIT NUMBER,
GROSS_CREDIT NUMBER, 
NET_DEBIT NUMBER,
NET_CREDIT NUMBER,
COMMISSION NUMBER,
MARK_UP NUMBER,
SCHEME_FEE NUMBER,
INTERCHANGE NUMBER,
--amex fields
PAYMENT_DATE	date, --payment_date
PAYMENT_CURRENCY	VARCHAR2(3), --payment_currency
TRANSACTION_AMOUNT	number(6,2), -- transaction_amount
TRANSACTION_DATE	date, -- transaction_date
FEE_CODE	VARCHAR2(2), --fee_code
FEE_AMOUNT	number(6,2), --fee_amount
DISCOUNT_RATE	VARCHAR2(7), --discount_rate
DISCOUNT_AMOUNT	number(6,2), --discount_amount
CHARGEBACK_NO	VARCHAR2(30), --chargeback_no
CHARGEBACK_REASON_CODE	VARCHAR2(10), --chargeback_reason_code
CHARGEBACK_REASON_DESCRIPTION	VARCHAR2(280), --chargeback_reason_description
SERVICE_FEE_AMOUNT	number(6,2), --service_fee_amount
TAX_AMOUNT	number(6,2), --tax_amount
NET_AMOUNT	number(6,2), --net_amount
SERVICE_FEE_RATE	VARCHAR2(7), --service_fee_rate
ADJUSTMENT_NO	VARCHAR2(30), --adjustment_number
ADJUSTMENT_REASON_CODE	VARCHAR2(10), --adjustment_reason_code
ADJUSTMENT_REASON_DESCRIPTION VARCHAR2(280), --adjustment_reason_description

CREATED_DT timestamp,
MODIFIED_DT timestamp,
SOURCE VARCHAR2(30), --whether from amex/ayden.

CONSTRAINT TMTB_NON_BILLABLE_TXN_CRCA_PK PRIMARY KEY (ID,BATCH_CODE) --psp reference no is not unique because will contain same psp ref no for chargeback/chargeback reverse ..
--   CONSTRAINT fk_psp_no1
--     FOREIGN KEY (PSP_REF_NO)
--     REFERENCES TMTB_NON_BILLABLE_TXN (PSP_REF_NO1),
--   CONSTRAINT fk_psp_no2
--     FOREIGN KEY (PSP_REF_NO)
--     REFERENCES TMTB_NON_BILLABLE_TXN (PSP_REF_NO2)
);

CREATE SEQUENCE TMTB_NON_BILLABLE_TXN_CRCA_SQ1
INCREMENT BY 1
START WITH 1
NOMAXVALUE NOMINVALUE
ORDER;
--New Acquirers

-- to be confirm if need to add more acquirer for ayden. Based on payment type in ayden settlement file
Insert into MSTB_ACQUIRER (ACQUIRER_NO,NAME,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,VERSION) values (MSTB_ACQUIRER_SQ1.nextval,'AMEX (A)',current_timestamp,'user/TestDomain',null,null,0);
Insert into MSTB_ACQUIRER (ACQUIRER_NO,NAME,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,VERSION) values (MSTB_ACQUIRER_SQ1.nextval,'KLARNA',current_timestamp,'user/TestDomain',null,null,0);
Insert into MSTB_ACQUIRER (ACQUIRER_NO,NAME,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,VERSION) values (MSTB_ACQUIRER_SQ1.nextval,'MASTER',current_timestamp,'user/TestDomain',current_timestamp,'user/TestDomain',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextval,'APT','B','CRCA','KLARNA','A',0,'Others');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextval,'APT','G','CRCA','MASTER (A)','A',0,'Others');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextval,'APT','H','CRCA','AMEX (A)','A',0,'Others')
--Reports

SELECT * FROM SATB_RESOURCE order by rsrc_id desc;
SELECT * FROM SATB_RESOURCE where par_rsrc_id = 412;
SELECT * FROM MSTB_REPORT_FORMAT_MAP where rsrc_id = 1550;

DELETE MSTB_REPORT_FORMAT_MAP where rsrc_id = 1550;
DELETE SATB_RESOURCE where rsrc_id = 1550;

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (1550,440,'Ayden Payment Matching Report','U','/report/ayden_payment_matching_report.zul?rsrcId=1550',17,'Ayden Payment Matching Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 1550, 'CSV');

Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (19,1550);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (9,1550);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,1550);

--Email Notifications

Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_ACQUIRER_SQ1.nextVal,'ETAS','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_ACQUIRER_SQ1.nextVal,'ETASC','CONT',null,'Dear Receiver,\n Settlement Detail Report has been successfully processed for #settlementDate \n All the records are successfully matched and there are no discrepancies found.','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_ACQUIRER_SQ1.nextVal,'ETASC','SUBJ',null,'<SUCCESS> Ayden Settlement For #settlementDate','A',0,'Email');

Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_ACQUIRER_SQ1.nextVal,'ETASE','CONT',null,'<FAILURE> Ayden Settlement For #settlementDate','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_ACQUIRER_SQ1.nextVal,'ETASE','SUBJ',null,'Dear Receiver, \n Settlement Detail Report has been successfully processed for #settlementDate','A',0,'Email');


commit;

