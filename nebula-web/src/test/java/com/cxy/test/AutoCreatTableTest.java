package com.cxy.test;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import com.cxy.entity.User;

public class AutoCreatTableTest {

    @Test 
    public void test(){
        
        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();

        MetadataSources metadataSources = new MetadataSources(standardRegistry);
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.buildMetadata();
        metadata.buildSessionFactory();

        SchemaExport se = new SchemaExport();
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
        
        se.create(targetTypes, metadata);
    }
    
}
