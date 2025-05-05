------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt.&new_ext
select 'Running at ' || sys_context('userenv', 'host') || '[' || sys_context('userenv', 'ip_address') || ']' || '@' || sys_context('userenv', 'os_user') 
       || ' as ' || sys_context('userenv', 'current_user') || '@' || sys_context('userenv', 'db_unique_name') as where_run
from   dual;
select 'Run start: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_start
from dual;
set linesize  10000
set pages 5000
set feedback on
set timing on
set heading on
set echo on
set define off
------------- Top Standard statements end

  ALTER session SET current_schema=ibssys;

  --------------------------------------------------------
  -- 01 DDL for Table ITTB_SETL_REPORTING_AMEX
  --------------------------------------------------------
  DROP TABLE ITTB_SETL_REPORTING_AMEX;
  CREATE TABLE ITTB_SETL_REPORTING_AMEX (
  ID                            NUMBER CONSTRAINT PC_ISRAX__ID  PRIMARY KEY,
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
  
   ALTER TABLE ITTB_SETL_REPORTING_AMEX ADD CONSTRAINT CC_ISRAX__RF CHECK (RETRIEVED_FLAG IN ('Y', 'N'));


  --Add comment to the table
  comment on table ittb_setl_reporting_amex is 'This table is a temporary table that store the records of amex settlement file provided by RCVW_BANK_ADVICE_AMEX_FOR_IBS. It hold identical fields as RCVW_BANK_ADVICE_AMEX_FOR_IBS.';
  
  --Add comment to the columns
  comment on column ittb_setl_reporting_amex.record_type is 'This field contains the Record identifier';
  comment on column ittb_setl_reporting_amex.merchant_id is 'American Express-assigned Service Establishment Number of the Merchant';
  comment on column ittb_setl_reporting_amex.account_type is 'This field contains the Settlement Account Type';
  comment on column ittb_setl_reporting_amex.payment_number is 'This field contains the American Express-assigned Payment/Settlement Number';
  comment on column ittb_setl_reporting_amex.payment_date is 'This field contains the Payment Date scheduled in American Express systems';
  comment on column ittb_setl_reporting_amex.payment_currency is 'This field contains the Alphanumeric ISO Code for the Payment currency.';
  comment on column ittb_setl_reporting_amex.business_submission_date is 'The field contains the date assigned by the Merchant or Partner to this submission';
  comment on column ittb_setl_reporting_amex.submission_invoice_number is 'This field contains the Submission Invoice number';
  comment on column ittb_setl_reporting_amex.submission_currency is 'This field contains the Submission Currency Code in Alphanumeric ISO format';
  comment on column ittb_setl_reporting_amex.invoice_number is 'This field contains the Invoice/Reference Number assigned by the Merchant or Partner to this transaction at the time the sale was executed';
  comment on column ittb_setl_reporting_amex.account_number is 'This field contains the Cardmember Account Number that corresponds to this transaction';
  comment on column ittb_setl_reporting_amex.reference_number is 'This field contains an industry-specific identifier';
  comment on column ittb_setl_reporting_amex.submission_gross_amount is 'This field contains the Gross Amount of American Express charges submitted in the original submission';
  comment on column ittb_setl_reporting_amex.transaction_amount is 'This field contains the Transaction Amount for a single transaction';
  comment on column ittb_setl_reporting_amex.transaction_date is 'This field contains the Transaction Date';
  comment on column ittb_setl_reporting_amex.transaction_id is 'This field contains Transaction Identifier';
  comment on column ittb_setl_reporting_amex.approval_code is 'This field contains the Approval Code obtained on the Authorization Request';
  comment on column ittb_setl_reporting_amex.acquirer_reference_number is 'This field contains the Acquirer Reference Number';
  comment on column ittb_setl_reporting_amex.transaction_rejected_ind is 'This field indicates that the ROC was rejected within the American Express payments processing system';
  comment on column ittb_setl_reporting_amex.fee_code is 'This field contains a Fee Code that corresponds to the preceding ROC Detail Record';
  comment on column ittb_setl_reporting_amex.fee_amount is 'This field contains the Fee Amount charged by American Express for this transaction to six decimal places';
  comment on column ittb_setl_reporting_amex.dicount_rate is 'This field contains the Discount Rate';
  comment on column ittb_setl_reporting_amex.discount_amount is 'This field contains the Discount Amount charged by American Express for this transaction to six decimal places';
  comment on column ittb_setl_reporting_amex.chargeback_number is 'This field contains the American Express-assigned reference number which relates to this chargeback';
  comment on column ittb_setl_reporting_amex.chargeback_reason_code  is 'This field contains the Dispute/Chargeback Reason Code used by American Express systems according to the region of the chargeback';
  comment on column ittb_setl_reporting_amex.chargeback_reason_description is 'This field contains the Dispute/Chargeback Reason Description, which is the reason the Merchant is assigned the amount that appears in Field 24, Net Amount';
  comment on column ittb_setl_reporting_amex.gross_amount is 'This field contains the Gross Amount relating to this Chargeback Record';
  comment on column ittb_setl_reporting_amex.service_fee_amount is 'This field contains the total Service Fee Amount relating to this Chargeback Record';
  comment on column ittb_setl_reporting_amex.tax_amount is 'This field contains the Tax Amount relating to this Chargeback Record';
  comment on column ittb_setl_reporting_amex.net_amount is 'This field contains the Net Amount of the chargeback, which is the gross amount less deductions';
  comment on column ittb_setl_reporting_amex.service_fee_rate is 'This field contains the Service Fee Rate used to calculate the amount American Express charges a Merchant as service fees';
  comment on column ittb_setl_reporting_amex.batch_code is 'This field contains the three-digit, numeric Batch Code that corresponds to the Chargeback Reason, when used in conjunction with Bill Code';
  comment on column ittb_setl_reporting_amex.bill_code is 'This field contains the three-digit, numeric Bill Code that corresponds to the Chargeback Reason, when used in conjunction with Batch Code. Refer to the Appendix for a list of Bill Codes and their associated descriptions';
  comment on column ittb_setl_reporting_amex.adjustment_number is 'This field contains the American Express-assigned reference number which relates to this adjustment';
  comment on column ittb_setl_reporting_amex.adjustment_reason_code is 'This field contains the Adjustment Reason Code used by American Express systems according to the region of the adjustment';
  comment on column ittb_setl_reporting_amex.adjustment_reason_description is 'This field contains the Adjustment Reason Description, which is the reason the Merchant is assigned the amount that appears in Field 24, Net Amount';
  comment on column ittb_setl_reporting_amex.retrieved_flag is 'Indicate if the record has been retrieved. Retrieve flag status: Y = Yes, N = No';
  comment on column ittb_setl_reporting_amex.retrieved_date is 'Indicate when the record was retrieved';
  
  --------------------------------------------------------
  -- 02 DDL for Table ITTB_SETL_REPORTING_ADYEN
  --------------------------------------------------------
  DROP TABLE ITTB_SETL_REPORTING_ADYEN;
  CREATE TABLE ITTB_SETL_REPORTING_ADYEN(
  ID                              NUMBER CONSTRAINT PC_ISRAN__ID  PRIMARY KEY,
  FILE_NAME                       VARCHAR2(30) NOT NULL,
  UPLOAD_DATE                     DATE NOT NULL,
  PSPREFERENCE                    VARCHAR2(16),
  MERCHANT_REFERENCE              VARCHAR2(80),
  PAYMENT_METHOD                  VARCHAR2(30),
  CREATION_DATE                   DATE,
  TYPE                            VARCHAR2(50),
  MODIFICATION_REFERENCE          VARCHAR2(256),
  GROSS_CURRENCY                  VARCHAR2(3),
  GROSS_DEBIT                     NUMBER(6,2),
  GROSS_CREDIT                    NUMBER(6,2),
  EXCHANGE_RATE                   NUMBER,
  NET_CURRENCY                    VARCHAR2(3),
  NET_DEBIT                       NUMBER(6,2),
  NET_CREDIT                      NUMBER(6,2),
  COMMISSION                      NUMBER(6,2),
  MARKUP                          NUMBER(6,2),
  SCHEME_FEES                     NUMBER(6,2),
  INTERCHANGE                     NUMBER(6,2),
  PAYMENT_METHOD_VARIANT          VARCHAR2(50),
  MODIFICATION_MERCHANT_REF       VARCHAR2(80),
  BATCH_NO                        VARCHAR2(6),
  RETRIEVED_FLAG                  VARCHAR2(1),
  RETRIEVED_DATE                  DATE
  );
  
  -- Add comments to the table
  comment on table ittb_setl_reporting_adyen is 'This table is a temporary table that store the records of adyen settlement file provided by RCVW_BANK_ADVICE_ADYEN_FOR_IBS. It hold identical fields as RCVW_BANK_ADVICE_ADYEN_FOR_IBS.';

  -- Add comments to the columns 
  comment on column ittb_setl_reporting_adyen.pspreference is 'Adyen unique reference';
  comment on column ittb_setl_reporting_adyen.merchant_reference is 'Reference number provided when initiating the payment request';
  comment on column ittb_setl_reporting_adyen.payment_method is 'Payment method type of the payment which was processed';
  comment on column ittb_setl_reporting_adyen.creation_date is 'Time stamp indicating when the capture was received by Adyen';
  comment on column ittb_setl_reporting_adyen.type is 'Type of record included in a report';
  comment on column ittb_setl_reporting_adyen.modification_reference is 'this is the modification PSP reference';
  comment on column ittb_setl_reporting_adyen.gross_currency is 'Three character ISO code for the currency which was used for processing the payment';
  comment on column ittb_setl_reporting_adyen.gross_debit is 'Gross debit amount; this is the amount submitted in the transaction request';
  comment on column ittb_setl_reporting_adyen.gross_credit is 'Gross credit amount; this is the amount submitted in the transaction request';
  comment on column ittb_setl_reporting_adyen.exchange_rate is 'Exchange rate used for converting the gross amount into the net amount';
  comment on column ittb_setl_reporting_adyen.net_currency is 'Three character ISO code for the currency which was used for processing the payment';
  comment on column ittb_setl_reporting_adyen.net_debit is 'Net amount debited from the Payable';
  comment on column ittb_setl_reporting_adyen.net_credit is 'Net credit amount; this is the amount submitted in the transaction request minus Acquirer fees';
  comment on column ittb_setl_reporting_adyen.commission is 'The commission fee that was withheld by the acquirer';
  comment on column ittb_setl_reporting_adyen.markup is 'Fee charged by the Acquiring bank';
  comment on column ittb_setl_reporting_adyen.scheme_fees is 'Fee which is charged by Visa / MC';
  comment on column ittb_setl_reporting_adyen.interchange is 'Fee charged by the Issuing bank';
  comment on column ittb_setl_reporting_adyen.payment_method_variant is 'Sub-brand of the payment method';
  comment on column ittb_setl_reporting_adyen.modification_merchant_ref is 'Reference number provided when initiating the modification request';
  comment on column ittb_setl_reporting_adyen.batch_no is 'Sequence number of the settlement';
  comment on column ittb_setl_reporting_adyen.retrieved_flag is 'Indicate if the record has been retrieved, Retrieve flag status: Y = Yes, N = No ';
  comment on column ittb_setl_reporting_adyen.retrieved_date is 'Indicate when the record was retrieved';

  ALTER TABLE ITTB_SETL_REPORTING_ADYEN ADD CONSTRAINT CC_ISRAN__RF CHECK (RETRIEVED_FLAG IN ('Y', 'N'));
  ALTER TABLE ITTB_SETL_REPORTING_ADYEN ADD CONSTRAINT UC_ISRAN__PR$T UNIQUE (PSPREFERENCE, TYPE);

  --------------------------------------------------------
  -- 03 Alter existing table to add new adyen fields
  --------------------------------------------------------
  
  -- Add/modify columns   
  ALTER TABLE ITTB_SETL_TXN 
	ADD(pspreference1 VARCHAR2(20),
    pspreference2 VARCHAR2(20),
    txn_amount1 NUMBER(6,2),
    txn_amount2 NUMBER(6,2)
  );

  --comment on the new columns
  comment on column ITTB_SETL_TXN.pspreference1 is 'psp reference of 1st adyen call';
  comment on column ITTB_SETL_TXN.txn_amount1 is 'transaction amount of 1st adyen call';
  comment on column ITTB_SETL_TXN.pspreference2 is 'psp reference of 2nd adyen call';
  comment on column ITTB_SETL_TXN.txn_amount2 is 'transaction amount of 2nd adyen call';

  
  ALTER TABLE TMTB_NON_BILLABLE_TXN 
	ADD(
    PSP_REF_NO1 VARCHAR2(20),
    PSP_REF_NO2 VARCHAR2(20),
    TXN_AMOUNT1 NUMBER(6,2),
    TXN_AMOUNT2 NUMBER(6,2),
    MATCHING_STATUS VARCHAR2(1)
  );
  
  
  comment on column TMTB_NON_BILLABLE_TXN.PSP_REF_NO1 is 'psp reference of 1st adyen call';
  comment on column TMTB_NON_BILLABLE_TXN.PSP_REF_NO2 is 'psp reference of 2nd adyen call';
  comment on column TMTB_NON_BILLABLE_TXN.TXN_AMOUNT1 is 'transaction amount of 1st adyen call';
  comment on column TMTB_NON_BILLABLE_TXN.TXN_AMOUNT2 is 'transaction amount of 2nd adyen call';
  comment on column TMTB_NON_BILLABLE_TXN.MATCHING_STATUS is 'indicate if the records are matched with tmtb_non_billable_txn_crca. Matching status value: P = Pending, M = Matched, E = Error';
    

  ALTER TABLE TMTB_NON_BILLABLE_TXN ADD CONSTRAINT CC_TNBT__MS CHECK (MATCHING_STATUS IN ('P', 'M','E'));
  
  ALTER TABLE TMTB_NON_BILLABLE_BATCH 
	ADD(
    COMPLETE_STATUS VARCHAR2(1),
    REFUND_AMT NUMBER,
    REFUND_REVERSE_AMT NUMBER,
    CHARGEBACK_AMT NUMBER,
    MARKUP NUMBER,
    COMMISSION NUMBER,
    SCHEME_FEE NUMBER,
    INTERCHANGE NUMBER,
    OTHER_CREDIT_AMT NUMBER,
    OTHER_DEBIT_AMT NUMBER
  );
  
  comment on column TMTB_NON_BILLABLE_BATCH.COMPLETE_STATUS is 'indicate if the status is completed or not. Complete status value: I=Incomplete, C=Completed';
  comment on column TMTB_NON_BILLABLE_BATCH.REFUND_AMT is 'sum of the refund amount from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.REFUND_REVERSE_AMT is 'sum of the refund reverse_amount from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.CHARGEBACK_AMT is 'sum of the chargeback amount from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.MARKUP is 'sum of the markup from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.COMMISSION is 'sum of the commission from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.SCHEME_FEE is 'sum of the scheme fee from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.INTERCHANGE is 'sum of the interchange from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.OTHER_CREDIT_AMT is 'sum of the credit amount from their respective tmtb_non_billable_txn_crca records';
  comment on column TMTB_NON_BILLABLE_BATCH.OTHER_DEBIT_AMT is 'sum of the debit amount from their respective tmtb_non_billable_txn_crca records';

  ALTER TABLE TMTB_NON_BILLABLE_BATCH ADD CONSTRAINT CC_TNBB__STATUS CHECK (complete_status IN ('C', 'I'));

  --------------------------------------------------------
  -- 04 DDL for Table TMTB_NON_BILLABLE_TXN_CRCA
  --------------------------------------------------------
  DROP TABLE TMTB_NON_BILLABLE_TXN_CRCA;
  CREATE TABLE TMTB_NON_BILLABLE_TXN_CRCA (
    CRCA_ID                       NUMBER CONSTRAINT PC_TNBTC__CID PRIMARY KEY,
    PSP_REF_NO                    VARCHAR2(30) NOT NULL,
    RECORD_TYPE                   VARCHAR2(50) NOT NULL,
    SUBMISSION_MERCHANT_ID        VARCHAR2(15),
    BATCH_CODE                    VARCHAR2(3) NOT NULL,
    PAYMENT_METHOD                VARCHAR2(30) NOT NULL,
    GROSS_AMOUNT                  NUMBER,
    GROSS_DEBIT                   NUMBER,
    GROSS_CREDIT                  NUMBER,
    NET_DEBIT                     NUMBER,
    NET_CREDIT                    NUMBER,
    COMMISSION                    NUMBER,
    MARK_UP                       NUMBER,
    SCHEME_FEE                    NUMBER,
    INTERCHANGE                   NUMBER,
    PAYMENT_DATE                  DATE,          --payment_date
    PAYMENT_CURRENCY              VARCHAR2(3),   --payment_currency
    TRANSACTION_AMOUNT            NUMBER(6,2),   -- transaction_amount
    TRANSACTION_DATE              DATE,          -- transaction_date
    FEE_CODE                      VARCHAR2(2),   --fee_code
    FEE_AMOUNT                    NUMBER(6,2),   --fee_amount
    DISCOUNT_RATE                 VARCHAR2(7),   --discount_rate
    DISCOUNT_AMOUNT               NUMBER(6,2),   --discount_amount
    CHARGEBACK_NO                 VARCHAR2(30),  --chargeback_no
    CHARGEBACK_REASON_CODE        VARCHAR2(10),  --chargeback_reason_code
    CHARGEBACK_REASON_DESCRIPTION VARCHAR2(280), --chargeback_reason_description
    SERVICE_FEE_AMOUNT            NUMBER(6,2),   --service_fee_amount
    TAX_AMOUNT                    NUMBER(6,2),   --tax_amount
    NET_AMOUNT                    NUMBER(6,2),   --net_amount
    SERVICE_FEE_RATE              VARCHAR2(7),   --service_fee_rate
    ADJUSTMENT_NO                 VARCHAR2(30),  --adjustment_number
    ADJUSTMENT_REASON_CODE        VARCHAR2(10),  --adjustment_reason_code
    ADJUSTMENT_REASON_DESCRIPTION VARCHAR2(280), --adjustment_reason_description
    CREATED_DT                    TIMESTAMP,
    MODIFIED_DT                   TIMESTAMP,
    SOURCE                        VARCHAR2(30) --whether from amex/adyen.
  ); 
  
  -- Add comments to the table
  comment on table TMTB_NON_BILLABLE_TXN_CRCA is 'This table contains a combination of ittb_setl_reporting_amex and ittb_setl_reporting_adyen. Used by IBS to show the fields';

  -- Add comments to the columns 
  comment on column tmtb_non_billable_txn_crca.CRCA_ID is 'Unique identifier for tmtb_non_billable_txn_crca';
  comment on column tmtb_non_billable_txn_crca.PSP_REF_NO  is 'Adyen unique reference';
  comment on column tmtb_non_billable_txn_crca.RECORD_TYPE  is 'Type of record included in a report';
  comment on column tmtb_non_billable_txn_crca.SUBMISSION_MERCHANT_ID is 'Reference number provided when initiating the payment request';
  comment on column tmtb_non_billable_txn_crca.BATCH_CODE  is 'Sequence number of the settlement';
  comment on column tmtb_non_billable_txn_crca.PAYMENT_METHOD  is 'Payment method type of the payment which was processed';
  comment on column tmtb_non_billable_txn_crca.GROSS_AMOUNT is 'It is derived from subtracting gross debit from gross credit. Also, this field contains the Gross Amount relating to this Chargeback Record';
  comment on column tmtb_non_billable_txn_crca.GROSS_DEBIT is 'Gross debit amount; this is the amount submitted in the transaction request';
  comment on column tmtb_non_billable_txn_crca.GROSS_CREDIT  is 'Gross credit amount; this is the amount submitted in the transaction request';
  comment on column tmtb_non_billable_txn_crca.NET_DEBIT is 'Net amount debited from the Payable';
  comment on column tmtb_non_billable_txn_crca.NET_CREDIT is 'Net credit amount; this is the amount submitted in the transaction request minus Acquirer fees';
  comment on column tmtb_non_billable_txn_crca.COMMISSION is 'The commission fee that was withheld by the acquirer';
  comment on column tmtb_non_billable_txn_crca.MARK_UP is 'Fee charged by the Acquiring bank';
  comment on column tmtb_non_billable_txn_crca.SCHEME_FEE is 'Fee which is charged by Visa / MC';
  comment on column tmtb_non_billable_txn_crca.INTERCHANGE is 'Fee charged by the Issuing bank';
  comment on column tmtb_non_billable_txn_crca.PAYMENT_DATE is 'This field contains the Payment Date scheduled in American Express systems';
  comment on column tmtb_non_billable_txn_crca.PAYMENT_CURRENCY	is 'This field contains the Alphanumeric ISO Code for the Payment currency.';
  comment on column tmtb_non_billable_txn_crca.TRANSACTION_AMOUNT is 'This field contains the Transaction Amount for a single transaction';
  comment on column tmtb_non_billable_txn_crca.TRANSACTION_DATE is 'This field contains the Transaction Date';
  comment on column tmtb_non_billable_txn_crca.FEE_CODE	is 'This field contains a Fee Code that corresponds to the preceding ROC Detail Record';
  comment on column tmtb_non_billable_txn_crca.FEE_AMOUNT is 'This field contains the Fee Amount charged by American Express for this transaction to six decimal places';
  comment on column tmtb_non_billable_txn_crca.DISCOUNT_RATE is 'This field contains the Discount Rate';
  comment on column tmtb_non_billable_txn_crca.DISCOUNT_AMOUNT is 'This field contains the Discount Amount charged by American Express for this transaction to six decimal places';
  comment on column tmtb_non_billable_txn_crca.CHARGEBACK_NO is 'This field contains the American Express-assigned reference number which relates to this chargeback';
  comment on column tmtb_non_billable_txn_crca.CHARGEBACK_REASON_CODE is 'This field contains the Dispute/Chargeback Reason Code used by American Express systems according to the region of the chargeback';
  comment on column tmtb_non_billable_txn_crca.CHARGEBACK_REASON_DESCRIPTION is 'This field contains the Dispute/Chargeback Reason Description, which is the reason the Merchant is assigned the amount that appears in Field 24, Net Amount';
  comment on column tmtb_non_billable_txn_crca.SERVICE_FEE_AMOUNT is 'This field contains the total Service Fee Amount relating to this Chargeback Record';
  comment on column tmtb_non_billable_txn_crca.TAX_AMOUNT is 'This field contains the Tax Amount relating to this Chargeback Record';
  comment on column tmtb_non_billable_txn_crca.NET_AMOUNT is 'This field contains the Net Amount of the chargeback, which is the gross amount less deduction';
  comment on column tmtb_non_billable_txn_crca.SERVICE_FEE_RATE	is 'This field contains the Service Fee Rate used to calculate the amount American Express charges a Merchant as service fee';
  comment on column tmtb_non_billable_txn_crca.ADJUSTMENT_NO is 'This field contains the American Express-assigned reference number which relates to this adjustment';
  comment on column tmtb_non_billable_txn_crca.ADJUSTMENT_REASON_CODE is 'This field contains the Adjustment Reason Code used by American Express systems according to the region of the adjustment';
  comment on column tmtb_non_billable_txn_crca.ADJUSTMENT_REASON_DESCRIPTION is 'This field contains the Adjustment Reason Description, which is the reason the Merchant is assigned the amount that appears in Field 24, Net Amount';
  comment on column tmtb_non_billable_txn_crca.CREATED_DT is 'This field contains the creation date at the point when IBS received the records';
  comment on column tmtb_non_billable_txn_crca.MODIFIED_DT is 'This field contains the modified date of this record';
  comment on column tmtb_non_billable_txn_crca.SOURCE is 'This field tells if this record belongs to AMEX or ADYEN';
  
  --------------------------------------------------------
-- 04 Constraints for Table TMTB_NON_BILLABLE_TXN_CRCA
--------------------------------------------------------
DROP SEQUENCE SQ_TMTB_NON_BILLABLE_TXN_CRCA;
CREATE SEQUENCE SQ_TMTB_NON_BILLABLE_TXN_CRCA INCREMENT BY 1 START WITH 1 NOMAXVALUE NOMINVALUE ORDER;

COMMIT;

------------- Bottom Standard statements begin
set echo off
set timing off
set heading off
set feedback off
select 'Run end: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_end
from dual;
spool off
set echo on
set timing on
set heading on
set feedback on
------------- Bottom Standard statements end