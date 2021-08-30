package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.Chest;
import us.pixelgames.pixeltreasure.items.Loot;
import us.pixelgames.pixeltreasure.items.Treasure;
import us.pixelgames.pixeltreasure.util.MessageUtil;
import us.pixelgames.pixeltreasure.util.TreasureUtil;

import java.util.ArrayList;
import java.util.List;

public class OpenChestTask implements Runnable {
    private final PixelTreasure instance;
    private final Chest chest;
    private final Player player;
    private final Location centerBlock;

    public OpenChestTask(PixelTreasure instance, Chest chest, Player player, Location centerBlock) {
        this.instance = instance;
        this.chest = chest;
        this.player = player;
        this.centerBlock = centerBlock;
    }

    @Override
    public void run() {
        // Broadcast opening the crate to all players
        for(Player player:instance.getServer().getOnlinePlayers()) {
            player.sendMessage(MessageUtil.message("opening", instance).replaceAll("%player%", player.getName()).replaceAll("%chest_name%", chest.getName()));
        }
        // Build platform and teleport the player
        BuildPlatformTask buildPlatformTask = new BuildPlatformTask(instance, chest, player, centerBlock);
        for(int i = 0; i <= chest.getBuildAnimation().size(); i++) {
            Bukkit.getScheduler().runTaskLater(instance, buildPlatformTask, 20L);
        }
        // Create 8 treasure chests for the player & roll them
        TreasureUtil.addTreasureChests(chest, player, centerBlock);
        // Grab the player's treasure
        Treasure playersTreasure = TreasureUtil.getPlayersTreasure(player);
        // For now we'll just place them, adding animations is next
        for(Loot loot:playersTreasure.getTreasureLoot()) {
            // Place the open-able chests
            Bukkit.getScheduler().runTaskLater(instance, new PlaceTreasureTask(instance, loot, player, centerBlock), 40L);
        }
        // Check if the player has opened the chests yet, if not, open it up for them (60 seconds)
        Bukkit.getScheduler().runTaskLater(instance, new CheckOpenedTask(instance, player, playersTreasure), 600L);
        // Queue breaking the chests (90 seconds) also replaces the center chest
        Bukkit.getScheduler().runTaskLater(instance, new BreakLootTask(playersTreasure, centerBlock), 900L);
    }
}