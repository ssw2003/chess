package server;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.*;

import java.util.Map;

import java.util.UUID;



import java.util.ArrayList;
import java.util.Collection;

public class Server {
    Service the_service = new Service();

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
        //try {
            var registerRequest = new Gson().fromJson(req.body(), UserData.class);
            if (registerRequest.username() == null) {
                res.status(400);
                var thing_to_serialize = Map.of("message", "Error: bad request");
                var thing_serializer = new Gson();
                var thing_json = thing_serializer.toJson(thing_to_serialize);
                return thing_json;
            }
            if (registerRequest.password() == null) {
                res.status(400);
                var thing_to_serialize = Map.of("message", "Error: bad request");
                var thing_serializer = new Gson();
                var thing_json = thing_serializer.toJson(thing_to_serialize);
                return thing_json;
            }
            if (registerRequest.emails() == null) {
                res.status(400);
                var thing_to_serialize = Map.of("message", "Error: bad request");
                var thing_serializer = new Gson();
                var thing_json = thing_serializer.toJson(thing_to_serialize);
                return thing_json;
            }
            //
            //return new Gson().toJson(registerRequest);
            //res = new Response(100);
            AuthData the_new_auth_data = the_service.register(registerRequest);

            if (the_new_auth_data != null) {
                res.status(200);
                //System.out.println(String.join("","200 " ,"{ \"username\":\"", the_new_auth_data.username(), "\", \"authToken\":\"\"", the_new_auth_data.authToken(), "\"\" }"));
                var thing_to_serialize = Map.of("username", the_new_auth_data.username(), "authToken", the_new_auth_data.authToken());
                var thing_serializer = new Gson();
                var thing_json = thing_serializer.toJson(thing_to_serialize);
                return thing_json;
            }

            res.status(403);
            var my_thing_to_serialize = Map.of("message", "Error: already taken");
            var my_thing_serializer = new Gson();
            var my_thing_json = my_thing_serializer.toJson(my_thing_to_serialize);
            return my_thing_json;
            //return """
            //        {"message": "Hello world"}""";

        //} catch () {
        //}

    }
    private String loginUser(Request req, Response res) {
        var new_thing = new Gson().fromJson(req.body(), LoginData.class);
        AuthData did_it_work = the_service.login(new_thing.username(), new_thing.password());
        if (did_it_work == null) {
            res.status(401);
            var thing_to_serialize = Map.of("message", "Error: unauthorized");
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
        else {
            res.status(200);
            var thing_to_serialize = Map.of("username", did_it_work.username(), "authToken", did_it_work.authToken());
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
    }
    private String logoutUser(Request req, Response res) {
        var new_thing = new Gson().fromJson(req.body(), String.class);
        String my_auth_data = req.headers("authorization"); //req.attribute("Authorization");
        if (the_service.logout(my_auth_data)) {
            res.status(200);
            var thing_to_serialize = Map.of();
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
        else {
            res.status(401);
            var thing_to_serialize = Map.of("message", "Error: unauthorized");
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
    }
    private String clearApplication(Request req, Response res) {
        //var new_thing = new Gson().fromJson(req.body(), UserData.class);
        the_service.clear_thing();
        res.status(200);
        var thing_to_serialize = Map.of();
        var thing_serializer = new Gson();
        var thing_json = thing_serializer.toJson(thing_to_serialize);
        return thing_json;
    }
    private String joinGame(Request req, Response res) {
        var new_thing = new Gson().fromJson(req.body(), PlayerColorGameNumber.class);
        String my_auth_data = req.headers("authorization");

        if (!the_service.check_for(my_auth_data)) {
            res.status(401);
            var the_thing_to_serialize = Map.of("message", "Error: unauthorized");
            var the_thing_serializer = new Gson();
            var the_thing_json = the_thing_serializer.toJson(the_thing_to_serialize);
            return the_thing_json;
        }
        if (new_thing.playerColor() != ChessGame.TeamColor.WHITE &&
        new_thing.playerColor() != ChessGame.TeamColor.BLACK) {
            res.status(400);
            var the_thing_to_serialize = Map.of("message", "Error: bad request");
            var the_thing_serializer = new Gson();
            var the_thing_json = the_thing_serializer.toJson(the_thing_to_serialize);
            return the_thing_json;
        }
        if (!the_service.check_for_game_existence(new_thing.gameID())) {
            res.status(400);
            var the_thing_to_serialize = Map.of("message", "Error: bad request");
            var the_thing_serializer = new Gson();
            var the_thing_json = the_thing_serializer.toJson(the_thing_to_serialize);
            return the_thing_json;
        }
        if (the_service.join_game_thingy(new_thing.gameID(), new_thing.playerColor(), my_auth_data)) {
            res.status(200);
            var the_thing_to_serialize = Map.of();
            var the_thing_serializer = new Gson();
            var the_thing_json = the_thing_serializer.toJson(the_thing_to_serialize);
            return the_thing_json;
        }
        res.status(403);
        var thing_to_serialize = Map.of("message", "Error: already taken");
        var thing_serializer = new Gson();
        var thing_json = thing_serializer.toJson(thing_to_serialize);
        return thing_json;
    }
    private String listGames(Request req, Response res) {
        String my_auth_data = req.headers("authorization");
        if (the_service.check_for(my_auth_data)) {
            res.status(200);
            Collection<GameData> game_data_values = the_service.get_all_games();
            Collection<GameMetadata> new_maps = new ArrayList<>();
            for (GameData gd: game_data_values) {
                GameMetadata gm = new GameMetadata(gd.gameID(), gd.whiteUsername(), gd.blackUsername(), gd.gameName());
                new_maps.add(gm);
            }
            var thing_to_serialize = Map.of("games", new_maps);
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
        else {
            res.status(401);
            var thing_to_serialize = Map.of("message", "Error: unauthorized");
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
    }
    private String createGame(Request req, Response res) {
        GameName new_thing = new Gson().fromJson(req.body(), GameName.class);
        String my_auth_data = req.headers("authorization"); //req.attribute("Authorization");
        if (the_service.check_for(my_auth_data)) {
            res.status(200);
            var thing_to_serialize = Map.of("gameID", the_service.add_game(new_thing.gameName()));
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
        else {
            res.status(401);
            var thing_to_serialize = Map.of("message", "Error: unauthorized");
            var thing_serializer = new Gson();
            var thing_json = thing_serializer.toJson(thing_to_serialize);
            return thing_json;
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
