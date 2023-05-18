package com.ssafy.foodfind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ssafy.foodfind.model.dao.UserDao;

@SpringBootApplication
@MapperScan(basePackageClasses = UserDao.class)
public class FoodfindApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodfindApplication.class, args);
	}

}
