package com.simtopup.user.test;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.testng.annotations.BeforeSuite;
import com.serverlite.core.ServerLiteApplication;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.serverlite.core.utils.SqlRunnerUtil;
import com.simtopup.user.Configuration;

public class DatabaseInitTest {
	
	public static final int TEST_USER_ID=100001;
	public static final String TEST_USER_NAME="test001";
	public static final String TEST_THAI_AIS_NUMBER="0800000001";
	public static final String TEST_THAI_DTAC_NUMBER="0800000002";
	public static final String TEST_THAI_TRUE_NUMBER="0800000003";
	
	@BeforeSuite
	public void  init(){
		System.out.println("----init database---");
		ServerLiteApplication.builder().path("classpath:/config.properties").withDataSource(Configuration.DATA_SOURCE_SESSION).withDataSource(Configuration.DATA_SOURCE_USER).build();
		TransactionManager transactionManagerSession = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_SESSION);
		transactionManagerSession.doInTransaction(()->{
			try {
				SqlRunnerUtil sqlRunnerDataCenter = new SqlRunnerUtil( transactionManagerSession.getConnection(),
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
		
		TransactionManager transactionManagerUser = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
		transactionManagerUser.doInTransaction(()->{
			try {
				SqlRunnerUtil sqlRunnerDataCenter = new SqlRunnerUtil( transactionManagerUser.getConnection(),
												new PrintWriter(System.out), 
												new PrintWriter(System.err), 
												false ,
												true);
				sqlRunnerDataCenter.runScript(new InputStreamReader(DatabaseInitTest.class.getClassLoader().getResourceAsStream("user.sql")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
}
