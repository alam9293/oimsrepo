update SATB_RESOURCE set RSRC_NAME = 'Soft Copy Invoice', DISPLAY_NAME = 'Soft Copy Invoice'
where RSRC_NAME ='Soft Copy Invoice & Trips Detail (By Invoice No.)';

update SATB_RESOURCE set RSRC_NAME = 'Trips Detail', DISPLAY_NAME = 'Trips Detail'
where RSRC_NAME ='Soft Copy Invoice & Trips Detail (By Transaction Status)';

Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (111,21,'Suspend Products','U','/product/suspend_products.zul',7,'Suspend Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (112,21,'Retag Products','U','/product/retag_products.zul',8,'Retag Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (113,21,'Replace Products','U','/product/replacement_products.zul',9,'Replace Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (114,21,'Renew Products','U','/product/renew_products.zul',10,'Renew Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (115,21,'Terminate Products','U','/product/terminate_products.zul',11,'Terminate Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (116,21,'Reactivate Products','U','/product/reactive_products.zul',12,'Reactivate Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (117,21,'Update Products Credit Limit','U','/product/update_credit_limit.zul',13,'Update Products Credit Limit','N',0);

------ Yiming's report
update satb_resource set URI = '/report/credit_debit_note.zul' where RSRC_ID = 411;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 411, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 411, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 411, 'XLS');
update satb_resource set URI = '/report/customer_report.zul' where RSRC_ID = 450;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 450, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 450, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 450, 'XLS');
update satb_resource set URI = '/report/receipt_by_period_detailed.zul' where RSRC_ID = 407;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 407, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 407, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 407, 'XLS');
update satb_resource set URI = '/report/cashless_txn_trip_analysis.zul' where RSRC_ID = 423;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 423, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 423, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 423, 'XLS');
update satb_resource set URI = '/report/customer_deposit_detailed.zul' where RSRC_ID = 425;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 425, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 425, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 425, 'XLS');
update satb_resource set URI = '/report/customer_deposit_summary.zul' where RSRC_ID = 419;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 419, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 419, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 419, 'XLS');
update satb_resource set URI = '/report/daily_cheque_deposit_listing.zul' where RSRC_ID = 409;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 409, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 409, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 409, 'XLS');
update satb_resource set URI = '/report/receipt_by_period_summary.zul' where RSRC_ID = 408;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 408, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 408, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 408, 'XLS');
update satb_resource set URI = '/report/customer_usage.zul' where RSRC_ID = 413;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 413, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 413, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 413, 'XLS');
update satb_resource set URI = '/report/customer_usage_comparsion.zul' where RSRC_ID = 414;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 414, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 414, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 414, 'XLS');
update satb_resource set URI = '/report/sales_report_by_salesperson.zul' where RSRC_ID = 417;
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 417, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 417, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 417, 'XLS');

--report changed to menu listing
--you may need to remove records in satb_role_resource due to foreign key in order for below delete statements to execute without any error.
Delete from SATB_RESOURCE where rsrc_id=402;
Delete from SATB_RESOURCE where rsrc_id=403;
Delete from SATB_RESOURCE where rsrc_id=404;
Delete from SATB_RESOURCE where rsrc_id=405;
update SATB_RESOURCE set display='Y' where rsrc_id=406;
update SATB_RESOURCE set uri='/report/receipt_by_period_detailed.zul?rsrcId=407', display='Y' where rsrc_id=407;
update SATB_RESOURCE set uri='/report/receipt_by_period_summary.zul?rsrcId=408', display='Y' where rsrc_id=408;
update SATB_RESOURCE set uri='/report/daily_cheque_deposit_listing.zul?rsrcId=409', display='Y' where rsrc_id=409;
update SATB_RESOURCE set display='Y' where rsrc_id=410;
update SATB_RESOURCE set uri='/report/credit_debit_note.zul?rsrcId=411', display='Y' where rsrc_id=411;
update SATB_RESOURCE set display='Y' where rsrc_id=412;
update SATB_RESOURCE set uri='/report/customer_usage.zul?rsrcId=413', display='Y' where rsrc_id=413;
update SATB_RESOURCE set uri='/report/customer_usage_comparsion.zul?rsrcId=414', display='Y' where rsrc_id=414;
update SATB_RESOURCE set uri='/report/sales_report_by_salesperson.zul?rsrcId=417', display='Y' where rsrc_id=417;
update SATB_RESOURCE set display='Y' where rsrc_id=418;
update SATB_RESOURCE set uri='/report/customer_deposit_summary.zul?rsrcId=419', display='Y' where rsrc_id=419;
update SATB_RESOURCE set display='Y' where rsrc_id=421;
update SATB_RESOURCE set uri='/report/customer_aging_detail.zul?rsrcId=422', display='Y' where rsrc_id=422;
update SATB_RESOURCE set uri='/report/cashless_txn_trip_analysis.zul?rsrcId=423', display='Y' where rsrc_id=423;
update SATB_RESOURCE set uri='/report/customer_aging_summary.zul?rsrcId=424', display='Y' where rsrc_id=424;
update SATB_RESOURCE set uri='/report/customer_deposit_detailed.zul?rsrcId=425', display='Y' where rsrc_id=425;
update SATB_RESOURCE set uri='/report/soft_copy_invoice_trip_details_by_invoice_no.zul?rsrcId=426', display='Y' where rsrc_id=426;
update SATB_RESOURCE set uri='/report/soft_copy_invoice_trip_details_by_transaction_status.zul?rsrcId=427', display='Y' where rsrc_id=427;
