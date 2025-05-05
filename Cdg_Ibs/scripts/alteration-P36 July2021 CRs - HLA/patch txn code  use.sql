
-- find the nonbillable premium txn code.
-- select * from FMTB_TRANSACTION_CODE order by TRANSACTION_CODE_NO desc;

-- find the transaction code no from the first line.
-- update fmtb_non_billable_detail set PREMIUM_AMOUNT_TXN_CODE = (select txn_code from fmtb_transaction_code where TRANSACTION_CODE_NO = '385');

