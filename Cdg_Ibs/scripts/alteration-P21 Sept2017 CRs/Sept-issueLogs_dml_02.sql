SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_septIssueLogs_dml_02_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;

/*-- 30/11/2017 CR #171 Virtual Cabcharge START */

-- Change summary email content for summary

update mstb_master_table set master_value = '<body style="font-family:Calibri; font-size:16px;white-space: pre">Summary of email sent on #currentDateTime# : <br><br><br>Successful Deliveries #table1# <br><br> Unsuccessful Deliveries #table2# </body>'
where MASTER_TYPE = 'EMVCSE' and MASTER_CODE = 'CONT';


-- Change summary email subject

update mstb_master_table set master_value = 'Virtual Cabcharge Email Delivery Summary Report (#currentDate#)'
where master_type = 'EMVCSE' and master_code = 'SUBJ';


-- Change summary email content for individual email

update mstb_master_table set master_value = '<body style="font-family:Calibri; font-size:16px;white-space: pre">Dear #shippingContact# <br /><br /> The following virtual cards are created and ready for immediate use. <br /><br /> Please log in to our Cabcharge portal <a href="http://www.cabchargeasia.com.sg"><u>www.cabchargeasia.com.sg</u></a> to activate the virtual cards. <br /><br /> #table# <br /><br /> You may email to <u>sales@cdgtaxi.com.sg</u> if you require any clarification. <br /><br /> Thank you <br /><br /> Yours sincerely <br /><br /> Cabcharge Asia Pte Ltd</body>'
where master_type = 'EMVCE' and master_CODE = 'CONT';

/*-- 30/11/2017 CR #171 Virtual CabCharge END */

spool off