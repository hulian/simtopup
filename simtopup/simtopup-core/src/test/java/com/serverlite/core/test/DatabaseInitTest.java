package com.serverlite.core.test;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.testng.annotations.BeforeSuite;

import com.serverlite.core.Configuration;
import com.serverlite.core.ServerLiteApplication;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.serverlite.core.utils.SqlRunnerUtil;

public class DatabaseInitTest {
	
	@BeforeSuite
	public void  init(){
		System.out.println("----init database---");
		ServerLiteApplication.builder().path("classpath:/config.properties").withDataSource(Configuration.DATA_SOURCE_SESSION).build();
		TransactionManager transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_SESSION);
		transactionManager.doInTransaction(()->{
			try {
				SqlRunnerUtil sqlRunnerDataCenter = new SqlRunnerUtil( transactionManager.getConnection(),
												new PrintWriter(System.out), 
												new PrintWriter(System.err), 
												false ,
												true);
				sqlRunnerDataCenter.runScript(new InputStreamReader(DatabaseInitTest.class.getClassLoader().getResourceAsStream("session.sql")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		});
		
	}
	
}
