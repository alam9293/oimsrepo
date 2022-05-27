--During migration
--1. make sure there is no any pending approve trip adjustment of prepaid product
--2. this migration script should be be executed start from 01 to 07, one by one from top to bottom
--3. script 08 is to be executed after data migration performed after few months and all things getting stable. 
--4. all scripts should only be executed while system is down to avoid concurrency issue

--populate balance expiry date for existing product
update pmtb_product set balance_expiry_date = expiry_date
where product_no in (
	select product_no from pmtb_product p
	inner join pmtb_product_type t on p.PRODUCT_TYPE_ID=t.PRODUCT_TYPE_ID 
	where t.prepaid='Y'
) and product_no not in (
select product_no from PMTB_ISSUANCE_REQ_CARD
);