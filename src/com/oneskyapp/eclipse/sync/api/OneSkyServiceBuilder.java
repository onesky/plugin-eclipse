package com.oneskyapp.eclipse.sync.api;

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
        .setErrorHandler(new ErrorHandler() {
			
			@Override
			public Throwable handleError(RetrofitError err) {
//				String respBody = err.getResponse().getBody().toString();//TODO read content from input stream
//				System.out.println(respBody);
//				Meta meta = new Gson().fromJson(respBody, Meta.class);
//				System.out.println(meta.getStatus());
				return err;
			}
		});
//		.setLogLevel(LogLevel.FULL)
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
