--SELECT owner, constraint_name, constraint_type, table_name from user_constraints WHERE table_name = 'SATB_USER'
--need to drop constraint first before you can drop index
alter table SATB_USER drop constraint SACT_USER_U1;
DROP INDEX SACT_USER_U1;

--add new column DOMAIN
ALTER TABLE SATB_USER ADD
(
   DOMAIN varchar2(80)
);

--UPDATE EXISTING DATA TO A SPECIFIC DOMAIN
update SATB_USER set DOMAIN = 'TestDomain';

--MODIFY COLUMN NOT NULL
ALTER TABLE SATB_USER modify DOMAIN not null;

ALTER TABLE SATB_USER ADD CONSTRAINT SACT_USER_U1 UNIQUE (LOGIN_ID, DOMAIN);
CREATE UNIQUE INDEX SACT_USER_U1 ON SATB_USER(LOGIN_ID, DOMAIN);