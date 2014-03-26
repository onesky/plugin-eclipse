package com.oneskyapp.eclipse.sync.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

import retrofit.mime.TypedFile;

import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.OneSkyServiceBuilder;
import com.oneskyapp.eclipse.sync.utils.ProjectPreferenceHelper;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PushAndroidStringsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PushAndroidStringsHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String handlerName = "Send to OneSky for Translation";
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
        Object firstElement = selection.getFirstElement();
        IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
//        IPath path = project.getFullPath();
//        System.out.println(path);
        
        final ProjectPreferenceHelper pref = new ProjectPreferenceHelper(project);
        
        final OneSkyService service = new OneSkyServiceBuilder(pref.getAPIPublicKey(), pref.getAPISecretKey()).build();
        
		final String stringFilePath = "/res/values/strings.xml";
		final File stringFile = project.getFile(stringFilePath).getLocation().toFile();
		
		System.out.println(stringFile);
		
		if(pref.checkIfProjectPreferenceSet(project, window.getShell())){
			if(stringFile.exists()){
				
				Job job = new Job(handlerName) {
				     @Override
				     protected IStatus run(IProgressMonitor monitor) {
				         monitor.beginTask("Sending " + stringFilePath + " to OneSky for Translation", 100);
				         
				         TypedFile typedFile = new TypedFile("application/xml", stringFile);
						 service.uploadFile(pref.getProjectId(), typedFile, "ANDROID_XML", null, null);
							
//						 new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Job finished");
						 
				         monitor.done();
				         return Status.OK_STATUS;
				     }

				 };
				 job.setUser(true);
				 job.schedule();
			}else{
				MessageDialog.openError(window.getShell(), handlerName, 
						stringFilePath + " does not exists.");
			}
		}
		
		return null;
	}
}
