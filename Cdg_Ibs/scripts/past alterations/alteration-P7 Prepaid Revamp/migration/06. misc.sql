--back up credit limit column
alter table pmtb_product add td_credit_limit number(19,2);

update pmtb_product set td_credit_limit = credit_limit;

--set credit limit as null for all prepaid products
update pmtb_product set credit_limit = null 
where product_type_id in ( 
        select t.product_type_id from pmtb_product_type t where t.prepaid='Y' 
); 
