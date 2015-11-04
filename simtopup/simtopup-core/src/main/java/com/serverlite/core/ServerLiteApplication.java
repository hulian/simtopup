package com.serverlite.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import com.serverlite.core.controller.Dispatcher;
import com.serverlite.core.controller.impl.CoreControllerFactory;
import com.serverlite.core.server.ServerProviderManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.serverlite.core.utils.PropertiesUtil;

public class ServerLiteApplication {
	
	

	public void run(){
		ServerProviderManager.getInstance().start();
	}
	
	
	public static class Builder{
		
		private Properties configuration;
		
		private List<Dispatcher> dispatcheres=new ArrayList<>();
		
		public Builder  path( String path ){
			configuration=PropertiesUtil.loadProperties(path);
			return this;
		}
		
		public Builder  withDispatcher( Dispatcher dispatcher ){
			dispatcheres.add(dispatcher);
			return this;
		}
		
		public Builder  withDataSource( String name ){
			DbUtils.loadDriver(configuration.getProperty(name+".jdbc.driver"));
			BoneCPConfig boneCPConfigDataCenter = new BoneCPConfig();
			boneCPConfigDataCenter.setJdbcUrl(configuration.getProperty(name+".jdbc.url"));
			boneCPConfigDataCenter.setUsername(configuration.getProperty(name+".jdbc.username"));
			boneCPConfigDataCenter.setPassword(configuration.getProperty(name+".jdbc.password"));
			boneCPConfigDataCenter.setIdleConnectionTestPeriodInMinutes(5);
			boneCPConfigDataCenter.setIdleMaxAgeInMinutes(10);
			boneCPConfigDataCenter.setMaxConnectionsPerPartition(Integer.parseInt(configuration.getProperty(name+".jdbc.poolsize")));
			boneCPConfigDataCenter.setMinConnectionsPerPartition(10);
			boneCPConfigDataCenter.setPartitionCount(3);
			boneCPConfigDataCenter.setAcquireIncrement(5);
			boneCPConfigDataCenter.setStatementsCacheSize(100);
			boneCPConfigDataCenter.setDisableConnectionTracking(true);
			DataSource dataSource = new BoneCPDataSource(boneCPConfigDataCenter);
			TransactionManagerFactory.getInstance().addDataSource(name, dataSource);
			return this;
		}
		
		public ServerLiteApplication build(){
			
			//配置server
			for( Dispatcher dispatcher : dispatcheres ){
				CoreControllerFactory.getInstance().addFrontController(dispatcher);
			}
			ServerProviderManager.getInstance().configServer(configuration, CoreControllerFactory.getInstance().getFrontControllers());
			
			ServerLiteApplication serverLiteApplication = new ServerLiteApplication();
			
			return serverLiteApplication;
		}
		
	}
	

	public static Builder builder(){
		return new Builder();
	}

}
