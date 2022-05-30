INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMACLL','SUBJ',null,'IBS-PROD - Low Credit Balance Alert','A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMACLL','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #customerName#,<br/><br/>The credit balance of the following Account/Division/Department is low.<br/>The details are as follows: <br/><br/>#table#<br/><br/>To view this account, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System.<br/><br/>Thanks and Best Regards.</P>','A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMFSRA','SUBJ',null,'IBS-PROD - #suspendReactivate# of Account/Sub-Account','A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMFSRA','CONT',null,'<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #customerName#,<br/><br/>The following Account/Division/Department has been #heading# by #userName#.<br/>The details are as follows:<br/>#table#<br/><br/>To view this account, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System.<br/><br/>Thanks and Best Regards.</P>','A',0);


INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'BCCEM','EMAIL',null,'ibs@wiz.com;panel@wiz.com','A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMEXAC','SUBJ',null,'=SG= Payment status - Cust Id # #customerId# ','A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'EMEXAC','ACCT',null,'333;353','A',0);
