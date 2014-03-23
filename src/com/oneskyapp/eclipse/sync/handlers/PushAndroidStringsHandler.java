package com.oneskyapp.eclipse.sync.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

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
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"OneSky Sync",
//				"Push");
		
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
        Object firstElement = selection.getFirstElement();
        IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
//        IPath path = project.getFullPath();
//        System.out.println(path);
        
        ProjectPreferenceHelper pref = new ProjectPreferenceHelper(project);
        
        OneSkyService service = new OneSkyServiceBuilder(pref.getAPIPublicKey(), pref.getAPISecretKey()).build();
        
		return null;
	}
}
