SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_julIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/*-- 20/08/2021  CR HLA 02 START */


--Alter existing FMTB_NON_BILLABLE_DETAIL table to add new HLA fields

ALTER TABLE FMTB_NON_BILLABLE_DETAIL ADD (PREMIUM_AMOUNT_TXN_CODE VARCHAR2(10) );

COMMENT ON COLUMN FMTB_NON_BILLABLE_DETAIL.PREMIUM_AMOUNT_TXN_CODE IS 'Insurance Premium Amount Txn Code';


-- set all premium amt txn code == fare amt txn code
update fmtb_non_billable_detail set PREMIUM_AMOUNT_TXN_CODE = FARE_AMOUNT_TXN_CODE where PREMIUM_AMOUNT_TXN_CODE Is null;


-- set column back to not null;
ALTER TABLE FMTB_NON_BILLABLE_DETAIL MODIFY PREMIUM_AMOUNT_TXN_CODE  not null ;


-- find the nonbillable premium txn code.
-- select * from FMTB_TRANSACTION_CODE order by TRANSACTION_CODE_NO desc;

-- find the transaction code no from the first line.
-- update fmtb_non_billable_detail set PREMIUM_AMOUNT_TXN_CODE = (select txn_code from fmtb_transaction_code where TRANSACTION_CODE_NO = '385');

/*-- 20/08/2021  CR HLA 02 END */




spool off