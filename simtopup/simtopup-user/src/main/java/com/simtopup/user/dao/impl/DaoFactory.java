package com.simtopup.user.dao.impl;

import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.ApiConfigDao;
import com.simtopup.user.dao.TopUpOrderDao;
import com.simtopup.user.dao.UserDao;
import com.simtopup.user.dao.WalletDao;

public class DaoFactory{
	
	private static final DaoFactory daoFactory = new DaoFactory();
	
	private UserDao userDao;
	
	private WalletDao walletDao;
	
	private ApiConfigDao apiConfigDao;
	
	private TopUpOrderDao topUpOrderDao;
	
	private DaoFactory() {
		TransactionManagerFactory transactionManagerFactory = TransactionManagerFactory.getInstance();
		TransactionManager transactionManager = transactionManagerFactory.getTransactionManager(Configuration.DATA_SOURCE_USER);
		userDao = new UserDaoImpl(transactionManager);
		walletDao=new WalletDaoImpl(transactionManager);
		apiConfigDao = new ApiConfigDaoImpl(transactionManager);
		topUpOrderDao = new TopUpOrderDaoImpl(transactionManager);
	}


	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public WalletDao getWalletDao() {
		return walletDao;
	}

	public void setWalletDao(WalletDao walletDao) {
		this.walletDao = walletDao;
	}

	public ApiConfigDao getApiConfigDao() {
		return apiConfigDao;
	}


	public void setApiConfigDao(ApiConfigDao apiConfigDao) {
		this.apiConfigDao = apiConfigDao;
	}


	public TopUpOrderDao getTopUpOrderDao() {
		return topUpOrderDao;
	}


	public void setTopUpOrderDao(TopUpOrderDao topUpOrderDao) {
		this.topUpOrderDao = topUpOrderDao;
	}


	public static DaoFactory getInstance() {
		return daoFactory;
	}

	
}
