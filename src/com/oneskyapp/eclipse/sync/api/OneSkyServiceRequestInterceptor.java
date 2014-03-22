package com.oneskyapp.eclipse.sync.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import retrofit.RequestInterceptor;

public class OneSkyServiceRequestInterceptor implements RequestInterceptor{

	private String publicKey;
	private String sercetKey;
	
	public OneSkyServiceRequestInterceptor(String publicKey, String sercetKey) {
		super();
		this.publicKey = publicKey;
		this.sercetKey = sercetKey;
	}

	@Override
	public void intercept(RequestFacade request) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
			String devHash = Hex.encodeHexString(md.digest((timestamp+sercetKey).getBytes()));
			System.out.println(devHash);
			request.addQueryParam("api_key", publicKey);
        	request.addQueryParam("timestamp", timestamp);
        	request.addQueryParam("dev_hash", devHash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
