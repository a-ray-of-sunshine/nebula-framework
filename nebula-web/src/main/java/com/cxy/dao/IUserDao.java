package com.cxy.dao;

import java.util.List;

import com.cxy.entity.User;

public interface IUserDao {
	
	public void add(User user);
	
	public List<User> getAll();

}