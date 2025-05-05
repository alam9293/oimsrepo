SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_aprIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 19/04/2019 CR #211 CR0419009 Map to Generic Vehicle Type START */

-- CFPT  map to Vehicle Model "HS"   can change accordingly.
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'DTMVT','CFPT',null,'HS','A',0, 'Others');

--select * from MSTB_MASTER_TABLE where master_type = 'VM';
--to check vehicle model.  Master Code.

/*-- 19/04/2019 CR #211 CR0419009 Map to Generic Vehicle Type END */


spool off
