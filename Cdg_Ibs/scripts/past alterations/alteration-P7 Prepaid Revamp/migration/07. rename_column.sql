alter table pmtb_product_replacement rename column paid_value to td_paid_value;
alter table pmtb_product_replacement rename column value_plus to td_value_plus;
alter table pmtb_product_replacement rename column new_status to td_new_status;
alter table pmtb_product_replacement rename column transfer_value to td_transfer_value;
alter table pmtb_product_replacement rename column old_card_no to td_old_card_no;

alter table pmtb_product rename column paid_value to td_paid_value;  
alter table pmtb_product rename column value_plus to td_value_plus;  	
alter table pmtb_product rename column initial_paid_value to td_initial_paid_value; 
alter table pmtb_product rename column initial_value_plus to td_initial_value_plus; 