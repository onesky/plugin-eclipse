package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;
import com.oneskyapp.eclipse.sync.api.OneSkyService;

public class FileUploadResponse{
	private Meta meta;
	
	@SerializedName("data")
	private FileUploadDetail detail;

	public Meta getMeta() {
		return meta;
	}

	public FileUploadDetail getDetail() {
		return detail;
	}
	
	
}