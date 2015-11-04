package com.simtopup.user.dao;

import com.simtopup.user.entity.ApiConfig;

public interface ApiConfigDao {
	
	ApiConfig findApiConfigById( Integer id );
	
}
