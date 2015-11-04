package com.simtopup.user.service.impl;

import com.serverlite.core.service.impl.AbstractService;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.TopUpOrderDao;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.TopUpOrderService;

public class TopUpOrderServiceImpl extends AbstractService implements TopUpOrderService{
	
	private TopUpOrderDao topUpOrderDao;

	public TopUpOrderServiceImpl(TransactionManager transactionManager,
							TopUpOrderDao topUpOrderDao) {
		super(transactionManager);
		this.topUpOrderDao=topUpOrderDao;
	}

	@Override
	public void createTopUpOrder(TopUpOrder topUpOrder) {
		transactionManager.doInTransaction(()->{
			topUpOrderDao.createTopUpOrder(topUpOrder);
			return null;
		});
	}

	@Override
	public void updateTopUpOrderStatus(Integer id, Integer status) {
		transactionManager.doInTransaction(()->{
			topUpOrderDao.updateOrderStatus(id, status);
			return null;
		});		
	}

	@Override
	public TopUpOrder findTopupOrderById(Integer id) {
		return transactionManager.doInTransaction(()->{
			return topUpOrderDao.findTopUpOrderById(id, false);
		});		
	}

	@Override
	public TopUpOrder findTopUpOrderByPaymentOrderId(String id, boolean lock) {
		return transactionManager.doInTransaction(()->{
			return topUpOrderDao.findTopUpOrderByPaymentOrderId(id, false);
		});	
	}

}
