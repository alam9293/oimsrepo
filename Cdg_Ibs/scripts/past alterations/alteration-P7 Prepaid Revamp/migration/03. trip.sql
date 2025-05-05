--to set status as billed for those existsing transaction of prepaid products

update tmtb_acquire_txn set txn_status='B' where txn_status='A'  and PRODUCT_TYPE_ID in (select PRODUCT_TYPE_ID from PMTB_PRODUCT_TYPE where PREPAID='Y');