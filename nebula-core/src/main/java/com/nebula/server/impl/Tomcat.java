package com.nebula.server.impl;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.tomcat.util.scan.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebula.server.IServer;

public class Tomcat implements IServer {

    private org.apache.catalina.startup.Tomcat tomcat;
    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    Tomcat() {
        tomcat = new org.apache.catalina.startup.Tomcat();
    }

    @Override
    public void init(int port, String contextPath) {
        setEnv();

        try {
            // 添加 docBase
            File file = new File("src/main/webapp");
            tomcat.addWebapp(contextPath, file.getAbsolutePath());
            tomcat.getConnector().setPort(port);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void setEnv() {
        // 2. 禁止扫描 classpath 中的 jar 包，查找 tld 文件，加快启动速度
        System.setProperty(Constants.SKIP_JARS_PROPERTY, "*.jar");

        // 2. 创建 work 目录，用来存储 jsp 编译后的文件
        File f = new File("target/tomcat/");
        f.mkdirs();
        System.setProperty("catalina.base", f.getAbsolutePath());
    }

    @Override
    public void start() {
        try {
            long t1 = System.nanoTime();
            tomcat.start();
            long t2 = System.nanoTime();
            log.info("Server startup in {} s (total: {} ms)", (t2 - t1) / 1000000000, (t2 - t1) / 1000000);
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        try {
            tomcat.stop();
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            tomcat.stop();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
