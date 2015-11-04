package com.serverlite.core.transaction;

import java.sql.Connection;

public interface TransactionManager {

	Connection getConnection();
	void start();
	void commit();
	void rollback();
	void stop();
	<T> T doInTransaction( Runable<T> runable );
}
