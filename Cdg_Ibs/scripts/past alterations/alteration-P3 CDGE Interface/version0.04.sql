--UPDATE of status column length
alter table IMTB_ITEM modify(STATUS varchar2(2));
alter table IMTB_ITEM_REQ modify(CURRENT_STATUS varchar2(2));
alter table IMTB_ITEM_REQ modify(ACTION varchar2(2));
alter table IMTB_ITEM_REQ_FLOW modify(FROM_STATUS varchar2(2));
alter table IMTB_ITEM_REQ_FLOW modify(TO_STATUS varchar2(2));
