/* *******************
 * REWARDS
 * *******************/
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'RGP','GRACE',null,'3','A',0);

alter table IBSSYS.LRTB_REWARD_TXN add(
	BILLED_FLAG varchar2(1) default 'N' not null,
	ADJ_REQ_NO number(8),
	CREATED_DT timestamp,
	CREATED_BY varchar2(80)
);
CREATE INDEX IBSSYS.LRTB_REWARD_TXN_N6 ON IBSSYS.LRTB_REWARD_TXN(BILLED_FLAG);

alter table IBSSYS.LRTB_REWARD_ACCOUNT add(
	EXPIRED_BILLED_FLAG varchar2(1) default 'N' not null
);
CREATE INDEX IBSSYS.LRTB_REWARD_ACCOUNT_N2 ON IBSSYS.LRTB_REWARD_ACCOUNT(EXPIRED_BILLED_FLAG, EXPIRE_DT);

alter table IBSSYS.LRTB_GIFT_STOCK add(
	SERIAL_NO_START number(25),
   	SERIAL_NO_END number(25),
	PREVIOUS_PTS number(8),
	BALANCE_PTS number(8),
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table IBSSYS.BMTB_INVOICE_HEADER add(
	EXPIRED_REWARDS_PTS number(8) DEFAULT 0,
	ADJUSTED_REWARDS_PTS number(8) DEFAULT 0,
	FORFEITED_REWARDS_PTS number(8) DEFAULT 0
);
alter table IBSSYS.BMTB_DRAFT_INV_HEADER add(
	EXPIRED_REWARDS_PTS number(8) DEFAULT 0,
	ADJUSTED_REWARDS_PTS number(8) DEFAULT 0,
	FORFEITED_REWARDS_PTS number(8) DEFAULT 0
);

-- OGH: AMENDED, NO INSERT STATEMENT BEFORE, MERGED TO CHANGES BELOW, ALSO RSRC NAME TO REMAIN AS VIEW, ONLY FOR DISPLAY NAME
-- change of resource name
--update IBSSYS.SATB_RESOURCE set RSRC_NAME = 'Approve Pending Adjustment Req', DISPLAY_NAME='Approve Pending Adjustment Req' where URI = '/rewards/adjustment/view_pending_req.zul'

/* *******************
 * REWARDS BILL GEN
 * *******************/
alter table IBSSYS.BMTB_BILL_GEN_REQ add(
	VD_FLAG varchar2(1),
	RWD_FLAG varchar2(1)
);

/* *******************
 * for sms indicator
 * *******************/
ALTER TABLE IBSSYS.AMTB_APPLICATION ADD "SMS_FLAG" VARCHAR(1);
ALTER TABLE IBSSYS.AMTB_ACCOUNT ADD "SMS_FLAG" VARCHAR(1);

/* *************************
 * ENTITY EFFECTIVE END DATE
 * *************************/
alter table IBSSYS.FMTB_ENTITY_MASTER add(
	EFFECTIVE_END_DATE date
);

/* *************************
 * PREPAID
 * *************************/
ALTER TABLE IBSSYS.PMTB_PRODUCT_TYPE ADD(
	PREPAID varchar2(1) DEFAULT 'N'  NOT NULL
);

ALTER TABLE IBSSYS.PMTB_PRODUCT ADD(
	EXPIRY_TIME TIMESTAMP,
	INITIAL_PAID_VALUE NUMBER(12,2),
	INITIAL_VALUE_PLUS NUMBER(12,2),
	PAID_VALUE NUMBER(12,2),
	VALUE_PLUS NUMBER(12,2)
);

ALTER TABLE IBSSYS.PMTB_PRODUCT_REPLACEMENT ADD(
	TRANSFER_VALUE NUMBER(12,2),
	CURRENT_EXP_TIME timestamp,
	NEW_EXP_TIME timestamp,
	PAID_VALUE NUMBER(12,2),
	VALUE_PLUS NUMBER(12,2),
	NEW_STATUS VARCHAR2(2),
	OLD_CARD_NO VARCHAR2(19)
);

ALTER TABLE IBSSYS.PMTB_PRODUCT_RENEW ADD(
	CURRENT_EXP_TIME timestamp,
	NEW_EXP_TIME timestamp
);

--FOR REPLACEMENT RECORD WHEN CREATING NEW PP CARDS 
INSERT INTO IBSSYS.MSTB_MASTER_TABLE VALUES (-1,'RR','NPPC',null,'NEW PREPAID CARD','I',0);

/* *************************
 * INVENTORY
 * *************************/
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES 
(621,21,'Search Issuance Request (Sales of Taxi Vouchers)','U','/inventory/search_issuance_request.zul',14,'Sales of Taxi Vouchers','Y',0);

alter table IBSSYS.IMTB_ISSUE_REQ add(
	CONTACT_PERSON_NO number(8) not null,
	EXPIRY_DATE date not null,
	HANDLING_FEE number(12,2) not null,
	DISCOUNT number(12,2) not null,
	DELIVERY_CHARGES number(12,2) not null,
	SERIAL_NO_START number(25) not null,
   	SERIAL_NO_END number(25) not null
);
ALTER TABLE IBSSYS.IMTB_ISSUE_REQ ADD CONSTRAINT IMTB_ISSUE_REQ_FK4 FOREIGN KEY (CONTACT_PERSON_NO) REFERENCES IBSSYS.AMTB_CONTACT_PERSON (CONTACT_PERSON_NO) ENABLE;
alter table IBSSYS.IMTB_ISSUE_REQ MODIFY quantity number(18);

INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'DC','DC',null,'DELIVERY CHARGES','A',0);

alter table IBSSYS.IMTB_ISSUE add(
	DELIVERY_CHARGES number(12,2) not null
);

--Update of email content
UPDATE IBSSYS.MSTB_MASTER_TABLE SET MASTER_VALUE='<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Inventory Issuance Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>To check your Inventory Issuance Request, please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.'
WHERE MASTER_TYPE = 'EMIRA' AND MASTER_CODE = 'CONT';
UPDATE IBSSYS.MSTB_MASTER_TABLE SET MASTER_VALUE='<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Inventory Issuance Request you submitted has been <font face="Calibri Bold" color="red">rejected</font>.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>To check your Inventory Issuance Request, please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.'
WHERE MASTER_TYPE = 'EMIRR' AND MASTER_CODE = 'CONT';
UPDATE IBSSYS.MSTB_MASTER_TABLE SET MASTER_VALUE='<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Inventory Issuance Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>Please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.'
WHERE MASTER_TYPE = 'EMIRS' AND MASTER_CODE = 'CONT';

--Update view request (Sales of Taxi Vouchers) to be under product management
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from  SATB_RESOURCE where uri = 'Product Mgmt') where uri = '/inventory/view_request.zul'

 --OGH ADD IN MISSING ;
/* *******************
 * View Role
 * *******************/
update IBSSYS.SATB_RESOURCE set RSRC_NAME='Manage Role', DISPLAY_NAME='Manage Role' where URI = '/acl/role/search_role.zul';
update IBSSYS.SATB_RESOURCE set RSRC_NAME='Manage User', DISPLAY_NAME='Manage User' where URI = '/acl/user/search_user.zul';

--@GH: OGH:VERIFIED OK
--If I am not wrong, the function should be USER assignable function.
--If not please change to 'A' and let me know
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (120,10,'View Role','U','/acl/role/view_role.zul',4,'View Role','N',1);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (121,6,'View User','U','/acl/user/view_user.zul',4,'View User','N',1);

--@GH: If required, please execute them. OGH:VERIFIED NO NEED
--To make the manage function available for normal users
--update IBSSYS.SATB_RESOURCE set RSRC_TYPE='U' where URI = '/acl/role/search_role.zul'
--update IBSSYS.SATB_RESOURCE set RSRC_TYPE='U' where URI = '/acl/user/search_user.zul'

/* **********************
 * GL Bank - Bank Acct No
 * **********************/
alter table IBSSYS.FMTB_BANK_CODE add(
	BANK_ACCT_NO varchar2(25)
);

--@GH: OGH TO CONFIRM MASTER VALUE
UPDATE IBSSYS.FMTB_BANK_CODE SET BANK_ACCT_NO = '7375' WHERE BANK_CODE = '7171';
UPDATE IBSSYS.FMTB_BANK_CODE SET BANK_ACCT_NO = '7375' WHERE BANK_CODE = '7171';

--To update the existing records bank account no before making the column as mandatory
alter table IBSSYS.FMTB_BANK_CODE modify BANK_ACCT_NO not null;

/* **********************
 * Update Negative List
 * **********************/
--@GH: Your side should be creating as a view
--create table ASVW_NEGATIVE_LIST(
--	CARD_NO varchar2(19) not null primary key,
--	CREATE_DATE timestamp,
--	CREATE_BY varchar2(50)
--);

/* **********************
 * External Card
 * **********************/
alter table IBSSYS.PMTB_PRODUCT_TYPE rename column NEGATIVE_FILE_CHECK to EXTERNAL_CARD;
alter table IBSSYS.PMTB_PRODUCT_TYPE modify SUB_BIN_RANGE null;

alter table IBSSYS.TMTB_ACQUIRE_TXN add(
	EXTERNAL_CARD_NO varchar2(19)
);
alter table IBSSYS.TMTB_TXN_REVIEW_REQ add(
	EXTERNAL_CARD_NO varchar2(19)
);

--Resources
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (134,21,'Manage Negative External Product','U','/product/external/manage_negative_external_product.zul',15,'Manage Negative External Product','Y',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (135,21,'Create Negative External Product','U','/product/external/create_negative_external_product.zul',16,'Create Negative External Product','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (136,21,'Delete Negative External Product','U','Delete Negative External Product',17,'Delete Negative External Product','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (137,501,'Create Ext Trips','U','/txn/new_ext_txn.zul',8,'Create Ext Trips','Y',0);

/* **********************
 * Access Control
 * **********************/
-- Edit Product
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (138,21,'Edit Product','U','/product/edit_specific_product.zul',8,'Edit Product','N',0);
-- Inventory
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (144,529,'Issue Invoice (Sales of Taxi Vouchers)','U','Issue Invoice (Sales of Taxi Vouchers)',8,'Issue Invoice (Sales of Taxi Vouchers)','N',0);
-- View Expired Rewards
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (145,37,'View Expired Rewards','U','/acct/acct/viewExpiredRewards.zul',1,'View Expired Rewards','N',0);

/* **********************
 * Administration
 * **********************/
-- AR Control Account (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (622,602,'Delete AR Control Account','U','Delete AR Control Account',22,'Delete AR Control Account','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (623,602,'Delete AR Control Account Detail','U','Delete AR Control Account Detail',23,'Delete AR Control Account Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (624,602,'View AR Control Account','U','/admin/gl_control_code/view_gl_control_code.zul',24,'View AR Control Account','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (625,602,'View AR Control Account Detail','U','/admin/gl_control_code/view_gl_control_code_detail.zul',25,'View AR Control Account Detail','N',0);
-- Bank Payment GL (Check)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (626,602,'Create Bank Payment GL','U','/admin/gl_bank_payment/create_bank_payment.zul',26,'Create Bank Payment GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (627,602,'Create Bank Payment Detail GL','U','/admin/gl_bank_payment/create_bank_payment_detail.zul',27,'Create Bank Payment Detail GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (628,602,'Edit Bank Payment GL','U','/admin/gl_bank_payment/edit_bank_payment.zul',28,'Edit Bank Payment GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (629,602,'Edit Bank Payment Detail GL','U','/admin/gl_bank_payment/edit_bank_payment_detail.zul',29,'Edit Bank Payment Detail GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (630,602,'View Bank Payment GL','U','/admin/gl_bank_payment/view_bank_payment.zul',29,'View Bank Payment GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (631,602,'View Bank Payment Detail GL','U','/admin/gl_bank_payment/view_bank_payment_detail.zul',30,'View Bank Payment Detail GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (632,602,'Delete Bank Payment GL','U','Delete Bank Payment GL',31,'Delete Bank Payment GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (633,602,'Delete Bank Payment Detail GL','U','Delete Bank Payment Detail GL',32,'Delete Bank Payment Detail GL','N',0);
-- Manage Entity (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (634,602,'View Entity','U','/admin/entity/view_entity.zul',33,'View Entity','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (635,602,'Delete Entity','U','Delete Entity',34,'Delete Entity','N',0);
-- Manage GL Bank (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (577,602,'View GL Bank','U','/admin/gl_bank/view_gl_bank.zul',35,'View GL Bank','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (578,602,'Delete GL Bank','U','Delete GL Bank',36,'Delete GL Bank','N',0);
-- Manage Non Billable GL (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (619,602,'Create Non Billable GL','U','/admin/gl_non_billable/create_non_billable.zul',5,'Create Non Billable GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (636,602,'Create Non Billable GL Detail','U','/admin/gl_non_billable/create_non_billable_detail.zul',37,'Create Non Billable GL Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (637,602,'Edit Non Billable GL Detail','U','/admin/gl_non_billable/edit_non_billable_detail.zul',38,'Edit Non Billable GL Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (638,602,'View Non Billable GL','U','/admin/gl_non_billable/view_non_billable.zul',39,'View Non Billable GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (639,602,'View Non Billable GL Detail','U','/admin/gl_non_billable/view_non_billable_detail.zul',40,'View Non Billable GL Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (640,602,'Delete Non Billable GL','U','Delete Non Billable GL',41,'Delete Non Billable GL','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (641,602,'Delete Non Billable GL Detail','U','Delete Non Billable GL Detail',42,'Delete Non Billable GL Detail','N',0);
-- Manage Tax Code (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (642,602,'View Tax Code','U','/admin/gst/view_gst.zul',43,'View Tax Code','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (643,602,'Delete Tax Code','U','Delete Tax Code',44,'Delete Tax Code','N',0);
-- Manage Transaction Codes (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (644,602,'View Transaction Code','U','/admin/transaction_code/view_transaction_code.zul',45,'View Transaction Code','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (645,602,'Delete Transaction Code','U','Delete Transaction Code',46,'Delete Transaction Code','N',0);
-- Manage Acquirer (Checked)(Completed)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (646,537,'Create Acquirer','U','/admin/non_billable/create_acquirer.zul',47,'Create Acquirer','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (647,537,'Edit Acquirer','U','/admin/non_billable/edit_acquirer.zul',48,'Edit Acquirer','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (648,537,'View Acquirer','U','/admin/non_billable/view_acquirer.zul',49,'View Acquirer','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (649,537,'Delete Acquirer','U','Delete Acquirer',50,'Delete Acquirer','N',0);
-- Manage Acquirer MDR (Checked)(Completed)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (650,537,'Create Acquirer MDR','U','/admin/non_billable/create_acquirer_mdr.zul',51,'Create Acquirer MDR','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (651,537,'Edit Acquirer MDR','U','/admin/non_billable/edit_acquirer_mdr.zul',52,'Edit Acquirer MDR','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (652,537,'View Acquirer MDR','U','/admin/non_billable/view_acquirer_mdr.zul',53,'View Acquirer MDR','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (653,537,'Delete Acquirer MDR','U','Delete Acquirer MDR',54,'Delete Acquirer MDR','N',0);
-- Manage Acquirer Payment Type (Checked)(Completed)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (654,537,'Create Acquirer Payment Type','U','/admin/non_billable/create_acquirer_pymt_type.zul',55,'Create Acquirer Payment Type','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (655,537,'Edit Acquirer Payment Type','U','/admin/non_billable/edit_acquirer_pymt_type.zul',56,'Edit Acquirer Payment Type','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (656,537,'View Acquirer Payment Type','U','/admin/non_billable/view_acquirer_pymt_type.zul',57,'View Acquirer Payment Type','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (657,537,'Delete Acquirer Payment Type','U','Delete Acquirer Payment Type',58,'Delete Acquirer Payment Type','N',0);
-- Manage Admin Fee Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (658,537,'View Admin Fee Plan','U','/admin/admin_fee/view_admin_fee_plan.zul',59,'View Admin Fee Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (659,537,'View Admin Fee Plan Detail','U','/admin/admin_fee/view_admin_fee_plan_detail.zul',60,'View Admin Fee Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (660,537,'Delete Admin Fee Plan','U','Delete Admin Fee Plan',61,'Delete Admin Fee Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (661,537,'Delete Admin Fee Plan Detail','U','Delete Admin Fee Plan Detail',62,'Delete Admin Fee Plan Detail','N',0);
-- Manage Bank (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (662,537,'View Bank','U','/admin/bank/view_bank.zul',63,'View Bank','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (663,537,'View Bank Detail','U','/admin/bank/view_branch.zul',64,'View Bank Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (664,537,'Delete Bank','U','Delete Bank',65,'Delete Bank','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (665,537,'Delete Bank Detail','U','Delete Bank Detail',66,'Delete Bank Detail','N',0);
-- Manage Credit Term Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (666,537,'View Credit Term Plan','U','/admin/credit_term/view_credit_term_plan.zul',67,'View Credit Term Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (667,537,'View Credit Term Plan Detail','U','/admin/credit_term/view_credit_term_plan_detail.zul',68,'View Credit Term Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (668,537,'Delete Credit Term Plan','U','Delete Credit Term Plan',69,'Delete Credit Term Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (669,537,'Delete Credit Term Plan Detail','U','Delete Credit Term Plan Detail',70,'Delete Credit Term Plan Detail','N',0);
-- Manage Early Payment Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (670,537,'View Early Payment Plan','U','/admin/early_payment/view_early_payment_plan.zul',71,'View Early Payment Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (671,537,'View Early Payment Plan Detail','U','/admin/early_payment/view_early_payment_plan_detail.zul',72,'View Early Payment Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (672,537,'Delete Early Payment Plan','U','Delete Early Payment Plan',73,'Delete Early Payment Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (673,537,'Delete Early Payment Plan Detail','U','Delete Early Payment Plan Detail',74,'Delete Early Payment Plan Detail','N',0);
-- Manage Late Payment Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (674,537,'View Late Payment Plan','U','/admin/late_payment/view_late_payment_plan.zul',75,'View Late Payment Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (675,537,'View Late Payment Plan Detail','U','/admin/late_payment/view_late_payment_plan_detail.zul',76,'View Late Payment Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (676,537,'Delete Late Payment Plan','U','Delete Late Payment Plan',77,'Delete Late Payment Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (677,537,'Delete Late Payment Plan Detail','U','Delete Late Payment Plan Detail',78,'Delete Late Payment Plan Detail','N',0);
-- Manage Product Discount Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (678,537,'View Product Discount Plan','U','/admin/product_discount/view_product_discount_plan.zul',79,'View Product Discount Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (679,537,'View Product Discount Plan Detail','U','/admin/product_discount/view_product_discount_plan_detail.zul',80,'View Product Discount Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (680,537,'Delete Product Discount Plan','U','Delete Product Discount Plan',81,'Delete Product Discount Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (681,537,'Delete Product Discount Plan Detail','U','Delete Product Discount Plan Detail',82,'Delete Product Discount Plan Detail','N',0);
-- Manage Promotion Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (682,537,'Delete Promotion Plan','U','Delete Promotion Plan',83,'Delete Promotion Plan','N',0);
-- Manage Sales Person (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (683,537,'View Sales Person','U','/admin/sales_person/view_sales_person.zul',84,'View Sales Person','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (684,537,'Delete Sales Person','U','Delete Sales Person',85,'Delete Sales Person','N',0);
-- Manage Subscription Fee Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (685,537,'View Subscription Fee Plan','U','/admin/subscription_fee/view_subscription_fee_plan.zul',86,'View Subscription Fee Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (686,537,'View Subscription Fee Plan Detail','U','/admin/subscription_fee/view_subscription_fee_plan_detail.zul',87,'View Subscription Fee Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (687,537,'Delete Subscription Fee Plan','U','Delete Subscription Fee Plan',88,'Delete Subscription Fee Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (688,537,'Delete Subscription Fee Plan Detail','U','Delete Subscription Fee Plan Detail',89,'Delete Subscription Fee Plan Detail','N',0);
-- Manage Volume Discount Plan (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (689,537,'View Volume Discount Plan','U','/admin/volume_discount/view_volume_discount_plan.zul',90,'View Volume Discount Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (690,537,'View Volume Discount Plan Detail','U','/admin/volume_discount/view_volume_discount_plan_detail.zul',91,'View Volume Discount Plan Detail','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (691,537,'Delete Volume Discount Plan','U','Delete Volume Discount Plan',92,'Delete Volume Discount Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (692,537,'Delete Volume Discount Plan Detail','U','Delete Volume Discount Plan Detail',93,'Delete Volume Discount Plan Detail','N',0);
-- Manage Gifts (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (693,514,'Delete Gift Category','U','Delete Gift Category',15,'Delete Gift Category','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (694,514,'Delete Gift Item','U','Delete Gift Item',16,'Delete Gift Item','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (695,514,'Edit Gift Category','U','/rewards/edit_category.zul',17,'Edit Gift Category','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (696,514,'View Gift Item','U','/rewards/view_item.zul',18,'View Gift Item','N',0);
-- Manage Loyalty Plans (Checked)
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (697,514,'Delete Loyalty Plan','U','Delete Loyalty Plan',19,'Delete Loyalty Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (698,514,'Edit Loyalty Plan','U','/rewards/edit_plan.zul',20,'Edit Loyalty Plan','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (699,514,'View Loyalty Plan Detail','U','/rewards/view_plan_detail.zul',21,'View Loyalty Plan Detail','N',0);
-- Manage Inventory
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (700,529,'Delete Inventory Item Type','U','Delete Inventory Item Type',3,'Delete Inventory Item Type','N',0);
-- Moving Rewards Plan managing functions to Administration
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/add_plan_detail.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/create_plan.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/edit_plan.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/edit_plan_detail.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/view_plan.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/view_plan_detail.zul';
update IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from IBSSYS.SATB_RESOURCE where URI = 'Administration') where uri = 'Delete Loyalty Plan';
-- Delete Loyalty Plan Detail
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (701,537,'Delete Loyalty Plan Detail','U','Delete Loyalty Plan Detail',49,'Delete Loyalty Plan Detail','N',0);

/* *******************
 * REWARDS ADJUSTMENT
 * *******************/
create table LRTB_REWARD_ADJ_REQ(
	ADJ_REQ_NO number(8) primary key,
	REQUEST_TYPE varchar2(1) not null,
	ACCOUNT_NO NUMBER(8) not null,
	POINTS NUMBER(8) NOT NULL,
	ADJUST_POINTS_FROM varchar2(1) not null,
	MASTER_NO NUMBER(8) NOT NULL,
	REMARKS VARCHAR2(500),
	CREATED_DT timestamp, 
	CREATED_BY varchar2(80), 
	UPDATED_DT timestamp, 
	UPDATED_BY varchar2(80),
	version number(9) default 0
);
--add foreign key constraint
ALTER TABLE LRTB_REWARD_ADJ_REQ ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FK1 FOREIGN KEY (ACCOUNT_NO) REFERENCES IBSSYS.AMTB_ACCOUNT (ACCOUNT_NO) ENABLE;
ALTER TABLE LRTB_REWARD_ADJ_REQ ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FK2 FOREIGN KEY (MASTER_NO) REFERENCES IBSSYS.MSTB_MASTER_TABLE (MASTER_NO) ENABLE;
--sequence
CREATE SEQUENCE LRTB_REWARD_ADJ_REQ_SQ1 MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;

create table LRTB_REWARD_ADJ_REQ_FLOW(
	ADJ_REQ_FLOW_NO NUMBER(8) primary key,
	PARENT_REQ_FLOW_NO NUMBER(8),
	ADJ_REQ_NO NUMBER(8) NOT NULL,
	FROM_STATUS VARCHAR2(1) NOT NULL,
	TO_STATUS VARCHAR2(1) NOT NULL,
	USER_ID NUMBER(10) NOT NULL,
	REMARKS VARCHAR2(500),
	FLOW_DT timestamp, 
	version number(9) default 0
);
--add foreign key constraint
ALTER TABLE LRTB_REWARD_ADJ_REQ_FLOW ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FLOW_FK1 FOREIGN KEY (PARENT_REQ_FLOW_NO) REFERENCES LRTB_REWARD_ADJ_REQ_FLOW (ADJ_REQ_FLOW_NO) ENABLE;
ALTER TABLE LRTB_REWARD_ADJ_REQ_FLOW ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FLOW_FK2 FOREIGN KEY (ADJ_REQ_NO) REFERENCES LRTB_REWARD_ADJ_REQ (ADJ_REQ_NO) ENABLE;
ALTER TABLE LRTB_REWARD_ADJ_REQ_FLOW ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FLOW_FK3 FOREIGN KEY (USER_ID) REFERENCES SATB_USER (USER_ID) ENABLE;
--sequence
CREATE SEQUENCE LRTB_REWARD_ADJ_REQ_FLOW_SQ1 MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;

--@GH: Mergerd with alter statement at the top
/*alter table IBSSYS.LRTB_REWARD_TXN add(
	ADJ_REQ_NO number(8)
);*/
ALTER TABLE IBSSYS.LRTB_REWARD_TXN ADD CONSTRAINT IBSSYS.LRTB_REWARD_TXN_FK6 FOREIGN KEY (ADJ_REQ_NO) REFERENCES LRTB_REWARD_ADJ_REQ (ADJ_REQ_NO) ENABLE;

--@GH: Please amend accordingly
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'RADJR','R1',null,'REASON 1','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'RADJR','R2',null,'REASON 2','A',0);

--Resources
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (139,514,'Create Adjustment Req','U','/rewards/adjustment/adjustment_req.zul',22,'Create Adjustment Req','Y',0);
-- OGH: AMENDED TO CHANGE THE RSRC NAME BACK TO VIEW, OR LESS ACL MODULE WILL SEE 2 RESOURCE CONTROLING APPROVAL, ALTHOUGH 1 IS FOR VIEW
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (140,514,'View Pending Adjustment Req','U','/rewards/adjustment/view_pending_req.zul',23,'Approve Pending Adjustment Req','Y',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (141,514,'Approve Adjustment Req','U','/rewards/adjustment/approve_req.zul',24,'Approve Adjustment Req','N',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (142,514,'View Adjustment Req History','U','/rewards/adjustment/search_req.zul',25,'View Adjustment Req History','N',0);

--To cater for email contents
alter table IBSSYS.MSTB_MASTER_TABLE modify MASTER_VALUE varchar2(600);

--@GH: Amending accordingly
--Emails
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAS','SUBJ',null,'IBS-PROD - Rewards Adjustment Request','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAS','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>A Rewards Adjustment Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submiter#<br><br>Please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAA','SUBJ',null,'IBS-PROD - Rewards Adjustment Request','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAA','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Rewards Adjustment Request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>To check your Rewards Adjustment Request, please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAR','SUBJ',null,'IBS-PROD - Rewards Adjustment Request','A',0);
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAR','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Rewards Adjustment Request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>To check your Rewards Adjustment, please <a href="http://192.168.32.21/ibs/index.html">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);

/* *******************
 * VIEW PRODUCT TYPE
 * *******************/
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (143,21,'View Product Type','U','/product/type/view_product_type.zul',18,'View Product Type','N',0);

/* *********************
 * PROMOTION
 * *********************/
ALTER TABLE BMTB_NOTE ADD(
   PROMO_DIS NUMBER(12,2) DEFAULT 0  NOT NULL
);

/* *******************
 * REPORTS
 * *******************/
 /* *** for max report result *** */
INSERT INTO IBSSYS.MSTB_MASTER_TABLE (MASTER_NO, MASTER_TYPE, MASTER_CODE, INTERFACE_MAPPING_VALUE, MASTER_VALUE, MASTER_STATUS, VERSION) VALUES (IBSSYS.MSTB_MASTER_TABLE_SQ1.nextVal, 'MRR', '200', null, '200', 'A', 0);
-- for contact person report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (454,418,'Contact Person','U','/report/contact_person.zul?rsrcId=454',7,'Contact Person','Y',0);
INSERT INTO IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 454, 'CSV');
-- for card statistics report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (452,415,'Card Statistics Report','U','/report/card_statistics_report.zul?rsrcId=452',3,'Card Statistics Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 452, 'PDF');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 452, 'XLS');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 452, 'CSV');
-- for new accounts revenue report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (453,429,'New Accounts Revenue','U','/report/new_accounts_revenue.zul?rsrcId=453',2,'New Accounts Revenue','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 453, 'CSV');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 453, 'PDF');
-- for Refund Deposit
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (455,418,'Refund Deposit Report','U','/report/refund_deposit_report.zul?rsrcId=454',3,'Refund Deposit Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 455, 'CSV');
-- for financial memo
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (457,418,'Financial Memo Report Detailed','U','/report/financial_memo_report.zul?rsrcId=457',10,'Financial Memo Report Detailed','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 457, 'CSV');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 457, 'PDF');
--@GH: Merged with above insert statement
--update IBSSYS.SATB_RESOURCE set RSRC_NAME = 'Financial Memo Report Detailed', DISPLAY_NAME = 'Financial Memo Report Detailed' where RSRC_ID = 457
--PREPAID REPORT
UPDATE IBSSYS.SATB_RESOURCE SET URI = 'Product' WHERE RSRC_ID = 415; -- OGH: TYPO MISTAKE IN EXISTING RESOURCE
--INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (447,(select RSRC_ID from IBSSYS.SATB_RESOURCE where uri = 'Product'),'Prepaid Product','U','/report/prepaid_product.zul?rsrcId=447',3,'Prepaid Product','Y',0);
-- OGH: AMENDED
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (447,415,'Prepaid Product','U','/report/prepaid_product.zul?rsrcId=447',3,'Prepaid Product','Y',0);

insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 447, 'CSV');
-- for Stock take for inventory
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (458,401,'Inventory','U','Inventory',10,'Inventory','Y',0);
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (459,458,'Stock Take for Inventory Items','U','/report/stock_take_for_inventory_items.zul?rsrcId=458',3,'Stock Take for Inventory Items','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 458, 'PDF');
-- reassign inventory reports to inventory category
UPDATE IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = 458, SEQUENCE = 1 where RSRC_ID = 445;
UPDATE IBSSYS.SATB_RESOURCE set PAR_RSRC_ID = 458, SEQUENCE = 2 where RSRC_ID = 446;
-- for Item Type Revenue and Profit Report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (460,429,'Item Type Revenue and Profit Report','U','/report/item_type_revenue_profit_report.zul?rsrcId=460',1,'Item Type Revenue and Profit Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 460, 'PDF');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 460, 'CSV');
-- for Monthly Debt Management Report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (461,421,'Monthly Debt Management Report','U','/report/monthly_debt_management_report.zul?rsrcId=461',3,'Monthly Debt Management Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 461, 'PDF');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 461, 'CSV');
 --Corporate Customer Usage Breakdown
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (456,418,'Corporate Customer Usage Breakdown','U','/report/corporate_customer_usage_breakdown.zul?rsrcId=456',8,'Corporate Customer Usage Breakdown','Y',0);
INSERT INTO IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 456, 'CSV');

--Premier Service Trip Reconciliation
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (448,412,'Premier Service Trip Reconciliation','U','/report/premier_service_trip_reconciliation.zul?rsrcId=448',14,'Premier Service Trip Reconciliation','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 448, 'CSV');
UPDATE IBSSYS.SATB_RESOURCE set RSRC_NAME='Trip Reconciliation',URI='/report/trip_reconciliation.zul?rsrcId=448',DISPLAY_NAME='Trip Reconciliation' WHERE RSRC_ID=448;
-- for prepaid movement report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (462,415,'Movement Report','U','/report/movement_report.zul?rsrcId=462',5,'Movement Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 462, 'PDF');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 462, 'CSV');
-- Debts Reminder Letter
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (463,421,'Debts Reminder Letter','U','/report/debts_reminder_letter.zul?rsrcId=463',5,'Debts Reminder Letter','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 463, 'PDF');
-- Rewards Report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (464,401,'Rewards Report','U','Rewards Report',11,'Rewards','Y',0);
-- Points Redemption Letter
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (465,464,'Points Redemption Letter','U','/report/points_redemption_letter.zul?rsrcId=465',1,'Points Redemption Letter','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 465, 'PDF');
-- Loyalty Program Report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (466,464,'Loyalty Program Report','U','/report/loyalty_program_report.zul?rsrcId=466',2,'Loyalty Program Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 466, 'PDF');
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 466, 'CSV');
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (467,464,'Points Reminder Letter','U','/report/points_reminder_letter.zul?rsrcId=467',3,'Points Reminder Letter','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 467, 'PDF');
-- Trip Adjustment Report
INSERT INTO IBSSYS.SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (468,412,'Trip Adjustment Report','U','/report/trip_adjustment_report.zul?rsrcId=468',15,'Trip Adjustment Report','Y',0);
insert into IBSSYS.MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from IBSSYS.MSTB_REPORT_FORMAT_MAP), 468, 'CSV');





-- OGH: ADDED
update IBSSYS.FMTB_TRANSACTION_CODE SET TXN_TYPE = 'PR' where TXN_TYPE = 'PD';


