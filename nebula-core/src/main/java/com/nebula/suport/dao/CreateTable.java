package com.nebula.suport.dao;

import java.util.EnumSet;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 SchemaExport 创建 Entity 注解的 实体类的表
 * 需要在 classpath 配置 hibernate.cfg.xml
 */
public class CreateTable {

    private static final Logger log = LoggerFactory.getLogger(CreateTable.class);

    private ServiceRegistry standardRegistry;
    private SchemaExport export;
    
    public CreateTable(){
        // TODO:: 优化 hibernate.cfg.xml 的创建
        // 最好做到让合适的人不用编写这个文件
        standardRegistry = new StandardServiceRegistryBuilder().configure().build();
        export =  new SchemaExport();;
    }
    
    public void createByEntityNames(List<String> entityNames){
       for(String entityName : entityNames){
           create(entityName);
       }
    }
    
    public void createByEntityClazzs(List<Class> clazzs){
        for(Class clazz : clazzs){
            create(clazz);
        }
    }
    
    public void create(String entityName){
        Class<?> entityClazz = null;
        try {
            entityClazz = CreateTable.class.forName(entityName);
        } catch (ClassNotFoundException e) {
            log.error("无法载入类: ", entityName);
        }
        create(entityClazz);
    }

    public void create(Class entityClazz){
        if(validateClass(entityClazz)){
            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            metadataSources.addAnnotatedClass(entityClazz);
            doCreate(metadataSources);
        }
    }
    
    private boolean validateClass(Class entityClazz) {
        return entityClazz.getAnnotation(Entity.class) != null;
    }

    private void doCreate(MetadataSources metadataSources){

        Metadata metadata = metadataSources.buildMetadata();
        metadata.buildSessionFactory();
        
        export.create(EnumSet.of(TargetType.DATABASE), metadata);
    }
    
}
