SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_mayp32IssueLogs_ddl_03_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*--  CR AUTO TOPUP START */

alter table PMTB_PREPAID_REQ add REDDOTINVOICENO varchar2(100 char);
COMMENT ON COLUMN PMTB_PREPAID_REQ.REDDOTINVOICENO IS 'RedDot Invoice No. Require for Requesting Email Address';

/*-- CR  AUTO TOPUP  END */

spool off
