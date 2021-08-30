package us.pixelgames.pixeltreasure.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.EnumRarity;
import us.pixelgames.pixeltreasure.items.Loot;
import us.pixelgames.pixeltreasure.items.Treasure;
import us.pixelgames.pixeltreasure.tasks.OpenLootTask;
import us.pixelgames.pixeltreasure.util.BlockUtil;
import us.pixelgames.pixeltreasure.util.InventoryUtil;
import us.pixelgames.pixeltreasure.util.MessageUtil;
import us.pixelgames.pixeltreasure.util.TreasureUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockListener implements Listener {
    private final PixelTreasure instance;

    public BlockListener(PixelTreasure instance) {
        this.instance = instance;
    }

    @EventHandler
    public void chestInteractEvent(PlayerInteractEvent playerInteractEvent) {
        // check to if it's a block first
        if (playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || playerInteractEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Block block = playerInteractEvent.getClickedBlock();
            // check if it's a treasure chest
            if (BlockUtil.isBlockTreasureChest(block.getLocation(), instance)) {
                playerInteractEvent.getPlayer().openInventory(InventoryUtil.loadInventoryFromConfig(instance, playerInteractEvent.getPlayer()).getInventory());
                InventoryUtil.loadInventoryFromConfig(instance, playerInteractEvent.getPlayer());
                playerInteractEvent.setCancelled(true);
            }
            if(TreasureUtil.getLootAt(block.getLocation()) != null) {
                Loot loot = TreasureUtil.getLootAt(block.getLocation());

                if(loot.getPlayer().equals(playerInteractEvent.getPlayer())) {
                    if(!TreasureUtil.getLootAt(block.getLocation()).isOpened()) {
                        // REWARD PLAYER
                        Bukkit.getScheduler().runTaskLater(instance, new OpenLootTask(instance, playerInteractEvent.getPlayer(), TreasureUtil.getLootAt(block.getLocation()), true), 20L);
                    }
                    playerInteractEvent.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void chestBreakEvent(BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.getPlayer() != null) {
            Player player = blockBreakEvent.getPlayer();

            if (player.isSneaking()) {
                // check if it's a treasure chest
                if (BlockUtil.isBlockTreasureChest(blockBreakEvent.getBlock().getLocation(), instance)) {
                    blockBreakEvent.setCancelled(true);
                }
            }
        }
    }
}