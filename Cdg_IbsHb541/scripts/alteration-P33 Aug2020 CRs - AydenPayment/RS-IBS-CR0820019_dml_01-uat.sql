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


DELETE MSTB_REPORT_FORMAT_MAP where rsrc_id = 925;
DELETE SATB_RESOURCE where rsrc_id = 925;

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (925,440,'Ayden Payment Matching Report','U','/report/ayden_payment_matching_report.zul?rsrcId=1550',17,'Ayden Payment Matching Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 925, 'CSV');

Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (51,925);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (61,925);

Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'ETAS','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'ETASC','CONT',null,'Dear Receiver,\n Settlement Detail Report has been successfully processed for #settlementDate \n All the records are successfully matched and there are no discrepancies found.','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'ETASC','SUBJ',null,'<SUCCESS> Ayden Settlement For #settlementDate','A',0,'Email');

Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'ETASE','CONT',null,'<FAILURE> Ayden Settlement For #settlementDate','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'ETASE','SUBJ',null,'Dear Receiver, \n Settlement Detail Report has been successfully processed for #settlementDate','A',0,'Email');





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
