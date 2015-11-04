package com.simtopup.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.serverlite.core.dao.impl.AbstractDao;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.UserDao;
import com.simtopup.user.entity.User;

public class UserDaoImpl extends AbstractDao implements UserDao{
	
	
	private ResultSetHandler<List<User>> resultSetHandler = (ResultSet resultSet)-> {
			List<User> users = new ArrayList<User>();
			while(resultSet.next()){
				User user = new User();
				user.setUserId(resultSet.getInt("userId"));
				user.setUserName(resultSet.getString("userName"));
				user.setPassword(resultSet.getString("password"));
				user.setRoles(resultSet.getString("roles"));
				user.setCreateTime(resultSet.getDate("createTime"));
				users.add(user);
			}
			return users;
	};
	
	public UserDaoImpl( TransactionManager transactionManager) {
		this.transactionManager=transactionManager;
	}

	@Override
	public User createUser(User user) {
		try {
			Number id = queryRunner.insert(transactionManager.getConnection(),
					"INSERT INTO User ( partitionId , userName , password , roles ,  createTime ) VALUES (?,?,?,?,?)",new ScalarHandler<Number>()
					,user.getPartitionId(),user.getUserName(),user.getPassword(),user.getRoles(),user.getCreateTime());
			user.setUserId(id.intValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return user;
	}
	
	
	@Override
	public User findUserByUserName(String userName) {
		try {
			User user = queryRunner.query(transactionManager.getConnection(),
					"SELECT userId,userName,password,roles,createTime FROM User WHERE userName=?"
					,resultSetHandler,userName).stream().findFirst().orElse(null);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
