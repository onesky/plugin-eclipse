package com.oneskyapp.eclipse.sync.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class ProjectGroupSelectionWizardPage extends WizardPage {

	public ProjectGroupSelectionWizardPage() {
		super("Project Group Selection");
	}

	@Override
	public void createControl(Composite parent) {
		setControl(parent);
		setPageComplete(true);
	}

}
