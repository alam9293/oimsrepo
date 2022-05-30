SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_juneIssueLogs_ddl_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 26/06/2017 CR #153 154 Update non billable flat fare and report START */

--Add new PRODUCT_ID Column to ITTB_SETL_TXN
ALTER TABLE ITTB_SETL_TXN ADD PRODUCT_ID VARCHAR(10);
COMMENT ON COLUMN ITTB_SETL_TXN.PRODUCT_ID IS 'Product Id Trip Type';

--Add new PRODUCT_ID Column to RCVW_INTF_SETL_FOR_IBS
ALTER TABLE RCVW_INTF_SETL_FOR_IBS ADD PRODUCT_ID VARCHAR(10);
COMMENT ON COLUMN RCVW_INTF_SETL_FOR_IBS.PRODUCT_ID IS 'Product Id Trip Type';

--Add new PRODUCT_ID Column to TMTB_NON_BILLABLE_TXN
ALTER TABLE TMTB_NON_BILLABLE_TXN ADD PRODUCT_ID VARCHAR(10);
COMMENT ON COLUMN TMTB_NON_BILLABLE_TXN.PRODUCT_ID IS 'Product Id Trip Type';

/*-- 26/06/2017 CR #153 154 Update non billable flat fare and report END */

spool off
