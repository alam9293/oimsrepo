/* *************************************
 * Increase datatype size
 * ************************************/
alter table MSTB_ADMIN_FEE_DETAIL modify( ADMIN_FEE number(5,2) );
alter table MSTB_EARLY_PAYMENT_DETAIL modify( EARLY_PAYMENT number(5,2) );
alter table MSTB_LATE_PAYMENT_DETAIL modify( LATE_PAYMENT number(5,2) );
alter table MSTB_PROD_DISC_DETAIL modify( PRODUCT_DISCOUNT number(5,2) );
alter table MSTB_VOL_DISC_TIER modify( VOLUME_DISCOUNT number(5,2) );

/* ***************************************
 * Adding 4 common fields to master tables
 * ***************************************/
alter table MSTB_VOL_DISC_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_VOL_DISC_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_VOL_DISC_TIER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_PROD_DISC_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_PROD_DISC_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_ADMIN_FEE_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_ADMIN_FEE_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_CREDIT_TERM_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_CREDIT_TERM_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_EARLY_PAYMENT_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_EARLY_PAYMENT_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_LATE_PAYMENT_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_LATE_PAYMENT_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_SUBSC_FEE_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_SUBSC_FEE_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_BANK_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table MSTB_BRANCH_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table MSTB_SALESPERSON add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table FMTB_ENTITY_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table FMTB_BANK_CODE add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table FMTB_AR_CONT_CODE_MASTER add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);
alter table FMTB_AR_CONT_CODE_DETAIL add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table FMTB_TRANSACTION_CODE add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

alter table FMTB_TAX_CODE add(
	CREATED_DT timestamp, 
    CREATED_BY varchar2(80), 
    UPDATED_DT timestamp, 
    UPDATED_BY varchar2(80)
);

/* ***************************************
 * Inventory Misc Invoice Processing Fee Description
 * ***************************************/
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'PF','PF',null,'PROCESSING FEE','A',0);
