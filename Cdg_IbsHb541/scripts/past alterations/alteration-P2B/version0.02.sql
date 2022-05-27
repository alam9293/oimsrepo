/* *******************
 * View Role
 * *******************/
update SATB_RESOURCE set RSRC_NAME='Manage Role', DISPLAY_NAME='Manage Role' where URI = '/acl/role/search_role.zul'
update SATB_RESOURCE set RSRC_NAME='Manage User', DISPLAY_NAME='Manage User' where URI = '/acl/user/search_user.zul'

--If I am not wrong, the function should be USER assignable function.
--If not please change to 'A' and let me know
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (120,10,'View Role','U','/acl/role/view_role.zul',4,'View Role','N',1);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (121,6,'View User','U','/acl/user/view_user.zul',4,'View User','N',1);

--@GH: If required, please execute them.
--To make the manage function available for normal users
--update SATB_RESOURCE set RSRC_TYPE='U' where URI = '/acl/role/search_role.zul'
--update SATB_RESOURCE set RSRC_TYPE='U' where URI = '/acl/user/search_user.zul'

/* **********************
 * GL Bank - Bank Acct No
 * **********************/
alter table FMTB_BANK_CODE add(
	BANK_ACCT_NO varchar2(25)
);

--To update the existing records bank account no before making the column as mandatory
alter table FMTB_BANK_CODE modify BANK_ACCT_NO not null;

/* **********************
 * Update Negative List
 * **********************/
--@GH: Your side should be creating as a view
--create table ASVW_NEGATIVE_LIST(
--	CARD_NO varchar2(19) not null primary key,
--	CREATE_DATE timestamp,
--	CREATE_BY varchar2(50)
--);

/* ********************************************
 * Account - Corporate Customer Usage Breakdown
 * ********************************************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (456,418,'Corporate Customer Usage Breakdown','U',
'/report/corporate_customer_usage_breakdown.zul?rsrcId=456',8,'Corporate Customer Usage Breakdown','Y',0);

INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 456, 'CSV');

/* **********************
 * External Card
 * **********************/
alter table PMTB_PRODUCT_TYPE rename column NEGATIVE_FILE_CHECK to EXTERNAL_CARD;
alter table PMTB_PRODUCT_TYPE modify SUB_BIN_RANGE null;

alter table TMTB_ACQUIRE_TXN add(
	EXTERNAL_CARD_NO varchar2(19)
);
alter table TMTB_TXN_REVIEW_REQ add(
	EXTERNAL_CARD_NO varchar2(19)
);

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (134,21,'Manage Negative External Product','U','/product/external/manage_negative_external_product.zul',15,'Manage Negative External Product','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (135,21,'Create Negative External Product','U','/product/external/create_negative_external_product.zul',16,'Create Negative External Product','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (136,21,'Delete Negative External Product','U','Delete Negative External Product',17,'Delete Negative External Product','N',0);

/* **********************
 * Report
 * **********************/
-- for Stock take for inventory
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (458,401,'Inventory','U','Inventory',10,'Inventory','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (459,458,'Stock Take for Inventory Items','U','/report/stock_take_for_inventory_items.zul?rsrcId=458',3,'Stock Take for Inventory Items','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 458, 'PDF');
-- reassign inventory reports to inventory category
UPDATE SATB_RESOURCE set PAR_RSRC_ID = 458, SEQUENCE = 1 where RSRC_ID = 445;
UPDATE SATB_RESOURCE set PAR_RSRC_ID = 458, SEQUENCE = 2 where RSRC_ID = 446;
-- for Item Type Revenue and Profit Report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (460,429,'Item Type Revenue and Profit Report','U','/report/item_type_revenue_profit_report.zul?rsrcId=460',1,'Item Type Revenue and Profit Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 460, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 460, 'CSV');
-- for Monthly Debt Management Report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (461,421,'Monthly Debt Management Report','U','/report/monthly_debt_management_report.zul?rsrcId=461',3,'Monthly Debt Management Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 461, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 461, 'CSV');
-- for Corporate Cutomer Usage Breakdown
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (456,418,'Corporate Customer Usage Breakdown','U',
'/report/corporate_customer_usage_breakdown.zul?rsrcId=456',8,'Corporate Customer Usage Breakdown','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 456, 'CSV');
-- for Premier Service Trip Reconciliation
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (448,412,'Premier Service Trip Reconciliation','U','/report/premier_service_trip_reconciliation.zul?rsrcId=448',14,'Premier Service Trip Reconciliation','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 448, 'CSV');
-- for contact person
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (454,418,'Contact Person','U',
'/report/contact_person.zul?rsrcId=454',7,'Contact Person','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 454, 'CSV');

/* **********************
 * Rewards
 * **********************/
alter table LRTB_GIFT_STOCK add(
	PREVIOUS_PTS number(8),
	BALANCE_PTS number(8),
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

/* **********************
 * Administration
 * **********************/
/*--------------------- AR Control Account (Checked) ---------------------*/
INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(622,602,'Delete AR Control Account','U','Delete AR Control Account',22,'Delete AR Control Account','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(623,602,'Delete AR Control Account Detail','U','Delete AR Control Account Detail',23,'Delete AR Control Account Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(624,602,'View AR Control Account','U','/admin/gl_control_code/view_gl_control_code.zul',24,'View AR Control Account','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(625,602,'View AR Control Account Detail','U','/admin/gl_control_code/view_gl_control_code_detail.zul',25,'View AR Control Account Detail','N',0);

/*--------------------- AR Control Account ---------------------*/

/*--------------------- Bank Payment GL (Check) ------------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(626,602,'Create Bank Payment GL','U','/admin/gl_bank_payment/create_bank_payment.zul',26,'Create Bank Payment GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(627,602,'Create Bank Payment Detail GL','U','/admin/gl_bank_payment/create_bank_payment_detail.zul',27,'Create Bank Payment Detail GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(628,602,'Edit Bank Payment GL','U','/admin/gl_bank_payment/edit_bank_payment.zul',28,'Edit Bank Payment GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(629,602,'Edit Bank Payment Detail GL','U','/admin/gl_bank_payment/edit_bank_payment_detail.zul',29,'Edit Bank Payment Detail GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(630,602,'View Bank Payment GL','U','/admin/gl_bank_payment/view_bank_payment.zul',29,'View Bank Payment GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(631,602,'View Bank Payment Detail GL','U','/admin/gl_bank_payment/view_bank_payment_detail.zul',30,'View Bank Payment Detail GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(632,602,'Delete Bank Payment GL','U','Delete Bank Payment GL',31,'Delete Bank Payment GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(633,602,'Delete Bank Payment Detail GL','U','Delete Bank Payment Detail GL',32,'Delete Bank Payment Detail GL','N',0);

/*--------------------- Bank Payment GL ------------------------*/

/*--------------------- Manage Entity (Checked) --------------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(634,602,'View Entity','U','/admin/entity/view_entity.zul',33,'View Entity','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(635,602,'Delete Entity','U','Delete Entity',34,'Delete Entity','N',0);

/*--------------------- Manage Entity --------------------------*/

/*--------------------- Manage GL Bank (Checked) -------------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(577,602,'View GL Bank','U','/admin/gl_bank/view_gl_bank.zul',35,'View GL Bank','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(578,602,'Delete GL Bank','U','Delete GL Bank',36,'Delete GL Bank','N',0);

/*--------------------- Manage GL Bank -------------------------*/

/*--------------------- Manage Non Billable GL (Checked) -----------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(636,602,'Create Non Billable GL Detail','U','/admin/gl_non_billable/create_non_billable_detail.zul',37,'Create Non Billable GL Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(637,602,'Edit Non Billable GL Detail','U','/admin/gl_non_billable/edit_non_billable_detail.zul',38,'Edit Non Billable GL Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(638,602,'View Non Billable GL','U','/admin/gl_non_billable/view_non_billable.zul',39,'View Non Billable GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(639,602,'View Non Billable GL Detail','U','/admin/gl_non_billable/view_non_billable_detail.zul',40,'View Non Billable GL Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(640,602,'Delete Non Billable GL','U','Delete Non Billable GL',41,'Delete Non Billable GL','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(641,602,'Delete Non Billable GL Detail','U','Delete Non Billable GL Detail',42,'Delete Non Billable GL Detail','N',0);


/*--------------------- Manage Non Billable GL -----------------*/

/*--------------------- Manage Tax Code (Checked) ------------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(642,602,'View Tax Code','U','/admin/gst/view_gst.zul',43,'View Tax Code','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(643,602,'Delete Tax Code','U','Delete Tax Code',44,'Delete Tax Code','N',0);


/*--------------------- Manage Tax Code ------------------------*/

/*--------------------- Manage Transaction Codes (Checked) ---------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(644,602,'View Transaction Code','U','/admin/transaction_code/view_transaction_code.zul',45,'View Transaction Code','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(645,602,'Delete Transaction Code','U','Delete Transaction Code',46,'Delete Transaction Code','N',0);

/*--------------------- Manage Transaction Codes ---------------*/

/*--------------------- Manage Acquirer (Checked)(Completed) ------------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(646,537,'Create Acquirer','U','/admin/non_billable/create_acquirer.zul',47,'Create Acquirer','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(647,537,'Edit Acquirer','U','/admin/non_billable/edit_acquirer.zul',48,'Edit Acquirer','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(648,537,'View Acquirer','U','/admin/non_billable/view_acquirer.zul',49,'View Acquirer','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(649,537,'Delete Acquirer','U','Delete Acquirer',50,'Delete Acquirer','N',0);

/*--------------------- Manage Acquirer ------------------------*/

/*--------------------- Manage Acquirer MDR (Checked)(Completed) --------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(650,537,'Create Acquirer MDR','U','/admin/non_billable/create_acquirer_mdr.zul',51,'Create Acquirer MDR','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(651,537,'Edit Acquirer MDR','U','/admin/non_billable/edit_acquirer_mdr.zul',52,'Edit Acquirer MDR','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(652,537,'View Acquirer MDR','U','/admin/non_billable/view_acquirer_mdr.zul',53,'View Acquirer MDR','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(653,537,'Delete Acquirer MDR','U','Delete Acquirer MDR',54,'Delete Acquirer MDR','N',0);

/*--------------------- Manage Acquirer MDR --------------------*/

/*--------------------- Manage Acquirer Payment Type (Checked)(Completed) -----------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(654,537,'Create Acquirer Payment Type','U','/admin/non_billable/create_acquirer_pymt_type.zul',55,'Create Acquirer Payment Type','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(655,537,'Edit Acquirer Payment Type','U','/admin/non_billable/edit_acquirer_pymt_type.zul',56,'Edit Acquirer Payment Type','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(656,537,'View Acquirer Payment Type','U','/admin/non_billable/view_acquirer_pymt_type.zul',57,'View Acquirer Payment Type','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(657,537,'Delete Acquirer Payment Type','U','Delete Acquirer Payment Type',58,'Delete Acquirer Payment Type','N',0);

/*--------------------- Manage Acquirer Payment Type -----------*/

/*--------------------- Manage Admin Fee Plan (Checked) ------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(658,537,'View Admin Fee Plan','U','/admin/admin_fee/view_admin_fee_plan.zul',59,'View Admin Fee Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(659,537,'View Admin Fee Plan Detail','U','/admin/admin_fee/view_admin_fee_plan_detail.zul',60,'View Admin Fee Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(660,537,'Delete Admin Fee Plan','U','Delete Admin Fee Plan',61,'Delete Admin Fee Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(661,537,'Delete Admin Fee Plan Detail','U','Delete Admin Fee Plan Detail',62,'Delete Admin Fee Plan Detail','N',0);

/*--------------------- Manage Bank (Checked) ------------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(662,537,'View Bank','U','/admin/bank/view_bank.zul',63,'View Bank','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(663,537,'View Bank Detail','U','/admin/bank/view_branch.zul',64,'View Bank Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(664,537,'Delete Bank','U','Delete Bank',65,'Delete Bank','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(665,537,'Delete Bank Detail','U','Delete Bank Detail',66,'Delete Bank Detail','N',0);

/*--------------------- Manage Bank ------------------*/

/*--------------------- Manage Credit Term Plan (Checked) ------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(666,537,'View Credit Term Plan','U','/admin/credit_term/view_credit_term_plan.zul',67,'View Credit Term Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(667,537,'View Credit Term Plan Detail','U','/admin/credit_term/view_credit_term_plan_detail.zul',68,'View Credit Term Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(668,537,'Delete Credit Term Plan','U','Delete Credit Term Plan',69,'Delete Credit Term Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(669,537,'Delete Credit Term Plan Detail','U','Delete Credit Term Plan Detail',70,'Delete Credit Term Plan Detail','N',0);

/*--------------------- Manage Credit Term Plan ------*/

/*--------------------- Manage Early Payment Plan (Checked) ----*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(670,537,'View Early Payment Plan','U','/admin/early_payment/view_early_payment_plan.zul',71,'View Early Payment Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(671,537,'View Early Payment Plan Detail','U','/admin/early_payment/view_early_payment_plan_detail.zul',72,'View Early Payment Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(672,537,'Delete Early Payment Plan','U','Delete Early Payment Plan',73,'Delete Early Payment Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(673,537,'Delete Early Payment Plan Detail','U','Delete Early Payment Plan Detail',74,'Delete Early Payment Plan Detail','N',0);

/*--------------------- Manage Early Payment Plan ----*/

/*--------------------- Manage Late Payment Plan (Checked) -----*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(674,537,'View Late Payment Plan','U','/admin/late_payment/view_late_payment_plan.zul',75,'View Late Payment Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(675,537,'View Late Payment Plan Detail','U','/admin/late_payment/view_late_payment_plan_detail.zul',76,'View Late Payment Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(676,537,'Delete Late Payment Plan','U','Delete Late Payment Plan',77,'Delete Late Payment Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(677,537,'Delete Late Payment Plan Detail','U','Delete Late Payment Plan Detail',78,'Delete Late Payment Plan Detail','N',0);

/*--------------------- Manage Late Payment Plan -----*/

/*--------------------- Manage Product Discount Plan (Checked) -*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(678,537,'View Product Discount Plan','U','/admin/product_discount/view_product_discount_plan.zul',79,'View Product Discount Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(679,537,'View Product Discount Plan Detail','U','/admin/product_discount/view_product_discount_plan_detail.zul',80,'View Product Discount Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(680,537,'Delete Product Discount Plan','U','Delete Product Discount Plan',81,'Delete Product Discount Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(681,537,'Delete Product Discount Plan Detail','U','Delete Product Discount Plan Detail',82,'Delete Product Discount Plan Detail','N',0);

/*--------------------- Manage Product Discount Plan -*/

/*--------------------- Manage Promotion Plan (Checked) --------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(682,537,'Delete Promotion Plan','U','Delete Promotion Plan',83,'Delete Promotion Plan','N',0);

/*--------------------- Manage Promotion Plan --------*/

/*--------------------- Manage Sales Person (Checked) ----------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(683,537,'View Sales Person','U','/admin/sales_person/view_sales_person.zul',84,'View Sales Person','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(684,537,'Delete Sales Person','U','Delete Sales Person',85,'Delete Sales Person','N',0);

/*--------------------- Manage Sales Person ----------*/

/*--------------------- Manage Subscription Fee Plan (Checked) -*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(685,537,'View Subscription Fee Plan','U','/admin/subscription_fee/view_subscription_fee_plan.zul',86,'View Subscription Fee Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(686,537,'View Subscription Fee Plan Detail','U','/admin/subscription_fee/view_subscription_fee_plan_detail.zul',87,'View Subscription Fee Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(687,537,'Delete Subscription Fee Plan','U','Delete Subscription Fee Plan',88,'Delete Subscription Fee Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(688,537,'Delete Subscription Fee Plan Detail','U','Delete Subscription Fee Plan Detail',89,'Delete Subscription Fee Plan Detail','N',0);

/*--------------------- Manage Subscription Fee Plan -*/

/*--------------------- Manage Volume Discount Plan (Checked) --*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(689,537,'View Volume Discount Plan','U','/admin/volume_discount/view_volume_discount_plan.zul',90,'View Volume Discount Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(690,537,'View Volume Discount Plan Detail','U','/admin/volume_discount/view_volume_discount_plan_detail.zul',91,'View Volume Discount Plan Detail','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(691,537,'Delete Volume Discount Plan','U','Delete Volume Discount Plan',92,'Delete Volume Discount Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(692,537,'Delete Volume Discount Plan Detail','U','Delete Volume Discount Plan Detail',93,'Delete Volume Discount Plan Detail','N',0);


/*--------------------- Manage Volume Discount Plan --*/

/*--------------------- Manage Gifts (Checked) -----------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(693,514,'Delete Gift Category','U','Delete Gift Category',15,'Delete Gift Category','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(694,514,'Delete Gift Item','U','Delete Gift Item',16,'Delete Gift Item','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(695,514,'Edit Gift Category','U','/rewards/edit_category.zul',17,'Edit Gift Category','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(696,514,'View Gift Item','U','/rewards/view_item.zul',18,'View Gift Item','N',0);
   
/*--------------------- Manage Gifts -----------------*/

/*--------------------- Manage Loyalty Plans (Checked) ---------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(697,514,'Delete Loyalty Plan','U','Delete Loyalty Plan',19,'Delete Loyalty Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(698,514,'Edit Loyalty Plan','U','/rewards/edit_plan.zul',20,'Edit Loyalty Plan','N',0);

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(699,514,'View Loyalty Plan Detail','U','/rewards/view_plan_detail.zul',21,'View Loyalty Plan Detail','N',0);

/*--------------------- Manage Loyalty Plans ---------*/

/*--------------------- Manage Inventory -------------*/

INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(700,529,'Delete Inventory Item Type','U','Delete Inventory Item Type',3,'Delete Inventory Item Type','N',0);


/*--------------------- Manage Inventory -------------*/