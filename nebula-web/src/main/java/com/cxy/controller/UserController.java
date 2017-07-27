package com.cxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxy.entity.User;
import com.cxy.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	IUserService userService;
	
	@RequestMapping("add")
	public void add(){
		User user = new User();
		user.setId(1L);
		user.setUsername("admin");
		user.setPassword("123456");

		userService.add(user);
	}
	
	@RequestMapping("list")
	public void list(){
		
		List<User> users = userService.getAll();
		for(User user : users){
			System.err.println(user);
		}
		
	}
}
