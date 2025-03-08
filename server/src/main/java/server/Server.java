package server;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.javac.Main;
import model.*;
import spark.*;

import java.util.Map;

public class Server {
    private dataaccess.Service svc;

    public int run(int desiredPort) {
        svc = new dataaccess.Service();
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
        String usn;
        String psw;
        var gson = new Gson();
        try {
            var json = gson.fromJson(request.body(), UserPass.class);
            usn = json.username();
            psw = json.password();
        } catch (JsonSyntaxException e) {
            response.status(500);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            var f = Map.of("username", usn, "authToken", svc.regUsr(usn, psw, "", false));
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: unauthorized");
            response.status(401);
            return gson.toJson(e);
        }
    }

    private Object goThisThingy(Request request, Response response) {
        //Games listing
    }

    private Object goThatThingy(Request request, Response response) {
        //Clear
    }

    private Object goThingy(Request request, Response response) {
        //register
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
            response.status(500);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            var f = Map.of("username", usn, "authToken", svc.regUsr(usn, psw, eml, true));
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: already taken");
            response.status(403);
            return gson.toJson(e);
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
