/* *************************************
 * Update resource name/display name
 * ************************************/
update SATB_RESOURCE set RSRC_NAME='Manage Bank Payment Advice',DISPLAY_NAME='Manage Bank Payment Advice' where uri = '/nonbillable/manage_bank_payment_advise.zul';
update SATB_RESOURCE set RSRC_NAME='Create Bank Payment Advice',DISPLAY_NAME='Create Bank Payment Advice' where uri = '/nonbillable/create_bank_payment_advise.zul';


/* *************************************
 * application opt in invoice
 * ************************************/
ALTER TABLE AMTB_APPLICATION ADD "E_INVOICE_FLAG" VARCHAR(1);

/* *************************************
 * Insert resource name/display name
 * ************************************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION)VALUES (617,602,'Manage Non Billable GL','U','/admin/gl_non_billable/manage_non_billable.zul',6,'Manage Non Billable GL','Y',0);