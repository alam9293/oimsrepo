
alter table PMTB_TRANSFER_REQ add TRANSFER_CARD_VALUE number(19,2);

alter table PMTB_TRANSFER_REQ add TRANSFER_CASHPLUS number(19,2);

alter table PMTB_TRANSFER_REQ add TRANSFER_ALL_FLAG varchar2(20 char);