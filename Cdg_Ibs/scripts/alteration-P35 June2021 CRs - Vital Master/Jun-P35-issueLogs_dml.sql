SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/*-- 16/06/2021  CR VITAL MASTER ACCESS START */

 INSERT INTO SATB_RESOURCE
 (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
 VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Search New Portal User', 'U', '/portal/search_new_portal_user.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Search New Portal User', 'Y', 0);


/*-- 16/06/2021  CR VITAL MASTER ACCESS  END */




spool off