package com.nebula.server;

import java.io.File;
import java.net.URL;

public class LogTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//       URL u = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
//       System.out.println(u.toString());
        File file = new File("target/classe");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        File logfile = new File("log4j.xml");
        System.out.println(logfile.getAbsolutePath());
        System.out.println(logfile.exists());
        
        URL u = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
        System.out.println(u.getFile());
        System.out.println(Thread.currentThread().getContextClassLoader());
        
    }
}
