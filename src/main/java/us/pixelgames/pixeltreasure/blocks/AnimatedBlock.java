package us.pixelgames.pixeltreasure.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

public class AnimatedBlock {
    private final MaterialData blockData;
    private final Location blockLocation;

    /**
     * Simple object class for creating animated blocks when placed
     * Used by BuildAnimation and Schematic class
     * Should only be created via .json's using the BlockUtil util class
     */
    public AnimatedBlock(MaterialData materialData, Location blockLocation) {
        this.blockData = materialData;
        this.blockLocation = blockLocation;
    }

    public AnimatedBlock(Block block, Location newLocation) {
        this(block.getState().getData(), newLocation);
    }

    @Override
    public String toString() {
        return blockData.getItemType() + ", " + blockData.getData() + ", " + blockLocation.getBlockX() + ", " + blockLocation.getBlockY() + ", " + blockLocation.getZ() + ", " + blockLocation.getYaw() + ", " + blockLocation.getPitch();
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public MaterialData getBlockData() {
        return blockData;
    }
}