SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_juneIssueLogs_ddl_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 10/06/2017 CR #151 Cardless Product START */

--Add new Cardless Column to PMTB_PRODUCT_TYPE
ALTER TABLE PMTB_PRODUCT_TYPE ADD CARDLESS VARCHAR(1) DEFAULT 'N' NOT NULL;
COMMENT ON COLUMN PMTB_PRODUCT_TYPE.CARDLESS IS 'Cardless Yes No';

/*-- 10/06/2017 CR #151 Cardless Product END */

spool off
