package com.simtopup.user.test.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.TransactionManagerFactory;
import com.simtopup.user.Configuration;
import com.simtopup.user.dao.TopUpOrderDao;
import com.simtopup.user.dao.impl.DaoFactory;
import com.simtopup.user.entity.TopUpOrder;

@Test(groups="dao")
public class TopUpOrderDaoTest {
		
		private TransactionManager transactionManager ;
		private TopUpOrderDao topUpOrderDao;
		
		@BeforeTest
		public void init(){
			 transactionManager = TransactionManagerFactory.getInstance().getTransactionManager(Configuration.DATA_SOURCE_USER);
			 topUpOrderDao = DaoFactory.getInstance().getTopUpOrderDao();
		}
		
		@Test
		public void testTopUpDaoDao(){
			
			final TopUpOrder topUpOrder = new TopUpOrder();;
			transactionManager.doInTransaction(()->{
				topUpOrder.setTopUpAmount(new BigDecimal(1));
				topUpOrder.setCountryCode(66);
				topUpOrder.setCreateTime(new Date());
				topUpOrder.setCurrency("thai");
				topUpOrder.setExchangeRate(new BigDecimal(2));
				topUpOrder.setMobileNumber("123456");
				topUpOrder.setNetworkOperator("true");
				topUpOrderDao.createTopUpOrder(topUpOrder);
				return topUpOrder;
			});
			
			Assert.assertNotNull(topUpOrder.getId());
			
			List<TopUpOrder>  topUpOrdersf = transactionManager.doInTransaction(()->{
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				Date startTime = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, 2);
				Date endTime = calendar.getTime();
				List<TopUpOrder>  topUpOrders = topUpOrderDao.findTopUpOrderByCreatetime(startTime, endTime, 0, 10);
				return topUpOrders;
			});
			
			Assert.assertEquals(topUpOrdersf.size(), 1);
			Assert.assertEquals(topUpOrdersf.get(0).getId(),topUpOrder.getId());
		}
		
		@Test
		public void testTopUpDaoDaoUpdateStatus(){
			
			final TopUpOrder topUpOrder = new TopUpOrder();;
			transactionManager.doInTransaction(()->{
				topUpOrder.setTopUpAmount(new BigDecimal(1));
				topUpOrder.setCountryCode(66);
				topUpOrder.setCreateTime(new Date());
				topUpOrder.setCurrency("thai");
				topUpOrder.setExchangeRate(new BigDecimal(2));
				topUpOrder.setMobileNumber("123456");
				topUpOrder.setNetworkOperator("true");
				topUpOrder.setStatus(TopUpOrder.UNPAID);
				topUpOrderDao.createTopUpOrder(topUpOrder);
				return topUpOrder;
			});
			
			Assert.assertNotNull(topUpOrder.getId());
			
			TopUpOrder topUpOrderf = transactionManager.doInTransaction(()->{
				topUpOrderDao.updateOrderStatus(topUpOrder.getId(), TopUpOrder.PAID);
				return topUpOrderDao.findTopUpOrderById(topUpOrder.getId(), true);
			});
			
			Assert.assertNotNull(topUpOrderf.getId());
			Assert.assertEquals(topUpOrderf.getId(),topUpOrder.getId());
			Assert.assertEquals(topUpOrderf.getStatus(),Integer.valueOf(TopUpOrder.PAID));
			
		}
}
