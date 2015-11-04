package com.simtopup.user.controller;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.cache.CacheFactory;
import com.serverlite.core.constant.Code;
import com.serverlite.core.controller.Dispatcher;
import com.serverlite.core.controller.impl.ServerLiteExchange;
import com.serverlite.core.service.RoleService;
import com.serverlite.core.service.impl.CoreServiceFactory;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.controller.authentication.AuthenticationController;
import com.simtopup.user.controller.balance.BalanceController;
import com.simtopup.user.controller.kaptcha.KaptchaController;
import com.simtopup.user.controller.register.RegisterController;
import com.simtopup.user.controller.topup.TopUpController;
import com.simtopup.user.service.impl.ServiceFactory;

public class ControllerDispatcher implements Dispatcher {
	
	private static final ControllerDispatcher CONTROLLER_DISPATCHER = new ControllerDispatcher();
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerDispatcher.class);
	
	public static final int CREATE_KAPTCHA = 1;
	public static final int REISGER = 2;
	public static final int LOGIN = 3;
	public static final int LOGOUT = 4;
	public static final int FIND_BALANCE = 5;
	
	public static final int CREATE_TOPUP = 10;
	
	
	private KaptchaController kaptchaController;
	private RegisterController registerController;
	private AuthenticationController authenticationController;
	private BalanceController balanceController;
	private TopUpController topUpController;
	
	private SecurityFilter securityFilter;
	
	private ControllerDispatcher() {
		
		CoreServiceFactory coreServiceFactory = CoreServiceFactory.getInstrance();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		
		//创建控制器
		kaptchaController = new KaptchaController(CacheFactory.getInstrance().getKaptchaCache());
		registerController=new RegisterController(serviceFactory.getRegisterService(), CacheFactory.getInstrance().getKaptchaCache());
		authenticationController = new AuthenticationController( coreServiceFactory.getSessionService()	, serviceFactory.getLoginService());
		balanceController=new BalanceController(serviceFactory.getBalanceService());
		
		topUpController = new TopUpController(
				serviceFactory.getApiConfigService(),
				serviceFactory.getTopUpOrderService(), 
				serviceFactory.getPaypalService() ,
				serviceFactory.getAlipayService() ,
				serviceFactory.getThaiTopUpService());
		
		//创建过滤器
		securityFilter = new SecurityFilter(coreServiceFactory.getRoleService(),coreServiceFactory.getSessionService());
		
		//设置控制器访问权限
		RoleService roleService = coreServiceFactory.getRoleService();
		roleService.rolesAllowed(FIND_BALANCE, Arrays.asList("user"));
		
	}
	
	@Override
	public void dispatch(ServerLiteExchange exchange) {
		
		//过滤器，处理权限检查
		Request request = exchange.readJsonObject(Request.class);
		Response response = new Response();
		
		//会话，权限过滤
		if(!securityFilter.doFilter(exchange.getToken(), request, response)){
			exchange.writeJsonObject(response);
			return;
		}
		
		try {
			//请求派发
			switch (request.getEventId()) {
			//获取验证码
			case CREATE_KAPTCHA:
				kaptchaController.createKaptcha(request,response);
				break;
			//注册
			case REISGER:
				registerController.regist(request, response);
				break;
			//登录
			case LOGIN:
				authenticationController.login(request, response);
				break;
			//注销
			case LOGOUT:
				authenticationController.logout(request, response);
				break;
			//查询余额
			case FIND_BALANCE:
				balanceController.findBalanceByUserId(request, response);
				break;		
			case CREATE_TOPUP:
				topUpController.createTopUp(request, response);
				break;	
			default:
				response.setCode(Code.ERROR);
				response.setMessage("Cannot find this event. eventId:"+request.getEventId());
				break;
			}
		} catch (Exception e) {
			response.setCode(Code.ERROR);
			response.setMessage("Server error!");
			logger.error("Server error",e);
		}
		
		//返回token
		if(StringUtils.isNotEmpty(response.getToken())){
			exchange.setToken(response.getToken());
		}
		exchange.writeJsonObject(response);
		
		return;
	}

	public KaptchaController getKaptchaController() {
		return kaptchaController;
	}

	public void setKaptchaController(KaptchaController kaptchaController) {
		this.kaptchaController = kaptchaController;
	}

	public RegisterController getRegisterController() {
		return registerController;
	}

	public void setRegisterController(RegisterController registerController) {
		this.registerController = registerController;
	}

	public AuthenticationController getAuthenticationController() {
		return authenticationController;
	}

	public void setAuthenticationController(
			AuthenticationController authenticationController) {
		this.authenticationController = authenticationController;
	}

	public BalanceController getBalanceController() {
		return balanceController;
	}

	public void setBalanceController(BalanceController balanceController) {
		this.balanceController = balanceController;
	}

	public static ControllerDispatcher getInstance() {
		return CONTROLLER_DISPATCHER;
	}

	public TopUpController getTopUpController() {
		return topUpController;
	}

	public void setTopUpController(TopUpController topUpController) {
		this.topUpController = topUpController;
	}

	@Override
	public String getPath() {
		return "/uc";
	}

	@Override
	public boolean isPasreForm() {
		return false;
	}
	
}
