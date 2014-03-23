package com.oneskyapp.eclipse.sync.api;

import java.util.Date;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

import com.oneskyapp.eclipse.sync.api.model.FileUploadResponse;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroupList;
import com.oneskyapp.eclipse.sync.api.model.ProjectLanguageList;
import com.oneskyapp.eclipse.sync.api.model.ProjectList;

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
	
	@GET("/projects/{project_id}/languages")
	public ProjectLanguageList getProjectLanguageList(@Path("project_id") String projectId);
}
