package com.nebula.suport.dao;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Entity;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebula.utils.ResourceLoader;

/**
 * 使用 SchemaExport 创建 Entity 注解的 实体类的表
 * 需要在 classpath 配置 hibernate.cfg.xml
 */
public class CreateTable {

    private static final Logger log = LoggerFactory.getLogger(CreateTable.class);

    private ServiceRegistry standardRegistry;
    private SchemaExport export;
    private MetadataSources metadataSources;

    public CreateTable(){
        init(loadSettings());
    }

    @SuppressWarnings("rawtypes")
    public CreateTable(Map settings){
        init(settings);
    }

    @SuppressWarnings("rawtypes")
    private void init(Map settings) {
        // TODO:: 优化 hibernate.cfg.xml 的创建
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySettings(settings);
        standardRegistry = registryBuilder.build();
        metadataSources = new MetadataSources(standardRegistry);
        export =  new SchemaExport();
    }
    
    public void addByEntityNames(List<String> entityNames){
       for(String entityName : entityNames){
           add(entityName);
       }
    }
    
    @SuppressWarnings("rawtypes")
    public void addByEntityClazzs(List<Class> clazzs){
        for(Class clazz : clazzs){
            add(clazz);
        }
    }
    
    public void add(String entityName){
        Class<?> entityClazz = null;
        try {
            entityClazz = Class.forName(entityName);
        } catch (ClassNotFoundException e) {
            log.error("无法载入类: ", entityName);
        }
        add(entityClazz);
    }

    @SuppressWarnings("rawtypes")
    public void add(Class entityClazz){
        if(validateClass(entityClazz)){
            metadataSources.addAnnotatedClass(entityClazz);
        }
    }
    
    public void create(){
        Metadata metadata = metadataSources.buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        export.create(EnumSet.of(TargetType.DATABASE), metadata);
        sessionFactory.close();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean validateClass(Class entityClazz) {
        return entityClazz.getAnnotation(Entity.class) != null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map loadSettings() {
        Properties properties = ResourceLoader.loadAsProperties("jdbc.properties");
        Map settings = new HashMap();
        settings.put("hibernate.connection.driver_class", properties.get("jdbc.driverClass"));
        settings.put("hibernate.connection.url", properties.get("jdbc.jdbcUrl"));
        settings.put("hibernate.connection.username", properties.get("jdbc.user"));
        settings.put("hibernate.connection.password", properties.get("jdbc.password"));
        settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        settings.put("hibernate.show_sql",true);
        settings.put("hibernate.format_sql",true);
        settings.put("hibernate.connection.pool_size", 1);
        return settings;
    }
   
}
