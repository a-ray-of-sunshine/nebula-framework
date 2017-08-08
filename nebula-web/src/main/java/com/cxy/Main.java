package com.cxy;

import java.util.HashMap;
import java.util.Map;

import com.nebula.server.IServer;
import com.nebula.server.IServerFactory;
import com.nebula.server.impl.JettyFactory;
import com.nebula.server.impl.TomcatFactory;

public class Main {

    public static void main(String[] args) {
        
        Map<String, String> config = new HashMap<>();
        config.put(JettyFactory.PORT, "8080");
        config.put(JettyFactory.CONTEXT, "");

        // IServerFactory serverFactory = new JettyFactory();
        IServerFactory serverFactory = new TomcatFactory();
        IServer server = serverFactory.createServer(config);
        
        server.start();
    }

  
}