-- Create External Trips
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (137,501,'Create Ext Trips','U','/txn/new_ext_txn.zul',8,'Create Ext Trips','Y',0);

-- Edit Product
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (138,21,'Edit Product','U','/product/edit_specific_product.zul',8,'Edit Product','N',0);


INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE) values (89,'EMARS','SUBJ','[IBS-APPL] - Application Request','A',0,null);
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,MASTER_VALUE,MASTER_STATUS,VERSION,INTERFACE_MAPPING_VALUE) values (90,'EMARS','CONT','<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br>An Application Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Application ID: #appNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #appName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>','A',0,null);