package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.Loot;

public class PlaceTreasureTask implements Runnable {
    private final PixelTreasure instance;
    private final Loot loot;
    private final Player player;
    private final Location centerBlock;

    public PlaceTreasureTask(PixelTreasure instance, Loot loot, Player player, Location centerBlock) {
        this.instance = instance;
        this.loot = loot;
        this.player = player;
        this.centerBlock = centerBlock;
    }

    @Override
    public void run() {
        Block block = loot.getLocation().getBlock();
        block.setType(Material.CHEST);
        BlockState state = block.getState();
        org.bukkit.material.Chest chest = new org.bukkit.material.Chest(loot.getBlockFace());
        state.setType(Material.CHEST);
        state.setData(chest);
        state.setType(Material.CHEST);
        state.update();
    }
}