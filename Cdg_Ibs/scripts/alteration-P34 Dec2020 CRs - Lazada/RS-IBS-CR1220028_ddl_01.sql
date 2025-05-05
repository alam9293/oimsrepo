ALTER TABLE TMTB_NON_BILLABLE_TXN_CRCA
ADD DETAILS	VARCHAR(100)
ADD PAID_STATUS	VARCHAR(3)
ADD PAYMENT_REF_ID	VARCHAR(30)
ADD TRANSACTION_NUMBER	VARCHAR(30);

comment on column TMTB_NON_BILLABLE_TXN_CRCA.DETAILS is 'The transaction details. Used by Lazada';
comment on column TMTB_NON_BILLABLE_TXN_CRCA.PAID_STATUS is 'To check the paid status is being paid';
comment on column TMTB_NON_BILLABLE_TXN_CRCA.PAYMENT_REF_ID is 'Payment Reference Id from Bank or other payment provider. Used by Lazada';
comment on column TMTB_NON_BILLABLE_TXN_CRCA.TRANSACTION_NUMBER is 'Unique ID of the transaction in the format of Seller code- xxxxxxx. Used by Lazada';