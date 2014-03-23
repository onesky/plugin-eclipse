package com.oneskyapp.eclipse.sync.api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
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
	
	@GET("/projects/{project_id}/translations")
	public Response exportTranslation(@Path("project_id") String projectId, 
			@Query("locale") String locale,
			@Query("source_file_name") String sourceFileName,
			@Query("export_file_name") String exportFileName);
}
