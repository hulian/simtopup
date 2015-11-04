package com.serverlite.core.service;

import com.serverlite.core.entity.Session;

public interface SessionService {

	Session createSession( Session session );
	Session findSessionByToken(String token);
}
