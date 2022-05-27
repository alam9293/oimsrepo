SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_mayIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;


/*-- 09/05/2016  #151  CR New Non Billable Payment (Paylah, Alipay)   START */

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'APT','PL','PAYLAH','A',0,'PAYLAH','Others');

insert into mstb_acquirer (acquirer_no, name, created_dt, created_by) values (mstb_acquirer_sq1.nextval, 'PAYLAH', sysdate, 'TestDomain\system');

insert into mstb_acquirer_mdr 
(
mdr_no,
acquirer_no,
effective_date,
rate,
created_dt,
created_by
) 
values 
(
MSTB_ACQUIRER_MDR_SQ1.nextval,
(select acquirer_no from mstb_acquirer where name='PAYLAH'),
sysdate,
0,
sysdate,
'TestDomain\system'
);



INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE,MASTER_GROUPING) 
values (MSTB_MASTER_TABLE_SQ1.nextVal,'APT','AP','ALIPAY','A',0,'ALIPAY','Others');

insert into mstb_acquirer (acquirer_no, name, created_dt, created_by) values (mstb_acquirer_sq1.nextval, 'ALIPAY', sysdate, 'TestDomain\system');

insert into mstb_acquirer_mdr 
(
mdr_no,
acquirer_no,
effective_date,
rate,
created_dt,
created_by
) 
values 
(
MSTB_ACQUIRER_MDR_SQ1.nextval,
(select acquirer_no from mstb_acquirer where name='ALIPAY'),
sysdate,
0,
sysdate,
'TestDomain\system'
);
/*-- 09/05/2016  #151  CR New Non Billable Payment (Paylah, Alipay)   END */


spool off