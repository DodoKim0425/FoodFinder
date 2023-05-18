package com.ssafy.foodfind.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.foodfind.model.dto.User;

public interface UserService {
	public User selectUser(String phoneNumber);
	public Boolean checkPhoneNumber(String phoneNumber);
	public Boolean insert(User user);
	public User login(String phoneNumber, String pass);
	public Boolean update(User user);
}
