package com.simtopup.user.dao;

import java.util.Date;
import java.util.List;

import com.simtopup.user.entity.TopUpOrder;

public interface TopUpOrderDao {

	void createTopUpOrder( TopUpOrder topUpOrder );
	
	TopUpOrder findTopUpOrderById( Integer id , boolean lock );

	List<TopUpOrder> findTopUpOrderByCreatetime( Date startTime , Date endTime , Integer pageIndex , Integer pageSize );

	void updateOrderStatus(Integer id, int status);

	void updateTopUpOrderId(Integer id, String topUpId);
	
	void updateTopUpOrderIdAndStatus(Integer id, String topUpId , int status);

	void updateTopUpTransactionId(Integer id, String topUpTransactionId);
	
	void updatePaymentOrderId(Integer id, String topUpId);

	void updatePaymentTransactionId(Integer id, String topUpTransactionId);

	TopUpOrder findTopUpOrderByPaymentOrderId(String id, boolean lock);
}
