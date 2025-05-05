
/*
 * Check this Load long? 
 * alot of records?
 */
select r.PRODUCT_REPLACEMENT_NO, r.PRODUCT_NO, r.CURRENT_CARD_NO, r.NEW_CARD_NO, p.PRODUCT_NO, p.CARD_NO, p.ACCOUNT_NO, p2.PRODUCT_NO, p2.CARD_NO, p2.ACCOUNT_NO
from PMTB_PRODUCT_REPLACEMENT r
inner join PMTB_PRODUCT p on r.PRODUCT_NO = p.PRODUCT_NO
inner join PMTB_PRODUCT p2 on p.ACCOUNT_NO = p2.ACCOUNT_NO and r.NEW_CARD_NO = p2.CARD_NO
where current_card_no <> new_card_no
order by 1 desc


/*
 * Do the above first before trying this
 * Add new Column Product No, so when replacing card, will know the new card no is which product no
 */
ALTER TABLE PMTB_PRODUCT_REPLACEMENT add NEW_PRODUCT_NO NUMBER(19,0);

/*
 * Do the above above first before trying this.
 * will update New Product No. into the new column
 */
update PMTB_PRODUCT_REPLACEMENT SET
NEW_PRODUCT_NO =
(SELECT p2.PRODUCT_NO from PMTB_PRODUCT_REPLACEMENT r
inner join PMTB_PRODUCT p on r.PRODUCT_NO = p.PRODUCT_NO
inner join PMTB_PRODUCT p2 on p.ACCOUNT_NO = p2.ACCOUNT_NO and r.NEW_CARD_NO = p2.CARD_NO
where   r.current_card_no <> r.new_card_no and
r.PRODUCT_REPLACEMENT_NO = PMTB_PRODUCT_REPLACEMENT.PRODUCT_REPLACEMENT_NO and rownum=1)