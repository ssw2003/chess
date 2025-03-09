package server;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.javac.Main;
import dataaccess.DataAccessException;
import model.*;
import spark.*;

import java.util.ArrayList;
import java.util.Collection;
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
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        if (!svc.isAuthorized(authrztn)) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
        String gN;
        String psw;
        String eml;
        try {
            var json = gson.fromJson(request.body(), GameNameData.class);
            gN = json.gameName();
            if (gN == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        int i = svc.addGame(gN);
        var ce = Map.of("gameID", i);
        response.status(200);
        return gson.toJson(ce);
    }

    private Object thingyThing(Request request, Response response) {
        //Join game
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        if (!svc.isAuthorized(authrztn)) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
        String pC;
        int ident;
        try {
            var json = gson.fromJson(request.body(), ColorAndNumber.class);
            pC = json.playerColor();
            ident = json.gameID();
            if (pC == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        var ce = Map.of();
        try {
            svc.joinGame(ident, pC.equals("WHITE"), authrztn);
        } catch (DataAccessException e) {
            ce = Map.of("message", "Error: already taken");
            response.status(403);
            return gson.toJson(ce);
        }
        response.status(200);
        return gson.toJson(ce);
    }

    private Object thatThingy(Request request, Response response) {
        //LOGOUT
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            svc.logout(authrztn);
            var f = Map.of();
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: unauthorized");
            response.status(401);
            return gson.toJson(e);
        }
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
            if (usn == null) {
                throw new DataAccessException("");
            }
            if (psw == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
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
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
        try {
            var e = Map.of();
            Collection<GameDataWithout> cGD = svc.getGames();
            var f = Map.of("games", cGD);
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: unauthorized");
            response.status(500);
            return gson.toJson(e);
        }
    }

    private Object goThatThingy(Request request, Response response) {
        //Clear
        var gson = new Gson();
        try {
            svc.clearThingy();
            response.status(200);
            var c = Map.of();
            return gson.toJson(c);
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
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
            if (usn == null) {
                throw new DataAccessException("");
            }
            if (psw == null) {
                throw new DataAccessException("");
            }
            if (eml == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
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
