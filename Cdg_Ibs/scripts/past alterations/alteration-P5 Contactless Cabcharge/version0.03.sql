--issuance fee invoice generate

alter table BMTB_INVOICE_DETAIL add ISSUANCE_FEE number(12,2);
alter table BMTB_INVOICE_DETAIL add ISSUANCE_FEE_GST number(12,2) ;
alter table BMTB_INVOICE_DETAIL add ISSUANCE_FEE_GST_PERCENT  NUMBER(5,2);

alter table BMTB_DRAFT_INV_DETAIL add ISSUANCE_FEE number(12,2);
alter table BMTB_DRAFT_INV_DETAIL add ISSUANCE_FEE_GST number(12,2) ;
alter table BMTB_DRAFT_INV_DETAIL add ISSUANCE_FEE_GST_PERCENT  number(5,2);


alter table pmtb_product add LAST_ISSUANCE_FEE_CHARGE_DATE DATE;