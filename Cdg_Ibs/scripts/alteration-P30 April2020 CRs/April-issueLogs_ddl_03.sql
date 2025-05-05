SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_aprP30IssueLogs_ddl_03_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/* For E Invoice Email Master List */

UPDATE MSTB_MASTER_TABLE SET MASTER_VALUE = 'E Invoice Email - #accountNo# #accountName# #invoiceDate#' WHERE MASTER_TYPE = 'EERL' AND MASTER_CODE = 'SUBJ';

commit;