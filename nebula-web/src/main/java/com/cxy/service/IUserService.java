package com.cxy.service;

import java.util.List;

import com.cxy.entity.User;

public interface IUserService {

	public void add(User user);
	
	public List<User> getAll();
}
