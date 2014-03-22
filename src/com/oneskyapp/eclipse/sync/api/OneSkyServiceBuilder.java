package com.oneskyapp.eclipse.sync.api;

import java.io.StringWriter;

import com.google.gson.Gson;
import com.oneskyapp.eclipse.sync.api.OneSkyService.Meta;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

public class OneSkyServiceBuilder {
	
	private final static String domain = "https://platform.api.onesky.io/1";
	private String publicKey;
	private String sercetKey;
	
	public OneSkyServiceBuilder(String publicKey, String sercetKey) {
		super();
		this.publicKey = publicKey;
		this.sercetKey = sercetKey;
	}

	public OneSkyService build(){
		RestAdapter restAdapter = new RestAdapter.Builder()
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
		})
//		.setLogLevel(LogLevel.FULL)
		.build();
	
		OneSkyService service = restAdapter.create(OneSkyService.class);
		return service;
	}
}
