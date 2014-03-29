package com.oneskyapp.eclipse.sync.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.oneskyapp.eclipse.sync.utils.ProjectPreferenceHelper;

public class ProjectSelectionWizard extends Wizard {
	
	private ProjectGroupSelectionWizardPage projectGroupSelectionPage;
	private ProjectSelectionWizardPage projectSelectionWizardPage;
	private ProjectSelectionWizardModel model;
	
	public ProjectSelectionWizard(ProjectSelectionWizardModel model){
		super();
		this.model = model;
		
		setWindowTitle("OneSky Sync Configuration");
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
		
		projectGroupSelectionPage = new ProjectGroupSelectionWizardPage(model);
		projectSelectionWizardPage = new ProjectSelectionWizardPage(model);
	}
	
	@Override
	public void addPages() {
		addPage(projectGroupSelectionPage);
		addPage(projectSelectionWizardPage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public boolean canFinish() {
		return model.getProject() != null;
	}
	
}
