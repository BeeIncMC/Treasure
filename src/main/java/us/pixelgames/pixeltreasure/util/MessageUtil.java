package us.pixelgames.pixeltreasure.util;

import org.bukkit.ChatColor;
import us.pixelgames.pixeltreasure.PixelTreasure;

public final class MessageUtil {
    public static String message(String string, PixelTreasure instance) {
        if (string.equalsIgnoreCase("treasure-help")) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String strings : instance.getConfigHandler().getMessagesConfig().getStrings("treasure-help")) {
                stringBuilder.append(strings).append("\n");
            }
            return colour(stringBuilder.toString());
        }
        return colour(instance.getConfigHandler().getMessagesConfig().getString(string).replaceAll("%prefix%", instance.getConfigHandler().getMessagesConfig().getString("prefix")));
    }

    public static String colour(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String stripColour(String message) {
        return ChatColor.stripColor(message);
    }
}