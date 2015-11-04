package com.serverlite.core.controller.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverlite.core.entity.Session;

public class ServerLiteExchange {
	
	private static ObjectMapper objectMapper;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	private String token;
	
	private String url;
	
	private Map<String,  String> queryParams = new HashMap<>();
	
	private Session session;

	private InputStream inputStream;
	
	private OutputStream outputStream;
	
	public<T> T readJsonObject(Class<T> T){
		try {
			return objectMapper.readValue(inputStream, T);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeJsonObject(Object obj){
		try {
			 objectMapper.writeValue(outputStream, obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeString(String string , String charset ){
		 try {
			outputStream.write(string.getBytes(charset));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
}
