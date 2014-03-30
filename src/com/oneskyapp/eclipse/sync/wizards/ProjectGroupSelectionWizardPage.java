package com.oneskyapp.eclipse.sync.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.OneSkyServiceBuilder;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroup;
import com.oneskyapp.eclipse.sync.api.model.ProjectGroupList;

public class ProjectGroupSelectionWizardPage extends WizardPage {
	private Composite composite;
	private Text txtSearch;
	private Table table;
	private TableViewer tableViewer;
	private ProjectSelectionWizardModel model;
	private TableViewerColumn nameCol;
	private ProjectGroupFilter projectGroupFilter;

	public ProjectGroupSelectionWizardPage(ProjectSelectionWizardModel model) {
		super("Project Group Selection");
		this.model = model;
		setTitle("Project Group Selection");
		setDescription("Select a project group");
	}
	
	

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			setPageComplete(false);
			model.setProjectGroup(null);
			getShell().getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					loadProjectGroups();
				}
			});
		}
	}

	@Override
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		{
			txtSearch = new Text(composite, SWT.BORDER | SWT.SEARCH);
			txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false, 1, 1));
			txtSearch.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ke) {
					projectGroupFilter.setSearchText(txtSearch.getText());
					tableViewer.refresh();
				}
			});
		}

		{

			String[] titles = { "#", "Name" };
			int[] bounds = { 100, 100 };
			tableViewer = new TableViewer(composite, SWT.BORDER
					| SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
					1));
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			tableViewer.setContentProvider(new ArrayContentProvider());
			projectGroupFilter = new ProjectGroupFilter();
			tableViewer.addFilter(projectGroupFilter);

			TableViewerColumn col = createTableViewerColumn(titles[0],
					bounds[0], 0);
			col.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					ProjectGroup p = (ProjectGroup) element;
					return String.valueOf(p.getId());
				}
			});

			nameCol = createTableViewerColumn(titles[1], bounds[1], 1);
			nameCol.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					ProjectGroup p = (ProjectGroup) element;
					return p.getName();
				}
			});

			tableViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(
								final SelectionChangedEvent event) {
							IStructuredSelection selection = (IStructuredSelection) event
									.getSelection();
							ProjectGroup grp = (ProjectGroup) selection
									.getFirstElement();
							model.setProjectGroup(grp);
							setPageComplete(true);
						}
					});
		}
		setControl(composite);
	}

	private void loadProjectGroups() {
		try {
			this.getContainer().run(true, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Retrieving Project Groups", 100);
					OneSkyService service = new OneSkyServiceBuilder(model
							.getPublicKey(), model.getSecretKey()).build();
					ProjectGroupList groupList = service.getProjectGroupList();
					final List<ProjectGroup> projectGroups = groupList
							.getProjectGroups();

					monitor.done();

					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							tableViewer.setInput(projectGroups);
							nameCol.getColumn().pack();
						}
					});
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
}
