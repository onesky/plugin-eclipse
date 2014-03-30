package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class Meta {
	private String status;

	@SerializedName("record_count")
	private int recordCount;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	public int getRecordCount() {
		return recordCount;
	}

}