package com.oneskyapp.eclipse.sync.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;

public class MultiLineRecursiveToStringStyle extends RecursiveToStringStyle {

	public MultiLineRecursiveToStringStyle(){
		super();
		this.setContentStart("[");
        this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
        this.setFieldSeparatorAtStart(true);
        this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        this.setUseShortClassName(true);
	}
	
}
