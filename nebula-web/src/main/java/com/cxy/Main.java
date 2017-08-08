package com.cxy;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.Constants;

public class Main {

    public static void main(String[] args) {
        
        
        test();
        
        System.setProperty(Constants.SKIP_JARS_PROPERTY, "*.jar");
        Tomcat tomcat = new Tomcat();
        try {
            // URL url = Thread.currentThread().getContextClassLoader().getResource("target\\tomcat");
            File file = new File("src/main/webapp");
            // System.setProperty("catalina.base", url.getFile());
            System.out.println(file.getAbsolutePath());
            tomcat.addWebapp("", file.getAbsolutePath());
            // tomcat.addWebapp("/", "src/main/webapp");
            tomcat.start();
            System.out.println("start finish.");
            // tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Map<String, String> config = new HashMap<>();
//        config.put(JettyFactory.PORT, "8080");
//        config.put(JettyFactory.CONTEXT, "/");
//
//        IServerFactory serverFactory = new JettyFactory();
//        IServer server = serverFactory.createServer(config);
//        
//        server.start();
//        try {
//            Thread.sleep(99999999);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void test() {
        
        ClassLoader cl = Main.class.getClassLoader();
        while(null != cl){
            if(cl instanceof URLClassLoader){
               System.out.println(cl);
               URL[] urLs = ((URLClassLoader) cl).getURLs();
               for(URL url : urLs){
                   System.out.println(url);
               }
            }
            cl = cl.getParent();
        }
        
        
        
    }
}