--Reports
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (491,458,'Unredeemed Voucher Report','U', '/report/unredeemed_voucher.zul?rsrcId=488',4,'Unredeemed Voucher Report','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 491, 'CSV');
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (489,458,'Suspension Reactivation Voucher Report','U', '/report/suspension_reactivation_voucher.zul?rsrcId=489',5,'Suspension Reactivation Voucher Report','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 489, 'CSV');
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (490,458,'Adjust Voucher Redemption Report','U', '/report/adjust_voucher_redemption.zul?rsrcId=490',6,'Adjust Voucher Redemption Report','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 490, 'CSV');

--add a new column to cater for report
alter table IMTB_ITEM_REQ add(
  ACTION varchar2(1) NOT NULL
);