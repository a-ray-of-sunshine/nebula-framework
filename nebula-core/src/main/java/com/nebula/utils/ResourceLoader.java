package com.nebula.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class ResourceLoader {
    
    private static final Logger log = LoggerFactory.getLogger(ResourceLoader.class);
    private static ClassLoader resourceLoader;
    
    static{
        resourceLoader = Thread.currentThread().getContextClassLoader();
        if(null == resourceLoader){
            resourceLoader = ClassLoader.getSystemClassLoader();
        }
    }
    
    public static InputStream load(String resourceName){
        return resourceLoader.getResourceAsStream(resourceName);
    }
    
    public static Properties loadAsProperties(String propertiesName){
        Properties prop = new Properties();
        try {
            prop.load(load(propertiesName));
        } catch (IOException e) {
            log.error("解析文件 {} 失败", propertiesName);
            throw new RuntimeException(e);
        }
        return prop;
    }
    
    public static Document loadAsXML(String xmlName){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(load(xmlName));
        } catch (Exception e) {
            log.error("解析文件 {} 失败", xmlName);
            throw new RuntimeException(e);
        }

        return document;
    }
}