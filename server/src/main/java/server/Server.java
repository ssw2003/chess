package server;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.javac.Main;
import model.*;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
        Spark.post("/user", this::goThingy);
        Spark.delete("/db", this::goThatThingy);
        Spark.get("/game", this::goThisThingy);
        Spark.post("/session", this::thingy);
        Spark.delete("/session", this::thatThingy);
        Spark.put("/game", this::thingyThing);
        Spark.post("/game", this::thisThingy);
        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object thisThingy(Request request, Response response) {
        //Create game
    }

    private Object thingyThing(Request request, Response response) {
        //Join game
    }

    private Object thatThingy(Request request, Response response) {
        //LOGOUT
    }

    private Object thingy(Request request, Response response) {
        //LOGIN
    }

    private Object goThisThingy(Request request, Response response) {
        //Games listing
    }

    private Object goThatThingy(Request request, Response response) {
        //Clear
    }

    private Object goThingy(Request request, Response response) {
        String usn;
        String psw;
        String eml;
        var gson = new Gson();
        try {
            var json = gson.fromJson(request.body(), UserData.class);
            usn = json.username();
            psw = json.password();
            eml = json.email();
        } catch (JsonSyntaxException e) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        var d = Map.of("message", "Error: Bad");
        response.status(500);
        return gson.toJson(d);
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
