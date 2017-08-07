package com.nebula.server.impl;

import java.io.File;

import org.apache.catalina.startup.CatalinaProperties;

import com.nebula.server.IServer;

public class Tomcat implements IServer{

    private static File configurationDir;
    static {
        configurationDir = new File("D:\\workspace\\nebula-framework\\nebula-web\\target\\tomcat");
        System.setProperty("catalina.base", configurationDir.getAbsolutePath());
        // Trigger loading of catalina.properties
        CatalinaProperties.getProperty("foo");
    }

    @Override
    public void init(int port, String contextPath) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

}
