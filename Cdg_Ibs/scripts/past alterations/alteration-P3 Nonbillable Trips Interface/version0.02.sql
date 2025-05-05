alter table Tmtb_Acquire_Txn add(
  OFFLINE_FLAG  VARCHAR2(1) default 'N'
);

CREATE INDEX Tmtb_Non_Billable_Txn_N10 ON Tmtb_Non_Billable_Txn (OFFLINE_FLAG);

-- for error txn report
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (487,412,'Error Transaction Report','U', '/report/error_txn_report.zul?rsrcId=487',7,'Error Transaction Report','Y',0);
INSERT INTO MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) VALUES ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), 487, 'CSV');

INSERT INTO "SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (479,412,'AS Offline Approval','U','/report/as_offline_approval_report.zul?rsrcId=479',16,'AS Offline Approval','Y',0);
INSERT INTO "MSTB_REPORT_FORMAT_MAP" (MAP_NO,RSRC_ID,REPORT_FORMAT) VALUES (100,479,'CSV');

--INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES ((SELECT ROLE_ID FROM SATB_ROLE WHERE NAME = 'IT_ADMIN'),479);