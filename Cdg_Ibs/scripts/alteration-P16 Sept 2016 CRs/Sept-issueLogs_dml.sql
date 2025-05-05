SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_septIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 18/08/2016  #122 ADD ON OUTSOURCE START */

--Invoice Promo
INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Administration'), 'Manage Invoice Promo', 'U', '/admin/invoice_promo/manage_invoice_promo.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Administration'), 'Manage Invoice Promo', 'Y', 0);


-- Race
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'CRACE','CHI',null,'CHINESE','A',0, 'Others');

/*-- 18/08/2016  #122- ADD ON OUTSOURCE END */

spool off