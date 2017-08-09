package com.nebula.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来解析 XML 文件的 ${...}
 */
public class XMLPropertyTokensParser {
    
    private static final Logger log = LoggerFactory.getLogger(XMLPropertyTokensParser.class);
    
    private Properties configurationProperties;
    private ClassLoader resourceLoader;
    
    public XMLPropertyTokensParser(String propertyName){
        this(propertyName, null);
    }

    public XMLPropertyTokensParser(String propertyName, Properties exProperties){
        initLoader();
        
        URL url = resourceLoader.getResource(propertyName);
        if(null != url){
            try {

                InputStream stream = url.openStream();
                configurationProperties = new Properties();
                configurationProperties.load(stream);
                stream.close();
                
                if(null != exProperties) {
                    configurationProperties.putAll(exProperties);
                }
            } catch (IOException e) {
                log.error("读取资源 {} 失败，请检查配置", propertyName);
            }
            
        }
    }
    
    public InputStream parseTokens(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringWriter source = new StringWriter(1024);
        BufferedWriter cache = new BufferedWriter(source, 512);
        try {
            String line = br.readLine();
            if(line.contains("$")) {
               line = parseProperties(line); 
            }
            cache.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new ByteArrayInputStream(source.getBuffer().toString().getBytes());
    }
    
    private String parseProperties(String line) {
        String normal = line;
        // TODO 解析一行文本中的  ${...}
        // <property name="driverClass" value="${jdbc.driverClass}"/>
        return normal;
    }

    private void initLoader() {
       resourceLoader = Thread.currentThread().getContextClassLoader();
       if(null != resourceLoader) {
          resourceLoader = ClassLoader.getSystemClassLoader(); 
       }
    }

}