package com.serverlite.core.controller;

import com.serverlite.core.controller.impl.ServerLiteExchange;

public interface FrontController {

	boolean isPasreFormr();

	void dispatchRequest(ServerLiteExchange exchange);

	void setDispatcher( Dispatcher dispatcher );
	
	String getPath();
}
