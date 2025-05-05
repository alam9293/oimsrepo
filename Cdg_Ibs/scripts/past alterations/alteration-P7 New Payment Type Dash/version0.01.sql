insert into mstb_acquirer (acquirer_no, name, created_dt, created_by) values (mstb_acquirer_sq1.nextval, 'DASH', sysdate, 'TestDomain\system');

insert into mstb_master_table (master_no, master_type,master_code,interface_mapping_value,master_value,master_status) values (mstb_master_table_sq1.nextval, 'APT','DA','DASH','DASH','A');

insert into mstb_acquirer_pymt_type 
(
pymt_type_no,
acquirer_no,
effective_dt_from,
EFFECTIVE_DT_TO,
created_dt,
created_by,
master_no
)
values 
(
mstb_acquirer_pymt_type_sq1.nextval,
(select acquirer_no from mstb_acquirer where name='DASH'),
sysdate,
null,
sysdate,
'TestDomain\system',
(select master_no from MSTB_MASTER_TABLE where master_type='APT' and master_code='DA')
);


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
(select acquirer_no from mstb_acquirer where name='DASH'),
sysdate,
0,
sysdate,
'TestDomain\system'
);