package com.cloud.publishing.frontend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class Application {
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        final Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(PORT);
        tomcat.getConnector();
        tomcat.getHost().setAppBase(".");
        File webAppDir = new File("frontend/src/main/webapp");
        if (!webAppDir.exists()) {
            webAppDir = new File("webapp");
        }
        Context context = tomcat.addWebapp("", webAppDir.getAbsolutePath());
        context.setParentClassLoader(Application.class.getClassLoader());
        StandardJarScanner jarScanner = new StandardJarScanner();
        jarScanner.setScanManifest(true);
        context.setJarScanner(jarScanner);
        tomcat.start();
        tomcat.getServer().await();
    }

    private static String createTempDir() {
        try {
            Path tempDir = Files.createTempDirectory("tomcat-frontend");
            tempDir.toFile().deleteOnExit();
            return tempDir.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create tempDir", e);
        }
    }
}