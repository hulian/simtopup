package com.serverlite.core.transaction.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.serverlite.core.transaction.TransactionManager;

public class TransactionManagerFactory {
	
	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	
	private Map<String, TransactionManager> transationManagers = new HashMap<String, TransactionManager>();
	
	private static TransactionManagerFactory trransactionManagerFactory = new TransactionManagerFactory();
	
	private TransactionManagerFactory() {
	}
	
	public static TransactionManagerFactory getInstance(){
		return trransactionManagerFactory;
	}
	
	public void addTransactionManager( String name ,String dataSourceName ){
		addTransactionManager(name, new TransactionManagerImpl(dataSources.get(dataSourceName)));
	}
	
	public void addTransactionManager( String name , TransactionManager transactionManager ){
		transationManagers.put(name, transactionManager);
	}
	
	public void addDataSource( String name , DataSource dataSource ){
		dataSources.put(name, dataSource);
		addTransactionManager(name, name);
	}

	public TransactionManager getTransactionManager( String name ){
		TransactionManager transactionManager = transationManagers.get(name);
		if(transactionManager==null){
			throw new RuntimeException("cannot find transactionManager "+name);
		}
		return transactionManager;
	}
}
