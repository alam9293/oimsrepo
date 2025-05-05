--delete transaction code Prepaid Admin Fee GST
delete from fmtb_transaction_code where txn_code='AFPPG';


--Prepaid Issuance Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_IF','PREPAID ISSUANCE FEE',
    'PIF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Top Up Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_TF','PREPAID TOPUP FEE',
    'PTF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
--Prepaid Transfer Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_XF','PREPAID TRANSFER FEE',
    'PXF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Replacement Fee  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_RF','PREPAID REPLACEMENT FEE',
    'PRF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );  
  
--Prepaid Deferred Income  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_DR','DEFERRED INCOME',
    'PDR', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      

--Prepaid Prepayment CashPlus
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PC','PREPAYMENT CASHPLUS',
    'PPC', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );    
  
  
  
--Prepaid Promotion Expense  
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PE','PREPAID PROMOTION EXPENSE',
    'PPE', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );    
  
--Prepaid Payment to Driver
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PM','PAYMENT TO DRIVER',
    'PPM', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      

  
--Prepaid Payable to Driver
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_PB','PAYABLE TO DRIVER',
    'PPB', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );      
  
  
--Prepaid Discount
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_DI','PREPAID DISCOUNT',
    'PDI', '1234', 'XXXXXXXX', 'N', '1234',
    '1234', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );

--Prepaid Forfeited Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', null, 'P_AF','PREPAID ADMIN FEE FORFEIT',
    'PAF', '1234', 'XXXXXXXX', 'N', '1234',
    '1234', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  

--Priority Card
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'PR', 'TFPR','PRIORITY CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );
  
--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'PR', 'AFPR','PRIORITY CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
  
--Contactless 3Gen Card 
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C4', 'TFC4','CONTACTLESS 3GEN  CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );

--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C4', 'AFC4','CONTACTLESS 3GEN CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );
  
  
--Contactless Priority Card
--Taxi Fare
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C3', 'TFC3','CONTACTLESS PRIORITY CARD - TAXI FARE',
    'FA', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='NGST' and master_type='TT'),  0, 'N'
  );

--Admin Fee
INSERT
INTO FMTB_TRANSACTION_CODE
  (
    TRANSACTION_CODE_NO, ENTITY_NO, PRODUCT_TYPE_ID, TXN_CODE, DESCRIPTION,
    TXN_TYPE, GL_CODE, COST_CENTRE, DISCOUNTABLE, DISCOUNT_GL_CODE, 
    DISCOUNT_COST_CENTRE, EFFECTIVE_DATE, TAX_TYPE, VERSION, IS_MANUAL
  )
  VALUES
  (
    FMTB_TRANSACTION_CODE_SQ1.nextval, '1', 'C3', 'AFC3','CONTACTLESS PRIORITY CARD - ADMIN FEE',
    'AF', '1234', 'XXXXXXXX', 'N', '',
    '', DATE '2009-01-01', (select master_no from mstb_master_table where master_code='GST' and master_type='TT'),  0, 'N'
  );


  