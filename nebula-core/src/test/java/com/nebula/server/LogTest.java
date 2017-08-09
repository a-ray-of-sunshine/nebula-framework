package com.nebula.server;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class LogTest {

    public static void main(String[] args) {
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test() throws IOException {
        
//        System.out.println(System.getProperty("java.ext.dirs"));
//        System.out.println(System.getProperty("java.class.path"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
////       URL u = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
////       System.out.println(u.toString());
//        File file = new File("target/classe");
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.exists());
//        File logfile = new File("log4j.xml");
//        System.out.println(logfile.getAbsolutePath());
//        System.out.println(logfile.exists());
//        
//        URL u = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
//        System.out.println(u.getFile());
//        System.out.println(Thread.currentThread().getContextClassLoader());
//        
        
        
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Enumeration<URL> resources = classLoader.getResources("log4j.xml");
        while(resources.hasMoreElements()){
            System.out.println(resources.nextElement().toString());
        }
        
        ClassLoader cl = LogTest.class.getClassLoader();
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
