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
import com.oneskyapp.eclipse.sync.api.model.Project;
import com.oneskyapp.eclipse.sync.api.model.ProjectDetail;
import com.oneskyapp.eclipse.sync.api.model.ProjectList;
import com.oneskyapp.eclipse.sync.api.model.ProjectType;

public class ProjectSelectionWizardPage extends WizardPage {

	private Composite composite;
	private Text txtSearch;
	private Table table;
	private TableViewer tableViewer;
	private ProjectSelectionWizardModel model;
	private TableViewerColumn nameCol;
	private ProjectDetailFilter projectFilter;

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
			txtSearch.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ke) {
					projectFilter.setSearchText(txtSearch.getText());
					tableViewer.refresh();
				}
			});
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
			projectFilter = new ProjectDetailFilter();
			tableViewer.addFilter(projectFilter);
			
			String[] titles = { "#", "Name", "Type", "Description"};
		    int[] bounds = { 100, 100, 100, 100 };

		    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		    col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		    	  ProjectDetail p = (ProjectDetail) element;
		        return String.valueOf(p.getId());
		      }
		    });

		    col = createTableViewerColumn(titles[1], bounds[1], 1);
		    col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
					ProjectDetail p = (ProjectDetail) element;
					return p.getName();
				}
		    });
		    
		    col = createTableViewerColumn(titles[2], bounds[2], 2);
		    col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
					ProjectDetail p = (ProjectDetail) element;
					ProjectType type = p.getType();
					if(type != null){
						return type.getName();
					}
					return "";
				}
		    });
		    
		    col = createTableViewerColumn(titles[3], bounds[3], 3);
		    col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
					ProjectDetail p = (ProjectDetail) element;
					return p.getDescription();
				}
		    });
		    
		    tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		        public void selectionChanged(final SelectionChangedEvent event) {
		            IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		            final ProjectDetail prjDetail = (ProjectDetail) selection.getFirstElement();
		            model.setProject(new Project(){

						@Override
						public long getId() {
							return prjDetail.getId();
						}

						@Override
						public String getName() {
							return prjDetail.getName();
						}
		            	
		            });
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

					ProjectList projectList = service.getProjectList(String
							.valueOf(model.getProjectGroup().getId()));
					final List<Project> projects = projectList.getProjects();
					
					final List<ProjectDetail> projectDetails = new ArrayList<ProjectDetail>();
					for(Project project: projects){
						monitor.subTask(String.format("Retrieving Project %s Details", project.getId()));
						ProjectDetail prjDetail = service.getProjectDetail(
								String.valueOf(project.getId())).getDetail();
						projectDetails.add(prjDetail);
					}
					
					monitor.done();
					
					Display.getDefault().syncExec(new Runnable() {
						
						@Override
						public void run() {
							tableViewer.setInput(projectDetails);
							for(TableColumn tableColumn: table.getColumns()){
								tableColumn.pack();
							}
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
