package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;
import com.oneskyapp.eclipse.sync.api.OneSkyService;

public class FileUploadDetail{
	private String name;
	private String format;
	
	@SerializedName("language")
	private LanguageDetail languageDetail;
	
	@SerializedName("import")
	private ImportDetail importDetail;

	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}

	public LanguageDetail getLanguageDetail() {
		return languageDetail;
	}

	public ImportDetail getImportDetail() {
		return importDetail;
	}
	
	
}