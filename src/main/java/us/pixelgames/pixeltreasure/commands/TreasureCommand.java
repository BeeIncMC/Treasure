package us.pixelgames.pixeltreasure.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import us.pixelgames.pixeltreasure.PixelTreasure;

public final class TreasureCommand implements CommandExecutor {
    private final PixelTreasure instance;

    public TreasureCommand(PixelTreasure instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            return Commands.commandHelp(commandSender, args, instance);
        }
        if (args[0] != null) {
            switch (args[0].toLowerCase()) {
                case "add":
                    return Commands.commandAdd(commandSender, instance, args);
                case "remove":
                    return Commands.commandRemove(commandSender, instance, args);
                case "setstation":
                    return Commands.commandSetStation(commandSender, instance);
                case "removestation":
                    return Commands.commandRemoveStation(commandSender, instance);
                case "schematic":
                    return Commands.commandSchematic(commandSender, args, instance);
                case "chest":
                    return Commands.commandChest(commandSender, args, instance);
                default:
                    return Commands.commandHelp(commandSender, args, instance);
            }
        }
        return Commands.commandHelp(commandSender, args, instance);
    }
}