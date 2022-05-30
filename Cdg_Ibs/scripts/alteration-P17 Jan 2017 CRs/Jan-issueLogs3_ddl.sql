SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs3_ddl_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;




/*-- 10/02/2017  CR/0216/038 #85 VITAL MASTER ACCESS START */

-- Create Index
CREATE INDEX ITIX_MASTER_LOGIN__MASNAMEID ON ITTB_CP_MASTER_LOGIN(MASTER_NAME);

--add primary key
alter table add CONSTRAINT ITPC_CP_MAS_LOG__MAS_LOG_NO PRIMARY KEY(MASTER_LOGIN_NO)



--add primary key
alter table add CONSTRAINT ITPC_CP_MAS_TAG__TAG_ACC_NO PRIMARY KEY(TAG_ACCOUNT_NO);

-- Create Index
CREATE INDEX ITIX_MASTER_TAG__ACCTNOID ON ITTB_CP_MASTER_TAG_ACCT(ACCOUNT_NO);

/*-- 10/02/2017 CR/0216/038 #85 VITAL MASTER ACCESS  END */

spool off
