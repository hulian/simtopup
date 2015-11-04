package com.serverlite.core.test.dao;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.serverlite.core.Configuration;
import com.serverlite.core.dao.SessionDao;
import com.serverlite.core.dao.impl.CoreDaoFactory;
import com.serverlite.core.entity.Session;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.serverlite.core.utils.TokenUtil;

public class TransactionTest {

	@Test
	public void testTransaction(){
		
		TransactionManager transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_SESSION);
		SessionDao sessionDao = CoreDaoFactory.getInstance().getSessionDao();
		
		String token = TokenUtil.createRandomToken();
		try {
			transactionManager.doInTransaction(()->{
				Session session = new Session();
				session.setRoles(Arrays.asList("user"));
				session.setToken(token);
				session.setStatus(1);
				sessionDao.createSession(session);
				{
					throw new  RuntimeException();
				}
			});
		} catch (Exception e) {
		}
		
		Session sessionf = transactionManager.doInTransaction(()->{
			return sessionDao.findSessionByToken(token);
		});
		
		Assert.assertNull(sessionf);
		
	}
	
}
