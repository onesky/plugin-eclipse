package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class ProjectLanguage {
	private String code;
	
	@SerializedName("english_name")
	private String englishName;
	
	@SerializedName("local_name")
	private String localName;
	
	private String locale;
	private String region;
	
	@SerializedName("is_base_language")
	private boolean baseLanguage;
	
	@SerializedName("is_ready_to_publish")
	private boolean readyToPublish;

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

	public boolean isBaseLanguage() {
		return baseLanguage;
	}

	public boolean isReadyToPublish() {
		return readyToPublish;
	}
	
}
