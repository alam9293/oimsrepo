--Assumption:
--As previous replacement txn include info of top up, transfer and replacement fee,
--to differentiate the precedence of transaction, we set
--top up date = replacement date
--adjustment date = replacement date + 1 seconds
--transfer out date = replacement date + 2 seconds
--transfer in date = replacement date + 3 seconds
--date of replacement fee = replacement date + 4 seconds


--create a temporary prepaid product table
create table td_product as
select p.* from pmtb_product p
left join pmtb_product_type t on p.PRODUCT_TYPE_ID=t.PRODUCT_TYPE_ID 
where t.prepaid='Y';

alter table TD_PRODUCT add td_apply_amt_status varchar2(15);
alter table td_product add td_credit_balance_adjusted varchar(2);
alter table td_product add td_credit_balance number(12,2);

update td_product set td_credit_balance = credit_balance;


--create a temporary prepaid card txn table
create table TD_PREPAID_CARD_TXN as select * from PMTB_PREPAID_CARD_TXN where 1=2;
CREATE SEQUENCE "TD_PREPAID_CARD_TXN_SQ1" MINVALUE 1 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER NOCYCLE ;

alter table td_prepaid_card_txn add td_transfer_balance number(19,2);

alter table td_prepaid_card_txn add td_new_status varchar(10);

CREATE INDEX TD_PREPAID_CARD_TXN_INDEX1 ON TD_PREPAID_CARD_TXN (PMTB_PRODUCT, TXN_DATE);
CREATE INDEX TD_PREPAID_CARD_TXN_INDEX2 ON TD_PREPAID_CARD_TXN (PMTB_PRODUCT, TXN_NO);
CREATE INDEX TD_PREPAID_CARD_TXN_INDEX3 ON TD_PREPAID_CARD_TXN (TXN_TYPE);
CREATE UNIQUE INDEX TD_PREPAID_CARD_TXN_INDEX4 ON TD_PREPAID_CARD_TXN (TXN_NO);

--create a temporary product replacement table
create table TD_PRODUCT_REPLACEMENT as select * from PMTB_PRODUCT_REPLACEMENT;


--create a temporary acquire txn table
create table TD_ACQUIRE_TXN as select * from  tmtb_acquire_txn where product_no in (
	select product_no from pmtb_product p
	inner join pmtb_product_type t on p.PRODUCT_TYPE_ID=t.PRODUCT_TYPE_ID 
	where t.prepaid='Y'
);

--patch temporary product replament table
--due to system bug, duplicate record (product replacement no 12220 & 12222) were created during card replacement with new card.
--here delete the duplicate record to guarantee system consistency
delete from TD_PRODUCT_REPLACEMENT where product_replacement_no in (12222, 12223);


--populate issue txn
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, PAID_VALUE + VALUE_PLUS, 'ISSUE',product_no,null,CREATED_DT, 1, null,null,'N',null,PAID_VALUE,VALUE_PLUS,0
from TD_PRODUCT_REPLACEMENT 
where REPLACEMENT_REMARKS = 'CREATION OF NEW PREPAID CARD'
and OLD_CARD_NO is null;


--populate top up txn
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST, TD_TRANSFER_BALANCE, TD_NEW_STATUS)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, r.PAID_VALUE + r.VALUE_PLUS, 'TOP_UP',r.product_no,r.REPLACEMENT_REMARKS ,r.CREATED_DT, 1, null,null,'N',null,r.PAID_VALUE,r.VALUE_PLUS,0, r.transfer_value, r.new_status
from TD_PRODUCT_REPLACEMENT r
inner join td_product p on r.product_no=p.product_no
where REPLACEMENT_REASON!=-1;



--replacement fee of replacement without new card
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, -REPLACEMENT_FEE, 'RP_FEE',r.product_no, REPLACEMENT_REMARKS ,r.CREATED_DT + numtodsinterval(4, 'second'), 1, null,null,'N',null,null,null,0
from TD_PRODUCT_REPLACEMENT r
inner join td_product p on r.product_no=p.product_no
where REPLACEMENT_REASON!=-1 and new_status is null
and REPLACEMENT_FEE > 0;


--populate void trips
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, -t.BILLABLE_AMT, 'TRIP', t.product_no ,null,t.CREATED_DT, 1, null,t.ACQUIRE_TXN_NO,'N',null,null,null,0
from TD_ACQUIRE_TXN t
inner join td_product p on t.product_no=p.product_no
where 
txn_status = 'V';

INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, t.BILLABLE_AMT, 'VOID_TRIP', t.product_no ,null,t.UPDATED_DT, 1, null,t.ACQUIRE_TXN_NO,'N',null,null,null,0
from TD_ACQUIRE_TXN t
inner join td_product p on t.product_no=p.product_no
where
txn_status = 'V';


--populate trips
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, -t.BILLABLE_AMT, 'TRIP', t.product_no ,null,t.CREATED_DT, 1, null,t.ACQUIRE_TXN_NO,'N',null,null,null,0
from TD_ACQUIRE_TXN t
inner join td_product p on t.product_no=p.product_no
where
txn_status = 'B';


--update those updated trip txn type as EDIT_TRIP
UPDATE TD_PREPAID_CARD_TXN d SET d.txn_type = 'EDIT_TRIP'
WHERE 
EXISTS (
	select 1 from 
	(
		select a.txn_no, txn_type, job_no, pmtb_product, ROW_NUMBER() OVER (PARTITION BY job_no, pmtb_product ORDER BY txn_no) as row_num from PMTB_PREPAID_CARD_TXN a
		inner join TMTB_ACQUIRE_TXN b on a.ACQUIRE_TXN_NO = b.ACQUIRE_TXN_NO
	) c where c.row_num > 1 and c.txn_type='TRIP' and c.txn_no = d.txn_no
);


--replacement fee of replacement with new card
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, -r.REPLACEMENT_FEE, 'RP_FEE',p.product_no,null,r.CREATED_DT + numtodsinterval(4, 'second'), 1, null,null,'N',null,null,null,0
from TD_PRODUCT_REPLACEMENT r
inner join td_product p on r.new_card_no = p.card_no
where REPLACEMENT_REASON!=-1 and new_status='T' and REPLACEMENT_FEE > 0;

--populate external transfer out
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, -(PAID_VALUE + VALUE_PLUS + TRANSFER_VALUE), 'EX_TR_OUT',product_no,REPLACEMENT_REMARKS,CREATED_DT  + numtodsinterval(2, 'second'), 1, null,null,'N',null,null,null,0
from TD_PRODUCT_REPLACEMENT
where new_status='T';


--populate external transfer in 
INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
select TD_PREPAID_CARD_TXN_SQ1.nextval, PAID_VALUE + VALUE_PLUS + TRANSFER_VALUE, 'EX_TR_IN',product_no,null,CREATED_DT  + numtodsinterval(3, 'second'), 1, null,null,'N',null,null,null,0
from TD_PRODUCT_REPLACEMENT
where REPLACEMENT_REMARKS = 'CREATION OF NEW PREPAID CARD'
and old_card_no is not null;


--data patched to those terminated products, the credit balance should be deducted when theirs amount are transfer out
update td_product p set p.credit_balance = p.credit_balance - (select sum(transfer_value) as transfer_value from TD_PRODUCT_REPLACEMENT r where r.new_status='T' and p.product_no= r.product_no group by PRODUCT_NO),
p.td_credit_balance_adjusted = 'Y'
where p.product_no in 
(
select product_no from TD_PRODUCT_REPLACEMENT where new_status='T'
) and p.td_credit_balance_adjusted is null;


--transfer adjustment
declare
 cummulative_balance number(19,2);
begin
  dbms_output.enable(1000000);

  for product in (select * from td_product t where t.product_no in (select pmtb_product from td_prepaid_card_txn where txn_type='TOP_UP'))
  loop
    --dbms_output.put_line ('Processing product ' || product.product_no); 
     for txn in (select * from td_prepaid_card_txn where pmtb_product=product.product_no and txn_type='TOP_UP' and td_new_status is null order by txn_date)
     loop
      
      select sum(amount + gst) into cummulative_balance from td_prepaid_card_txn where pmtb_product=product.product_no and txn_date < txn.txn_date;
    
      if cummulative_balance!=txn.td_transfer_balance
      then
        INSERT INTO TD_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
        values( TD_PREPAID_CARD_TXN_SQ1.nextval, -(cummulative_balance-txn.td_transfer_balance), 'TR_ADJ',txn.pmtb_product, null ,txn.txn_date + numtodsinterval(1, 'second'), 1, null,null,'N',null,null,null,0);
      end if;
     end loop;
  end loop;
   dbms_output.put_line ('Completed'); 
end;



--**to check whether total txn amount is tally with credit balance, if there is result return please let me know, I will manually check the replacement records then create adjustment txn of product respectively.
WITH card_total_amt AS
(
select pmtb_product as product_no, 
sum(AMOUNT + gst) as total_amt 
from TD_PREPAID_CARD_TXN 
group by pmtb_product
),
transfer_out AS
(
select product_no, 
sum(transfer_value) as amount
from TD_PRODUCT_REPLACEMENT
where new_status='T'
group by product_no
)
select t.product_no, total_amt,  credit_balance, o.amount as transfer_amount
from card_total_amt t
left join transfer_out o on t.product_no = o.product_no
left join TD_PRODUCT p on t.product_no = p.product_no
where total_amt != CREDIT_BALANCE;
;

--update those product that have issue txn to ready populate apply amount;
update TD_PRODUCT set td_apply_amt_status='READY' where product_no in (select pmtb_product from TD_PREPAID_CARD_TXN where txn_type='ISSUE');


--populate apply card value and cash plus of txn of those products created from issuance or transfer in
--the script will loop 8 times assume that there will be no card being replaced with new card more than 8 times
DECLARE 
 current_card_value number(19,2); 
 current_cashplus number(19,2);
 usage_amt number(19,2);
 exceed_amt number(19,2);
 exceed_cashplus number(19,2);
 to_apply_card_value number(19,2); 
 to_apply_cashplus number(19,2);
 
 transfer_card_value number(19,2); 
 transfer_cashplus number(19,2);
 txf_out_prod td_product%rowtype;
 txf_in_prod td_product%rowtype;
 
 
begin
  DBMS_OUTPUT.ENABLE(1000000);  
 
	FOR i IN 1..8
	loop
	
	   dbms_output.put_line ('Processing ' || i || ' times'); 
	
	   for product in (select * from TD_PRODUCT where td_apply_amt_status='READY' )
	   loop
		--dbms_output.put_line ('Processing product ' || product.product_no); 
		
			current_card_value := 0;
			current_cashplus := 0;
			for txn in (select * from TD_PREPAID_CARD_TXN where pmtb_product = product.product_no order by txn_date)
			loop
			  --dbms_output.put_line ('Processing txn ' || txn.txn_no); 
			  if txn.txn_type='ISSUE' or txn.txn_type='EX_TR_IN' or txn.txn_type='TOP_UP'
			  then
				current_card_value:= current_card_value + txn.apply_card_value;
				current_cashplus:= current_cashplus + txn.apply_cashplus;
			  
			  else

				usage_amt := -(txn.amount + txn.gst);
				exceed_amt := current_card_value - usage_amt;
				
				to_apply_card_value := 0;
				to_apply_cashplus := 0;
				
				--dbms_output.put_line ('Usage Amt: ' || usage_amt); 
				if exceed_amt < 0
				THEN 
				  to_apply_card_value := current_card_value;
				  to_apply_cashplus := -exceed_amt;
				  
				  exceed_cashplus := current_cashplus - to_apply_cashplus;
				  if exceed_cashplus < 0
				  then
				  	to_apply_cashplus := current_cashplus;
				  	to_apply_card_value := to_apply_card_value -  exceed_cashplus;
				  end if;
				  
				else
				  to_apply_card_value := usage_amt;
				end if;
				
				current_card_value := current_card_value - to_apply_card_value;
				current_cashplus := current_cashplus - to_apply_cashplus;
				
				--dbms_output.put_line ('update txn ' || txn.txn_no); 
				
				update TD_PREPAID_CARD_TXN set apply_card_value=-to_apply_card_value, APPLY_CASHPLUS=-to_apply_cashplus where txn_no=txn.txn_no;
				
			  end if;

			end loop;
	   
			update TD_PRODUCT set td_apply_amt_status='COMPLETED' where product_no=product.product_no;
			dbms_output.put_line ('Product updated: ' || product.product_no); 
	   
		end loop;
   
		dbms_output.put_line ('Done calculate apply amount'); 
	

		for txf_out_rep in (select r.* from TD_PRODUCT_REPLACEMENT r 
			left join td_product o on o.product_no = r.product_no
			left join td_product n on n.card_no=r.new_card_no
			where 
			r.new_status ='T' and
			o.td_apply_amt_status = 'COMPLETED'
			and n.td_apply_amt_status is null
		)
		loop
	  
			select p.* into txf_in_prod from td_product p where p.card_no = txf_out_rep.new_card_no;
			
			select p.* into txf_out_prod from td_product p where p.product_no = txf_out_rep.product_no;
		  
			select -apply_card_value, -apply_cashplus into transfer_card_value,  transfer_cashplus from td_prepaid_card_txn txn where pmtb_product=txf_out_prod.product_no and  txn.txn_type='EX_TR_OUT';

			update td_prepaid_card_txn t set apply_card_value= transfer_card_value, apply_cashplus = transfer_cashplus where t.pmtb_product=txf_in_prod.product_no and t.txn_type='EX_TR_IN';
			
			update td_product tp set td_apply_amt_status = 'READY' where tp.product_no=txf_in_prod.product_no;
			
			dbms_output.put_line ('Product updated: ' || txf_in_prod.product_no); 
		
		end loop;
		
		dbms_output.put_line ('Done create allocate transfer in value'); 
	
	end loop;
	
	dbms_output.put_line ('Completed'); 
	  
end;


--update the current card value of product
update td_product p set card_value = (select sum(apply_card_value) from td_prepaid_card_txn t where t.pmtb_product= p.product_no);

--update the current cash plus of product
update td_product p set cashplus = (select sum(apply_cashplus) from td_prepaid_card_txn t where t.pmtb_product= p.product_no);

--this should return zero results
select * from td_product
where CASHPLUS+ card_value!= CREDIT_BALANCE;


--copy td product card value, cash plus to pmtb_product
update pmtb_product p set card_value = (select card_value from td_product t where p.product_no = t.product_no), cashplus = (select cashplus from td_product t where p.product_no = t.product_no)
where p.product_no in (select product_no from td_product);


--copy td prepaid card txn into pmtb prepaid card transaction
begin
  dbms_output.enable(1000000);

  for txn in (select * from td_prepaid_card_txn order by txn_date)
  loop
    INSERT INTO PMTB_PREPAID_CARD_TXN (TXN_NO,AMOUNT,TXN_TYPE,PMTB_PRODUCT,REMARKS,TXN_DATE,VERSION,PMTB_PREPAID_REQ,ACQUIRE_TXN_NO,GLABLE_FLAG,FMTB_TRANSACTION_CODE,APPLY_CARD_VALUE,APPLY_CASHPLUS,GST)  
    values(pmsq_prepaid_card_txn.nextval, txn.AMOUNT, txn.TXN_TYPE, txn.PMTB_PRODUCT, txn.REMARKS, txn.TXN_DATE, txn.VERSION, txn.PMTB_PREPAID_REQ, txn.ACQUIRE_TXN_NO, txn.GLABLE_FLAG, txn.FMTB_TRANSACTION_CODE, txn.APPLY_CARD_VALUE, txn.APPLY_CASHPLUS, txn.GST);
  end loop;
   dbms_output.put_line ('Completed'); 
end;






