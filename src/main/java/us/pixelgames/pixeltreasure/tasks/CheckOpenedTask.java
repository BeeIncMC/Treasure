package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.Loot;
import us.pixelgames.pixeltreasure.items.Treasure;

public class CheckOpenedTask implements Runnable {
    private final PixelTreasure instance;
    private final Treasure treasure;
    private final Player player;

    public CheckOpenedTask(PixelTreasure instance, Player player, Treasure treasure) {
        this.instance = instance;
        this.treasure = treasure;
        this.player = player;
    }

    @Override
    public void run() {
        int amountOpened = 0;

        for(Loot loot:treasure.getTreasureLoot()) {
            if(loot.isOpened()) amountOpened++;
        }

        for(Loot loot:treasure.getTreasureLoot()) {
            if(amountOpened <= 4) {
                if(!loot.isOpened()) {
                    Bukkit.getScheduler().runTaskLater(instance, new OpenLootTask(instance, player, loot, true), 40L);
                }
            } else {
                if(!loot.isOpened()) {
                    Bukkit.getScheduler().runTaskLater(instance, new OpenLootTask(instance, player, loot, false), 40L);
                }
            }
        }
    }
}