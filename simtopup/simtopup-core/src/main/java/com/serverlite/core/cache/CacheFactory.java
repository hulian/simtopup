package com.serverlite.core.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.serverlite.core.entity.Session;

public class CacheFactory {
	
	private static final CacheFactory cacheFactory = new CacheFactory();
	
	
	private Cache<String, String> kaptchaCache = CacheBuilder.newBuilder().maximumSize(10000).build();
	private Cache<String,Session> sessionCache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterAccess(2, TimeUnit.HOURS).build();
	
	private CacheFactory() {
	}
	
	public static CacheFactory getInstrance(){
		return cacheFactory;
	}

	public Cache<String, String> getKaptchaCache() {
		return kaptchaCache;
	}

	public void setKaptchaCache(Cache<String, String> kaptchaCache) {
		this.kaptchaCache = kaptchaCache;
	}

	public Cache<String,Session> getSessionCache() {
		return sessionCache;
	}

	public void setSessionCache(Cache<String,Session> sessionCache) {
		this.sessionCache = sessionCache;
	}
	
}
	
