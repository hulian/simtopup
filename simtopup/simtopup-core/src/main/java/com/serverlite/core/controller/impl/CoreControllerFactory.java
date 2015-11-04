package com.serverlite.core.controller.impl;

import java.util.ArrayList;
import java.util.List;

import com.serverlite.core.controller.Dispatcher;
import com.serverlite.core.controller.FrontController;

public class CoreControllerFactory{
	
	private static final CoreControllerFactory coreControllerFactory = new CoreControllerFactory();
	
	private List<FrontController> frontControllers = new ArrayList<>();
	
	private CoreControllerFactory() {
		
	}
	
	public static CoreControllerFactory getInstance() {
		return coreControllerFactory;
	}
	
	public void addFrontController( Dispatcher dispatcher ){
		if(dispatcher==null){
			throw new RuntimeException("dispatcher is null");
		}
		frontControllers.add(new BasicFrontControllerImpl(dispatcher));
	}

	public List<FrontController> getFrontControllers() {
		return frontControllers;
	}

	public void setFrontControllers(List<FrontController> frontControllers) {
		this.frontControllers = frontControllers;
	}
	
}
