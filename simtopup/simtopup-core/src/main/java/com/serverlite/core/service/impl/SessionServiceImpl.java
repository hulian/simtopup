package com.serverlite.core.service.impl;

import com.google.common.cache.Cache;
import com.serverlite.core.dao.SessionDao;
import com.serverlite.core.entity.Session;
import com.serverlite.core.service.SessionService;
import com.serverlite.core.transaction.TransactionManager;

public class SessionServiceImpl implements SessionService{
	
	private Cache<String, Session> sessionCache;
	private TransactionManager transactionManager;
	private SessionDao sessionDao;
	
	public SessionServiceImpl(Cache<String, Session> sessionCache,TransactionManager transactionManager,SessionDao sessionDao) {
		this.sessionCache=sessionCache;
		this.transactionManager=transactionManager;
		this.sessionDao=sessionDao;
	}

	@Override
	public Session createSession( Session session ) {
		sessionCache.put(session.getToken(), session);
		Session sessionC=transactionManager.doInTransaction(()->{
			sessionDao.createSession(session);
			return session;
		});
		return sessionC;
	}

	@Override
	public Session findSessionByToken(String token) {
		Session session = sessionCache.getIfPresent(token);
		if(session==null){
			session=transactionManager.doInTransaction(()->{
				return sessionDao.findSessionByToken(token);
			});
		}
		return session;
	}

	public Cache<String, Session> getSessionCache() {
		return sessionCache;
	}

	public void setSessionCache(Cache<String, Session> sessionCache) {
		this.sessionCache = sessionCache;
	}

}
