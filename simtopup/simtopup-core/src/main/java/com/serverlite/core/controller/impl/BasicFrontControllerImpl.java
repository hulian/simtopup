package com.serverlite.core.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.controller.Dispatcher;
import com.serverlite.core.controller.FrontController;

public class BasicFrontControllerImpl implements FrontController{
	
	private static final Logger logger = LoggerFactory.getLogger(BasicFrontControllerImpl.class);
	
	private Dispatcher dispatcher;
	
	
	public BasicFrontControllerImpl( Dispatcher dispatcher ) {
		this.dispatcher=dispatcher;
	}

	@Override
	public void dispatchRequest(ServerLiteExchange exchange) {
		try {
			dispatcher.dispatch(exchange);
		} catch (Throwable e) {
			logger.error("SERVER ERROR",e);
		}
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public String getPath() {
		return dispatcher.getPath();
	}

	@Override
	public boolean isPasreFormr() {
		return dispatcher.isPasreForm();
	}

}
