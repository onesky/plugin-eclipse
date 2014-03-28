package com.oneskyapp.eclipse.sync.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class ProjectSelectionWizardPage extends WizardPage {

	public ProjectSelectionWizardPage() {
		super("Project Selection");
	}

	@Override
	public void createControl(Composite parent) {
		setControl(parent);
		setPageComplete(true);
	}

}
