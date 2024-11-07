package java.passoff.server;

import org.junit.jupiter.api.BeforeAll;
import passoff.model.TestUser;
import passoff.server.TestServerFacade;
import server.Server;

public class NewTests {
    private static final TestUser T_U = new TestUser("Pu", "Pp", "Pe");

    private static TestServerFacade sF;

    private static Server sv;

    private static Class<?> dMC;


    @BeforeAll
    public static void startServer() {
        sv = new Server();
        var port = sv.run(0);
        System.out.println("Started test HTTP server on " + port);

        sF = new TestServerFacade("localhost", Integer.toString(port));
    }
}
