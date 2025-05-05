update BMTB_BILL_GEN_SETUP set name='BI-WEEKLY 1' WHERE setup_no = 1;
update BMTB_BILL_GEN_SETUP set name='BI-WEEKLY 2' WHERE setup_no = 2;

--creates the credit debit note menu
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (110,25,'Credit/Debit Note','U','Credit/Debit Note',3,'Credit/Debit Note','Y',0);
--tie the children to new parent
update SATB_RESOURCE set par_rsrc_id = '110' where rsrc_id in (508,509,510,511,26,27);
--update those roles with the new parent if they are already given with the children
insert into satb_role_resource 
(select DISTINCT(role_id) role_id, '110' rsrc_id from satb_role_resource
where rsrc_id in (508,509,510,511,26,27));