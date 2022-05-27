alter table BMTB_ISSUANCE_INVOICE_TXN rename column remarks to description;

alter table BMTB_TOP_UP_INVOICE_TXN rename column remarks to description;


alter table BMTB_ISSUANCE_INVOICE_TXN add TXN_CODE number(10,0) ;

alter table BMTB_ISSUANCE_INVOICE_TXN add constraint FK8B8BCBA8436DA5B foreign key (TXN_CODE) 
references FMTB_TRANSACTION_CODE ;

rename PMTB_PREPAID_TXN_SQ1 to PMTB_PREPAID_CARD_TXN_SQ1;


alter table Tmtb_Txn_Review_Req add ADMIN_FEE_VALUE number(12,2) ;

alter table Tmtb_Txn_Review_Req add GST_VALUE number(12,2) ;


CREATE INDEX PMTB_PREPAID_CARD_TXN_INDEX1 ON PMTB_PREPAID_CARD_TXN (PMTB_PRODUCT, TXN_DATE);

CREATE INDEX PMTB_PREPAID_CARD_TXN_INDEX2 ON PMTB_PREPAID_CARD_TXN (PMTB_PRODUCT, TXN_NO);

CREATE INDEX PMTB_PREPAID_CARD_TXN_INDEX3 ON PMTB_PREPAID_CARD_TXN (TXN_TYPE);


alter table MSTB_PROMOTION_CASHPLUS drop column status;