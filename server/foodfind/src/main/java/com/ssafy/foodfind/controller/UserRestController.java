package com.ssafy.foodfind.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.User;
import com.ssafy.foodfind.model.service.UserService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/rest/user")
public class UserRestController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	UserService uService;
	
	@GetMapping("/info")
    @ApiOperation(value="해당 phoneNumber를 가진 회원의 정보를 조회함. 없는 경우 null로 채워진 User객체 반환", response = User.class)
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
    @ApiOperation(value="비밀번호와 휴대폰 전화번호가 일치하는 경우 로그인, 로그인 성공하면 해당 유저의 정보가 반환되고 실패하면 null로 채워진 User객체 반환", response = User.class)
    public User login(@RequestBody User user) {
        User selected = uService.login(user.getPhoneNumber(), user.getPassword());
        if (selected == null){
        	selected = new User();
        }
        log.debug(""+selected);

        return selected;
    }
	
	@PostMapping("/insert")
    @ApiOperation(value="User객체를 전달하여 회원가입, 가입 성공하면 true, 실패하면 false반환", response = Boolean.class)
	public Boolean insert(@RequestBody User user) {
        return uService.insert(user);
    }
	
	@GetMapping("/checkphone")
    @ApiOperation(value="중복되는 전화번호가 있는지 확인, 중복되는것이 있으면 true, 중복이 없으면 false", response = Boolean.class)
	public Boolean checkPhoneNumber(String phoneNumber) {
		Boolean isExist = uService.checkPhoneNumber(phoneNumber);
		return isExist;
	}
	
	@PutMapping("/update")
    @ApiOperation(value="User정보를 업데이트, 성공하면 true, 실패하면 false", response = Boolean.class)
	public boolean update(@RequestBody User user) {
		return uService.update(user);
	}
}
