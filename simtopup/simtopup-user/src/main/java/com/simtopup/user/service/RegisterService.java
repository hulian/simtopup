package com.simtopup.user.service;

import com.simtopup.user.boundary.Response;
import com.simtopup.user.entity.User;

public interface RegisterService {

	User register( User user , Response response);
	
}
