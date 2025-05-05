/* **************************************************
 * Rewards txn should allow contact person to be null
 * Initial points has no input for that
 * **************************************************/
alter table LRTB_REWARD_TXN modify (
	CONTACT_PERSON_NO number(8) null 
);
alter table LRTB_REWARD_TXN modify (
	CONTACT_PERSON_NO default null
);

