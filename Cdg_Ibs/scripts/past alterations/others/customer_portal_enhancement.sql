ALTER TABLE ITTB_CP_LOGIN
  ADD LOGIN_NO NUMBER(8);

CREATE SEQUENCE ITTB_CP_LOGIN_SQ1 START WITH 1 INCREMENT BY 1;

merge into ittb_cp_login tf
    using (select * from ittb_cp_login) q
    on (tf.login_id = q.login_id)
    when matched then
      update
      set    tf.login_no=ittb_cp_login_sq1.nextval
    when not matched then
      insert
        (tf.login_id)
      values
        (0);
        
ALTER TABLE ITTB_CP_LOGIN
  MODIFY LOGIN_NO NOT NULL;

ALTER TABLE ITTB_CP_LOGIN
  ADD CONSTRAINT ITTB_CP_LOGIN_UQ UNIQUE (LOGIN_NO);

ALTER TABLE PMTB_PRODUCT_TYPE
  ADD MIN_TOPUP_LIMIT NUMBER(4)
  ADD MAX_TOPUP_LIMIT NUMBER(4);

CREATE TABLE ITTB_CP_PYMT_REQ_SUM
(
PYMT_REQ_ID  Number(20)  Not null,
LOGIN_ID  Varchar2(80)  Not null,
LOGIN_NO  Number(8)  Not null,
MID  Varchar2(20)  Not null,
REF_NO  Varchar2(40)  Not null,
CURRENCY  Varchar2(3)  Not null,
TOPUP_AMOUNT  Number(10)  Not null,
STATUS  Varchar2(1)  Not null,
CREATED_BY  Varchar2(80)   Not null,
CREATED_DATE	timestamp(6)	Not null,
UPDATED_BY	Varchar2(80)	Null,
UPDATED_DATE	timestamp(6)	Null,
CONSTRAINT ITTB_CP_PYMT_REQ_SUM_PK1 PRIMARY KEY (PYMT_REQ_ID),
CONSTRAINT ITTB_CP_PYMT_REQ_SUM_FK1 FOREIGN KEY (LOGIN_NO) references ITTB_CP_LOGIN (LOGIN_NO),
CONSTRAINT ITTB_CP_PYMT_REQ_SUM_CC1 CHECK (STATUS IN ('R','C','F','N','E'))
);

COMMENT ON COLUMN ITTB_CP_PYMT_REQ_SUM.STATUS IS 'R ?requesting for payment; C ?payment completed; F ?payment failed; N ?payment incomplete; E ?unable to load payment page';
COMMENT ON COLUMN ITTB_CP_PYMT_REQ_SUM.LOGIN_NO IS 'refering to ITTB_CP_LOGIN';

CREATE SEQUENCE ITTB_CP_PYMT_REQ_SUM_SQ1 START WITH 1 INCREMENT BY 1;

CREATE TABLE ITTB_CP_PYMT_REQ_DET
(
PYMT_REQ_DET_ID  Number(20)  Not null,
PYMT_REQ_ID  Number(20)  Not null,
PRODUCT_NO  Number(19)  Not null,
TOPUP_AMOUNT  Number(10)  Not null,
NAME_ON_PRODUCT  Varchar2(80)  Not null,
CARD_NO  Varchar2(19)  Not null,
PRODUCT_TYPE_ID  Varchar2(2)  Not null,
CREDIT_BALANCE  Number(12,2)  Not null,
EXPIRY_DATE  Date  Not null,
CREATED_BY  Varchar2(80)   Not null,
CREATED_DATE	timestamp(6)	Not null,
UPDATED_BY	Varchar2(80)	Null,
UPDATED_DATE	timestamp(6)	Null,
CONSTRAINT ITTB_CP_PYMT_REQ_DET_PK1 PRIMARY KEY (PYMT_REQ_DET_ID),
CONSTRAINT ITTB_CP_PYMT_REQ_DET_FK1 FOREIGN KEY (PYMT_REQ_ID) REFERENCES ITTB_CP_PYMT_REQ_SUM(PYMT_REQ_ID),
CONSTRAINT ITTB_CP_PYMT_REQ_DET_FK2 FOREIGN KEY (PRODUCT_NO) REFERENCES PMTB_PRODUCT(PRODUCT_NO)
);

CREATE SEQUENCE ITTB_CP_PYMT_REQ_DET_SQ1 START WITH 1 INCREMENT BY 1;

CREATE TABLE ITTB_CP_PYMT_RESP
(
PYMT_RESP_ID  Number(20)  Not null,
PYMT_REQ_ID  Number(20)  Not null,
LOGIN_ID  Varchar2(80)  Not null,
LOGIN_NO  Number(8)  Not null,
ACCOUNT_NO  Number(8)  Not null,
PRODUCT_NO  Number(19)  Not null,
TM_MCode  Number(16)  null,
TM_RefNo  Varchar2(20)  null,
TM_Currency  Varchar2(3)  null,
TM_DebitAmt  Number(10,2)  null,
TM_Status  Varchar2(3)  null,
TM_ErrorMsg  Varchar2(700)  null,
TM_PaymentType Varchar2(3)  null,
TM_ApprovalCode Varchar2(6)  null,
TM_BankRespCode Varchar2(6)  null,
TM_Error Varchar2(20) null,
TM_UserField1 Varchar2(20) null,
TM_UserField2 Varchar2(20) null,
TM_UserField3 Varchar2(40) null,
TM_UserField4 Varchar2(40) null,
TM_UserField5 Varchar2(200) null,
TM_TrnType Varchar2(10)  null,
TM_SubTrnType Varchar2(10)  null,
TM_CCLast4Digit  Number(4) null,
TM_ExpiryDate	  Varchar2(4) null,
TM_RecurrentId  Varchar2(20) null,
TM_SubSequentMCode Number(16) null,
TM_CardNum Varchar2(19) null,
CREATED_BY  Varchar2(80)   Not null,
CREATED_DATE	timestamp(6)	Not null,
UPDATED_BY	Varchar2(80)	Null,
UPDATED_DATE	timestamp(6)	Null,
CONSTRAINT ITTB_CP_PYMT_RESP_PK1 PRIMARY KEY (PYMT_RESP_ID),
CONSTRAINT ITTB_CP_PYMT_RESP_FK1 FOREIGN KEY (PYMT_REQ_ID) REFERENCES ITTB_CP_PYMT_REQ_SUM(PYMT_REQ_ID),
CONSTRAINT ITTB_CP_PYMT_RESP_FK2 FOREIGN KEY (LOGIN_NO) REFERENCES ITTB_CP_LOGIN(LOGIN_NO),
CONSTRAINT ITTB_CP_PYMT_RESP_FK3 FOREIGN KEY (ACCOUNT_NO) REFERENCES AMTB_ACCOUNT(ACCOUNT_NO),
CONSTRAINT ITTB_CP_PYMT_RESP_FK4 FOREIGN KEY (PRODUCT_NO) REFERENCES PMTB_PRODUCT(PRODUCT_NO)
);

COMMENT ON COLUMN ITTB_CP_PYMT_RESP.TM_Status IS 'NO ?unsuccessful; YES ?successful';
COMMENT ON COLUMN ITTB_CP_PYMT_RESP.TM_BankRespCode IS '00 ?successful; NN ?failure reason code';
COMMENT ON COLUMN ITTB_CP_PYMT_RESP.TM_Error IS 'NULL or empty for successful transactions';
COMMENT ON COLUMN ITTB_CP_PYMT_RESP.TM_SubTrnType IS 'UPDATE for UnionPay transactions';
COMMENT ON COLUMN ITTB_CP_PYMT_RESP.TM_CCLast4Digit IS 'Not applicable for UnionPay, deprecated by TM_CardNum';

CREATE SEQUENCE ITTB_CP_PYMT_RESP_SQ1 START WITH 1 INCREMENT BY 1;


CREATE SEQUENCE ITTB_CP_MESSAGE_LOG_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE; 

CREATE TABLE ITTB_CP_MESSAGE_LOG 
( 
        LOG_ID                        NUMBER(18, 0) NOT NULL,                 
        SENDER                        VARCHAR2(8) NOT NULL, 
        RECEIVER                VARCHAR2(8) NOT NULL, 
        INTERFACE_TYPE        VARCHAR2(40) NOT NULL, 
        SENT_TIME                VARCHAR2(19),         
        XML_REQUEST                CLOB, 
        XML_RESPONSE        CLOB, 
        CREATED_BY                VARCHAR2(80) NOT NULL, 
        CREATED_DT                TIMESTAMP  NOT NULL, 
        UPDATED_BY                VARCHAR2(80), 
        UPDATED_DT                TIMESTAMP, 
        CONSTRAINT ITTB_CP_MESSAGE_LOG_PK PRIMARY KEY(LOG_ID) 
        
); 


CREATE SEQUENCE ITTB_CP_REWARD_REQ_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE; 

CREATE TABLE ITTB_CP_REWARD_REQ 
( 
        REQ_NO                NUMBER(8, 0) NOT NULL,                 
        REQ_DT                TIMESTAMP  NOT NULL, 
        ACCOUNT_NO                NUMBER(8, 0) NOT NULL,         
            PENDING_POINTS                NUMBER(8, 0), 
        STATUS                        VARCHAR2(2), 
        REMARKS                        VARCHAR2(255), 
        CREATED_BY                VARCHAR2(80) NOT NULL, 
        CREATED_DT                TIMESTAMP  NOT NULL, 
        UPDATED_BY                VARCHAR2(80), 
        UPDATED_DT                TIMESTAMP, 
        CONSTRAINT ITTB_CP_REWARD_ITEMS_PK PRIMARY KEY(REQ_NO) 
); 


CREATE TABLE ITTB_CP_PYMT_REQ_REVERSAL 
( 
PYMT_REQ_REV_ID  Number(20)  Not null, 
PYMT_REQ_ID  Number(20)  Not null, 
REQ_STATUS  Varchar2(1)  Not null, 
REQUESTED_DATE        timestamp(6)        Not null, 
REQUESTED_BY  Varchar2(80)   Not null, 
SENT_CTR  NUMBER(2) DEFAULT 0 Not null, 
SENT_EMAIL  Varchar2(3) DEFAULT 'N' Not null, 
UPDATED_DATE        timestamp(6)        Null, 
UPDATED_BY        Varchar2(80)        Null, 
VERSION                NUMBER(9) NOT NULL, 
CONSTRAINT ITTB_CP_PYMT_REQ_REVERSAL_PK1 PRIMARY KEY (PYMT_REQ_REV_ID), 
CONSTRAINT ITTB_CP_PYMT_REQ_REVERSAL_FK1 FOREIGN KEY (PYMT_REQ_ID) REFERENCES ITTB_CP_PYMT_REQ_SUM(PYMT_REQ_ID) 
); 
CREATE SEQUENCE ITTB_CP_PYMT_REQ_REVERSAL_SQ1 START WITH 1 INCREMENT BY 1; 




