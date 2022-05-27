insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,version) 
values (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'SMS','3GEN',null,
'Thank you for taking Comfort and Citycab. Your remaining Card Balance is :$#creditBalance# Last Drop Off: #destination#',
'A',0);

alter table pmtb_product_type add SMS_FORMAT_MASTER_NO NUMBER(8);