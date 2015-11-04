package com.simtopup.user.test;

import com.serverlite.core.ServerLiteApplication;
import com.simtopup.user.Configuration;
import com.simtopup.user.controller.ControllerDispatcher;
import com.simtopup.user.controller.PaymentNotifictionDispatcher;

public class ApplicationTest {
	

	public static void main(String[] args) {
		
		DatabaseInitTest databaseInitTest = new DatabaseInitTest();
		databaseInitTest.init();
		
		ServerLiteApplication
		.builder()
		.path("classpath:/config.properties")
		.withDataSource(Configuration.DATA_SOURCE_SESSION)
		.withDataSource(Configuration.DATA_SOURCE_USER)
		.withDispatcher(ControllerDispatcher.getInstance())
		.withDispatcher(PaymentNotifictionDispatcher.getInstance())
		.build().run();
	}
}
