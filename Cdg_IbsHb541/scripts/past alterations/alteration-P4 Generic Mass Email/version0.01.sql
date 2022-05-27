create TABLE MSTB_GENERIC_EMAIL_TEMPLATE
(
   NAME varchar2(80) PRIMARY KEY NOT NULL,
   CONTENT varchar2(2000) NOT NULL,
   VERSION number(9) DEFAULT 0,
   CREATED_BY varchar2(80),
   CREATED_DT timestamp,
   UPDATED_BY varchar2(80),
   UPDATED_DT timestamp
);

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES (707,537,'Generic Mass Email','U','/generic_mass_email/generic_mass_email.zul',201,'Generic Mass Email','Y',0);