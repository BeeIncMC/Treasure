package us.pixelgames.pixeltreasure.util;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.items.Chest;
import us.pixelgames.pixeltreasure.items.Loot;
import us.pixelgames.pixeltreasure.items.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TreasureUtil {
    private static HashMap<Player, Treasure> lootChests = new HashMap<>();
    private static final Random treasureRNG = new Random();

    public static Treasure getPlayersTreasure(Player player) {
        return lootChests.get(player);
    }

    public static void addTreasureChests(Chest chest, Player player, Location centerLocation) {
        ArrayList<Loot> treasureLoot = new ArrayList<>();
        treasureLoot.add(new Loot(chest, BlockFace.NORTH, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() + 1, centerLocation.getBlockY(), centerLocation.getBlockZ() + 3), player));
        treasureLoot.add(new Loot(chest, BlockFace.NORTH, treasureRNG,new Location(centerLocation.getWorld(), centerLocation.getX() - 1, centerLocation.getBlockY(), centerLocation.getBlockZ() + 3), player));
        treasureLoot.add(new Loot(chest, BlockFace.SOUTH, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() + 1, centerLocation.getBlockY(), centerLocation.getBlockZ() - 3), player));
        treasureLoot.add(new Loot(chest, BlockFace.SOUTH, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() - 1, centerLocation.getBlockY(), centerLocation.getBlockZ() - 3), player));
        treasureLoot.add(new Loot(chest, BlockFace.EAST, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() - 3, centerLocation.getBlockY(), centerLocation.getBlockZ() + 1), player));
        treasureLoot.add(new Loot(chest, BlockFace.EAST, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() - 3, centerLocation.getBlockY(), centerLocation.getBlockZ() - 1), player));
        treasureLoot.add(new Loot(chest, BlockFace.WEST, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() + 3, centerLocation.getBlockY(), centerLocation.getBlockZ() + 1), player));
        treasureLoot.add(new Loot(chest, BlockFace.WEST, treasureRNG, new Location(centerLocation.getWorld(), centerLocation.getX() + 3, centerLocation.getBlockY(), centerLocation.getBlockZ() - 1), player));

        lootChests.put(player, new Treasure(treasureLoot, chest));
    }

    public static Loot getLootAt(Location location) {
        for(Treasure treasure:lootChests.values()) {
            for(Loot loot : treasure.getTreasureLoot()) {
                if(loot.getLocation().equals(location)){
                    return loot;
                }
            }
        }
        return null;
    }

    public static Treasure getTreasureAt(Location location) {
        for(Treasure treasure:lootChests.values()) {
            for(Loot loot : treasure.getTreasureLoot()) {
                if(loot.getLocation().equals(location)){
                    return treasure;
                }
            }
        }
        return null;
    }
}