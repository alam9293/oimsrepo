------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on
    column dt new_value new_dt
    column ext new_value new_ext
select  'RS_IBS_CR1220028_ddl_02.sql_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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

ALTER TABLE TMTB_NON_BILLABLE_TXN_CRCA MODIFY (RECORD_TYPE NULL);
ALTER TABLE TMTB_NON_BILLABLE_TXN_CRCA MODIFY (SUBMISSION_MERCHANT_ID NULL);


commit;

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