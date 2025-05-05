alter table TMTB_ACQUIRE_TXN add ( promo_amt decimal(12,2) );

-- alter table ITTB_FMS_DRVR_RFND_COL_REQ add ( promo_amt decimal(12,2) );

alter table tmtb_txn_review_req add ( promo_amt decimal(12,2) );

alter table TMTB_NON_BILLABLE_TXN add ( promo_amt decimal(12,2) );

alter table ittb_trips_txn add ( promo_amt decimal(12,2) );

alter table ittb_trips_txn_error add ( promo_amt decimal(12,2) );

update ITTB_FMS_DRVR_RFND_COL_REQ set promo_amt=0 where promo_amt is null;

alter table rcvw_intf_trips_for_IBS add ( promo_amt decimal(12,2) );