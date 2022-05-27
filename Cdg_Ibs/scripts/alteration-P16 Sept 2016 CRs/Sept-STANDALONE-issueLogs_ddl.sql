SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_septStandaloneIssueLogs_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/* 26/09/2016 Overdue Invoice Size Increase Fix Start */

alter table bmtb_invoice_header modify overdue_invoice varchar2(500);

/* 26/09/2016 Overdue Invoice Size Increase Fix END */



spool off
