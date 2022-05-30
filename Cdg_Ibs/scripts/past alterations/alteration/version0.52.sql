alter table TMTB_ACQUIRE_TXN drop column division_code
alter table TMTB_ACQUIRE_TXN drop column dept_code
alter table TMTB_ACQUIRE_TXN drop column txn_type

alter table BMTB_PAYMENT_RECEIPT_DETAIL add (CANCEL_BILLED_FLAG VARCHAR2(1) DEFAULT 'N' NOT NULL);

Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (429,401,'Revenue','U','Revenue',7,'Revenue','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (430,429,'Revenue Report','U','/report/revenue.zul?rsrcId=430',6,'Revenue Report','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 430, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 430, 'CSV');