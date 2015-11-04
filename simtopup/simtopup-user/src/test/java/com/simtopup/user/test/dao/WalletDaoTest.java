package com.simtopup.user.test.dao;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.WalletDao;
import com.simtopup.user.dao.impl.DaoFactory;

@Test(groups="dao")
public class WalletDaoTest {
	
	private TransactionManager transactionManager;
	private WalletDao walletDao;
	
	@BeforeTest
	public void init(){
		 transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
		 walletDao = DaoFactory.getInstance().getWalletDao();
	}
	
	@Test(threadPoolSize=10,invocationCount=10,timeOut=3000)
	public void test(){
		
		transactionManager.doInTransaction(()->{
				walletDao.increaseBalance(100001, new BigDecimal(1));
				return null;
		});
		
		transactionManager.doInTransaction(()->{
				walletDao.decreaseBalance(100001, new BigDecimal(1));
				return null;
		});
		
		transactionManager.doInTransaction(()->{
				BigDecimal balance = walletDao.findBalanceByUserId(100001);
				System.out.println("Current balance:"+balance);
				return null;
		});
	}
	
	@Test(dependsOnMethods="test")
	public void test2(){
		
		transactionManager.doInTransaction(()->{
				BigDecimal balance = walletDao.findBalanceByUserId(100001);
				Assert.assertEquals(new BigDecimal("0.00"), balance);
				return null;
		});
		
	}
	
}
