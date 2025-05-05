------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'DM-AF0820001-RerunBillGenStatusPatch_DML.sql_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
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

alter session set current_schema=ibssys;


SELECT * FROM BMTB_BILL_GEN_REQ billgen where billgen.request_date = TO_DATE('31/07/2020','dd/mm/yyyy');

---update one record only
UPDATE BMTB_BILL_GEN_REQ billgen SET billgen.status = 'P', billgen.request_date = TO_DATE('01/08/2020','dd/mm/yyyy'), billgen.bill_gen_time = '13:00'
where billgen.status = 'G' and billgen.request_date = TO_DATE('31/07/2020','dd/mm/yyyy');

SELECT * FROM BMTB_BILL_GEN_REQ billgen where billgen.request_date = TO_DATE('31/07/2020','dd/mm/yyyy');

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