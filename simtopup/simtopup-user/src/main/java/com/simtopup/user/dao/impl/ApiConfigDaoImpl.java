package com.simtopup.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import com.serverlite.core.dao.impl.AbstractDao;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.user.dao.ApiConfigDao;
import com.simtopup.user.entity.ApiConfig;

public class ApiConfigDaoImpl extends AbstractDao implements ApiConfigDao{
	
	private ResultSetHandler<List<ApiConfig>> resultSetHandler = (ResultSet resultSet)-> {
		List<ApiConfig> apiConfigs = new ArrayList<ApiConfig>();
		while(resultSet.next()){
			ApiConfig apiConfig = new ApiConfig();
			apiConfig.setApiAddress(resultSet.getString("apiAddress"));
			apiConfig.setId(resultSet.getInt("id"));
			apiConfig.setKey1(resultSet.getString("key1"));
			apiConfig.setKey2(resultSet.getString("key2"));
			apiConfig.setKey3(resultSet.getString("key3"));
			apiConfig.setMerchant(resultSet.getString("merchant"));
			apiConfig.setName(resultSet.getString("name"));
			apiConfigs.add(apiConfig);
		}
		return apiConfigs;
	};
	
	public ApiConfigDaoImpl(TransactionManager transactionManager) {
		this.transactionManager=transactionManager;
	}
	

	@Override
	public ApiConfig findApiConfigById(Integer id) {
		try {
			ApiConfig apiConfig = queryRunner.query(transactionManager.getConnection(),
					"SELECT * FROM ApiConfig WHERE id=?"
					,resultSetHandler,id).stream().findFirst().orElse(null);
			return apiConfig;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
