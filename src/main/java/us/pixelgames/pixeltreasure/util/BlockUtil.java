  
package us.pixelgames.pixeltreasure.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.blocks.AnimatedBlock;
import us.pixelgames.pixeltreasure.config.ChestLocation;
import us.pixelgames.pixeltreasure.config.JsonConfig;
import us.pixelgames.pixeltreasure.config.JsonData;
import us.pixelgames.pixeltreasure.items.*;
import us.pixelgames.pixeltreasure.tasks.PlaceBlockTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.logging.Level;

public final class BlockUtil {
    /**
     * Load all of the chest locations from the config
     *
     * @param jsonConfig - the config to look into (should be default)
     * @return - array of chest locations
     */
    public static ChestLocation[] loadLocationsFromConfig(JsonConfig jsonConfig) {
        return new Gson().fromJson(jsonConfig.getValues().get("locations").toString(), ChestLocation[].class);
    }

    /**
     * Save all of the locations to the config
     *
     * @param jsonConfig - config to save to
     * @param locations  - locations to save
     */
    public static void saveLocationsToConfig(JsonConfig jsonConfig, ArrayList<ChestLocation> locations) {
        jsonConfig.getValues().put("locations", locations.toArray(new ChestLocation[0]));
        jsonConfig.save();
        jsonConfig.reload();
    }

    public static Byte fixedData(Byte originalData) {
        switch (originalData.intValue()) {
            case 0:
                return Byte.parseByte("1");
            case 2:
                return Byte.parseByte("3");
            case 3:
                return Byte.parseByte("2");
            default:
                return Byte.parseByte("0");
        }
    }

    /**
     * List all schematics loaded
     * @param instance
     * @return
     */
    public static String listSchematics(PixelTreasure instance) {
        Set<String> jsonData = instance.getConfigHandler().getSchematicConfigs().getValues().keySet();

        StringBuilder stringBuilder = new StringBuilder();
        for (String strings : jsonData) {
            stringBuilder.append(strings).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Load specific schematic from configs
     *
     * @param instance - instance of javaplugin
     * @param name - the name of the schematic, will save file with this name
     * @param centerBlockLocation - center of the schematic
     */
    public static void loadSchematicFromConfig(PixelTreasure instance, String name, Location centerBlockLocation) {
        JsonData jsonData = instance.getConfigHandler().getSchematicConfigs().getConfig(name);

        ArrayList<Object> blocks = (ArrayList<Object>) jsonData.getValues().get("blocks");

        for(Object object:blocks) {
            if(object instanceof String) {
                String objectString = (String) object;
                String[] split = objectString.split(", ");

                AnimatedBlock animatedBlock = new AnimatedBlock(
                        new MaterialData(Material.getMaterial(split[0]), Byte.parseByte(split[1])),
                        new Location(centerBlockLocation.getWorld(),
                                centerBlockLocation.getBlockX() - Double.parseDouble(split[2]),
                                centerBlockLocation.getBlockY() + Double.parseDouble(split[3]),
                                centerBlockLocation.getBlockZ() - Double.parseDouble(split[4]),
                                Float.parseFloat(split[5]),
                                Float.parseFloat(split[6])));
                Bukkit.getScheduler().runTaskLater(instance, new PlaceBlockTask(animatedBlock.getBlockData(), animatedBlock.getBlockLocation()), 10);
            }
        }
    }

    public static ArrayList<AnimatedBlock> getSchematicFromConfig(PixelTreasure instance, String name, Location centerBlockLocation) {
        JsonData jsonData = instance.getConfigHandler().getSchematicConfigs().getConfig(name);
        ArrayList<Object> blocks = (ArrayList<Object>) jsonData.getValues().get("blocks");
        ArrayList<AnimatedBlock> animatedBlocks = new ArrayList<>();

        for (Object object : blocks) {
            if (object instanceof String) {
                String objectString = (String) object;
                String[] split = objectString.split(", ");

                AnimatedBlock animatedBlock = new AnimatedBlock(
                        new MaterialData(Material.getMaterial(split[0]), Byte.parseByte(split[1])),
                        new Location(centerBlockLocation.getWorld(),
                                centerBlockLocation.getBlockX() - Double.parseDouble(split[2]),
                                centerBlockLocation.getBlockY() + Double.parseDouble(split[3]),
                                centerBlockLocation.getBlockZ() - Double.parseDouble(split[4]),
                                Float.parseFloat(split[5]),
                                Float.parseFloat(split[6])));
                animatedBlocks.add(animatedBlock);
            }
        }
        return animatedBlocks;
    }

    /**
     * Function to save a schematic to the config
     *
     * @param instance            - instance of javaplugin
     * @param name                - the name of the schematic, will save file with this name
     * @param centerBlockLocation - center of the schematic
     */
    public static void saveSchematicToConfig(PixelTreasure instance, String name, Location centerBlockLocation) {
        JsonData jsonData = instance.getConfigHandler().getSchematicConfigs().getConfig(name);
        ArrayList<String> animatedBlocks = new ArrayList<>();

        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Location location = new Location(centerBlockLocation.getWorld(), centerBlockLocation.getBlockX() - 3 + j, centerBlockLocation.getBlockY() - 1, centerBlockLocation.getBlockZ() - 3 + i);
                Location newLocation = new Location(centerBlockLocation.getWorld(), -3 + j, -1, -3 + i, location.getYaw(), location.getPitch());
                animatedBlocks.add(new AnimatedBlock(location.getWorld().getBlockAt(location), newLocation).toString());
            }
        }
        jsonData.getValues().put("blocks", animatedBlocks.toArray(new String[0]));
        jsonData.save();
        jsonData.reload();
    }

    public static Chest loadChestFromConfig(PixelTreasure instance, String name) {
        try {
            Gson GSON = instance.getConfigHandler().getChestConfig().getConfig(name).getGSON();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(instance.getConfigHandler().getChestConfig().getConfig(name).getPath().toFile()));
            JsonReader jsonReader = new JsonReader(bufferedReader);
            JsonObject mainConfig = GSON.fromJson(jsonReader, JsonObject.class);
            JsonArray rewards = mainConfig.getAsJsonArray("rewards");
            JsonArray buildAnimation = mainConfig.getAsJsonArray("build-animation");
            JsonObject chestAnimation = mainConfig.getAsJsonObject("chest-animation");
            List<String> buildAnimationOrder = new ArrayList<>();
            List<Reward> rewardItems = new ArrayList<>();

            for (JsonElement jsonElement : rewards) {
                // Reference the main config & try to match it to this reward
                Gson GSON2 = instance.getConfigHandler().getRewardsConfig().getGSON();
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(instance.getConfigHandler().getRewardsConfig().getPath().toFile()));
                JsonReader jsonReader2 = new JsonReader(bufferedReader2);
                JsonObject mainConfig2 = GSON2.fromJson(jsonReader2, JsonObject.class);

                // Loop the names
                for(JsonElement mainReward:mainConfig2.getAsJsonArray("rewards")) {
                    JsonObject mainRewardObject = mainReward.getAsJsonObject();

                    if(mainRewardObject.get("name").getAsString().equalsIgnoreCase(jsonElement.getAsString())) {
                        instance.getLogger().log(Level.INFO, mainRewardObject.toString());

                        rewardItems.add(new Reward(mainRewardObject.get("display-name").getAsString(), new MaterialData(Material.getMaterial(mainRewardObject.get("type").getAsString())), mainRewardObject.get("enchanted").getAsBoolean(), mainRewardObject.get("unique").getAsBoolean(), mainRewardObject.get("command").getAsString(), mainRewardObject.get("permission").getAsString(), EnumRarity.valueOf(mainRewardObject.get("rarity").getAsString())));
                    }
                }
            }
            for(JsonElement jsonElement : buildAnimation) {
                buildAnimationOrder.add(jsonElement.getAsString());
            }
            return new Chest(mainConfig.get("display-name").getAsString(), mainConfig.get("schematic").getAsString(), mainConfig.get("chest-count").getAsInt(), rewardItems, buildAnimationOrder,
                    new ChestAnimation(chestAnimation.getAsJsonObject().get("animation").getAsString(), chestAnimation.getAsJsonObject().get("head").getAsString(), chestAnimation.getAsJsonObject().get("data").getAsString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveChestToConfig(PixelTreasure instance, String name) {
        JsonData jsonData = instance.getConfigHandler().getChestConfig().getConfig(name);
        jsonData.save();
        jsonData.reload();
    }

    /**
     * Simple function to grab whatever chest location a player is looking at
     *
     * @param player - player in question, careful with this being null
     * @return - location the block is at
     */
    public static Location getBlockPlayerIsLookingAt(Player player) {
        Block targetBlock = player.getTargetBlock(transparentTypes(), 100);

        if (targetBlock == null) {
            return null;
        }
        return targetBlock.getLocation();
    }

    public static Location getChestNearPlayer(PixelTreasure instance, Player player) {
        Location location = player.getLocation();

        int radius = 5;

        for (int x = radius; x >= -radius; x--) {
            for (int y = radius; y >= -radius; y--) {
                for (int z = radius; z >= -radius; z--) {
                    Location targetLocation = new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    if (isBlockTreasureChest(targetLocation, instance)) {
                        return targetLocation;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Simple function to check if a block is a treasure chest or not
     *
     * @param location - location of the block
     * @return - whether the block is a treasurechest or not
     */
    public static boolean isBlockTreasureChest(Location location, PixelTreasure instance) {
        Block block = location.getWorld().getBlockAt(location);

        if (!(chestTypes().contains(block.getType()))) {
            return false;
        }

        ChestLocation[] s = BlockUtil.loadLocationsFromConfig(instance.getConfigHandler().getPixelConfig());
        ArrayList<ChestLocation> locations = new ArrayList<>(Arrays.asList(s));

        for (ChestLocation location1 : locations) {
            if (location1.toLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A set of all the different treasure chest types, modify when needed
     * <p>
     * TODO: Convert to config
     */
    public static Set<Material> chestTypes() {
        Set<Material> materialSet = new HashSet<>();
        materialSet.add(Material.CHEST);
        materialSet.add(Material.ENDER_CHEST);
        materialSet.add(Material.TRAPPED_CHEST);

        return materialSet;
    }

    public static Set<Material> transparentTypes() {
        Set<Material> materialSet = new HashSet<>();
        materialSet.add(Material.AIR);

        return materialSet;
    }
}