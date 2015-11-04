package com.simtopup.user.controller.register;

import org.apache.commons.lang.StringUtils;

import com.google.common.cache.Cache;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.service.RegisterService;

public class RegisterController{
	
	private RegisterService registerService;
	private Cache<String, String> kaptchaCache;
	
	public RegisterController(RegisterService registerService , Cache<String, String> kaptchaCache) {
		this.registerService=registerService;
		this.kaptchaCache=kaptchaCache;
	}

	public void regist(Request request,Response response) {
		
		//检查验证码
		if( request.getKaptcha() == null || StringUtils.isEmpty(request.getKaptcha().getKaptchaKey()) ){
			response.setCode(-1);
			response.setMessage("Kaptcha key is empty!");
			return;
		}
		String checkCode = kaptchaCache.getIfPresent(request.getKaptcha().getKaptchaKey());
		if( checkCode == null ){
			response.setCode(-1);
			response.setMessage("Kaptcha value is empty!");
			return;
		}
		if( !checkCode.equals(request.getKaptcha().getKaptchaValue())){
			response.setCode(-1);
			response.setMessage("Kaptcha value is not right!");
			return;
		}
		kaptchaCache.invalidate(request.getKaptcha().getKaptchaKey());
		
		//注册会员
		registerService.register(request.getUser(),response);
		
		return;
	}

}
