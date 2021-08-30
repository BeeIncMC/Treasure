package us.pixelgames.pixeltreasure.config;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class JsonFolder {
    private final Plugin main;
    private final Path path;
    private final File directory;
    private final JsonConfig blankConfig;
    private final String directoryName;
    private final Map<String, JsonData> configs = new HashMap<>();

    public JsonFolder(Plugin main, Path path, String directoryName) {
        this.main = main;
        this.path = path;
        this.directoryName = directoryName;

        blankConfig = new JsonConfig(main, "blank-" + directoryName + ".json");
        directory = new File("plugins/" + main.getName() + "/" + directoryName + "/");
        reload();
    }

    public JsonFolder(Plugin main, String name) {
        this(main, main.getDataFolder().toPath().resolve(name), name);
    }

    public void reload() {
        if (!directory.exists()) {
            directory.mkdir();
            directory.mkdirs();
        }
        File blankFile = new File("blank-" + directoryName + ".json");

        if(!blankFile.exists()) {
            try {
                Files.copy(blankConfig.getPath(), blankFile.toPath());
                main.getLogger().log(Level.INFO, "Copying default config file: " + blankFile.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                main.getLogger().log(Level.INFO, "Loading config file: " + file.toString());
                if (file.isFile() && !(configs.containsKey(file.getName().replaceAll(".json", "")))) {
                    JsonData jsonData = new JsonData(main, directory, file);
                    jsonData.reload();
                    configs.put(file.getName().replaceAll(".json", ""), jsonData);
                }
            }
        }
    }

    public void save() {
        for (JsonData jsonData : configs.values()) {
            jsonData.save();
        }
    }

    public Map<String, JsonData> getValues() {
        return configs;
    }

    public JsonData getConfig(String key) {
        if(!getValues().containsKey(key)) {
            try {
                main.getLogger().log(Level.INFO, "Data file " + key + " does note exist. Attempting to create it now.");
                Path newPath = Files.copy(blankConfig.getPath(), new File(directory.getPath() + "/" + key + ".json").toPath());
                main.getLogger().log(Level.INFO, "Copying new file, " + new File(directory.getPath() + "/" + key + ".json"));
                File newFile = newPath.toFile();
                JsonData jsonData = new JsonData(main, directory, newFile);
                jsonData.reload();
                configs.put(key, jsonData);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return getValues().get(key);
    }
}