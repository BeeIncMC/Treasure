package us.pixelgames.pixeltreasure.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.config.ChestLocation;
import us.pixelgames.pixeltreasure.util.BlockUtil;
import us.pixelgames.pixeltreasure.util.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Messages class for all of the subcommands of /treasure
 */
public final class Commands {
    /**
     * Command Params: "/treasure setstation"
     * Permission: "treasure.station.set"
     * <p>
     * This command will allow an admin to add a location of a chest, wherever you're looking at
     * Stores location in config and will load it in memory
     *
     * @return if command is successful
     */
    public static boolean commandSetStation(CommandSender commandSender, PixelTreasure instance) {
        if (!commandSender.hasPermission("treasure.station.set")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return false;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(MessageUtil.message("player-only", instance));
            return false;
        }

        Player player = (Player) commandSender;

        Location newLocation = BlockUtil.getBlockPlayerIsLookingAt(player);
        if (newLocation != null) {
            if (!BlockUtil.isBlockTreasureChest(newLocation, instance)) {
                ChestLocation[] s = BlockUtil.loadLocationsFromConfig(instance.getConfigHandler().getPixelConfig());
                ArrayList<ChestLocation> locations = new ArrayList<>(Arrays.asList(s));
                locations.add(new ChestLocation(newLocation));
                BlockUtil.saveLocationsToConfig(instance.getConfigHandler().getPixelConfig(), locations);

                commandSender.sendMessage(MessageUtil.message("treasure-setstation", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
            } else {
                commandSender.sendMessage(MessageUtil.message("treasure-alreadyexists", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
            }
            return true;
        }
        commandSender.sendMessage(MessageUtil.message("treasure-doesnotexist", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
        return true;
    }

    /**
     * Command Params: "/treasure removestation"
     * Permission: "treasure.station.set"
     * <p>
     * This command will allow an admin to remove a location of a chest, wherever you're looking at
     * Stores location in config and will load it in memory
     *
     * @return if command is successful
     */
    public static boolean commandRemoveStation(CommandSender commandSender, PixelTreasure instance) {
        if (!commandSender.hasPermission("treasure.station.set")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return false;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(MessageUtil.message("player-only", instance));
            return false;
        }

        Player player = (Player) commandSender;

        Location newLocation = BlockUtil.getBlockPlayerIsLookingAt(player);
        if (newLocation != null) {
            if (BlockUtil.isBlockTreasureChest(newLocation, instance)) {
                ChestLocation[] s = BlockUtil.loadLocationsFromConfig(instance.getConfigHandler().getPixelConfig());
                ArrayList<ChestLocation> locations = new ArrayList<>(Arrays.asList(s));
                locations.removeIf(chestLocation -> chestLocation.toLocation().equals(newLocation));
                BlockUtil.saveLocationsToConfig(instance.getConfigHandler().getPixelConfig(), locations);
                commandSender.sendMessage(MessageUtil.message("treasure-removestation", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
            } else {
                commandSender.sendMessage(MessageUtil.message("treasure-doesnotexist", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
            }
            return true;
        }
        return false;
    }

    /**
     * Command Params: "/treasure add <chestname> <amount> <player>"
     * Permission: "treasure.add"
     * <p>
     * This command will allow an admin to add chests to players, executable from console as well
     * Will update the DB with the new chest count
     *
     * @return if command is successful
     */
    public static boolean commandAdd(CommandSender commandSender, PixelTreasure instance, String[] args) {
        if (!commandSender.hasPermission("treasure.add")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return false;
        }
        return true;
    }

    /**
     * Command Params: "/treasure remove <chestname> <amount> <player>"
     * Permission: "treasure.remove"
     * <p>
     * This command will allow an admin to remove chests from players, executable from console as well
     * Will update the DB with the new chest count
     *
     * @return if command is successful
     */
    public static boolean commandRemove(CommandSender commandSender, PixelTreasure instance, String[] args) {
        if (!commandSender.hasPermission("treasure.remove")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return false;
        }
        return true;
    }

    /**
     * Command Params: "/treasure save <name>"
     * Permission: "treasure.save"
     *
     * @return if command is successful
     */
    public static boolean commandSchematic(CommandSender commandSender, String[] args, PixelTreasure instance) {
        if (!commandSender.hasPermission("treasure.schematic")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return true;
        }
        if (args == null) {
            commandSender.sendMessage(MessageUtil.message("invalid-arguments", instance) + Arrays.toString(args));
            return true;
        }
        Player player = (Player) commandSender;
        if(args[1] != null) {
            switch (args[1].toLowerCase()) {
                case "save":
                    Location newLocation = BlockUtil.getBlockPlayerIsLookingAt(player);
                    if (newLocation != null) {
                        if (BlockUtil.isBlockTreasureChest(newLocation, instance)) {
                            String savedSchematicName = args[2];
                            BlockUtil.saveSchematicToConfig(instance, savedSchematicName, newLocation);
                            commandSender.sendMessage(MessageUtil.message("treasure-save", instance).replaceAll("%schematic%", savedSchematicName));
                        } else {
                            commandSender.sendMessage(MessageUtil.message("treasure-doesnotexist", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
                        }
                    }
                    break;
                case "load":
                    newLocation = BlockUtil.getBlockPlayerIsLookingAt(player);
                    if (newLocation != null) {
                        if (BlockUtil.isBlockTreasureChest(newLocation, instance)) {
                            String savedSchematicName = args[2];
                            BlockUtil.loadSchematicFromConfig(instance, savedSchematicName, newLocation);
                            commandSender.sendMessage(MessageUtil.message("treasure-load", instance).replaceAll("%schematic%", savedSchematicName));
                        } else {
                            commandSender.sendMessage(MessageUtil.message("treasure-doesnotexist", instance).replaceAll("%location%", "(x: " + newLocation.getBlockX() + "y: " + newLocation.getBlockY() + "z: " + newLocation.getBlockZ() + ")"));
                        }
                    }
                    break;
                default:
                    commandSender.sendMessage(MessageUtil.message("treasure-list", instance) + BlockUtil.listSchematics(instance));
                    break;
            }
        }
        return true;
    }

    /**
     * Command Params: "/treasure save <name>"
     * Permission: "treasure.save"
     *
     * @return if command is successful
     */
    public static boolean commandChest(CommandSender commandSender, String[] args, PixelTreasure instance) {
        if (!commandSender.hasPermission("treasure.chest")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return true;
        }
        if (args == null) {
            commandSender.sendMessage(MessageUtil.message("invalid-arguments", instance) + Arrays.toString(args));
            return true;
        }
        if (args[1] != null) {
            if ("create".equalsIgnoreCase(args[1])) {
                if (args[2] != null) {
                    String chestName = args[2].toLowerCase();
                    BlockUtil.saveChestToConfig(instance, chestName);
                    commandSender.sendMessage(MessageUtil.message("treasure-chest-create", instance).replaceAll("%chest%", chestName));
                }
            } else {
                commandSender.sendMessage(MessageUtil.message("treasure-list", instance) + BlockUtil.listSchematics(instance));
            }
        }
        return true;
    }

    /**
     * Command Params: "/treasure help"
     * Permission: "treasure.help"
     * <p>
     * This command will show all available subcommands of /treasure
     * Also default if args are invalid or no args are present
     *
     * @return if command is successful
     */
    public static boolean commandHelp(CommandSender commandSender, String[] args, PixelTreasure instance) {
        if (!commandSender.hasPermission("treasure.help")) {
            commandSender.sendMessage(MessageUtil.message("invalid-permission", instance));
            return true;
        }
        if (args != null && args.length != 0) {
            commandSender.sendMessage(MessageUtil.message("invalid-arguments", instance) + Arrays.toString(args));
        }
        commandSender.sendMessage(MessageUtil.message("treasure-help", instance));
        return true;
    }
}