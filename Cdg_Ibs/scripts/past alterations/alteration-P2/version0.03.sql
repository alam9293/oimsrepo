/****************************
 * PROMOTION
 * **************************/
create table MSTB_PROMOTION(
	promo_no number(8) primary key,
	name varchar2(80) not null,
	type varchar2(1) not null,
	product_type_id varchar2(2),
	promo_type varchar2(1) not null,
	promo_value number(12,2) not null,
	EFFECTIVE_DT_FROM timestamp,
	EFFECTIVE_DT_TO timestamp,
	job_type number(8),
	vehicle_model number(8),
	CREATED_DT timestamp, 
	CREATED_BY varchar2(80), 
	UPDATED_DT timestamp, 
	UPDATED_BY varchar2(80),
	version number(9) default 0
);
--add foreign key constraint
ALTER TABLE MSTB_PROMOTION ADD CONSTRAINT MSTB_PROMOTION_FK1 FOREIGN KEY (job_type) REFERENCES MSTB_MASTER_TABLE (master_No) ENABLE;
ALTER TABLE MSTB_PROMOTION ADD CONSTRAINT MSTB_PROMOTION_FK2 FOREIGN KEY (vehicle_model) REFERENCES MSTB_MASTER_TABLE (master_No) ENABLE;
ALTER TABLE MSTB_PROMOTION ADD CONSTRAINT MSTB_PROMOTION_FK3 FOREIGN KEY (product_type_id) REFERENCES PMTB_PRODUCT_TYPE (PRODUCT_TYPE_ID) ENABLE;
--sequence
CREATE SEQUENCE MSTB_PROMOTION_SQ1 MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;

--resource
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (603,537,'Manage Promotion Plan','U','/admin/promotion/manage_promotion_plan.zul',1,'Manage Promotion Plan','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (604,537,'Create Promotion Plan','U','/admin/promotion/create_promotion_plan.zul',2,'Create Promotion Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (605,537,'Edit Promotion Plan','U','/admin/promotion/edit_promotion_plan.zul',3,'Edit Promotion Plan','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (606,537,'View Promotion Plan','U','/admin/promotion/view_promotion_plan.zul',4,'View Promotion Plan','N',0);

/*
 * JOB TYPE MASTER TYPE IS JT
 * VEHICLE MODEL MASTER TYPE IS VM 
 * 
 * e.g.
	INSERT INTO MSTB_MASTER_TABLE SELECT
	MAX(MASTER_NO)+1 as MASTER_NO, 'JT' as MASTER_TYPE, 'IMD' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'IMMEDIATE' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
	
	INSERT INTO MSTB_MASTER_TABLE SELECT 
	MAX(MASTER_NO)+1 as MASTER_NO, 'JT' as MASTER_TYPE, 'ADV' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'ADVANCE' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
	
	INSERT INTO MSTB_MASTER_TABLE SELECT
	MAX(MASTER_NO)+1 as MASTER_NO, 'JT' as MASTER_TYPE, 'ST' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'STREET' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
	
	INSERT INTO MSTB_MASTER_TABLE SELECT
	MAX(MASTER_NO)+1 as MASTER_NO, 'VM' as MASTER_TYPE, 'NC' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'NISSAN CEDRIC' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
	
	INSERT INTO MSTB_MASTER_TABLE SELECT
	MAX(MASTER_NO)+1 as MASTER_NO, 'VM' as MASTER_TYPE, 'MC' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'MAXI CAB' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
	
	INSERT INTO MSTB_MASTER_TABLE SELECT
	MAX(MASTER_NO)+1 as MASTER_NO, 'VM' as MASTER_TYPE, 'HS' as MASTER_CODE, null as INTERFACE_MAPPING_VALUE,
	'HYUNDAI SONATA' as MASTER_VALUE, 'A' as MASTER_STATUS, 0 as VERSION FROM MSTB_MASTER_TABLE;
 */

/****************************
 * DOWNLOAD TRIPS TXN
 * **************************/
alter table Ittb_Trips_Txn add(
	VEH_TYPE varchar2(80),
	MID varchar2(15)
);

/****************************
 * Resources for GL modules
 * **************************/
--To update existing resources under GL submenu to one level up
--update satb_resource set PAR_RSRC_ID = 537 where PAR_RSRC_ID = 602

--Insert resources that should be under GL submenu
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (595,602,'Manage Transaction Code','U','/admin/transaction_code/manage_transaction_code.zul',5,'Manage Transaction Code','Y',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (596,602,'Create Transaction Code','U','/admin/transaction_code/create_transaction_code.zul',99,'Create Transaction Code','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (597,602,'Edit Transaction Code','U','/admin/transaction_code/edit_transaction_code.zul',99,'Edit Transaction Code','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (598,602,'Manage Entity','U','/admin/entity/manage_entity.zul',1,'Manage Entity','Y',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (599,602,'Create Entity','U','/admin/entity/create_entity.zul',99,'Create Entity','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (600,602,'Edit Entity','U','/admin/entity/edit_entity.zul',99,'Edit Entity','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (574,602,'Manage GL Bank','U','/admin/gl_bank/manage_gl_bank.zul',2,'Manage GL Bank','Y',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (575,602,'Create GL Bank','U','/admin/gl_bank/create_gl_bank.zul',99,'Create GL Bank','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (576,602,'Edit GL Bank','U','/admin/gl_bank/edit_gl_bank.zul',99,'Edit GL Bank','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (577,602,'Add GL Bank Detail','U','/admin/gl_bank/create_gl_branch.zul',99,'Add GL Bank Detail','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (578,602,'Edit GL Bank Detail','U','/admin/gl_bank/edit_gl_branch.zul',99,'Edit GL Bank Detail','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (579,602,'Manage AR Control Account','U','/admin/gl_control_code/manage_gl_control_code.zul',3,'Manage AR Control Account','Y',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (580,602,'Create AR Control Account','U','/admin/gl_control_code/create_gl_control_code.zul',99,'Create AR Control Account','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (581,602,'Edit AR Control Account','U','/admin/gl_control_code/edit_gl_control_code.zul',99,'Edit AR Control Account','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (582,602,'Add AR Control Account Detail','U','/admin/gl_control_code/create_gl_control_code_detail.zul',99,'Add AR Control Account Detail','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (583,602,'Edit AR Control Account Detail','U','/admin/gl_control_code/edit_gl_control_code_detail.zul',99,'Edit AR Control Account Detail','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (589,602,'Manage Tax Code','U','/admin/gst/manage_gst.zul',4,'Manage Tax Code','Y',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (590,602,'Create Tax Code','U','/admin/gst/create_gst.zul',99,'Create Tax Code','N',0);
INSERT INTO satb_resource (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (591,602,'Edit Tax Code','U','/admin/gst/edit_gst.zul',99,'Edit Tax Code','N',0);

/*********************
 * add 4 common fields
 * *******************/
alter table LRTB_REWARD_DETAIL add(
	CREATED_DT timestamp, 
	CREATED_BY varchar2(80), 
	UPDATED_DT timestamp, 
	UPDATED_BY varchar2(80)
);

alter table LRTB_GIFT_ITEM add(
	CREATED_DT timestamp, 
	CREATED_BY varchar2(80), 
	UPDATED_DT timestamp, 
	UPDATED_BY varchar2(80)
);

/******************************************
 * drop, rename and change constraint logic
 * ****************************************/
alter table LRTB_REWARD_TIER drop constraint LRCT_REWARD_TIER_C1;
alter table LRTB_REWARD_TIER add CONSTRAINT LRTB_REWARD_TIER_C1 CHECK (START_RANGE <= END_RANGE);