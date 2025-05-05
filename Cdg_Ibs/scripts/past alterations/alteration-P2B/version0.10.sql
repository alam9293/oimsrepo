/* *******************
 * Reports
 * *******************/
alter table LRTB_REWARD_TXN add(CREATED_DT timestamp, CREATED_BY varchar2(80));

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (464,401,'Rewards Report','U','Rewards Report',11,'Rewards','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (465,464,'Points Redemption Letter','U','/report/points_redemption_letter.zul?rsrcId=465',1,'Points Redemption Letter','Y',0);
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 465, 'PDF');