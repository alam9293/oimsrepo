  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_16_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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

  
 -- Add/modify columns 

ALTER TABLE FMTB_GL_LOG_DETAIL
DROP CONSTRAINT FMTB_GL_LOG_DETAIL_FK6;

-- Need to find what is the constraint name of the primary key of TMTB_NON_BILLABLE_TXN.
-- Put in the constraint name found in dml_4
ALTER TABLE TMTB_NON_BILLABLE_TXN
DROP CONSTRAINT SYS_C0029898;

ALTER TABLE TMTB_NON_BILLABLE_TXN
ADD CONSTRAINT PC_TNBT_TXNID PRIMARY KEY (txn_id);

ALTER TABLE FMTB_GL_LOG_DETAIL
ADD CONSTRAINT FMTB_GL_LOG_DETAIL_FK6
FOREIGN KEY(CHARGEBACK_TXN_NO) REFERENCES TMTB_NON_BILLABLE_TXN(TXN_ID);

comment on column TMTB_NON_BILLABLE_TXN.TXN_ID is 'New primary identifier for Tmtb_Non_Billable_Txn table';


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