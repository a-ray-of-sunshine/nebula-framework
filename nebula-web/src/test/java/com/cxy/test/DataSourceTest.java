package com.cxy.test;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxy.dao.IUserDao;
import com.cxy.entity.User;
import com.cxy.service.IUserService;


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DataSourceTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactory;
	
	@Test
	public void testContext(){
	    
	    String[] names = applicationContext.getBeanDefinitionNames();
	    System.out.println("-----------context-----------");
	    for(int i = 0; i < names.length; i++){
	        System.out.println(names[i]);
	    }
	    System.out.println("-----------context-----------");
	}
	
	// @Test
	public void testConn(){
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println(dataSource);
		try {

			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("select 1");
			statement.execute("create table sys_user (id long, username varchar(255), password varchar(255))");
			statement.execute("insert into sys_user values (1, 'admin', '123456')");
			ResultSet resultSet = statement.executeQuery("select * from sys_user");
			
			while(resultSet.next()){
				System.out.println(resultSet.getLong("id") + " " +  resultSet.getString("username") + " " + resultSet.getString("password"));
			}
			
			// System.out.println(userService);
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSqlSession() throws Exception{
	    assertNotNull(sqlSessionFactory);
	    assertNotNull(userDao);
	    
	    User user = new User();
	    user.setUsername("tom");
	    user.setPassword("000000");

	    userDao.add(user);
	    System.out.println(userDao.getAll());
	    System.out.println(userDao);
	}
	
	@Test
	public void testService(){
	    assertNotNull(userService);
	    
	    System.out.println(userService.getAll());
	}
	
}