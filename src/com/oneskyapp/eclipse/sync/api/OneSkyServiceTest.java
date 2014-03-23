package com.oneskyapp.eclipse.sync.api;

import java.io.File;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import retrofit.mime.TypedFile;

import com.oneskyapp.eclipse.sync.api.model.FileUploadResponse;
import com.oneskyapp.eclipse.sync.api.model.Project;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroup;
import com.oneskyapp.eclipse.sync.api.model.ProjectLanguage;
import com.oneskyapp.eclipse.sync.api.model.ProjectLanguageList;
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
			printObject(pg);
			
			ProjectList projectList = service.getProjectList(String.valueOf(pg.getId()));
			for(Project project: projectList.getProjects()){
				printObject(project);
				ProjectLanguageList languageList = service.getProjectLanguageList(String.valueOf(project.getId()));
				printObject(languageList);
			}
		}
		
		String mimeType = "application/xml";
		File file = new File("strings.xml");
		System.out.println(file.getAbsolutePath());
		TypedFile typedFile = new TypedFile(mimeType, file);
		FileUploadResponse resp = service.uploadFile("8903", typedFile, "ANDROID_XML", null , null);
		printObject(resp);
	}

	public static void printObject(Object obj){
		MultiLineRecursiveToStringStyle style = new MultiLineRecursiveToStringStyle();
		System.out.println(
				ReflectionToStringBuilder.toString(obj, style)
				);
	}
}

