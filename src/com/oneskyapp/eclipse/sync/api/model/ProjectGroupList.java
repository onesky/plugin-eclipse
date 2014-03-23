package com.oneskyapp.eclipse.sync.api.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProjectGroupList {
	private Meta meta;

	@SerializedName("data")
	private List<ProjectGroup> projectGroups;

	public List<ProjectGroup> getProjectGroups() {
		return projectGroups;
	}

	public Meta getMeta() {
		return meta;
	}

}