package com.simtopup.user.service.impl;

import com.serverlite.core.constant.Code;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.utils.DigestUtil;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.dao.UserDao;
import com.simtopup.user.entity.User;
import com.simtopup.user.service.LoginService;

public class LoginServiceImpl implements LoginService{
	
	private UserDao userDao;
	private TransactionManager transactionManager;
	
	public LoginServiceImpl( 
			TransactionManager transactionManager,
			UserDao userDao) {
		this.transactionManager=transactionManager;
		this.userDao=userDao;
	}

	@Override
	public User login(  String userName,  String password ,  Response response ) {
		
		return transactionManager.doInTransaction( ()->{
				User user = userDao.findUserByUserName(userName);
				if( user==null ){
					response.setCode(Code.ERROR);
					response.setMessage("User not existed!");
					return null;
				}
				
				if(!user.getPassword().equals(DigestUtil.sha256_base64(password))){
					response.setCode(Code.ERROR);
					response.setMessage("Password error!");
					return null;
				}
				
				return user;
		});
		
	}

}
