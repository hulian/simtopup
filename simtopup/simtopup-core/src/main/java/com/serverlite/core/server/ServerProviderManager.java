package com.serverlite.core.server;

import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.controller.FrontController;

public class ServerProviderManager implements ServerProvider{
	
	private static final Logger logger = LoggerFactory.getLogger(ServerProviderManager.class);
	
	private static ServiceLoader<ServerProvider> serverLoader = ServiceLoader.load(ServerProvider.class); 
	
	private static ServerProviderManager serverProviderManager = new ServerProviderManager();
	
	
	public static ServerProviderManager getInstance(){
		return serverProviderManager;
	}

	@Override
	public void configServer(Properties configuration,List<FrontController> frontControllers) {
		for( ServerProvider provider : serverLoader ){
			logger.info("config server:"+provider.getClass().getSimpleName());
			provider.configServer(configuration,frontControllers);
		}
	}
	
	@Override
	public void start() {
		for( ServerProvider provider : serverLoader ){
			provider.start();
		}
	}
	
	@Override
	public void stop() {
		for( ServerProvider provider : serverLoader ){
			provider.stop();
		}
	}

}
