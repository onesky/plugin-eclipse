package com.oneskyapp.eclipse.sync.api;

import java.util.Date;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

import com.google.gson.annotations.SerializedName;

public interface OneSkyService {

	@GET("/project-groups")
	public ProjectGroupList getProjectGroupList();
	
	@GET("/project-groups/{project_group_id}/projects")
	public ProjectList getProjectList(@Path("project_group_id") String projectGroupId);
	
	@Multipart
	@POST("/projects/{project_id}/files")
	public FileUploadResponse uploadFile(@Path("project_id") String projectId, @Part("file") TypedFile file, 
			@Part("file_format") String fileFormat, @Part("locale") String local, 
			@Part("is_keeping_all_strings") Boolean keepAllStrings);
	
	static class FileUploadResponse{
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
	
	static class FileUploadDetail{
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
	
	static class LanguageDetail{
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
	
	static class ImportDetail{
		private Long id;
		
//		FIXME unable to parse		
//		@SerializedName("created_at")
//		private Date createdAt;
		
		@SerializedName("created_at_timestamp")
		private Long createdAtTimestamp;

		public Long getId() {
			return id;
		}

//		public Date getCreatedAt() {
//			return createdAt;
//		}

		public Long getCreatedAtTimestamp() {
			return createdAtTimestamp;
		}
		
		
	}
	
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
