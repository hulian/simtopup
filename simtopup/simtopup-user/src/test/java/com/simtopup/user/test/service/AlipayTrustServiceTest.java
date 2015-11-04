package com.simtopup.user.test.service;

import java.math.BigDecimal;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.simtopup.user.Configuration;
import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.ApiConfigService;
import com.simtopup.user.service.PaymentService;
import com.simtopup.user.service.bean.PaymentAccountBean;
import com.simtopup.user.service.bean.PaymentOrderBean;
import com.simtopup.user.service.bean.PaymentSubmitBean;
import com.simtopup.user.service.impl.ServiceFactory;

@Test(groups = "service", dependsOnGroups = "dao")
public class AlipayTrustServiceTest {

	private PaymentService alipayTrustService;
	private ApiConfigService ApiConfigService;

	@BeforeTest
	public void init() {
		alipayTrustService = ServiceFactory.getInstance().getAlipayService();
		ApiConfigService = ServiceFactory.getInstance().getApiConfigService();
	}

	@Test
	public void testApiConfigTest() {

		TopUpOrder topUpOrder = new TopUpOrder();
		topUpOrder.setId(1);
		topUpOrder.setPaymentAmount(new BigDecimal("10"));
		
		ApiConfig apiConfig=ApiConfigService.findApiConfigById(Configuration.API_CONFIG_ALIPAY_TRUST);

		PaymentAccountBean paymentAccountBean = new PaymentAccountBean();
		paymentAccountBean.setApiAddress(apiConfig.getApiAddress());
		paymentAccountBean.setMerchantId(apiConfig.getMerchant());
		paymentAccountBean.setMerchantUserName(apiConfig.getName());
		paymentAccountBean.setMerchantKey(apiConfig.getKey1());
		
		PaymentOrderBean paymentOrderBean = new PaymentOrderBean();
		paymentOrderBean.setPaymentOrderId(topUpOrder.getPaymentOrderId());
		paymentOrderBean.setPaymentAmount(topUpOrder.getPaymentAmount());
		paymentOrderBean.setInfo(topUpOrder.getMobileNumber());
		
		PaymentSubmitBean paymentSubmitBean = alipayTrustService.createPaymentSign(paymentOrderBean, paymentAccountBean);
		
		System.out.println(paymentSubmitBean);

	}
}
