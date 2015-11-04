package com.simtopup.user.test.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.serverlite.core.constant.Code;
import com.simtopup.user.Configuration;
import com.simtopup.user.boundary.PaymentNotificaton;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.controller.ControllerDispatcher;
import com.simtopup.user.controller.topup.TopUpController;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.impl.paypal.PaypalServiceImpl;
import com.simtopup.user.test.DatabaseInitTest;

@Test(groups="controller",dependsOnGroups="service")
public class TopUpControllerTest {
	
	private TopUpController topUpController;
	
	@BeforeTest
	public void init(){
		topUpController = ControllerDispatcher.getInstance().getTopUpController();
	}
	
	Request request ;
	Response response ;
	
	@Test
	public void  testTopUpControllerTopUp(){
		
		request = new Request();
		response = new Response();
		TopUpOrder topUpOrder = new TopUpOrder();
		topUpOrder.setPayMethod(PaypalServiceImpl.NAME);
		topUpOrder.setTopUpAmount(new BigDecimal("10"));
		topUpOrder.setPaymentAmount(new BigDecimal("100"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_AIS);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_AIS_NUMBER);
		//topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_TRUE);
		//topUpOrder.setMobileNumber("0941898373");
		topUpOrder.setCountryCode(TopUpOrder.COUNTRY_CODE_THAI);
		topUpOrder.setCurrency("");
		topUpOrder.setExchangeRate(new BigDecimal(5));
		request.setTopUpOrder(topUpOrder);
		
		topUpController.createTopUp(request, response);
		
		Assert.assertEquals(response.getCode(), Code.SUCCESS);
		
	}
	
	@Test(threadPoolSize=10,invocationCount=1,dependsOnMethods="testTopUpControllerTopUp")
	public void  testTopUpControllerPayCallbackConcurrency(){
		
		Assert.assertNotNull(request);
		PaymentNotificaton paymentNotificaton = new PaymentNotificaton();
		paymentNotificaton.setPaymentOrderId(request.getTopUpOrder().getPaymentOrderId());
		paymentNotificaton.setPaymentTransactionId("2015111200001000970068016243");
		paymentNotificaton.setPayMethod(Configuration.NAME);
		paymentNotificaton.setPaymentStatus("WAIT_SELLER_SEND_GOODS");
		request.setPaymentNotificaton(paymentNotificaton);
		
		/*{gmt_create=2015-11-12 12:11:13,
		 *  seller_email=lian.hu.chinese@gmail.com, 
		 *  subject=海外手机充值订单 您的手机号是:0941898373, 
		 *  use_coupon=N, 
		 *  sign=444eeee020600e7a14143a5741c899d7, 
		 *  discount=0.00, logistics_fee=0.00, 
		 *  buyer_id=2088122294944971,
		 *   notify_id=de0d9d22d4ae3cc620bf94d95448876f7e, 
		 *   notify_type=trade_status_sync,
		 *    receive_phone=18508313751, 
		 *    price=2.00, 
		 *    trade_status=WAIT_BUYER_PAY,
		 *     total_fee=2.00, 
		 *     receive_name=胡炼,
		 *      logistics_type=EXPRESS, sign_type=MD5,
		 *       seller_id=2088312171023422,
		 *        receive_address=四川省 宜宾市 长宁县 过岁小区, 
		 *        is_total_fee_adjust=N, 
		 *        receive_zip=644300, 
		 *        buyer_email=hu.lian.cn@gmail.com, 
		 *        notify_time=2015-11-12 15:35:27,
		 *         quantity=1, 
		 *         gmt_logistics_modify=2015-11-12 12:11:13, 
		 *         payment_type=1, 
		 *         out_trade_no=5fe0eb0c-8578-478d-af31-9b51ff1694a6, 
		 *         trade_no=2015111200001000970068016243, 
		 *         logistics_payment=SELLER_PAY}
		 *         */
		Map<String, String> params = new HashMap<>();
		params.put("gmt_create", "2015-11-12 12:11:13");
		params.put("seller_email", "lian.hu.chinese@gmail.com");
		params.put("subject", new String("海外手机充值订单 您的手机号是:0941898373"));
		params.put("use_coupon", "N");
		params.put("sign", "444eeee020600e7a14143a5741c899d7");
		params.put("discount", "0.00");
		params.put("logistics_fee", "0.00");
		params.put("buyer_id", "2088122294944971");
		params.put("notify_id", "de0d9d22d4ae3cc620bf94d95448876f7e");
		params.put("notify_type", "trade_status_sync");
		params.put("receive_phone", "18508313751");
		params.put("price", "2.00");
		params.put("trade_status", "WAIT_BUYER_PAY");
		params.put("total_fee", "2.00");
		params.put("receive_name", "胡炼");
		params.put("logistics_type", "EXPRESS");
		params.put("sign_type", "MD5");
		params.put("seller_id", "2088312171023422");
		params.put("is_total_fee_adjust", "N");
		params.put("receive_zip", "644300");
		params.put("receive_address", "四川省 宜宾市 长宁县 过岁小区");
		params.put("buyer_email", "hu.lian.cn@gmail.com");//
		params.put("notify_time", "2015-11-12 15:35:27");
		params.put("quantity", "1");
		params.put("gmt_logistics_modify", "2015-11-12 12:11:13");
		params.put("payment_type", "1");
		params.put("out_trade_no", "5fe0eb0c-8578-478d-af31-9b51ff1694a6");
		params.put("trade_no", "2015111200001000970068016243");
		params.put("logistics_payment", "SELLER_PAY");
		request.setQueryParams(params);
		
		try {
			topUpController.approveTopUp(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(response.getCode(), Code.SUCCESS);
		Assert.assertEquals(response.getMessage().toUpperCase(),"SUCCESS");
		
	}

}
