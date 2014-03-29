package com.oneskyapp.eclipse.sync.utils;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.oneskyapp.eclipse.sync.Activator;

public class ProjectPreferenceHelper {

	public static final String PREF_API_SECRET_KEY = "api.secret_key";
	public static final String PREF_API_PUBLIC_KEY = "api.public_key";
	public static final String PREF_PROJECT_ID = "project_id";

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

	public void setProjectId(String projectId) {
		prefStore.setValue(PREF_PROJECT_ID, projectId);
	}

	public String getProjectId() {
		return prefStore.getString(PREF_PROJECT_ID);
	}
	
	public boolean isProjectSet() {
		if (StringUtils.isBlank(getAPIPublicKey())
				|| StringUtils.isBlank(getAPISecretKey())
				|| StringUtils.isBlank(getProjectId())) {
			return false;
		}
		return true;
	}
	
	public boolean checkIfProjectPreferenceSet(IProject project, Shell shell) {
		if(!isProjectSet()){
			String handlerName = Activator.getDefault().getBundle().getHeaders()
					.get(org.osgi.framework.Constants.BUNDLE_NAME);
			MessageDialog.openError(shell, handlerName, "Project is not set correctly");//TODO add more info
			String propertyPageId = "com.oneskyapp.eclipse.sync.properties.OneSkyPropertyPage";
			PreferenceDialog dialog = PreferencesUtil.createPropertyDialogOn(
					shell, project, propertyPageId, null, null);
			dialog.open();
			return false;
		}else{
			return true;
		}
	}
}
