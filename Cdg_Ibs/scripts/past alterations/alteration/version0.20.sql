-- for reports
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (408,406,'Receipt By Period Summary','U','/report/report_input.zul?report=Receipt By Period Summary',1,'Receipt By Period Summary','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (409,406,'Daily Cheque Deposit Listing','U','/report/report_input.zul?report=Daily Cheque Deposit Listing',1,'Daily Cheque Deposit Listing','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (410,401,'Note','U','Note',5,'Note','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (411,410,'Credit Debit Note','U','/report/report_input.zul?report=Credit Debit Note',1,'Credit Debit Note','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (412,401,'Trips','U','Trips',6,'Trips','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (413,412,'Customer Usage','U','/report/report_input.zul?report=Customer Usage',1,'Customer Usage','N',0);
-- to standardise contact person
alter table AMTB_CONTACT_PERSON modify MAIN_CONTACT_NAME varchar(50);
alter table AMTB_CONTACT_PERSON modify SUB_CONTACT_NAME varchar(50);
alter table AMTB_CONTACT_PERSON modify SUB_CONTACT_TITLE varchar(50);