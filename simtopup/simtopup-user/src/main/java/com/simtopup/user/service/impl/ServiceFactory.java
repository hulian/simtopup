package com.simtopup.user.service.impl;

import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.impl.DaoFactory;
import com.simtopup.user.service.ApiConfigService;
import com.simtopup.user.service.BalanceService;
import com.simtopup.user.service.LoginService;
import com.simtopup.user.service.PaymentService;
import com.simtopup.user.service.RegisterService;
import com.simtopup.user.service.TopUpOrderService;
import com.simtopup.user.service.TopUpService;
import com.simtopup.user.service.impl.alipay.AlipayTrustServiceImpl;
import com.simtopup.user.service.impl.paypal.PaypalServiceImpl;
import com.simtopup.user.service.impl.thaitopup.ThaiTopUpServiceImpl;

public class ServiceFactory {
	
	private static final ServiceFactory serviceFactory = new ServiceFactory();
	
	private BalanceService balanceService;
	
	private LoginService loginService;

	private RegisterService registerService;
	
	private ApiConfigService apiConfigService;
	
	private TopUpService thaiTopUpService;
	
	private TopUpOrderService topUpOrderService;
	
	private PaymentService paypalService;
	
	private PaymentService alipayService;
	
	private ServiceFactory() {
		DaoFactory daoFactory = DaoFactory.getInstance();
		TransactionManager transactionManagerUser = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
		balanceService = new BalanceServiceImpl(transactionManagerUser, daoFactory.getWalletDao());
		loginService = new LoginServiceImpl(transactionManagerUser, daoFactory.getUserDao());
		registerService = new RegisterServiceImpl(transactionManagerUser, daoFactory.getUserDao(),daoFactory.getWalletDao());
		apiConfigService = new ApiConfigServiceImpl(transactionManagerUser, daoFactory.getApiConfigDao());
		thaiTopUpService = new ThaiTopUpServiceImpl(transactionManagerUser,apiConfigService,daoFactory.getTopUpOrderDao());
		topUpOrderService = new TopUpOrderServiceImpl(transactionManagerUser, daoFactory.getTopUpOrderDao());
		paypalService = new PaypalServiceImpl();
		alipayService = new AlipayTrustServiceImpl();
	}

	public static ServiceFactory getInstance() {
		return serviceFactory;
	}
	

	public BalanceService getBalanceService() {
		return balanceService;
	}

	public void setBalanceService(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public ApiConfigService getApiConfigService() {
		return apiConfigService;
	}

	public void setApiConfigService(ApiConfigService apiConfigService) {
		this.apiConfigService = apiConfigService;
	}

	public TopUpService getThaiTopUpService() {
		return thaiTopUpService;
	}

	public void setThaiTopUpService(TopUpService thaiTopUpService) {
		this.thaiTopUpService = thaiTopUpService;
	}

	public TopUpOrderService getTopUpOrderService() {
		return topUpOrderService;
	}

	public void setTopUpOrderService(TopUpOrderService topUpOrderService) {
		this.topUpOrderService = topUpOrderService;
	}

	public PaymentService getPaypalService() {
		return paypalService;
	}

	public void setPaypalService(PaymentService paypalService) {
		this.paypalService = paypalService;
	}

	public PaymentService getAlipayService() {
		return alipayService;
	}

	public void setAlipayService(PaymentService alipayService) {
		this.alipayService = alipayService;
	}
	
}
