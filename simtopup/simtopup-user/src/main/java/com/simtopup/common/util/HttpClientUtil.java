package com.simtopup.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static ObjectMapper objectMapper;
	static {
		objectMapper=new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	}

	public static <T> T httpGet( String url , Class<T> T ,String username , String password){
		
		
		T t=null;
		CloseableHttpResponse response = null;
		try {
			
			CloseableHttpClient httpclient = null;
			if( StringUtils.isNotEmpty(username) || StringUtils.isNotEmpty(password)){
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
			        credsProvider.setCredentials(
			        		AuthScope.ANY,
			                new UsernamePasswordCredentials(username, password));
			    httpclient= HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
			}else{
				httpclient= HttpClients.createDefault();
			}
			 
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				 logger.error("Method failed: " + response.getStatusLine());
				 return null;
			 }
			
		    t=objectMapper.readValue(response.getEntity().getContent(),T);
		
		} catch (Exception e) {
			logger.error("httpclient error",e);
		} finally {
			if( response!=null ){
				try {
					response.close();
				} catch (IOException e) {
					logger.error("http client close error:",e);
				}
			}
		}
		
		return t;
	}
	
	public static <T> T httpPostForm( String url , Map<String, String > form , Class<T> T ,String username , String password){
		
		logger.debug("http post form url:"+url);
		logger.debug("http post form data:"+form);
		
		T t=null;
		CloseableHttpResponse response = null;

		try {
			
			CloseableHttpClient httpclient = null;
			if( StringUtils.isNotEmpty(username) || StringUtils.isNotEmpty(password)){
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
			        credsProvider.setCredentials(
			        		AuthScope.ANY,
			                new UsernamePasswordCredentials(username, password));
			    httpclient= HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
			}else{
				httpclient= HttpClients.createDefault();
			}
			
			HttpPost httpPost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			for( String key : form.keySet()){
				nvps.add(new BasicNameValuePair(key	,form.get(key)));
			}
			
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				 logger.error("Method failed: " + response.getStatusLine());
				 return null;
			 }
			
		    t=objectMapper.readValue(response.getEntity().getContent(),T);
		    
		} catch (Exception e) {
			logger.error("httpclient error",e);
		} finally {
			if( response!=null ){
				try {
					response.close();
				} catch (IOException e) {
					logger.error("http client close error:",e);
				}
			}
		}
		
		return t;
	}
	
	
	public static String httpPostFormForString( String url , Map<String, String > form  ,String username , String password){
		
		logger.debug("http post form url:"+url);
		logger.debug("http post form data:"+form);
		
		CloseableHttpResponse response = null;

		try {
			
			CloseableHttpClient httpclient = null;
			if( StringUtils.isNotEmpty(username) || StringUtils.isNotEmpty(password)){
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
			        credsProvider.setCredentials(
			        		AuthScope.ANY,
			                new UsernamePasswordCredentials(username, password));
			    httpclient= HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
			}else{
				httpclient= HttpClients.createDefault();
			}
			
			HttpPost httpPost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			for( String key : form.keySet()){
				nvps.add(new BasicNameValuePair(key	,form.get(key)));
			}
			
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				 logger.error("Method failed: " + response.getStatusLine());
				 return null;
			 }
			
			return CharStreams.toString(new InputStreamReader(response.getEntity().getContent(),Charsets.UTF_8));
		    
		} catch (Exception e) {
			logger.error("httpclient error",e);
		} finally {
			if( response!=null ){
				try {
					response.close();
				} catch (IOException e) {
					logger.error("http client close error:",e);
				}
			}
		}
		
		return null;
	}
}
