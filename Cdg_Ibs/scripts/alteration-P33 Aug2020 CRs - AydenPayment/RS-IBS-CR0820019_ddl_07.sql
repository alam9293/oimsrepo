  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_07_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
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

  
CREATE INDEX IX_TNBT__PSPREF1
ON TMTB_NON_BILLABLE_TXN (PSPREFERENCE1) online;

CREATE INDEX IX_TNBT__PSPREF2
ON TMTB_NON_BILLABLE_TXN (PSPREFERENCE2) online;

CREATE INDEX IX_TNBTC__PSPREF
ON TMTB_NON_BILLABLE_TXN_CRCA (PSP_REF_NO) online;

CREATE INDEX IX_TNBB__CS
ON TMTB_NON_BILLABLE_BATCH (COMPLETE_STATUS) online;

CREATE INDEX IX_TNBT__MS
ON TMTB_NON_BILLABLE_TXN (MATCHING_STATUS) online;


	
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