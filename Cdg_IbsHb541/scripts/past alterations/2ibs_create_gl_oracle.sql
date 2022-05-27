-- creating seq for entity --
CREATE SEQUENCE  "FMTB_ENTITY_MASTER_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
-- entity --
CREATE TABLE FMTB_ENTITY_MASTER (
	ENTITY_NO NUMBER(8) NOT NULL,
	ENTITY_CODE VARCHAR2(5) NOT NULL,
	ENTITY_NAME VARCHAR2(80) NOT NULL,
	ENTITY_BLOCK VARCHAR2(10),
	ENTITY_UNIT VARCHAR2(10),
	ENTITY_STREET VARCHAR2(60) NOT NULL,
	ENTITY_BUILDING VARCHAR2(60),
	ENTITY_AREA VARCHAR2(35),
	ENTITY_COUNTRY NUMBER(8) NOT NULL,
	ENTITY_POSTAL VARCHAR2(10) NOT NULL,
	ENTITY_CITY VARCHAR2(35),
	ENTITY_STATE VARCHAR2(35),
	ENTITY_GST_NO VARCHAR2(25),
	ENTITY_RCB_NO VARCHAR2(35),
	ENTITY_TEL VARCHAR2(20),
	ENTITY_EMAIL VARCHAR2(255),
	LOGO BLOB,
	CURRENCY_CODE VARCHAR2(3) NOT NULL,
	VERSION NUMBER(9,0) DEFAULT 0,
	CONSTRAINT FMTB_ENTITY_MASTER_PK PRIMARY KEY(ENTITY_NO),
	CONSTRAINT FMTB_ENTITY_MASTER_U1 UNIQUE (ENTITY_CODE),
	CONSTRAINT FMTB_ENTITY_MASTER_FK1 FOREIGN KEY(ENTITY_COUNTRY) REFERENCES MSTB_MASTER_TABLE(MASTER_NO)
);
-- creating indexes --
-- dumping some data into it
INSERT INTO FMTB_ENTITY_MASTER (ENTITY_NO, ENTITY_CODE, ENTITY_NAME, ENTITY_BLOCK, ENTITY_UNIT, ENTITY_STREET, ENTITY_BUILDING, ENTITY_AREA, ENTITY_COUNTRY, ENTITY_POSTAL, ENTITY_CITY, ENTITY_STATE, ENTITY_GST_NO, ENTITY_RCB_NO, CURRENCY_CODE) VALUES (1, 'CCA', 'CABCHARGE ASIA PTE LTD', '383', NULL, 'SIN MING DRIVE', NULL, NULL, 4, '575717', NULL, NULL, 'M2-0054075-1', '198102368C', 'SGD');
--INSERT INTO FMTB_ENTITY_MASTER (ENTITY_NO, ENTITY_CODE, ENTITY_NAME) VALUES (2, 'CCE', 'CABCHARGE EUROPE');
-- creating seq for cost centre --
--CREATE SEQUENCE  "FMTB_COST_CENTRE_MASTER_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 2 CACHE 10 NOORDER  NOCYCLE ;
---- entity --
--CREATE TABLE FMTB_COST_CENTRE_MASTER (
--	COST_CENTRE_NO NUMBER(8) NOT NULL,
--	COST_CENTRE_NAME VARCHAR2(80) NOT NULL,
--	VERSION NUMBER(9,0) DEFAULT 0,
--	CONSTRAINT FMTB_COST_CENTRE_MASTER_PK PRIMARY KEY(COST_CENTRE_NO)
--);
---- dumping some data into it
--INSERT INTO FMTB_COST_CENTRE_MASTER (COST_CENTRE_NO, COST_CENTRE_NAME) VALUES (1, 'VICOM PTE LTD');
-- creating seq for AR CONTROL CODE --
CREATE SEQUENCE  "FMTB_AR_CONT_CODE_MASTER_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
-- AR CONTROL CODE TABLE --
CREATE TABLE FMTB_AR_CONT_CODE_MASTER (
	AR_CONTROL_CODE_NO NUMBER(8) NOT NULL,
	ENTITY_NO NUMBER(8) NOT NULL,
	AR_CONTROL_CODE VARCHAR2(35) NOT NULL,
	VERSION NUMBER(9,0) DEFAULT 0,
	CONSTRAINT FMTB_AR_CONT_CODE_MASTER_FK1 FOREIGN KEY(ENTITY_NO) REFERENCES FMTB_ENTITY_MASTER(ENTITY_NO),
	--CONSTRAINT FMTB_AR_CONT_CODE_MASTER_FK2 FOREIGN KEY(COST_CENTRE) REFERENCES FMTB_COST_CENTRE_MASTER(COST_CENTRE_NO),
	CONSTRAINT FMTB_AR_CONT_CODE_MASTER_PK PRIMARY KEY(AR_CONTROL_CODE_NO)
);
-- creating indexes --
CREATE INDEX FMTB_AR_CONT_CODE_MASTER_N1 ON FMTB_AR_CONT_CODE_MASTER (ENTITY_NO);
CREATE INDEX FMTB_AR_CONT_CODE_MASTER_N2 ON FMTB_AR_CONT_CODE_MASTER(AR_CONTROL_CODE,ENTITY_NO);
--CREATE INDEX FMTB_AR_CONT_CODE_MASTER_N2 ON FMTB_AR_CONT_CODE_MASTER (COST_CENTRE);
-- dumping some data into it
INSERT INTO FMTB_AR_CONT_CODE_MASTER
	(AR_CONTROL_CODE_NO, ENTITY_NO, AR_CONTROL_CODE)
VALUES
	(1, 1, 'VIC');
-- creating seq for AR CONTROL CODE --
CREATE SEQUENCE  "FMTB_AR_CONT_CODE_DETAIL_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
-- AR CONTROL CODE TABLE --
CREATE TABLE FMTB_AR_CONT_CODE_DETAIL (
	AR_CONT_CODE_DETAIL_NO NUMBER(8) NOT NULL,
	AR_CONTROL_CODE_NO NUMBER(8) NOT NULL,
	GL_ACCOUNT NUMBER(8) NOT NULL,
	COST_CENTRE VARCHAR2(80) NOT NULL,
	TAX_TYPE NUMBER(8),
	DESCRIPTION VARCHAR2(60) NOT NULL,
	EFFECTIVE_DT TIMESTAMP,
	VERSION NUMBER(9,0) DEFAULT 0,
	CONSTRAINT FMTB_AR_CONT_CODE_DETAIL_FK1 FOREIGN KEY(AR_CONTROL_CODE_NO) REFERENCES FMTB_AR_CONT_CODE_MASTER(AR_CONTROL_CODE_NO),
	CONSTRAINT FMTB_AR_CONT_CODE_DETAIL_FK2 FOREIGN KEY(TAX_TYPE) REFERENCES MSTB_MASTER_TABLE(MASTER_NO),
	CONSTRAINT FMTB_AR_CONT_CODE_DETAIL_PK PRIMARY KEY(AR_CONT_CODE_DETAIL_NO)
);
-- creating indexes --
CREATE INDEX FMTB_AR_CONT_CODE_DETAIL_N1 ON FMTB_AR_CONT_CODE_DETAIL (AR_CONTROL_CODE_NO);
CREATE INDEX FMTB_AR_CONT_CODE_DETAIL_N2 ON FMTB_AR_CONT_CODE_DETAIL (TAX_TYPE);
-- dumping some data into it
INSERT INTO FMTB_AR_CONT_CODE_DETAIL
	(AR_CONT_CODE_DETAIL_NO, AR_CONTROL_CODE_NO, GL_ACCOUNT, COST_CENTRE, TAX_TYPE, DESCRIPTION, EFFECTIVE_DT)
VALUES
	(1,1,1,1,NULL, 'VICOM', TO_TIMESTAMP('01/01/2009','dd/mm/yyyy'));
-- creating seq for ENTITY CODE --
--CREATE SEQUENCE  "FMTB_ENTITY_CODE_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
---- entity code --
--CREATE TABLE FMTB_ENTITY_CODE (
--	ENTITY_CODE_NO NUMBER(8) NOT NULL,
--	ENTITY_NO NUMBER(8) NOT NULL,
--	GL_CO_CODE VARCHAR2(10) NOT NULL,
--	GL_CURRENCY_CODE VARCHAR2(3) NOT NULL,
--	EFFECTIVE_DATE DATE NOT NULL,
--	VERSION NUMBER(9,0) DEFAULT 0,
--	CONSTRAINT FMTB_ENTITY_CODE_FK1 FOREIGN KEY(ENTITY_NO) REFERENCES FMTB_ENTITY_MASTER(ENTITY_NO),
--	CONSTRAINT FMTB_ENTITY_CODE_PK PRIMARY KEY(ENTITY_CODE_NO)
--);
-- creating indexes --
-- CREATE INDEX FMTB_ENTITY_CODE_N1 ON FMTB_ENTITY_CODE (ENTITY_NO);
-- dumping some data into it
-- creating seq for TAX CODE --
CREATE SEQUENCE  "FMTB_TAX_CODE_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
-- tax code --
CREATE TABLE FMTB_TAX_CODE (
	TAX_CODE_NO NUMBER(8) NOT NULL,
	ENTITY_NO NUMBER(8) NOT NULL,
	GL_TAX_CODE VARCHAR2(3),
	GL_CODE VARCHAR2(10),
	COST_CENTRE VARCHAR2(10),
	TAX_TYPE NUMBER(8),
	TAX_RATE NUMBER(5,2),
	EFFECTIVE_DATE DATE NOT NULL,
	VERSION NUMBER(9,0) DEFAULT 0,
	CONSTRAINT FMTB_TAX_CODE_FK1 FOREIGN KEY(ENTITY_NO) REFERENCES FMTB_ENTITY_MASTER(ENTITY_NO),
	CONSTRAINT FMTB_TAX_CODE_FK2 FOREIGN KEY(TAX_TYPE) REFERENCES MSTB_MASTER_TABLE(MASTER_NO),
	CONSTRAINT FMTB_TAX_CODE_PK PRIMARY KEY(TAX_CODE_NO)
);
-- creating indexes --
CREATE INDEX FMTB_TAX_CODE_N1 ON FMTB_TAX_CODE (ENTITY_NO);
CREATE INDEX FMTB_TAX_CODE_N2 ON FMTB_TAX_CODE (TAX_TYPE);
CREATE INDEX FMTB_TAX_CODE_N3 ON FMTB_TAX_CODE(ENTITY_NO, TAX_TYPE, EFFECTIVE_DATE);
CREATE INDEX FMTB_TAX_CODE_N4 ON FMTB_TAX_CODE(EFFECTIVE_DATE);
-- dumping some data into it
-- creating seq for BANK CODE --
CREATE SEQUENCE  "FMTB_BANK_CODE_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
-- bank code --
CREATE TABLE FMTB_BANK_CODE (
	BANK_CODE_NO NUMBER(8) NOT NULL,
	ENTITY_NO NUMBER(8) NOT NULL,
	BANK_CODE VARCHAR2(10) NOT NULL,
	GL_CODE VARCHAR2(10) NOT NULL,
	COST_CENTRE VARCHAR2(10) NOT NULL,
	TAX_TYPE NUMBER(8),
	BANK_NAME VARCHAR2(20) NOT NULL,
	BRANCH_CODE VARCHAR2(10) NOT NULL,
	BRANCH_NAME VARCHAR2(20) NOT NULL,
	IS_DEFAULT VARCHAR2(1) NOT NULL, -- Y=Yes N=No
	EFFECTIVE_DATE DATE NOT NULL,
	VERSION NUMBER(9,0) DEFAULT 0,
	CONSTRAINT FMTB_BANK_CODE_FK1 FOREIGN KEY(ENTITY_NO) REFERENCES FMTB_ENTITY_MASTER(ENTITY_NO),
	CONSTRAINT FMTB_BANK_CODE_FK2 FOREIGN KEY(TAX_TYPE) REFERENCES MSTB_MASTER_TABLE(MASTER_NO),
	CONSTRAINT FMTB_BANK_CODE_PK PRIMARY KEY(BANK_CODE_NO)
);
-- creating indexes --
CREATE INDEX FMTB_BANK_CODE_N1 ON FMTB_BANK_CODE (ENTITY_NO);
CREATE INDEX FMTB_BANK_CODE_N2 ON FMTB_BANK_CODE (TAX_TYPE);
COMMENT ON COLUMN FMTB_BANK_CODE.IS_DEFAULT IS 'Y=YES N=NO';
-- dumping some data into it
-- creating seq for payment mode --
--CREATE SEQUENCE  "FMTB_PAYMENT_MODE_SQ1"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
---- payment mode --
--CREATE TABLE FMTB_PAYMENT_MODE (
--	PAYMENT_MODE_NO NUMBER(8) NOT NULL,
--	ENTITY_NO NUMBER(8) NOT NULL,
--	PAYMENT_CODE VARCHAR2(10) NOT NULL,
--	EFFECTIVE_DATE DATE NOT NULL,
--	VERSION NUMBER(9,0) DEFAULT 0,
--	CONSTRAINT FMTB_PAYMENT_MODE_FK1 FOREIGN KEY(ENTITY_NO) REFERENCES FMTB_ENTITY_MASTER(ENTITY_NO),
--	CONSTRAINT FMTB_PAYMENT_MODE_PK PRIMARY KEY(PAYMENT_MODE_NO)
--);
---- creating indexes --
--CREATE INDEX FMTB_PAYMENT_MODE_N1 ON FMTB_PAYMENT_MODE (ENTITY_NO);
---- dumping some data into it

------ scripts to clear database
--DROP TABLE FMTB_PAYMENT_MODE;
DROP TABLE FMTB_BANK_CODE;
DROP TABLE FMTB_TAX_CODE;
--DROP TABLE FMTB_ENTITY_CODE;
DROP TABLE FMTB_AR_CONT_CODE_DETAIL;
DROP TABLE FMTB_AR_CONT_CODE_MASTER;
--DROP TABLE FMTB_COST_CENTRE_MASTER;
DROP TABLE FMTB_ENTITY_MASTER;

--DROP SEQUENCE FMTB_PAYMENT_MODE_SQ1;
DROP SEQUENCE FMTB_BANK_CODE_SQ1;
DROP SEQUENCE FMTB_TAX_CODE_SQ1;
--DROP SEQUENCE FMTB_ENTITY_CODE_SQ1;
DROP SEQUENCE FMTB_AR_CONT_CODE_DETAIL_SQ1;
DROP SEQUENCE FMTB_AR_CONT_CODE_MASTER_SQ1;
--DROP SEQUENCE MSTB_COST_CENTRE_MASTER_SQ1;
DROP SEQUENCE FMTB_ENTITY_MASTER_SQ1;