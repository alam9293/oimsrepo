  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR1220017_dml_3.sql_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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

    UPDATE TMTB_NON_BILLABLE_TXN txn
    SET txn.txn_id = txn.txn_no;
      
    UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca 
    SET FILE_NAME = (
    SELECT FILE_NAME
    FROM ITTB_SETL_REPORTING_ADYEN adyen
    WHERE adyen.pspreference = crca.psp_ref_no and adyen.file_name != null)
    , UPLOAD_DATE = (   
    SELECT UPLOAD_DATE
    FROM ITTB_SETL_REPORTING_ADYEN adyen
    WHERE adyen.pspreference = crca.psp_ref_no and adyen.file_name != null)
    ;
    
    UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca 
    SET FILE_NAME = (
    SELECT FILE_NAME
    FROM ITTB_SETL_REPORTING_AMEX amex
    WHERE amex.reference_number = crca.psp_ref_no and amex.file_name != null)
    , UPLOAD_DATE = (   
    SELECT UPLOAD_DATE
    FROM ITTB_SETL_REPORTING_AMEX amex
    WHERE amex.reference_number = crca.psp_ref_no and amex.file_name != null)
    ;

UPDATE MSTB_MASTER_TABLE SET master_value  = '<SUCCESS> #acq For #settlementDate' where master_type = 'ETASC' and MASTER_CODE = 'SUBJ'; 
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