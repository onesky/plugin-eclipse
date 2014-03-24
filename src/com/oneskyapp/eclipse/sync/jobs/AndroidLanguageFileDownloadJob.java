package com.oneskyapp.eclipse.sync.jobs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import retrofit.client.Response;

import com.oneskyapp.eclipse.sync.Activator;
import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.model.ProjectLanguage;

public final class AndroidLanguageFileDownloadJob extends Job {
	private final ProjectLanguage[] selectLangs;
	private final IProject project;
	private final OneSkyService service;
	private final String projectId;

	public AndroidLanguageFileDownloadJob(ProjectLanguage[] selectLangs,
			IProject project, OneSkyService service, String projectId) {
		super("Sync Languages");
		this.selectLangs = selectLangs;
		this.project = project;
		this.service = service;
		this.projectId = projectId;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Syncing Languages", selectLangs.length);
		for (ProjectLanguage lang : selectLangs) {
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			} else {
				try {
					Response resp = service.exportTranslation(projectId,
							lang.getCode(), "strings.xml", "strings.xml");

					String androidCode;
					if (lang.getCode().contains("-")) {
						androidCode = lang.getCode().replaceFirst("-", "-r");
					} else {
						androidCode = lang.getCode();
					}
					IFile file = project.getFile("/res/values-" + androidCode
							+ "/strings.xml");
					prepareFolder((IFolder) file.getParent().getAdapter(
							IFolder.class));
					file.create(resp.getBody().in(), true, monitor);
					// exportFile.mkdir();
					// FileUtils.copyInputStreamToFile(
					// resp.getBody().in(), exportFile);
					monitor.worked(1);
				} catch (Exception e) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							"Error while downloading file", e);
				}
			}
		}
		monitor.done();
		return Status.OK_STATUS;
	}

	public void prepareFolder(IFolder folder) throws CoreException {
		if (!folder.exists()) {
			prepareFolder((IFolder) folder.getParent());
			folder.create(false, false, null);
		}
	}
}