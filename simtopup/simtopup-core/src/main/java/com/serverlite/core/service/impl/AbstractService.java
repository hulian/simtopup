package com.serverlite.core.service.impl;

import com.serverlite.core.transaction.TransactionManager;

public class AbstractService {

	protected TransactionManager transactionManager;
	
	public AbstractService(TransactionManager transactionManager) {
		this.transactionManager=transactionManager;
	}
}
