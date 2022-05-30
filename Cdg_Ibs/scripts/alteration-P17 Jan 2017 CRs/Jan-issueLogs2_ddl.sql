SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs2_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;




/*-- 08/02/2017  CR/0216/038 #85 VITAL MASTER ACCESS START */
alter table ITTB_CP_MASTER_LOGIN rename column name to MASTER_NAME;
/*-- 08/02/2017 CR/0216/038 #85 VITAL MASTER ACCESS  END */

spool off
