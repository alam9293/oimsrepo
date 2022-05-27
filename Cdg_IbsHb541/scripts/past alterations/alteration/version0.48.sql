update satb_resource set URI = '/report/cardholder_list.zul?rsrcId=416' where RSRC_ID = 416;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 416, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 416, 'XLS');
update satb_resource set URI = '/report/credit_balance.zul?rsrcId=420' where RSRC_ID = 420;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 420, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 420, 'CSV');
