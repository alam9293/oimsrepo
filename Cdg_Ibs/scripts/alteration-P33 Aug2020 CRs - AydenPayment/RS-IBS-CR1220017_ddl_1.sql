  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_15_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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

ALTER TABLE TMTB_NON_BILLABLE_BATCH
ADD FILE_NAME VARCHAR2(200);

ALTER TABLE TMTB_NON_BILLABLE_BATCH
ADD UPLOAD_DT_STRING VARCHAR2(200);

ALTER TABLE TMTB_NON_BILLABLE_BATCH 
MODIFY batch_no VARCHAR2(9);

ALTER TABLE TMTB_NON_BILLABLE_CRCA

-- ALTER TABLE TMTB_NON_BILLABLE_BATCH
-- DROP CONSTRAINT TMCT_NON_BILLABLE_BATCH_U1;
--
-- ALTER TABLE TMTB_NON_BILLABLE_BATCH
-- ADD CONSTRAINT TMCT_NON_BILLABLE_BATCH_U1 UNIQUE (BATCH_NO);

ALTER TABLE TMTB_NON_BILLABLE_TXN
ADD TXN_ID NUMBER(22);

declare
  maxval number;
begin
  select max(txn_no) + 1 into maxval from TMTB_NON_BILLABLE_TXN;
  execute immediate 'CREATE SEQUENCE SQ_TMTB_NON_BILLABLE_TXN  MINVALUE 1 INCREMENT BY 1 START WITH ' || maxval || ' CACHE 20 NOORDER  NOCYCLE ';
end;

comment on column TMTB_NON_BILLABLE_BATCH.FILE_NAME is 'The file name used in TMTB_NON_BILLABLE_CRCA to match this batch';
comment on column TMTB_NON_BILLABLE_BATCH.UPLOAD_DT_STRING is 'The upload date used in TMTB_NON_BILLABLE_CRCA to match this batch';

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