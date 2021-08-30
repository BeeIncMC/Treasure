package us.pixelgames.pixeltreasure.config;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Plugin main;
    private final Path path;
    private final Map<String, Object> values = new HashMap<>();

    public JsonConfig(Plugin main, Path path) {
        this.main = main;
        this.path = path;
        reload();
    }

    public JsonConfig(Plugin main, String name) {
        this(main, main.getDataFolder().toPath().resolve(name));
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void reload() {
        if (Files.notExists(path)) {
            main.saveResource(path.getFileName().toString(), false);
        }
        try {
            values.putAll(GSON.fromJson(Files.newBufferedReader(path), values.getClass()));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public void save() {
        String json = GSON.toJson(values);
        try {
            Files.write(
                    path,
                    Collections.singletonList(json),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public String getString(String key) {
        return getValues().get(key).toString();
    }

    public ArrayList<String> getStrings(String key) {
        return (ArrayList<String>) getValues().get(key);
    }

    public Path getPath() {
        return path;
    }

    public Gson getGSON() {
        return GSON;
    }
}