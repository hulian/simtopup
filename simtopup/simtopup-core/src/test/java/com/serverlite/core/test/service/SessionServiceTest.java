package com.serverlite.core.test.service;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.serverlite.core.entity.Session;
import com.serverlite.core.service.SessionService;
import com.serverlite.core.service.impl.CoreServiceFactory;
import com.serverlite.core.utils.TokenUtil;

public class SessionServiceTest {
	
	@Test
	public void testSessionService(){
		
		CoreServiceFactory coreServiceFactory = CoreServiceFactory.getInstrance();
		
		SessionService sessionService = coreServiceFactory.getSessionService();
		
		Session session = new Session();
		String token = TokenUtil.createRandomToken();
		session.setRoles(Arrays.asList("user"));
		session.setToken(token);
		session.setStatus(1);
		sessionService.createSession(session);
		
		
		Assert.assertNotNull(sessionService.findSessionByToken(session.getToken()));
		
	}

}
