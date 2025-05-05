alter table TMTB_ACQUIRE_TXN add ( incentive_amt decimal(12,2) );

alter table tmtb_txn_review_req add ( incentive_amt decimal(12,2) );

alter table TMTB_NON_BILLABLE_TXN add ( incentive_amt decimal(12,2) );

alter table ITTB_FMS_DRVR_RFND_COL_REQ add ( incentive_amt decimal(12,2) );