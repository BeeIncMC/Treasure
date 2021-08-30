package us.pixelgames.pixeltreasure.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Wrapper for gson to serialize all of the treasure chest locations
 */
public class ChestLocation {
    private final String world;
    private final double x;
    private final double y;
    private final double z;

    /**
     * Parse in location to this class to convert all of the fields
     * Used for the locations section of the config.json
     *
     * @param location - Block location/position
     */
    public ChestLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    /**
     * Convert the location back for memory purposes
     *
     * @return - the converted location
     */
    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}