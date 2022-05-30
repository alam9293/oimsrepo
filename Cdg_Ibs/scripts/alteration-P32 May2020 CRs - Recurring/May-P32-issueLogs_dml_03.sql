  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'IBS-CR0220014-AutomateRecurring_dml.sql_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
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



--RERDS
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDS','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com to allow user to receive the start notification','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDS','CONT',null,'Dear Receiver,

Downloading of recurring is commencing
','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDS','SUBJ',null,'Starting of the Downloading of Recurring','A',0,'Email');


--RERDC
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDC','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com to allow user to receive the completion notification','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDC','CONT',null,'Dear Receiver,

Downloading of recurring has concluded
','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERDC','SUBJ',null,'Completion of the Downloading of Recurring','A',0,'Email');




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