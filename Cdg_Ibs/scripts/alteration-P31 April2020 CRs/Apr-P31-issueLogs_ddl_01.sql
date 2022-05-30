SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_aprP31IssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 29/04/2020 CR #219 Auto Update Start */

alter table PMTB_PREPAID_REQ add uri varchar2(100 char);
COMMENT ON COLUMN PMTB_PREPAID_REQ.URI IS 'URL for reddot';

alter table PMTB_PREPAID_REQ add failureUri varchar2(100 char);
COMMENT ON COLUMN PMTB_PREPAID_REQ.URI IS 'Failure URL for reddot';

alter table PMTB_PREPAID_REQ add redDotInvoiceNo varchar2(100 char);
COMMENT ON COLUMN PMTB_PREPAID_REQ.URI IS 'INVOICE NUMBER GENERATED FROM RED DOT';

/*-- 29/04/2020 CR #219 Auto Update END */

commit;