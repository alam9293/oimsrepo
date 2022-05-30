/*ADD EXPIRY_DATE IN IMTB_ITEM*/
ALTER TABLE IMTB_ITEM
ADD EXPIRY_DATE DATE;

/*UPDATE THE EXPIRY_DATE FROM IMTB_ISSUE*/
UPDATE IMTB_ITEM itm SET
itm.EXPIRY_DATE = (select issue.EXPIRY_DATE 
FROM IMTB_ISSUE issue where itm.ISSUE_NO = issue.ISSUE_NO)
where itm.EXPIRY_DATE is null;

/*ADD NEW_EXPIRY_DATE IN IMTB_ITEM_REQ*/
ALTER TABLE IMTB_ITEM_REQ
ADD NEW_EXPIRY_DATE DATE;
COMMIT;

/*ADD resource "edit taxi voucher expiry date" under product management*/
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
values (
(SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
(select RSRC_ID from SATB_RESOURCE where rsrc_name='Product Mgmt'),
'Edit Taxi Voucher Expiry Date',
'U',
'/inventory/edit_item_expiry_date.zul',
(select max(sequence)+1 from SATB_RESOURCE where PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where rsrc_name='Product Mgmt')),
'Edit Taxi Voucher Expiry Date',
'N',
0);

/* assign role to resources */
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Manage Taxi Vouchers')
and b.rsrc_name in ('Edit Taxi Voucher Expiry Date');

/*ADD resource "approve taxi voucher request" under product management*/
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
values (
(SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
(select RSRC_ID from SATB_RESOURCE where rsrc_name='Product Mgmt'),
'Approve Taxi Voucher Request',
'U',
'Approve Taxi Voucher Request',
(select max(sequence)+1 from SATB_RESOURCE where PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where rsrc_name='Product Mgmt')),
'Approve Taxi Voucher Request',
'N',
0);

/* assign role to resources */
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Approval of Taxi Vouchers')
and b.rsrc_name in('Approve Taxi Voucher Request');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Amendment Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Amendment Request','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Amendment Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Amendment Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Amendment Request','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Amendment Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Amendment Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIIRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Amendment Request','A',0,'Email');

/*Edit expiry date reason master list*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'IVER','EXT',NULL,'EXTENSION','A',0,'Others');
COMMIT;



