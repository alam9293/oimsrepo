SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_octIssueLogs_ddl_03_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 24/1/2019 CR #192 PUBBS P3 START */


--Add new PUBBS RENAME COLUMN TO REQ_NO

-- pls check if require before running

alter table ittb_pubbs_return_detail RENAME COLUMN SEQ_NO to REQ_NO;
alter table ittb_pubbs_return_req RENAME COLUMN SEQ_NO to REQ_NO;

/*-- 24/1/2019 CR #192 PUBBS P3 END */

spool off
