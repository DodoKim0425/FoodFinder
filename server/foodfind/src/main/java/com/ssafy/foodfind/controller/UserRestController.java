package com.ssafy.foodfind.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public User getInfo(String id) {
		log.debug("message");

		User selected = uService.selectUser(id);
		if(selected==null) {
			User user = new User();
			return user;
		}else {
			return selected;
		}
	}
}
