package com.simtopup.user.controller;

import org.apache.commons.lang.StringUtils;

import com.serverlite.core.entity.Session;
import com.serverlite.core.service.RoleService;
import com.serverlite.core.service.SessionService;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;

public class SecurityFilter {

	private RoleService roleService;
	
	private SessionService sessionService;
	
	public SecurityFilter(  RoleService roleService,
							SessionService sessionService) {
		this.roleService=roleService;
		this.sessionService=sessionService;
	}
	
	public boolean doFilter( String token , Request request ,Response response) {

		// 验证请求权限
		// 如果未设置权限，不做权限验证
		if (roleService.isUserAllowed(request.getEventId())) {
			return true;
		}
		
		if(StringUtils.isNotEmpty(token)){
			Session session = sessionService.findSessionByToken(token);
			request.setSession(session);
		}

		// 验证是否有有效会话
		if( request.getSession()==null ){
			response.setCode(401);
			response.setMessage("Cannot find session!");
			return false;
		}
		

		// 验证是否有权限访问
		if (roleService.isUserAllowed(request.getEventId(),request.getSession().getRoles())) {
			return true;
		}

		response.setCode(403);
		response.setMessage("Not allowed! eventId:"+request.getEventId()+" roles:"+request.getSession().getRoles());
		return false;
	}

}
