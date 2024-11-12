package server;

import chess.ChessGame;
import com.google.gson.Gson;
import spark.*;

import java.util.Map;


import java.util.ArrayList;
import java.util.Collection;

public class Server {
    Service theService = new Service();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::addUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/db", this::clearApplication);
        Spark.delete("/session", this::logoutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }
    private String addUser(Request req, Response res) {
            var registerRequest = new Gson().fromJson(req.body(), UserData.class);
            if (registerRequest.username() == null) {
                var thingSerializer = new Gson();
                var thingToSerialize = Map.of("message", "Error: bad request");
                res.status(400);
                var thingJson = thingSerializer.toJson(thingToSerialize);
                return thingJson;
            }
            if (registerRequest.password() == null) {
                res.status(400);
                var thingToSerialize = Map.of("message", "Error: bad request");
                var thingSerializer = new Gson();
                var thingJson = thingSerializer.toJson(thingToSerialize);
                return thingJson;
            }
            if (registerRequest.email() == null) {
                res.status(400);
                var stringMap = Map.of("message", "Error: bad request");
                var thingSerializer = new Gson();
                var thingJson = thingSerializer.toJson(stringMap);
                return thingJson;
            }
            AuthData theNewAuthData = theService.register(registerRequest);

            if (theNewAuthData != null) {
                res.status(200);
                //System.out.println(String.join("","200 " ,"{ \"username\":\"", theNewAuthData.username(), "\", \"authToken\":\"\"", theNewAuthData.authToken(), "\"\" }"));
                var thingToSerialize = Map.of("username", theNewAuthData.username(), "authToken", theNewAuthData.authToken());
                var thingSerializer = new Gson();
                var thingJson = thingSerializer.toJson(thingToSerialize);
                return thingJson;
            }

            res.status(403);
            var myThingToSerialize = Map.of("message", "Error: already taken");
            var myThingSerializer = new Gson();
            var myThingJson = myThingSerializer.toJson(myThingToSerialize);
            return myThingJson;
    }
    private String loginUser(Request req, Response res) {
        var frommedJson = new Gson().fromJson(req.body(), LoginData.class);
        AuthData didItWork = theService.login(frommedJson.username(), frommedJson.password());
        if (didItWork == null) {
            res.status(401);
            var thingToSerialize = Map.of("message", "Error: unauthorized");
            var thingSerializer = new Gson();
            var thingJson = thingSerializer.toJson(thingToSerialize);
            return thingJson;
        }
        else {
            res.status(200);
            var stringStringMap = Map.of("username", didItWork.username(), "authToken", didItWork.authToken());
            var thingSerializer = new Gson();
            var thingJson = thingSerializer.toJson(stringStringMap);
            return thingJson;
        }
    }
    private String logoutUser(Request req, Response res) {
        var newThing = new Gson().fromJson(req.body(), String.class);
        String myAuthData = req.headers("authorization"); //req.attribute("Authorization");
        if (theService.logout(myAuthData)) {
            res.status(200);
            var objectObjectMap = Map.of();
            var thingSerializer = new Gson();
            var thingJson = thingSerializer.toJson(objectObjectMap);
            return thingJson;
        }
        else {
            res.status(401);
            var stringMap = Map.of("message", "Error: unauthorized");
            var thingSerializer = new Gson();
            var thingJson = thingSerializer.toJson(stringMap);
            return thingJson;
        }
    }
    private String clearApplication(Request req, Response res) {
        theService.clearThing();
        res.status(200);
        var objectObjectMap = Map.of();
        var thingSerializer = new Gson();
        var thingJson = thingSerializer.toJson(objectObjectMap);
        return thingJson;
    }
    private String joinGame(Request req, Response res) {
        var newThing = new Gson().fromJson(req.body(), PlayerColorGameNumber.class);
        String myAuthData = req.headers("authorization");

        if (!theService.checkFor(myAuthData)) {
            res.status(401);
            var stringStringMap = Map.of("message", "Error: unauthorized");
            var thingSerializer = new Gson();
            var theThingJson = thingSerializer.toJson(stringStringMap);
            return theThingJson;
        }
        if (newThing.playerColor() != ChessGame.TeamColor.WHITE &&
        newThing.playerColor() != ChessGame.TeamColor.BLACK) {
            res.status(400);
            var theThingToSerialize = Map.of("message", "Error: bad request");
            var thingSerializer = new Gson();
            var theThingJson = thingSerializer.toJson(theThingToSerialize);
            return theThingJson;
        }
        if (!theService.checkForGameExistence(newThing.gameID())) {
            res.status(400);
            var stringMap = Map.of("message", "Error: bad request");
            var thingSerializer = new Gson();
            var thingSerializerJson = thingSerializer.toJson(stringMap);
            return thingSerializerJson;
        }
        if (theService.joinGameThingy(newThing.gameID(), newThing.playerColor(), myAuthData)) {
            res.status(200);
            var objectObjectMap = Map.of();
            var thingSerializer = new Gson();
            var thingSerializerJson = thingSerializer.toJson(objectObjectMap);
            return thingSerializerJson;
        }
        res.status(403);
        var thingToSerialize = Map.of("message", "Error: already taken");
        var thingSerializer = new Gson();
        var thingJson = thingSerializer.toJson(thingToSerialize);
        return thingJson;
    }
    private String listGames(Request req, Response res) {
        String myAuthData = req.headers("authorization");
        if (theService.checkFor(myAuthData)) {
            res.status(200);
            Collection<GameData> gameData = theService.getAllGames();
            Collection<GameMetadata> arrayList = new ArrayList<>();
            for (GameData gd: gameData) {
                GameMetadata gm = new GameMetadata(gd.gameID(), gd.whiteUsername(), gd.blackUsername(), gd.gameName());
                arrayList.add(gm);
            }
            var thingToSerialize = Map.of("games", arrayList);
            var thingSerializer = new Gson();
            var thingSerializerJson = thingSerializer.toJson(thingToSerialize);
            return thingSerializerJson;
        }
        else {
            var thingToSerialize = Map.of("message", "Error: unauthorized");
            var thingSerializer = new Gson();
            res.status(401);
            var thingJson = thingSerializer.toJson(thingToSerialize);
            return thingJson;
        }
    }
    private String createGame(Request req, Response res) {
        GameName gameName = new Gson().fromJson(req.body(), GameName.class);
        String myAuthData = req.headers("authorization"); //req.attribute("Authorization");
        if (theService.checkFor(myAuthData)) {
            res.status(200);
            var integerMap = Map.of("gameID", theService.addGame(gameName.gameName()));
            var thingSerializer = new Gson();
            var thingJson = thingSerializer.toJson(integerMap);
            return thingJson;
        }
        else {
            res.status(401);
            var stringMap = Map.of("message", "Error: unauthorized");
            var thingSerializer = new Gson();
            var thingSerializerJson = thingSerializer.toJson(stringMap);
            return thingSerializerJson;
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
