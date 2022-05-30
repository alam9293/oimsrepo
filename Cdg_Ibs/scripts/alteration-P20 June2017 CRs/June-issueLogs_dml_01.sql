SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_juneIssueLogs_dml_01_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 10/06/2017 CR #151 Cardless Product START */

-- Change menu name
update satb_resource set display_name = 'Create Cardless Trips', rsrc_name = 'Create Cardless Trips' where rsrc_name = 'Create Premier Txn';
update satb_resource set display_name = 'Create Card Trips', rsrc_name = 'Create Card Trips' where rsrc_name = 'Create Txn';


-- Set cardless to yes for Premier Service.
update pmtb_product_type set cardless = 'Y' where product_type_id = 'PS';

/*-- 10/06/2017 CR #151 Cardless Product END */

spool off