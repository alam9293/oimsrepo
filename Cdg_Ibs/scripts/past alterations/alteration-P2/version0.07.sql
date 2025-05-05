/**************************************
 * FOR NON BILLABLE TRIPS DOWNLOAD
 * ************************************/
CREATE TABLE ITTB_SETL_TXN
(
   SETL_PK number(22) primary key NOT NULL,
   JOB_NO varchar2(10) NOT NULL,
   VEHICLE_NO varchar2(10) NOT NULL,
   DRIVER_IC varchar2(10),
   ENTITY varchar2(10),
   TXN_DATE date,
   PAYMENT_MODE varchar2(10),
   CARD_NO varchar2(20),
   APPROVAL_CODE varchar2(20),
   TXN_AMOUNT number(6,2),
   ADMIN_AMOUNT number(6,2),
   GST_AMOUNT number(6,2),
   FILE_ID varchar2(30),
   CREATED_DT date,
   JOB_STATUS varchar2(10) NOT NULL,
   RECON_FLAG varchar2(1),
   RECON_DT date,
   FARE_AMOUNT number(6,2),
   TID varchar2(8),
   MID varchar2(15),
   STAN varchar2(6),
   TRIP_START date,
   TRIP_END date,
   PICKUP varchar2(80),
   DESTINATION varchar2(80),
   SALES_DRAFT_NO varchar2(15),
   ERROR_CODE varchar2(10),
   FLOWTHRU_ACTION varchar2(10),
   FLOWTHRU_BY varchar2(30),
   FLOWTHRU_DT date,
   FLOWTHRU_COMMENTS varchar2(80),
   FLOWTHRU_TRIP_INTF_PK decimal(22),
   SETL_REF decimal(22),
   CARD_TYPE varchar2(1),
   SETL_DATE date,
   BANK_TID varchar2(8),
   BANK_BATCH_CLOSE_NO varchar2(6),
   UPDATED_DT timestamp, 
   UPDATED_BY varchar2(80)
)
;

--due to changes
alter table TMTB_NON_BILLABLE_TXN modify(
	PICKUP_ADDRESS varchar2(255) null,
	TRIP_END_DT timestamp null
);

alter table TMTB_NON_BILLABLE_TXN drop column CHARGEBACK_AMT;
alter table TMTB_NON_BILLABLE_TXN drop column vehicle_type;

alter table TMTB_NON_BILLABLE_TXN add(
	CHARGEBACK_FARE_AMT number(12,2),
	CHARGEBACK_ADMIN_FEE number(12,2),
	CHARGEBACK_GST number(12,2)
)

/**************************************
 * FOR REPORTS
 * ************************************/
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 433, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 434, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 435, 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 436, 'CSV');
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (437,412,'Cashless Bank Collection Detailed','U','/report/cashless_bank_collection_detailed.zul?rsrcId=437',2,'Cashless Bank Collection Detailed','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 437, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 437, 'CSV');
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (438,412,'Cashless Transaction By Amount Range','U','/report/cashless_txn_by_amt_range.zul?rsrcId=438',2,'Cashless Transaction By Amount Range','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 438, 'CSV');
update SATB_RESOURCE set sequence = 1 where rsrc_id = 433;
update SATB_RESOURCE set sequence = 2 where rsrc_id = 434;
update SATB_RESOURCE set sequence = 3 where rsrc_id = 435;
update SATB_RESOURCE set sequence = 4 where rsrc_id = 437;
update SATB_RESOURCE set sequence = 5 where rsrc_id = 436;
update SATB_RESOURCE set sequence = 6 where rsrc_id = 438;
update SATB_RESOURCE set sequence = 7 where rsrc_id = 423;
update SATB_RESOURCE set sequence = 8 where rsrc_id = 413;
update SATB_RESOURCE set sequence = 9 where rsrc_id = 414;
update SATB_RESOURCE set sequence = 11 where rsrc_id = 417;
update SATB_RESOURCE set sequence = 12 where rsrc_id = 426;
update SATB_RESOURCE set sequence = 13 where rsrc_id = 427;