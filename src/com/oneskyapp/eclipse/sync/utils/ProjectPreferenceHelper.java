package com.oneskyapp.eclipse.sync.utils;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.oneskyapp.eclipse.sync.Activator;

public class ProjectPreferenceHelper {

	public static final String PREF_PROJECT_GROUP_ID = "project_group_id";
	public static final String PREF_API_SECRET_KEY = "api.secret_key";
	public static final String PREF_API_PUBLIC_KEY = "api.public_key";
	public static final String PREF_PROJECT_GROUP_NAME = "project_group_name";
	public static final String PREF_PROJECT_ID = "project_id";
	public static final String PREF_PROJECT_NAME = "project_name";

	private IProject project;
	private ScopedPreferenceStore prefStore;

	public ProjectPreferenceHelper(IProject project) {
		this.project = project;
		this.prefStore = new ScopedPreferenceStore(new ProjectScope(project),
				Activator.PLUGIN_ID);
	}

	public IProject getProject() {
		return project;
	}

	public ScopedPreferenceStore getPrefStore() {
		return prefStore;
	}

	public void setAPIPublicKey(String publicKey) {
		prefStore.setValue(PREF_API_PUBLIC_KEY, publicKey);
	}

	public String getAPIPublicKey() {
		return prefStore.getString(PREF_API_PUBLIC_KEY);
	}

	public void setAPISecretKey(String secretKey) {
		prefStore.setValue(PREF_API_SECRET_KEY, secretKey);
	}

	public String getAPISecretKey() {
		return prefStore.getString(PREF_API_SECRET_KEY);
	}

	public void setProjectGroupId(String projectGroupId) {
		prefStore.setValue(PREF_PROJECT_GROUP_ID, projectGroupId);
	}

	public String getProjectGroupId() {
		return prefStore.getString(PREF_PROJECT_GROUP_ID);
	}

	public void setProjectGroupName(String projectGroupName) {
		prefStore.setValue(PREF_PROJECT_GROUP_NAME, projectGroupName);
	}

	public String getProjectGroupName() {
		return prefStore.getString(PREF_PROJECT_GROUP_NAME);
	}
	
	public void setProjectId(String projectId) {
		prefStore.setValue(PREF_PROJECT_ID, projectId);
	}

	public String getProjectId() {
		return prefStore.getString(PREF_PROJECT_ID);
	}
	
	public void setProjectName(String projectName) {
		prefStore.setValue(PREF_PROJECT_NAME, projectName);
	}

	public String getProjectName() {
		return prefStore.getString(PREF_PROJECT_NAME);
	}

	public boolean isProjectSet() {
		if (StringUtils.isBlank(getAPIPublicKey())
				|| StringUtils.isBlank(getAPISecretKey())
				|| StringUtils.isBlank(getProjectGroupId())
				|| StringUtils.isBlank(getProjectId())) {
			return false;
		}
		return true;
	}
}
