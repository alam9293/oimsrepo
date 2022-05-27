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
ALTER TABLE LRTB_REWARD_ADJ_REQ ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FK1 FOREIGN KEY (ACCOUNT_NO) REFERENCES AMTB_ACCOUNT (ACCOUNT_NO) ENABLE;
ALTER TABLE LRTB_REWARD_ADJ_REQ ADD CONSTRAINT LRTB_REWARD_ADJ_REQ_FK2 FOREIGN KEY (MASTER_NO) REFERENCES MSTB_MASTER_TABLE (MASTER_NO) ENABLE;
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

alter table LRTB_REWARD_TXN add(
	ADJ_REQ_NO number(8)
);
ALTER TABLE LRTB_REWARD_TXN ADD CONSTRAINT LRTB_REWARD_TXN_FK6 FOREIGN KEY (ADJ_REQ_NO) REFERENCES LRTB_REWARD_ADJ_REQ (ADJ_REQ_NO) ENABLE;

--@GH: Please amend accordingly
/*
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RADJR','R1',null,'REASON 1','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'RADJR','R2',null,'REASON 2','A',0);
*/

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (139,514,'Create Adjustment Req','U','/rewards/adjustment/adjustment_req.zul',22,'Create Adjustment Req','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (140,514,'View Pending Adjustment Req','U','/rewards/adjustment/view_pending_req.zul',23,'View Pending Adjustment Req','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (141,514,'Approve Adjustment Req','U','/rewards/adjustment/approve_req.zul',24,'Approve Adjustment Req','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (142,514,'View Adjustment Req History','U','/rewards/adjustment/search_req.zul',25,'View Adjustment Req History','N',0);

--To cater for email contents
alter table MSTB_MASTER_TABLE modify MASTER_VALUE varchar2(600);

--@GH: Amending accordingly
--Emails
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES 
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAS','SUBJ',null,'[IBS-APPL] - Rewards Adjustment Request','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES 
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAS','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>A Rewards Adjustment Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submiter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES 
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAA','SUBJ',null,'[IBS-APPL] - Rewards Adjustment Request','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAA','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Rewards Adjustment Request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>To check your Rewards Adjustment Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES 
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAR','SUBJ',null,'[IBS-APPL] - Rewards Adjustment Request','A',0);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES 
(MSTB_MASTER_TABLE_SQ1.nextVal,'EMRAR','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Rewards Adjustment Request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>To check your Rewards Adjustment, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0);

/* *******************
 * Reports
 * *******************/
-- for prepaid movement report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (462,415,'Movement Report','U','/report/movement_report.zul?rsrcId=462',5,'Movement Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 462, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 462, 'CSV');