package com.simtopup.user.service;

import java.util.Map;
import com.simtopup.user.boundary.PaymentNotificaton;
import com.simtopup.user.service.bean.PaymentAccountBean;
import com.simtopup.user.service.bean.PaymentOrderBean;
import com.simtopup.user.service.bean.PaymentSubmitBean;

public interface PaymentService {

	/**
	 * 
	 * @return 签名信息
	 */
	PaymentSubmitBean createPaymentSign( PaymentOrderBean paymentOrderBean , PaymentAccountBean paymentAccountBean );

	/**
	 * 验证签名信息
	 * @param params
	 * @return
	 */
	boolean verifySign(Map<String, String> params , PaymentAccountBean paymentAccountBean);
	
	/**
	 * 处理第三方支付通知结果
	 * @param paymentNotificaton
	 * @return 如果支付成功返回true 负责返回false
	 */
	boolean handlePaymentStatus(PaymentNotificaton paymentNotificaton);
	
	/**
	 * 查询支付订单状态
	 * @param orderId 订单id
	 * @return
	 */
	String checkPaymentStatus( String paymentId );
	
	
	/**
	 * 确认发货，担保交易需要实现
	 * @param paymentAccountBean
	 * @return
	 */
	Map<String, String> createSendGoodsParams(PaymentNotificaton paymentNotificaton  ,PaymentAccountBean paymentAccountBean);
	
}
