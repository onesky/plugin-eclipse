package com.oneskyapp.eclipse.sync.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

import com.google.gson.annotations.SerializedName;

public interface OneSkyService {

	@GET("/project-groups")
	public ProjectGroupList getProjectGroupList();
	
	@GET("/project-groups/{project_group_id}/projects")
	public ProjectList getProjectList(@Path("project_group_id") String projectGroupId);
	
	static class ProjectList{
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
	
	static class Project{
		private long id;
		private String name;

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

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
