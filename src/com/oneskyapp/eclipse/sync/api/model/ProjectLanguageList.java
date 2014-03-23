package com.oneskyapp.eclipse.sync.api.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProjectLanguageList {
	private Meta meta;
	
	@SerializedName("data")
	private List<ProjectLanguage> languages;

	public Meta getMeta() {
		return meta;
	}

	public List<ProjectLanguage> getLanguages() {
		return languages;
	}
	
}
