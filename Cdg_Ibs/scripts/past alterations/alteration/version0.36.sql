-- Manage Master List
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (537,0,'Administration','U','Administration',99,'Administration','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (538,537,'Manage Master List','U','/admin/manage_master_list.zul',99,'Manage Master List','Y',0);

Insert into SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) values (2,537);
Insert into SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) values (2,538);

Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (421,401,'Aging','U','Aging',8,'Aging','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (422,421,'Customer Aging Detail','U','/report/customer_aging_detail.zul',1,'Customer Aging Detail','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (424,421,'Customer Aging Summary','U','/report/customer_aging_summary.zul',2,'Customer Aging Summary','N',0);