SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_marIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 01/03/2019 CR #207 Tax Invoice Update START */


--Add new Snapshot of Current invoice's Tax Rate to BMTB_INVOICE_HEADER
ALTER TABLE BMTB_INVOICE_HEADER ADD (CURRENT_TAX_RATE NUMBER(5,2) DEFAULT 0);
COMMENT ON COLUMN BMTB_INVOICE_HEADER.CURRENT_TAX_RATE IS 'Current Tax Rate Snapshot';



ALTER TABLE BMTB_DRAFT_INV_HEADER ADD (CURRENT_TAX_RATE NUMBER(5,2) DEFAULT 0);
COMMENT ON COLUMN BMTB_DRAFT_INV_HEADER.CURRENT_TAX_RATE IS 'Current Tax Rate Snapshot';

/*-- 01/03/2019 CR #207 Tax Invoice Update END */

spool off
