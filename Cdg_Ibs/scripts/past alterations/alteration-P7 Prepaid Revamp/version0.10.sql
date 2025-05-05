delete from satb_role_resource where RSRC_ID in (select RSRC_ID from SATB_RESOURCE where rsrc_name='Manage Prepaid Cards');

delete from SATB_RESOURCE where rsrc_name='Manage Prepaid Cards';

--change date field type of balance expiry date to timestamp
alter table PMTB_PRODUCT rename column balance_expiry_date to td_balance_expiry_date;

ALTER TABLE PMTB_PRODUCT ADD balance_expiry_date TIMESTAMP;

UPDATE PMTB_PRODUCT  SET balance_expiry_date = TD_BALANCE_EXPIRY_DATE;

alter table PMTB_PRODUCT drop column TD_BALANCE_EXPIRY_DATE;


--change date field type of new balance expiry date to timestamp
alter table PMTB_EXT_BAL_EXP_DATE_REQ rename column new_bal_exp_date to td_balance_expiry_date;

ALTER TABLE PMTB_EXT_BAL_EXP_DATE_REQ ADD new_bal_exp_date TIMESTAMP;

UPDATE PMTB_EXT_BAL_EXP_DATE_REQ  SET new_bal_exp_date = TD_BALANCE_EXPIRY_DATE;

alter table PMTB_EXT_BAL_EXP_DATE_REQ drop column TD_BALANCE_EXPIRY_DATE;
