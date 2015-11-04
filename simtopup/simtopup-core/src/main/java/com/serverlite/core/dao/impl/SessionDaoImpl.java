package com.serverlite.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.serverlite.core.dao.SessionDao;
import com.serverlite.core.entity.Session;
import com.serverlite.core.transaction.TransactionManager;

public class SessionDaoImpl extends AbstractDao implements SessionDao{

	private TransactionManager transactionManager;
	
	private ResultSetHandler<List<Session>> resultSetHandler = (ResultSet resultSet)-> {
		List<Session> sessions = new ArrayList<Session>();
		while(resultSet.next()){
			Session session = new Session();
			session.setToken(resultSet.getString("token"));
			session.setCreateTime(resultSet.getDate("createTime"));
			session.setLastAccessTime(resultSet.getDate("lastAccessTime"));
			session.setUserId(resultSet.getInt("userId"));
			session.setUserName(resultSet.getString("userName"));
			sessions.add(session);
		}
		return sessions;
};
	
	public SessionDaoImpl(TransactionManager transactionManager ) {
		this.transactionManager = transactionManager;
	}

	@Override
	public void createSession(Session session) {
		
		try {
			queryRunner.update(transactionManager.getConnection(),
					"INSERT INTO Session ( token , tokenKey , status , roles , userId , userName ,  lastAccessTime , createTime ) VALUES (?,?,?,?,?,?,?,?)"
					,session.getToken(),session.getTokenKey(),session.getStatus(),session.getRolesString(),
					session.getUserId(),session.getUserName(),session.getLastAccessTime(),session.getCreateTime());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public Session findSessionByToken(String token) {
		try {
			return queryRunner.query(transactionManager.getConnection(),
					"SELECT * FROM Session WHERE token=?"
					,resultSetHandler,token).stream().findFirst().orElse(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
