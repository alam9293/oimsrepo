CREATE TABLE SATB_EMAIL_AUDIT_LOG (
	EMAIL_LOG_ID NUMBER(8) NOT NULL,
	EMAIL_FROM VARCHAR2(500) NOT NULL,
	EMAIL_TO VARCHAR2(500) NOT NULL,
	SEND_DT TIMESTAMP NOT NULL,
	SUB_HEADER VARCHAR2(100),
	JOB VARCHAR2(50),
	STATUS VARCHAR2(8),
	STATUS_REMARKS VARCHAR2(500),
	CREATED_BY VARCHAR2(40)
);

-- Create SEQUENCE
CREATE SEQUENCE  "SASQ_EMAIL_AUDIT_LOG"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 2 CACHE 10 NOORDER  NOCYCLE ;

-- Create Index
CREATE INDEX SAIX_EMAIL_AUDIT_LOG__EMAILTO ON SATB_EMAIL_AUDIT_LOG(EMAIL_TO);
CREATE INDEX SAIX_EMAIL_AUDIT_LOG__SEND_DT ON SATB_EMAIL_AUDIT_LOG(SEND_DT);
CREATE INDEX SAIX_EMAIL_AUDIT_LOG__SUB_HDR ON SATB_EMAIL_AUDIT_LOG(SUB_HEADER);
CREATE INDEX SAIX_EMAIL_AUDIT_LOG__JOB ON SATB_EMAIL_AUDIT_LOG(JOB);
CREATE INDEX SAIX_EMAIL_AUDIT_LOG__STATUS ON SATB_EMAIL_AUDIT_LOG(STATUS);

COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.EMAIL_FROM IS 'Email Address Send From ';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.EMAIL_TO IS 'Email Address Send To';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.SEND_DT IS 'Send DateTime';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.SUB_HEADER IS 'Email Subject Header';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.JOB IS 'Remarks';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.STATUS IS 'Email Send Status; Send Successful or Fail';
COMMENT ON COLUMN SATB_EMAIL_AUDIT_LOG.STATUS_REMARKS IS 'Status Remarks if fail, reasons';



-- SATB RESOURCE for Email Audit Log

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Report'), 'Email Audit', 'U', 'Email Audit', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Report'), 'Email Audit', 'Y', 0);

INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Email Audit'), 'Email Audit Log', 'U', '/report/email_audit.zul?rsrcId=', (SELECT MAX(SEQUENCE)+1 FROM SATB_RESOURCE where rsrc_name='Email Audit'), 'View Email Audit Log', 'Y', 0);

UPDATE SATB_RESOURCE
SET URI = CONCAT(URI, (SELECT RSRC_ID from SATB_RESOURCE WHERE RSRC_NAME='Email Audit Log'))
WHERE RSRC_NAME = 'Email Audit Log';

insert into MSTB_REPORT_FORMAT_MAP (map_no, rsrc_id, report_format) values ((select max(map_no)+1 from MSTB_REPORT_FORMAT_MAP), (select rsrc_id from satb_resource where rsrc_name='Email Audit Log'), 'CSV');
