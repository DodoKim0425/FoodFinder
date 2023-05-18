package com.ssafy.foodfind.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.User;
import com.ssafy.foodfind.model.service.UserService;


@RestController
@RequestMapping("/rest/user")
public class UserRestController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	UserService uService;
	
	@GetMapping("/info")
	public User getInfo(String phoneNumber) {
		User selected = uService.selectUser(phoneNumber);
		if(selected==null) {
			User user = new User();
			return user;
		}else {
			return selected;
		}
	}
	
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User selected = uService.login(user.getPhoneNumber(), user.getPassword());
        if (selected == null){
        	selected = new User();
        }
        log.debug(""+selected);

        return selected;
    }
	
	@PostMapping("/insert")
	public Boolean insert(@RequestBody User user) {
        return uService.insert(user);
    }
	
	@GetMapping("/checkphone")
	public Boolean checkPhoneNumber(String phoneNumber) {
		Boolean isExist = uService.checkPhoneNumber(phoneNumber);
		return isExist;
	}
	
	@PostMapping("/update")
	public boolean update(@RequestBody User user) {
		return uService.update(user);
	}
}
