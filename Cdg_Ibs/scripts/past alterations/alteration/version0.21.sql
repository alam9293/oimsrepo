alter sequence PMTB_PRODUCT_STATUS_SQ1 MAXVALUE 9999999999999999999;
update mstb_master_table set master_value = 'CLEARING DOCUMENT' where master_type='PM' and master_code='CT';