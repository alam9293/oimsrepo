alter table AMTB_CONTACT_PERSON modify MAIN_CONTACT_NAME varchar(80);
alter table AMTB_CONTACT_PERSON modify SUB_CONTACT_NAME varchar(80);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (428,415,'Card In Production','U','/report/card_in_production.zul?rsrcId=428',2,'Card In Production','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 428, 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 428, 'CSV');
delete from MSTB_REPORT_FORMAT_MAP where rsrc_id = 423 and REPORT_FORMAT = 'CSV'