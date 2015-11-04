package com.simtopup.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.constant.Code;
import com.serverlite.core.transaction.TransactionManager;
import com.serverlite.core.utils.DigestUtil;
import com.simtopup.user.Configuration;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.dao.UserDao;
import com.simtopup.user.dao.WalletDao;
import com.simtopup.user.entity.User;
import com.simtopup.user.service.RegisterService;

public class RegisterServiceImpl implements RegisterService{
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	private TransactionManager transactionManager;
	
	private UserDao userDao;
	
	private WalletDao walletDao;

	public RegisterServiceImpl(TransactionManager transactionManager,UserDao userDao,WalletDao walletDao) {
		this.userDao = userDao;
		this.walletDao=walletDao;
		this.transactionManager=transactionManager;
	}

	@Override
	public User register( User user , Response response ) {
		
		return transactionManager.doInTransaction(()->{
				//检查用户是否存在
				if(isUserExisted(user.getUserName())){
					response.setCode(Code.USER_EXISTED);
					response.setMessage("User:"+user.getUserName()+" existed!");
					return null;
				}
				
				//加密密码
				user.setPassword(DigestUtil.sha256_base64(user.getPassword()));
				
				//分配权限
				user.setRoles("user");
				
				//分配分区
				user.setPartitionId(Configuration.PARTITION_ID);
				
				//创建会员
				User userCreated = userDao.createUser(user);
				
				//创建钱包
				walletDao.createWallet(user.getUserId());
				
				logger.trace("User:"+user+" create success!");
				
				return userCreated;
		});
		
	}

	private boolean isUserExisted(String userName) {
		User user = userDao.findUserByUserName(userName);
		if( user!=null ){
			return true;
		}
		return false;
	}

	public WalletDao getWalletDao() {
		return walletDao;
	}

	public void setWalletDao(WalletDao walletDao) {
		this.walletDao = walletDao;
	}

}
