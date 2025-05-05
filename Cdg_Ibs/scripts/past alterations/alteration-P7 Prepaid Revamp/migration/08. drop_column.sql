--execute this only when system has been stable
alter table pmtb_product drop column TD_CREDIT_LIMIT;
alter table pmtb_product drop column TD_CREDIT_BALANCE;

alter table pmtb_product_replacement drop column td_paid_value;
alter table pmtb_product_replacement drop column td_value_plus;
alter table pmtb_product_replacement drop column td_new_status;
alter table pmtb_product_replacement drop column td_transfer_value;
alter table pmtb_product_replacement drop column td_old_card_no;

alter table pmtb_product drop column td_paid_value;
alter table pmtb_product drop column td_value_plus;
alter table pmtb_product drop column td_initial_paid_value;
alter table pmtb_product drop column td_initial_value_plus;

alter table bmtb_invoice_header drop column td_account_no;
alter table bmtb_invoice_header drop column td_debt_to;

alter table bmtb_payment_receipt drop column td_account_no;

alter table amtb_account drop column td_ar_control_code_no;


--drop previous created table
drop table td_product;

drop table td_product_replacement;

drop table td_prepaid_card_txn;


drop table td_account;
