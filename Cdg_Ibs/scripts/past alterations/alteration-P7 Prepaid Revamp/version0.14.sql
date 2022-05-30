alter table pmtb_product add BALANCE_FORFEITED_FLAG varchar2(10 char);

alter table PMTB_PRODUCT drop constraint FK36F42DBBCEC9D30A;

alter table PMTB_PRODUCT drop column LAST_BALANCE_FORFEITURE;