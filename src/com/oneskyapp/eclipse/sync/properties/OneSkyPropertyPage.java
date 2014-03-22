package com.oneskyapp.eclipse.sync.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.swt.widgets.Button;

import com.oneskyapp.eclipse.sync.Activator;

public class OneSkyPropertyPage extends PropertyPage {
	private static final String PREF_API_SECRET_KEY = "api.secret_key";
	private static final String PREF_API_PUBLIC_KEY = "api.public_key";
	private Text txtPublicKey;
	private Text txtSecretKey;
	private IProject project;

	public OneSkyPropertyPage() {
		super();
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		
		setDescription("Get your API keys from your OneSky Site Setting Page and select project");
	}


	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		this.noDefaultAndApplyButton();
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);
		
		Label lblPublicKey = new Label(composite, SWT.NONE);
		lblPublicKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPublicKey.setText("Public Key");
		
		txtPublicKey = new Text(composite, SWT.BORDER);
		txtPublicKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
				Label lblSecretKey = new Label(composite, SWT.NONE);
				lblSecretKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblSecretKey.setText("Secret Key");
		
		txtSecretKey = new Text(composite, SWT.BORDER);
		txtSecretKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblProjectGroup = new Label(composite, SWT.NONE);
		lblProjectGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProjectGroup.setText("Project Group");
		
		Label lblProjectGroupDetail = new Label(composite, SWT.NONE);
		lblProjectGroupDetail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnBrowseProjectGroup = new Button(composite, SWT.NONE);
		btnBrowseProjectGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnBrowseProjectGroup.setText("Browse");
		
		Label lblProject = new Label(composite, SWT.NONE);
		lblProject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProject.setText("Project");
		
		Label lblProjectDetail = new Label(composite, SWT.NONE);
		lblProjectDetail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnBrowseProject = new Button(composite, SWT.NONE);
		btnBrowseProject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnBrowseProject.setText("Browse");
		
		loadPreference();

		return composite;
	}
	
	
	protected void loadPreference(){
		txtPublicKey.setText(getPreferenceStore().getString(PREF_API_PUBLIC_KEY));
		txtSecretKey.setText(getPreferenceStore().getString(PREF_API_SECRET_KEY));
	}


	protected void performDefaults() {
		super.performDefaults();
	}
	
	public boolean performOk() {
		String publicKey = txtPublicKey.getText();
		String secretKey = txtSecretKey.getText();
		
		if(publicKey == null || publicKey.isEmpty() || 
				secretKey == null || secretKey.isEmpty()){
			setErrorMessage("Public Key and Secret Key cannot be empty");
			return false;
		}
		
		getPreferenceStore().setValue(PREF_API_PUBLIC_KEY, publicKey);
		getPreferenceStore().setValue(PREF_API_SECRET_KEY, secretKey);
		return true;
	}

}