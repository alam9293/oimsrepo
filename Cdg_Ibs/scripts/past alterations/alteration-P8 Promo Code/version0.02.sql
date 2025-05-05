-- drop the column and add back with a default 0 --START

alter table ITTB_FMS_DRVR_RFND_COL_REQ drop column PROMO_AMT

alter table ITTB_FMS_DRVR_RFND_COL_REQ add ( PROMO_AMT decimal(12,2) DEFAULT 0);

-- drop the column and add back with a default 0 --END