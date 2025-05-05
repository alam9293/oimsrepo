SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_aprP31IssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Prepaid Credit Card Transaction',
    'U',
    '/prepaid/prepaid_credit_txn_search.zul',
    2,
    'Prepaid Credit Card Transaction',
    'Y',
    0
  );


insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Manage Product Types')
and b.rsrc_name in('Prepaid Credit Card Transaction');



----------------------------
--email master list
---------------------------

delete from MSTB_MASTER_TABLE where master_type = 'EMPCRA';
-- Change menu name
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMPCRA','SUBJ',null,'Email Approve for Prepaid Top Up (Cabcharge Card) #cardNo# ','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMPCRA','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #nameOnCard# <br><br> The following topup is successfully approved<br> You may email to <u>sales@cdgtaxi.com.sg</u> if you require any clarification. <br><br> Thank you <br><br> Yours sincerely <br><br> Cabcharge Asia Pte Ltd </P>','A',0,'Email');



-- add your own emails address for receiver
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMPCRA','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0,'Others');



commit;