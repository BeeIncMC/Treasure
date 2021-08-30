package us.pixelgames.pixeltreasure.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;

public class EntityListener implements Listener {
    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event) {
        if(event.getItem().getItemStack().getItemMeta().hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
            event.setCancelled(true);
        }
    }
}