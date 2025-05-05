/* *******************
 * Reports
 * *******************/
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (466,464,'Loyalty Program Report','U','/report/loyalty_program_report.zul?rsrcId=466',2,'Loyalty Program Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 466, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 466, 'CSV');

update SATB_RESOURCE set RSRC_NAME = 'Financial Memo Report Detailed', DISPLAY_NAME = 'Financial Memo Report Detailed' where RSRC_ID = 457