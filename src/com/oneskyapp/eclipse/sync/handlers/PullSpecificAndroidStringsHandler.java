package com.oneskyapp.eclipse.sync.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

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
public class PullSpecificAndroidStringsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PullSpecificAndroidStringsHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		IStructuredSelection selection = (IStructuredSelection) window
				.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		final IProject project = (IProject) ((IAdaptable) firstElement)
				.getAdapter(IProject.class);

		final ProjectPreferenceHelper pref = new ProjectPreferenceHelper(
				project);

		final OneSkyService service = new OneSkyServiceBuilder(
				pref.getAPIPublicKey(), pref.getAPISecretKey()).build();

		final String projectId = pref.getProjectId();
		
		Job job = new Job("Retrieve available languages"){

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Retrieving available languages",
						100);
				final List<ProjectLanguage> langs = service
						.getProjectLanguageList(projectId).getLanguages();
				if(monitor.isCanceled()){
					return Status.CANCEL_STATUS;
				}
				monitor.done();
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	ProjectLanguage[] selectedLangs = getSelectedLanguages(window, langs);
				    	if(selectedLangs != null){
							Job job = new AndroidLanguageFileDownloadJob(selectedLangs, project,
							service, projectId);
							job.setUser(true);
							job.schedule();
				    	}
				    }
				});
				return Status.OK_STATUS;
			}
			
		};
		job.setUser(true);
		job.schedule();

		return null;
	}

	private ProjectLanguage[] getSelectedLanguages(
			final IWorkbenchWindow window,
			List<ProjectLanguage> langs) {
		List<ProjectLanguage> filteredLangs = new ArrayList<ProjectLanguage>();
		for (ProjectLanguage lang : langs) {
			if (//lang.isReadyToPublish() && the api not finished 
					!lang.isBaseLanguage()) {
				filteredLangs.add(lang);
			}
		}

		ListSelectionDialog dialog = new ListSelectionDialog(window.getShell(),
				filteredLangs, ArrayContentProvider.getInstance(),
				new LabelProvider() {

					@Override
					public String getText(Object element) {
						ProjectLanguage lang = (ProjectLanguage) element;
						return String.format("%s - %s [%s]",
								lang.getEnglishName(), lang.getLocalName(),
								lang.getCode());
					}

				}, "Select Languages");
		dialog.setHelpAvailable(false);
		if (dialog.open() == Window.OK) {
			Object[] results = dialog.getResult();

			ProjectLanguage[] selectedLangs = Arrays.copyOf(results,
					results.length, ProjectLanguage[].class);

			return selectedLangs;
		}else{
			return null;
		}
	}
}
