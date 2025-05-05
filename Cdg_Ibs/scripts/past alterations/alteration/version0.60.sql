----------------------
-- Printing Invoice --
----------------------
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (118,29,'Print Invoice','U','/billing/invoice/print_invoice.zul',13,'Print Invoice','Y',0);

create table BMTB_INVOICE_PRINT_REQ
(
	REQ_ID NUMBER(18) primary key not null,
	BILL_GEN_REQ_NO NUMBER(9),
	CUST_NO_FROM NUMBER(6),
	CUST_NO_TO NUMBER(6),
	INVOICE_NO_FROM NUMBER(18),
	INVOICE_NO_TO NUMBER(18),
	CREATED_BY varchar2(80),
	CREATED_DT timestamp
)
;

CREATE SEQUENCE  "BMTB_INVOICE_PRINT_REQ_SQ1"  MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;

-- To create the inital record inside the master table setting.
-- Subsequently, you can add in new records thru the manage master listing module
-- Slightly different for printers, because we need the full name so the master_code will be just for reference.
-- The master_value will be the one used by the system to send the print job.
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO, MASTER_TYPE, MASTER_CODE, INTERFACE_MAPPING_VALUE, MASTER_VALUE, MASTER_STATUS) VALUES (119, 'PRT', 'TEST', null, 'TEST PRINTER', 'A');

-------------
---- END ----
-------------

INSERT INTO "IBS_USR"."MSTB_MASTER_TABLE" (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (115,'FAX','FAX',null,'12353334','A',0);
INSERT INTO "IBS_USR"."MSTB_MASTER_TABLE" (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (116,'TEL','TEL',null,'97596902','A',0);

--FOR CUST PORTAL LOGIN
create table ITTB_CP_LOGIN
(
	login_id varchar2(80) primary key not null,
	login_type varchar2(3) not null,
	login_method varchar2(1) not null,
	product_no decimal(19),
	contact_person_no decimal(8),
	status varchar2(1) not null,
	CREATED_DT timestamp,
    UPDATED_DT timestamp,
    UPDATED_BY varchar2(80),
    CREATED_BY varchar2(80),
    VERSION decimal(9) DEFAULT 0
);

CREATE INDEX ITTB_CP_LOGIN_N1 ON ITTB_CP_LOGIN(product_no);
CREATE INDEX ITTB_CP_LOGIN_N2 ON ITTB_CP_LOGIN(contact_person_no);
COMMENT ON COLUMN ITTB_CP_LOGIN.login_type IS 'HS - Hotel System CP – Customer Portal HCP – Hotel Customer Portal';
COMMENT ON COLUMN ITTB_CP_LOGIN.login_method IS 'P – Product No Login C – Contact Person Login ';
COMMENT ON COLUMN ITTB_CP_LOGIN.product_no IS 'In the event of card replacement or recycle, this will be updated.';
COMMENT ON COLUMN ITTB_CP_LOGIN.contact_person_no IS 'New main contact for an account, this will be updated.';
COMMENT ON COLUMN ITTB_CP_LOGIN.status IS 'A = Active D = Deleted';
ALTER TABLE ITTB_CP_LOGIN ADD CONSTRAINT ITTB_CP_LOGIN_FK1 FOREIGN KEY (product_no) REFERENCES PMTB_PRODUCT (product_no) ENABLE;
ALTER TABLE ITTB_CP_LOGIN ADD CONSTRAINT ITTB_CP_LOGIN_FK2 FOREIGN KEY (contact_person_no) REFERENCES AMTB_CONTACT_PERSON (contact_person_no) ENABLE;