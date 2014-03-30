package com.oneskyapp.eclipse.sync.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.Gson;
import com.oneskyapp.eclipse.sync.Activator;
import com.oneskyapp.eclipse.sync.api.model.APIResponse;

public class OneSkyErrorHandler implements ErrorHandler {

	@Override
	public Throwable handleError(final RetrofitError err) {
//		OneSkyServiceTest.printObject(err);
		final Response resp = err.getResponse();
		if (err.isNetworkError()) {
//			Display.getDefault().syncExec(new Runnable() {
//
//				@Override
//				public void run() {
//					IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
//							"Network Connection Error", err.getCause());
//					ErrorDialog.openError(
//							Display.getDefault().getActiveShell(),
//							"Problem Occured", "Network Error", status);
//				}
//			});
			return new RuntimeException("Network Connection Error", err.getCause());
		}else if(resp != null){
			String statusMsg = "Unknown error";
			try {
				statusMsg = IOUtils.toString(resp.getBody().in());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String errMsg = StringUtils.defaultIfBlank(statusMsg, err.getResponse().getReason());
			try{
				errMsg = new Gson().fromJson(statusMsg, APIResponse.class).getMeta().getMessage();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(resp.getStatus() == 401){
				return new RuntimeException(errMsg, err.getCause());
			}else{
				return new RuntimeException(errMsg, err.getCause());
			}
		}
		return err;
	}

}
