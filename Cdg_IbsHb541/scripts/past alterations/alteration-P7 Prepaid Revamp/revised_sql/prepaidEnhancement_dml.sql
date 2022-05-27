SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_prepaidEnhancement_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

--- version 0.01
--12 scripts
update satb_resource set sequence=13 where rsrc_name='Report';
update satb_resource set sequence=12 where rsrc_name='Bill Gen';
update satb_resource set sequence=11 where rsrc_name='Billing';
update satb_resource set sequence=10 where rsrc_name like 'Loyalty%Rewards';
update satb_resource set sequence=9 where rsrc_name='Non Billable';
update satb_resource set sequence=8 where rsrc_name='Txn';

insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
    0,
    'Prepaid',
    'U',
    'Prepaid',
    7,
    'Prepaid',
    'Y',
    0
  );


insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Prepaid Request',
    'U',
    '/prepaid/prepaid_request_search.zul',
    1,
    'Prepaid Request',
    'Y',
    0
  );

insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Approval of Prepaid Request',
    'U',
    '/prepaid/prepaid_request_approval_search.zul',
    2,
    'Approval of Prepaid Request',
    'Y',
    0
  );
  
insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Manage Prepaid Cards',
    'U',
    '/prepaid/manage_prepaid_cards.zul',
    3,
    'Manage Prepaid Cards',
    'Y',
    0
  );


insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    537,
    'Manage Prepaid Promotion Plan',
    'U',
    '/admin/prepaid_promotion/manage_prepaid_promotion_plan.zul',
    1,
    'Manage Prepaid Promotion Plan',
    'Y',
    0
  );  
  
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Manage Product Types')
and b.rsrc_name in('Manage Prepaid Promotion Plan', 'Prepaid', 'Prepaid Request', 'Approval of Prepaid Request', 'Manage Prepaid Cards');

--- version 0.03
--- 1 script
--populate default data for product types
update PMTB_PRODUCT_TYPE set top_up_fee=0, transfer_fee=0, default_balance_exp_months=3;

--- version 0.04
--- 4 scrpts
--populate transaction code of Admin Fee GST
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO,
    ENTITY_NO,
    PRODUCT_TYPE_ID,
    TXN_CODE,
    DESCRIPTION,
    TXN_TYPE,
    GL_CODE,
    COST_CENTRE,
    DISCOUNTABLE,
    DISCOUNT_GL_CODE,
    DISCOUNT_COST_CENTRE,
    EFFECTIVE_DATE,
    TAX_TYPE,
    VERSION,
    IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval,
    '1',
     null,
    'AFPPG',
    'PREPAID ADMIN FEE GST',
    'AF',
    '0',
    'XXXXXXXX',
    'N',
    '',
    '',
    DATE '2009-01-01',
    45,0,
    'Y'
  );



--temporary set all existing prepaid product's value and cashplus to zero, this value will be replaced during migration
UPDATE pmtb_product
SET cashplus=0, card_value=0
WHERE product_no IN
  (
    SELECT product_no
    FROM pmtb_product a
    LEFT JOIN pmtb_product_type b
    ON a.product_type_id=b.product_type_id
    WHERE b.prepaid     ='Y'
  );

--populate all balance expiry date of existing prepaid product same with card expiry date
update pmtb_product a
SET a.balance_expiry_date=a.expiry_time
WHERE product_no IN
  (
    SELECT product_no
    FROM pmtb_product a
    LEFT JOIN pmtb_product_type b
    ON a.product_type_id=b.product_type_id
    where b.prepaid     ='Y'
  );
  
--master table for balance expiry grace period
insert into mstb_master_table (master_no,master_type,master_code,interface_mapping_value,master_value,master_status,version) 
values (mstb_master_table_sq1.nextval,'BLGP','GP',null, '3', 'A',0);

--- version 0.05
--- 20 scripts

--prepaid usage report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Usage Report',
    'U',
    '/report/prepaid_usage_report.zul?rsrcId=',
    6,
    'Prepaid Usage Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Usage Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Report'), 'CSV');


--prepaid usage detail report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Usage Detail Report',
    'U',
    '/report/prepaid_usage_detail_report.zul?rsrcId=',
    7,
    'Prepaid Usage Detail Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Usage Detail Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Detail Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Usage Detail Report'), 'CSV');


--top up report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Top Up Report',
    'U',
    '/report/top_up_report.zul?rsrcId=',
    8,
    'Top Up Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Top Up Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Top Up Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Top Up Report'), 'CSV');



--prepaid approval report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Approval Report',
    'U',
    '/report/prepaid_approval_report.zul?rsrcId=',
    9,
    'Prepaid Approval Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Approval Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Approval Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Approval Report'), 'CSV');


--prepaid card transaction report
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select rsrc_id from satb_resource where rsrc_name='Product'),
    'Prepaid Card Transaction Report',
    'U',
    '/report/prepaid_card_transaction_report.zul?rsrcId=',
    10,
    'Prepaid Card Transaction Report',
    'Y',
    0
  );

update SATB_RESOURCE a set URI=URI || a.rsrc_id where a.rsrc_name='Prepaid Card Transaction Report';

insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Card Transaction Report') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Movement Report');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, (select rsrc_id from SATB_RESOURCE where rsrc_name='Prepaid Card Transaction Report'), 'CSV');

--- version 0.06
--- 18 scripts
--delete transaction code Prepaid Admin Fee GST
delete from fmtb_transaction_code where txn_code='AFPPG';


--Prepaid Issuance Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_IF','PREPAID ISSUANCE FEE',
    'PIF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Top Up Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_TF','PREPAID TOPUP FEE',
    'PTF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
--Prepaid Transfer Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_XF','PREPAID TRANSFER FEE',
    'PXF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Replacement Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_RF','PREPAID REPLACEMENT FEE',
    'PRF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );  
  
--Prepaid Deferred Income  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_DR','DEFERRED INCOME',
    'PDR', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      

--Prepaid Prepayment CashPlus
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PC','PREPAYMENT CASHPLUS',
    'PPC', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );    
  
  
  
--Prepaid Promotion Expense  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PE','PREPAID PROMOTION EXPENSE',
    'PPE', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );    
  
--Prepaid Payment to Driver
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PM','PAYMENT TO DRIVER',
    'PPM', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      

  
--Prepaid Payable to Driver
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PB','PAYABLE TO DRIVER',
    'PPB', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      
  
  
--Prepaid Discount
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_DI','PREPAID DISCOUNT',
    'PDI', '1234', 'XXXXXXXX', 'N', '1234',
    '1234', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Forfeited Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_AF','PREPAID ADMIN FEE FORFEIT',
    'PAF', '1234', 'XXXXXXXX', 'N', '1234',
    '1234', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  

--Priority Card
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'PR', 'TFPR','PRIORITY CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );
  
--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'PR', 'AFPR','PRIORITY CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
  
--Contactless 3Gen Card 
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C4', 'TFC4','CONTACTLESS 3GEN  CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );

--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C4', 'AFC4','CONTACTLESS 3GEN CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
  
--Contactless Priority Card
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C3', 'TFC3','CONTACTLESS PRIORITY CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );

--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C3', 'AFC3','CONTACTLESS PRIORITY CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );


--- version 0.07
--- 1 script
insert into mstb_master_table (master_no, master_type,master_code,interface_mapping_value,master_value,master_status) values (mstb_master_table_sq1.nextval, 'PM','DR',null ,'DIRECT RECEIPT','A');

--- version 0.09
--- 30 scripts
--issuance request
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRS','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>Card issuance with CashPlus request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRA','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card issuance with CashPlus request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRR','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card issuance with CashPlus request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

--top up request
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRS','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>Card value topup with CashPlus request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRA','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card value topup with CashPlus request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRR','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card value topup with CashPlus request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--transfer
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRS','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance transfer request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRA','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance transfer request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRR','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance transfer request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--extend balance expiry
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERS','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance expiry extension request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERA','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance expiry extension request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERR','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance expiry extension request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--adjust
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARS','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance adjustment request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARA','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance adjustment request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARR','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance adjustment request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

--- version 0.10
--- 2 scripts
delete from satb_role_resource where RSRC_ID in (select RSRC_ID from SATB_RESOURCE where rsrc_name='Manage Prepaid Cards');

delete from SATB_RESOURCE where rsrc_name='Manage Prepaid Cards';

--- version 0.13
--- 2 scripts
--to delete Prepaid Product report menu
delete from MSTB_REPORT_FORMAT_MAP where RSRC_ID in (select RSRC_ID from  SATB_RESOURCE where rsrc_name ='Prepaid Product');

delete from  SATB_RESOURCE where rsrc_name ='Prepaid Product';


spool off



