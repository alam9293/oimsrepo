-- Create SEQUENCE
CREATE SEQUENCE  "AMSQ_SUBSC_PROD_REQ"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 2 CACHE 10 NOORDER  NOCYCLE ;
-- CREDIT REVIEW REQUEST --
CREATE TABLE AMTB_SUBSC_PROD_REQ (
	SUBSC_PROD_REQUEST_NO NUMBER(8) NOT NULL,
	ACCOUNT_NO NUMBER(8) NOT NULL,
	PRODUCT_TYPE_ID VARCHAR2(2) NOT NULL,
	PRODUCT_DISCOUNT_PLAN_NO NUMBER(8),
	REWARD_PLAN_NO NUMBER(8),
	SUBSCRIPTION_FEE_NO NUMBER(8),
	ISSUANCE_FEE_NO NUMBER(8),
	EFFECTIVE_DT TIMESTAMP NOT NULL,
	REQ_BY NUMBER(10),
	REQ_DT TIMESTAMP NOT NULL,
	SUBSC_ACTION VARCHAR2(2) NOT NULL,
	APP_STATUS VARCHAR2(2) NOT NULL,
	APPROVE_BY NUMBER(10),
	APPROVE_DT TIMESTAMP,
	REMARKS VARCHAR2(500),
	CONSTRAINT AMPC_SUBSC_PROD_REQ__REQ_NO PRIMARY KEY(SUBSC_PROD_REQUEST_NO),
	CONSTRAINT AMFC_SUBSC_PROD_REQ__ACCT_NO FOREIGN KEY(ACCOUNT_NO) REFERENCES AMTB_ACCOUNT(ACCOUNT_NO)
);
-- CREATING COMMENTS --
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.SUBSC_PROD_REQUEST_NO IS 'Sequence No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.ACCOUNT_NO IS 'Account No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.PRODUCT_TYPE_ID IS 'Product Type ID';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.PRODUCT_DISCOUNT_PLAN_NO IS 'Discount Plan No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.REWARD_PLAN_NO IS 'Reward Plan No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.SUBSCRIPTION_FEE_NO IS 'Subscription Fee No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.ISSUANCE_FEE_NO IS 'Issuance Fee No';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.EFFECTIVE_DT IS 'Effective Date';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.REQ_BY IS 'Request By';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.REQ_DT IS 'Request Date';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.SUBSC_ACTION IS 'Action (S-Subscribing, E-Edit, U-Unsubscribing, SD-Subscribe Dept/Div, UD - Unsubscribe Dept/Div)';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.APP_STATUS IS 'Approval Status (A-Approve, PA-PendingApprove, R-Reject)';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.APPROVE_BY IS 'Approve By';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.APPROVE_DT IS 'Approve Date';
COMMENT ON COLUMN AMTB_SUBSC_PROD_REQ.REMARKS IS 'Remarks';

-- creating indexes --
CREATE INDEX AMIX_SUBSC_PROD_REQ__ACCT_NO ON AMTB_SUBSC_PROD_REQ (ACCOUNT_NO);




-- EMSRS = Email Subscription Request Submit
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRS','SUBJ','[IBS-APPL] - Subscription Request','A',0,null,'Email');
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRS','CONT','<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>A Subscription Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,null,'Email');
-- EMSRA = Email Subscription Request Approved
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRA','SUBJ','[IBS-APPL] - Subscription Request Approved','A',0,null,'Email');
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRA','CONT','<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Subscription Request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br><br>To check your Subscription Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,null,'Email');
-- EMSRR = Email Subscription Request Rejected
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRR','SUBJ','[IBS-APPL] - Subscription Request Rejected','A',0,null,'Email');
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMSRR','CONT','<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Subscription Request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br><br>To check your Subscription Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,null,'Email');


-- SATB RESOURCE for Approve Prod Subscription
INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Acct Approvals'), 'Approve Subscription', 'U', '/acct/approval/viewPendingSubscriptionRequest.zul', 1, 'View Product Subscription Request', 'Y', 0);
