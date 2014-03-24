package com.oneskyapp.eclipse.sync.properties;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;

import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.OneSkyServiceBuilder;
import com.oneskyapp.eclipse.sync.api.model.Project;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroup;
import com.oneskyapp.eclipse.sync.utils.ProjectPreferenceHelper;

public class OneSkyPropertyPage extends PropertyPage {
	private Text txtPublicKey;
	private Text txtSecretKey;
	private IProject project;
	private Label lblProjectGroup;
	private Button btnBrowseProjectGroup;
	private Label lblProject;
	private Button btnBrowseProject;
	private Text txtProjectGroupDetail;
	private Text txtProjectDetail;

	private String projectGroupId;
	private String projectGroupName;
	
	private ProjectPreferenceHelper prjPerf;
	private String projectName;
	private String projectId;

	public OneSkyPropertyPage() {
		super();

		setDescription("Get your API keys from your OneSky Site Setting Page and select project");
	}

	@Override
	public void setElement(IAdaptable element) {
		project = (IProject) element.getAdapter(IProject.class);

		prjPerf = new ProjectPreferenceHelper(project);

		setPreferenceStore(prjPerf.getPrefStore());
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(final Composite parent) {
		this.noDefaultAndApplyButton();

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		Label lblPublicKey = new Label(composite, SWT.NONE);
		lblPublicKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPublicKey.setText("Public Key");

		txtPublicKey = new Text(composite, SWT.BORDER);
		txtPublicKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));

		Label lblSecretKey = new Label(composite, SWT.NONE);
		lblSecretKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSecretKey.setText("Secret Key");

		txtSecretKey = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		txtSecretKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));

		lblProjectGroup = new Label(composite, SWT.NONE);
		lblProjectGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblProjectGroup.setText("Project Group");

		txtProjectGroupDetail = new Text(composite, SWT.BORDER);
		txtProjectGroupDetail.setEditable(false);
		txtProjectGroupDetail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		new Label(composite, SWT.NONE);

		btnBrowseProjectGroup = new Button(composite, SWT.NONE);
		btnBrowseProjectGroup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseProjectGroups();
			}
		});

		btnBrowseProjectGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnBrowseProjectGroup.setText("Browse");

		lblProject = new Label(composite, SWT.NONE);
		lblProject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblProject.setText("Project");

		txtProjectDetail = new Text(composite, SWT.BORDER);
		txtProjectDetail.setEditable(false);
		txtProjectDetail.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
				false, 1, 1));
		new Label(composite, SWT.NONE);

		btnBrowseProject = new Button(composite, SWT.NONE);
		btnBrowseProject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnBrowseProject.setText("Browse");
		btnBrowseProject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseProjects();
			}
		});

		loadPreference();

		return composite;
	}
	
	protected void browseProjects() {
		String publicKey = txtPublicKey.getText();
		String secretKey = txtSecretKey.getText();

		if(StringUtils.isBlank(publicKey)){
			setErrorMessage("API Public Key is not set");
			return;
		}
		
		if(StringUtils.isBlank(secretKey)){
			setErrorMessage("API Secret Key is not set");
			return;
		}
		
		if(StringUtils.isBlank(projectGroupId)){
			setErrorMessage("Project Group is not set");
			return;
		}
		
		OneSkyService service = new OneSkyServiceBuilder(publicKey, secretKey)
				.build();

		List<Project> project = service.getProjectList(projectGroupId).getProjects();

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), new LabelProvider() {

					@Override
					public String getText(Object element) {
						Project projectGroup = (Project) element;
						return String.format("#%s - %s", projectGroup.getId(), projectGroup.getName());
					}

				});
		dialog.setElements(project.toArray(new Project[0]));
		dialog.setEmptyListMessage("No Project Available");
		dialog.setTitle("Project ");
		dialog.setHelpAvailable(false);
		dialog.setMessage("Select Project from the list");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			System.out.println(result.length);
			if (result.length > 0) {
				Project pg = (Project) result[0];
				projectName = pg.getName();
				projectId = String.valueOf(pg.getId());

				setProjectDetail(projectId, projectName);
			}
		}

	}

	protected void browseProjectGroups() {
		String publicKey = txtPublicKey.getText();
		String secretKey = txtSecretKey.getText();
		OneSkyService service = new OneSkyServiceBuilder(publicKey, secretKey)
				.build();

		List<ProjectGroup> projectGroups = service.getProjectGroupList()
				.getProjectGroups();

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), new LabelProvider() {

					@Override
					public String getText(Object element) {
						ProjectGroup projectGroup = (ProjectGroup) element;
						return projectGroup.getName();
					}

				});
		dialog.setElements(projectGroups.toArray(new ProjectGroup[0]));
		dialog.setEmptyListMessage("No Project Group Available");
		dialog.setTitle("Project Group");
		dialog.setHelpAvailable(false);
		dialog.setMessage("Select Project Group from the list");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			System.out.println(result.length);
			if (result.length > 0) {
				ProjectGroup pg = (ProjectGroup) result[0];
				projectGroupName = pg.getName();
				projectGroupId = String.valueOf(pg.getId());

				setProjectGroupDetail(projectGroupId, projectGroupName);
			}
		}

	}
	
	protected void setProjectGroupDetail(String pgId, String pgName){
		txtProjectGroupDetail.setText(String.format("#%s, %s", pgId, pgName));
	}
	
	protected void setProjectDetail(String prjId, String prjName){
		txtProjectDetail.setText(String.format("#%s, %s", prjId, prjName));
	}

	protected void loadPreference() {
		txtPublicKey.setText(prjPerf.getAPIPublicKey());
		txtSecretKey.setText(prjPerf.getAPISecretKey());
		
		projectGroupId = prjPerf.getProjectGroupId();
		projectGroupName = prjPerf.getProjectGroupName();
		setProjectGroupDetail(projectGroupId, projectGroupName);
		
		projectId = prjPerf.getProjectId();
		projectName = prjPerf.getProjectName();
		setProjectDetail(projectId, projectName);
	}

	protected void performDefaults() {
		super.performDefaults();
	}

	public boolean performOk() {
		IPreferenceStore ps = getPreferenceStore();

		String publicKey = txtPublicKey.getText();
		String secretKey = txtSecretKey.getText();

		if (publicKey == null || publicKey.isEmpty() || secretKey == null
				|| secretKey.isEmpty()) {
			setErrorMessage("Public Key and Secret Key cannot be empty");
			return false;
		}

		if (projectGroupId == null || projectGroupId.isEmpty()) {
			setErrorMessage("Project Group cannot be empty");
			return false;
		}

		prjPerf.setAPIPublicKey(publicKey);
		prjPerf.setAPISecretKey(secretKey);
		prjPerf.setProjectGroupId(projectGroupId);
		prjPerf.setProjectGroupName(projectGroupName);
		prjPerf.setProjectId(projectId);
		prjPerf.setProjectName(projectName);

		return true;
	}

}