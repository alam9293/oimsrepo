SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_aprP30IssueLogs_ddl_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/* For Email Summary Master List */

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES ((SELECT MAX(MASTER_NO)+1 FROM MSTB_MASTER_TABLE), 'EESL', 'SUBJ', NULL, 'E Invoice Email Summary', 'A', 0, 'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES ((SELECT MAX(MASTER_NO)+1 FROM MSTB_MASTER_TABLE), 'EESL', 'CONT', NULL, '<p>Below table is the summary of success and fail E Invoice Email.</p><p>#successcount# email success to sent.<br />#successtable#</p><p>#failcount# email fail to sent.<br />#failtable#</p>', 'A', 0, 'Email');


commit;