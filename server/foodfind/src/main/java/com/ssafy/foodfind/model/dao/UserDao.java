package com.ssafy.foodfind.model.dao;

import com.ssafy.foodfind.model.dto.User;

public interface UserDao {
	 User select(String userId);
}
