package com.serverlite.core.controller;

import com.serverlite.core.controller.impl.ServerLiteExchange;

public interface Dispatcher {
	boolean isPasreForm();
	void dispatch ( ServerLiteExchange exchange );
	String getPath();
}
