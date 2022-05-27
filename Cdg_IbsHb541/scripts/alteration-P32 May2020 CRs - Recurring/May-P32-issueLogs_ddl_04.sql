SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_mayp32IssueLogs_ddl_04_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

-- Tokenization EMAIL

--TERL
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','SUBJ',null,'Tokenization Email Receiver List','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','CONT',null,'<p>Dear Receivers</br></p>

<p>(re)Tokenization is completed</p>
<p>However, these are the affected tokens that have more than 1 tokenized ID belonging in the account hierarchy or product</p>','A',0,'Email');


/*--  To send email out for auto upload */


--RERS
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERS','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com to allow user to receive the start notification','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERS','CONT',null,'Dear Receiver,

Uploading of auto recurring is commencing
','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERS','SUBJ',null,'Completion of the Uploading of Recurring','A',0,'Email');


--RERC
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERC','EMAIL',null,'For Jheneffer to put in the recipient email eg. ibs@wiz.com to allow user to receive the completion notification','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERC','CONT',null,'Dear Receiver,

Uploading of auto recurring has concluded
','A',0,'Email');
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) values (MSTB_MASTER_TABLE_SQ1.nextVal,'RERC','SUBJ',null,'Commencing of the Uploading of Recurring','A',0,'Email');


commit

spool off
