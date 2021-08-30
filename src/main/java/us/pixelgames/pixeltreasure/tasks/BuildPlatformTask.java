package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.blocks.AnimatedBlock;
import us.pixelgames.pixeltreasure.items.Chest;
import us.pixelgames.pixeltreasure.util.BlockUtil;

import java.util.ArrayList;

public class BuildPlatformTask implements Runnable {
    private final PixelTreasure instance;
    private final Chest chest;
    private final Player player;
    private final Location centerBlock;
    private int sequenceNumber;

    public BuildPlatformTask(PixelTreasure instance, Chest chest, Player player, Location centerBlock) {
        this.instance = instance;
        this.chest = chest;
        this.player = player;
        this.centerBlock = centerBlock;
        this.sequenceNumber = 0;
    }

    @Override
    public void run() {
        ArrayList<AnimatedBlock> blocks = BlockUtil.getSchematicFromConfig(instance, chest.getSchematicName(), centerBlock);
        Location newCenter = centerBlock.clone().subtract(0, 1, 0);

        if (sequenceNumber == 0) {
            Bukkit.getScheduler().runTask(instance, new PlaceBlockTask(new MaterialData(Material.AIR), centerBlock));
        }

        if (sequenceNumber == 1) {
            player.teleport(new Location(centerBlock.getWorld(), centerBlock.getX() + 0.5f, centerBlock.getBlockY(), centerBlock.getBlockZ() + 0.5f, player.getLocation().getYaw(), player.getLocation().getPitch()));
        }

        try {
            if (chest.getBuildAnimation().get(sequenceNumber) != null) {
                String[] line = chest.getBuildAnimation().get(sequenceNumber).split(", ");

                for (String nString : line) {
                    int number = Integer.parseInt(nString);
                    Bukkit.getScheduler().runTaskLater(instance, new PlaceBlockTask(blocks.get(number).getBlockData(), getRelativeLocation(newCenter, number)), 1L);
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        sequenceNumber++;
    }

    public Location getRelativeLocation(Location centerBlock, int block) {
        switch (block) {
            case 0:
                return centerBlock.clone().add(3, 0, 3);
            case 1:
                return centerBlock.clone().add(2, 0, 3);
            case 2:
                return centerBlock.clone().add(1, 0, 3);
            case 3:
                return centerBlock.clone().add(0, 0, 3);
            case 4:
                return centerBlock.clone().add(-1, 0, 3);
            case 5:
                return centerBlock.clone().add(-2, 0, 3);
            case 6:
                return centerBlock.clone().add(-3, 0, 3);
            // NEW ROW
            case 7:
                return centerBlock.clone().add(3, 0, 2);
            case 8:
                return centerBlock.clone().add(2, 0, 2);
            case 9:
                return centerBlock.clone().add(1, 0, 2);
            case 10:
                return centerBlock.clone().add(0, 0, 2);
            case 11:
                return centerBlock.clone().add(-1, 0, 2);
            case 12:
                return centerBlock.clone().add(-2, 0, 2);
            case 13:
                return centerBlock.clone().add(-3, 0, 2);
            // NEW ROW
            case 14:
                return centerBlock.clone().add(3, 0, 1);
            case 15:
                return centerBlock.clone().add(2, 0, 1);
            case 16:
                return centerBlock.clone().add(1, 0, 1);
            case 17:
                return centerBlock.clone().add(0, 0, 1);
            case 18:
                return centerBlock.clone().add(-1, 0, 1);
            case 19:
                return centerBlock.clone().add(-2, 0, 1);
            case 20:
                return centerBlock.clone().add(-3, 0, 1);
            // NEW ROW
            case 21:
                return centerBlock.clone().add(3, 0, 0);
            case 22:
                return centerBlock.clone().add(2, 0, 0);
            case 23:
                return centerBlock.clone().add(1, 0, 0);
            case 25:
                return centerBlock.clone().add(-1, 0, 0);
            case 26:
                return centerBlock.clone().add(-2, 0, 0);
            case 27:
                return centerBlock.clone().add(-3, 0, 0);
            // NEW ROW
            case 28:
                return centerBlock.clone().add(3, 0, -1);
            case 29:
                return centerBlock.clone().add(2, 0, -1);
            case 30:
                return centerBlock.clone().add(1, 0, -1);
            case 31:
                return centerBlock.clone().add(0, 0, -1);
            case 32:
                return centerBlock.clone().add(-1, 0, -1);
            case 33:
                return centerBlock.clone().add(-2, 0, -1);
            case 34:
                return centerBlock.clone().add(-3, 0, -1);
            // NEW ROW
            case 35:
                return centerBlock.clone().add(3, 0, -2);
            case 36:
                return centerBlock.clone().add(2, 0, -2);
            case 37:
                return centerBlock.clone().add(1, 0, -2);
            case 38:
                return centerBlock.clone().add(0, 0, -2);
            case 39:
                return centerBlock.clone().add(-1, 0, -2);
            case 40:
                return centerBlock.clone().add(-2, 0, -2);
            case 41:
                return centerBlock.clone().add(-3, 0, -2);
            // NEW ROW
            case 42:
                return centerBlock.clone().add(3, 0, -3);
            case 43:
                return centerBlock.clone().add(2, 0, -3);
            case 44:
                return centerBlock.clone().add(1, 0, -3);
            case 45:
                return centerBlock.clone().add(0, 0, -3);
            case 46:
                return centerBlock.clone().add(-1, 0, -3);
            case 47:
                return centerBlock.clone().add(-2, 0, -3);
            case 48:
                return centerBlock.clone().add(-3, 0, -3);
            default:
                return centerBlock;
        }
    }
}