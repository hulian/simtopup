package com.simtopup.user.test.service;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.service.ApiConfigService;
import com.simtopup.user.service.impl.ServiceFactory;
import com.simtopup.user.test.DatabaseInitTest;

@Test(groups="service",dependsOnGroups="dao")
public class ApiConfigServiceTest {

	private ApiConfigService apiConfigService;
	
	@BeforeTest
	public void init(){
		apiConfigService=ServiceFactory.getInstance().getApiConfigService();
	}
	
	@Test
	public void testApiConfigTest(){
		
		ApiConfig apiConfig = apiConfigService.findApiConfigById(DatabaseInitTest.TEST_USER_ID);
		
		Assert.assertNotNull(apiConfig);
	}
}
