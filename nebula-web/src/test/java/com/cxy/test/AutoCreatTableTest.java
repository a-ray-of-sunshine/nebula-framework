package com.cxy.test;

import java.nio.channels.SeekableByteChannel;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxy.dao.IUserDao;
import com.cxy.entity.Role;
import com.cxy.entity.User;
import com.nebula.suport.dao.CreateTable;
import com.nebula.utils.ResourceLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoCreatTableTest /*extends SpringContext*/{

    @Autowired
    private IUserDao userDao;
    
    public static void main(String[] args) {

        new AutoCreatTableTest().test1();
    }
     // @Test 
    public void test1(){
        
        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
        // Map settings = new HashMap();
        // ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(settings ).build();

        MetadataSources metadataSources = new MetadataSources(standardRegistry);
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(Role.class);
        Metadata metadata = metadataSources.buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();

        SchemaExport se = new SchemaExport();
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
        
        se.create(targetTypes, metadata);
        sessionFactory.close();
    }

    // @Test
    public void test2() {
        Assert.assertNotNull(userDao);
        userDao.getAll();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void test3(){
        Properties properties = ResourceLoader.loadAsProperties("jdbc.properties");
        Map setting = new HashMap();
        setting.put("hibernate.connection.driver_class", properties.get("jdbc.driverClass"));
        setting.put("hibernate.connection.url", properties.get("jdbc.jdbcUrl"));
        CreateTable ct = new CreateTable(setting);
        ct.add(User.class);
        ct.create();
    }
    
}