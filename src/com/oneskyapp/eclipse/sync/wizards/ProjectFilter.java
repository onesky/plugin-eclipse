package com.oneskyapp.eclipse.sync.wizards;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.oneskyapp.eclipse.sync.api.model.Project;

public class ProjectFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Project prj = (Project) element;
		if (StringUtils.defaultString(String.valueOf(prj.getId())).matches(
				searchString)) {
			return true;
		}
		if (StringUtils.defaultString(prj.getName()).matches(searchString)) {
			return true;
		}

		return false;
	}

}
