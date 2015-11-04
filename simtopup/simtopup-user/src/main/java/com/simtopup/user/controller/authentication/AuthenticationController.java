package com.simtopup.user.controller.authentication;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.entity.Session;
import com.serverlite.core.service.SessionService;
import com.serverlite.core.utils.TokenUtil;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.entity.User;
import com.simtopup.user.service.LoginService;

public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	private SessionService sessionService;
	private LoginService loginService;
	
	public AuthenticationController( 
			SessionService sessionService ,
			LoginService loginService) {
		this.sessionService=sessionService;
		this.loginService=loginService;
	}

	public void login( Request request , Response response ){
		
		User user = loginService.login(request.getUser().getUserName(), request.getUser().getPassword(), response);
		
		if( user!=null ){
			String token = TokenUtil.createRandomToken();
			Session session = new Session();
			session.setToken(token);
			session.setUserId(user.getUserId());
			session.setUserName(user.getUserName());
			session.setRoles(Arrays.asList(user.getRoles().split(",")));
			sessionService.createSession(session);
			
			logger.info("Session created:"+session);
			
			response.setToken(token);
		}
		
	
		
	}
	
	public void logout( Request request , Response response ){
		response.setToken("logout");
	}
}
