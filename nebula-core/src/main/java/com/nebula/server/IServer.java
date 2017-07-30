package com.nebula.server;

public interface IServer {

    void init(int port, String contextPath);
    
    void start();
    
    void reload();
    
    void stop();
}