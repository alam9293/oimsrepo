
INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    904,
    458,
    'Redeemed Voucher Report',
    'U',
    '/report/redeemed_voucher.zul?rsrcId=904',
    7,
    'Redeemed Voucher Report',
    'Y',
    0
  );


insert into SATB_ROLE_RESOURCE select role_id, 904 from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Unredeemed Voucher Report');

CREATE SEQUENCE MSTB_REPORT_FORMAT_MAP_SQ1 MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 109 CACHE 10 NOORDER NOCYCLE ;

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 904, 'CSV');


INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'MRR','10',NULL,'10','A',0);








