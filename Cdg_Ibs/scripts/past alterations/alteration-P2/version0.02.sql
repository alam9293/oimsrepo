/*************************
 * Bill Gen Entity Feature
 * ************************/
--add column
alter table BMTB_BILL_GEN_REQ add ( entity_no number(8) );
--update all records to map to the current entity used assuming 1
update BMTB_BILL_GEN_REQ set entity_no = 1 where
req_no in (
	select req_no
	from BMTB_BILL_GEN_REQ req
	inner join BMTB_BILL_GEN_SETUP setup on req.SETUP_NO = setup.SETUP_NO
	where setup.SETUP_NO not in (4, 5)
	-- ad hoc and draft
);
--add foreign key constraint
ALTER TABLE BMTB_BILL_GEN_REQ ADD CONSTRAINT BMTB_BILL_GEN_REQ_FK4 FOREIGN KEY (entity_no) REFERENCES FMTB_ENTITY_MASTER (entity_no) ENABLE;

/*************************
 * Bill Gen Time Feature
 * ************************/
--add column
alter table BMTB_BILL_GEN_REQ add ( bill_gen_time varchar2(5) );
--update any existing pending request to have the time populated according to their setup
update BMTB_BILL_GEN_REQ set BILL_GEN_TIME = 
	(select BILL_GEN_TIME from BMTB_BILL_GEN_SETUP where SETUP_NO = BMTB_BILL_GEN_REQ.SETUP_NO)
where STATUS = 'P'

/*************************
 * Resources for Administration
 * ************************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (537,0,'Administraton','U','Administration',99,'Administration','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (538,537,'Manage Master List','U','/admin/manage_master_list.zul',99,'Manage Master List','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (584,537,'Manage Bank','U','/admin/bank/manage_bank.zul',11,'Manage Bank','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (585,537,'Create Bank','U','/admin/bank/create_bank.zul',99,'Create Bank','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (586,537,'Edit Bank','U','/admin/bank/edit_bank.zul',99,'Edit Bank','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (587,537,'Add Bank Detail','U','/admin/bank/create_branch.zul',99,'Add Bank Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (588,537,'Edit Bank Detail','U','/admin/bank/edit_branch.zul',99,'Edit Bank Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (592,537,'Manage Sales Persons','U','/admin/sales_person/manage_sales_person.zul',13,'Manage Sales Persons','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (593,537,'Create Sales Person','U','/admin/sales_person/create_sales_person.zul',99,'Create Sales Person','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (594,537,'Edit Sales Person','U','/admin/sales_person/edit_sales_person.zul',99,'Edit Sales Person','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (602,537,'General Ledger','U','General Ledger',98,'General Ledger','Y',0);

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (539,602,'Manage Volume Discount Plan','U','/admin/volume_discount/manage_volume_discount_plan.zul',2,'Manage Volume Discount Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (540,602,'Create Volume Discount Plan','U','/admin/volume_discount/create_volume_discount_plan.zul',99,'Create Volume Discount Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (541,602,'Edit Volume Discount Plan','U','/admin/volume_discount/edit_volume_discount_plan.zul',99,'Edit Volume Discount Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (542,602,'Add Volume Discount Plan Detail','U','/admin/volume_discount/create_volume_discount_plan_detail.zul',99,'Add Volume Discount Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (543,602,'Edit Volume Discount Plan Detail','U','/admin/volume_discount/edit_volume_discount_plan_detail.zul',99,'Edit Volume Discount Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (544,602,'Manage Product Discount Plan','U','/admin/product_discount/manage_product_discount_plan.zul',3,'Manage Product Discount Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (545,602,'Create Product Discount Plan','U','/admin/product_discount/create_product_discount_plan.zul',99,'Create Product Discount Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (546,602,'Edit Product Discount Plan','U','/admin/product_discount/edit_product_discount_plan.zul',99,'Edit Product Discount Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (547,602,'Add Product Discount Plan Detail','U','/admin/product_discount/create_product_discount_plan_detail.zul',99,'Add Product Discount Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (548,602,'Edit Product Discount Plan Detail','U','/admin/product_discount/edit_product_discount_plan_detail.zul',99,'Edit Product Discount Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (549,602,'Manage Admin Fee Plan','U','/admin/admin_fee/manage_admin_fee_plan.zul',4,'Manage Admin Fee Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (550,602,'Create Admin Fee Plan','U','/admin/admin_fee/create_admin_fee_plan.zul',99,'Create Admin Fee Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (551,602,'Edit Admin Fee Plan','U','/admin/admin_fee/edit_admin_fee_plan.zul',99,'Edit Admin Fee Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (552,602,'Add Admin Fee Plan Detail','U','/admin/admin_fee/create_admin_fee_plan_detail.zul',99,'Add Admin Fee Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (553,602,'Edit Admin Fee Plan Detail','U','/admin/admin_fee/edit_admin_fee_plan_detail.zul',99,'Edit Admin Fee Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (554,602,'Manage Credit Term Plan','U','/admin/credit_term/manage_credit_term_plan.zul',5,'Manage Credit Term Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (555,602,'Create Credit Term Plan','U','/admin/credit_term/reate_credit_term_plan.zul',99,'Create Credit Term Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (556,602,'Edit Credit Term Plan','U','/admin/credit_term/edit_credit_term_plan.zul',99,'Edit Credit Term Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (557,602,'Add Credit Term Plan Detail','U','/admin/credit_term/create_credit_term__plandetail.zul',99,'Add Credit Term Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (558,602,'Edit Credit Term Plan Detail','U','/admin/credit_term/edit_credit_term__plandetail.zul',99,'Edit Credit Term Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (559,602,'Manage Early Payment Plan','U','/admin/early_payment/manage_early_payment_plan.zul',6,'Manage Early Payment Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (560,602,'Create Early Payment Plan','U','/admin/early_payment/create_early_payment_plan.zul',99,'Create Early Payment Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (561,602,'Edit Early Payment Plan','U','/admin/early_payment/edit_early_payment_plan.zul',99,'Edit Early Payment Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (562,602,'Add Early Payment Plan Detail','U','/admin/early_payment/create_early_payment_plan_detail.zul',99,'Add Early Payment Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (563,602,'Edit Early Payment Plan Detail','U','/admin/early_payment/edit_early_payment_plan_detail.zul',99,'Edit Early Payment Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (564,602,'Manage Late Payment Plan','U','/admin/late_payment/manage_late_payment_plan.zul',7,'Manage Late Payment Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (565,602,'Create Late Payment Plan','U','/admin/late_payment/create_late_payment_plan.zul',99,'Create Late Payment Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (566,602,'Edit Late Payment Plan','U','/admin/late_payment/edit_late_payment_plan.zul',99,'Edit Late Payment Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (567,602,'Add Late Payment Plan Detail','U','/admin/late_payment/create_late_payment_plan_detail.zul',99,'Add Late Payment Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (568,602,'Edit Late Payment Plan Detail','U','/admin/late_payment/edit_late_payment_plan_detail.zul',99,'Edit Late Payment Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (569,602,'Manage Subscription Fee Plan','U','/admin/subscription_fee/manage_subscription_fee_plan.zul',8,'Manage Subscription Fee Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (570,602,'Create Subscription Fee Plan','U','/admin/subscription_fee/create_subscription_fee_plan.zul',99,'Create Subscription Fee Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (571,602,'Edit Subscription Fee Plan','U','/admin/subscription_fee/edit_subscription_fee_plan.zul',99,'Edit Subscription Fee Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (572,602,'Add Subscription Fee Plan Detail','U','/admin/subscription_fee/create_subscription_fee_plan_detail.zul',99,'Add Subscription Fee Plan Detail','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (573,602,'Edit Subscription Fee Plan Detail','U','/admin/subscription_fee/edit_subscription_fee_plan_detail.zul',99,'Edit Subscription Fee Plan Detail','N',0);

