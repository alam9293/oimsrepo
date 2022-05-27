alter table IMTB_ITEM_REQ add BATCH_NO  VARCHAR2(10);

alter table IMTB_ITEM_REQ add BATCH_DATE DATE;

INSERT
INTO SATB_RESOURCE
  (
    RSRC_ID,
    PAR_RSRC_ID,
    RSRC_NAME,
    RSRC_TYPE,
    URI,
    SEQUENCE,
    DISPLAY_NAME,
    DISPLAY,
    VERSION
  )
  VALUES
  (
    905,
    458,
    'Cash Voucher Settlement Report',
    'U',
    '/report/cash_voucher_settlement.zul?rsrcId=905',
    7,
    'Cash Voucher Settlement Report',
    'Y',
    0
  );


insert into SATB_ROLE_RESOURCE select role_id, 905 from SATB_ROLE_RESOURCE where rsrc_id in (select rsrc_id from SATB_RESOURCE where rsrc_name= 'Unredeemed Voucher Report');


insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 905, 'PDF');

insert into MSTB_REPORT_FORMAT_MAP values (MSTB_REPORT_FORMAT_MAP_SQ1.NEXTVAL, 905, 'XLS');

--update where batch date = redeem time for those missing batch date item
UPDATE imtb_item 
SET batch_date   = TO_CHAR(redeem_time, 'dd-MON-yy') 
WHERE serial_no IN 
  ( 
    SELECT item.serial_no 
    FROM imtb_item item 
    INNER JOIN imtb_item_req req 
    ON req.item_no         = item.item_no 
    WHERE req.action      IN ('PV') 
    AND req.current_status = 'A' 
    AND item.batch_date   IS NULL 
  ); 


UPDATE
  (
    SELECT req.batch_no AS req_batch_no,
      req.batch_date  AS req_batch_date,
      req.redeem_point AS req_redeem_point,
      req.redeem_time AS req_redeem_time,
      item.batch_no AS item_batch_no,
      item.batch_date AS item_batch_date,
      item.redeem_point AS item_redeem_point,
      item.redeem_time as item_redeem_time
    FROM imtb_item_req req
    INNER JOIN imtb_item item
    ON req.item_no         = item.item_no
    WHERE req.action      IN ('PV')
    AND req.current_status = 'A'
  )
SET req_batch_no = item_batch_no,
  req_batch_date = item_batch_date ,
  req_redeem_point = item_redeem_point,
  req_redeem_time = item_redeem_time;



