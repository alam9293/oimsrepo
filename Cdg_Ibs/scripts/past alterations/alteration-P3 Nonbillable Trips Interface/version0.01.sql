--Not required to run the commented script below
/*
alter table RCVW_INTF_SETL_FOR_IBS add(
  OFFLINE_FLAG      VARCHAR2(1),
  OFFLINE_TXN_DATE  DATE
);
alter table RCVW_INTF_TRIPS_FOR_IBS add(
  OFFLINE_FLAG      VARCHAR2(1),
  OFFLINE_TXN_DATE  DATE
);
*/

alter table ITTB_SETL_TXN add(
  OFFLINE_FLAG      VARCHAR2(1),
  OFFLINE_TXN_DATE  DATE
);
alter table ITTB_TRIPS_TXN add(
  OFFLINE_FLAG      VARCHAR2(1),
  OFFLINE_TXN_DATE  DATE
);
alter table ITTB_TRIPS_TXN_ERROR add(
  OFFLINE_FLAG      VARCHAR2(1),
  OFFLINE_TXN_DATE  DATE
);
alter table Tmtb_Non_Billable_Txn add(
  OFFLINE_FLAG      VARCHAR2(1)
);

insert into MSTB_MASTER_TABLE values (MSTB_MASTER_TABLE_SQ1.nextVal, 'APT', 'F', 'NFP', 'NETS FLASHPAY', 'A', 0);
insert into MSTB_MASTER_TABLE values (MSTB_MASTER_TABLE_SQ1.nextVal, 'APT', 'C', 'CUP', 'CUP', 'A', 0);
insert into MSTB_MASTER_TABLE values (MSTB_MASTER_TABLE_SQ1.nextVal, 'APT', 'E', 'EPIN', 'EPIN', 'A', 0);
insert into MSTB_MASTER_TABLE values (MSTB_MASTER_TABLE_SQ1.nextVal, 'APT', 'Z', 'EZL', 'EZLINK', 'A', 0);