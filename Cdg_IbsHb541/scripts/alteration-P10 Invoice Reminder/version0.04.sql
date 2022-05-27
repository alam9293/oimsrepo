-- 18 Jan 2016 - add customer Id to email subject

update MSTB_MASTER_TABLE set MASTER_VALUE = 'Invoice Reminder Due Soon - #accountName# (#customerId#) (#salesNo#)' 
where MASTER_TYPE = 'EMIRDS' and MASTER_CODE = 'SUBJ';

update MSTB_MASTER_TABLE set MASTER_VALUE = 'Invoice Reminder Overdue - #accountName# (#customerId#) (#salesNo#)' 
where MASTER_TYPE = 'EMIROD' and MASTER_CODE = 'SUBJ';

update MSTB_MASTER_TABLE set MASTER_VALUE = '=SG= Payment status - Acct : #accountName# - Cust Id # #customerId# (#salesNo#)' 
where MASTER_TYPE = 'EMEXAC' and MASTER_CODE = 'SUBJ';