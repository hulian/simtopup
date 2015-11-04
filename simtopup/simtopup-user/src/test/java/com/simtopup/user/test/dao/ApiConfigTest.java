package com.simtopup.user.test.dao;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.ApiConfigDao;
import com.simtopup.user.dao.impl.DaoFactory;
import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.test.DatabaseInitTest;

@Test(groups="dao")
public class ApiConfigTest {

	private TransactionManager transactionManager ;
	private ApiConfigDao apiConfigDao;
	
	@BeforeTest
	public void init(){
		 transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
		 apiConfigDao = DaoFactory.getInstance().getApiConfigDao();
	}
	
	@Test
	public void testApiConfigDao(){
		
		ApiConfig apiConfig =transactionManager.doInTransaction(()->{
			return apiConfigDao.findApiConfigById(DatabaseInitTest.TEST_USER_ID);
		});
		
		Assert.assertNotNull(apiConfig);
	}
}
