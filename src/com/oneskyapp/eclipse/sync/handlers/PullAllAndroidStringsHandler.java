package com.oneskyapp.eclipse.sync.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.OneSkyServiceBuilder;
import com.oneskyapp.eclipse.sync.api.model.ProjectLanguage;
import com.oneskyapp.eclipse.sync.jobs.AndroidLanguageFileDownloadJob;
import com.oneskyapp.eclipse.sync.utils.ProjectPreferenceHelper;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PullAllAndroidStringsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PullAllAndroidStringsHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		IStructuredSelection selection = (IStructuredSelection) window
				.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		final IProject project = (IProject) ((IAdaptable) firstElement)
				.getAdapter(IProject.class);

		final ProjectPreferenceHelper pref = new ProjectPreferenceHelper(
				project);

		if(pref.checkIfProjectPreferenceSet(project, window.getShell())){
		
			final OneSkyService service = new OneSkyServiceBuilder(
					pref.getAPIPublicKey(), pref.getAPISecretKey()).build();
	
			final String projectId = pref.getProjectId();
	
			final List<ProjectLanguage> langs = service.getProjectLanguageList(
					projectId).getLanguages();
	
			Job job = new AndroidLanguageFileDownloadJob(
					langs.toArray(new ProjectLanguage[0]), project, service,
					projectId);
			job.setUser(true);
			job.schedule();
		
		}
		return null;
	}
}
