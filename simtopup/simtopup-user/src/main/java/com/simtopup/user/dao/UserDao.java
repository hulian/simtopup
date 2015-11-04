package com.simtopup.user.dao;

import com.simtopup.user.entity.User;

public interface UserDao {

	User createUser(User user);

	User findUserByUserName(String userName);
}
