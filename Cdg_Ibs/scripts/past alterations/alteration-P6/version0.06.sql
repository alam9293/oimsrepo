
--Timely Payment Statistics Report
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
    906,
    421,
    'Timely Payment Statistics Report',
    'U',
    '/report/timely_payment_statistics.zul?rsrcId=906',
    5,
    'Timely Payment Statistics Report',
    'Y',
    0
  );


insert into SATB_ROLE_RESOURCE select role_id, 906 from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Customer Aging Detail');


insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 906, 'PDF');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 906, 'XLS');


--Timely Payment Statistics Detailed Report 
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
    907,
    421,
    'Timely Payment Statistics Detailed Report',
    'U',
    '/report/timely_payment_statistics_detailed.zul?rsrcId=907',
    6,
    'Timely Payment Statistics Detailed Report',
    'Y',
    0
  );


insert into SATB_ROLE_RESOURCE select role_id, 907 from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Customer Aging Detail');


insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 907, 'CSV');


--GIRO File Report
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
    908,
    484,
    'GIRO File Report',
    'U',
    '/report/giro_file.zul?rsrcId=908',
    3,
    'GIRO File Report',
    'Y',
    0
  );

insert into SATB_ROLE_RESOURCE select role_id, 908 from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'GIRO Summary');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 908, 'CSV');






