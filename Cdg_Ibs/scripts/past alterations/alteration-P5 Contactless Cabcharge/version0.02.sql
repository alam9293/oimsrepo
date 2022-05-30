--Manage Issuance Fee Plan
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    (SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
    537,
    'Manage Issuance Fee Plan',
    'U',
    '/admin/issuance_fee/manage_issuance_fee_plan.zul',
    9,
    'Manage Issuance Fee Plan',
    'Y',
    0
  );
--Manage Issuance Fee Plan Roles Resources
insert into SATB_ROLE_RESOURCE select role_id, (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Manage Issuance Fee Plan') from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name='Manage Subscription Fee Plan');



--Create Issuance Fee 
CREATE TABLE MSTB_ISSUANCE_FEE_MASTER
(
    "ISSUANCE_FEE_NO"   NUMBER(8,0) NOT NULL ENABLE,
    "ISSUANCE_FEE_NAME" VARCHAR2(80 BYTE) NOT NULL ENABLE,
    "VERSION"               NUMBER(9,0) DEFAULT 0,
    "CREATED_DT" TIMESTAMP (6),
    "CREATED_BY" VARCHAR2(80 BYTE),
    "UPDATED_DT" TIMESTAMP (6),
    "UPDATED_BY" varchar2(80 byte),
    constraint "MSTB_ISSUANCE_FEE_MASTER_PK" primary key ("ISSUANCE_FEE_NO") enable
);


CREATE TABLE MSTB_ISSUANCE_FEE_DETAIL
(
    "ISSUANCE_FEE_DETAIL_NO" NUMBER(8,0) NOT NULL ENABLE,
    "ISSUANCE_FEE_NO"        NUMBER(8,0) NOT NULL ENABLE,
    "EFFECTIVE_DT" TIMESTAMP (6) NOT NULL ENABLE,
    "ISSUANCE_FEE" NUMBER(10,2) NOT NULL ENABLE,
    "VERSION"          NUMBER(9,0) DEFAULT 0,
    "CREATED_DT" TIMESTAMP (6),
    "CREATED_BY" VARCHAR2(80 BYTE),
    "UPDATED_DT" TIMESTAMP (6),
    "UPDATED_BY" varchar2(80 byte),
    constraint "MSTB_ISSUANCE_FEE_DETAIL_PK" primary key ("ISSUANCE_FEE_DETAIL_NO")enable,
    CONSTRAINT "MSCT_ISSUANCE_FEE_DETAIL_C1" UNIQUE ("ISSUANCE_FEE_NO", "EFFECTIVE_DT")enable,
    constraint "MSTB_ISSUANCE_FEE_DETAIL_FK1" foreign key ("ISSUANCE_FEE_NO") references "MSTB_ISSUANCE_FEE_MASTER" ("ISSUANCE_FEE_NO") enable
);


CREATE SEQUENCE "MSTB_ISSUANCE_FEE_MASTER_SQ1" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

CREATE SEQUENCE "MSTB_ISSUANCE_FEE_DETAIL_SQ1" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

alter table amtb_subsc_to add ISSUANCE_FEE_NO NUMBER(8,0);


alter table AMTB_APPLICATION_PRODUCT add ISSUANCE_FEE_NO NUMBER(8,0);

alter table  AMTB_APPLICATION_PRODUCT add CONSTRAINT AMTB_APPLICATION_PRODUCT_FK6 FOREIGN KEY (ISSUANCE_FEE_NO) REFERENCES MSTB_ISSUANCE_FEE_MASTER (ISSUANCE_FEE_NO) enable;



