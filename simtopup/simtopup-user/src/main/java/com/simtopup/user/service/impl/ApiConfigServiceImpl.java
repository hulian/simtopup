package com.simtopup.user.service.impl;

import com.serverlite.core.service.impl.AbstractService;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.ApiConfigDao;
import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.service.ApiConfigService;

public class ApiConfigServiceImpl extends AbstractService implements ApiConfigService{
	
	private ApiConfigDao apiConfigDao;

	public ApiConfigServiceImpl(TransactionManager transactionManager,
							ApiConfigDao apiConfigDao) {
		super(transactionManager);
		this.apiConfigDao=apiConfigDao;
	}

	@Override
	public ApiConfig findApiConfigById(Integer apiConfigId) {
		return transactionManager.doInTransaction(()->{
			return  apiConfigDao.findApiConfigById(apiConfigId);
		});
	}

}
