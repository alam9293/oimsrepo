SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_septIssueLogs_ddl_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 16/09/2017 CR #171 Virtual CabCharge START */

--Add PRIMARY KEY To VIRTUAL_CARD_ID
ALTER TABLE PMTB_VIRTUAL_EMAIL ADD CONSTRAINT PMPC_VIRT_EMAIL__VIR_CARD_ID PRIMARY KEY(VIRTUAL_CARD_ID);

/*-- 16/09/2017 CR #171 Virtual Cabcharge END */

spool off

