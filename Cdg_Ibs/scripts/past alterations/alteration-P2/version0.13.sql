/* *************************************
 * INVENTORY EMAIL
 * ************************************/
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRA','CONT',null,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Inventory Issuance Request you submitted has been approved.<br>The details are as follows:<dir>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>To check your Inventory Issuance Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRA','SUBJ',null,'[IBS-APPL] - Inventory Issuance Request','A',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRR','CONT',null,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>The Inventory Issuance Request you submitted has been rejected.<br>The details are as follows:<dir>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>To check your Inventory Issuance Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRR','SUBJ',null,'[IBS-APPL] - Inventory Issuance Request','A',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRS','CONT',null,'<style>*{font-family:Calibri;font-size:16px;white-space:pre;}dir{margin-top:0}</style>Dear #userName#,<br><br>An Inventory Issuance Request has been submitted for your approval.<br>The details are as follows:<dir>Account No: #custNo#<br>Account Name: #acctName#<br>Item Type: #itemType#<br>Quantity: #quantity#</dir>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.','A',0);
Insert into MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) values (MSTB_MASTER_TABLE_SQ1.nextVal,'EMIRS','SUBJ',null,'[IBS-APPL] - Inventory Issuance Request','A',0);