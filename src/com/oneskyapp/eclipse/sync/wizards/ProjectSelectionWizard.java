package com.oneskyapp.eclipse.sync.wizards;

import org.eclipse.jface.wizard.Wizard;

public class ProjectSelectionWizard extends Wizard {
	
	protected ProjectGroupSelectionWizardPage projectGroupSelectionPage;
	protected ProjectSelectionWizardPage projectSelectionWizardPage;

	public ProjectSelectionWizard(){
		super();
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
		
		projectGroupSelectionPage = new ProjectGroupSelectionWizardPage();
		projectSelectionWizardPage = new ProjectSelectionWizardPage();
	}
	
	@Override
	public void addPages() {
		addPage(projectGroupSelectionPage);
		addPage(projectSelectionWizardPage);
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
