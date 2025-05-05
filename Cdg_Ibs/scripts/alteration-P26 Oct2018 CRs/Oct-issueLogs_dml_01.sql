SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_octIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 19/01/2019 CR #192 PUBBS p2 START */

-- Add Account maint email template

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMPSN','SUBJ',null,'IBS - Account Maintenance Dated #currentDate#','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMPSN','CONT',null,'<body style="font-family:Calibri; font-size:16px;white-space: pre">PUBBS Account Maintenance Completed : <br><br><br>Failed Account #table1# <br><br> Successful Account #table2# <br><br> File Process #table3#  </body>','A',0,'Email');

/*-- 19/01/2019 CR #192 PUBBS p2 END */


spool off
