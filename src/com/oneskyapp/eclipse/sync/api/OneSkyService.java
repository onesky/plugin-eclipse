package com.oneskyapp.eclipse.sync.api;

import java.util.List;

import retrofit.http.GET;

import com.google.gson.annotations.SerializedName;

public interface OneSkyService {

	@GET("/project-groups")
	public ProjectGroupList getProjectGroupList();

	// @Query("page") int page, @Query("per_page") int pageSize

	static class ProjectGroupList {
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

	static class ProjectGroup {
		private long id;
		private String name;

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	static class Meta {
		private String status;

		@SerializedName("record_count")
		private int recordCount;

		public String getStatus() {
			return status;
		}

		public int getRecordCount() {
			return recordCount;
		}

	}
}
