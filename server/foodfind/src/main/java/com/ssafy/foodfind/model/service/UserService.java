package com.ssafy.foodfind.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.foodfind.model.dto.User;

public interface UserService {
	public User selectUser(String userId);
}
