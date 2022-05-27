SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_marchIssueLogs_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 23/03/2017 CR/0117/002 #141 Flat Fare CabCharge START */

--Add to BMTB_DRAFT_INV_TXN
ALTER TABLE BMTB_DRAFT_INV_TXN ADD (TRIP_TYPE NUMBER(8));
COMMENT ON COLUMN BMTB_DRAFT_INV_TXN.TRIP_TYPE IS 'Trip Type';

-- Add to BMTB_INVOICE_TXN
ALTER TABLE BMTB_INVOICE_TXN ADD (TRIP_TYPE NUMBER(8));
COMMENT ON COLUMN BMTB_INVOICE_TXN.TRIP_TYPE IS 'Trip Type';

/*-- 23/03/2017 CR/0117/002 #141 Flat Fare CabCharge END */

spool off
