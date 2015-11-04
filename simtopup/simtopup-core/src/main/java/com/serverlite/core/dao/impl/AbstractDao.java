package com.serverlite.core.dao.impl;

import org.apache.commons.dbutils.QueryRunner;

import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.transaction.impl.QueryRunnerFactory;

public abstract class AbstractDao {

	protected QueryRunner queryRunner = QueryRunnerFactory.getInstance().getQueryRunner();
	
	protected  TransactionManager transactionManager;
}
