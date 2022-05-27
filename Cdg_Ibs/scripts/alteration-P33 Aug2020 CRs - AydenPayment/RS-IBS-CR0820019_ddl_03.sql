  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_03_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
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

  
  ALTER TABLE BMTB_BANK_PAYMENT 
	ADD(
    REFUND_AMT NUMBER,
    REFUND_REVERSE_AMT NUMBER,
    CHARGEBACK_AMT NUMBER,
    CHARGEBACK_REVERSE_AMT NUMBER,
    MARKUP NUMBER,
    COMMISSION NUMBER,
    SCHEME_FEE NUMBER,
    INTERCHANGE NUMBER,
    OTHER_CREDIT_AMT NUMBER,
    OTHER_DEBIT_AMT NUMBER
  );
  
  comment on column BMTB_BANK_PAYMENT.REFUND_AMT is 'sum of the refund amount taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.REFUND_REVERSE_AMT is 'sum of the refund reverse_amount taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.CHARGEBACK_AMT is 'sum of the chargeback amount taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.MARKUP is 'sum of the markup taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.COMMISSION is 'sum of the commission taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.SCHEME_FEE is 'sum of the scheme fee taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.INTERCHANGE is 'sum of the interchange taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.OTHER_CREDIT_AMT is 'sum of the credit amount taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.OTHER_DEBIT_AMT is 'sum of the debit amount taken from TMTB_NON_BILLABLE_BATCH';
  comment on column BMTB_BANK_PAYMENT.CHARGEBACK_REVERSE_AMT is 'sum of the chargeback reverse taken from TMTB_NON_BILLABLE_BATCH';
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_DATE TO CHARGEBACK_REFUND_DATE;
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_GST TO CHARGEBACK_REFUND_GST;
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_ADMIN_FEE TO CHARGEBACK_REFUND_ADMIN_FEE;
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_FARE_AMT TO CHARGEBACK_REFUND_FARE_AMT;
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_REASON TO CHARGEBACK_REFUND_REASON;
  
  ALTER TABLE TMTB_NON_BILLABLE_TXN
  RENAME COLUMN CHARGEBACK_TYPE TO CHARGEBACK_REFUND_TYPE;
  
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_DATE is 'The date where chargeback/refunded occur. Used in the chargeback/refunded module';
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_GST is 'The gst amount of chargeback/refunded. Used in the chargeback/refunded module';
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_ADMIN_FEE is 'The admin fee amount of chargeback/refunded. Used in the chargeback/refunded module';
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_FARE_AMT is 'The fare amount of chargeback/refunded. Used in the chargeback/refunded module';
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_REASON is 'The reason for the provision of the chargeback/refunded. Used in the chargeback/refunded module';
  comment on column TMTB_NON_BILLABLE_TXN.CHARGEBACK_REFUND_TYPE is 'The type of the chargeback/refunded. Used in the chargeback/refunded module';
  
  commit;

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