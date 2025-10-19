import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.catalina.startup.Tomcat;

public class Application {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        final Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(PORT);
        tomcat.getConnector();
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("/", ".");
        tomcat.start();
        tomcat.getServer().await();
    }

    private static String createTempDir() {
        try {
            Path tempDir = Files.createTempDirectory("tomcat");
            tempDir.toFile().deleteOnExit();
            return tempDir.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create tempDir", e);
        }
    }
}