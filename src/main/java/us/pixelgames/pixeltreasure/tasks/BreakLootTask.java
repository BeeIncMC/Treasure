package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.Loot;
import us.pixelgames.pixeltreasure.items.Treasure;

public class BreakLootTask implements Runnable {
    private final Treasure treasure;
    private final Location centerBlock;

    public BreakLootTask(Treasure treasure, Location centerBlock) {
        this.treasure = treasure;
        this.centerBlock = centerBlock;
    }

    @Override
    public void run() {
        for(Loot loot:treasure.getTreasureLoot()) {
            for(Entity entity:loot.getLocation().getWorld().getNearbyEntities(loot.getLocation(), 10, 5, 10)) {
                if(entity.getType().equals(EntityType.DROPPED_ITEM)) {
                    entity.remove();
                }
                if(entity.getType().equals(EntityType.ARMOR_STAND)) {
                    entity.remove();
                }
            }
            loot.getLocation().getBlock().setType(Material.AIR);
        }
        centerBlock.getBlock().setType(Material.CHEST);
    }
}