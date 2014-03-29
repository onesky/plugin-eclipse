package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class ProjectDetail {
	private long id;
	private String name;
	private String description;
	
	@SerializedName("project_type")
	private ProjectType type;
	
	public ProjectType getType() {
		return type;
	}

	@SerializedName("string_count")
	private Long stringCount;
	
	@SerializedName("word_count")
	private Long StringWordCount;

	public String getDescription() {
		return description;
	}

	public Long getStringCount() {
		return stringCount;
	}

	public Long getStringWordCount() {
		return StringWordCount;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}