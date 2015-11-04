package com.simtopup.user.test.service;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.serverlite.core.constant.Code;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.entity.User;
import com.simtopup.user.service.RegisterService;
import com.simtopup.user.service.impl.ServiceFactory;

@Test(groups="service",dependsOnGroups="dao")
public class RegisterServiceTest {
	
	private RegisterService registerService;
	
	@BeforeTest
	public void init(){
		registerService=ServiceFactory.getInstance().getRegisterService();
	}

	@Test
	public void test() {

		User user = new User();
		user.setUserName("testregist");
		user.setPassword("111111");
		Response response = new Response();
		registerService.register(user, response);
		Assert.assertEquals(Code.SUCCESS, response.getCode());
		
		registerService.register(user, response);
		Assert.assertEquals(Code.USER_EXISTED, response.getCode());
		
	}

}
