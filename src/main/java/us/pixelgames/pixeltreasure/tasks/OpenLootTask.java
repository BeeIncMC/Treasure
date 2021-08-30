package us.pixelgames.pixeltreasure.tasks;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.*;
import us.pixelgames.pixeltreasure.util.MessageUtil;
import us.pixelgames.pixeltreasure.util.TreasureUtil;

public class OpenLootTask implements Runnable {
    private final PixelTreasure instance;
    private final Loot loot;
    private final boolean doReward;
    private final Player player;

    public OpenLootTask(PixelTreasure instance, Player player, Loot loot, boolean doReward) {
        this.instance = instance;
        this.loot = loot;
        this.doReward = doReward;
        this.player = player;
    }

    @Override
    public void run() {
        Reward reward = loot.getReward();
        ItemStack rewardItem = reward.getRewardItem();
        Location location = loot.getLocation();

        Treasure playersTreasure = TreasureUtil.getPlayersTreasure(player);

        int amountOpened = 0;

        for(Loot loot:playersTreasure.getTreasureLoot()) {
            if(loot.isOpened()) amountOpened++;
        }

        if(amountOpened <= playersTreasure.getChest().getChestAmount()) {
            // Chest animation
            World world = ((CraftWorld) location.getBlock().getWorld()).getHandle();
            BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(blockPosition);
            world.playBlockAction(blockPosition, tileEntityChest.w(), 1, 1);

            // Create fake item for the display
            Item droppedItem = location.getWorld().dropItem(new Location(location.getWorld(), location.getBlockX() + 0.5f, location.getBlockY() + 0.5, location.getBlockZ() + 0.5f), rewardItem);
            droppedItem.setVelocity(new Vector());
            droppedItem.setPickupDelay(9999);

            // Create two armorstands for display
            ArmorStand textDisplay = (ArmorStand) player.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX() + 0.5f, location.getBlockY() - 0.25, location.getBlockZ() + 0.5f), EntityType.ARMOR_STAND);

            textDisplay.setCustomNameVisible(true);
            textDisplay.setVisible(false);
            textDisplay.setGravity(false);

            switch (reward.getEnumRarity()) {
                case RARE:
                    textDisplay.setCustomName(ChatColor.RED + reward.toString());
                    break;
                case LEGENDARY:
                    textDisplay.setCustomName(ChatColor.GOLD + reward.toString());
                    break;
                default:
                    textDisplay.setCustomName(ChatColor.GRAY + reward.toString());
                    break;
            }
            loot.openCrate();

            if (doReward) {
                // Give stuff to the player
                if (loot.getReward().getEnumRarity().equals(EnumRarity.RARE)) {
                    // broadcast to all players
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        player.sendMessage(MessageUtil.message("rare", instance).replaceAll("%player%", this.player.getPlayer().getName()).replaceAll("%item_found%", TreasureUtil.getLootAt(location).getReward().toString()).replaceAll("%chest_name%", TreasureUtil.getTreasureAt(location).getChest().getName()));
                    }
                } else if (loot.getReward().getEnumRarity().equals(EnumRarity.LEGENDARY)) {
                    // broadcast to all players
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        player.sendMessage(MessageUtil.message("legendary", instance).replaceAll("%player%", this.player.getPlayer().getName()).replaceAll("%item_found%", TreasureUtil.getLootAt(location).getReward().toString()).replaceAll("%chest_name%", TreasureUtil.getTreasureAt(location).getChest().getName()));
                    }
                }
            }
        }
    }
}