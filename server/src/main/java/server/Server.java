package server;

import com.sun.tools.javac.Main;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
        //Spark.init("::POST", "/user", doIt());

        Spark.awaitInitialization();
        return Spark.port();
    }
    public void doIt() {
        System.out.println("Happy is me\n");
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
