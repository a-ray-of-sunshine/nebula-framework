package com.nebula.server;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.nebula.server.impl.JettyFactory;

public class JettyTest {

    @Test
    public void test(){
        Map<String, String> config = new HashMap<>();
        config.put(JettyFactory.PORT, "8080");
        config.put(JettyFactory.CONTEXT, "/");

        IServerFactory serverFactory = new JettyFactory();
        IServer server = serverFactory.createServer(config);
        
        server.start();
    }
}
