package com.simtopup.user.service.impl;

import java.math.BigDecimal;

import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.WalletDao;
import com.simtopup.user.service.BalanceService;

public class BalanceServiceImpl implements BalanceService {
	
	private TransactionManager transactionManager;
	private WalletDao walletDao;
	
	public BalanceServiceImpl(TransactionManager transactionManager,WalletDao walletDao) {
		this.transactionManager=transactionManager;
		this.walletDao=walletDao;
	}

	@Override
	public BigDecimal findBalanceByUserId(Integer userId) {
		return transactionManager.doInTransaction(()->{
			return walletDao.findBalanceByUserId(userId);
		});
	}

}
