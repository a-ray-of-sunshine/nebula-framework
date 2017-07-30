package com.nebula.server.impl;

import java.util.Map;

import com.nebula.server.IServer;
import com.nebula.server.IServerFactory;

public class JettyFactory implements IServerFactory {

    private IServer server;
    public static final String PORT = "PORT";
    public static final String CONTEXT = "CONTEXT";

    @Override
    public IServer createServer(Map<String, String> config) {
        
        server = new Jetty();
        // 初始化 server
        server.init(Integer.valueOf(config.get(PORT)), config.get(CONTEXT));
        // 增加目录监听器 对编译后的代码目录监听，如果文件发生变化，则对 applicationContext 进行刷新

        return server;
    }

}
