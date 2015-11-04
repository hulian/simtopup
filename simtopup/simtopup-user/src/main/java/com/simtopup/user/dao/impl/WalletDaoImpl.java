package com.simtopup.user.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import com.serverlite.core.dao.impl.AbstractDao;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.WalletDao;
import com.simtopup.user.entity.Wallet;

public class WalletDaoImpl extends AbstractDao implements WalletDao{
	
	@SuppressWarnings("unused")
	private ResultSetHandler<List<Wallet>> resultSetHandler = (ResultSet resultSet) -> {
			List<Wallet> list = new ArrayList<Wallet>();
			if(resultSet.next()){
				Wallet wallet = new Wallet();
				wallet.setUserId(resultSet.getInt("userId"));
				wallet.setBalance(resultSet.getBigDecimal("balance"));
				list.add(wallet);
			}
			return list;
	};
	
	private ResultSetHandler<List<BigDecimal>> balanceResultSetHandler = (ResultSet resultSet) -> {
			List<BigDecimal> list = new ArrayList<BigDecimal>();
			while(resultSet.next()){
				BigDecimal balance=resultSet.getBigDecimal("balance");
				list.add(balance);
			}
			return list;
	};

	public WalletDaoImpl(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public void increaseBalance(Integer userId , BigDecimal amount) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE Wallet SET balance=balance+? WHERE userId=? ",
					amount ,
					userId );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void decreaseBalance(Integer userId , BigDecimal amount) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"UPDATE Wallet SET balance=balance-? WHERE userId=? ",
					amount ,
					userId );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BigDecimal findBalanceByUserId(Integer userId) {
		try {
			BigDecimal balance = queryRunner.query(transactionManager.getConnection(),
					"SELECT balance FROM Wallet WHERE userId=?",
					balanceResultSetHandler,userId).stream().findFirst().orElse(new BigDecimal("0.00"));
			return balance;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createWallet(Integer userId) {
		try {
			queryRunner.update(transactionManager.getConnection(),
					"INSERT INTO Wallet (userId,balance) VALUES (?,?) ",
					userId ,
					new BigDecimal("0.00") );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
