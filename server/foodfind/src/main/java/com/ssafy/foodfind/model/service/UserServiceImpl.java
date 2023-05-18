package com.ssafy.foodfind.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.foodfind.model.dao.UserDao;
import com.ssafy.foodfind.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	
	private static UserServiceImpl instance = new UserServiceImpl();
	
	private UserServiceImpl() {}
	
	public static UserServiceImpl getInstance() {
		return instance;
	}
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User selectUser(String userId) {
		User user = userDao.select(userId);
		if(user != null) {
			return user;
		}else {
			return null;
		}
	}

}
