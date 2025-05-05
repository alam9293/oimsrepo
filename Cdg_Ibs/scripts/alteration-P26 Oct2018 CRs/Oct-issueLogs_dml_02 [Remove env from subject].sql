SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_octIssueLogs_ddl_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 19/01/2019 CR #192 PUBBS p3 START */

-- remove env from subject

update mstb_master_table set master_value = 'IBS - Account Maintenance Dated #currentDate#' where master_type = 'EMPSN' and MASTER_CODE = 'SUBJ';
/*-- 19/01/2019 CR #192 PUBBS p3 END */


spool off
