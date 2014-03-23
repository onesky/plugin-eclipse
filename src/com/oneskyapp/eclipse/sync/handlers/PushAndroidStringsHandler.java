package com.oneskyapp.eclipse.sync.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
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
        
        ProjectPreferenceHelper pref = new ProjectPreferenceHelper(project);
        
        OneSkyService service = new OneSkyServiceBuilder(pref.getAPIPublicKey(), pref.getAPISecretKey()).build();
        
		String stringFilePath = "/res/values/strings.xml";
		File stringFile = project.getFile(stringFilePath).getLocation().toFile();
		
		System.out.println(stringFile);
		
		if(pref.isProjectSet()){
			if(stringFile.exists()){
				TypedFile typedFile = new TypedFile("application/xml", stringFile);
				service.uploadFile(pref.getProjectId(), typedFile, "ANDROID_XML", null, null);
			}else{
				MessageDialog.openError(window.getShell(), handlerName, 
						stringFilePath + " does not exists.");
			}
		}else{
			MessageDialog.openError(window.getShell(), handlerName, "Project is not set");
			String propertyPageId = "com.oneskyapp.eclipse.sync.properties.OneSkyPropertyPage";
			PreferenceDialog dialog = PreferencesUtil.createPropertyDialogOn(
					window.getShell(), project, propertyPageId, null, null);
			dialog.open();
		}
		
		return null;
	}
}
