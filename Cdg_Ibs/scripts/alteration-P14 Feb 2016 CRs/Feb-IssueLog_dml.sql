SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_febIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/*-- 05/04/2016 #70 CR/0315/038  Birthday Announcement START */

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Account'), 'Birthday Announcement', 'U', '/report/birthday_announcement.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Account'), 'Birthday Announcement', 'Y', 0);

UPDATE SATB_RESOURCE
SET URI = CONCAT(URI, (SELECT RSRC_ID from SATB_RESOURCE WHERE RSRC_NAME='Birthday Announcement'))
WHERE RSRC_NAME = 'Birthday Announcement';

insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Birthday Announcement'), 'CSV');

/*-- 05/04/2016 #70 CR/0315/038  Announcement END */
         



/*-- 05/04/2016 #103 CR/0116/040 Invoice Reminder Email Audit   START */

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Report'), 'Email Audit', 'U', 'Email Audit', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Report'), 'Email Audit', 'Y', 0);

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Email Audit'), 'Email Audit Log', 'U', '/report/email_audit.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Email Audit'), 'View Email Audit Log', 'Y', 0);

UPDATE SATB_RESOURCE
SET URI = CONCAT(URI, (SELECT RSRC_ID from SATB_RESOURCE WHERE RSRC_NAME='Email Audit Log'))
WHERE RSRC_NAME = 'Email Audit Log';

insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Email Audit Log'), 'CSV');

/*-- 05/04/2016 #103 CR/0116/040 Invoice Reminder Email Audit   END*/




/*-- 05/04/2016  #107  CR/0116/010 New Non Billable Payment Karhoo   START */

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'APT','K','KARHOO','A',0,'KARHOO','Others');

insert into mstb_acquirer (acquirer_no, name, created_dt, created_by) values (mstb_acquirer_sq1.nextval, 'KARHOO', sysdate, 'TestDomain\system');

insert into mstb_acquirer_mdr 
(
mdr_no,
acquirer_no,
effective_date,
rate,
created_dt,
created_by
) 
values 
(
MSTB_ACQUIRER_MDR_SQ1.nextval,
(select acquirer_no from mstb_acquirer where name='KARHOO'),
sysdate,
0,
sysdate,
'TestDomain\system'
);

-- Add to masterlist
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'COMM','PCT','PERCENTAGE COMMISSION (%)','A',0,null,'Others');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'COMM','FIX','FIXED VALUE BUSINESS PARTNER COMMISSION ($)','A',0,null,'Others');

/*-- 05/04/2016  #107  CR/0116/010 New Non Billable Payment Karhoo   END */

spool off