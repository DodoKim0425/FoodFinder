package com.ssafy.foodfind.model.dto;


public class User {
    private String userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String userType;
    
    
	public User() {
		super();
	}
    
	public User(String userId, String username, String password, String phoneNumber, String userType) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.userType = userType;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", userType=" + userType + "]";
	}

}
