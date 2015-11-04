package com.simtopup.user;

import com.serverlite.core.ServerLiteApplication;
import com.simtopup.user.controller.ControllerDispatcher;
import com.simtopup.user.controller.PaymentNotifictionDispatcher;

public class Application {
	

	public static void main(String[] args) {
		
		ServerLiteApplication
		.builder()
		.path("file:config/config.properties")
		.withDataSource(Configuration.DATA_SOURCE_SESSION)
		.withDataSource(Configuration.DATA_SOURCE_USER)
		.withDispatcher(ControllerDispatcher.getInstance())
		.withDispatcher(PaymentNotifictionDispatcher.getInstance())
		.build().run();
	}
}
