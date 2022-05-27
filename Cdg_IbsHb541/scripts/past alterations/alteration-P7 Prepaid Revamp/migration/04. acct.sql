--back up ar control code no column

alter table amtb_account add TD_AR_CONTROL_CODE_NO NUMBER(8);

update amtb_account set TD_AR_CONTROL_CODE_NO = AR_CONTROL_CODE_NO;


--All accounts that are tagged to the fake entity will be transferred to Cabcharge entity. 
update amtb_account set AR_CONTROL_CODE_NO= (select ar_control_code_no from FMTB_AR_CONT_CODE_MASTER where ar_control_code='TDIBS'and entity_no in (select entity_no from FMTB_ENTITY_MASTER where entity_code='CCA'))
where account_no in (select card_acct from TD_ACCT_MAPPING where card_acct is not null);