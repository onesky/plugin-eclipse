package com.oneskyapp.eclipse.sync.api.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProjectList{
	private Meta meta;
	
	@SerializedName("data")
	private List<Project> projects;

	public Meta getMeta() {
		return meta;
	}

	public List<Project> getProjects() {
		return projects;
	}
}