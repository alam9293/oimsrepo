SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_septIssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 16/09/2017 CR #171 Virtual Cabcharge START */

-- Change menu name
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMVCE','SUBJ',null,'Application of Virtual Cabcharge For #accountName# (#customerId#)','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMVCE','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #shippingContact# <br><br> The following virtual cards are created and ready for immediate use. <br><br> Please log in to our Cabcharge portal <u>www.cabchargeasia.com.sg</u> to activate the virtual cards. <br><br> #table# <br><br> You may email to <u>sales@cdgtaxi.com.sg</u> if you require any clarification. <br><br> Thank you <br><br> Yours sincerely <br><br> Cabcharge Asia Pte Ltd </P>','A',0,'Email');


INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMVCSE','SUBJ',null,'IBS-PROD - VIRTUAL PRODUCT SUMMARY REPORT (#currentDate#)','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMVCSE','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Summary of email send on #currentDateTime# <br><br>Successful Account No Send<br> #table1# <br><br> Email Failed<br> #table2# </P>','A',0,'Email');

-- add your own emails address for receiver
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMVCSER','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0,'Others');

/*-- 16/09/2017 CR #171 Virtual CabCharge END */

spool off