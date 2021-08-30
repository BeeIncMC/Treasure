package us.pixelgames.pixeltreasure.config;

import us.pixelgames.pixeltreasure.PixelTreasure;

public class ConfigHandler {
    private JsonConfig pixelConfig;
    private JsonConfig messagesConfig;
    private JsonConfig rewardsConfig;
    private JsonFolder schematicConfigs;
    private JsonFolder chestConfig;

    public ConfigHandler(PixelTreasure pixelTreasure) {
        this.pixelConfig = new JsonConfig(pixelTreasure, "config.json");
        this.messagesConfig = new JsonConfig(pixelTreasure, "messages.json");
        this.rewardsConfig = new JsonConfig(pixelTreasure, "rewards.json");
        this.schematicConfigs = new JsonFolder(pixelTreasure, "schematic");
        this.chestConfig = new JsonFolder(pixelTreasure, "chest");
    }

    public void onDisable() {
        /* Kill all of the configs */
        this.pixelConfig = null;
        this.messagesConfig = null;
        this.rewardsConfig = null;
        this.schematicConfigs = null;
        this.chestConfig = null;
    }

    public JsonConfig getPixelConfig() {
        return this.pixelConfig;
    }

    public JsonConfig getMessagesConfig() {
        return this.messagesConfig;
    }

    public JsonConfig getRewardsConfig() {
        return rewardsConfig;
    }

    public JsonFolder getSchematicConfigs() {
        return this.schematicConfigs;
    }

    public JsonFolder getChestConfig() {
        return chestConfig;
    }
}