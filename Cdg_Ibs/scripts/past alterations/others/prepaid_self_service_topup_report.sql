--Prepaid Self-Service Topup Report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES ((select max(rsrc_id)+1 from SATB_RESOURCE),415,'Prepaid Self-Service Topup Report','U','/report/prepaid_selfService_topup_report.zul?rsrcId=911',7,'Prepaid Self-Service Topup Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select max(rsrc_id) from SATB_RESOURCE), 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select max(rsrc_id) from SATB_RESOURCE), 'XLS');


