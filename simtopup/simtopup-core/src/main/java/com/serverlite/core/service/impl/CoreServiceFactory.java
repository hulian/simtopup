package com.serverlite.core.service.impl;

import com.serverlite.core.Configuration;
import com.serverlite.core.cache.CacheFactory;
import com.serverlite.core.dao.impl.CoreDaoFactory;
import com.serverlite.core.service.RoleService;
import com.serverlite.core.service.SessionService;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;

public class CoreServiceFactory{
	
	private static final CoreServiceFactory coreServiceFactory = new CoreServiceFactory();
	
	private RoleService roleService;
	
	private SessionService sessionService;

	public CoreServiceFactory() {
		CoreDaoFactory coreDaoFactory = CoreDaoFactory.getInstance();
		CacheFactory cacheFactory = CacheFactory.getInstrance();
		TransactionManager transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_SESSION);
		roleService=new RoleServiceImpl();
		sessionService=new SessionServiceImpl(cacheFactory.getSessionCache(),transactionManager,coreDaoFactory.getSessionDao());
	}
	
	public static CoreServiceFactory getInstrance(){
		return coreServiceFactory;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	
}
