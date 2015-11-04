package com.serverlite.core.transaction.impl;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerFactory {
	
	private static QueryRunnerFactory queryRunnerSingleton = new QueryRunnerFactory();
	
	private QueryRunner queryRunner = new QueryRunner();
	
	private QueryRunnerFactory() {
	}
	
	public static QueryRunnerFactory getInstance(){
		return queryRunnerSingleton;
	}

	public QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void setQueryRunner(QueryRunner queryRunner) {
		this.queryRunner = queryRunner;
	}

}
