package com.simtopup.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serverlite.core.controller.Dispatcher;
import com.serverlite.core.controller.impl.ServerLiteExchange;
import com.simtopup.user.Configuration;
import com.simtopup.user.boundary.PaymentNotificaton;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.controller.topup.TopUpController;
import com.simtopup.user.service.impl.ServiceFactory;

public class PaymentNotifictionDispatcher implements Dispatcher {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentNotifictionDispatcher.class);
	
	private static final PaymentNotifictionDispatcher instance = new PaymentNotifictionDispatcher();
	
	private TopUpController topUpController;
	
	public PaymentNotifictionDispatcher( ) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		topUpController=new TopUpController(
				serviceFactory.getApiConfigService(),
				serviceFactory.getTopUpOrderService(),
				serviceFactory.getPaypalService(),
				serviceFactory.getAlipayService(),
				serviceFactory.getThaiTopUpService());
	}

	@Override
	public void dispatch(ServerLiteExchange exchange) {

		Request request = new Request();
		Response response = new Response();
		try {
			logger.info(exchange.getUrl());
			logger.info(exchange.getQueryParams().toString());
			request.setQueryParams(exchange.getQueryParams());
			switch (exchange.getUrl()) {
			case "/alipay":
				PaymentNotificaton paymentNotificaton = new PaymentNotificaton();
				paymentNotificaton.setPayMethod(Configuration.NAME);
				paymentNotificaton.setPaymentOrderId(exchange.getQueryParams().get("out_trade_no"));
				paymentNotificaton.setPaymentTransactionId(exchange.getQueryParams().get("trade_no"));
				paymentNotificaton.setPaymentStatus(exchange.getQueryParams().get("trade_status"));
				request.setPaymentNotificaton(paymentNotificaton);
				topUpController.approveTopUp(request, response);
				response.setMessage(response.getMessage().toLowerCase());
				break;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error("SERVER ERROR",e);
			response.setMessage("ERROR");
		}
		
		exchange.writeString(response.getMessage(),"utf-8");
	}

	@Override
	public String getPath() {
		return "/payment";
	}

	public static PaymentNotifictionDispatcher getInstance() {
		return instance;
	}

	@Override
	public boolean isPasreForm() {
		return true;
	}

}
