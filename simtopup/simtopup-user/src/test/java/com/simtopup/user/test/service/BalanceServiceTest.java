package com.simtopup.user.test.service;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.simtopup.user.service.BalanceService;
import com.simtopup.user.service.impl.ServiceFactory;
import com.simtopup.user.test.DatabaseInitTest;

@Test(groups="service",dependsOnGroups="dao")
public class BalanceServiceTest {

	private BalanceService balanceService;
	
	@BeforeTest
	public void init(){
		balanceService=ServiceFactory.getInstance().getBalanceService();
	}

	@Test
	public void test() {

		BigDecimal balance = balanceService.findBalanceByUserId(DatabaseInitTest.TEST_USER_ID);
		Assert.assertNotNull(balance);
		
	}
}
