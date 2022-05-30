

insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Trips'),
    'Customer Usage @ Card Levely','U',concat('/report/customer_usage_card_level.zul?rsrcId=',(select max(rsrc_id)+1 from satb_resource)),1,'Customer Usage @ Card Level','Y',0
  );
  
  
insert into satb_role_resource (role_id, rsrc_id) VALUES (2, (select rsrc_id from satb_resource where rsrc_name='Customer Usage @ Card Level'));
insert into satb_role_resource (role_id, rsrc_id) VALUES (9, (select rsrc_id from satb_resource where rsrc_name='Customer Usage @ Card Level'));
  
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Customer Usage @ Card Level'), 'CSV');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Customer Usage @ Card Level'), 'PDF');
insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Customer Usage @ Card Level'), 'XLS');

