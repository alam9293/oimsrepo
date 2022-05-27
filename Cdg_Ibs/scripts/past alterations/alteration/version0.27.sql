alter sequence PMTB_PRODUCT_CREDIT_LIMIT_SQ1 MAXVALUE 9999999999999999999;
CREATE TABLE ITTB_TRIPS_TXN_ERROR(
	TRIP_INTF_PK decimal(22) NOT NULL,
	JOB_NO varchar2(10),
	VEHICLE_NO varchar2(10),
	DRIVER_IC varchar2(10),
	ENTITY varchar2(10),
	BOOKED_CHANNEL varchar2(10),
	BOOKED_DATE_TIME timestamp,
	BOOKED_VEHICLE_GROUP varchar2(40),
	PAX_NAME varchar2(40),
	BOOKING_REFERENCE varchar2(40),
	TRIP_START timestamp,
	TRIP_END timestamp,
	PICKUP varchar2(80),
	DESTINATION varchar2(80),
	JOB_TYPE varchar2(10),
	JOB_STATUS varchar2(10),
	PAX_PRIVILEGE varchar2(20),
	PRODUCT_ID varchar2(10),
	PRODUCT varchar2(40),
	PAYMENT_MODE varchar2(10),
	ACCOUNT_LV1 varchar2(10),
	ACCOUNT_LV2 varchar2(10),
	CARD_NO varchar2(20),
	APPROVAL_CODE varchar2(20),
	GST_INCLUSIVE varchar2(1),
	TXN_AMOUNT NUMBER(6,2),
	BOOKING_FEE NUMBER(6,2),
	ADMIN_AMOUNT NUMBER(6,2),
	GST_AMOUNT NUMBER(6,2),
	TOTAL_AMOUNT NUMBER(6,2),
	DRIVER_LEVY NUMBER(6,2),
	MERIT_POINT_FEE NUMBER(6,2),
	SURCHARGE_DESCRIPTION varchar2(80),
	CHARGED_TO varchar2(40),
	COMPLIMENTARY varchar2(1),
	STATUS varchar2(1),
	FMS_STATUS varchar2(1),
	FMS_DATE timestamp,
	CREATE_BY varchar2(10),
	CREATE_DATE timestamp,
	LAST_UPDATE_BY varchar2(10),
	LAST_UPDATE_DATE timestamp,
	SERVICE_TYPE varchar2(10),
	ACCOUNT_LV3 varchar2(10),
	ACCOUNT_LV1_NAME varchar2(80),
	ACCOUNT_LV2_NAME varchar2(80),
	ACCOUNT_LV3_NAME varchar2(80),
	BOOKED_BY varchar2(15),
	ERROR_CODE varchar2(10),
	FLOWTHRU_ACTION varchar2(10),
	FLOWTHRU_BY varchar2(10),
	FLOWTHRU_DT timestamp,
	FLOWTHRU_COMMENTS varchar2(80),
	TID varchar2(8),
	CANCEL_REASON_CODE decimal(22),
	CANCEL_REASON_DESC varchar2(60),
	SURCHARGE_BREAKDOWN varchar2(80),
	TRIP_INTF_REF varchar2(20),
	REFUND NUMBER(6,2),
	CARD_TYPE varchar2(1),
	SETL_DATE timestamp,
	BANK_TID varchar2(8),
	BANK_BATCH_CLOSE_NO varchar2(6),
	COMPLETED_DT timestamp,
	SALES_DRAFT_NO varchar2(15),
	RECON_DATE timestamp,
	SCENARIO varchar2(20),
	IBS_STATUS varchar2(1),
	IBS_ERROR_CODE varchar2(10),
	IBS_DATE timestamp,
	ERROR_MSG VARCHAR2(255)
);