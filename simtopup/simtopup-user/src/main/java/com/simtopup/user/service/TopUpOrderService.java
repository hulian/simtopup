package com.simtopup.user.service;

import com.simtopup.user.entity.TopUpOrder;

public interface TopUpOrderService {

	void createTopUpOrder(TopUpOrder topUpOrder);
	
	void updateTopUpOrderStatus( Integer id , Integer status );
	
	TopUpOrder findTopupOrderById( Integer id );
	
	TopUpOrder findTopUpOrderByPaymentOrderId(String id, boolean lock);
}
