DELETE FROM mstb_master_table WHERE master_type = 'EMIIRS';
DELETE FROM mstb_master_table WHERE master_type = 'EMIIRA';
DELETE FROM mstb_master_table WHERE master_type = 'EMIIRR';


/*EMAIL TYPE Taxi Voucher EXPIRY DATE AMENDMENT Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Expiry Date Amendment Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Expiry Date Amendment Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Expiry Date Amendment Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Expiry Date Amendment Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Expiry Date Amendment Approval','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Expiry Date Amendment Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Expiry Date Amendment Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIERR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Expiry Date Amendment Rejection','A',0,'Email');

/*EMAIL TYPE Taxi Voucher SUSPENSION Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Suspension Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Suspension Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Suspension Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Suspension Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Suspension Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Suspension Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Suspension Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMISRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Suspension Request','A',0,'Email');

/*EMAIL TYPE Taxi Voucher REACTIVATION Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Reactivation Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Reactivation Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Reactivation Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Reactivation Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Reactivation Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Reactivation Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Reactivation Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Reactivation Request','A',0,'Email');

/*EMAIL TYPE Taxi Voucher VOID Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Void Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Void Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Void Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Void Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Void Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Void Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Void Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIVRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Void Request','A',0,'Email');

/*EMAIL TYPE Taxi Voucher CHANGE OF REDEMPTION Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Change of Redemption Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Change of Redemption Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Change of Redemption Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Change of Redemption Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Change of Redemption Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Change of Redemption Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Change of Redemption Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Change of Redemption Request','A',0,'Email');

/*EMAIL TYPE Taxi Voucher REMOVE REDEMPTION Request*/
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRS','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Taxi Voucher Remove Redemption Request has been submitted for your approval.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRS','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Remove Redemption Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRA','CONT',NULL,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Taxi Voucher Remove Redemption Request you submitted has been approved.<br>The details are as follows:<dir>Submitter: #submitter#<br>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br></dir>To check your Taxi Voucher Remove Redemption Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRA','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Remove Redemption Request','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRR','CONT',NULL,'<style type="text/css">*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br /><br />The Taxi Voucher Remove Redemption Request you submitted has been <span style="color: #ff0000"><strong>rejected</strong></span>.<br />The details are as follows:<dir>Submitter: #submitter#<br />Account No: #custNo#<br />Account Name: #acctName#<br />Item Type: #itemType#<br /></dir>To check your Taxi Voucher Remove Redemption Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br /><br />Thanks and Best Regards.','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIXRR','SUBJ',NULL,'[IBS-APPL] - Taxi Voucher Remove Redemption Request','A',0,'Email');
commit;
