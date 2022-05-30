SET PAGES 5000
SET LINE  10000
SET TRIMSPOOL ON
set echo on

column dt new_value new_dt 
column ext new_value new_ext
select  sys_context('userenv','instance_name') || '_janIssueLogs_dml_' || to_char(sysdate,'YYYYMMDDHH24MISS') dt,'.txt' ext from dual; 
SPOOL  &new_dt&new_ext
set define off
alter session set current_schema=ibssys;



--Update ittb_trips_txn set trip type & rcvw_intf_trips_for_ibs for download trips 05/04/2017 -START
--	 if payment mode = CABC, EVCH, INVO, it will considered as a billable txn
-- "CABC" , "EVCH" , INVO"
-- Basically just set product_id = the interface mapping value of the trip type.

update ittb_trips_txn set product_id = 
(select interface_mapping_value from mstb_master_table where master_type = 'VTT' 
-- add your own condition to choose which trip type to patch to 
-- and interface_mapping_value = '???' )
-- where payment_mode = 'cabc/evch/invo?' and .. ;      
-- add your own where conditions for which download trip or all trips and payment mode
-- normally payment mode should be the above 3

update RCVW_INTF_TRIPS_FOR_IBS set product_id = 
(select interface_mapping_value from mstb_master_table where master_type = 'VTT' 
-- add your own condition to choose which trip type to patch to 
-- and interface_mapping_value = '???' )
-- where payment_mode = 'cabc/evch/invo?' and .. ;      
-- add your own where conditions for which download trip or all trips and payment mode
-- normally payment mode should be the above 3


--update tmtb_acquire_txn

update TMTB_ACQUIRE_TXN set trip_type =
(select master_no from MSTB_MASTER_TABLE
where master_type = 'VTT' and interface_mapping_value = '???' )
-- where job_no =?  or acquire_txn_no = ?
--set your own job_no

--update tmtb_acquire_txn

--Update ittb_trips_txn & rcvw_intf_trips_for_ibs set trip type for download trips 05/04/2017 -END


spool off