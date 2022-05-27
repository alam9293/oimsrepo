CREATE OR REPLACE VIEW asvw_product
AS
  SELECT "CARD_NO",
    "ACCT_ID",
    "PROD_TYPE_ID",
    "FIXED_VALUE",
    "CREDIT_LIMIT",
    "TOTAL_TXN_AMT",
    "STATUS",
    "REASON_CODE",
    "USED",
    "OFFLINE_COUNT_LIMIT",
    "OFFLINE_AMT_LIMIT",
    "OFFLINE_TXN_LIMIT",
    "FORCE_ONLINE",
    "CREATE_DATE",
    "CREATE_BY",
    "UPDATE_DATE",
    "UPDATE_BY"
  FROM product;