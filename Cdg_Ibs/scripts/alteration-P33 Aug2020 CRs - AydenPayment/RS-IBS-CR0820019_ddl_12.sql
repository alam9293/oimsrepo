  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_12_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
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

	ALTER TABLE ITTB_SETL_REPORTING_AMEX 
	MODIFY FILE_NAME VARCHAR2( 50 );
	
		ALTER TABLE ITTB_SETL_REPORTING_ADYEN 
	MODIFY FILE_NAME VARCHAR2( 50 ); 




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