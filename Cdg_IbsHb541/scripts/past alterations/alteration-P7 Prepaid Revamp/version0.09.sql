
alter table MSTB_MASTER_TABLE modify (MASTER_TYPE varchar2(10));

--issuance request
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRS','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>Card issuance with CashPlus request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRA','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card issuance with CashPlus request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRR','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPIRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card issuance with CashPlus request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

--top up request
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRS','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>Card value topup with CashPlus request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRA','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card value topup with CashPlus request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRR','SUBJ',NULL, 'IBS-PROD – CashPlus Issuance (Topup) Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPTRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>Card value topup with CashPlus request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--transfer
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRS','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance transfer request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRA','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance transfer request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRR','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Transfer Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPXRR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance transfer request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--extend balance expiry
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERS','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance expiry extension request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERA','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request Approved', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance expiry extension request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERR','SUBJ',NULL, 'IBS-PROD – Extend Prepaid Card Balance Expiry Request Rejected', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPERR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance expiry extension request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);


--adjust
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARS','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARS','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #approverUserName#,<br>The card balance adjustment request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitterUserName#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARA','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARA','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance adjustment request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARR','SUBJ',NULL, 'IBS-PROD – Prepaid Card Balance Adjustment Request', 'A',0);

INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (MSTB_MASTER_TABLE_SQ1.NEXTVAL,'EMPARR','CONT',NULL, '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #submitterUserName#,<br>The card balance adjustment request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Request No: #requestNo#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>', 'A',0);





