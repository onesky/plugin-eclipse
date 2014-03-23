package com.oneskyapp.eclipse.sync.api;

import java.io.File;

import retrofit.mime.TypedFile;

import com.oneskyapp.eclipse.sync.api.model.FileUploadResponse;
import com.oneskyapp.eclipse.sync.api.model.Project;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroup;
import com.oneskyapp.eclipse.sync.api.model.ProjectList;

public class OneSkyServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String publicKey;
		final String sercetKey;

		publicKey = args[0];
		sercetKey = args[1];
		
		OneSkyServiceBuilder builder = new OneSkyServiceBuilder(publicKey,sercetKey);
		builder.setDebugMode(true);
		OneSkyService service = builder.build();
		
		for(ProjectGroup pg:service.getProjectGroupList().getProjectGroups()){
			System.out.println(String.format("%s %s", String.valueOf(pg.getId()), pg.getName()));
			
			ProjectList projectList = service.getProjectList(String.valueOf(pg.getId()));
			for(Project project: projectList.getProjects()){
				System.out.println(String.format("%s %s", String.valueOf(project.getId()), project.getName()));
			}
		}
		
//		8903
		String mimeType = "application/xml";
		File file = new File("strings.xml");
		System.out.println(file.getAbsolutePath());
		TypedFile typedFile = new TypedFile(mimeType, file);
		FileUploadResponse resp = service.uploadFile("8903", typedFile, "ANDROID_XML", null , null);
		System.out.println(resp.getDetail().getLanguageDetail().getEnglishName());
	}

}

