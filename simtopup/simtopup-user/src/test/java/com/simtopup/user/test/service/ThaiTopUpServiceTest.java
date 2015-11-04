package com.simtopup.user.test.service;

import java.math.BigDecimal;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.TopUpOrderService;
import com.simtopup.user.service.TopUpService;
import com.simtopup.user.service.impl.ServiceFactory;
import com.simtopup.user.test.DatabaseInitTest;

@Test(groups="service",dependsOnGroups="dao")
public class ThaiTopUpServiceTest {

	private TopUpOrderService topUpOrderService;
	private TopUpService thaiTopUpService;
	
	@BeforeTest
	public void init(){
		thaiTopUpService=ServiceFactory.getInstance().getThaiTopUpService();
		topUpOrderService=ServiceFactory.getInstance().getTopUpOrderService();
	}

	@Test
	public void test() {
		
		TopUpOrder topUpOrder = new TopUpOrder();
		topUpOrder.setTopUpAmount(new BigDecimal("1"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_AIS);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_AIS_NUMBER);
		topUpOrder.setStatus(TopUpOrder.PAID);
		topUpOrderService.createTopUpOrder(topUpOrder);
		thaiTopUpService.createToUpId(topUpOrder);
		thaiTopUpService.topUp(topUpOrder);
		
		topUpOrder.setTopUpAmount(new BigDecimal("1"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_DTAC);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_DTAC_NUMBER);
		topUpOrder.setStatus(TopUpOrder.PAID);
		topUpOrderService.createTopUpOrder(topUpOrder);
		thaiTopUpService.createToUpId(topUpOrder);
		thaiTopUpService.topUp(topUpOrder);
		
		topUpOrder.setTopUpAmount(new BigDecimal("1"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_TRUE);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_TRUE_NUMBER);
		topUpOrder.setStatus(TopUpOrder.PAID);
		topUpOrderService.createTopUpOrder(topUpOrder);
		thaiTopUpService.createToUpId(topUpOrder);
		thaiTopUpService.topUp(topUpOrder);
		
		topUpOrder.setTopUpAmount(new BigDecimal("1"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_TRUE);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_TRUE_NUMBER);
		topUpOrder.setStatus(TopUpOrder.TOPUPED);
		topUpOrderService.createTopUpOrder(topUpOrder);
		thaiTopUpService.createToUpId(topUpOrder);
		thaiTopUpService.topUp(topUpOrder);
		
		
		topUpOrder.setTopUpAmount(new BigDecimal("1"));
		topUpOrder.setNetworkOperator(TopUpOrder.NETWORK_OPERATOR_THAI_TRUE);
		topUpOrder.setMobileNumber(DatabaseInitTest.TEST_THAI_TRUE_NUMBER);
		topUpOrder.setStatus(TopUpOrder.UNPAID);
		topUpOrderService.createTopUpOrder(topUpOrder);
		thaiTopUpService.topUp(topUpOrder);
		
	}
}
