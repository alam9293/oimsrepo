/* *******************
 * Reports
 * *******************/

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (467,464,'Points Reminder Letter','U','/report/points_reminder_letter.zul?rsrcId=467',3,'Points Reminder Letter','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 467, 'PDF');

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (468,412,'Trip Adjustment Report','U','/report/trip_adjustment_report.zul?rsrcId=468',15,'Trip Adjustment Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 468, 'CSV');

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (145,37,'View Expired Rewards','U','/acct/acct/viewExpiredRewards.zul',1,'View Expired Rewards','N',0);