-- SATB RESOURCE for Birth Announcement Report

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Account'), 'Birthday Announcement', 'U', '/report/birthday_announcement.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Account'), 'Birthday Announcement', 'Y', 0);

UPDATE SATB_RESOURCE
SET URI = CONCAT(URI, (SELECT RSRC_ID from SATB_RESOURCE WHERE RSRC_NAME='Birthday Announcement'))
WHERE RSRC_NAME = 'Birthday Announcement';

insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Birthday Announcement'), 'CSV');
