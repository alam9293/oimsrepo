UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>A Subscription Request has been submitted for your approval.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Product Type: #prodType#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Submitter Name: #submitter#<br><br>Please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>'
WHERE MASTER_TYPE='EMSRS' AND MASTER_CODE='CONT';

UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '[IBS-APPL] - Subscription Request (#subscribe#)'
WHERE MASTER_TYPE='EMSRS' AND MASTER_CODE='SUBJ';

UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Subscription Request you submitted has been approved.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Product Type: #prodType#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br><br>To check your Subscription Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>'
WHERE MASTER_TYPE='EMSRA' AND MASTER_CODE='CONT';

UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '[IBS-APPL] - Subscription Request Approved (#subscribe#)'
WHERE MASTER_TYPE='EMSRA' AND MASTER_CODE='SUBJ';

UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '<P style="font-family:Calibri; font-size:16px;white-space: pre">Dear #userName#,<br><br>The Subscription Request you submitted has been rejected.<br>The details are as follows:<br>&nbsp;&nbsp;&nbsp;&nbsp;Account No: #custNo#<br>&nbsp;&nbsp;&nbsp;&nbsp;Account Name: #acctName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Product Type: #prodType#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Code: #divCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Div Name: #divName#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Code: #deptCode#<br>&nbsp;&nbsp;&nbsp;&nbsp;Dept Name: #deptName#<br><br>To check your Subscription Request, please <a href="http://localhost:8080/ibs">click this link</a> to login to the System<br><br>Thanks and Best Regards.</P>'
WHERE MASTER_TYPE='EMSRR' AND MASTER_CODE='CONT';

UPDATE MSTB_MASTER_TABLE set MASTER_VALUE = '[IBS-APPL] - Subscription Request Rejected (#subscribe#)'
WHERE MASTER_TYPE='EMSRR' AND MASTER_CODE='SUBJ';
