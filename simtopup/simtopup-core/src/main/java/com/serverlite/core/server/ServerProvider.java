package com.serverlite.core.server;

import java.util.List;
import java.util.Properties;

import com.serverlite.core.controller.FrontController;

public interface ServerProvider {

	void configServer(Properties configuration ,List<FrontController> frontControllers );
	void start();
	void stop();
}
