package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class ProjectDetailResponse {
	private Meta meta;
	
	@SerializedName("data")
	private ProjectDetail detail;
	
	public Meta getMeta() {
		return meta;
	}

	public ProjectDetail getDetail() {
		return detail;
	}
}