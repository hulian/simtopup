package com.simtopup.user.service;

import com.simtopup.user.boundary.Response;
import com.simtopup.user.entity.User;

public interface LoginService {
	User login( String userName , String password , final Response response  );
}
