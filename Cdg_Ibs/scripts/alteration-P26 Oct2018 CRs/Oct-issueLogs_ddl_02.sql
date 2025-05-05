SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_octIssueLogs_ddl_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 18/1/2019 CR #192 PUBBS P2 START */


--Add new PUBBS Incoming Request

-- 20/02/2019 - updated with "header" columns
create TABLE ITTB_PUBBS_RETURN_REQ
(
   REQ_NO number(18) PRIMARY KEY NOT NULL,
   FILE_NAME varchar2(80),
   HEADER varchar2(80),
   STATUS varchar2(1) NOT NULL,
   REMARKS varchar2(80),
   VERSION number(8) DEFAULT 0,
   CREATED_BY varchar2(80),
   CREATED_DT timestamp,
   UPDATED_BY varchar2(80),
   UPDATED_DT timestamp
);
CREATE INDEX ITTB_PUBBS_RETURN_REQ_N1 ON ITTB_PUBBS_RETURN_REQ(FILE_NAME, STATUS);
create SEQUENCE ITTB_PUBBS_RETURN_REQ_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;



CREATE TABLE ITTB_PUBBS_RETURN_DETAIL(
	PUBBS_DETAIL_NO number(18) PRIMARY KEY NOT NULL,
	REQ_NO number(18) NOT NULL,
	CUSTOMER_ACCOUNT_NO VARCHAR2(30) not null,
        RECORD_STATUS VARCHAR2(1) not null,
        REMARKS VARCHAR2(80),
	VERSION number(9) DEFAULT 0,
	CREATED_BY varchar2(80),
        CREATED_DT timestamp,
        UPDATED_BY varchar2(80),
        UPDATED_DT timestamp
);
create SEQUENCE ITTB_PUBBS_RETURN_DETAIL_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;



/*-- 18/1/2019 CR #192 PUBBS P2 END */

spool off
