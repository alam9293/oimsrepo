package com.cdgtaxi.ibs.common.model;

import java.sql.Timestamp;

public interface Creatable {
	public void setCreatedBy(String createdBy);
	public void setCreatedDt(Timestamp timestamp);
}
