SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 01/01/2019 CR #191 FI START */


--Add new PUBBS FLAG to AMTB_APPLICATION
ALTER TABLE AMTB_ACCOUNT ADD (FI_FLAG varchar2(1 char) DEFAULT 'N');
COMMENT ON COLUMN AMTB_ACCOUNT.FI_FLAG IS 'FI Flag';

ALTER TABLE AMTB_APPLICATION ADD (FI_FLAG varchar2(1 char) DEFAULT 'N');
COMMENT ON COLUMN AMTB_APPLICATION.FI_FLAG IS 'FI Flag';


--EMAIL configurable
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMFIN','SUBJ',null,'IBS Production : FI Generation Completed','A',0,'Email');

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION,MASTER_GROUPING)
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMFIN','CONT',null,'<body style="font-family:Calibri; font-size:16px;white-space: pre"> FI Generation Completed : <br><br> Trips Details has been uploaded to FI@Gov <br> Number of Invoice: #invoiceNumber# <br> Number of Trips: #tripNumber# <br><br> Process Start = #startTime# <br><br> Process End = #endTime# <br><br> BillGen Request No: #billGenReqNo# <br><br>FI Generation Status = Completed </body>','A',0,'Email');



CREATE TABLE ITTB_FI_REQ(
	FI_REQ_NO number(18) PRIMARY KEY NOT NULL,
	REQ_NO varchar2(80),
        STATUS VARCHAR2(1) not null,
        START_DATE timestamp,
        END_DATE timestamp,
        FILE_NAME varchar2(200),
	VERSION number(9) DEFAULT 0,
	CREATED_BY varchar2(80),
        CREATED_DT timestamp,
        UPDATED_BY varchar2(80),
        UPDATED_DT timestamp
);
create SEQUENCE ITTB_FI_REQ_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;




/*-- 01/01/2019 CR #191 FI END */

spool off
