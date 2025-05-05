--SENGTAT: RENAME MENU NAME FOR ISSUE MISC INVOICE
update satb_resource set display_name = 'Issue Misc. Invoice' 
where uri = '/billing/invoice/search_account_for_issue_misc_invoice.zul'
--END
-- to add in is manual and migration user
alter table FMTB_TRANSACTION_CODE add IS_MANUAL VARCHAR2(1);
Insert into SATB_USER (USER_ID,LOGIN_ID,PASSWORD,NAME,EMAIL,STATUS,LOCKED,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (14,'MIGRATION','123','Migratoin Admin','123','A','N','admin',to_timestamp('11-JUN-09 11.26.18.843000000 AM','DD-MON-RR HH.MI.SS.FF AM'),null,null,0);
