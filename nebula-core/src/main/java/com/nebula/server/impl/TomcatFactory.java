package com.nebula.server.impl;

import java.util.Map;

import com.nebula.server.IServer;
import com.nebula.server.IServerFactory;

public class TomcatFactory implements IServerFactory {

    IServer tomcat;
    
    @Override
    public IServer createServer(Map<String, String> config) {
        tomcat = new Tomcat();
        tomcat.init(Integer.valueOf(config.get(JettyFactory.PORT)), config.get(JettyFactory.CONTEXT));
        return tomcat;
    }

}
