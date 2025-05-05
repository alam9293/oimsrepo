/* *************************************
 * NON BILLABLE GL
 * ************************************/

alter table fmtb_non_billable_master add (
	pymt_type_master_no number(8)
);

update fmtb_non_billable_master set pymt_type_master_no = (
	select master_no from mstb_master_table
	where master_type ='APT' and master_code = fmtb_non_billable_master.pymt_type
);

alter table fmtb_non_billable_master modify(
	pymt_type_master_no number(8) not null
);

alter table fmtb_non_billable_master drop column pymt_type;

ALTER TABLE fmtb_non_billable_master ADD CONSTRAINT fmtb_non_billable_master_FK2 FOREIGN KEY (pymt_type_master_no) REFERENCES MSTB_MASTER_TABLE (master_no) ENABLE;
CREATE INDEX fmtb_non_billable_master_N2 ON fmtb_non_billable_master(pymt_type_master_no);

/* *************************************
 * BANK PAYMENT ADVISE GL
 * ************************************/

alter table FMTB_BANK_PAYMENT_MASTER drop column pymt_type;

--this part u have to update the data accordingly
alter table FMTB_BANK_PAYMENT_MASTER add (
	acquirer_no number(8) not null
);

ALTER TABLE FMTB_BANK_PAYMENT_MASTER ADD CONSTRAINT FMTB_BANK_PAYMENT_MASTER_FK1 FOREIGN KEY (acquirer_no) REFERENCES MSTB_ACQUIRER (acquirer_no) ENABLE;
CREATE INDEX FMTB_BANK_PAYMENT_MASTER_N1 ON FMTB_BANK_PAYMENT_MASTER(acquirer_no);

-- TEST DATA (Feel free to use them)
/*
*******************************************
* GL CODE FOR NON BILLABLE AND BANK PAYMENT
*******************************************
TXN TYPE IS USED TO DETERMINE THE DROPDOWN LIST FOR MASTER SCREEN FOR NON BILLABLE GL MAPPING AND BANK PAYMENT ADVISE GL MAPPING
'NBCB' | 'NC' - NON BILLABLE CHARGEBACK
'BPCB' | 'BC' - BANK PAYMENT CHARGEBACK
'BPAMT' | 'BA' - BANK PAYMENT AMOUNT

INSERT INTO FMTB_TRANSACTION_CODE (TRANSACTION_CODE_NO,ENTITY_NO,PRODUCT_TYPE_ID,TXN_CODE,DESCRIPTION,TXN_TYPE,GL_CODE,COST_CENTRE,DISCOUNTABLE,DISCOUNT_GL_CODE,DISCOUNT_COST_CENTRE,EFFECTIVE_DATE,TAX_TYPE,IS_MANUAL,VERSION)
VALUES (FMTB_TRANSACTION_CODE_SQ1.nextVal,1,null,'NBCB','CHARGEBACK','NC','308329','BCCACCA0','N',null,null,sysdate,46,'N',0);

INSERT INTO FMTB_TRANSACTION_CODE (TRANSACTION_CODE_NO,ENTITY_NO,PRODUCT_TYPE_ID,TXN_CODE,DESCRIPTION,TXN_TYPE,GL_CODE,COST_CENTRE,DISCOUNTABLE,DISCOUNT_GL_CODE,DISCOUNT_COST_CENTRE,EFFECTIVE_DATE,TAX_TYPE,IS_MANUAL,VERSION)
VALUES (FMTB_TRANSACTION_CODE_SQ1.nextVal,1,null,'BPCB','CHARGEBACK','BC','308329','BCCACCA0','N',null,null,sysdate-1,46,'N',0);

INSERT INTO FMTB_TRANSACTION_CODE (TRANSACTION_CODE_NO,ENTITY_NO,PRODUCT_TYPE_ID,TXN_CODE,DESCRIPTION,TXN_TYPE,GL_CODE,COST_CENTRE,DISCOUNTABLE,DISCOUNT_GL_CODE,DISCOUNT_COST_CENTRE,EFFECTIVE_DATE,TAX_TYPE,IS_MANUAL,VERSION)
VALUES (FMTB_TRANSACTION_CODE_SQ1.nextVal,1,null,'BPAMT','CHARGEBACK','BA','308329','BCCACCA0','N',null,null,sysdate-1,46,'N',0);
*/


/******************************************
* FOR REPORT
*******************************************/
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (440,401,'Non Billable Reports','U','Non Billable Reports',9,'Non Billable','Y',0);
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/bank_chargeback_report.zul?rsrcId=433';
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/cashless_aging_report_detailed.zul?rsrcId=434';
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/cashless_aging_report_summary.zul?rsrcId=435';
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/cashless_bank_collection_summary.zul?rsrcId=436';
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/cashless_bank_collection_detailed.zul?rsrcId=437';
update SATB_RESOURCE set par_rsrc_id = '440' where uri = '/report/cashless_txn_by_amt_range.zul?rsrcId=438';
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (441,440,'Cashless Collection Statistics','U','/report/cashless_collection_statistics.zul?rsrcId=441',10,'Cashless Collection Statistics','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 441, 'CSV');


/******************************************
* non billable master management
*******************************************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (613,537,'Manage Acquirer','U','/admin/non_billable/manage_acquirer.zul',14,'Manage Acquirer','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (614,537,'Manage Acquirer Payment Type','U','/admin/non_billable/manage_acquirer_pymt_type.zul',15,'Manage Acquirer Payment Type','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (615,537,'Manage Acquirer MDR','U','/admin/non_billable/manage_acquirer_mdr.zul',15,'Manage Acquirer MDR','Y',0);


/******************************************
* SNAPSHOT CONTACT PERSON DURING REDEMPTION
*******************************************/
alter table LRTB_REWARD_TXN add(
	contact_person_no number(8) default 1 not null 
);
ALTER TABLE LRTB_REWARD_TXN ADD CONSTRAINT LRTB_REWARD_TXN_FK5 FOREIGN KEY (contact_person_no) REFERENCES AMTB_CONTACT_PERSON (contact_person_no) ENABLE;
CREATE INDEX LRTB_REWARD_TXN_N5 ON LRTB_REWARD_TXN(contact_person_no);

/* ***************
 * GL
 * ***************/

alter FMTB_GL_LOG_DETAIL add(
	BATCH_ID decimal(18),
	BANK_PAYMENT_NO decimal(18),
	CHARGEBACK_TXN_NO decimal(22)
);

CREATE INDEX FMTB_GL_LOG_DETAIL_N7 ON FMTB_GL_LOG_DETAIL(BANK_PAYMENT_NO);
CREATE INDEX FMTB_GL_LOG_DETAIL_N6 ON FMTB_GL_LOG_DETAIL(CHARGEBACK_TXN_NO);
CREATE INDEX FMTB_GL_LOG_DETAIL_N5 ON FMTB_GL_LOG_DETAIL(BATCH_ID);

ALTER TABLE FMTB_GL_LOG_DETAIL ADD CONSTRAINT FMTB_GL_LOG_DETAIL_FK5 FOREIGN KEY (BATCH_ID) REFERENCES TMTB_NON_BILLABLE_BATCH (BATCH_ID) ENABLE;
ALTER TABLE FMTB_GL_LOG_DETAIL ADD CONSTRAINT FMTB_GL_LOG_DETAIL_FK6 FOREIGN KEY (CHARGEBACK_TXN_NO) REFERENCES TMTB_NON_BILLABLE_TXN (TXN_NO) ENABLE;
ALTER TABLE FMTB_GL_LOG_DETAIL ADD CONSTRAINT FMTB_GL_LOG_DETAIL_FK7 FOREIGN KEY (BANK_PAYMENT_NO) REFERENCES BMTB_BANK_PAYMENT (PAYMENT_NO) ENABLE;