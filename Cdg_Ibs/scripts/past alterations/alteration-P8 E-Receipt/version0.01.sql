alter table ITTB_TRIPS_TXN add DISTANCE number(10,2);

alter table ITTB_TRIPS_TXN add DISCOUNT_AMOUNT number(6,2);

alter table ITTB_TRIPS_TXN add VOUCHER_AMOUNT number(6,2);


alter table ITTB_TRIPS_TXN_ERROR add DISTANCE number(10,2);

alter table ITTB_TRIPS_TXN_ERROR add DISCOUNT_AMOUNT number(6,2);

alter table ITTB_TRIPS_TXN_ERROR add VOUCHER_AMOUNT number(6,2);


alter table TMTB_ACQUIRE_TXN add DISTANCE number(10,2);

alter table TMTB_ACQUIRE_TXN add DISCOUNT_AMOUNT number(6,2);

alter table TMTB_ACQUIRE_TXN add VOUCHER_AMOUNT number(6,2);