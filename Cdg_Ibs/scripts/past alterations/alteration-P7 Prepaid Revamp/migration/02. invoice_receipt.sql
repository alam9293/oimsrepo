--create temporary account table, 17705 is the account no that use as a billing account of existing prepaid product. 
create table td_account as select * from amtb_account where parent_no in (17705, 27050, 32048);

create table td_acct_mapping  
(
invoice_acct number(12),
card_cust number(12),
card_acct number(12)
);

insert into td_acct_mapping (invoice_acct, card_cust) select account_no, TRIM(substr(account_name, INSTR(account_name, '-', -1)+1, LENGTH(account_name))) as cust_no from td_account;


update td_acct_mapping t set card_acct= (select account_no from amtb_account a where a.cust_no=t.card_cust);

--**this should return zero results, if there exists result please let me know
select * from td_acct_mapping where invoice_acct is null or card_cust is null or card_acct is null;


--back up the existing column
alter table bmtb_invoice_header add td_account_no NUMBER(8);

alter table bmtb_invoice_header add td_debt_to NUMBER(8);

update bmtb_invoice_header set td_account_no=account_no, td_debt_to = debt_to;

alter table BMTB_PAYMENT_RECEIPT add td_account_no NUMBER(8);

update BMTB_PAYMENT_RECEIPT set td_account_no = account_no;


--move the invoices to the account that have the cards and trips 
update bmtb_invoice_header h set account_no=(select card_acct from td_acct_mapping t where t.invoice_acct = h.td_account_no)
where h.td_account_no in (select invoice_acct from td_acct_mapping where card_acct is not null);

update bmtb_invoice_header h set debt_to =(select card_acct from td_acct_mapping t where t.invoice_acct = h.td_debt_to)
where h.td_account_no in (select invoice_acct from td_acct_mapping where card_acct is not null);


--move the receipts to the account that have the cards and trips 
update BMTB_PAYMENT_RECEIPT p set account_no =(select card_acct from td_acct_mapping t where t.invoice_acct = p.td_account_no)
where p.td_account_no in (select invoice_acct from td_acct_mapping where card_acct is not null);



--retag special case where invoice's tagged to corporate level of the billing account
--invoice no, customer_no, account_no
--1248976, 171511, 25554
--1248978, 171513, 25556
--1277826, 169189, 19948
--1349078, 169189, 19948
--1393224, 173235, 28827
update bmtb_invoice_header set account_no='25554', debt_to='25554', where INVOICE_NO='1248976';
update bmtb_invoice_header set account_no='25556', debt_to='25556', where INVOICE_NO='1248978';
update bmtb_invoice_header set account_no='19948', debt_to='19948', where INVOICE_NO='1277826';
update bmtb_invoice_header set account_no='19948', debt_to='19948', where INVOICE_NO='1349078';
update bmtb_invoice_header set account_no='28827', debt_to='28827', where INVOICE_NO='1393224';




