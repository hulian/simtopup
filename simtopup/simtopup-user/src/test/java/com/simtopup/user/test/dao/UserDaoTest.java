package com.simtopup.user.test.dao;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.UserDao;
import com.simtopup.user.dao.impl.DaoFactory;
import com.simtopup.user.entity.User;

@Test(groups="dao")
public class UserDaoTest {
	
	private TransactionManager transactionManager ;
	private UserDao userDao;
	
	@BeforeTest
	public void init(){
		 transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
		 userDao = DaoFactory.getInstance().getUserDao();
	}
	
	@Test
	public void test(){
		
		transactionManager.doInTransaction(()->{
				User user = new User();
				user.setUserName("test");
				user.setPassword("111111");
				user.setRoles("role");
				user.setPartitionId(Configuration.PARTITION_ID);
				userDao.createUser(user);
				return user;
		});
		
		User userf=transactionManager.doInTransaction(()->{
				User user = userDao.findUserByUserName("test");
				return user;
		});
		
		Assert.assertNotNull(userf);
		
	}
	
}
