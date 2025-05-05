--extends affected tables' column length
alter table IMTB_ISSUE_REQ modify (CREATE_BY VARCHAR2(80));
alter table ITTB_AS_ADD_ACCT_REQ modify (CREATE_BY VARCHAR2(80));
alter table ITTB_AS_ADD_NEG_PROD_REQ modify (CREATE_BY VARCHAR2(80));
alter table ITTB_AS_ADD_PROD_REQ modify (CREATE_BY VARCHAR2(80));
alter table ITTB_FMS_DRVR_RFND_COL_REQ modify (UPDATED_BY VARCHAR2(80));
alter table ITTB_AS_REV_ACCT_TXN_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_REV_PROD_TXN_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_ACCT_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_BILL_ACCT_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_BILL_PROD_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_PAY_ACCT_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_PAY_PROD_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_PROD_REQ modify (UPDATE_BY VARCHAR2(80));
alter table ITTB_AS_UPD_USAGE_REQ modify (UPDATE_BY VARCHAR2(80));