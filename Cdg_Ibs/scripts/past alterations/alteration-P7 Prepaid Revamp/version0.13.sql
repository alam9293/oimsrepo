
--to delete Prepaid Product report menu
delete from MSTB_REPORT_FORMAT_MAP where RSRC_ID in (select RSRC_ID from  SATB_RESOURCE where rsrc_name ='Prepaid Product');

delete from  SATB_RESOURCE where rsrc_name ='Prepaid Product';

