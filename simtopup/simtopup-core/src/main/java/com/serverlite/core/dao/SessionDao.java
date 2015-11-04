package com.serverlite.core.dao;

import com.serverlite.core.entity.Session;

public interface SessionDao {
	void createSession( Session session );
	Session findSessionByToken( String token );
}
