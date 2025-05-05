  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_ddl_09_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt.&new_ext
select 'Running at ' || sys_context('userenv', 'host') || '[' || sys_context('userenv', 'ip_address') || ']' || '@' || sys_context('userenv', 'os_user') 
       || ' as ' || sys_context('userenv', 'current_user') || '@' || sys_context('userenv', 'db_unique_name') as where_run
from   dual;
select 'Run start: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_start
from dual;
set linesize  10000
set pages 5000
set feedback on
set timing on
set heading on
set echo on
set define off
------------- Top Standard statements end
  
ALTER session SET current_schema=ibssys;

CREATE TABLE TMTB_NON_BILLABLE_TXN_CRCA_REQ(
    REQ_NO NUMBER(30) CONSTRAINT PC_TNBTCR__RN primary key,
    STATUS VARCHAR2(1) NOT NULL,
    SETTLEMENT_DATE DATE,
    VERSION NUMBER(9),
    CREATED_BY VARCHAR2(80),
    CREATED_DT TIMESTAMP(6),
    UPDATED_BY VARCHAR2(80),
    UPDATED_DT TIMESTAMP(6)
);

CREATE SEQUENCE SQ_TMTB_NON_BILLABLE_CRCA_REQ INCREMENT BY 1 START WITH 1 NOMAXVALUE NOMINVALUE ORDER;

comment on table TMTB_NON_BILLABLE_TXN_CRCA_REQ is 'This table is used to block any incoming thread that will run runAdyenSettlement';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.REQ_NO is 'The request no. of TMTB_NON_BILLABLE_TXN_CRCA_REQ. This is the primary key';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.STATUS is 'The status of the process which state if the process is running or have an error. P=Pending, E=Error, C=Completed';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.SETTLEMENT_DATE is 'The settlement date that is run by the batchjob';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.VERSION is 'The version of this table. It helps hibernate to do version control';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.CREATED_BY is 'The record that is created by';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.CREATED_DT is 'the created date of this record';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.UPDATED_BY is 'the record that is updated by';
comment on column TMTB_NON_BILLABLE_TXN_CRCA_REQ.UPDATED_DT is 'the updated date of this record';

ALTER TABLE TMTB_NON_BILLABLE_TXN_CRCA_REQ ADD CONSTRAINT CC_TNBTCR__RF CHECK (STATUS IN ('P', 'E', 'C'));	

begin
fms_dba.fms_pkg_dba.grant_ibs_privs;
end;
/

commit;

  ------------- Bottom Standard statements begin
set echo off
set timing off
set heading off
set feedback off
select 'Run end: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_end
from dual;
spool off
set echo on
set timing on
set heading on
set feedback on
------------- Bottom Standard statements end