
--*Please note that execution of script should be from top to bottom

--create backup table for mstb_promotion
create table mstb_promotion_backup as select * from mstb_promotion;

alter table mstb_promotion_backup add CONSTRAINT mstb_promotion_backup_pk PRIMARY KEY ("PROMO_NO") USING INDEX;

--create mstb_promo_detail table
CREATE TABLE MSTB_PROMO_DETAIL
  (
    "PROMO_DETAIL_NO"        NUMBER(8,0),
    "PROMO_NO"				 NUMBER(8,0),
    "NAME"            VARCHAR2(80 BYTE) NOT NULL ENABLE,
    "TYPE"            VARCHAR2(1 BYTE) NOT NULL ENABLE,
    "PRODUCT_TYPE_ID" VARCHAR2(2 BYTE),
    "PROMO_TYPE"      VARCHAR2(1 BYTE) NOT NULL ENABLE,
    "PROMO_VALUE"     NUMBER(12,2) NOT NULL ENABLE,
    "EFFECTIVE_DT_FROM" TIMESTAMP (6),
    "EFFECTIVE_DT_TO" TIMESTAMP (6),
    "JOB_TYPE"      NUMBER(8,0),
    "VEHICLE_MODEL" NUMBER(8,0),
    "EFFECTIVE_CUTOFF_DATE" DATE,
    "VERSION"    NUMBER(9,0) DEFAULT 0,
    CONSTRAINT "MSTB_PROMO_DETAIL_PK" PRIMARY KEY ("PROMO_DETAIL_NO") USING INDEX ENABLE,
    CONSTRAINT "MSTB_PROMO_DETAIL_FK1" FOREIGN KEY ("JOB_TYPE") REFERENCES "MSTB_MASTER_TABLE" ("MASTER_NO") ENABLE,
    CONSTRAINT "MSTB_PROMO_DETAIL_FK2" FOREIGN KEY ("VEHICLE_MODEL") REFERENCES "MSTB_MASTER_TABLE" ("MASTER_NO") ENABLE,
    CONSTRAINT "MSTB_PROMO_DETAIL_FK3" FOREIGN KEY ("PRODUCT_TYPE_ID") REFERENCES "PMTB_PRODUCT_TYPE" ("PRODUCT_TYPE_ID") ENABLE,
    CONSTRAINT "MSTB_PROMO_DETAIL_FK4" FOREIGN KEY ("PROMO_NO") REFERENCES "MSTB_PROMOTION" ("PROMO_NO") ENABLE
  )
  ;
  
CREATE SEQUENCE "MSTB_PROMO_DETAIL_SQ1" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

--please ignore at UAT/SIT, rename constraint for primary key constraint of mstb_promotion in production
alter table MSTB_PROMOTION rename constraint SYS_C0031896 to MSTB_PROMOTION_PK;

--create req table
CREATE TABLE MSTB_PROMO_REQ
  (
    "PROMO_REQ_NO"        NUMBER(8,0),
    "PROMO_NO"				     NUMBER(8,0),
    "EVENT" 		 VARCHAR2(12 BYTE) NOT NULL ENABLE,
    "REQUEST_BY"      VARCHAR2(80 BYTE),
    "REQUEST_DT" TIMESTAMP (6),
    "FROM_PROMO_DETAIL_NO"        NUMBER(8,0),
    "LAST_PROMO_REQ_FLOW_NO" NUMBER(18,0),
    "VERSION"    NUMBER(9,0) DEFAULT 0,
    CONSTRAINT "MSTB_PROMOTION_REQ_PK" PRIMARY KEY ("PROMO_REQ_NO") USING INDEX ENABLE,
   	CONSTRAINT "MSTB_PROMOTION_REQ_FK1" FOREIGN KEY ("PROMO_NO") REFERENCES "MSTB_PROMOTION" ("PROMO_NO") ENABLE,
    CONSTRAINT "MSTB_PROMOTION_REQ_FK2" FOREIGN KEY ("FROM_PROMO_DETAIL_NO") REFERENCES "MSTB_PROMO_DETAIL" ("PROMO_DETAIL_NO") ENABLE
   	)
  ;
  
CREATE SEQUENCE "MSTB_PROMO_REQ_SQ1" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

--create mstb_promo_req_flow table
CREATE TABLE "MSTB_PROMO_REQ_FLOW"
  (
    "PROMO_REQ_FLOW_NO" NUMBER(18,0) NOT NULL ENABLE,
    "PROMO_REQ_NO"      NUMBER(18,0) NOT NULL ENABLE,
    "REQ_FROM_STATUS"  VARCHAR2(2 BYTE) NOT NULL ENABLE,
    "REQ_TO_STATUS"    VARCHAR2(2 BYTE) NOT NULL ENABLE,
    "FROM_STATUS"      VARCHAR2(2 BYTE) NOT NULL ENABLE,
    "TO_STATUS"        VARCHAR2(2 BYTE) NOT NULL ENABLE,
    "TO_PROMO_DETAIL_NO"   NUMBER(8,0),
    "CREATED_BY"      VARCHAR2(80 BYTE),
    "CREATED_DT" TIMESTAMP (6),
    "REMARKS"      VARCHAR2(500 BYTE),
    "VERSION" NUMBER(9,0) DEFAULT 0,
    CONSTRAINT "MSTB_PROMO_REQ_FLOW_PK" PRIMARY KEY ("PROMO_REQ_FLOW_NO") USING INDEX ENABLE,
    CONSTRAINT "MSTB_PROMO_REQ_FLOW_FK1" FOREIGN KEY ("PROMO_REQ_NO") REFERENCES "MSTB_PROMO_REQ" ("PROMO_REQ_NO") ENABLE,
    CONSTRAINT "MSTB_PROMO_REQ_FLOW_FK2" FOREIGN KEY ("TO_PROMO_DETAIL_NO") REFERENCES "MSTB_PROMO_DETAIL" ("PROMO_DETAIL_NO") ENABLE
  );

CREATE SEQUENCE "MSTB_PROMO_REQ_FLOW_SQ1" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;


--add effective promo flow, current promo flow, last updated by, last updated dt column to mstb_promotion
ALTER TABLE MSTB_PROMOTION ADD (
LAST_UPDATED_BY      VARCHAR2(80 BYTE),
LAST_UPDATED_DT TIMESTAMP (6),
LAST_PROMO_REQ_NO NUMBER(8,0),
CURRENT_PROMO_DETAIL_NO NUMBER(8,0), 
CURRENT_STATUS  VARCHAR2(2 BYTE)
);

alter table MSTB_PROMOTION ADD CONSTRAINT MSTB_PROMOTION_FK1 FOREIGN KEY ("CURRENT_PROMO_DETAIL_NO") REFERENCES "MSTB_PROMO_DETAIL" ("PROMO_DETAIL_NO") ENABLE;

--migrate mstb_promotion data to mstb_promotion_detail table 
insert into mstb_promo_detail (
"PROMO_DETAIL_NO",
"PROMO_NO",
"NAME",
"TYPE",
"PRODUCT_TYPE_ID",
"PROMO_TYPE",
"PROMO_VALUE",
"EFFECTIVE_DT_FROM",
"EFFECTIVE_DT_TO",
"JOB_TYPE",
"VEHICLE_MODEL",
"EFFECTIVE_CUTOFF_DATE",
"VERSION"
)
select
MSTB_PROMO_DETAIL_SQ1.NEXTVAL,
PROMO_NO,
NAME,
TYPE,
PRODUCT_TYPE_ID,
PROMO_TYPE,
PROMO_VALUE,
EFFECTIVE_DT_FROM,
EFFECTIVE_DT_TO,
JOB_TYPE,
VEHICLE_MODEL,
EFFECTIVE_CUTOFF_DATE,
0
FROM 
(select * from  MSTB_PROMOTION order by promo_no)
;


--migrate mstb_promotion data to mstb_promotion_flow table 
insert into mstb_promo_req (
"PROMO_REQ_NO",
"PROMO_NO",
"EVENT",
"REQUEST_BY",
"REQUEST_DT",
"FROM_PROMO_DETAIL_NO",
"LAST_PROMO_REQ_FLOW_NO",
"VERSION"
)
SELECT
MSTB_PROMO_REQ_SQ1.NEXTVAL,
PROMO_NO,
'C',
created_by,
created_dt,
null,
promo_detail_no,
0
FROM 
(SELECT a.promo_no as promo_no, b.promo_detail_no, a.created_by, a.created_dt FROM  MSTB_PROMOTION A
INNER JOIN MSTB_PROMO_DETAIL b ON A.promo_no = b.promo_no
order by promo_no)
;


--determine current and last promotion detail no for existing records
alter table MSTB_PROMO_DETAIL add constraint temp_detail_unique unique(PROMO_NO);

alter table MSTB_PROMO_REQ add constraint temp_req_unique unique(PROMO_NO);

UPDATE
  (
    SELECT 
    P.last_PROMO_REQ_NO,
    P.current_PROMO_DETAIL_NO,
    p.current_status,
    d.promo_detail_no,
    r.promo_req_no
    FROM MSTB_PROMOTION p
    INNER JOIN MSTB_PROMO_DETAIL d
    ON p.promo_no         = d.promo_no
    INNER JOIN MSTB_PROMO_REQ r
    ON p.promo_no         = r.promo_no
  )
  SET
LAST_PROMO_REQ_NO = PROMO_REQ_NO,
CURRENT_PROMO_DETAIL_NO = PROMO_DETAIL_NO,
CURRENT_status = 'A';

alter table MSTB_PROMO_DETAIL drop constraint temp_detail_unique;

alter table MSTB_PROMO_REQ drop constraint temp_req_unique;

--migrate pending promotion request flow
insert into MSTB_PROMO_REQ_FLOW (
"PROMO_REQ_FLOW_NO",
"PROMO_REQ_NO",
"REQ_FROM_STATUS",
"REQ_TO_STATUS",
"FROM_STATUS",
"TO_STATUS",
"TO_PROMO_DETAIL_NO",
"CREATED_BY",
"CREATED_DT",
"REMARKS",
"VERSION"
)
SELECT
MSTB_PROMO_REQ_FLOW_SQ1.NEXTVAL,
PROMO_REQ_NO,
'N',
'P',
'N',
'PC',
current_promo_detail_no,
created_by,
created_dt,
'',
0
FROM 
(SELECT b.PROMO_REQ_NO, A.current_promo_detail_no,  A.CREATED_BY, A.CREATED_DT 
FROM MSTB_PROMOTION A
INNER JOIN MSTB_PROMO_REQ b on A.PROMO_NO=b.PROMO_NO 
ORDER BY a.promo_no
)
;


--migrate approve promotion request flow
insert into MSTB_PROMO_REQ_FLOW (
"PROMO_REQ_FLOW_NO",
"PROMO_REQ_NO",
"REQ_FROM_STATUS",
"REQ_TO_STATUS",
"FROM_STATUS",
"TO_STATUS",
"TO_PROMO_DETAIL_NO",
"CREATED_BY",
"CREATED_DT",
"REMARKS",
"VERSION"
)
SELECT
MSTB_PROMO_REQ_FLOW_SQ1.NEXTVAL,
PROMO_REQ_NO,
'P',
'A',
'PC',
'A',
current_promo_detail_no,
created_by,
created_dt,
'',
0
FROM 
(SELECT b.PROMO_REQ_NO, A.current_promo_detail_no, A.CREATED_BY, A.CREATED_DT
FROM MSTB_PROMOTION A
INNER JOIN MSTB_PROMO_REQ b on A.PROMO_NO=b.PROMO_NO 
ORDER BY a.promo_no
)
;


--determine last req flow no for existing records
UPDATE MSTB_PROMO_REQ a
SET LAST_PROMO_REQ_FLOW_NO =
  (
    SELECT DISTINCT first_value(D.PROMO_REQ_FLOW_NO) OVER (PARTITION BY D.PROMO_REQ_NO order by D.PROMO_REQ_FLOW_NO DESC) AS dLAST_PROMO_REQ_FLOW_NO
    FROM MSTB_PROMO_REQ_FLOW D
    WHERE A.PROMO_REQ_NO = d.PROMO_REQ_NO
  );


--set last updated by, updated dt
update MSTB_PROMOTION set last_updated_by = updated_by, last_updated_dt = updated_dt;


--drop mstb_promotion column
ALTER TABLE MSTB_PROMOTION DROP
(
NAME,
TYPE,
PRODUCT_TYPE_ID,
PROMO_TYPE,PROMO_VALUE,
EFFECTIVE_DT_FROM,
EFFECTIVE_DT_TO,
JOB_TYPE,
VEHICLE_MODEL,
EFFECTIVE_CUTOFF_DATE,
UPDATED_DT,
UPDATED_BY
);
