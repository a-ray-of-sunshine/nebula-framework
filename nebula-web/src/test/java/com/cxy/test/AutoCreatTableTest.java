package com.cxy.test;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoCreatTableTest extends SpringContext{

    @Autowired
    private IUserDao userDao;
    
    @Test 
    public void test1(){
        
        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
        // Map settings = new HashMap();
        // ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(settings ).build();

        MetadataSources metadataSources = new MetadataSources(standardRegistry);
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(Role.class);
        Metadata metadata = metadataSources.buildMetadata();
        metadata.buildSessionFactory();

        SchemaExport se = new SchemaExport();
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
        
        se.create(targetTypes, metadata);
    }

    @Test
    public void test2() {
        Assert.assertNotNull(userDao);;
        userDao.getAll();
    }
    
}
