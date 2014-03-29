package com.oneskyapp.eclipse.sync.wizards;

import com.oneskyapp.eclipse.sync.api.model.Project;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroup;

public class ProjectSelectionWizardModel {
	private String publicKey;
	private String secretKey;
	private ProjectGroup projectGroup;
	private Project project;

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public Project getProject() {
		return project;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
