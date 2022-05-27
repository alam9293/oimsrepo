SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_febIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 7/2018 CR #190 Ace Interface START */


--Add new ACE_INDICATOR to AMTB_APPLICATION
ALTER TABLE AMTB_APPLICATION ADD (ACE_INDICATOR VARCHAR(1) DEFAULT 'N');
COMMENT ON COLUMN AMTB_APPLICATION.ACE_INDICATOR IS 'ACE Indicator Flag';

--Add new ACE_INDICATOR to AMTB_ACCOUNT
ALTER TABLE AMTB_ACCOUNT ADD (ACE_INDICATOR VARCHAR(1) DEFAULT 'N');
COMMENT ON COLUMN AMTB_ACCOUNT.ACE_INDICATOR IS 'ACE Indicator Flag';

--Add new EMPLOYEE_ID to PMTB_PRODUCT
ALTER TABLE PMTB_PRODUCT ADD (EMPLOYEE_ID VARCHAR(20));
COMMENT ON COLUMN PMTB_PRODUCT.EMPLOYEE_ID IS 'Products Employee ID';

--Add new EMPLOYEE_ID to PMTB_ISSUANCE_REQ_CARD
ALTER TABLE PMTB_ISSUANCE_REQ_CARD ADD (EMPLOYEE_ID VARCHAR(20));
COMMENT ON COLUMN PMTB_ISSUANCE_REQ_CARD.EMPLOYEE_ID IS 'Products Employee ID';

/*-- 7/2017 CR #190 ACE Interface END */

spool off
