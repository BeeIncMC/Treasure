package us.pixelgames.pixeltreasure.config;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonData {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final Path path, directory;
    private final Plugin main;
    private final Map<String, Object> values = new HashMap<>();

    public JsonData(Plugin plugin, File directory, File file) {
        this.main = plugin;
        this.directory = directory.toPath();
        this.path = file.toPath();
        reload();
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void reload() {
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