SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



/*-- 13/12/2016  CR/0216/038 #85 VITAL MASTER ACCESS START */

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Master Login Registration', 'U', '/portal/master_login_registration.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Master Login Registration', 'Y', 0);

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Manage Master Login Assignment', 'U', '/portal/manage_master_login.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Manage Master Login Assignment', 'Y', 0);


/*-- 13/12/2016  CR/0216/038 #85 VITAL MASTER ACCESS END */






/* -- 31/01/2017 Clear up Vital Master Access  & RePopulate Portal User Mgmt START */

--delete existing added by prev script
DELETE from SATB_RESOURCE where rsrc_name='Portal User Mgmt';
--delete existing search portal mgmt
DELETE from SATB_RESOURCE where rsrc_name='Search Portal user';


-- add new Portal User Mgmt
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (
(SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
(select rsrc_id from satb_resource where rsrc_name = 'Acct Mgmt')
,'Portal User Mgmt','U','Portal User Mgmt',
(select max(SEQUENCE)+1 from satb_resource where PAR_RSRC_ID = (select rsrc_id from satb_resource where rsrc_name = 'Acct Mgmt'))
,'Portal User Mgmt','Y',0);


-- add back existing script for Search Portal User
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),(select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'),'Search Portal User','U','/portal/search_portal_user.zul',(SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'),'Search Portal User','Y',0);

--add back prev script  for New Master Login Registration & Manage Master Login
INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Master Login Registration', 'U', '/portal/master_login_registration.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Master Login Registration', 'Y', 0);

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Manage Master Login Assignment', 'U', '/portal/manage_master_login.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Portal User Mgmt'), 'Manage Master Login Assignment', 'Y', 0);


/* -- 31/01/2017 Clear up Vital Master Access  & RePopulate Portal User Mgmt END */
spool off