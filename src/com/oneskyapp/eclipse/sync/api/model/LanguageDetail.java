package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class LanguageDetail{
	private String code;
	
	@SerializedName("english_name")
	private String englishName;
	
	@SerializedName("local_name")
	private String localName;
	
	private String locale;
	private String region;
	public String getCode() {
		return code;
	}
	public String getEnglishName() {
		return englishName;
	}
	public String getLocalName() {
		return localName;
	}
	public String getLocale() {
		return locale;
	}
	public String getRegion() {
		return region;
	}
	
	
}