/* *******************
 * REWARDS
 * *******************/
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RGP','GRACE',null,'3','A',0);

alter table LRTB_REWARD_TXN add(
	BILLED_FLAG varchar2(1) default 'N' not null
);
CREATE INDEX LRTB_REWARD_TXN_N6 ON LRTB_REWARD_TXN(BILLED_FLAG);

alter table LRTB_REWARD_ACCOUNT add(
	EXPIRED_BILLED_FLAG varchar2(1) default 'N' not null
);
CREATE INDEX LRTB_REWARD_ACCOUNT_N2 ON LRTB_REWARD_ACCOUNT(EXPIRED_BILLED_FLAG, EXPIRE_DT);

alter table LRTB_GIFT_STOCK add(
	SERIAL_NO_START number(25),
   	SERIAL_NO_END number(25)
);

alter table BMTB_INVOICE_HEADER add(
	EXPIRED_REWARDS_PTS number(8) DEFAULT 0,
	ADJUSTED_REWARDS_PTS number(8) DEFAULT 0
);
alter table BMTB_DRAFT_INV_HEADER add(
	EXPIRED_REWARDS_PTS number(8) DEFAULT 0,
	ADJUSTED_REWARDS_PTS number(8) DEFAULT 0
);

/* *******************
 * REWARDS BILL GEN
 * *******************/
alter table BMTB_BILL_GEN_REQ add(
	VD_FLAG varchar2(1),
	RWD_FLAG varchar2(1)
);

/* *******************
 * REPORTS
 * *******************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (448,412,'Premier Service Trip Reconciliation','U','/report/premier_service_trip_reconciliation.zul?rsrcId=448',14,'Premier Service Trip Reconciliation','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 448, 'CSV');

UPDATE SATB_RESOURCE set RSRC_NAME='Trip Reconciliation',URI='/report/trip_reconciliation.zul?rsrcId=448',DISPLAY_NAME='Trip Reconciliation' WHERE RSRC_ID=448;

-- for contact person report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (454,418,'Contact Person','U', '/report/contact_person.zul?rsrcId=454',7,'Contact Person','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 454, 'CSV');
-- for card statistics report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (452,415,'Card Statistics Report','U','/report/card_statistics_report.zul?rsrcId=452',3,'Card Statistics Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 452, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 452, 'XLS');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 452, 'CSV');
-- for new accounts revenue report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (453,429,'New Accounts Revenue','U','/report/new_accounts_revenue.zul?rsrcId=453',2,'New Accounts Revenue','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 453, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 453, 'PDF');
-- for Refund Deposit
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (455,418,'Refund Deposit Report','U','/report/refund_deposit_report.zul?rsrcId=454',3,'Refund Deposit Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 455, 'CSV');
-- for financial memo
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (457,418,'Financial Memo Report','U','/report/financial_memo_report.zul?rsrcId=457',10,'Financial Memo Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 457, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 457, 'PDF');


/* *******************
 * for sms indicator
 * *******************/
ALTER TABLE AMTB_APPLICATION ADD "SMS_FLAG" VARCHAR(1);
ALTER TABLE AMTB_ACCOUNT ADD "SMS_FLAG" VARCHAR(1);

/* *************************
 * ENTITY EFFECTIVE END DATE
 * *************************/
alter table FMTB_ENTITY_MASTER add(
	EFFECTIVE_END_DATE date
);

/* *************************
 * PREPAID
 * *************************/
ALTER TABLE PMTB_PRODUCT_TYPE ADD(
	PREPAID varchar2(1) DEFAULT 'N'  NOT NULL
);

ALTER TABLE PMTB_PRODUCT ADD(
	EXPIRY_TIME TIMESTAMP,
	INITIAL_PAID_VALUE NUMBER(12,2),
	INITIAL_VALUE_PLUS NUMBER(12,2),
	PAID_VALUE NUMBER(12,2),
	VALUE_PLUS NUMBER(12,2)
);

ALTER TABLE PMTB_PRODUCT_REPLACEMENT ADD(
	TRANSFER_VALUE NUMBER(12,2),
	CURRENT_EXP_TIME timestamp,
	NEW_EXP_TIME timestamp,
	PAID_VALUE NUMBER(12,2),
	VALUE_PLUS NUMBER(12,2),
	NEW_STATUS VARCHAR2(2),
	OLD_CARD_NO VARCHAR2(19)
);

ALTER TABLE PMTB_PRODUCT_RENEW ADD(
	CURRENT_EXP_TIME timestamp,
	NEW_EXP_TIME timestamp
);

--FOR REPLACEMENT RECORD WHEN CREATING NEW PP CARDS 
INSERT INTO MSTB_MASTER_TABLE VALUES (-1,'RR','NPPC',null,'NEW PREPAID CARD','I',0);

--PREPAID REPORT
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (447,(select RSRC_ID from SATB_RESOURCE where uri = 'Product'),'Prepaid Product','U','/report/prepaid_product.zul?rsrcId=447',3,'Prepaid Product','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 447, 'CSV');

/* *************************
 * INVENTORY
 * *************************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES 
(621,21,'Search Issuance Request (Sales of Taxi Vouchers)','U','/inventory/search_issuance_request.zul',14,'Sales of Taxi Vouchers','Y',0);

alter table IMTB_ISSUE_REQ add(
	CONTACT_PERSON_NO number(8) not null,
	EXPIRY_DATE date not null,
	HANDLING_FEE number(12,2) not null,
	DISCOUNT number(12,2) not null,
	DELIVERY_CHARGES number(12,2) not null,
	SERIAL_NO_START number(25) not null,
   	SERIAL_NO_END number(25) not null
);
ALTER TABLE IMTB_ISSUE_REQ ADD CONSTRAINT IMTB_ISSUE_REQ_FK4 FOREIGN KEY (CONTACT_PERSON_NO) REFERENCES AMTB_CONTACT_PERSON (CONTACT_PERSON_NO) ENABLE;
alter table IMTB_ISSUE_REQ MODIFY quantity number(18);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'DC','DC',null,'DELIVERY CHARGES','A',0);

alter table IMTB_ISSUE add(
	DELIVERY_CHARGES number(12,2) not null
);