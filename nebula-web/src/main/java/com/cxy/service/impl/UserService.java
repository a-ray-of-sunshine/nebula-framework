package com.cxy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxy.dao.IUserDao;
import com.cxy.entity.User;
import com.cxy.service.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserDao userDao;

	@Override
	public void add(User user) {
		userDao.add(user);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

}
