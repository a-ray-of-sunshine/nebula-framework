package com.cxy;

import java.util.HashMap;
import java.util.Map;

import com.nebula.server.IServer;
import com.nebula.server.IServerFactory;
import com.nebula.server.impl.JettyFactory;

public class Main {

    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put(JettyFactory.PORT, "8080");
        config.put(JettyFactory.CONTEXT, "/");

        IServerFactory serverFactory = new JettyFactory();
        IServer server = serverFactory.createServer(config);
        
        server.start();
        try {
            Thread.sleep(99999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}