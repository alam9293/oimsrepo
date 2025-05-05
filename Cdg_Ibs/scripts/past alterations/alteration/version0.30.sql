ALTER TABLE BMTB_INVOICE_TXN
ADD (
  BOOKING_REF VARCHAR2(40),
  VEHICLE_TYPE VARCHAR2(500)
);

update satb_resource set rsrc_name = 'Manage Acct Types' where uri = '/acct/type/manage.zul'
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (414,412,'Customer Usage Comparsion','U','/report/report_input.zul?report=Customer Usage Comparsion',1,'Customer Usage Comparsion','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (417,412,'Sales Report by Salesperson','U','/report/report_input.zul?report=Sales Report by Salesperson',1,'Sales Report by Salesperson','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (418,401,'Account','U','Account',6,'Account','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (419,418,'Customer Deposit Summary','U','/report/report_input.zul?report=Customer Deposit Summary',6,'Customer Deposit Summary','N',0);
