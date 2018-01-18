package springifyapi;

import com.intuit.karate.junit4.Karate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import test.ServerStart;

@Ignore
@RunWith(Karate.class)
public abstract class TestBase {
    private final static  int DEFAULT_SERVER_PORT= 8087;
    private static ServerStart server;

    public static int startServer() throws Exception {
        if (server == null) { // keep spring boot side alive for all tests including package 'mock'
            server = new ServerStart();
            server.start(new String[]{"--server.port=0"}, false);
        }
        System.setProperty("springify.server.port", server.getPort() + "");
        return server.getPort();
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        startServer();
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

}
