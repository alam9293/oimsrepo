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


SELECT * FROM SATB_RESOURCE order by rsrc_id desc;
SELECT * FROM SATB_RESOURCE where par_rsrc_id = 412;

DELETE MSTB_REPORT_FORMAT_MAP where rsrc_id = 1550;
DELETE SATB_RESOURCE where rsrc_id = 1550;

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (1550,440,'Ayden Payment Matching Report','U','/report/ayden_payment_matching_report.zul?rsrcId=1550',17,'Ayden Payment Matching Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 1550, 'CSV');

Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (19,1550);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (9,1550);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,1550);

commit

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
