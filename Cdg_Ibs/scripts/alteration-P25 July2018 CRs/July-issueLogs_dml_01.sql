SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_mayIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 07/2018 CR #190 Ace Interface START */

/* Pending batch job part to confirm before doing this.
 * 
-- Change menu name
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMACE','SUBJ',null,'Ace Email Job Report (#currentDate#)','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMACE','CONT',null,'<body style="font-family:Calibri; font-size:16px;white-space: pre">Dear #shippingContact# <br /><br /> The following virtual cards are created and ready for immediate use. <br /><br /> Please log in to our Cabcharge portal <a href="http://www.cabchargeasia.com.sg"><u>www.cabchargeasia.com.sg</u></a> to activate the virtual cards. <br /><br /> #table# <br /><br /> You may email to <u>sales@cdgtaxi.com.sg</u> if you require any clarification. <br /><br /> Thank you <br /><br /> Yours sincerely <br /><br /> Cabcharge Asia Pte Ltd</body>','A',0,'Email');

-- add your own emails address for receiver
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMACERL','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0,'Others');

*/

-- add your Employee ID Nature of Business
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EINOB','NOB',null,'VITAL','A',0,'Others');



/*-- 07/2018 CR #190 Ace Interface END */


spool off