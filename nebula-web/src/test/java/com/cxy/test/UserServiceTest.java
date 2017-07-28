package com.cxy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxy.service.IUserService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceTest {

    @Autowired
    IUserService userService;
    
    @Test
    public void test() {
        System.out.println(userService.getAll());
    }
}
