-- for error txn report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (487,412,'Error Transaction Report','U', '/report/error_txn_report.zul?rsrcId=487',7,'Error Transaction Report','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 487, 'CSV');
