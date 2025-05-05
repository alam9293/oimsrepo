SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_decIssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 15/12/2017 CR0917014 #172 Batch Upload START */

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Txn'), 'Upload Trips Batch', 'U', '/txn/upload_trips_batch.zul', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where par_rsrc_id=(select rsrc_id from satb_resource where rsrc_name='Txn')), 'Upload Trips Batch', 'Y', 0);


/*-- 15/12/2017 CR0917014 #172 Batch Upload END */

spool off