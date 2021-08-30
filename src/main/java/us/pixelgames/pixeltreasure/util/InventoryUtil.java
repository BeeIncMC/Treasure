package us.pixelgames.pixeltreasure.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.inventory.CratesInventory;
import us.pixelgames.pixeltreasure.items.ChestItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class InventoryUtil {
    /**
     * Load the GUI Inventory from main config
     */
    public static CratesInventory loadInventoryFromConfig(PixelTreasure instance, Player player) {
        try {
            Gson GSON = instance.getConfigHandler().getPixelConfig().getGSON();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(instance.getConfigHandler().getPixelConfig().getPath().toFile()));
            JsonReader jsonReader = new JsonReader(bufferedReader);
            JsonObject mainConfig = GSON.fromJson(jsonReader, JsonObject.class);
            JsonObject inventory = mainConfig.getAsJsonObject("opening-gui");
            JsonArray slots = inventory.getAsJsonArray("slots");
            List<ChestItem> chestItems = new ArrayList<>();

            for(JsonElement jsonElement:slots) {
                JsonObject itemInfo = jsonElement.getAsJsonObject();
                List<String> loreLines = new ArrayList<>();
                for(JsonElement jsonElement1:itemInfo.get("lore").getAsJsonArray()){
                    loreLines.add(jsonElement1.getAsString());
                }
                chestItems.add(new ChestItem(itemInfo.get("display-name").getAsString(), itemInfo.get("slot").getAsInt(), new MaterialData(Material.getMaterial(itemInfo.get("type").getAsString())), itemInfo.get("enchanted").getAsBoolean(), itemInfo.get("chest").getAsString(), loreLines));
            }
            CratesInventory cratesInventory = new CratesInventory(instance, player, inventory.get("display-name").getAsString(),
                    inventory.get("slot-count").getAsInt(),
                    Material.valueOf(inventory.get("filler").getAsString()), chestItems);

            return cratesInventory;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}