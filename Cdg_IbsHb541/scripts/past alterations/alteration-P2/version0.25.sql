/* *******************
 * RE-ORDER MENU ITEMS
 * *******************/
update SATB_RESOURCE set SEQUENCE = 1 where uri = 'Common';
update SATB_RESOURCE set SEQUENCE = 2 where uri = 'Common Public';
update SATB_RESOURCE set SEQUENCE = 3 where uri = 'Access Control';
update SATB_RESOURCE set SEQUENCE = 4 where uri = 'Administration';
update SATB_RESOURCE set SEQUENCE = 5 where uri = 'Acct Mgmt';
update SATB_RESOURCE set SEQUENCE = 6 where uri = 'Product Mgmt';
update SATB_RESOURCE set SEQUENCE = 7 where uri = 'Txn';
update SATB_RESOURCE set SEQUENCE = 8 where uri = 'Non Billable';
update SATB_RESOURCE set SEQUENCE = 9 where uri = 'Rewards';
update SATB_RESOURCE set SEQUENCE = 10 where uri = 'Billing';
update SATB_RESOURCE set SEQUENCE = 11 where uri = 'Bill Gen';
update SATB_RESOURCE set SEQUENCE = 12 where uri = 'Report';

update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where uri = 'Administration') where uri = '/rewards/list_plan.zul'
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where uri = 'Administration') where uri = '/rewards/list_category.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where uri = 'Administration') where uri = '/inventory/list_item_type.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where uri = 'Administration') where uri = '/master/refresh.zul';

--This script will generate a list of update statements to order the items under a specific menu in alphabetical order
select 'Update SATB_RESOURCE set sequence = ' || rownum || ' where rsrc_id = ' || rsrc_id || ';'
from(
	select RSRC_ID
	from SATB_RESOURCE where PAR_RSRC_ID in (select RSRC_ID from SATB_RESOURCE where uri = 'Administration') order by DISPLAY_NAME
)