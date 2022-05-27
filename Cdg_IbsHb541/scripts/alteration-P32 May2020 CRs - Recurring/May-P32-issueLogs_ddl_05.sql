  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'IBS-CR0220014-AutomateRecurring_ddl.sql_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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


ALTER TABLE ITTB_RECURRING_CHARGE ADD "CREATED_BY" varchar2(80);
COMMENT ON COLUMN ITTB_RECURRING_CHARGE.CREATED_BY IS 'The system that inserts the credit card token';

ALTER TABLE ITTB_RECURRING_CHARGE ADD "CREATED_DT" DATE;
COMMENT ON COLUMN ITTB_RECURRING_CHARGE.CREATED_DT IS 'The date when the credit card token data record is initially generated';
 
ALTER TABLE ITTB_RECURRING_CHARGE ADD "UPDATED_BY" varchar2(80);
COMMENT ON COLUMN ITTB_RECURRING_CHARGE.UPDATED_BY IS 'The system that updates the credit card token';

ALTER TABLE ITTB_RECURRING_CHARGE ADD "UPDATED_DT" DATE;
COMMENT ON COLUMN ITTB_RECURRING_CHARGE.UPDATED_DT IS 'The date when the credit card token is updated';


ALTER TABLE ITTB_RECURRING_DTL ADD "CREATED_BY" varchar2(80);
COMMENT ON COLUMN ITTB_RECURRING_DTL.CREATED_BY IS 'The system that inserts the detail of the recurring detail';

ALTER TABLE ITTB_RECURRING_DTL ADD "CREATED_DT" DATE;
COMMENT ON COLUMN ITTB_RECURRING_DTL.CREATED_DT IS 'The date when the recurring record is initially generated';

ALTER TABLE ITTB_RECURRING_DTL ADD "UPDATED_BY" varchar2(80);
COMMENT ON COLUMN ITTB_RECURRING_DTL.UPDATED_BY IS 'The system that updates the recurring record ';

ALTER TABLE ITTB_RECURRING_DTL ADD "UPDATED_DT" DATE;
COMMENT ON COLUMN ITTB_RECURRING_DTL.UPDATED_DT IS 'The date when the recurring record is updated';

commit

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