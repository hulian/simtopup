package com.simtopup.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serverlite.core.dao.impl.AbstractDao;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.TopUpOrderDao;
import com.simtopup.user.entity.TopUpOrder;

public class TopUpOrderDaoImpl extends AbstractDao implements TopUpOrderDao{
	
	private static final Logger logger = LoggerFactory.getLogger(TopUpOrderDaoImpl.class);

	private ResultSetHandler<List<TopUpOrder>> resultSetHandler = (ResultSet resultSet)-> {
		List<TopUpOrder> topUpOrders = new ArrayList<TopUpOrder>();
		while(resultSet.next()){
			TopUpOrder topUpOrder = new TopUpOrder();
			topUpOrder.setTopUpAmount(resultSet.getBigDecimal("topUpAmount"));
			topUpOrder.setCountryCode(resultSet.getInt("countryCode"));
			topUpOrder.setCreateTime(resultSet.getDate("createTime"));
			topUpOrder.setCurrency(resultSet.getString("currency"));
			topUpOrder.setExchangeRate(resultSet.getBigDecimal("exchangeRate"));
			topUpOrder.setId(resultSet.getInt("id"));
			topUpOrder.setMobileNumber(resultSet.getString("mobileNumber"));
			topUpOrder.setNetworkOperator(resultSet.getString("networkOperator"));
			topUpOrder.setTopUpOrderId(resultSet.getString("topUpOrderId"));
			topUpOrder.setTopUpTransactionId(resultSet.getString("topUpTransactionId"));
			topUpOrder.setPaymentOrderId(resultSet.getString("paymentOrderId"));
			topUpOrder.setPaymentTransactionId(resultSet.getString("paymentTransactionId"));
			topUpOrder.setPayMethod(resultSet.getString("payMethod"));
			topUpOrder.setStatus(resultSet.getInt("status"));
			topUpOrders.add(topUpOrder);
		}
		return topUpOrders;
	};
	
	public TopUpOrderDaoImpl( TransactionManager transactionManager ) {
		this.transactionManager=transactionManager;
	}
	
	@Override
	public void createTopUpOrder(TopUpOrder topUpOrder) {
		try {
			Number id = queryRunner.insert(transactionManager.getConnection(),
					"INSERT INTO TopUpOrder "
					+ "( countryCode , networkOperator , mobileNumber , topUpAmount , paymentAmount , paymentOrderId, currency , exchangeRate , createTime , status ) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?)",new ScalarHandler<Number>()
					,topUpOrder.getCountryCode(),
					topUpOrder.getNetworkOperator(),
					topUpOrder.getMobileNumber(),
					topUpOrder.getTopUpAmount(),
					topUpOrder.getPaymentAmount(),
					topUpOrder.getPaymentOrderId(),
					topUpOrder.getCurrency(),
					topUpOrder.getExchangeRate(),
					topUpOrder.getCreateTime(),
					topUpOrder.getStatus());
					topUpOrder.setId(id.intValue());
		} catch (SQLException e) {
			logger.error("dao createTopUpOrder error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<TopUpOrder> findTopUpOrderByCreatetime(Date startTime, Date endTime, Integer pageIndex,
			Integer pageSize) {
		try {
			List<TopUpOrder> topUpOrders = queryRunner.query(transactionManager.getConnection(),
					"SELECT * FROM TopUpOrder WHERE createTime>=? AND createTime<=? ORDER BY createTime DESC LIMIT ?,?"
					,resultSetHandler,startTime,endTime,pageIndex*pageSize,(pageIndex+1)*pageSize);
			return topUpOrders;
		} catch (SQLException e) {
			logger.error("dao findTopUpOrderByCreatetime error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public TopUpOrder findTopUpOrderById(Integer id, boolean lock) {
		try {
			TopUpOrder topUpOrder = queryRunner.query(transactionManager.getConnection(),
					"SELECT * FROM TopUpOrder WHERE id=?"+(lock?" FOR UPDATE ":"")
					,resultSetHandler,id).stream().findFirst().orElse(null);
			return topUpOrder;
		} catch (SQLException e) {
			logger.error("dao findTopUpOrderById error",e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public TopUpOrder findTopUpOrderByPaymentOrderId(String id, boolean lock) {
		try {
			TopUpOrder topUpOrder = queryRunner.query(transactionManager.getConnection(),
					"SELECT * FROM TopUpOrder WHERE paymentOrderId=?"+(lock?" FOR UPDATE ":"")
					,resultSetHandler,id).stream().findFirst().orElse(null);
			return topUpOrder;
		} catch (SQLException e) {
			logger.error("dao findTopUpOrderById error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateOrderStatus(Integer id, int status) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET status=? WHERE id=? "
					,status,id);
		} catch (SQLException e) {
			logger.error("dao updateOrderStatus error",e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void updateTopUpOrderId(Integer id, String topUpOrderId) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET topUpOrderId=? WHERE id=? "
					,topUpOrderId,id);
		} catch (SQLException e) {
			logger.error("dao updateTopUpOrderId error",e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void updateTopUpTransactionId(Integer id, String topUpTransactionId) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET topUpTransactionId=? WHERE id=? "
					,topUpTransactionId,id);
		} catch (SQLException e) {
			logger.error("dao updateTopUpTransactionId error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updatePaymentTransactionId(Integer id, String paymentOrderId) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET paymentOrderId=? WHERE id=? "
					,paymentOrderId,id);
		} catch (SQLException e) {
			logger.error("dao updatePaymentTransactionId error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updatePaymentOrderId(Integer id, String paymentTransactionId) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET paymentTransactionId=? WHERE id=? "
					,paymentTransactionId,id);
		} catch (SQLException e) {
			logger.error("dao updatePaymentOrderId error",e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateTopUpOrderIdAndStatus(Integer id, String topUpId, int status) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE  TopUpOrder SET topUpOrderId=?,status=? WHERE id=? "
					,topUpId,status,id);
		} catch (SQLException e) {
			logger.error("dao updateTopUpOrderIdAndStatus error",e);
			throw new RuntimeException(e);
		}
	}

}
