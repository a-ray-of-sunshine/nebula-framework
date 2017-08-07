package com.nebula.server;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomcatTest {

    private static final Logger log = LoggerFactory.getLogger(TomcatTest.class);
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {

        String CATALINA_BASE = "D:\\workspace\\nebula-framework\\nebula-web\\target\\tomcat";
        // 设置不扫描 jar查找 tld 文件，加快启动速度
        System.setProperty(Constants.SKIP_JARS_PROPERTY, "*.jar");
        System.setProperty("catalina.base", CATALINA_BASE);
        Tomcat embeddedTomcat = new Tomcat();
        embeddedTomcat.getServer().addLifecycleListener(new VersionLoggerListener());
        embeddedTomcat.setBaseDir(CATALINA_BASE);

        try {
            Context ctx = createContext(embeddedTomcat);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        embeddedTomcat.getHost().setName("localhost");

        String protocol = "HTTP/1.1";
        Connector connector = new Connector(protocol);
        int port = 8080;
        connector.setPort(port);
        String uriEncoding = "ISO-8859-1";
        connector.setURIEncoding(uriEncoding);
        connector.setUseBodyEncodingForURI(false);
        embeddedTomcat.getService().addConnector(connector);
        embeddedTomcat.setConnector(connector);

        try {
            long t1 = System.nanoTime();
            embeddedTomcat.start();
            long t2 = System.nanoTime();
            log.info("Server startup in {} s (total: {} ms)", (t2 - t1) / 1000000000, (t2 - t1) / 1000000);
            embeddedTomcat.getServer().await();
        } catch (LifecycleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected static Context createContext(Tomcat container) throws IOException, ServletException {
        String contextPath = "/";

        String baseDir = "D:\\workspace\\nebula-framework\\nebula-web\\src\\main\\webapp";
        contextPath = "/".equals(contextPath) ? "" : contextPath;
        Context context = container.addWebapp(contextPath, baseDir);

        String resourceBase = "D:\\workspace\\nebula-framework\\nebula-web\\target";
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                resourceBase, "/classes"));
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/lib",
                resourceBase, "/lib"));
        context.setResources(resources);

        return context;

    }
}