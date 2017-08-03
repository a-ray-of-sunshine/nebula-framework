package com.nebula.server.impl;

import com.nebula.server.IServer;

public class Jetty implements IServer {

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

//    private static Logger log = LoggerFactory.getLogger(Jetty.class);
//    private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
//    private static final String WINDOWS_WEBDEFAULT_PATH = "jetty/webdefault-windows.xml";
// 
//    private Server server;
//    
//    Jetty() {
//        server = new Server();
//    }
//    
//    @Override
//    public void start() {
//        try {
//            server.start();
//        } catch (Exception e) {
//            log.error("start fail. {}", e);
//        }
//    }
//
//    @Override
//    public void reload() {
//    }
//
//    @Override
//    public void stop() {
//        try {
//            server.stop();
//        } catch (Exception e) {
//            log.error("stop fail. {}", e);
//        }
//    }
//
//    @Override
//    public void init(int port, String contextPath) {
//        // 设置在JVM退出时关闭Jetty的钩子。
//        server.setStopAtShutdown(true);
//
//        SelectChannelConnector connector = new SelectChannelConnector();
//        connector.setPort(port);
//        // 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
//        connector.setReuseAddress(false);
//        server.setConnectors(new Connector[] { connector });
//
//        WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
//        // 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
//        // 参考解决办法 http://www.blogjava.net/alwayscy/archive/2007/05/27/120305.html
//        // webContext.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);
//        server.setHandler(webContext);
//    }

}
