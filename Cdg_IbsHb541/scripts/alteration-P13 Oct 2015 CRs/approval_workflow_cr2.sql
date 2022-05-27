alter table TMTB_NON_BILLABLE_TXN add CHARGEBACK_SUBMITTED_USER number NULL;
	
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRS','CONT',NULL,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br>A Chargeback Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Txn No: #txnNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Card No.: #cardNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submiter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRS','SUBJ',NULL,'[IBS-APPL] - Chargeback Request','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRA','CONT',NULL,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br>A Chargeback Request has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Txn No: #txnNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Card No.: #cardNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submiter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRA','SUBJ',NULL,'[IBS-APPL] - Chargeback Approved','A',0,'Email');

insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRR','CONT',NULL,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br>A Chargeback Request has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Txn No: #txnNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Card No.: #cardNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submiter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,'Email');
insert into mstb_master_table (master_no, master_type, master_code, interface_mapping_value, master_value,master_status, version, master_grouping)
values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMICBRR','SUBJ',NULL,'[IBS-APPL] - Chargeback Rejected','A',0,'Email');