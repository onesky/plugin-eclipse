package com.oneskyapp.eclipse.sync.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.oneskyapp.eclipse.sync.api.OneSkyService;
import com.oneskyapp.eclipse.sync.api.OneSkyServiceBuilder;
import com.oneskyapp.eclipse.sync.api.model.Project;

public class ProjectSelectionWizardPage extends WizardPage {

	private Composite composite;
	private Text txtSearch;
	private Table table;
	private TableViewer tableViewer;
	private ProjectSelectionWizardModel model;
	private TableViewerColumn nameCol;

	public ProjectSelectionWizardPage(ProjectSelectionWizardModel model) {
		super("Project Selection");
		this.model = model;
		setTitle("Project Selection");
		setDescription("Select a project");
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			setPageComplete(false);
			model.setProject(null);
			getShell().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					loadProjects();
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
		}

		{
			tableViewer = new TableViewer(composite, SWT.BORDER
					| SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
					1));
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			tableViewer.setContentProvider(new ArrayContentProvider());
			
			String[] titles = { "#", "Name"};
		    int[] bounds = { 100, 100 };

		    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		    col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		    	  Project p = (Project) element;
		        return String.valueOf(p.getId());
		      }
		    });

		    nameCol = createTableViewerColumn(titles[1], bounds[1], 1);
		    nameCol.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		    	  Project p = (Project) element;
		        return p.getName();
		      }
		    });
		    
		    tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		        public void selectionChanged(final SelectionChangedEvent event) {
		            IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		            Project prj = (Project) selection.getFirstElement();
		            model.setProject(prj);
		            setPageComplete(true);
		        }
		    });
		}
		setPageComplete(false);
		setControl(composite);
		
	}
	
	private void loadProjects(){
		try {
			this.getContainer().run(true, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Retrieving Projects", 100);
					OneSkyService service = new OneSkyServiceBuilder(model
							.getPublicKey(), model.getSecretKey()).build();

//					ProjectList projectList = service.getProjectList(String
//							.valueOf(model.getProjectGroup().getId()));
//					List<Project> projects = projectList.getProjects();
					
					Thread.sleep(2000);
					
					final List<Project> projects = new ArrayList<Project>();
					Project prj;
					prj = new Project(){

						@Override
						public long getId() {
							return 1L;
						}

						@Override
						public String getName() {
							return "test 1";
						}
						
					};
					projects.add(prj);
					prj = new Project(){

						@Override
						public long getId() {
							return 2L;
						}

						@Override
						public String getName() {
							return "test 2";
						}
						
					};
					projects.add(prj);
					monitor.done();
					
					Display.getDefault().syncExec(new Runnable() {
						
						@Override
						public void run() {
							tableViewer.setInput(projects);
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
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
}
