package us.pixelgames.pixeltreasure;

import org.bukkit.plugin.java.JavaPlugin;
import us.pixelgames.pixeltreasure.commands.TreasureCommand;
import us.pixelgames.pixeltreasure.config.ConfigHandler;
import us.pixelgames.pixeltreasure.inventory.CratesInventory;
import us.pixelgames.pixeltreasure.listener.BlockListener;
import us.pixelgames.pixeltreasure.listener.EntityListener;
import us.pixelgames.pixeltreasure.tasks.data.ConnectToDBTask;

import java.sql.Connection;

public final class PixelTreasure extends JavaPlugin {
    private ConfigHandler configHandler;
    private Connection connection;

    @Override
    public void onEnable() {
        /* Register all of the configs for the plugin */
        this.configHandler = new ConfigHandler(this);

        /* Register the commands for the plugin */
        getCommand("treasure").setExecutor(new TreasureCommand(this));

        /* Register listeners for the plugin */
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new CratesInventory(this), this);

        /* Connect to the DB */
        getServer().getScheduler().runTask(this, new ConnectToDBTask(this));
    }

    @Override
    public void onDisable() {
        configHandler.onDisable();
    }

    public void establishConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getMySQL() {
        return connection;
    }

    public ConfigHandler getConfigHandler() {
        return this.configHandler;
    }
}