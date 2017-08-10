package com.nebula.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来解析 XML 文件的 ${...}
 */
public class XMLPropertyTokensParser {
    
    private static final Logger log = LoggerFactory.getLogger(XMLPropertyTokensParser.class);
    private static final String TOKEN_TAG = "$";
    private static final String START_TAG = "{";
    private static final String END_TAG = "}";

    private static Pattern pattern;

    private Properties configurationProperties;
    private ClassLoader resourceLoader;
    
    static{
        String reg = "\\" + TOKEN_TAG +  "\\s*\\" + START_TAG + "([\\w\\.\\s*]*)\\" + END_TAG;
        pattern = Pattern.compile(reg);
    }
    
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
    
    public InputStream parse(String resourceName) {
       InputStream res = null;
       try {
           InputStream stream = resourceLoader.getResource(resourceName).openStream();
           res = parse(stream);
           stream.close();
       } catch (IOException e) {
           log.warn("读取资源失败，请检查 {}", resourceName);
       }
       return res;
    }
    
    public InputStream parse(File file) {
       InputStream res = null;
       try {
           res = parse(new FileInputStream(file));
       } catch (FileNotFoundException e) {
           log.warn("资源不存在，请检查 {}", file.getAbsolutePath());
       }
       return res;
    }
    
    public InputStream parse(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringWriter source = new StringWriter(1024);
        BufferedWriter cache = new BufferedWriter(source, 512);
        try {
            String line ;
            while(null != (line = br.readLine())){
                if(line.contains(TOKEN_TAG)) {
                   line = parseProperties(line); 
                }
                cache.write(line);
                cache.newLine();
            }
            cache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new ByteArrayInputStream(source.getBuffer().toString().getBytes());
    }
    
    private String parseProperties(String line) {
        // TODO 解析一行文本中的  ${...}
        // <property name="driverClass" value="${jdbc.driverClass}"/>
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            String token = matcher.group();
            line = line.replace(token, resolve(token));
        }
        return line;
    }

    private String resolve(String token) {
        token = token.substring(token.indexOf(START_TAG) + 1, token.indexOf(END_TAG)).trim();
        String property = configurationProperties.getProperty(token);
        if(null == property){
           log.warn("Token resovle fail. can't find token: {}", token); 
           property = token;
        }
        return property;
    }

    private void initLoader() {
       resourceLoader = Thread.currentThread().getContextClassLoader();
       if(null != resourceLoader) {
          resourceLoader = ClassLoader.getSystemClassLoader(); 
       }
    }

}