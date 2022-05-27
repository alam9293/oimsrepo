SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_novssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 20/11/2017 CR #176 Trip Code Reason START */

/*-- 20/11/2017 CR #176 Trip Code Reason END */

spool off