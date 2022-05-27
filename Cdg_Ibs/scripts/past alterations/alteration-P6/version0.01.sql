
alter table LRTB_REWARD_ACCOUNT add IBS_EXPIRE_DT TIMESTAMP(6);

insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,version) 
values (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'RGP','IBSGR',null, '0','A',0);

alter table bmtb_invoice_header add rewards_expiry_date TIMESTAMP(6);

alter table BMTB_DRAFT_INV_HEADER add rewards_expiry_date TIMESTAMP(6);

--initialize all existing records for expiry date (IBS) with extends 2 months from expiry date (customers) 
update LRTB_REWARD_ACCOUNT set IBS_EXPIRE_DT= add_months(EXPIRE_DT,2);