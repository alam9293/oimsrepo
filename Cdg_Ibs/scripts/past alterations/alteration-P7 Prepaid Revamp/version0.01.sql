update satb_resource set sequence=13 where rsrc_name='Report';
update satb_resource set sequence=12 where rsrc_name='Bill Gen';
update satb_resource set sequence=11 where rsrc_name='Billing';
update satb_resource set sequence=10 where rsrc_name like 'Loyalty%Rewards';
update satb_resource set sequence=9 where rsrc_name='Non Billable';
update satb_resource set sequence=8 where rsrc_name='Txn';

insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE),
    0,
    'Prepaid',
    'U',
    'Prepaid',
    7,
    'Prepaid',
    'Y',
    0
  );


insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Prepaid Request',
    'U',
    '/prepaid/prepaid_request_search.zul',
    1,
    'Prepaid Request',
    'Y',
    0
  );

insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Approval of Prepaid Request',
    'U',
    '/prepaid/prepaid_request_approval_search.zul',
    2,
    'Approval of Prepaid Request',
    'Y',
    0
  );
  
insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    (select RSRC_ID from SATB_RESOURCE where rsrc_name='Prepaid'),
    'Manage Prepaid Cards',
    'U',
    '/prepaid/manage_prepaid_cards.zul',
    3,
    'Manage Prepaid Cards',
    'Y',
    0
  );


insert into satb_resource (rsrc_id, par_rsrc_id, rsrc_name, rsrc_type, uri, sequence, display_name, display, version)
VALUES
  (
    (select max(rsrc_id)+1 from satb_resource),
    537,
    'Manage Prepaid Promotion Plan',
    'U',
    '/admin/prepaid_promotion/manage_prepaid_promotion_plan.zul',
    1,
    'Manage Prepaid Promotion Plan',
    'Y',
    0
  );  
  
insert into satb_role_resource (role_id, rsrc_id) 
select a.role_id,  b.rsrc_id from satb_role_resource a, SATB_RESOURCE b
where a.rsrc_id in (select rsrc_id from satb_resource where rsrc_name='Manage Product Types')
and b.rsrc_name in('Manage Prepaid Promotion Plan', 'Prepaid', 'Prepaid Request', 'Approval of Prepaid Request', 'Manage Prepaid Cards');



