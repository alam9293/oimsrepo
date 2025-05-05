/**********************************************/
/**************** DROP STATMENTS **************/
/**********************************************/

drop table  SATB_PASSWORD                   cascade constraints ; 
drop table  SATB_AUDIT_LOG                  cascade constraints ; 
drop table  SATB_RESOURCE                   cascade constraints ; 
drop table  SATB_ROLE                       cascade constraints ; 
drop table  SATB_ROLE_RESOURCE              cascade constraints ; 
drop table  SATB_SESSION                    cascade constraints ; 
drop table  SATB_USER                       cascade constraints ; 
drop table  SATB_USER_ROLE                  cascade constraints ;

drop sequence  SATB_AUDIT_LOG_SQ1          ;   
drop sequence  SATB_PASSWORD_SQ1           ;   
drop sequence  SATB_ROLE_SQ1               ;   
drop sequence  SATB_SESSION_SQ1            ;   
drop sequence  SATB_USER_SQ1               ; 


/**********************************************/
/**************** SEQUENCE ********************/
/**********************************************/

CREATE SEQUENCE  "SATB_AUDIT_LOG_SQ1"  MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 71 CACHE 10 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SATB_PASSWORD_SQ1"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SATB_ROLE_SQ1"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SATB_SESSION_SQ1"  MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 152 CACHE 10 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SATB_USER_SQ1"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 3 CACHE 10 NOORDER  NOCYCLE ;

/**********************************************/
/**************** TABLES **********************/
/**********************************************/

  CREATE TABLE "SATB_AUDIT_LOG" 
   (	"LOG_ID" NUMBER(18,0) NOT NULL, 
	"LOGIN_ID" VARCHAR2(32), 
	"ACTION" VARCHAR2(16) NOT NULL, 
	"ENTITY" VARCHAR2(64), 
	"ENTITY_ID" VARCHAR2(64), 
	"NEW_VALUE" VARCHAR2(2048), 
	"OLD_VALUE" VARCHAR2(2048), 
	"TIME" TIMESTAMP (6)
   ) ;
 
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."ACTION" IS 'I - INSERT  U - UPDATE  D - DELETE';
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."ENTITY" IS 'The entity (class) that is being modified.';
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."ENTITY_ID" IS 'The primary key of the record that is being modified.';
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."NEW_VALUE" IS 'The new value of the attribute.';
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."OLD_VALUE" IS 'The old value of the attribute.';
   COMMENT ON COLUMN "SATB_AUDIT_LOG"."TIME" IS 'The time the modification was made.';
   COMMENT ON TABLE "SATB_AUDIT_LOG"  IS 'Stores the audit log information for every data modification.';
   ALTER TABLE "SATB_AUDIT_LOG" ADD CONSTRAINT "SATB_AUDIT_LOG_PK" PRIMARY KEY ("LOG_ID") ENABLE;

/**********************************************/

  CREATE TABLE "SATB_USER" 
   (	"USER_ID" NUMBER(10,0) NOT NULL, 
	"LOGIN_ID" VARCHAR2(80) NOT NULL, 
	"PASSWORD" VARCHAR2(64), 
	"NAME" VARCHAR2(80) NOT NULL, 
	"EMAIL" VARCHAR2(128), 
	"STATUS" CHAR(1) DEFAULT 'A' NOT NULL, 
	"LOCKED" CHAR(1) DEFAULT 'N' NOT NULL, 
	"CREATED_BY" VARCHAR2(80),
	"CREATED_DT" TIMESTAMP,
	"UPDATED_BY" VARCHAR2(80),
	"UPDATED_DT" TIMESTAMP,
	"VERSION" NUMBER(9,0) DEFAULT 0 NOT NULL
   ) ;
 
   COMMENT ON COLUMN "SATB_USER"."NAME" IS 'User display name';
   COMMENT ON COLUMN "SATB_USER"."EMAIL" IS 'User notification email';
   COMMENT ON COLUMN "SATB_USER"."STATUS" IS 'N - New  A - Active  I - Inactive' ;
   COMMENT ON COLUMN "SATB_USER"."LOCKED" IS 'Y - Yes  N - No';
   COMMENT ON TABLE "SATB_USER"  IS 'Stores the users'' login information';
   ALTER TABLE "SATB_USER" ADD CONSTRAINT "SATB_USER_PK" PRIMARY KEY ("USER_ID") ENABLE;
   ALTER TABLE "SATB_USER" ADD CONSTRAINT "SACT_USER_U1" UNIQUE ("LOGIN_ID") ENABLE;
   
/**********************************************/

  CREATE TABLE "SATB_PASSWORD" 
   (	"PASSWORD_ID" NUMBER(10,0) NOT NULL, 
	"USER_ID" NUMBER(10,0) NOT NULL, 
	"PASSWORD" VARCHAR2(64), 
	"TIME" TIMESTAMP (6) NOT NULL
   ) ;
 
   COMMENT ON COLUMN "SATB_PASSWORD"."TIME" IS 'The date and time the password was changed.';
   COMMENT ON TABLE "SATB_PASSWORD"  IS 'Stores the users'' password history.';
   ALTER TABLE "SATB_PASSWORD" ADD CONSTRAINT "SATB_PASSWORD_PK" PRIMARY KEY ("PASSWORD_ID") ENABLE;
   ALTER TABLE "SATB_PASSWORD" ADD CONSTRAINT "SATB_PASSWORD_FK1" FOREIGN KEY ("USER_ID") REFERENCES "SATB_USER" ("USER_ID") ON DELETE CASCADE ENABLE;

/**********************************************/

  CREATE TABLE "SATB_RESOURCE" 
   (	"RSRC_ID" NUMBER(10,0) NOT NULL, 
	"PAR_RSRC_ID" NUMBER(10,0), 
	"RSRC_NAME" VARCHAR2(64) NOT NULL, 
	"RSRC_TYPE" CHAR(1) DEFAULT 'U',
	"URI" VARCHAR2(128), 
	"SEQUENCE" NUMBER(5,0), 
	"DISPLAY_NAME" VARCHAR2(64) NOT NULL, 
	"DISPLAY" CHAR(1) DEFAULT 'Y' NOT NULL,
	"VERSION" NUMBER(9,0) DEFAULT 0 NOT NULL
   ) ;

   COMMENT ON COLUMN "SATB_RESOURCE"."RSRC_TYPE" IS 'A = Admin, U = User';
   COMMENT ON COLUMN "SATB_RESOURCE"."URI" IS 'URI to the resource';
   COMMENT ON COLUMN "SATB_RESOURCE"."SEQUENCE" IS 'Display ordering';
   COMMENT ON TABLE "SATB_RESOURCE"  IS 'Stores the URIs to the available resources';
   ALTER TABLE "SATB_RESOURCE" ADD CONSTRAINT "SATB_RESOURCE_PK" PRIMARY KEY ("RSRC_ID") ENABLE;
   ALTER TABLE "SATB_RESOURCE" ADD CONSTRAINT "SACT_RESOURCE_U1" UNIQUE ("RSRC_NAME") ENABLE;
   ALTER TABLE "SATB_RESOURCE" ADD CONSTRAINT "SACT_RESOURCE_U2" UNIQUE ("URI") ENABLE;
   ALTER TABLE "SATB_RESOURCE" ADD CONSTRAINT "SATB_RESOURCE_FK1" FOREIGN KEY ("PAR_RSRC_ID") REFERENCES "SATB_RESOURCE" ("RSRC_ID") ON DELETE CASCADE ENABLE;

/**********************************************/

  CREATE TABLE "SATB_ROLE" 
   (	"ROLE_ID" NUMBER(10,0) NOT NULL, 
	"NAME" VARCHAR2(64) NOT NULL, 
	"STATUS" CHAR(1) DEFAULT 'A' NOT NULL, 
	"CREATED_BY" VARCHAR2(80),
	"CREATED_DT" TIMESTAMP,
	"UPDATED_BY" VARCHAR2(80),
	"UPDATED_DT" TIMESTAMP,
	"VERSION" NUMBER(9,0) DEFAULT 0 NOT NULL
   ) ;
 
   COMMENT ON COLUMN "SATB_ROLE"."NAME" IS 'Role name';
   COMMENT ON COLUMN "SATB_ROLE"."STATUS" IS 'A - Active  I - Inactive';
   COMMENT ON TABLE "SATB_ROLE"  IS 'Identifies a role';
   ALTER TABLE "SATB_ROLE" ADD CONSTRAINT "SATB_ROLE_PK" PRIMARY KEY ("ROLE_ID") ENABLE;
   ALTER TABLE "SATB_ROLE" ADD CONSTRAINT "SACT_ROLE_U1" UNIQUE ("NAME") ENABLE;
 
/**********************************************/

  CREATE TABLE "SATB_ROLE_RESOURCE" 
   (	"ROLE_ID" NUMBER(10,0) NOT NULL, 
	"RSRC_ID" NUMBER(10,0) NOT NULL
   ) ;

   COMMENT ON TABLE "SATB_ROLE_RESOURCE"  IS 'Maps roles to resources';
   ALTER TABLE "SATB_ROLE_RESOURCE" ADD CONSTRAINT "SATB_ROLE_RESOURCE_PK" PRIMARY KEY ("ROLE_ID", "RSRC_ID") ENABLE;
   ALTER TABLE "SATB_ROLE_RESOURCE" ADD CONSTRAINT "SATB_ROLE_RESOURCE_FK1" FOREIGN KEY ("ROLE_ID") REFERENCES "SATB_ROLE" ("ROLE_ID") ON DELETE CASCADE ENABLE;
   ALTER TABLE "SATB_ROLE_RESOURCE" ADD CONSTRAINT "SATB_ROLE_RESOURCE_FK2" FOREIGN KEY ("RSRC_ID") REFERENCES "SATB_RESOURCE" ("RSRC_ID") ON DELETE CASCADE ENABLE;
 
/**********************************************/

  CREATE TABLE "SATB_SESSION" 
   (	"SESSION_ID" NUMBER(18,0) NOT NULL, 
	"LOGIN_ID" VARCHAR2(80) NOT NULL, 
	"LOGIN_TIME" TIMESTAMP (6), 
	"IP_ADDR" VARCHAR2(32), 
	"STATUS" CHAR(1), 
	"LAST_ACTIVITY_TIME" TIMESTAMP (6), 
	"SESSION_KEY" VARCHAR2(256)
   ) ;
 
   COMMENT ON COLUMN "SATB_SESSION"."STATUS" IS 'S - Success  F - Failure';
   ALTER TABLE "SATB_SESSION" ADD CONSTRAINT "SATB_SESSION_PK" PRIMARY KEY ("SESSION_ID") ENABLE;

/**********************************************/

  CREATE TABLE "SATB_USER_ROLE" 
   (	"USER_ID" NUMBER(10,0) NOT NULL, 
	"ROLE_ID" NUMBER(10,0) NOT NULL
   ) ;
 
   COMMENT ON TABLE "SATB_USER_ROLE"  IS 'Maps users to roles';
   ALTER TABLE "SATB_USER_ROLE" ADD CONSTRAINT "SATB_USER_ROLE_PK" PRIMARY KEY ("USER_ID", "ROLE_ID") ENABLE;
   ALTER TABLE "SATB_USER_ROLE" ADD CONSTRAINT "SATB_USER_ROLE_FK1" FOREIGN KEY ("ROLE_ID") REFERENCES "SATB_ROLE" ("ROLE_ID") ON DELETE CASCADE ENABLE;
   ALTER TABLE "SATB_USER_ROLE" ADD CONSTRAINT "SATB_USER_ROLE_FK2" FOREIGN KEY ("USER_ID") REFERENCES "SATB_USER" ("USER_ID") ON DELETE CASCADE ENABLE;
-- CREATING INDEX
CREATE INDEX "SATB_PASSWORD_N1" ON SATB_PASSWORD("USER_ID");
CREATE INDEX "SATB_RESOURCE_N1" ON SATB_RESOURCE("PAR_RSRC_ID");
CREATE INDEX "SATB_ROLE_RESOURCE_N1" ON SATB_ROLE_RESOURCE("ROLE_ID");
CREATE INDEX "SATB_ROLE_RESOURCE_N2" ON SATB_ROLE_RESOURCE("RSRC_ID");
CREATE INDEX "SATB_USER_ROLE_N1" ON SATB_USER_ROLE("ROLE_ID");
CREATE INDEX "SATB_USER_ROLE_N2" ON SATB_USER_ROLE("USER_ID");
/**********************************************/
/**************** SAMPLE DATA *****************/
/**********************************************/

--REM INSERTING into SATB_USER
Insert into SATB_USER (USER_ID,LOGIN_ID,PASSWORD,NAME,EMAIL,STATUS,LOCKED,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (3,'approver','e38ad214943daad1d64c102faec29de4afe9da3d','Super Approver','yiming@wizvision.com','A','N','admin',to_timestamp('06-MAR-09 04.56.24.921000000 PM','DD-MON-RR HH.MI.SS.FF AM'),null,null,217);
Insert into SATB_USER (USER_ID,LOGIN_ID,PASSWORD,NAME,EMAIL,STATUS,LOCKED,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (2,'user','e38ad214943daad1d64c102faec29de4afe9da3d','Super User','yiming@wizvision.com','A','N','admin',to_timestamp('06-MAR-09 04.56.24.921000000 PM','DD-MON-RR HH.MI.SS.FF AM'),null,null,217);
Insert into SATB_USER (USER_ID,LOGIN_ID,PASSWORD,NAME,EMAIL,STATUS,LOCKED,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (1,'admin','e38ad214943daad1d64c102faec29de4afe9da3d','Administrator','admin@wizvision.com','A','N',null,null,null,null,211);

--REM INSERTING into SATB_RESOURCE
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (0,null,'Root','U','Root',1,'Root','N',18);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (1,0,'Common','U','Common',1,'Common (Do not assign to any roles)','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (2,0,'Common Public','U','Common Public',2,'Common Public (Default assigned to all roles)','N',17);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (3,1,'Login','U','/login.zul',1,'Login','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (4,2,'Index','U','/index.zul',1,'Index','N',17);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (5,0,'Access Control','A','Access Control',3,'Access Control','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (6,5,'User Mgmt','A','User Mgmt',1,'User Mgmt','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (7,6,'Search User','A','/acl/user/search_user.zul',1,'Edit User','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (8,6,'Create User','A','/acl/user/create_user.zul',2,'Create User','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (9,6,'Edit User','A','/acl/user/edit_user.zul',3,'Edit User','N',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (10,5,'Role Mgmt','A','Role Mgmt',2,'Role Mgmt','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (11,10,'Search Role','A','/acl/role/search_role.zul',1,'Edit Role','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (12,10,'Create Role','A','/acl/role/create_role.zul',2,'Create Role','Y',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (13,10,'Edit Role','A','/acl/role/edit_role.zul',3,'Edit Role','N',1);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (14,2,'Change Password','U','/acl/user/change_password.zul',2,'Change Password','N',17);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (15,0,'Acct Mgmt','U','Acct Mgmt',4,'Acct Mgmt','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (16,15,'Manage Acct Types','U','/acct/type/manage.zul',1,'Manage Account Types','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (17,15,'App','U','Application',2,'Application','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (18,17,'Create Corp App','U','/acct/app/createCorp.zul',1,'Create Corporate Application','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (19,17,'Manage App','U','/acct/app/manage.zul',3,'Manage Application','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (20,17,'Create Pers App','U','/acct/app/createPers.zul',2,'Create Personal Application','Y',16);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (21,0,'Product Mgmt','U','Product Mgmt',5,'Product Mgmt','Y',15);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (22,21,'Manage Product Types','U','/product/type/manage.zul',1,'Manage Product Types','Y',15);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (23,21,'Issue Products','U','/product/issue_products.zul',2,'Issue Products','Y',15);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (24,21,'Manage Existing Products','U','/product/manage_existing_products.zul',3,'Manage Existing Products','Y',15);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (25,0,'Billing','U','Billing',7,'Billing','Y',5);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (29,25,'Invoice','U','Invoice',1,'Invoice','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (26,29,'Issue Credit','U','/billing/issue_credit.zul',1,'Issue Credit/Debit Note','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (27,29,'Manage Credit/Debit Note','U','/billing/note_manage.zul',2,'Manage Credit/Debit Note','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (28,29,'Issue Draft Invoice','U','/billing/issue_draft_invoice.zul',3,'Issue Draft Invoice','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (30,15,'Add Acct Type','U','/acct/type/add.zul',1,'Add Account Type','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (31,17,'View Corp App','U','/acct/app/viewCorp.zul',4,'View Corporate Application','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (32,17,'View Pers App','U','/acct/app/viewPers.zul',5,'View Personal Application','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (33,17,'Approve Corp App','U','/acct/app/approveCorp.zul',6,'Approve Corporate Application','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (34,17,'Approve Pers App','U','/acct/app/approvePers.zul',7,'Approve Personal Application','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (35,17,'Approve Corp App2','U','/acct/app/approveCorp2.zul',6,'Approve Corporate Application2','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (36,17,'Approve Pers App2','U','/acct/app/approvePers2.zul',7,'Approve Personal Application2','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (37,15,'Manage Accounts','U','/acct/acct/search.zul',3,'Manage Accounts','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (38,25,'Payment','U','Payment',2,'Payment','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (39,38,'Create Payment Receipt','U','/billing/payment/create_payment_receipt.zul',1,'Create Payment Receipt','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (40,21,'Recyle OTU Products','U','/product/recycle_otu_products.zul',4,'Recycle OTU Products','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (42,37,'Manage Corp Acct','U','/acct/acct/manageCorp.zul',1,'Manage Corporate Account','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (43,37,'Manage Pers Acct','U','/acct/acct/managePers.zul',1,'Manage Personal Account','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (44,37,'Edit PA Corp','U','/acct/acct/editPACorp.zul',1,'Edit Pending Activation Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (45,37,'Edit PA Pers','U','/acct/acct/editPAPers.zul',1,'Edit Pending Activation Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (46,37,'View Corp','U','/acct/acct/viewCorp.zul',1,'View Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (47,37,'View Pers','U','/acct/acct/viewPers.zul',1,'View Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (48,37,'Edit Corp','U','/acct/acct/editCorp.zul',1,'Edit Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (49,37,'Edit Pers','U','/acct/acct/editPers.zul',1,'Edit Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (50,37,'Search Contact','U','/acct/acct/searchContact.zul',1,'Search Contact','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (51,37,'View Contact','U','/acct/acct/viewContact.zul',1,'View Contact','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (52,37,'Edit Contact','U','/acct/acct/editContact.zul',1,'Edit Contact','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (53,37,'Add Contact','U','/acct/acct/addContact.zul',1,'Add Contact','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (54,37,'Search Division','U','/acct/acct/searchDivision.zul',1,'Search Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (55,37,'View Division','U','/acct/acct/viewDivision.zul',1,'View Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (56,37,'Edit Division','U','/acct/acct/editDivision.zul',1,'Edit Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (57,37,'Add Division','U','/acct/acct/addDivision.zul',1,'Add Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (58,37,'Search Dept','U','/acct/acct/searchDept.zul',1,'Search Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (59,37,'View Dept','U','/acct/acct/viewDept.zul',1,'View Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (60,37,'Edit Dept','U','/acct/acct/editDept.zul',1,'Edit Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (61,37,'Add Dept','U','/acct/acct/addDept.zul',1,'Add Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (62,37,'Search Sub Pers','U','/acct/acct/searchSubPers.zul',1,'Search Sub Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (63,37,'View Sub Pers','U','/acct/acct/viewSubPers.zul',1,'View Sub Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (64,37,'Edit Sub Pers','U','/acct/acct/editSubPers.zul',1,'Edit Sub Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (65,37,'Add Sub Pers','U','/acct/acct/addSubPers.zul',1,'Add Sub Personal','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (66,37,'Edit PA Billing','U','/acct/acct/editPABilling.zul',1,'Edit Pending Activation Billing','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (67,37,'View Billing','U','/acct/acct/viewBilling.zul',1,'View Billing','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (68,37,'Edit Billing','U','/acct/acct/editBilling.zul',1,'Edit Billing','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (69,37,'Edit PA Rewards','U','/acct/acct/editPARewards.zul',1,'Edit Pending Activation Rewards','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (70,37,'View Rewards','U','/acct/acct/viewRewards.zul',1,'View Rewards','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (71,37,'View Deposit','U','/acct/acct/viewDeposit.zul',1,'View Deposit','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (72,37,'Suspend Corp','U','/acct/acct/suspendCorp.zul',1,'Suspend Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (73,37,'Reactivate Corp','U','/acct/acct/reactivateCorp.zul',1,'Reactivate Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (74,37,'Terminate Corp','U','/acct/acct/terminateCorp.zul',1,'Terminate Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (75,37,'Credit Review Corp','U','/acct/acct/creditReviewCorp.zul',1,'Credit Review Corporate','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (76,37,'Suspend Division','U','/acct/acct/suspendDivision.zul',1,'Suspend Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (77,37,'Reactivate Division','U','/acct/acct/reactivateDivision.zul',1,'Reactivate Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (78,37,'Terminate Division','U','/acct/acct/terminateDivision.zul',1,'Terminate Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (79,37,'Credit Review Division','U','/acct/acct/creditReviewDivision.zul',1,'Credit Review Division','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (80,37,'Suspend Dept','U','/acct/acct/suspendDept.zul',1,'Suspend Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (81,37,'Reactivate Dept','U','/acct/acct/reactivateDept.zul',1,'Reactivate Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (82,37,'Terminate Dept','U','/acct/acct/terminateDept.zul',1,'Terminate Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (83,37,'Credit Review Dept','U','/acct/acct/creditReviewDept.zul',1,'Credit Review Department','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (84,37,'Manage Prod Subsc','U','/acct/acct/manageProdSubsc.zul',1,'Manage Product Subscription','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (85,37,'Add Prod Subsc','U','/acct/acct/addProdSubsc.zul',1,'Add Product Subscription','N',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY) VALUES ('87', '38', 'Apply Partial Payment', 'U', '/billing/payment/apply_partial_payment.zul', '2', 'Apply Partial Payment', 'N');
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY) VALUES ('88', '38', 'Apply Excess Amount Payment Receipt', 'U', '/billing/payment/apply_excess_amount_payment_receipt.zul', '3', 'Apply Excess Amount Payment Receipt', 'N');
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (89,15,'Acct Approvals','U','Acct Approvals',4,'Account Approvals','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (90,89,'Billing Requests','U','/acct/approval/viewPendingBilling.zul',1,'Billing Requests','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (91,89,'Credit Review Requests','U','/acct/approval/viewPendingCreditReview.zul',2,'Credit Review Requests','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (92,38,'Search Payment Receipt','U','/billing/payment/search_payment_receipt.zul',4,'Maint Payment Receipt','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (93,38,'View Payment Receipt','U','/billing/payment/view_payment_receipt.zul',5,'View Payment Receipt','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (94,15,'Transfer Acct','U','Transfer Acct',5,'Transfer Account','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (95,94,'Transfer Accts','U','/acct/transfer/transferAcct.zul',1,'Transfer Account','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (96,94,'View Transfers','U','/acct/transfer/viewTransfers.zul',2,'View Transfer Requests','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (97,94,'View Transfer','U','/acct/transfer/viewTransfer.zul',1,'View Transfer Request','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (98,0,'Bill Gen','U','Bill Gen',9,'Bill Gen','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (99,98,'Create Bill Gen Request','U','/billgen/create_billgen_request.zul',1,'Create Bill Gen Request','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (100,98,'Search Bill Gen Request','U','/billgen/search_billgen_request.zul',2,'Search Bill Gen Request','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (101,98,'Configure Bill Gen Setup','U','/billgen/configure_billgen_setup.zul',3,'Configure Bill Gen Setup','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (102,91,'Approve Credit Review Request','U','/acct/approval/approveCreditReview.zul',3,'Approve Credit Review Request','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (103,89,'Approve Billing Request','U','/acct/approval/approveBilling.zul',3,'Approve Billing Request','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (501,0,'Txn','U','Txn',7,'Txn','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (502,501,'Maint Txn','U','/txn/search_txn.zul',1,'Maint Trips','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (503,501,'Create Txn','U','/txn/new_txn.zul',2,'Create Trips','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (504,501,'Approve','U','/txn/search_txn_req.zul',4,'Approve Trips Req','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (505,501,'Create Premier Txn','U','/txn/new_prem_txn.zul',3,'Create Premier Trips','Y',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (104,501,'Edit Txn','U','/txn/edit_txn.zul',5,'Edit Txn','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (105,501,'View Txn','U','/txn/view_txn.zul',6,'View Txn','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (106,38,'Cancel Payment Receipt','U','/billing/payment/cancel_payment_receipt.zul',6,'Cancel Payment Receipt','N',0);
INSERT into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (107,5,'Refresh Master','A','/master/refresh.zul',3,'Refresh Master','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (109,38,'Refund Excess Amount Payment Receipt','U','/billing/payment/refund_excess_amount_payment_receipt.zul',7,'Refund Excess Amount Payment Receipt','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (110,25,'Credit/Debit Note','U','Credit/Debit Note',3,'Credit/Debit Note','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (111,21,'Suspend Products','U','/product/suspend_products.zul',7,'Suspend Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (112,21,'Retag Products','U','/product/retag_products.zul',8,'Retag Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (113,21,'Replace Products','U','/product/replacement_products.zul',9,'Replace Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (114,21,'Renew Products','U','/product/renew_products.zul',10,'Renew Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (115,21,'Terminate Products','U','/product/terminate_products.zul',11,'Terminate Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (116,21,'Reactivate Products','U','/product/reactive_products.zul',12,'Reactivate Products','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (117,21,'Update Products Credit Limit','U','/product/update_credit_limit.zul',13,'Update Products Credit Limit','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (118,29,'Print Invoice','U','/billing/invoice/print_invoice.zul',13,'Print Invoice','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (119,15,'Search Portal User','U','/portal/search_portal_user.zul',7,'Portal User Mgmt','Y',0);
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (506, '29', 'Search Invoice', 'U', '/billing/invoice/search_invoice.zul', '4', 'View Invoice', 'Y', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (507, '29', 'View Invoice', 'U', '/billing/invoice/view_invoice.zul', '5', 'View Invoice', 'N', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (508, '110', 'Issue Note', 'U', '/billing/note/issue_note.zul', '6', 'Issue Credit/Debit Note', 'N', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (509, '110', 'Search Note', 'U', '/billing/note/search_note.zul', '7', 'View Credit/Debit Note', 'Y', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (510, '110', 'View Note', 'U', '/billing/note/view_note.zul', '8', 'View Credit/Debit Note', 'N', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (511, '110', 'Cancel Note', 'U', '/billing/note/cancel_note.zul', '9', 'View Credit/Debit Note', 'N', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (512, '29', 'Search Account', 'U', '/billing/invoice/search_account_for_issue_misc_invoice.zul', '10', 'Issue Misc. Account', 'Y', '0');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION) VALUES (513, '29', 'Issue Misc. Invoice', 'U', '/billing/invoice/issue_misc_invoice.zul', '11', 'Issue Misc. Invoice', 'N', '0');
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (514,0,'Loyalty & Rewards','U','Rewards',11,'Loyalty & Rewards','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (515,514,'List Loyalty Plans','U','/rewards/list_plan.zul',1,'Manage Loyalty Plans','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (516,514,'List Gifts','U','/rewards/list_category.zul',2,'Manage Gifts','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (517,514,'Search Item','U','/rewards/search_item.zul',3,'Redeem Gift','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (518,514,'Search History','U','/rewards/view_redemption_history.zul',4,'Rewards History','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (519,514,'Add Gift Item','U','/rewards/add_item.zul',5,'Add Gift Item','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (520,514,'Add Loyalty Plan Detail','U','/rewards/add_plan_detail.zul',6,'Add Loyalty Plan Detail','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (521,514,'Create Gift Category','U','/rewards/create_category.zul',7,'Create Gift Category','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (522,514,'Create Loyalty Plan','U','/rewards/create_plan.zul',8,'Create Loyalty Plan','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (523,514,'Edit Gift Item','U','/rewards/edit_item.zul',9,'Edit Gift Item','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (524,514,'Edit Loyalty Plan Detail','U','/rewards/edit_plan_detail.zul',10,'Edit Loyalty Plan Detail','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (525,514,'Move Gift Item','U','/rewards/move_item.zul',11,'Move Gift Item','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (526,514,'Stockup Gift Item','U','/rewards/stockup_item.zul',12,'Stockup Gift Item','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (527,514,'View Gift Category','U','/rewards/view_category.zul',13,'View Gift Category','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (528,514,'View Loyalty Plan','U','/rewards/view_plan.zul',14,'View Loyalty Plan','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (529,21,'Manage Inventory','U','/inventory/list_item_type.zul',6,'Manage Inventory','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (530,529,'Create Inventory Type','U','/inventory/create_item_type.zul',2,'Create Inventory Type','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (531,529,'Add Stock','U','/inventory/add_stock.zul',3,'Add Stock','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (532,529,'View Item Type','U','/inventory/view_item_type.zul',4,'View Item Type','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (533,529,'Request Issuance','U','/inventory/request_issuance.zul',5,'Request Issuance','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (534,529,'View Request','U','/inventory/view_request.zul',5,'View Request','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (535,529,'View Issuance','U','/inventory/view_issuance.zul',5,'View Issuance','N',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (537,0,'Administration','U','Administration',99,'Administration','Y',0);
INSERT INTO "IBS_USR"."SATB_RESOURCE" (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (538,537,'Manage Master List','U','/admin/manage_master_list.zul',99,'Manage Master List','Y',0);

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (536, '29', 'Search Draft Invoice', 'U', '/billing/invoice/search_draft_invoice.zul', '12', 'View Draft Invoice', 'Y', '0');
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (601,501,'AS Enquiry','U','/enquiry/enq_card.zul',1,'AS Enquiry','Y',0);
--FOR REPORTS
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (401,0,'Report','U','Report',6,'Report','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (406,401,'Receipt','U','Receipt',4,'Receipt','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (407,406,'Receipt By Period Detailed','U','/report/receipt_by_period_detailed.zul?rsrcId=407',1,'Receipt By Period Detailed','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (408,406,'Receipt By Period Summary','U','/report/receipt_by_period_summary.zul?rsrcId=408',1,'Receipt By Period Summary','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (409,406,'Daily Cheque Deposit Listing','U','/report/daily_cheque_deposit_listing.zul?rsrcId=409',1,'Daily Cheque Deposit Listing','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (410,401,'Note','U','Note',5,'Note','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (411,410,'Credit Debit Note','U','/report/credit_debit_note.zul?rsrcId=411',1,'Credit Debit Note','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (412,401,'Trips','U','Trips',6,'Trips','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (413,412,'Customer Usage','U','/report/customer_usage.zul?rsrcId=413',1,'Monthly Usage Report By Products','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (414,412,'Customer Usage Comparsion','U','/report/customer_usage_comparsion.zul?rsrcId=414',1,'Customer Usage Comparsion','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (417,412,'Sales Report by Salesperson','U','/report/sales_report_by_salesperson.zul?rsrcId=417',1,'Sales Report by Salesperson','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (418,401,'Account','U','Account',7,'Account','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (419,418,'Customer Deposit Summary','U','/report/customer_deposit_summary.zul?rsrcId=419',6,'Customer Deposit Summary','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (421,401,'Aging','U','Aging',8,'Aging','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (422,421,'Customer Aging Detail','U','/report/customer_aging_detail.zul?rsrcId=422',1,'Customer Aging Detail','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (423,412,'Cashless Transaction Trip Analysis','U','/report/cashless_txn_trip_analysis.zul?rsrcId=423',1,'Cashless Transaction Trip Analysis','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (424,421,'Customer Aging Summary','U','/report/customer_aging_summary.zul?rsrcId=424',2,'Customer Aging Summary','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (425,418,'Customer Deposit Detailed','U','/report/customer_deposit_detailed.zul?rsrcId=425',1,'Customer Deposit Detailed','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (426,412,'Soft Copy Invoice','U','/report/soft_copy_invoice_trip_details_by_invoice_no.zul?rsrcId=426',1,'Soft Copy Invoice','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (427,412,'Trips Detail','U','/report/soft_copy_invoice_trip_details_by_transaction_status.zul?rsrcId=427',1,'Trips Detail','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (431,418,'Card Issuance','U','/report/card_issuance.zul?rsrcId=431',1,'Card Issuance','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (432,412,'Transaction Summary','U','/report/transaction_summary.zul?rsrcId=432',2,'Transaction Summary','Y',0);
-- phase 2 reports
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (433,412,'Bank Chargeback Report','U','/report/bank_chargeback_report.zul?rsrcId=433',2,'Bank Chargeback Report','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (434,412,'Cashless Aging Report Detailed','U','/report/cashless_aging_report_detailed.zul?rsrcId=434',2,'Cashless Aging Report Detailed','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (435,412,'Cashless Aging Report Summary','U','/report/cashless_aging_report_summary.zul?rsrcId=435',2,'Cashless Aging Report Summary','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (436,412,'Cashless Bank Collection Summary','U','/report/cashless_bank_collection_summary.zul?rsrcId=436',2,'Cashless Bank Collection Summary','Y',0);

Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (108,401,'Product Embossing','U','/report/product_embossing.zul',2,'Generate Embossing','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (420,418,'Credit Balance','U','/report/credit_balance.zul?rsrcId=420',6,'Credit Balance','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (450,418,'Customer Report','U','/report/customer_report.zul?rsrcId=450',6,'Customer Report','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (415,401,'Product','U','Ptoduct',7,'Product','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (416,415,'Card Holder List','U','/report/cardholder_list.zul?rsrcId=416',1,'Card Holder List','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (428,415,'Card In Production','U','/report/card_in_production.zul?rsrcId=428',2,'Card In Production','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (429,401,'Revenue','U','Revenue',7,'Revenue','Y',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (430,429,'Revenue Report','U','/report/revenue.zul?rsrcId=430',6,'Revenue Report','Y',0);

INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY) VALUES ('901', '501', 'Retrieve Trips', 'U', '/txn/retrieve_txn.zul', '1', 'Retrieve Trips', 'N');
INSERT INTO SATB_RESOURCE (RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY) VALUES ('902', '502', 'Process Trips', 'U', '/txn/process_txn.zul', '2', 'Process Trips', 'N');


--REM INSERTING into SATB_ROLE
Insert into SATB_ROLE (ROLE_ID,NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (2,'SuperUser','A','admin',to_timestamp('06-MAR-09 04.49.49.937000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'admin',to_timestamp('17-MAR-09 05.10.07.546000000 PM','DD-MON-RR HH.MI.SS.FF AM'),229);
Insert into SATB_ROLE (ROLE_ID,NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (1,'SuperAdmin','A',null,null,'admin',to_timestamp('06-MAR-09 05.10.48.203000000 PM','DD-MON-RR HH.MI.SS.FF AM'),152);
Insert into SATB_ROLE (ROLE_ID,NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,VERSION) values (3,'SuperApprover','A','admin',to_timestamp('06-MAR-09 04.49.49.937000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'admin',to_timestamp('17-MAR-09 05.10.07.546000000 PM','DD-MON-RR HH.MI.SS.FF AM'),0);

--REM INSERTING into SATB_ROLE_RESOURCE
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,0);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,2);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,4);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,5);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,6);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,7);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,8);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,9);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,10);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,11);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,12);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,13);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,14);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,2);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,25);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,17);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,23);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,19);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,27);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,20);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,26);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,4);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,18);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,15);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,21);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,0);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,16);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,29);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,24);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,14);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,22);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,30);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,31);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,32);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,4);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,15);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,17);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,19);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,33);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,34);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,35);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,36);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,31);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,32);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,37);

INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,40);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,42);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,43);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,44);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,45);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,46);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,47);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,48);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,49);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,50);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,51);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,52);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,53);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,54);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,55);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,56);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,57);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,58);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,59);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,60);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,61);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,62);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,63);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,64);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,65);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,66);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,67);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,68);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,69);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,70);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,71);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,72);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,73);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,74);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,75);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,76);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,77);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,78);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,79);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,80);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,81);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,82);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,83);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,84);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,85);
--can't findINSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,86);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,87);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,88);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,89);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,90);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,91);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,94);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,95);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,96);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,97);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,503);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,501);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,502);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,504);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,505);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,102);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (3,103);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (1,107);
Insert into SATB_ROLE_RESOURCE (ROLE_ID,RSRC_ID) values (2,108);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 506);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 507);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 508);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 509);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 510);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 511);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 512);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 513);
INSERT INTO SATB_ROLE_RESOURCE (ROLE_ID, RSRC_ID) VALUES(2, 601);

--REM INSERTING into SATB_USER_ROLE
Insert into SATB_USER_ROLE (USER_ID,ROLE_ID) values (1,1);
Insert into SATB_USER_ROLE (USER_ID,ROLE_ID) values (2,2);
Insert into SATB_USER_ROLE (USER_ID,ROLE_ID) values (3,3);