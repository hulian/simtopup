package com.serverlite.core.dao.impl;

import com.serverlite.core.Configuration;
import com.serverlite.core.dao.SessionDao;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;

public class CoreDaoFactory{
	
	private static final CoreDaoFactory coreDaoFactory = new CoreDaoFactory();
	
	private CoreDaoFactory() {
		TransactionManager transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_SESSION);
		sessionDao = new SessionDaoImpl(transactionManager);
	}
	
	public static CoreDaoFactory getInstance(){
		return coreDaoFactory;
	}
	
	private SessionDao sessionDao;


	public SessionDao getSessionDao() {
		return sessionDao;
	}

	public void setSessionDao(SessionDao sessionDao) {
		this.sessionDao = sessionDao;
	}

	
}
