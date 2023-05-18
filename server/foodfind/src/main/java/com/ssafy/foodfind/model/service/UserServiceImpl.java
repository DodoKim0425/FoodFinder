package com.ssafy.foodfind.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.foodfind.model.dao.UserDao;
import com.ssafy.foodfind.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private static UserServiceImpl instance = new UserServiceImpl();
	
	private UserServiceImpl() {}
	
	public static UserServiceImpl getInstance() {
		return instance;
	}
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User selectUser(String phoneNumber) {
		User user = userDao.select(phoneNumber);
		if(user != null) {
			return user;
		}else {
			return null;
		}
	}

    @Override
    public User login(String phoneNumber, String pass) {
        User user = userDao.select(phoneNumber);
        if (user != null && user.getPassword().equals(pass)) {
            return user;
        } else {
            return null;
        }
    }
	
	@Override
	public Boolean checkPhoneNumber(String phoneNumber) {
		User user = userDao.checkPhoneNumber(phoneNumber);
		if(user !=null) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public Boolean insert(User user) {
		try {
			boolean isExist=checkPhoneNumber(user.getPhoneNumber());
			if(isExist) {//전화번호 중복
				return false;
			}else {
				int res=userDao.insert(user);
				if(res==1) {
					return true;
				}else {
					return false;
				}
			}

		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Boolean update(User user) {
		try {
			int res = userDao.update(user);
			if(res==1) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}

	}
}
