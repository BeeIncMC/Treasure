package us.pixelgames.pixeltreasure.tasks.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import us.pixelgames.pixeltreasure.PixelTreasure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class ConnectToDBTask implements Runnable {
    private final PixelTreasure instance;

    public ConnectToDBTask(PixelTreasure instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        if (instance.getMySQL() != null) {
            instance.getLogger().log(Level.INFO, "Connection already established!");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Gson GSON = instance.getConfigHandler().getPixelConfig().getGSON();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(instance.getConfigHandler().getPixelConfig().getPath().toFile()));
            JsonReader jsonReader = new JsonReader(bufferedReader);
            JsonObject mainConfig = GSON.fromJson(jsonReader, JsonObject.class);
            JsonObject database = mainConfig.getAsJsonObject("database");
            String url = "jdbc:mysql://" + database.get("host").getAsString() + ":" + database.get("port").getAsString() + "/" + database.get("database").getAsString();
            instance.getLogger().log(Level.INFO, "Attempting to connect to: " + url);
            try {
                instance.establishConnection(DriverManager.getConnection(url, database.get("username").getAsString(), database.get("password").getAsString()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
