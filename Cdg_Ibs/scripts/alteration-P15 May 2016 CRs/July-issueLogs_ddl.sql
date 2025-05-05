SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_julyIssueLogs_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/*-- 30/06/2016  #41 CR/0315/029 Allow Tax Invoice without Trip Detail START */
ALTER TABLE AMTB_ACCOUNT ADD (PRINT_TAX_INV_ONLY varchar2(1 char) DEFAULT 'N');

COMMENT ON COLUMN AMTB_ACCOUNT.PRINT_TAX_INV_ONLY IS 'Print Tax Invoice Flag';

ALTER TABLE AMTB_APPLICATION ADD (PRINT_TAX_INV_ONLY varchar2(1 char) DEFAULT 'N');

COMMENT ON COLUMN AMTB_ACCOUNT.PRINT_TAX_INV_ONLY IS 'Print Tax Invoice Flag';

/*-- 30/06/2016  #41 CR/0315/029 Allow Tax Invoice without Trip Detail END */

spool off
