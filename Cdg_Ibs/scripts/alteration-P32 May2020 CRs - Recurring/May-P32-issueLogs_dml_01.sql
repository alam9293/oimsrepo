SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_mayP32IssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


-- Create new Search Token Page in UI
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values ((select max(rsrc_id)+1 from satb_resource),(select RSRC_ID from SATB_RESOURCE where rsrc_name='Product Mgmt'),'Search Token','U','/product/search_token.zul',2,'Search Token','Y',0);

-- Adding of role to allow to see create new Search Token page.
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Product Mgmt')
and b.rsrc_name in('Search Token');


-- Create new Recurring Request Page on UI
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values ((select max(rsrc_id)+1 from satb_resource),(select RSRC_ID from SATB_RESOURCE where rsrc_name='Bill Gen'),'Create Recurring Request','U','/recurring/create_recurring_request.zul',2,'Create Recurring Request','Y',0);

-- Adding of role to allow to see create new recurring request page.
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Bill Gen')
and b.rsrc_name in('Create Recurring Request');



-- Manage new Recurring Request Page on UI
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values ((select max(rsrc_id)+1 from satb_resource),(select RSRC_ID from SATB_RESOURCE where rsrc_name='Bill Gen'),'Manage Recurring Request','U','/recurring/manage_recurring_request.zul',2,'Manage Recurring Request','Y',0);

-- Adding of role to allow to see manage new recurring request
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Bill Gen')
and b.rsrc_name in('Manage Recurring Request');



-- Add to MasterList for email receiver list.
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RERL','SUBJ',null,'Email: Recurring Email Receiver List','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RERL','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear Receivers, <br><br> The following Recurring invoices are successfully processed : <br><br> #invoiceNo# <br><br> The following Recurring invoices failed to be processed : #failInvoiceNo# <br><br> You may email to <u>sales@cdgtaxi.com.sg</u> if you require any clarification. <br><br> Thank you <br><br> Yours sincerely <br><br> Cabcharge Asia Pte Ltd </P>','A',0,'Email');

-- add your own emails address for receiver in MasterLIst
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RERL','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0,'Others');

-- add to MasterList for email receiver list for tokenization
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','SUBJ',null,null,'A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','CONT',null,null,'A',0,'Email');

-- add your own emails address for receiver in MasterLIst
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'TERL','EMAIL',null,null,'A',0,'Others');




-- change all past invoice set to done recurring if not it will be ran..
update bmtb_invoice_header set RECURRING_DONE_FLAG = 'Y'
--where invoice_date <= to_date('01-01-2020', 'DD-MM-YYYY')
;
--Have to run so that all past invoice will not be recurred. Only new invoice onward will be recurred.

commit;
