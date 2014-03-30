package com.oneskyapp.eclipse.sync.api;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;

public class OneSkyServiceBuilder {
	
	private final static String domain = "https://platform.api.onesky.io/1";
	private String publicKey;
	private String sercetKey;
	private boolean debugMode;
	
	public OneSkyServiceBuilder(String publicKey, String sercetKey) {
		super();
		this.publicKey = publicKey;
		this.sercetKey = sercetKey;
	}

	public OneSkyService build(){
		Builder builder = new RestAdapter.Builder()
		.setEndpoint(domain)
		.setRequestInterceptor(new OneSkyServiceRequestInterceptor(publicKey,sercetKey))
        .setErrorHandler(new OneSkyErrorHandler());
		if(debugMode){
			builder.setLogLevel(LogLevel.FULL);
		}
		
		RestAdapter restAdapter = builder.build();
	
		OneSkyService service = restAdapter.create(OneSkyService.class);
		return service;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
