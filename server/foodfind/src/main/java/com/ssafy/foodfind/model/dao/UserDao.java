package com.ssafy.foodfind.model.dao;

import com.ssafy.foodfind.model.dto.User;

public interface UserDao {
	 User select(String userId);
	 User checkPhoneNumber(String phoneNumber);
	 int insert(User user);
	 int update(User user);
	 int updateUserToOwner(String userId);
}
