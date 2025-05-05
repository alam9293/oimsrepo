/* *******************
 * Report
 * *******************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (463,421,'Debts Reminder Letter','U','/report/debts_reminder_letter.zul?rsrcId=463',5,'Debts Reminder Letter','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 463, 'PDF');